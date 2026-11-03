package com.kellen.auth.entity.vo;

import com.kellen.auth.entity.enums.AuthStateEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * 部门响应对象。
 *
 * @author sunkailun
 * @className AuthDeptVO
 * @time 2026/05/27
 */
@Data
@Schema(description = "部门响应对象")
public class AuthDeptVO implements Serializable {

    /**
     * 部门主键。
     */
    @Schema(description = "部门主键")
    private String id;

    /**
     * 租户ID。
     */
    @Schema(description = "租户ID")
    private String tenantId;

    /**
     * 部门编码。
     */
    @Schema(description = "部门编码")
    private String code;

    /**
     * 部门名称。
     */
    @Schema(description = "部门名称")
    private String name;

    /**
     * 父级部门ID。
     */
    @Schema(description = "父级部门ID")
    private String parentId;

    /**
     * 负责人用户ID。
     */
    @Schema(description = "负责人用户ID")
    private String ownerUserId;

    /**
     * 部门状态。
     */
    @Schema(description = "部门状态")
    private AuthStateEnum state;

    /**
     * 部门状态说明。
     */
    @Schema(description = "部门状态说明")
    private String stateDesc;

    /**
     * 数据库版本号。
     */
    @Schema(description = "数据库版本号")
    private Integer version;
}
