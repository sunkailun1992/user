package com.kellen.auth.entity;

import com.kellen.auth.entity.enums.AuthStateEnum;
import com.kellen.auth.entity.enums.AuthTypeEnum;
import com.kellen.entity.TenantEntityBase;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * 认证实体基类
 * <p>
 * 13 张认证表共用：把公共 type/state 字段解释成认证业务枚举；主键、编码、说明、审计、逻辑删除、排序、
 * 乐观锁版本号、租户与创建部门都继承自 {@link TenantEntityBase}。
 *
 * @author sunkailun
 * @className AuthEntity
 * @time 2026/05/25
 */
@Getter
@Setter
public class AuthEntity extends TenantEntityBase {

    @Schema(description = "认证数据类型（0：默认）")
    private AuthTypeEnum type;

    @Schema(description = "认证数据状态（0：默认，1：启用，2：禁用）")
    private AuthStateEnum state;
}
