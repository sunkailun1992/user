package com.kellen.auth.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * 认证用户对象
 * <p>
 * 用于登录认证，用户通过角色关联前端资源和后端接口权限。
 *
 * @author sunkailun
 * @className AuthUser
 * @time 2026/05/25
 */
@Getter
@Setter
@TableName("auth_user")
@Schema(description = "认证用户对象")
public class AuthUser extends AuthEntity {

    @Schema(description = "用户名")
    private String username;

    @Schema(description = "BCrypt加密后的密码")
    private String password;

    @Schema(description = "用户昵称")
    private String nickname;

    /**
     * 所属部门ID。
     */
    @Schema(description = "所属部门ID")
    private String deptId;
}
