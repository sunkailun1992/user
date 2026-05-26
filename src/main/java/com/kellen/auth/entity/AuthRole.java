package com.kellen.auth.entity;

import com.baomidou.mybatisplus.annotation.TableName;
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
}
