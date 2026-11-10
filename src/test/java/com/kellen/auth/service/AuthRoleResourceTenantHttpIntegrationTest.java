package com.kellen.auth.service;

import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.InnerInterceptor;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kellen.auth.controller.AuthGrantController;
import com.kellen.auth.mapper.*;
import com.kellen.auth.rpc.AuthSessionRpcServiceImpl;
import com.kellen.auth.service.impl.AuthGrantServiceImpl;
import com.kellen.auth.service.results.AuthResourceServiceResults;
import com.kellen.bean.GlobalExceptionHandler;
import com.kellen.config.mybatis.MyBatisPlusConfig;
import com.kellen.config.web.ReqInterceptor;
import com.kellen.datapermission.DataPermissionContextHolder;
import com.kellen.security.AuthTokenRedisKeys;
import com.kellen.security.config.SecurityAuthConfig;
import com.kellen.security.config.SecurityAuthProperties;
import com.kellen.security.config.TenantProperties;
import com.kellen.traffic.TrafficGovernanceProperties;
import com.kellen.utils.auth.JwtKeyConfig;
import com.kellen.utils.auth.JwtUtils;
import com.kellen.utils.context.TenantContextHolder;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.sql.DataSource;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.time.Duration;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/** 真实 HTTP、User 生命周期/身份流程、Mapper 与公共租户插件；Redis 仅用合成内存边界。 */
@SpringBootTest(classes = AuthRoleResourceTenantHttpIntegrationTest.Config.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        properties = {"spring.config.name=role-resource-tenant-isolated", "spring.cloud.nacos.config.enabled=false",
                "spring.cloud.nacos.discovery.enabled=false", "spring.cloud.nacos.config.import-check.enabled=false",
                "security.auth.enabled=true", "security.auth.header-enabled=false", "tenant.enabled=true",
                "tenant.ignore-without-tenant=false", "test.role-resource.tenant.fixture=true"})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
class AuthRoleResourceTenantHttpIntegrationTest {
    private static final String A = "fixture-tenant-a", B = "fixture-tenant-b";
    private static final AtomicReference<QueryContext> QUERY = new AtomicReference<>();
    @LocalServerPort int port;
    @Autowired DataSource dataSource;
    @Autowired AuthGrantService service;
    @Autowired AuthRoleResourceMapper mapper;
    @Autowired RedisBoundary redis;
    private JdbcTemplate jdbc;
    private final ObjectMapper json = new ObjectMapper();

    @DynamicPropertySource static void syntheticKey(DynamicPropertyRegistry registry) {
        byte[] key = new byte[32]; new SecureRandom().nextBytes(key);
        String value = Base64.getEncoder().encodeToString(key);
        registry.add("security.jwt.secret", () -> value);
    }
    @BeforeEach void fixture() throws Exception {
        TenantContextHolder.clear(); DataPermissionContextHolder.clear(); QUERY.set(null);
        redis.revoked = false; redis.unavailable = false;
        jdbc = new JdbcTemplate(dataSource);
        String schema = new ClassPathResource("db/postgresql/user-schema.sql").getContentAsString(StandardCharsets.UTF_8);
        for (String table : List.of("auth_role_resource", "auth_tenant", "auth_user", "auth_user_tenant")) {
            jdbc.execute("DROP TABLE IF EXISTS " + table);
            int start = schema.indexOf("CREATE TABLE IF NOT EXISTS " + table + " (");
            jdbc.execute(schema.substring(start, schema.indexOf(";", start) + 1).replace(" COLLATE public.fleet_general_ci", ""));
        }
        jdbc.update("INSERT INTO auth_tenant(id,name,tenant_id) VALUES(?,?,?),(?,?,?)", A, "fixture-a", A, B, "fixture-b", B);
        user("tenant-admin", A, "TENANT_ADMIN"); user("platform-admin", B, "PLATFORM_SUPER_ADMIN");
        relation("a-live", A, "shared-role", "a-resource", false);
        relation("a-deleted", A, "shared-role", "a-deleted-resource", true);
        relation("a-other-role", A, "other-role", "other-resource", false);
        relation("b-live", B, "shared-role", "b-resource", false);
    }
    @AfterEach void clear() { TenantContextHolder.clear(); DataPermissionContextHolder.clear(); }

    @Test void platformAdminCanReadTheExplicitTargetWithoutCallerTenantIntersection() throws Exception {
        var response = get(token("platform-admin", B, "PLATFORM_SUPER_ADMIN", true), A, false);
        assertThat(response.statusCode()).isEqualTo(200);
        assertThat(json.readTree(response.body()).path("data").toString()).isEqualTo("[\"a-resource\"]");
        assertThat(QUERY.get()).isEqualTo(new QueryContext(A, false));
    }
    @Test void tenantAdminCanReadOwnTenantButCannotReadAnotherEvenWithForgedHeaders() throws Exception {
        String token = token("tenant-admin", A, "TENANT_ADMIN", true);
        assertThat(json.readTree(get(token, A, false).body()).path("data").toString()).isEqualTo("[\"a-resource\"]");
        QUERY.set(null);
        assertThat(get(token, B, true).statusCode()).isEqualTo(403);
        assertThat(QUERY.get()).isNull();
    }
    @Test void anonymousOrMissingManagePermissionCannotQuery() throws Exception {
        assertThat(get(null, A, false).statusCode()).isIn(401, 403);
        assertThat(get(token("platform-admin", B, "PLATFORM_SUPER_ADMIN", false), A, false).statusCode()).isEqualTo(403);
        assertThat(QUERY.get()).isNull();
    }
    @Test void revokedDisabledOrUnavailableIdentityCannotReachResourceQuery() throws Exception {
        String token = token("platform-admin", B, "PLATFORM_SUPER_ADMIN", true);
        redis.revoked = true; assertThat(get(token, A, false).statusCode()).isEqualTo(403); redis.revoked = false;
        jdbc.update("UPDATE auth_user SET state=2 WHERE id='platform-admin'");
        assertThat(get(token, A, false).statusCode()).isEqualTo(403);
        jdbc.update("UPDATE auth_user SET state=1 WHERE id='platform-admin'");
        jdbc.update("UPDATE auth_tenant SET state=2 WHERE id=?", B);
        assertThat(get(token, A, false).statusCode()).isEqualTo(403);
        assertThat(QUERY.get()).isNull();
    }
    @Test void lifecycleBackendFailureIsRejectedAndRestoresContext() {
        redis.unavailable = true;
        context(B, true);
        assertThatThrownBy(() -> read(A, "shared-role"))
                .isInstanceOf(org.springframework.security.access.AccessDeniedException.class)
                .hasMessage("角色资源访问被拒绝");
        assertContext(B, true); assertThat(QUERY.get()).isNull();
    }
    @Test void emptyUnknownRoleAndWrongTenantNeverReturnOtherRelations() throws Exception {
        String token = token("platform-admin", B, "PLATFORM_SUPER_ADMIN", true);
        assertThat(json.readTree(get(token, B, false).body()).path("data").toString()).isEqualTo("[\"b-resource\"]");
        var request = request(token, A, false, "missing-role");
        var response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
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
    @Test void deniedCrossTenantReadRestoresEveryExistingContext() {
        context(B, true);
        assertThatThrownBy(() -> service.listRoleResourceIds(B, "shared-role", "Bearer " + token("tenant-admin", A, "TENANT_ADMIN", true)))
                .isInstanceOf(org.springframework.security.access.AccessDeniedException.class);
        assertContext(B, true);
    }
    @Test void ordinaryMapperStillRequiresTenantAndRespectsIt() {
        assertThatThrownBy(() -> mapper.selectById("a-live")).hasStackTraceContaining("tenant context required");
        TenantContextHolder.setTenantId(B); assertThat(mapper.selectById("a-live")).isNull();
        TenantContextHolder.setTenantId(A); assertThat(mapper.selectById("a-live")).isNotNull();
        assertThat(jdbc.queryForObject("SELECT COUNT(*) FROM auth_role_resource", Integer.class)).isEqualTo(4);
    }
    private List<String> read(String target, String role) { return service.listRoleResourceIds(target, role, "Bearer " + token("platform-admin", B, "PLATFORM_SUPER_ADMIN", true)); }
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
        jdbc.update("INSERT INTO auth_user(id,username,password,tenant_id,admin_type) VALUES(?,?,?,?,?)", id, id, "synthetic-unused", tenant, admin);
    }
    private void relation(String id, String tenant, String role, String resource, boolean deleted) {
        jdbc.update("INSERT INTO auth_role_resource(id,tenant_id,role_id,resource_id,is_delete) VALUES(?,?,?,?,?)", id, tenant, role, resource, deleted);
    }
    private String token(String user, String tenant, String admin, boolean manage) {
        return JwtUtils.createJwt(UUID.randomUUID().toString(), user, Map.of("userId", user, "tenantId", tenant,
                "adminType", admin, "permissions", manage ? List.of("user:auth:manage") : List.of(),
                AuthTokenRedisKeys.CLAIM_TOKEN_TYPE, AuthTokenRedisKeys.ACCESS_TOKEN_TYPE,
                AuthTokenRedisKeys.CLAIM_TOKEN_VERSION, "0"));
    }
    private HttpResponse<String> get(String token, String target, boolean forged) throws Exception {
        return HttpClient.newHttpClient().send(request(token, target, forged, "shared-role"), HttpResponse.BodyHandlers.ofString());
    }
    private HttpRequest request(String token, String target, boolean forged, String role) {
        var request = HttpRequest.newBuilder(URI.create("http://127.0.0.1:" + port + "/auth/manage/roles/" + role + "/resources?tenantId=" + target)).timeout(Duration.ofSeconds(5));
        if (token != null) request.header("Authorization", "Bearer " + token);
        if (forged) request.header("X-Admin-Type", "PLATFORM_SUPER_ADMIN").header("X-Tenant-Id", target).header("X-Permissions", "user:auth:manage");
        return request.GET().build();
    }
    private record QueryContext(String tenant, boolean ignored) { }
    static class RedisBoundary { volatile boolean revoked, unavailable; }

    @Configuration(proxyBeanMethods = false)
    @ConditionalOnProperty(name = "test.role-resource.tenant.fixture", havingValue = "true")
    @Import({AuthGrantController.class, AuthGrantServiceImpl.class, AuthSessionRpcServiceImpl.class,
            AuthTokenLifecycleService.class, SecurityAuthConfig.class, MyBatisPlusConfig.class, GlobalExceptionHandler.class})
    @ImportAutoConfiguration({
            org.springframework.boot.security.autoconfigure.web.servlet.SecurityFilterAutoConfiguration.class,
            org.springframework.boot.tomcat.autoconfigure.servlet.TomcatServletWebServerAutoConfiguration.class,
            org.springframework.boot.autoconfigure.context.PropertyPlaceholderAutoConfiguration.class,
            org.springframework.boot.autoconfigure.context.ConfigurationPropertiesAutoConfiguration.class,
            org.springframework.boot.jackson.autoconfigure.JacksonAutoConfiguration.class,
            org.springframework.boot.http.converter.autoconfigure.HttpMessageConvertersAutoConfiguration.class,
            org.springframework.boot.webmvc.autoconfigure.DispatcherServletAutoConfiguration.class,
            org.springframework.boot.webmvc.autoconfigure.WebMvcAutoConfiguration.class})
    static class Config {
        @Bean DataSource dataSource() { return new DriverManagerDataSource("jdbc:h2:mem:grant_" + UUID.randomUUID() + ";MODE=PostgreSQL;DB_CLOSE_DELAY=-1", "sa", ""); }
        @Bean SqlSessionFactory sqlSessionFactory(DataSource dataSource, MybatisPlusInterceptor interceptor) throws Exception {
            var config = new MybatisConfiguration(); config.setMapUnderscoreToCamelCase(true);
            for (Class<?> type : List.of(AuthRoleResourceMapper.class, AuthUserMapper.class, AuthTenantMapper.class, AuthUserTenantMapper.class)) config.addMapper(type);
            interceptor.addInnerInterceptor(new InnerInterceptor() {
                @Override public void beforeQuery(Executor executor, MappedStatement statement, Object parameter, RowBounds rowBounds, ResultHandler resultHandler, BoundSql sql) {
                    if (statement.getId().endsWith(".selectResourceIdsByRoleId")) QUERY.set(new QueryContext(TenantContextHolder.getTenantId(), TenantContextHolder.isIgnore()));
                }
            });
            var factory = new MybatisSqlSessionFactoryBean(); factory.setDataSource(dataSource); factory.setConfiguration(config); factory.setPlugins(interceptor); return factory.getObject();
        }
        @Bean AuthRoleResourceMapper resources(SqlSessionFactory factory) { return new SqlSessionTemplate(factory).getMapper(AuthRoleResourceMapper.class); }
        @Bean AuthUserMapper users(SqlSessionFactory factory) { return new SqlSessionTemplate(factory).getMapper(AuthUserMapper.class); }
        @Bean AuthTenantMapper tenants(SqlSessionFactory factory) { return new SqlSessionTemplate(factory).getMapper(AuthTenantMapper.class); }
        @Bean AuthUserTenantMapper memberships(SqlSessionFactory factory) { return new SqlSessionTemplate(factory).getMapper(AuthUserTenantMapper.class); }
        @Bean AuthUserRoleMapper userRoles() { return mock(AuthUserRoleMapper.class); }
        @Bean AuthRoleDataScopeMapper scopes() { return mock(AuthRoleDataScopeMapper.class); }
        @Bean AuthResourceMapper unusedResources() { return mock(AuthResourceMapper.class); }
        @Bean AuthResourceServiceResults results() { return mock(AuthResourceServiceResults.class); }
        @Bean JwtKeyConfig jwtKeyConfig(Environment environment) { return new JwtKeyConfig(environment); }
        @Bean RedisBoundary boundary() { return new RedisBoundary(); }
        @Bean StringRedisTemplate redis(RedisBoundary boundary) {
            var redis = mock(StringRedisTemplate.class);
            @SuppressWarnings("unchecked") ValueOperations<String,String> values = mock(ValueOperations.class);
            when(redis.opsForValue()).thenReturn(values);
            when(redis.hasKey(anyString())).thenAnswer(invocation -> {
                if (boundary.unavailable) throw new IllegalStateException("synthetic-private-backend");
                return boundary.revoked;
            });
            return redis;
        }
        @Bean WebMvcConfigurer requestTenant(TenantProperties tenant, SecurityAuthProperties auth) {
            return new WebMvcConfigurer() { @Override public void addInterceptors(InterceptorRegistry registry) {
                registry.addInterceptor(new ReqInterceptor(tenant, new TrafficGovernanceProperties(), auth));
            }};
        }
    }
}
