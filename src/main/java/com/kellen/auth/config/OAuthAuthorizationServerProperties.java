package com.kellen.auth.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.DefaultValue;

/**
 * OAuth 授权服务器对外 metadata 配置。
 */
@ConfigurationProperties(prefix = "oauth.authorization-server")
public record OAuthAuthorizationServerProperties(
        @DefaultValue("") String externalBaseUrl
) {
}
