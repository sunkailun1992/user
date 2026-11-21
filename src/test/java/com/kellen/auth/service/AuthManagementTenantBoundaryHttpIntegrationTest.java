package com.kellen.auth.service;

import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.InnerInterceptor;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kellen.auth.controller.AuthDeptController;
import com.kellen.auth.controller.AuthResourceController;
import com.kellen.auth.controller.AuthRoleController;
import com.kellen.auth.controller.AuthTenantController;
import com.kellen.auth.controller.AuthUserController;
import com.kellen.auth.keycloak.KeycloakAccountClient;
import com.kellen.auth.mq.producer.UserAccountEventProducer;
import com.kellen.auth.rpc.SecurityContextRpcServiceImpl;
import com.kellen.auth.service.impl.AuthDeptServiceImpl;
import com.kellen.auth.service.impl.AuthGrantServiceImpl;
import com.kellen.auth.service.impl.AuthResourceServiceImpl;
import com.kellen.auth.service.impl.AuthRoleServiceImpl;
import com.kellen.auth.service.impl.AuthTenantServiceImpl;
import com.kellen.auth.service.impl.AuthUserServiceImpl;
import com.kellen.auth.service.query.AuthDeptServiceQuery;
import com.kellen.auth.service.query.AuthResourceServiceQuery;
import com.kellen.auth.service.query.AuthRoleServiceQuery;
import com.kellen.auth.service.query.AuthTenantServiceQuery;
import com.kellen.auth.service.query.AuthUserServiceQuery;
import com.kellen.auth.service.results.AuthDeptServiceResults;
import com.kellen.auth.service.results.AuthResourceServiceResults;
import com.kellen.auth.service.results.AuthRoleServiceResults;
import com.kellen.auth.service.results.AuthTenantServiceResults;
import com.kellen.auth.service.results.AuthUserServiceResults;
import com.kellen.auth.support.AuthH2TestConfiguration;
import com.kellen.bean.GlobalExceptionHandler;
import com.kellen.config.web.ReqInterceptor;
import com.kellen.datapermission.DataPermissionContextHolder;
import com.kellen.security.UserContextHolder;
import com.kellen.security.config.TenantProperties;
import com.kellen.security.oauth2.FleetResourceServerAutoConfiguration;
import com.kellen.security.oauth2.SecurityUserCache;
import com.kellen.traffic.TrafficGovernanceProperties;
import com.kellen.utils.context.TenantContextHolder;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.oauth2.jwt.BadJwtException;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.sql.DataSource;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

/**
 * 真实 HTTP 经资源服务器链访问用户、角色、部门、资源与租户五个管理接口（第 5 阶段最终复核 Important 4）。
 *
 * <p>接口参数或请求体里的 {@code tenantId} 只能等于调用方的活动租户，平台超级管理员可以点名任一租户；越界在任何业务 SQL
 * 之前回 403，库里没有任何改动，也不调用 Keycloak（含越界重置别的租户账号的口令）。另覆盖：用户关联租户不能越到无权管理的租户、
 * 租户管理员改人时保留该用户在别的租户的关联、租户表只由平台超级管理员维护、租户列表对租户管理员只给自己的活动租户。</p>
 */
@SpringBootTest(classes = AuthManagementTenantBoundaryHttpIntegrationTest.Config.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        properties = {"spring.config.name=management-tenant-boundary-isolated", "spring.cloud.nacos.config.enabled=false",
                "spring.cloud.nacos.discovery.enabled=false", "spring.cloud.nacos.config.import-check.enabled=false",
                "spring.security.oauth2.resourceserver.jwt.issuer-uri=https://keycloak.invalid/realms/github-dev",
                "tenant.enabled=true", "tenant.ignore-without-tenant=false", "test.management.tenant.fixture=true"})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
class AuthManagementTenantBoundaryHttpIntegrationTest {
    private static final String A = "fixture-tenant-a", B = "fixture-tenant-b";
    /** 测试令牌前缀；令牌原文为前缀加用户编号。 */
    private static final String TOKEN_PREFIX = "synthetic-access-token-for-";
    /** 合成的临时口令，只给 Keycloak 替身。 */
    private static final String TEMPORARY = "Temp-Pass-2026x";
    private static final List<String> CALLERS = List.of("tenant-admin", "platform-admin");
    private static final List<String> TABLES = List.of("auth_user", "auth_user_tenant", "auth_role", "auth_dept", "auth_resource", "auth_tenant");
    /** 管理接口处理中（控制器阶段）发出的业务 SQL 次数；加载调用方业务上下文时的查询不算。 */
    private static final AtomicInteger BUSINESS_SQL = new AtomicInteger();
    private static final ThreadLocal<Boolean> HANDLING = ThreadLocal.withInitial(() -> false);
    @LocalServerPort int port;
    @Autowired DataSource dataSource;
    @Autowired SecurityUserCache cache;
    @Autowired KeycloakAccountClient keycloak;
    private JdbcTemplate jdbc;
    private final ObjectMapper json = new ObjectMapper();

    @BeforeEach void fixture() {
        TenantContextHolder.clear(); DataPermissionContextHolder.clear(); UserContextHolder.clear(); BUSINESS_SQL.set(0);
        Mockito.reset(keycloak);
        when(keycloak.createAccount(any(), any(), any(), any())).thenReturn("kc-created");
        AuthH2TestConfiguration.recreateTables(dataSource);
        jdbc = new JdbcTemplate(dataSource);
        jdbc.update("INSERT INTO auth_tenant(id,code,name,tenant_id,state,version) VALUES(?,?,?,?,1,1),(?,?,?,?,1,1)",
                A, "fixture-a", "fixture-a", A, B, "fixture-b", "fixture-b", B);
        user("tenant-admin", A, "TENANT_ADMIN", null); user("platform-admin", B, "PLATFORM_SUPER_ADMIN", null);
        for (String tenant : List.of(A, B)) {
            String p = tenant.equals(A) ? "a" : "b";
            jdbc.update("INSERT INTO auth_role(id,name,code,data_scope,tenant_id,state,version) VALUES(?,?,?,?,?,1,1)", tenant + "-manager", "manager", "manager", "ALL", tenant);
            jdbc.update("INSERT INTO auth_resource(id,name,code,resource_category,tenant_id,state,version) VALUES(?,?,?,?,?,1,1)",
                    tenant + "-manage", "manage", "user:auth:manage", "BACKEND", tenant);
            jdbc.update("INSERT INTO auth_role_resource(id,tenant_id,role_id,resource_id) VALUES(?,?,?,?)", tenant + "-rr", tenant, tenant + "-manager", tenant + "-manage");
            user(p + "-user", tenant, "TENANT_ADMIN", "kc-" + p + "-user");
            jdbc.update("INSERT INTO auth_user_tenant(id,user_id,relation_tenant_id,default_tenant,tenant_id,state) VALUES(?,?,?,true,?,1)", p + "-user-home", p + "-user", tenant, tenant);
            jdbc.update("INSERT INTO auth_role(id,name,code,data_scope,tenant_id,state,version) VALUES(?,?,?,?,?,1,1)", p + "-role", "role", p + "-role", "SELF", tenant);
            jdbc.update("INSERT INTO auth_dept(id,name,code,tenant_id,state,version) VALUES(?,?,?,?,1,1)", p + "-dept", "dept", p + "-dept", tenant);
            jdbc.update("INSERT INTO auth_resource(id,name,code,resource_category,tenant_id,state,version) VALUES(?,?,?,?,?,1,1)", p + "-res", "res", p + ":res", "BACKEND", tenant);
        }
        jdbc.update("INSERT INTO auth_user_role(id,tenant_id,user_id,role_id) VALUES(?,?,?,?),(?,?,?,?)",
                "tenant-admin-manager", A, "tenant-admin", A + "-manager", "platform-admin-manager", B, "platform-admin", B + "-manager");
        CALLERS.forEach(cache::bumpVersion);
    }
    @AfterEach void clear() { TenantContextHolder.clear(); DataPermissionContextHolder.clear(); UserContextHolder.clear(); }

    @Test void tenantAdminCannotReachAnotherTenantThroughAnyManagementEndpoint() throws Exception {
        var before = rows();
        for (var request : crossTenantRequests()) {
            BUSINESS_SQL.set(0);
            var response = call(request.method(), request.path(), "tenant-admin", request.body());
            assertThat(response.statusCode()).as(request.toString()).isEqualTo(403);
            assertThat(BUSINESS_SQL.get()).as(request.toString()).isZero();
            assertThat(rows()).as(request.toString()).isEqualTo(before);
        }
        verifyNoInteractions(keycloak);
    }

    /** 没点名租户：要么参数校验失败（本项目约定回 HTTP 200 加 REQUIRED_PARAMETER_MISSING），要么 403；都不查库、不给数据。 */
    @Test void missingTargetTenantNeverQueriesOrReturnsData() throws Exception {
        for (String path : List.of("/auth/manage/users/options", "/auth/manage/roles/options", "/auth/manage/depts/options", "/auth/manage/resources/options")) {
            BUSINESS_SQL.set(0);
            var response = call("GET", path, "tenant-admin", null);
            var body = json.readTree(response.body());
            assertThat(response.statusCode()).as(path).isIn(200, 403);
            assertThat(body.path("success").asBoolean(true)).as(path + " " + response.body()).isFalse();
            assertThat(body.path("data").isArray()).as(path).isFalse();
            assertThat(BUSINESS_SQL.get()).as(path).isZero();
        }
    }

    @Test void ownTenantStaysFullyManageableAndPlatformAdminMayTargetAnotherTenant() throws Exception {
        for (String kind : List.of("users", "roles", "depts", "resources")) {
            var own = call("GET", "/auth/manage/" + kind + "/options?tenantId=" + A, "tenant-admin", null);
            assertThat(own.statusCode()).as(kind).isEqualTo(200);
            assertThat(ids(data(own))).as(kind).anyMatch(id -> id.startsWith("a-")).noneMatch(id -> id.startsWith("b-"));
            var platform = call("GET", "/auth/manage/" + kind + "/options?tenantId=" + A, "platform-admin", null);
            assertThat(platform.statusCode()).as(kind).isEqualTo(200);
            assertThat(ids(data(platform))).as(kind).anyMatch(id -> id.startsWith("a-")).noneMatch(id -> id.startsWith("b-"));
        }
        var page = call("GET", "/auth/manage/users?current=1&size=10&tenantId=" + A, "tenant-admin", null);
        assertThat(page.statusCode()).isEqualTo(200);
        assertThat(ids(data(page).path("records"))).contains("a-user").doesNotContain("b-user");
        var renamed = call("PUT", "/auth/manage/users/a-user", "tenant-admin", Map.of("tenantId", A, "version", 1, "nickname", "renamed", "password", TEMPORARY));
        assertThat(renamed.statusCode()).isEqualTo(200);
        assertThat(data(renamed).asBoolean()).isTrue();
        assertThat(jdbc.queryForObject("SELECT nickname FROM auth_user WHERE id='a-user'", String.class)).isEqualTo("renamed");
        verify(keycloak).resetPassword("kc-a-user", TEMPORARY);
    }

    @Test void crossTenantPasswordResetNeverReachesKeycloak() throws Exception {
        var response = call("PUT", "/auth/manage/users/b-user", "tenant-admin", Map.of("tenantId", B, "version", 1, "password", TEMPORARY));
        assertThat(response.statusCode()).isEqualTo(403);
        verify(keycloak, never()).resetPassword(any(), any());
        verify(keycloak, never()).updateProfile(any(), any(), any());
        verify(keycloak, never()).setEnabled(any(), Mockito.anyBoolean());
    }

    @Test void tenantAdminCannotAssociateAUserWithATenantItCannotManage() throws Exception {
        var before = rows();
        var created = call("POST", "/auth/manage/users", "tenant-admin", Map.of("tenantId", A, "tenantIds", List.of(A, B), "username", "new-x", "password", TEMPORARY));
        assertThat(created.statusCode()).isEqualTo(403);
        var updated = call("PUT", "/auth/manage/users/a-user", "tenant-admin", Map.of("tenantId", A, "version", 1, "tenantIds", List.of(A, B)));
        assertThat(updated.statusCode()).isEqualTo(403);
        assertThat(rows()).isEqualTo(before);
        verifyNoInteractions(keycloak);
    }

    @Test void tenantAdminEditKeepsTheUsersRelationsInOtherTenants() throws Exception {
        jdbc.update("INSERT INTO auth_user_tenant(id,user_id,relation_tenant_id,default_tenant,tenant_id,state) VALUES('a-user-in-b','a-user',?,false,?,1)", B, B);
        var narrowed = call("PUT", "/auth/manage/users/a-user", "tenant-admin", Map.of("tenantId", A, "version", 1, "tenantIds", List.of(A)));
        assertThat(narrowed.statusCode()).isEqualTo(200);
        assertThat(relations("a-user")).containsExactly(A, B);
        var unchanged = call("PUT", "/auth/manage/users/a-user", "tenant-admin", Map.of("tenantId", A, "version", 2, "tenantIds", List.of(A, B)));
        assertThat(unchanged.statusCode()).isEqualTo(200);
        assertThat(relations("a-user")).containsExactly(A, B);
    }

    @Test void platformAdminControlsAssociationsAcrossTenants() throws Exception {
        jdbc.update("INSERT INTO auth_user_tenant(id,user_id,relation_tenant_id,default_tenant,tenant_id,state) VALUES('a-user-in-b','a-user',?,false,?,1)", B, B);
        var narrowed = call("PUT", "/auth/manage/users/a-user", "platform-admin", Map.of("tenantId", A, "version", 1, "tenantIds", List.of(A)));
        assertThat(narrowed.statusCode()).isEqualTo(200);
        assertThat(relations("a-user")).containsExactly(A);
        var widened = call("PUT", "/auth/manage/users/a-user", "platform-admin", Map.of("tenantId", A, "version", 2, "tenantIds", List.of(A, B)));
        assertThat(widened.statusCode()).isEqualTo(200);
        assertThat(relations("a-user")).containsExactly(A, B);
    }

    @Test void onlyPlatformAdminMaintainsTenantsAndTenantAdminSeesOnlyItsOwn() throws Exception {
        var before = rows();
        for (var request : List.of(new Call("POST", "/auth/manage/tenants", Map.of("code", "intruder", "name", "intruder")),
                new Call("PUT", "/auth/manage/tenants/" + B, Map.of("version", 1, "name", "hijacked")),
                new Call("PUT", "/auth/manage/tenants/" + A, Map.of("version", 1, "name", "renamed-by-tenant-admin")),
                new Call("DELETE", "/auth/manage/tenants/" + B, null))) {
            BUSINESS_SQL.set(0);
            assertThat(call(request.method(), request.path(), "tenant-admin", request.body()).statusCode()).as(request.toString()).isEqualTo(403);
            assertThat(BUSINESS_SQL.get()).as(request.toString()).isZero();
        }
        assertThat(rows()).isEqualTo(before);
        assertThat(ids(data(call("GET", "/auth/manage/tenants/options", "tenant-admin", null)))).containsExactly(A);
        assertThat(ids(data(call("GET", "/auth/manage/tenants?current=1&size=10", "tenant-admin", null)).path("records"))).containsExactly(A);
        assertThat(call("GET", "/auth/manage/tenants/options?id=" + B, "tenant-admin", null).statusCode()).isEqualTo(403);
        assertThat(ids(data(call("GET", "/auth/manage/tenants/options", "platform-admin", null)))).containsExactlyInAnyOrder(A, B);
        var renamed = call("PUT", "/auth/manage/tenants/" + A, "platform-admin", Map.of("version", 1, "name", "renamed-by-platform"));
        assertThat(renamed.statusCode()).isEqualTo(200);
        assertThat(jdbc.queryForObject("SELECT name FROM auth_tenant WHERE id=?", String.class, A)).isEqualTo("renamed-by-platform");
    }

    private List<Call> crossTenantRequests() {
        List<Call> requests = new ArrayList<>();
        for (String kind : List.of("users", "roles", "depts", "resources")) {
            String target = switch (kind) { case "users" -> "b-user"; case "roles" -> "b-role"; case "depts" -> "b-dept"; default -> "b-res"; };
            requests.add(new Call("GET", "/auth/manage/" + kind + "/options?tenantId=" + B, null));
            requests.add(new Call("GET", "/auth/manage/" + kind + "?current=1&size=10&tenantId=" + B, null));
            requests.add(new Call("POST", "/auth/manage/" + kind, switch (kind) {
                case "users" -> Map.of("tenantId", B, "username", "intruder", "password", TEMPORARY);
                case "resources" -> Map.of("tenantId", B, "code", "intruder", "name", "intruder", "resourceCategory", "BACKEND");
                default -> Map.of("tenantId", B, "code", "intruder", "name", "intruder");
            }));
            requests.add(new Call("PUT", "/auth/manage/" + kind + "/" + target, kind.equals("users")
                    ? Map.of("tenantId", B, "version", 1, "nickname", "hijacked", "password", TEMPORARY)
                    : Map.of("tenantId", B, "version", 1, "name", "hijacked")));
            requests.add(new Call("DELETE", "/auth/manage/" + kind + "/" + target + "?tenantId=" + B, null));
        }
        return requests;
    }
    private Map<String, List<Map<String, Object>>> rows() {
        Map<String, List<Map<String, Object>>> rows = new java.util.LinkedHashMap<>();
        TABLES.forEach(table -> rows.put(table, jdbc.queryForList("SELECT * FROM " + table + " ORDER BY id")));
        return rows;
    }
    private List<String> relations(String userId) {
        return jdbc.queryForList("SELECT relation_tenant_id FROM auth_user_tenant WHERE user_id=? AND is_deleted=false ORDER BY relation_tenant_id", String.class, userId);
    }
    private void user(String id, String tenant, String admin, String subject) {
        jdbc.update("INSERT INTO auth_user(id,username,nickname,tenant_id,admin_type,idp_subject,state,version) VALUES(?,?,?,?,?,?,1,1)", id, id, id, tenant, admin, subject);
    }
    private JsonNode data(HttpResponse<String> response) throws Exception {
        assertThat(response.statusCode()).as(response.body()).isEqualTo(200);
        return json.readTree(response.body()).path("data");
    }
    private static List<String> ids(JsonNode array) {
        List<String> ids = new ArrayList<>();
        array.forEach(node -> ids.add(node.path("id").asText()));
        return ids;
    }
    private HttpResponse<String> call(String method, String path, String caller, Object body) throws Exception {
        var builder = HttpRequest.newBuilder(URI.create("http://127.0.0.1:" + port + path)).timeout(Duration.ofSeconds(10));
        if (caller != null) builder.header("Authorization", "Bearer " + TOKEN_PREFIX + caller);
        if (body == null) builder.method(method, HttpRequest.BodyPublishers.noBody());
        else builder.header("Content-Type", "application/json").method(method, HttpRequest.BodyPublishers.ofString(json.writeValueAsString(body)));
        try (HttpClient client = HttpClient.newBuilder().proxy(HttpClient.Builder.NO_PROXY).build()) {
            return client.send(builder.build(), HttpResponse.BodyHandlers.ofString());
        }
    }
    private record Call(String method, String path, Object body) { }

    @Configuration(proxyBeanMethods = false)
    @ConditionalOnProperty(name = "test.management.tenant.fixture", havingValue = "true")
    @Import({AuthH2TestConfiguration.class, AuthUserController.class, AuthRoleController.class, AuthDeptController.class, AuthResourceController.class,
            AuthTenantController.class, AuthUserServiceImpl.class, AuthRoleServiceImpl.class, AuthDeptServiceImpl.class, AuthResourceServiceImpl.class,
            AuthTenantServiceImpl.class, AuthUserServiceQuery.class, AuthRoleServiceQuery.class, AuthDeptServiceQuery.class, AuthResourceServiceQuery.class,
            AuthTenantServiceQuery.class, AuthUserServiceResults.class, AuthRoleServiceResults.class, AuthDeptServiceResults.class,
            AuthResourceServiceResults.class, AuthTenantServiceResults.class, AuthTenantManagementAccess.class, SecurityContextRpcServiceImpl.class,
            AuthGrantServiceImpl.class, AuthTenantAccess.class, AuthDataScopeResolver.class, AuthSecurityContextInvalidator.class,
            GlobalExceptionHandler.class})
    @ImportAutoConfiguration({
            FleetResourceServerAutoConfiguration.class,
            org.springframework.boot.security.autoconfigure.web.servlet.SecurityFilterAutoConfiguration.class,
            org.springframework.boot.tomcat.autoconfigure.servlet.TomcatServletWebServerAutoConfiguration.class,
            org.springframework.boot.jackson.autoconfigure.JacksonAutoConfiguration.class,
            org.springframework.boot.http.converter.autoconfigure.HttpMessageConvertersAutoConfiguration.class,
            org.springframework.boot.webmvc.autoconfigure.DispatcherServletAutoConfiguration.class,
            org.springframework.boot.webmvc.autoconfigure.WebMvcAutoConfiguration.class})
    static class Config {
        /** 签发时刻固定，测试结果不依赖真实时间；令牌有效期不由本解码器校验。 */
        private static final Instant ISSUED = Instant.parse("2026-01-01T00:00:00Z");

        /** 测试解码器：只认前缀加用户编号的令牌，给出带 {@code user_id} 的已校验令牌；其他令牌按无效令牌拒绝。 */
        @Bean JwtDecoder jwtDecoder() {
            return token -> {
                if (!token.startsWith(TOKEN_PREFIX)) throw new BadJwtException("Unknown test token");
                String userId = token.substring(TOKEN_PREFIX.length());
                return Jwt.withTokenValue(token).header("alg", "RS256").subject("kc-" + userId).claim("user_id", userId)
                        .claim("azp", "github-gateway").issuedAt(ISSUED).expiresAt(ISSUED.plusSeconds(300)).build();
            };
        }
        /** Keycloak 账号同步替身：记录调用，越界请求一次也不该到这里。 */
        @Bean KeycloakAccountClient keycloakAccountClient() { return mock(KeycloakAccountClient.class); }
        @Bean UserAccountEventProducer userAccountEventProducer() { return mock(UserAccountEventProducer.class); }
        /** 记录控制器阶段的业务 SQL 次数（用户、租户关联、角色、部门、资源、租户六张表）。 */
        @Bean SmartInitializingSingleton businessSqlRecorder(MybatisPlusInterceptor interceptor) {
            return () -> interceptor.addInnerInterceptor(new InnerInterceptor() {
                @Override public void beforeQuery(Executor executor, MappedStatement statement, Object parameter, RowBounds rowBounds, ResultHandler resultHandler, BoundSql sql) {
                    if (HANDLING.get() && isBusinessStatement(statement)) BUSINESS_SQL.incrementAndGet();
                }
                @Override public void beforeUpdate(Executor executor, MappedStatement statement, Object parameter) {
                    if (HANDLING.get() && isBusinessStatement(statement)) BUSINESS_SQL.incrementAndGet();
                }
                private boolean isBusinessStatement(MappedStatement statement) {
                    String id = statement.getId();
                    return id.contains("AuthUserMapper.") || id.contains("AuthUserTenantMapper.") || id.contains("AuthRoleMapper.")
                            || id.contains("AuthDeptMapper.") || id.contains("AuthResourceMapper.") || id.contains("AuthTenantMapper.");
                }
            });
        }
        @Bean WebMvcConfigurer requestTenant(TenantProperties tenant) {
            return new WebMvcConfigurer() { @Override public void addInterceptors(InterceptorRegistry registry) {
                registry.addInterceptor(new ReqInterceptor(tenant, new TrafficGovernanceProperties()));
                registry.addInterceptor(new HandlerInterceptor() {
                    @Override public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
                        HANDLING.set(true);
                        return true;
                    }
                    @Override public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
                        HANDLING.remove();
                    }
                });
            }};
        }
    }
}
