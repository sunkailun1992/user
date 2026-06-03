package com.kellen.auth.entity.bo;

import com.kellen.auth.entity.enums.AuthResourceCategoryEnum;
import com.kellen.auth.entity.enums.AuthStateEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;

/**
 * 认证资源写入参数。
 *
 * @author sunkailun
 * @className AuthResourceBO
 * @time 2026/05/26
 */
@Data
@Schema(description = "认证资源写入参数")
public class AuthResourceBO implements Serializable {

    /**
     * 资源主键。
     */
    @Schema(description = "资源主键", example = "3000")
    @NotBlank(groups = {Update.class, Remove.class}, message = "id不能为空")
    private String id;

    /**
     * 数据库版本号。
     */
    @Schema(description = "数据库版本号，修改时必传", example = "1")
    @NotNull(groups = {Update.class}, message = "version不能为空")
    private Integer version;

    /**
     * 租户ID。
     */
    @Schema(description = "租户ID", example = "100")
    @NotBlank(groups = {Save.class, Update.class, Remove.class}, message = "tenantId不能为空")
    private String tenantId;

    /**
     * 权限编码。
     */
    @Schema(description = "权限编码", example = "user:auth:manage")
    @NotBlank(groups = {Save.class}, message = "code不能为空")
    private String code;

    /**
     * 资源名称。
     */
    @Schema(description = "资源名称", example = "用户管理")
    @NotBlank(groups = {Save.class}, message = "name不能为空")
    private String name;

    /**
     * 资源分类。
     */
    @Schema(description = "资源分类", example = "BACKEND")
    @NotNull(groups = {Save.class}, message = "resourceCategory不能为空")
    private AuthResourceCategoryEnum resourceCategory;

    /**
     * 资源路径。
     */
    @Schema(description = "资源路径", example = "/auth/manage/users")
    private String path;

    /**
     * 请求方法。
     */
    @Schema(description = "请求方法", example = "GET")
    private String method;

    /**
     * 父级资源ID。
     */
    @Schema(description = "父级资源ID", example = "0")
    private String parentId;

    /**
     * 排序。
     */
    @Schema(description = "排序", example = "1")
    private Integer sorting;

    /**
     * 资源状态。
     */
    @Schema(description = "资源状态", example = "启用")
    private AuthStateEnum state;

    /**
     * 新增校验分组。
     */
    public interface Save {
    }

    /**
     * 修改校验分组。
     */
    public interface Update {
    }

    /**
     * 删除校验分组。
     */
    public interface Remove {
    }
}
