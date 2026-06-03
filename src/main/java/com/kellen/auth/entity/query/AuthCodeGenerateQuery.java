package com.kellen.auth.entity.query;

import com.kellen.auth.entity.enums.AuthResourceCategoryEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.io.Serializable;

/**
 * 认证编码生成查询参数。
 *
 * @author sunkailun
 * @className AuthCodeGenerateQuery
 * @time 2026/05/27
 */
@Data
@Schema(description = "认证编码生成查询参数")
public class AuthCodeGenerateQuery implements Serializable {

    /**
     * 编码目标：TENANT、DEPT、ROLE、RESOURCE。
     */
    @Schema(description = "编码目标：TENANT、DEPT、ROLE、RESOURCE", example = "USER")
    @NotBlank(message = "target不能为空")
    private String target;

    /**
     * 租户ID，租户内资源生成时传入。
     */
    @Schema(description = "租户ID", example = "100")
    private String tenantId;

    /**
     * 资源分类，target=RESOURCE 时用于区分前端菜单和后端接口。
     */
    @Schema(description = "资源分类", example = "BACKEND")
    private AuthResourceCategoryEnum resourceCategory;

    /**
     * 业务名称，用于生成更可读的编码片段。
     */
    @Schema(description = "业务名称", example = "用户管理")
    private String name;
}
