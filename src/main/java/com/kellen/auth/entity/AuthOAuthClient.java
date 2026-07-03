package com.kellen.auth.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * OAuth 客户端。
 */
@Getter
@Setter
@TableName("auth_oauth_client")
@Schema(description = "OAuth 客户端")
public class AuthOAuthClient extends AuthEntity {

    @Schema(description = "OAuth client_id")
    private String clientId;

    @Schema(description = "客户端密钥，建议保存 BCrypt 哈希")
    private String clientSecret;

    @Schema(description = "客户端名称")
    private String name;

    @Schema(description = "客户端类型：public/confidential")
    private String clientType;

    @Schema(description = "token endpoint 认证方式：none/client_secret_basic/client_secret_post")
    private String tokenEndpointAuthMethod;

    @Schema(description = "允许的 grant type，空格或逗号分隔")
    private String grantTypes;

    @Schema(description = "允许的 redirect_uri，换行或空格分隔")
    private String redirectUris;

    @Schema(description = "允许的 scope，空格或逗号分隔")
    private String scopes;

    @Schema(description = "允许访问的 audience/resource，空格或逗号分隔")
    private String audiences;

    @Schema(description = "客户端主页")
    private String clientUri;

    @Schema(description = "客户端图标")
    private String logoUri;

    @Schema(description = "access token 有效秒数")
    private Long accessTokenTtlSeconds;
}
