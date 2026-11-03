package com.kellen.auth.controller;

import com.kellen.auth.entity.vo.AuthLoginVO;
import com.kellen.auth.entity.vo.AuthTenantVO;
import com.kellen.auth.service.AuthAuthenticationService;
import com.kellen.auth.service.AuthTenantService;
import com.kellen.auth.service.AuthThirdPartyAuthenticationService;
import com.kellen.bean.GlobalExceptionHandler;
import com.kellen.security.config.ActuatorSecurityConfig;
import com.kellen.security.config.SecurityAuthConfig;
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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

/**
 * 以真实 HTTP 验证 Actuator 新链与既有登录安全链并存。
 *
 * <p>仅模拟业务服务，不连接 Redis、数据库或 Nacos；所有请求经过真实 MVC、参数校验及安全过滤器链，
 * 防止公共依赖升级把正常登录入口误当作管理端点。</p>
 */
@SpringBootTest(classes = ActuatorAuthChainHttpIntegrationTest.Config.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        properties = {"spring.config.name=actuator-auth-isolated", "spring.cloud.nacos.config.enabled=false",
                "spring.cloud.nacos.discovery.enabled=false", "spring.cloud.nacos.config.import-check.enabled=false",
                "spring.cloud.discovery.enabled=false", "security.auth.enabled=true",
                "security.auth.header-enabled=false", "management.endpoints.web.exposure.include=health",
                "security.actuator.token=synthetic-user-actuator-test-token"})
class ActuatorAuthChainHttpIntegrationTest {

    @LocalServerPort private int port;
    @MockitoBean private AuthAuthenticationService authentication;
    @MockitoBean private AuthTenantService tenants;
    @MockitoBean private AuthThirdPartyAuthenticationService thirdParty;

    @Test
    void publicTenantLookupStillReturnsTheBusinessEnvelopeWithoutManagementToken() throws Exception {
        var tenant = new AuthTenantVO();
        tenant.setId("fixture-tenant");
        tenant.setName("合成测试租户");
        when(tenants.list(any())).thenReturn(List.of(tenant));

        var response = send("/auth/tenants", null, false);
        assertThat(response.statusCode()).isEqualTo(200);
        assertThat(response.body()).contains("\"success\":true", "fixture-tenant");
        verify(tenants).list(any());
    }

    @Test
    void normalLoginRemainsPublicWithoutActuatorHeader() throws Exception {
        when(authentication.login(any())).thenReturn(syntheticLogin());

        var response = send("/auth/sessions", "{}", false);
        assertThat(response.statusCode()).isEqualTo(200);
        assertThat(response.body()).contains("\"success\":true", "synthetic-access-token");
        verify(authentication).login(any());
    }

    @Test
    void healthRemainsPublicForDeploymentProbes() throws Exception {
        assertThat(send("/actuator/health", null, false).statusCode()).isEqualTo(200);
    }

    @Test
    void actuatorDiscoveryRequiresItsConfiguredHeader() throws Exception {
        assertThat(send("/actuator", null, false).statusCode()).isEqualTo(401);
        assertThat(send("/actuator", null, true).statusCode()).isEqualTo(200);
    }

    @Test
    void managementTokenDoesNotGrantAccessToNormalProtectedBusinessResources() throws Exception {
        assertThat(send("/auth/current/resources", null, true).statusCode()).isIn(401, 403);
        verifyNoInteractions(authentication);
    }

    private AuthLoginVO syntheticLogin() {
        var result = new AuthLoginVO();
        result.setToken("synthetic-access-token");
        return result;
    }

    private HttpResponse<String> send(String path, String body, boolean actuatorAuthorized) throws Exception {
        var request = HttpRequest.newBuilder(URI.create("http://127.0.0.1:" + port + path));
        if (actuatorAuthorized) {
            request.header("actuatorReq", "synthetic-user-actuator-test-token");
        }
        if (body == null) {
            request.GET();
        } else {
            request.header("Content-Type", "application/json").POST(HttpRequest.BodyPublishers.ofString(body));
        }
        return HttpClient.newHttpClient().send(request.build(), HttpResponse.BodyHandlers.ofString());
    }

    @Configuration(proxyBeanMethods = false)
    @Import({AuthController.class, SecurityAuthConfig.class, ActuatorSecurityConfig.class, GlobalExceptionHandler.class})
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
