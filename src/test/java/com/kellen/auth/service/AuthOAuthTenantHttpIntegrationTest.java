package com.kellen.auth.service;

import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.InnerInterceptor;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kellen.auth.config.OAuthAuthorizationServerProperties;
import com.kellen.auth.controller.OAuthController;
import com.kellen.auth.dto.OAuthAuthorizeUser;
import com.kellen.auth.mapper.AuthOAuthClientMapper;
import com.kellen.auth.service.impl.AuthOAuthTokenServiceImpl;
import com.kellen.config.mybatis.MyBatisPlusConfig;
import com.kellen.config.web.ReqInterceptor;
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
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.sql.DataSource;
import java.net.URI;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

/** 真实 HTTP、安全入口、MyBatis-Plus 租户插件和 H2；只模拟资源所有者登录，不读取外部配置。 */
@SpringBootTest(classes = AuthOAuthTenantHttpIntegrationTest.Config.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        properties = {"spring.config.name=oauth-tenant-isolated", "spring.cloud.nacos.config.enabled=false",
                "spring.cloud.nacos.discovery.enabled=false", "spring.cloud.nacos.config.import-check.enabled=false",
                "security.auth.enabled=true", "security.auth.header-enabled=false",
                "tenant.enabled=true", "tenant.ignore-without-tenant=false",
                "security.jwt.issuer=synthetic-oauth-issuer"})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
class AuthOAuthTenantHttpIntegrationTest {
    private static final ObjectMapper JSON = new ObjectMapper();
    private static final String CLIENT = "fixture-client-a";
    private static final String PUBLIC = "fixture-public-a";
    private static final String TENANT = "fixture-tenant-a";
    private static final String OTHER_TENANT = "fixture-tenant-b";
    private static final String RESOURCE = "https://gateway.example.test/api/mcp";
    private static final String CALLBACK = "https://client.example.test/callback";
    private static final String SECRET = UUID.randomUUID().toString();
    private static final String VERIFIER = UUID.randomUUID() + "-" + UUID.randomUUID();
    private static final AtomicReference<QueryContext> FULL_READ = new AtomicReference<>();
    @LocalServerPort private int port;
    @Autowired private DataSource dataSource;
    @Autowired private AuthOAuthClientMapper mapper;
    @Autowired private AuthOAuthTokenService service;
    @MockitoBean private AuthAuthenticationService authentication;
    private JdbcTemplate jdbc;

    @DynamicPropertySource
    static void syntheticKey(DynamicPropertyRegistry registry) {
        byte[] key = new byte[32]; new SecureRandom().nextBytes(key);
        String encoded = Base64.getEncoder().encodeToString(key);
        registry.add("security.jwt.secret", () -> encoded);
    }

    @BeforeEach
    void fixture() throws Exception {
        TenantContextHolder.clear(); reset(authentication); FULL_READ.set(null);
        jdbc = new JdbcTemplate(dataSource);
        jdbc.execute("DROP TABLE IF EXISTS auth_oauth_client");
        String schema = new ClassPathResource("db/postgresql/user-schema.sql").getContentAsString(StandardCharsets.UTF_8);
        int start = schema.indexOf("CREATE TABLE IF NOT EXISTS auth_oauth_client (");
        jdbc.execute(schema.substring(start, schema.indexOf(";", start) + 1)
                .replace(" COLLATE public.fleet_general_ci", ""));
        jdbc.execute("CREATE UNIQUE INDEX uk_auth_oauth_client_client_id ON auth_oauth_client(client_id)");
        insert("client-a", CLIENT, TENANT, false);
        insert("public-a", PUBLIC, TENANT, true);
    }

    @AfterEach
    void cleanContext() { TenantContextHolder.clear(); }

    @Test
    void anonymousBasicTokenUsesRegisteredTenantAndIgnoresForgedHeader() throws Exception {
        var response = token(CLIENT, SECRET, OTHER_TENANT);
        assertThat(response.statusCode()).isEqualTo(200);
        var body = JSON.readTree(response.body());
        var claims = JwtUtils.parseJwt(body.path("access_token").asText());
        assertThat(claims.get("tenantId", String.class)).isEqualTo(TENANT);
        assertThat(claims.get("client_id", String.class)).isEqualTo(CLIENT);
        assertThat(claims.get("scope", String.class)).isEqualTo("mcp.tools.read");
        assertThat(FULL_READ.get()).isEqualTo(new QueryContext(TENANT, false));
        assertThat(response.body()).doesNotContain(SECRET, "client_secret", "IllegalStateException");
    }

    @Test
    void ordinaryMapperStillRejectsMissingTenantAndFiltersOtherTenants() {
        assertThatThrownBy(() -> mapper.selectById("client-a"))
                .hasStackTraceContaining("tenant context required for tenant-filtered SQL");
        TenantContextHolder.setTenantId(OTHER_TENANT);
        assertThat(mapper.selectById("client-a")).isNull();
        TenantContextHolder.setTenantId(TENANT);
        assertThat(mapper.selectById("client-a").getClientId()).isEqualTo(CLIENT);
    }

    @Test
    void serviceRestoresOldTenantAndIgnoreFlagAfterSuccessfulAndRejectedCredentials() {
        for (String previous : new String[]{null, OTHER_TENANT}) {
            for (boolean ignored : new boolean[]{false, true}) {
                TenantContextHolder.setTenantId(previous);
                if (ignored) TenantContextHolder.ignore(); else TenantContextHolder.clearIgnore();
                service.issueClientCredentialsToken(basic(CLIENT, SECRET), credentials());
                assertThat(FULL_READ.get()).isEqualTo(new QueryContext(TENANT, false));
                assertThat(TenantContextHolder.getTenantId()).isEqualTo(previous);
                assertThat(TenantContextHolder.isIgnore()).isEqualTo(ignored);
                assertThatThrownBy(() -> service.issueClientCredentialsToken(basic(CLIENT, "synthetic-wrong"), credentials()))
                        .hasMessage("Client authentication failed");
                assertThat(TenantContextHolder.getTenantId()).isEqualTo(previous);
                assertThat(TenantContextHolder.isIgnore()).isEqualTo(ignored);
            }
        }
    }

    @Test
    void fullClientSqlFailureStillRestoresPreviousTenantAndIgnore() {
        TenantContextHolder.setTenantId(OTHER_TENANT); TenantContextHolder.ignore();
        // 身份投影仍可执行，完整读取故障必须发生在可信租户绑定之后。
        jdbc.execute("ALTER TABLE auth_oauth_client DROP COLUMN client_secret");
        assertThatThrownBy(() -> service.issueClientCredentialsToken(basic(CLIENT, SECRET), credentials()))
                .isInstanceOf(org.springframework.dao.DataAccessException.class);
        assertThat(FULL_READ.get()).isEqualTo(new QueryContext(TENANT, false));
        assertThat(TenantContextHolder.getTenantId()).isEqualTo(OTHER_TENANT);
        assertThat(TenantContextHolder.isIgnore()).isTrue();
    }

    @Test
    void unknownWrongSecretDisabledDeletedAndMissingTenantFailWithSameOAuthError() throws Exception {
        insert("disabled", "fixture-disabled", TENANT, false);
        insert("deleted", "fixture-deleted", TENANT, false);
        insert("missing-tenant", "fixture-no-tenant", "", false);
        jdbc.update("UPDATE auth_oauth_client SET state=2 WHERE id='disabled'");
        jdbc.update("UPDATE auth_oauth_client SET is_delete=true WHERE id='deleted'");
        for (String id : new String[]{"fixture-unknown", "fixture-disabled", "fixture-deleted", "fixture-no-tenant", "x' OR '1'='1"}) {
            assertInvalidClient(token(id, SECRET, TENANT));
        }
        assertInvalidClient(token(CLIENT, "synthetic-wrong", TENANT));
    }

    @Test
    void globalUniqueConstraintAndCorruptCrossTenantDuplicateBothFailClosed() throws Exception {
        assertThatThrownBy(() -> insert("duplicate", CLIENT, OTHER_TENANT, false))
                .isInstanceOf(org.springframework.dao.DataIntegrityViolationException.class);
        // 模拟旧环境缺失唯一约束；认证路径仍不得用 LIMIT 1 任取一个租户。
        jdbc.execute("DROP INDEX uk_auth_oauth_client_client_id");
        insert("duplicate", CLIENT, OTHER_TENANT, false);
        assertInvalidClient(token(CLIENT, SECRET, TENANT));
    }

    @Test
    void publicPkceFlowBindsLoginAndCodeToClientTenantAndRejectsReplay() throws Exception {
        when(authentication.authenticateForOAuth(any())).thenAnswer(invocation -> {
            com.kellen.auth.dto.LoginRequest login = invocation.getArgument(0);
            assertThat(login.getTenantId()).isEqualTo(TENANT);
            return new OAuthAuthorizeUser("fixture-user", "fixture-user", TENANT);
        });
        var params = authorizeParams();
        var page = send("GET", "/oauth2/authorize", params, null, OTHER_TENANT);
        assertThat(page.statusCode()).isEqualTo(200);
        assertThat(page.body()).contains("value=\"" + TENANT + "\"").doesNotContain("value=\"100\"");
        var authorized = send("POST", "/oauth2/authorize", loginForm(TENANT), null, OTHER_TENANT);
        assertThat(authorized.statusCode()).isEqualTo(302);
        String code = callbackCode(authorized);
        var exchange = exchange(code);
        assertThat(exchange.statusCode()).isEqualTo(200);
        assertThat(JwtUtils.parseJwt(JSON.readTree(exchange.body()).path("access_token").asText())
                .get("tenantId", String.class)).isEqualTo(TENANT);
        assertThat(exchange(code).statusCode()).isEqualTo(400);
    }

    @Test
    void authorizationRejectsRequestedOrAuthenticatedTenantMismatchBeforeIssuingCode() throws Exception {
        var mismatch = send("POST", "/oauth2/authorize", loginForm(OTHER_TENANT), null, null);
        assertThat(mismatch.statusCode()).isEqualTo(403);
        verifyNoInteractions(authentication);
        when(authentication.authenticateForOAuth(any())).thenReturn(new OAuthAuthorizeUser("other", "other", OTHER_TENANT));
        mismatch = send("POST", "/oauth2/authorize", loginForm(TENANT), null, null);
        assertThat(mismatch.statusCode()).isEqualTo(403);
        assertThat(mismatch.headers().firstValue("Location")).isEmpty();
    }

    @Test
    void authorizationCodeCannotMoveWithAClientReassignedToAnotherTenant() throws Exception {
        when(authentication.authenticateForOAuth(any())).thenReturn(new OAuthAuthorizeUser("fixture-user", "fixture-user", TENANT));
        var authorized = send("POST", "/oauth2/authorize", loginForm(TENANT), null, null);
        assertThat(authorized.statusCode()).isEqualTo(302);
        String code = callbackCode(authorized);
        jdbc.update("UPDATE auth_oauth_client SET tenant_id=? WHERE id='public-a'", OTHER_TENANT);
        var rejected = exchange(code);
        assertThat(rejected.statusCode()).isEqualTo(400);
        assertThat(JSON.readTree(rejected.body()).path("error").asText()).isEqualTo("invalid_grant");
    }

    private void insert(String id, String client, String tenant, boolean publicClient) {
        jdbc.update("INSERT INTO auth_oauth_client(id,client_id,client_secret,client_type,token_endpoint_auth_method,grant_types,redirect_uris,scopes,audiences,tenant_id) VALUES (?,?,?,?,?,?,?,?,?,?)",
                id, client, publicClient ? null : new BCryptPasswordEncoder().encode(SECRET), publicClient ? "public" : "confidential",
                publicClient ? "none" : "client_secret_basic", publicClient ? "authorization_code" : "client_credentials", CALLBACK,
                "mcp.tools.read", RESOURCE, tenant);
    }

    private LinkedMultiValueMap<String, String> credentials() {
        var form = new LinkedMultiValueMap<String, String>();
        form.add("grant_type", "client_credentials"); form.add("scope", "mcp.tools.read"); form.add("resource", RESOURCE);
        return form;
    }

    private Map<String, String> authorizeParams() throws Exception {
        return Map.of("response_type", "code", "client_id", PUBLIC, "redirect_uri", CALLBACK,
                "scope", "mcp.tools.read", "resource", RESOURCE, "code_challenge_method", "S256",
                "code_challenge", Base64.getUrlEncoder().withoutPadding().encodeToString(MessageDigest.getInstance("SHA-256").digest(VERIFIER.getBytes(StandardCharsets.US_ASCII))));
    }

    private Map<String, String> loginForm(String tenant) throws Exception {
        var form = new java.util.HashMap<>(authorizeParams());
        form.putAll(Map.of("tenantId", tenant, "username", "fixture-user", "password", "synthetic-login-only")); return form;
    }

    private HttpResponse<String> token(String id, String secret, String tenantHeader) throws Exception {
        return send("POST", "/oauth2/token", Map.of("grant_type", "client_credentials", "scope", "mcp.tools.read", "resource", RESOURCE), basic(id, secret), tenantHeader);
    }

    private HttpResponse<String> exchange(String code) throws Exception {
        return send("POST", "/oauth2/token", Map.of("grant_type", "authorization_code", "client_id", PUBLIC,
                "code", code, "redirect_uri", CALLBACK, "code_verifier", VERIFIER), null, OTHER_TENANT);
    }

    private HttpResponse<String> send(String method, String path, Map<String, String> form, String authorization, String tenantHeader) throws Exception {
        String encoded = form.entrySet().stream().map(e -> URLEncoder.encode(e.getKey(), StandardCharsets.UTF_8) + "="
                + URLEncoder.encode(e.getValue(), StandardCharsets.UTF_8)).collect(Collectors.joining("&"));
        var request = HttpRequest.newBuilder(URI.create("http://127.0.0.1:" + port + path + (method.equals("GET") ? "?" + encoded : "")));
        if (authorization != null) request.header("Authorization", authorization);
        if (tenantHeader != null) request.header("X-Tenant-Id", tenantHeader);
        if (method.equals("GET")) request.GET();
        else request.header("Content-Type", "application/x-www-form-urlencoded").POST(HttpRequest.BodyPublishers.ofString(encoded));
        return HttpClient.newHttpClient().send(request.build(), HttpResponse.BodyHandlers.ofString());
    }

    private String callbackCode(HttpResponse<String> response) {
        String query = URI.create(response.headers().firstValue("Location").orElseThrow()).getRawQuery();
        return URLDecoder.decode(query.substring("code=".length()), StandardCharsets.UTF_8);
    }

    private String basic(String id, String secret) {
        return "Basic " + Base64.getEncoder().encodeToString((id + ":" + secret).getBytes(StandardCharsets.UTF_8));
    }

    private void assertInvalidClient(HttpResponse<String> response) throws Exception {
        assertThat(response.statusCode()).isEqualTo(401);
        assertThat(JSON.readTree(response.body()).path("error").asText()).isEqualTo("invalid_client");
        assertThat(JSON.readTree(response.body()).path("error_description").asText()).isEqualTo("Client authentication failed");
        assertThat(response.body()).doesNotContain(SECRET, "client_secret", "tenant_id", "IllegalStateException");
    }

    private record QueryContext(String tenant, boolean ignored) { }

    @Configuration(proxyBeanMethods = false)
    @Import({OAuthController.class, AuthOAuthTokenServiceImpl.class, SecurityAuthConfig.class, MyBatisPlusConfig.class})
    @ImportAutoConfiguration({
            org.springframework.boot.tomcat.autoconfigure.servlet.TomcatServletWebServerAutoConfiguration.class,
            org.springframework.boot.autoconfigure.context.PropertyPlaceholderAutoConfiguration.class,
            org.springframework.boot.autoconfigure.context.ConfigurationPropertiesAutoConfiguration.class,
            org.springframework.boot.jackson.autoconfigure.JacksonAutoConfiguration.class,
            org.springframework.boot.http.converter.autoconfigure.HttpMessageConvertersAutoConfiguration.class,
            org.springframework.boot.webmvc.autoconfigure.DispatcherServletAutoConfiguration.class,
            org.springframework.boot.webmvc.autoconfigure.WebMvcAutoConfiguration.class
    })
    static class Config {
        @Bean DataSource dataSource() {
            return new DriverManagerDataSource("jdbc:h2:mem:oauth_" + UUID.randomUUID() + ";MODE=PostgreSQL;DB_CLOSE_DELAY=-1", "sa", "");
        }
        @Bean SqlSessionFactory sqlSessionFactory(DataSource dataSource, MybatisPlusInterceptor interceptors) throws Exception {
            var configuration = new MybatisConfiguration(); configuration.setMapUnderscoreToCamelCase(true);
            configuration.addMapper(AuthOAuthClientMapper.class);
            interceptors.addInnerInterceptor(new InnerInterceptor() {
                @Override public void beforeQuery(Executor executor, MappedStatement statement, Object parameter,
                        RowBounds rowBounds, ResultHandler resultHandler, BoundSql sql) {
                    if (statement.getId().endsWith(".selectById")) FULL_READ.set(new QueryContext(TenantContextHolder.getTenantId(), TenantContextHolder.isIgnore()));
                }
            });
            var factory = new MybatisSqlSessionFactoryBean(); factory.setDataSource(dataSource);
            factory.setConfiguration(configuration); factory.setPlugins(interceptors); return factory.getObject();
        }
        @Bean AuthOAuthClientMapper clientMapper(SqlSessionFactory factory) { return new SqlSessionTemplate(factory).getMapper(AuthOAuthClientMapper.class); }
        @Bean OAuthAuthorizationServerProperties oauthProperties() { return new OAuthAuthorizationServerProperties("https://gateway.example.test"); }
        @Bean JwtKeyConfig jwtKeyConfig(Environment environment) { return new JwtKeyConfig(environment); }
        @Bean WebMvcConfigurer tenantRequest(TenantProperties tenant, SecurityAuthProperties security) {
            return new WebMvcConfigurer() {
                @Override public void addInterceptors(InterceptorRegistry registry) {
                    registry.addInterceptor(new ReqInterceptor(tenant, new TrafficGovernanceProperties(), security));
                }
            };
        }
    }
}
