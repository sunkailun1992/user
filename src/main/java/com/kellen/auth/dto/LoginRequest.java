package com.kellen.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * 登录请求参数
 *
 * @author sunkailun
 * @className LoginRequest
 * @time 2026/05/25
 */
@Getter
@Setter
@Schema(description = "登录请求参数")
public class LoginRequest {

    /**
     * 租户ID。
     */
    @Schema(description = "租户ID")
    private String tenantId;

    /**
     * 租户编码。
     */
    @Schema(description = "租户编码")
    private String tenantCode;

    /**
     * 用户名。
     */
    @Schema(description = "用户名")
    private String username;

    /**
     * 密码。
     */
    @Schema(description = "密码")
    private String password;
}
