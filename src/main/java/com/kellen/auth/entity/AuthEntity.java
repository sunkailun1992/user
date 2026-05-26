package com.kellen.auth.entity;

import com.kellen.auth.entity.enums.AuthStateEnum;
import com.kellen.auth.entity.enums.AuthTypeEnum;
import com.kellen.bean.EntityBase;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * 认证实体基类
 * <p>
 * 只在认证模块内把公共 type/state 字段解释成认证业务枚举。
 *
 * @author sunkailun
 * @className AuthEntity
 * @time 2026/05/25
 */
@Getter
@Setter
public class AuthEntity extends EntityBase {

    @Schema(description = "认证数据类型（0：默认）")
    private AuthTypeEnum type;

    @Schema(description = "认证数据状态（0：默认，1：启用，2：禁用）")
    private AuthStateEnum state;
}
