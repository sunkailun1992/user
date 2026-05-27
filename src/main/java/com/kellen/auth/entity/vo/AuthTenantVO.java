package com.kellen.auth.entity.vo;

import com.kellen.auth.entity.enums.AuthStateEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * 租户响应对象。
 *
 * @author sunkailun
 * @className AuthTenantVO
 * @time 2026/05/27
 */
@Data
@Schema(description = "租户响应对象")
public class AuthTenantVO implements Serializable {

    /**
     * 租户主键。
     */
    @Schema(description = "租户主键")
    private String id;

    /**
     * 租户编码。
     */
    @Schema(description = "租户编码")
    private String code;

    /**
     * 租户名称。
     */
    @Schema(description = "租户名称")
    private String name;

    /**
     * 租户状态。
     */
    @Schema(description = "租户状态")
    private AuthStateEnum state;

    /**
     * 租户状态说明。
     */
    @Schema(description = "租户状态说明")
    private String stateDesc;

    /**
     * 排序。
     */
    @Schema(description = "排序")
    private Integer sorting;

    /**
     * 数据库版本号。
     */
    @Schema(description = "数据库版本号")
    private Integer version;
}
