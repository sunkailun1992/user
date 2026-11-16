package com.kellen.auth.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.kellen.auth.entity.enums.AuthDataScopeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * 认证角色对象
 * <p>
 * 角色是用户和资源之间的授权分组。
 *
 * @author sunkailun
 * @className AuthRole
 * @time 2026/05/25
 */
@Getter
@Setter
@TableName("auth_role")
@Schema(description = "认证角色对象")
public class AuthRole extends AuthEntity {

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
     * 数据权限范围。
     */
    @Schema(description = "数据权限范围")
    private AuthDataScopeEnum dataScope;
}
