package com.kellen.auth.entity.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 当前用户资源响应对象。
 *
 * @author sunkailun
 * @className AuthCurrentResourceVO
 * @time 2026/05/26
 */
@Data
@Schema(description = "当前用户资源响应对象")
public class AuthCurrentResourceVO implements Serializable {

    /**
     * 用户ID。
     */
    @Schema(description = "用户ID")
    private String userId;

    /**
     * 租户ID。
     */
    @Schema(description = "租户ID")
    private String tenantId;

    /**
     * 所属部门ID。
     */
    @Schema(description = "所属部门ID")
    private String deptId;

    /**
     * 后端权限码。
     */
    @Schema(description = "后端权限码")
    private List<String> permissions;

    /**
     * 前端资源。
     */
    @Schema(description = "前端资源")
    private List<AuthResourceVO> frontendResources;

    /**
     * 后端资源。
     */
    @Schema(description = "后端资源")
    private List<AuthResourceVO> backendResources;
}
