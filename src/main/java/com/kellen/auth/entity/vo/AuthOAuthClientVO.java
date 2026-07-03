package com.kellen.auth.entity.vo;

import com.kellen.auth.entity.enums.AuthStateEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * OAuth 客户端响应对象。
 */
@Data
@Schema(description = "OAuth 客户端响应对象")
public class AuthOAuthClientVO implements Serializable {

    @Schema(description = "主键")
    private String id;

    @Schema(description = "租户ID")
    private String tenantId;

    @Schema(description = "OAuth client_id")
    private String clientId;

    @Schema(description = "客户端名称")
    private String name;

    @Schema(description = "客户端类型：public/confidential")
    private String clientType;

    @Schema(description = "token endpoint 认证方式")
    private String tokenEndpointAuthMethod;

    @Schema(description = "允许的 grant type")
    private String grantTypes;

    @Schema(description = "允许的 redirect_uri")
    private String redirectUris;

    @Schema(description = "允许的 scope")
    private String scopes;

    @Schema(description = "允许访问的 audience/resource")
    private String audiences;

    @Schema(description = "客户端主页")
    private String clientUri;

    @Schema(description = "客户端图标")
    private String logoUri;

    @Schema(description = "access token 有效秒数")
    private Long accessTokenTtlSeconds;

    @Schema(description = "状态")
    private AuthStateEnum state;

    @Schema(description = "状态说明")
    private String stateDesc;

    @Schema(description = "数据库版本号")
    private Integer version;
}
