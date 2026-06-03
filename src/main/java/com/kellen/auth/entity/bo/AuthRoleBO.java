package com.kellen.auth.entity.bo;

import com.kellen.auth.entity.enums.AuthStateEnum;
import com.kellen.auth.entity.enums.AuthDataScopeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;

/**
 * 认证角色写入参数。
 *
 * @author sunkailun
 * @className AuthRoleBO
 * @time 2026/05/26
 */
@Data
@Schema(description = "认证角色写入参数")
public class AuthRoleBO implements Serializable {

    /**
     * 角色主键。
     */
    @Schema(description = "角色主键", example = "2000")
    @NotBlank(groups = {Update.class, Remove.class}, message = "id不能为空")
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
    @NotBlank(groups = {Save.class, Update.class, Remove.class}, message = "tenantId不能为空")
    private String tenantId;

    /**
     * 角色编码。
     */
    @Schema(description = "角色编码", example = "tenant_admin")
    @NotBlank(groups = {Save.class}, message = "code不能为空")
    private String code;

    /**
     * 角色名称。
     */
    @Schema(description = "角色名称", example = "租户管理员")
    @NotBlank(groups = {Save.class}, message = "name不能为空")
    private String name;

    /**
     * 负责人用户ID。
     */
    @Schema(description = "负责人用户ID", example = "1000000000000000001")
    private String ownerUserId;

    /**
     * 归属部门ID。
     */
    @Schema(description = "归属部门ID", example = "1000")
    private String deptId;

    /**
     * 角色状态。
     */
    @Schema(description = "角色状态", example = "启用")
    private AuthStateEnum state;

    /**
     * 数据权限范围。
     */
    @Schema(description = "数据权限范围", example = "ALL")
    private AuthDataScopeEnum dataScope;

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

    /**
     * 删除校验分组。
     */
    public interface Remove {
    }
}
