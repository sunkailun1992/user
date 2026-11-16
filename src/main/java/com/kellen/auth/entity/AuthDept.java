package com.kellen.auth.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * 认证部门对象
 * <p>
 * 部门用于描述用户在租户内的组织归属，并为数据权限提供部门维度。
 *
 * @author sunkailun
 * @className AuthDept
 * @time 2026/05/27
 */
@Getter
@Setter
@TableName("auth_dept")
@Schema(description = "认证部门对象")
public class AuthDept extends AuthEntity {

    /**
     * 部门名称。
     */
    @Schema(description = "部门名称")
    private String name;

    /**
     * 父级部门ID。
     */
    @Schema(description = "父级部门ID")
    private String parentId;

    /**
     * 负责人用户ID。
     */
    @Schema(description = "负责人用户ID")
    private String ownerUserId;
}
