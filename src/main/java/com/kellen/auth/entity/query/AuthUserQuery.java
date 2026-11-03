package com.kellen.auth.entity.query;

import com.kellen.auth.entity.enums.AuthStateEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.io.Serializable;

/**
 * 用户查询参数。
 *
 * @author sunkailun
 * @className AuthUserQuery
 * @time 2026/05/27
 */
@Data
@Schema(description = "用户查询参数")
public class AuthUserQuery implements Serializable {

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
     * 用户主键。
     */
    @Schema(description = "用户主键")
    private String id;

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
