package com.kellen.auth.entity.query;

import com.kellen.auth.entity.enums.AuthStateEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.io.Serializable;

/**
 * OAuth 客户端查询参数。
 */
@Data
@Schema(description = "OAuth 客户端查询参数")
public class AuthOAuthClientQuery implements Serializable {

    @Schema(description = "当前页码")
    @Min(groups = {Select.class}, value = 1, message = "current最小为1")
    private Long current;

    @Schema(description = "每页数量")
    @Min(groups = {Select.class}, value = 1, message = "size最小为1")
    private Long size;

    @Schema(description = "租户ID")
    @NotBlank(message = "tenantId不能为空")
    @NotBlank(groups = {Select.class}, message = "tenantId不能为空")
    private String tenantId;

    @Schema(description = "客户端ID")
    private String clientId;

    @Schema(description = "客户端名称")
    private String name;

    @Schema(description = "客户端类型")
    private String clientType;

    @Schema(description = "状态")
    private AuthStateEnum state;

    @Schema(description = "指定查询字段")
    private String fields;

    @Schema(description = "是否升序")
    private Boolean collation;

    @Schema(description = "排序字段")
    private String collationFields;

    @Schema(description = "通用关键字")
    private String query;

    @Schema(description = "是否执行结果增强")
    private Boolean assignment;

    public interface Select {
    }
}
