package com.kellen.auth.entity.bo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 用户角色同步参数。
 *
 * @author sunkailun
 * @className AuthUserRoleSyncBO
 * @time 2026/05/29
 */
@Data
@Schema(description = "用户角色同步参数")
public class AuthUserRoleSyncBO implements Serializable {

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
     * 角色ID列表。
     */
    @Schema(description = "角色ID列表")
    @NotNull(message = "roleIds不能为空")
    private List<String> roleIds;
}
