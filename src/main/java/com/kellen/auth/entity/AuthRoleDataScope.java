package com.kellen.auth.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * 角色自定义数据范围对象
 * <p>
 * 当角色数据范围为 CUSTOM 时，使用该关系表维护角色可访问部门。
 *
 * @author sunkailun
 * @className AuthRoleDataScope
 * @time 2026/05/27
 */
@Getter
@Setter
@TableName("auth_role_data_scope")
@Schema(description = "角色自定义数据范围对象")
public class AuthRoleDataScope extends AuthEntity {

    /**
     * 角色ID。
     */
    @Schema(description = "角色ID")
    private String roleId;

    /**
     * 部门ID。
     */
    @Schema(description = "部门ID")
    private String deptId;
}
