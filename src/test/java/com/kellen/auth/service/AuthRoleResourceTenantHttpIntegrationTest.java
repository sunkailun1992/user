package com.kellen.auth.service;

import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.InnerInterceptor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kellen.auth.controller.AuthGrantController;
import com.kellen.auth.mapper.AuthRoleResourceMapper;
import com.kellen.auth.rpc.SecurityContextRpcServiceImpl;
import com.kellen.auth.service.impl.AuthGrantServiceImpl;
import com.kellen.auth.service.results.AuthResourceServiceResults;
import com.kellen.auth.support.AuthH2TestConfiguration;
import com.kellen.bean.GlobalExceptionHandler;
import com.kellen.config.web.ReqInterceptor;
import com.kellen.datapermission.DataPermissionContextHolder;
import com.kellen.security.SecurityUser;
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
import org.springframework.security.access.AccessDeniedException;
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
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;

/**
 * 真实 HTTP 经资源服务器链访问授权管理接口：访问令牌由测试解码器给出（令牌原文带用户编号），业务上下文由 user 自己的
 * 提供方按 H2 里的用户、租户、角色与权限资源计算，经 utils 的令牌转换、业务上下文缓存与授权判断，与线上同一路径。
 *
 * <p>覆盖租户边界（伪造的 {@code X-Tenant-Id}、{@code X-Admin-Type}、{@code X-Permissions} 头都不能越权）、缺权限、停用、
 * 业务上下文取不到时失败关闭（503），以及授权查询前后租户与数据权限上下文原样恢复。访问令牌的撤销由 Keycloak 负责
 * （短时效访问令牌加会话注销），资源服务器不再查撤销名单，原撤销用例不再适用。</p>
 */
@SpringBootTest(classes = AuthRoleResourceTenantHttpIntegrationTest.Config.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        properties = {"spring.config.name=role-resource-tenant-isolated", "spring.cloud.nacos.config.enabled=false",
                "spring.cloud.nacos.discovery.enabled=false", "spring.cloud.nacos.config.import-check.enabled=false",
                "spring.security.oauth2.resourceserver.jwt.issuer-uri=https://keycloak.invalid/realms/github-dev",
                "tenant.enabled=true", "tenant.ignore-without-tenant=false", "test.role-resource.tenant.fixture=true"})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
class AuthRoleResourceTenantHttpIntegrationTest {
    private static final String A = "fixture-tenant-a", B = "fixture-tenant-b";
    /** 测试令牌前缀；令牌原文为前缀加用户编号。 */
    private static final String TOKEN_PREFIX = "synthetic-access-token-for-";
    private static final List<String> CALLERS = List.of("tenant-admin", "platform-admin", "platform-viewer");
    private static final AtomicReference<QueryContext> QUERY = new AtomicReference<>();
    /** 授权接口处理中（控制器阶段）发出的授权关系 SQL 次数；加载调用方业务上下文时读角色与资源不算。 */
    private static final AtomicInteger GRANT_SQL = new AtomicInteger();
    private static final ThreadLocal<Boolean> HANDLING = ThreadLocal.withInitial(() -> false);
    @LocalServerPort int port;
    @Autowired DataSource dataSource;
    @Autowired AuthGrantService service;
    @Autowired AuthTenantManagementAccess access;
    @Autowired AuthRoleResourceMapper mapper;
    @Autowired SecurityUserCache cache;
    private JdbcTemplate jdbc;
    private final ObjectMapper json = new ObjectMapper();

    @BeforeEach void fixture() {
        TenantContextHolder.clear(); DataPermissionContextHolder.clear(); UserContextHolder.clear(); QUERY.set(null); GRANT_SQL.set(0);
        AuthH2TestConfiguration.recreateTables(dataSource);
        jdbc = new JdbcTemplate(dataSource);
        jdbc.update("INSERT INTO auth_tenant(id,name,tenant_id,state) VALUES(?,?,?,1),(?,?,?,1)", A, "fixture-a", A, B, "fixture-b", B);
        user("tenant-admin", A, "TENANT_ADMIN"); user("platform-admin", B, "PLATFORM_SUPER_ADMIN"); user("platform-viewer", B, "PLATFORM_SUPER_ADMIN");
        for (String tenant : List.of(A, B)) {
            jdbc.update("INSERT INTO auth_role(id,name,code,data_scope,tenant_id,state) VALUES(?,?,?,?,?,1)", tenant + "-manager", "manager", "manager", "SELF", tenant);
            jdbc.update("INSERT INTO auth_resource(id,name,code,resource_category,tenant_id,state) VALUES(?,?,?,?,?,1)",
                    tenant + "-manage", "manage", "user:auth:manage", "BACKEND", tenant);
            relation(tenant + "-manager-manage", tenant, tenant + "-manager", tenant + "-manage", false);
        }
        jdbc.update("INSERT INTO auth_user_role(id,tenant_id,user_id,role_id) VALUES(?,?,?,?),(?,?,?,?)",
                "tenant-admin-manager", A, "tenant-admin", A + "-manager", "platform-admin-manager", B, "platform-admin", B + "-manager");
        relation("a-live", A, "shared-role", "a-resource", false);
        relation("a-deleted", A, "shared-role", "a-deleted-resource", true);
        relation("a-other-role", A, "other-role", "other-resource", false);
        relation("b-live", B, "shared-role", "b-resource", false);
        for (String tenant : List.of(A, B)) {
            jdbc.update("INSERT INTO auth_user_role(id,tenant_id,user_id,role_id) VALUES(?,?,?,?)", tenant + "-user-role", tenant, "subject", "shared-role");
            jdbc.update("INSERT INTO auth_role_data_scope(id,tenant_id,role_id,dept_id) VALUES(?,?,?,?)", tenant + "-scope", tenant, "shared-role", "department");
        }
        CALLERS.forEach(cache::bumpVersion);
    }
    @AfterEach void clear() { TenantContextHolder.clear(); DataPermissionContextHolder.clear(); UserContextHolder.clear(); }

    @Test void platformAdminCanReadTheExplicitTargetWithoutCallerTenantIntersection() throws Exception {
        var response = get("platform-admin", A, false);
        assertThat(response.statusCode()).isEqualTo(200);
        assertThat(json.readTree(response.body()).path("data").toString()).isEqualTo("[\"a-resource\"]");
        assertThat(QUERY.get()).isEqualTo(new QueryContext(A, false));
    }
    @Test void tenantAdminCanReadOwnTenantButCannotReadAnotherEvenWithForgedHeaders() throws Exception {
        assertThat(json.readTree(get("tenant-admin", A, false).body()).path("data").toString()).isEqualTo("[\"a-resource\"]");
        QUERY.set(null);
        assertThat(get("tenant-admin", B, false).statusCode()).isEqualTo(403);
        assertThat(get("tenant-admin", B, true).statusCode()).isEqualTo(403);
        assertThat(QUERY.get()).isNull();
    }
    @Test void anonymousOrMissingManagePermissionCannotQuery() throws Exception {
        assertThat(get(null, A, false).statusCode()).isEqualTo(401);
        assertThat(get("platform-viewer", A, false).statusCode()).isEqualTo(403);
        assertThat(QUERY.get()).isNull();
    }
    @Test void unknownDisabledOrHomeTenantDisabledIdentityCannotReachResourceQuery() throws Exception {
        assertThat(get("nobody", A, false).statusCode()).isEqualTo(401);
        jdbc.update("UPDATE auth_user SET state=2 WHERE id='platform-admin'"); cache.bumpVersion("platform-admin");
        var disabled = get("platform-admin", A, false);
        assertThat(disabled.statusCode()).isEqualTo(403);
        assertThat(disabled.body()).contains("账号已停用");
        jdbc.update("UPDATE auth_user SET state=1 WHERE id='platform-admin'");
        jdbc.update("UPDATE auth_tenant SET state=2 WHERE id=?", B); cache.bumpVersion("platform-admin");
        var tenantDenied = get("platform-admin", A, false);
        assertThat(tenantDenied.statusCode()).isEqualTo(403);
        assertThat(tenantDenied.body()).contains("无权访问所请求的租户");
        assertThat(QUERY.get()).isNull();
    }
    @Test void businessContextFailureIsServiceUnavailableAndNeverQueries() throws Exception {
        jdbc.execute("ALTER TABLE auth_user RENAME TO hidden_identity_user");
        try {
            var response = get("platform-admin", A, false);
            assertThat(response.statusCode()).isEqualTo(503);
            assertThat(response.body()).contains("C0110");
            assertThat(QUERY.get()).isNull();
        } finally { jdbc.execute("ALTER TABLE hidden_identity_user RENAME TO auth_user"); }
    }
    @Test void emptyUnknownRoleAndWrongTenantNeverReturnOtherRelations() throws Exception {
        assertThat(json.readTree(get("platform-admin", B, false).body()).path("data").toString()).isEqualTo("[\"b-resource\"]");
        var response = send(request("platform-admin", A, false, "missing-role"));
        assertThat(response.statusCode()).isEqualTo(200);
        assertThat(json.readTree(response.body()).path("data").toString()).isEqualTo("[]");
    }
    @Test void restoresTenantAndIgnoreAfterSuccessfulAndEmptyQueries() {
        for (String prior : new String[]{null, B}) for (boolean ignored : new boolean[]{false, true}) {
            context(prior, ignored);
            assertThat(read(A, "shared-role")).containsExactly("a-resource");
            assertThat(QUERY.get()).isEqualTo(new QueryContext(A, false));
            assertContext(prior, ignored);
            assertThat(read(A, "missing-role")).isEmpty(); assertContext(prior, ignored);
        }
    }
    @Test void restoresTenantAndIgnoreAfterSqlFailure() {
        jdbc.execute("ALTER TABLE auth_role_resource RENAME TO hidden_role_resource");
        try {
            for (String prior : new String[]{null, B}) for (boolean ignored : new boolean[]{false, true}) {
                context(prior, ignored);
                assertThatThrownBy(() -> read(A, "shared-role")).isInstanceOf(RuntimeException.class);
                assertContext(prior, ignored);
            }
        } finally { jdbc.execute("ALTER TABLE hidden_role_resource RENAME TO auth_role_resource"); }
    }
    @Test void deniedCrossTenantCheckLeavesEveryExistingContextUntouched() {
        context(B, true);
        assertThatThrownBy(() -> access.requireTenant(B)).isInstanceOf(AccessDeniedException.class).hasMessage("角色资源访问被拒绝");
        UserContextHolder.set(new SecurityUser("tenant-admin", "tenant-admin", A, List.of("user:auth:manage")));
        assertThatThrownBy(() -> access.requireTenant(B)).isInstanceOf(AccessDeniedException.class).hasMessage("角色资源访问被拒绝");
        assertContext(B, true);
    }
    @Test void ordinaryMapperStillRequiresTenantAndRespectsIt() {
        assertThatThrownBy(() -> mapper.selectById("a-live")).hasStackTraceContaining("tenant context required");
        TenantContextHolder.setTenantId(B); assertThat(mapper.selectById("a-live")).isNull();
        TenantContextHolder.setTenantId(A); assertThat(mapper.selectById("a-live")).isNotNull();
        assertThat(jdbc.queryForObject("SELECT COUNT(*) FROM auth_role_resource", Integer.class)).isEqualTo(6);
    }
    @Test void unauthorizedEmptyResourceSyncCannotChangeAnyStoredRowOrVersion() throws Exception {
        var before = grantRows();
        var response = grantRequest("PUT", "/roles/shared-role/resources", "tenant-admin", B, true, List.of());
        assertThat(grantRows()).isEqualTo(before);
        assertThat(response.statusCode()).isEqualTo(403);
        assertThat(GRANT_SQL.get()).isZero();
    }
    @Test void allEightGrantEndpointsRejectCrossTenantBeforeAnyGrantSql() throws Exception {
        var before = grantRows();
        for (boolean forged : new boolean[]{false, true}) for (var endpoint : endpoints()) {
            GRANT_SQL.set(0);
            var response = grantRequest(endpoint[0], endpoint[1], "tenant-admin", B, forged, List.of());
            assertThat(response.statusCode()).as(forged + " " + endpoint[0] + " " + endpoint[1]).isEqualTo(403);
            assertThat(GRANT_SQL.get()).isZero();
            assertThat(grantRows()).isEqualTo(before);
        }
    }
    @Test void allEightGrantEndpointsRetainOwnTenantAndPlatformCrossTenantAccess() throws Exception {
        for (String caller : List.of("tenant-admin", "platform-admin")) {
            for (var endpoint : endpoints()) {
                var response = grantRequest(endpoint[0], endpoint[1], caller, A, false, List.of("a-resource"));
                assertThat(response.statusCode()).as(caller + " " + endpoint[0] + " " + endpoint[1]).isEqualTo(200);
                assertThat(json.readTree(response.body()).path("success").asBoolean()).as(caller + " " + endpoint[0] + " " + endpoint[1]).isTrue();
            }
        }
        assertThat(jdbc.queryForObject("SELECT COUNT(*) FROM auth_role_resource WHERE id='b-live'", Integer.class)).isEqualTo(1);
    }
    @Test void allEightGrantEndpointsRejectDisabledUsersWithoutGrantSql() throws Exception {
        jdbc.update("UPDATE auth_user SET state=2 WHERE id='platform-admin'"); cache.bumpVersion("platform-admin");
        var before = grantRows();
        for (var endpoint : endpoints()) {
            GRANT_SQL.set(0);
            var response = grantRequest(endpoint[0], endpoint[1], "platform-admin", A, false, List.of());
            assertThat(response.statusCode()).as(endpoint[0] + " " + endpoint[1]).isEqualTo(403);
            assertThat(GRANT_SQL.get()).isZero(); assertThat(grantRows()).isEqualTo(before);
        }
    }
    @Test void allEightGrantEndpointsFailClosedWhenBusinessContextLookupFails() throws Exception {
        var before = grantRows();
        jdbc.execute("ALTER TABLE auth_user RENAME TO hidden_identity_user");
        try {
            for (var endpoint : endpoints()) {
                GRANT_SQL.set(0);
                var response = grantRequest(endpoint[0], endpoint[1], "platform-admin", A, false, List.of());
                assertThat(response.statusCode()).as(endpoint[0] + " " + endpoint[1]).isEqualTo(503);
                assertThat(GRANT_SQL.get()).isZero(); assertThat(grantRows()).isEqualTo(before);
            }
        } finally { jdbc.execute("ALTER TABLE hidden_identity_user RENAME TO auth_user"); }
    }
    @Test void allEightGrantEndpointsRequireAuthenticationAndManagePermission() throws Exception {
        var before = grantRows();
        for (var endpoint : endpoints()) {
            GRANT_SQL.set(0);
            assertThat(grantRequest(endpoint[0], endpoint[1], null, A, true, List.of()).statusCode()).as(endpoint[0] + " " + endpoint[1]).isEqualTo(401);
            assertThat(grantRequest(endpoint[0], endpoint[1], "platform-viewer", A, true, List.of()).statusCode()).as(endpoint[0] + " " + endpoint[1]).isEqualTo(403);
            assertThat(GRANT_SQL.get()).isZero(); assertThat(grantRows()).isEqualTo(before);
        }
    }
    private List<String[]> endpoints() {
        return List.of(new String[]{"POST", "/users/subject/roles"}, new String[]{"GET", "/users/subject/roles"},
                new String[]{"PUT", "/users/subject/roles"}, new String[]{"POST", "/roles/shared-role/resources"},
                new String[]{"GET", "/roles/shared-role/resources"}, new String[]{"PUT", "/roles/shared-role/resources"},
                new String[]{"GET", "/roles/shared-role/data-scope-depts"}, new String[]{"PUT", "/roles/shared-role/data-scope-depts"});
    }
    private List<List<Map<String,Object>>> grantRows() {
        return List.of("auth_role_resource", "auth_user_role", "auth_role_data_scope").stream()
                .map(table -> jdbc.queryForList("SELECT * FROM " + table + " ORDER BY id")).toList();
    }
    private HttpResponse<String> grantRequest(String method, String path, String caller, String target,
            boolean forged, List<String> resources) throws Exception {
        var builder = HttpRequest.newBuilder(URI.create("http://127.0.0.1:" + port + "/auth/manage" + path + (method.equals("GET") ? "?tenantId=" + target : "")))
                .timeout(Duration.ofSeconds(5));
        credentials(builder, caller, target, forged);
        if (method.equals("GET")) builder.GET();
        else builder.header("Content-Type", "application/json").method(method, HttpRequest.BodyPublishers.ofString(json.writeValueAsString(Map.of(
                "tenantId", target, "roleId", "shared-role", "resourceId", "a-resource", "resourceIds", resources,
                "roleIds", List.of("shared-role"), "deptIds", List.of()))));
        return send(builder.build());
    }
    private List<String> read(String target, String role) { return service.listRoleResourceIds(target, role); }
    private void context(String prior, boolean ignored) {
        TenantContextHolder.setTenantId(prior); if (ignored) TenantContextHolder.ignore(); else TenantContextHolder.clearIgnore();
        if (ignored) DataPermissionContextHolder.ignore(); else DataPermissionContextHolder.clear();
    }
    private void assertContext(String prior, boolean ignored) {
        assertThat(TenantContextHolder.getTenantId()).isEqualTo(prior);
        assertThat(TenantContextHolder.isIgnore()).isEqualTo(ignored);
        assertThat(DataPermissionContextHolder.isIgnore()).isEqualTo(ignored);
    }
    private void user(String id, String tenant, String admin) {
        jdbc.update("INSERT INTO auth_user(id,username,tenant_id,admin_type,state) VALUES(?,?,?,?,1)", id, id, tenant, admin);
    }
    private void relation(String id, String tenant, String role, String resource, boolean deleted) {
        jdbc.update("INSERT INTO auth_role_resource(id,tenant_id,role_id,resource_id,is_deleted) VALUES(?,?,?,?,?)", id, tenant, role, resource, deleted);
    }
    /** 带调用方的访问令牌；伪造头模拟前端或中间人塞进来的租户、管理员分类与权限，后端一律不认。 */
    private static void credentials(HttpRequest.Builder builder, String caller, String target, boolean forged) {
        if (caller != null) builder.header("Authorization", "Bearer " + TOKEN_PREFIX + caller);
        if (forged) builder.header("X-Admin-Type", "PLATFORM_SUPER_ADMIN").header("X-Tenant-Id", target).header("X-Permissions", "user:auth:manage");
    }
    private HttpResponse<String> get(String caller, String target, boolean forged) throws Exception {
        return send(request(caller, target, forged, "shared-role"));
    }
    private HttpRequest request(String caller, String target, boolean forged, String role) {
        var request = HttpRequest.newBuilder(URI.create("http://127.0.0.1:" + port + "/auth/manage/roles/" + role + "/resources?tenantId=" + target)).timeout(Duration.ofSeconds(5));
        credentials(request, caller, target, forged);
        return request.GET().build();
    }
    private static HttpResponse<String> send(HttpRequest request) throws Exception {
        try (HttpClient client = HttpClient.newBuilder().proxy(HttpClient.Builder.NO_PROXY).build()) {
            return client.send(request, HttpResponse.BodyHandlers.ofString());
        }
    }
    private record QueryContext(String tenant, boolean ignored) { }

    @Configuration(proxyBeanMethods = false)
    @ConditionalOnProperty(name = "test.role-resource.tenant.fixture", havingValue = "true")
    @Import({AuthH2TestConfiguration.class, AuthGrantController.class, AuthGrantServiceImpl.class, AuthTenantManagementAccess.class,
            SecurityContextRpcServiceImpl.class, AuthTenantAccess.class, AuthDataScopeResolver.class, AuthSecurityContextInvalidator.class,
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
        @Bean AuthResourceServiceResults results() { return mock(AuthResourceServiceResults.class); }
        /** 记录控制器阶段的授权关系 SQL 次数与资源查询时的租户上下文。 */
        @Bean SmartInitializingSingleton grantSqlRecorder(MybatisPlusInterceptor interceptor) {
            return () -> interceptor.addInnerInterceptor(new InnerInterceptor() {
                @Override public void beforeQuery(Executor executor, MappedStatement statement, Object parameter, RowBounds rowBounds, ResultHandler resultHandler, BoundSql sql) {
                    if (HANDLING.get() && isGrantStatement(statement)) GRANT_SQL.incrementAndGet();
                    if (statement.getId().endsWith(".selectResourceIdsByRoleId")) QUERY.set(new QueryContext(TenantContextHolder.getTenantId(), TenantContextHolder.isIgnore()));
                }
                @Override public void beforeUpdate(Executor executor, MappedStatement statement, Object parameter) {
                    if (HANDLING.get() && isGrantStatement(statement)) GRANT_SQL.incrementAndGet();
                }
                private boolean isGrantStatement(MappedStatement statement) {
                    return statement.getId().contains("AuthRoleResourceMapper.") || statement.getId().contains("AuthUserRoleMapper.") || statement.getId().contains("AuthRoleDataScopeMapper.");
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
