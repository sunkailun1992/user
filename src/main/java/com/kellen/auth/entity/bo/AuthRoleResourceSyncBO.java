package com.kellen.auth.entity.bo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 角色资源同步授权参数。
 *
 * @author sunkailun
 * @className AuthRoleResourceSyncBO
 * @time 2026/05/27
 */
@Data
@Schema(description = "角色资源同步授权参数")
public class AuthRoleResourceSyncBO implements Serializable {

    /**
     * 租户ID。
     */
    @Schema(description = "租户ID", example = "100")
    @NotBlank(message = "tenantId不能为空")
    private String tenantId;

    /**
     * 角色ID。
     */
    @Schema(description = "角色ID", example = "2000")
    private String roleId;

    /**
     * 完整资源ID列表。
     */
    @Schema(description = "完整资源ID列表", example = "[\"3000\"]")
    @NotNull(message = "resourceIds不能为空")
    private List<String> resourceIds;
}
