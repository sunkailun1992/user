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
     * 关系编码，由授权或绑定逻辑用两端ID拼成。
     */
    @Schema(description = "编码")
    private String code;

    /**
     * 说明。
     */
    @Schema(description = "说明")
    private String description;

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
