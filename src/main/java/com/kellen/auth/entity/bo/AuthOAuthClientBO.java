package com.kellen.auth.entity.bo;

import com.kellen.auth.entity.enums.AuthStateEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;

/**
 * OAuth 客户端写入参数。
 */
@Data
@Schema(description = "OAuth 客户端写入参数")
public class AuthOAuthClientBO implements Serializable {

    @Schema(description = "主键")
    private String id;

    @Schema(description = "数据库版本号，修改时必传")
    @NotNull(groups = {Update.class}, message = "version不能为空")
    private Integer version;

    @Schema(description = "租户ID")
    @NotBlank(groups = {Save.class, Update.class}, message = "tenantId不能为空")
    private String tenantId;

    @Schema(description = "OAuth client_id")
    @NotBlank(groups = {Save.class}, message = "clientId不能为空")
    private String clientId;

    @Schema(description = "OAuth client_secret，公开客户端可为空，修改时为空表示不轮换")
    private String clientSecret;

    @Schema(description = "客户端名称")
    @NotBlank(groups = {Save.class}, message = "name不能为空")
    private String name;

    @Schema(description = "客户端类型：public/confidential")
    private String clientType;

    @Schema(description = "token endpoint 认证方式：none/client_secret_basic/client_secret_post")
    private String tokenEndpointAuthMethod;

    @Schema(description = "允许的 grant type，空格、逗号或换行分隔")
    @NotBlank(groups = {Save.class}, message = "grantTypes不能为空")
    private String grantTypes;

    @Schema(description = "允许的 redirect_uri，空格、逗号或换行分隔")
    private String redirectUris;

    @Schema(description = "允许的 scope，空格、逗号或换行分隔")
    @NotBlank(groups = {Save.class}, message = "scopes不能为空")
    private String scopes;

    @Schema(description = "允许访问的 audience/resource，空格、逗号或换行分隔")
    @NotBlank(groups = {Save.class}, message = "audiences不能为空")
    private String audiences;

    @Schema(description = "客户端主页")
    private String clientUri;

    @Schema(description = "客户端图标")
    private String logoUri;

    @Schema(description = "access token 有效秒数")
    private Long accessTokenTtlSeconds;

    @Schema(description = "状态")
    private AuthStateEnum state;

    public interface Save {
    }

    public interface Update {
    }
}
