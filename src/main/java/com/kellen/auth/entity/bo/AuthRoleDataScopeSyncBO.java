package com.kellen.auth.entity.bo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 角色数据范围同步参数。
 *
 * @author sunkailun
 * @className AuthRoleDataScopeSyncBO
 * @time 2026/05/27
 */
@Data
@Schema(description = "角色数据范围同步参数")
public class AuthRoleDataScopeSyncBO implements Serializable {

    /**
     * 租户ID。
     */
    @Schema(description = "租户ID")
    @NotBlank(message = "tenantId不能为空")
    private String tenantId;

    /**
     * 角色ID。
     */
    @Schema(description = "角色ID")
    @NotBlank(message = "roleId不能为空")
    private String roleId;

    /**
     * 部门ID列表。
     */
    @Schema(description = "部门ID列表")
    @NotNull(message = "deptIds不能为空")
    private List<String> deptIds;
}
