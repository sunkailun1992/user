package com.kellen.auth.entity.vo;

import com.kellen.auth.entity.enums.AuthStateEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * 认证资源响应对象。
 *
 * @author sunkailun
 * @className AuthResourceVO
 * @time 2026/05/26
 */
@Data
@Schema(description = "认证资源响应对象")
public class AuthResourceVO implements Serializable {

    /**
     * 资源ID。
     */
    @Schema(description = "资源ID")
    private String id;

    /**
     * 资源编码。
     */
    @Schema(description = "资源编码")
    private String code;

    /**
     * 资源名称。
     */
    @Schema(description = "资源名称")
    private String name;

    /**
     * 资源分类。
     */
    @Schema(description = "资源分类")
    private String category;

    /**
     * 资源分类说明。
     */
    @Schema(description = "资源分类说明")
    private String categoryDesc;

    /**
     * 资源路径。
     */
    @Schema(description = "资源路径")
    private String path;

    /**
     * 请求方法。
     */
    @Schema(description = "请求方法")
    private String method;

    /**
     * 父级资源ID。
     */
    @Schema(description = "父级资源ID")
    private String parentId;

    /**
     * 排序。
     */
    @Schema(description = "排序")
    private Integer sorting;

    /**
     * 资源状态。
     */
    @Schema(description = "资源状态")
    private AuthStateEnum state;

    /**
     * 资源状态说明。
     */
    @Schema(description = "资源状态说明")
    private String stateDesc;

    /**
     * 租户ID。
     */
    @Schema(description = "租户ID")
    private String tenantId;

    /**
     * 数据库版本号。
     */
    @Schema(description = "数据库版本号")
    private Integer version;
}
