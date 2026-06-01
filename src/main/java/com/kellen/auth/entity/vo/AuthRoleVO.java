package com.kellen.auth.entity.vo;

import com.kellen.auth.entity.enums.AuthStateEnum;
import com.kellen.auth.entity.enums.AuthDataScopeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * 角色响应对象。
 *
 * @author sunkailun
 * @className AuthRoleVO
 * @time 2026/05/27
 */
@Data
@Schema(description = "角色响应对象")
public class AuthRoleVO implements Serializable {

    /**
     * 角色主键。
     */
    @Schema(description = "角色主键")
    private String id;

    /**
     * 租户ID。
     */
    @Schema(description = "租户ID")
    private String tenantId;

    /**
     * 角色编码。
     */
    @Schema(description = "角色编码")
    private String code;

    /**
     * 角色名称。
     */
    @Schema(description = "角色名称")
    private String name;

    /**
     * 负责人用户ID。
     */
    @Schema(description = "负责人用户ID")
    private String ownerUserId;

    /**
     * 归属部门ID。
     */
    @Schema(description = "归属部门ID")
    private String deptId;

    /**
     * 角色状态。
     */
    @Schema(description = "角色状态")
    private AuthStateEnum state;

    /**
     * 角色状态说明。
     */
    @Schema(description = "角色状态说明")
    private String stateDesc;

    /**
     * 数据权限范围。
     */
    @Schema(description = "数据权限范围")
    private AuthDataScopeEnum dataScope;

    /**
     * 数据权限范围说明。
     */
    @Schema(description = "数据权限范围说明")
    private String dataScopeDesc;

    /**
     * 排序。
     */
    @Schema(description = "排序")
    private Integer sorting;

    /**
     * 数据库版本号。
     */
    @Schema(description = "数据库版本号")
    private Integer version;
}
