package com.kellen.auth.entity.bo;

import com.kellen.auth.entity.enums.AuthStateEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;

/**
 * 认证部门写入参数。
 *
 * @author sunkailun
 * @className AuthDeptBO
 * @time 2026/05/27
 */
@Data
@Schema(description = "认证部门写入参数")
public class AuthDeptBO implements Serializable {

    /**
     * 部门主键。
     */
    @Schema(description = "部门主键", example = "1000")
    private String id;

    /**
     * 数据库版本号。
     */
    @Schema(description = "数据库版本号，修改时必传", example = "1")
    @NotNull(groups = {Update.class}, message = "version不能为空")
    private Integer version;

    /**
     * 租户ID。
     */
    @Schema(description = "租户ID", example = "100")
    @NotBlank(groups = {Save.class, Update.class}, message = "tenantId不能为空")
    private String tenantId;

    /**
     * 部门编码。
     */
    @Schema(description = "部门编码", example = "dept_rd")
    @NotBlank(groups = {Save.class}, message = "code不能为空")
    private String code;

    /**
     * 部门名称。
     */
    @Schema(description = "部门名称", example = "研发部")
    @NotBlank(groups = {Save.class}, message = "name不能为空")
    private String name;

    /**
     * 父级部门ID。
     */
    @Schema(description = "父级部门ID", example = "0")
    private String parentId;

    /**
     * 负责人用户ID。
     */
    @Schema(description = "负责人用户ID", example = "1000000000000000001")
    private String ownerUserId;

    /**
     * 部门状态。
     */
    @Schema(description = "部门状态", example = "启用")
    private AuthStateEnum state;

    /**
     * 新增校验分组。
     */
    public interface Save {
    }

    /**
     * 修改校验分组。
     */
    public interface Update {
    }
}
