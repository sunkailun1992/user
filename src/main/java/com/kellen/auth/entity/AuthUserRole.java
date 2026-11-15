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

    @Schema(description = "用户ID")
    private String userId;

    @Schema(description = "角色ID")
    private String roleId;
}
