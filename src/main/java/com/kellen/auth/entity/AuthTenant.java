package com.kellen.auth.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * 认证租户对象。
 * <p>
 * 租户是用户、角色、权限资源的数据隔离边界，用户登录前先根据租户编码确定租户上下文。
 *
 * @author sunkailun
 * @className AuthTenant
 * @time 2026/05/26
 */
@Getter
@Setter
@TableName("auth_tenant")
@Schema(description = "认证租户对象")
public class AuthTenant extends AuthEntity {

    /**
     * 租户名称。
     */
    @Schema(description = "租户名称")
    private String name;
}
