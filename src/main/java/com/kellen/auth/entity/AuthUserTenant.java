package com.kellen.auth.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * 用户租户关联对象。
 *
 * @author sunkailun
 * @className AuthUserTenant
 * @time 2026/05/29
 */
@Getter
@Setter
@TableName("auth_user_tenant")
@Schema(description = "用户租户关联对象")
public class AuthUserTenant extends AuthEntity {

    /**
     * 用户ID。
     */
    @Schema(description = "用户ID")
    private String userId;

    /**
     * 关联租户ID。
     */
    @Schema(description = "关联租户ID")
    private String relationTenantId;

    /**
     * 当前租户内部门ID。
     */
    @Schema(description = "当前租户内部门ID")
    private String deptId;

    /**
     * 是否默认租户。
     */
    @Schema(description = "是否默认租户")
    private Boolean defaultTenant;
}
