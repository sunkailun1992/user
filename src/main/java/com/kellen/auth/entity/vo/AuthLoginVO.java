package com.kellen.auth.entity.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 登录响应对象。
 *
 * @author sunkailun
 * @className AuthLoginVO
 * @time 2026/05/26
 */
@Data
@Schema(description = "登录响应对象")
public class AuthLoginVO implements Serializable {

    /**
     * JWT令牌。
     */
    @Schema(description = "JWT令牌")
    private String token;

    /**
     * 令牌类型。
     */
    @Schema(description = "令牌类型")
    private String tokenType;

    /**
     * 用户ID。
     */
    @Schema(description = "用户ID")
    private String userId;

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
     * 租户ID。
     */
    @Schema(description = "租户ID")
    private String tenantId;

    /**
     * 所属部门ID。
     */
    @Schema(description = "所属部门ID")
    private String deptId;

    /**
     * 当前登录用户合并后的数据权限范围。
     */
    @Schema(description = "当前登录用户合并后的数据权限范围")
    private String dataScope;

    /**
     * 当前登录用户可访问部门ID集合。
     */
    @Schema(description = "当前登录用户可访问部门ID集合")
    private List<String> dataScopeDeptIds;

    /**
     * 后端权限码。
     */
    @Schema(description = "后端权限码")
    private List<String> permissions;

    /**
     * 前端资源。
     */
    @Schema(description = "前端资源")
    private List<AuthResourceVO> frontendResources;

    /**
     * 后端资源。
     */
    @Schema(description = "后端资源")
    private List<AuthResourceVO> backendResources;
}
