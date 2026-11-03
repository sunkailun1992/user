package com.kellen.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 刷新登录会话请求。
 */
@Data
@Schema(description = "刷新登录会话请求")
public class RefreshSessionRequest {

    /**
     * refresh token。
     */
    @Schema(description = "refresh token")
    private String refreshToken;
}
