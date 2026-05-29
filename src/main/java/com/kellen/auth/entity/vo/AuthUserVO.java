package com.kellen.auth.entity.vo;

import com.kellen.auth.entity.enums.AuthStateEnum;
import com.kellen.auth.entity.enums.AuthAdminTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 用户响应对象。
 *
 * @author sunkailun
 * @className AuthUserVO
 * @time 2026/05/27
 */
@Data
@Schema(description = "用户响应对象")
public class AuthUserVO implements Serializable {

    /**
     * 用户主键。
     */
    @Schema(description = "用户主键")
    private String id;

    /**
     * 租户ID。
     */
    @Schema(description = "租户ID")
    private String tenantId;

    /**
     * 用户名。
     */
    @Schema(description = "用户名")
    private String username;

    /**
     * 用户昵称。
     */
    @Schema(description = "用户昵称")
    private String nickname;

    /**
     * 管理员分类。
     */
    @Schema(description = "管理员分类")
    private AuthAdminTypeEnum adminType;

    /**
     * 管理员分类说明。
     */
    @Schema(description = "管理员分类说明")
    private String adminTypeDesc;

    /**
     * 用户关联租户ID集合。
     */
    @Schema(description = "用户关联租户ID集合")
    private List<String> tenantIds;

    /**
     * 所属部门ID。
     */
    @Schema(description = "所属部门ID")
    private String deptId;

    /**
     * 用户状态。
     */
    @Schema(description = "用户状态")
    private AuthStateEnum state;

    /**
     * 用户状态说明。
     */
    @Schema(description = "用户状态说明")
    private String stateDesc;

    /**
     * 数据库版本号。
     */
    @Schema(description = "数据库版本号")
    private Integer version;
}
