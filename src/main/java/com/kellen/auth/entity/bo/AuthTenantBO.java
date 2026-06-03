package com.kellen.auth.entity.bo;

import com.kellen.auth.entity.enums.AuthStateEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;

/**
 * 认证租户写入参数。
 *
 * @author sunkailun
 * @className AuthTenantBO
 * @time 2026/05/26
 */
@Data
@Schema(description = "认证租户写入参数")
public class AuthTenantBO implements Serializable {

    /**
     * 租户主键。
     */
    @Schema(description = "租户主键", example = "100")
    @NotBlank(groups = {Update.class, Remove.class}, message = "id不能为空")
    private String id;

    /**
     * 数据库版本号。
     */
    @Schema(description = "数据库版本号，修改时必传", example = "1")
    @NotNull(groups = {Update.class}, message = "version不能为空")
    private Integer version;

    /**
     * 租户编码。
     */
    @Schema(description = "租户编码", example = "tenant_demo")
    @NotBlank(groups = {Save.class}, message = "code不能为空")
    private String code;

    /**
     * 租户名称。
     */
    @Schema(description = "租户名称", example = "演示租户")
    @NotBlank(groups = {Save.class}, message = "name不能为空")
    private String name;

    /**
     * 租户状态。
     */
    @Schema(description = "租户状态", example = "启用")
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
