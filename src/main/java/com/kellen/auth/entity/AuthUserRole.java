package com.kellen.auth.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * 用户角色关系对象
 * <p>
 * 用于建立用户和角色的多对多关系。
 *
 * @author sunkailun
 * @className AuthUserRole
 * @time 2026/05/25
 */
@Getter
@Setter
@TableName("auth_user_role")
@Schema(description = "用户角色关系对象")
public class AuthUserRole extends AuthEntity {

    @Schema(description = "用户ID")
    private String userId;

    @Schema(description = "角色ID")
    private String roleId;
}
