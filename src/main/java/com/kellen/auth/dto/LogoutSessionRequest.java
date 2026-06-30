package com.kellen.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 退出登录会话请求。
 */
@Data
@Schema(description = "退出登录会话请求")
public class LogoutSessionRequest {

    /**
     * 可选 refresh token，用于服务端同步撤销刷新凭据。
     */
    @Schema(description = "可选 refresh token")
    private String refreshToken;
}
