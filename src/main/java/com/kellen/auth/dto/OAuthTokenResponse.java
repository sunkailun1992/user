package com.kellen.auth.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;

/**
 * OAuth2 token 响应。
 *
 * <p>该接口按 OAuth 约定返回蛇形字段，不套用 {@code ApiResponse}，
 * 便于通用 MCP / OAuth 客户端直接消费。</p>
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "OAuth2 token 响应")
public record OAuthTokenResponse(
        @JsonProperty("access_token")
        @Schema(description = "access token")
        String accessToken,

        @JsonProperty("token_type")
        @Schema(description = "token 类型")
        String tokenType,

        @JsonProperty("expires_in")
        @Schema(description = "access token 有效秒数")
        long expiresIn,

        @Schema(description = "授权 scope，空格分隔")
        String scope
) implements Serializable {
}
