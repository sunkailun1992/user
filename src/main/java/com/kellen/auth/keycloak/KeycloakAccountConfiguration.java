package com.kellen.auth.keycloak;

import com.kellen.security.oauth2.client.FleetOAuth2Clients;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.JdkClientHttpRequestFactory;
import org.springframework.web.client.RestClient;

import java.net.http.HttpClient;
import java.time.Duration;

/**
 * Keycloak 账号同步客户端：基础地址为管理接口，管理令牌由 utils 的 {@link FleetOAuth2Clients} 按客户端凭证注册取得
 * （github-user-admin，服务账号只有 manage-users、view-users）。连接 5 秒、读取 10 秒超时。
 *
 * @author 孙凯伦
 */
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(KeycloakAdminProperties.class)
public class KeycloakAccountConfiguration {

    /**
     * 账号同步客户端。
     *
     * @param properties Keycloak 账号同步配置
     * @param clients    出站令牌工具
     * @return 账号同步客户端
     */
    @Bean
    public KeycloakAccountClient keycloakAccountClient(KeycloakAdminProperties properties, FleetOAuth2Clients clients) {
        JdkClientHttpRequestFactory requestFactory = new JdkClientHttpRequestFactory(
                HttpClient.newBuilder().connectTimeout(Duration.ofSeconds(5)).followRedirects(HttpClient.Redirect.NEVER).build());
        requestFactory.setReadTimeout(Duration.ofSeconds(10));
        return new KeycloakAccountClient(RestClient.builder().baseUrl(properties.adminBaseUrl()).requestFactory(requestFactory)
                .requestInterceptor(clients.interceptor(properties.getClientRegistrationId())).build());
    }
}
