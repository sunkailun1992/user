package com.kellen.auth.entity.vo;

import com.kellen.auth.entity.enums.AuthStateEnum;
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
