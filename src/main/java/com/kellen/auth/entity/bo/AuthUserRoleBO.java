package com.kellen.auth.entity.bo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.io.Serializable;

/**
 * 用户角色授权参数。
 *
 * @author sunkailun
 * @className AuthUserRoleBO
 * @time 2026/05/26
 */
@Data
@Schema(description = "用户角色授权参数")
public class AuthUserRoleBO implements Serializable {

    /**
     * 租户ID。
     */
    @Schema(description = "租户ID")
    @NotBlank(message = "tenantId不能为空")
    private String tenantId;

    /**
     * 用户ID。
     */
    @Schema(description = "用户ID")
    @NotBlank(message = "userId不能为空")
    private String userId;

    /**
     * 角色ID。
     */
    @Schema(description = "角色ID")
    @NotBlank(message = "roleId不能为空")
    private String roleId;
}
