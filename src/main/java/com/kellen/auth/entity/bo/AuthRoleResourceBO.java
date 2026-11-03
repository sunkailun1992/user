package com.kellen.auth.entity.bo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.io.Serializable;

/**
 * 角色资源授权参数。
 *
 * @author sunkailun
 * @className AuthRoleResourceBO
 * @time 2026/05/26
 */
@Data
@Schema(description = "角色资源授权参数")
public class AuthRoleResourceBO implements Serializable {

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
    private String roleId;

    /**
     * 资源ID。
     */
    @Schema(description = "资源ID")
    @NotBlank(message = "resourceId不能为空")
    private String resourceId;
}
