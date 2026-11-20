package com.kellen.auth.controller;

import com.kellen.auth.service.AuthCurrentUserService;
import com.kellen.auth.support.ResourceServerTestConfiguration;
import com.kellen.bean.GlobalExceptionHandler;
import com.kellen.security.config.ActuatorSecurityConfig;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verifyNoInteractions;

/**
 * 以真实 HTTP 验证 Actuator 链与资源服务器链并存。
 *
 * <p>仅模拟业务服务，不连接 Redis、数据库、Nacos 或 Keycloak；请求经过真实 MVC 与两条安全过滤器链：
 * 监控口令只开监控端点，业务接口只认 Bearer 访问令牌（这里的解码器一律拒绝，验证缺令牌与坏令牌都是 401）。</p>
 */
@SpringBootTest(classes = ActuatorAuthChainHttpIntegrationTest.Config.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        properties = {"spring.config.name=actuator-auth-isolated", "spring.cloud.nacos.config.enabled=false",
                "spring.cloud.nacos.discovery.enabled=false", "spring.cloud.nacos.config.import-check.enabled=false",
                "spring.cloud.discovery.enabled=false", "management.endpoints.web.exposure.include=health",
                "spring.security.oauth2.resourceserver.jwt.issuer-uri=https://keycloak.invalid/realms/github-dev",
                "security.actuator.token=synthetic-user-actuator-test-token"})
class ActuatorAuthChainHttpIntegrationTest {

    @LocalServerPort private int port;
    @MockitoBean private AuthCurrentUserService currentUser;

    @Test
    void healthRemainsPublicForDeploymentProbes() throws Exception {
        assertThat(send("/actuator/health", false, null).statusCode()).isEqualTo(200);
    }

    @Test
    void actuatorDiscoveryRequiresItsConfiguredHeader() throws Exception {
        assertThat(send("/actuator", false, null).statusCode()).isEqualTo(401);
        assertThat(send("/actuator", true, null).statusCode()).isEqualTo(200);
    }

    @Test
    void managementTokenDoesNotGrantAccessToBusinessResources() throws Exception {
        var response = send("/auth/current/resources", true, null);
        assertThat(response.statusCode()).isEqualTo(401);
        assertThat(response.body()).contains("\"success\":false", "A0220");
        verifyNoInteractions(currentUser);
    }

    @Test
    void businessRequestWithoutBearerTokenIs401() throws Exception {
        var response = send("/auth/current/tenants", false, null);
        assertThat(response.statusCode()).isEqualTo(401);
        assertThat(response.headers().firstValue("WWW-Authenticate")).hasValueSatisfying(value -> assertThat(value).startsWith("Bearer"));
        verifyNoInteractions(currentUser);
    }

    @Test
    void invalidBearerTokenIs401WithoutEchoingTheToken() throws Exception {
        var response = send("/auth/current/tenants", false, "synthetic-invalid-access-token");
        assertThat(response.statusCode()).isEqualTo(401);
        assertThat(response.body()).contains("A0220").doesNotContain("synthetic-invalid-access-token");
        assertThat(response.headers().firstValue("WWW-Authenticate")).hasValueSatisfying(value -> assertThat(value).contains("invalid_token"));
        verifyNoInteractions(currentUser);
    }

    private HttpResponse<String> send(String path, boolean actuatorAuthorized, String bearer) throws Exception {
        var request = HttpRequest.newBuilder(URI.create("http://127.0.0.1:" + port + path)).GET();
        if (actuatorAuthorized) {
            request.header("actuatorReq", "synthetic-user-actuator-test-token");
        }
        if (bearer != null) {
            request.header("Authorization", "Bearer " + bearer);
        }
        try (HttpClient client = HttpClient.newBuilder().proxy(HttpClient.Builder.NO_PROXY).build()) {
            return client.send(request.build(), HttpResponse.BodyHandlers.ofString());
        }
    }

    @Configuration(proxyBeanMethods = false)
    @Import({AuthController.class, ResourceServerTestConfiguration.class, ActuatorSecurityConfig.class, GlobalExceptionHandler.class})
    @ImportAutoConfiguration({
            org.springframework.boot.tomcat.autoconfigure.servlet.TomcatServletWebServerAutoConfiguration.class,
            org.springframework.boot.autoconfigure.context.PropertyPlaceholderAutoConfiguration.class,
            org.springframework.boot.autoconfigure.context.ConfigurationPropertiesAutoConfiguration.class,
            org.springframework.boot.autoconfigure.availability.ApplicationAvailabilityAutoConfiguration.class,
            org.springframework.boot.jackson.autoconfigure.JacksonAutoConfiguration.class,
            org.springframework.boot.http.converter.autoconfigure.HttpMessageConvertersAutoConfiguration.class,
            org.springframework.boot.webmvc.autoconfigure.DispatcherServletAutoConfiguration.class,
            org.springframework.boot.webmvc.autoconfigure.WebMvcAutoConfiguration.class,
            org.springframework.boot.validation.autoconfigure.ValidationAutoConfiguration.class,
            org.springframework.boot.actuate.autoconfigure.endpoint.EndpointAutoConfiguration.class,
            org.springframework.boot.actuate.autoconfigure.endpoint.jackson.JacksonEndpointAutoConfiguration.class,
            org.springframework.boot.actuate.autoconfigure.endpoint.web.WebEndpointAutoConfiguration.class,
            org.springframework.boot.actuate.autoconfigure.web.server.ManagementContextAutoConfiguration.class,
            org.springframework.boot.servlet.autoconfigure.actuate.web.ServletManagementContextAutoConfiguration.class,
            org.springframework.boot.health.autoconfigure.contributor.HealthContributorAutoConfiguration.class,
            org.springframework.boot.health.autoconfigure.registry.HealthContributorRegistryAutoConfiguration.class,
            org.springframework.boot.health.autoconfigure.actuate.endpoint.HealthEndpointAutoConfiguration.class,
            org.springframework.boot.health.autoconfigure.actuate.endpoint.AvailabilityProbesAutoConfiguration.class,
            org.springframework.boot.webmvc.autoconfigure.actuate.endpoint.web.WebMvcHealthEndpointExtensionAutoConfiguration.class
    })
    static class Config {
    }
}
