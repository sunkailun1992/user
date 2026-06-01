package com.kellen.auth.entity.query;

import com.kellen.auth.entity.enums.AuthStateEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;

/**
 * 部门查询参数。
 *
 * @author sunkailun
 * @className AuthDeptQuery
 * @time 2026/05/27
 */
@Data
@Schema(description = "部门查询参数")
public class AuthDeptQuery implements Serializable {

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
     * 部门主键。
     */
    @Schema(description = "部门主键")
    private String id;

    /**
     * 部门编码。
     */
    @Schema(description = "部门编码")
    private String code;

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

    /**
     * 部门状态。
     */
    @Schema(description = "部门状态")
    private AuthStateEnum state;

    /**
     * 通用关键字。
     */
    @Schema(description = "通用关键字")
    private String query;

    /**
     * 分页查询校验分组。
     */
    public interface Select {
    }
}
