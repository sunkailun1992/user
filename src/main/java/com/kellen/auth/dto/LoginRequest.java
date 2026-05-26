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

    @Schema(description = "租户ID")
    private String tenantId;

    @Schema(description = "用户名")
    private String username;

    @Schema(description = "密码")
    private String password;
}
