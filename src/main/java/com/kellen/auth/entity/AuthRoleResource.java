package com.kellen.auth.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * 角色资源关系对象
 * <p>
 * 用于建立角色和资源的多对多关系。
 *
 * @author sunkailun
 * @className AuthRoleResource
 * @time 2026/05/25
 */
@Getter
@Setter
@TableName("auth_role_resource")
@Schema(description = "角色资源关系对象")
public class AuthRoleResource extends AuthEntity {

    @Schema(description = "角色ID")
    private String roleId;

    @Schema(description = "资源ID")
    private String resourceId;
}
