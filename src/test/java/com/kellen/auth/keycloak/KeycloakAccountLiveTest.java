package com.kellen.auth.keycloak;

import com.kellen.security.oauth2.client.FleetOAuth2ClientAutoConfiguration;
import com.kellen.security.oauth2.client.FleetOAuth2Clients;
import com.kellen.utils.enumeration.ReturnCode;
import com.kellen.utils.exception.UserException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.context.ConfigurationPropertiesAutoConfiguration;
import org.springframework.boot.autoconfigure.context.PropertyPlaceholderAutoConfiguration;
import org.springframework.boot.http.converter.autoconfigure.HttpMessageConvertersAutoConfiguration;
import org.springframework.boot.jackson.autoconfigure.JacksonAutoConfiguration;
import org.springframework.boot.security.oauth2.client.autoconfigure.OAuth2ClientAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatusCode;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * 对 NAS 上真实 Keycloak（github-dev 域，Terraform 管理）的账号同步：经 github-user-admin 的客户端凭证（与线上同一套
 * {@link KeycloakAccountConfiguration} 与 utils 出站令牌）调 Admin REST API，建临时账号、改资料、启停、重置口令、删除，逐步读回。
 *
 * <p>只在设置了 {@code FLEET_KC_TEST_USER_ADMIN_CLIENT_SECRET} 时运行（本机私有的 fleet-kc-test.env，由 kc_test_user.py 生成）；
 * HTTPS 证书由 mkcert 签发，JDK 信任库已导入其根证书。CI 不设置这些变量，自动跳过。临时账号的用户名带随机后缀，测试结束一定删除。</p>
 */
@EnabledIfEnvironmentVariable(named = "FLEET_KC_TEST_USER_ADMIN_CLIENT_SECRET", matches = ".+")
@SpringBootTest(classes = KeycloakAccountLiveTest.Config.class, properties = {
        "spring.security.oauth2.client.registration.keycloak-admin.provider=keycloak",
        "spring.security.oauth2.client.registration.keycloak-admin.client-id=github-user-admin",
        "spring.security.oauth2.client.registration.keycloak-admin.authorization-grant-type=client_credentials",
        "fleet.keycloak.admin.client-registration-id=keycloak-admin"})
class KeycloakAccountLiveTest {

    @Autowired
    private KeycloakAccountClient client;

    @Autowired
    private KeycloakAdminProperties properties;

    @Autowired
    private FleetOAuth2Clients clients;

    @DynamicPropertySource
    static void live(DynamicPropertyRegistry registry) {
        registry.add("fleet.keycloak.admin.issuer-uri", () -> System.getenv("FLEET_KC_TEST_ISSUER"));
        registry.add("spring.security.oauth2.client.provider.keycloak.token-uri",
                () -> System.getenv("FLEET_KC_TEST_ISSUER") + "/protocol/openid-connect/token");
        registry.add("spring.security.oauth2.client.registration.keycloak-admin.client-secret",
                () -> System.getenv("FLEET_KC_TEST_USER_ADMIN_CLIENT_SECRET"));
    }

    @Test
    void accountLifecycleIsSyncedToKeycloak() {
        String suffix = UUID.randomUUID().toString().substring(0, 8);
        String username = "fleet-sync-test-" + suffix;
        String userId = "sync-test-" + suffix;
        String subject = client.createAccount(userId, username, "Sync Test", "Temp-" + UUID.randomUUID());
        try {
            Map<String, Object> created = account(subject);
            assertThat(created.get("username")).isEqualTo(username);
            assertThat(created.get("firstName")).isEqualTo("Sync Test");
            assertThat(created.get("enabled")).isEqualTo(true);
            assertThat(attributes(created).get("user_id")).isEqualTo(List.of(userId));
            assertThat(requiredActions(created)).containsExactly("UPDATE_PASSWORD");

            client.updateProfile(subject, username + "-renamed", "Sync Test Two");
            Map<String, Object> renamed = account(subject);
            assertThat(renamed.get("username")).isEqualTo(username + "-renamed");
            assertThat(renamed.get("firstName")).isEqualTo("Sync Test Two");
            assertThat(attributes(renamed).get("user_id")).isEqualTo(List.of(userId));

            client.setEnabled(subject, false);
            assertThat(account(subject).get("enabled")).isEqualTo(false);
            client.setEnabled(subject, true);
            assertThat(account(subject).get("enabled")).isEqualTo(true);
            assertThat(attributes(account(subject)).get("user_id")).isEqualTo(List.of(userId));

            client.resetPassword(subject, "Temp-" + UUID.randomUUID());
            assertThat(requiredActions(account(subject))).contains("UPDATE_PASSWORD");

            assertThatThrownBy(() -> client.createAccount("other-" + suffix, username + "-renamed", "Duplicate", "Temp-" + UUID.randomUUID()))
                    .isInstanceOfSatisfying(UserException.class, ex -> assertThat(ex.getReturnCode()).isEqualTo(ReturnCode.USERNAME_ALREADY_EXISTS));
        } finally {
            client.deleteAccount(subject);
        }
        assertThat(status(subject)).isEqualTo(404);
        client.deleteAccount(subject);
    }

    private Map<String, Object> account(String subject) {
        return admin().get().uri("/users/{id}", subject).retrieve().body(new ParameterizedTypeReference<>() {
        });
    }

    private int status(String subject) {
        return admin().get().uri("/users/{id}", subject).exchange((request, response) -> response.getStatusCode()).value();
    }

    private RestClient admin() {
        return RestClient.builder().baseUrl(properties.adminBaseUrl()).requestInterceptor(clients.interceptor(properties.getClientRegistrationId()))
                .defaultStatusHandler(HttpStatusCode::isError, (request, response) -> {
                    throw new IllegalStateException("Keycloak Admin API 返回 " + response.getStatusCode().value());
                }).build();
    }

    @SuppressWarnings("unchecked")
    private static Map<String, Object> attributes(Map<String, Object> account) {
        Object attributes = account.get("attributes");
        return attributes == null ? Map.of() : (Map<String, Object>) attributes;
    }

    @SuppressWarnings("unchecked")
    private static List<String> requiredActions(Map<String, Object> account) {
        Object actions = account.get("requiredActions");
        return actions == null ? List.of() : (List<String>) actions;
    }

    /**
     * 测试上下文：Spring Boot 的 OAuth2 客户端、utils 出站令牌与 user 的账号同步配置。
     */
    @Configuration(proxyBeanMethods = false)
    @Import(KeycloakAccountConfiguration.class)
    @ImportAutoConfiguration({PropertyPlaceholderAutoConfiguration.class, ConfigurationPropertiesAutoConfiguration.class,
            JacksonAutoConfiguration.class, HttpMessageConvertersAutoConfiguration.class, OAuth2ClientAutoConfiguration.class,
            FleetOAuth2ClientAutoConfiguration.class})
    static class Config {
    }
}
