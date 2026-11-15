package com.kellen.auth.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.Version;
import com.kellen.auth.entity.enums.AuthStateEnum;
import com.kellen.auth.entity.enums.AuthTypeEnum;
import com.kellen.entity.TenantEntityBase;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * 认证实体基类
 * <p>
 * 13 张认证表共用：字符串雪花主键、乐观锁版本号，以及把公共 type/state 字段解释成认证业务枚举；
 * 审计、逻辑删除与租户字段继承自 {@link TenantEntityBase}。
 *
 * @author sunkailun
 * @className AuthEntity
 * @time 2026/05/25
 */
@Getter
@Setter
public class AuthEntity extends TenantEntityBase {

    /**
     * 主键，字符串雪花ID。
     */
    @Schema(description = "主键ID")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    /**
     * MyBatis-Plus 乐观锁版本号。
     */
    @Version
    @Schema(description = "版本号（乐观锁，更新时回传查询到的值）")
    private Integer version;

    @Schema(description = "认证数据类型（0：默认）")
    private AuthTypeEnum type;

    @Schema(description = "认证数据状态（0：默认，1：启用，2：禁用）")
    private AuthStateEnum state;
}
