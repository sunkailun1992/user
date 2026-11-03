package com.kellen.auth.entity.query;

import com.kellen.auth.entity.enums.AuthResourceCategoryEnum;
import com.kellen.auth.entity.enums.AuthStateEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.io.Serializable;

/**
 * 权限资源查询参数。
 *
 * @author sunkailun
 * @className AuthResourceQuery
 * @time 2026/05/27
 */
@Data
@Schema(description = "权限资源查询参数")
public class AuthResourceQuery implements Serializable {

    /**
     * 当前页码。
     */
    @Schema(description = "当前页码")
    @Min(groups = {Select.class}, value = 1, message = "current最小为1")
    private Long current;

    /**
     * 每页数量。
     */
    @Schema(description = "每页数量")
    @Min(groups = {Select.class}, value = 1, message = "size最小为1")
    private Long size;

    /**
     * 租户ID。
     */
    @Schema(description = "租户ID")
    @NotBlank(message = "tenantId不能为空")
    @NotBlank(groups = {Select.class}, message = "tenantId不能为空")
    private String tenantId;

    /**
     * 资源主键。
     */
    @Schema(description = "资源主键")
    private String id;

    /**
     * 权限编码。
     */
    @Schema(description = "权限编码")
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
    private AuthResourceCategoryEnum resourceCategory;

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
     * 资源状态。
     */
    @Schema(description = "资源状态")
    private AuthStateEnum state;

    /**
     * 指定查询字段。
     */
    @Schema(description = "指定查询字段")
    private String fields;

    /**
     * 是否升序。
     */
    @Schema(description = "是否升序")
    private Boolean collation;

    /**
     * 排序字段。
     */
    @Schema(description = "排序字段")
    private String collationFields;

    /**
     * 通用关键字。
     */
    @Schema(description = "通用关键字")
    private String query;

    /**
     * 是否执行结果增强。
     */
    @Schema(description = "是否执行结果增强")
    private Boolean assignment;

    /**
     * 分页查询校验分组。
     */
    public interface Select {
    }
}
