package com.kellen.auth.entity.query;

import com.kellen.auth.entity.enums.AuthStateEnum;
import com.kellen.auth.entity.enums.AuthDataScopeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;

/**
 * 角色查询参数。
 *
 * @author sunkailun
 * @className AuthRoleQuery
 * @time 2026/05/27
 */
@Data
@Schema(description = "角色查询参数")
public class AuthRoleQuery implements Serializable {

    /**
     * 当前页码。
     */
    @Schema(description = "当前页码")
    @NotNull(groups = {Select.class}, message = "current不能为空")
    @Min(groups = {Select.class}, value = 1, message = "current最小为1")
    private Long current;

    /**
     * 每页数量。
     */
    @Schema(description = "每页数量")
    @NotNull(groups = {Select.class}, message = "size不能为空")
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
     * 角色主键。
     */
    @Schema(description = "角色主键")
    private String id;

    /**
     * 角色编码。
     */
    @Schema(description = "角色编码")
    private String code;

    /**
     * 角色名称。
     */
    @Schema(description = "角色名称")
    private String name;

    /**
     * 角色状态。
     */
    @Schema(description = "角色状态")
    private AuthStateEnum state;

    /**
     * 数据权限范围。
     */
    @Schema(description = "数据权限范围")
    private AuthDataScopeEnum dataScope;

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
