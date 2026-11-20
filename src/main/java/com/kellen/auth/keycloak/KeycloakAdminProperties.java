package com.kellen.auth.keycloak;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Keycloak 账号同步配置（前缀 {@code fleet.keycloak.admin}，Nacos 的 user-spring.yaml）。
 *
 * @author 孙凯伦
 */
@Getter
@Setter
@ConfigurationProperties(prefix = "fleet.keycloak.admin")
public class KeycloakAdminProperties {

    /**
     * 签发方（https://&lt;主机&gt;/realms/&lt;域&gt;）；管理接口地址由它推出。
     */
    private String issuerUri;

    /**
     * 取管理令牌的客户端注册 ID（spring.security.oauth2.client.registration 下的客户端凭证注册）。
     */
    private String clientRegistrationId = "keycloak-admin";

    /**
     * 创建带缺省值的配置，由 Spring Boot 按前缀绑定。
     */
    public KeycloakAdminProperties() {
    }

    /**
     * 管理接口地址：签发方的 {@code /realms/<域>} 换成 {@code /admin/realms/<域>}（Keycloak 的固定格式）。
     *
     * @return 管理接口地址
     */
    public String adminBaseUrl() {
        if (issuerUri == null || !issuerUri.contains("/realms/")) {
            throw new IllegalStateException("fleet.keycloak.admin.issuer-uri 必须是 Keycloak 域地址（…/realms/<域>）");
        }
        return issuerUri.replace("/realms/", "/admin/realms/");
    }
}
