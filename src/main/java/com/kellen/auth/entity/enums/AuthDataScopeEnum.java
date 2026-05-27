package com.kellen.auth.entity.enums;

import com.baomidou.mybatisplus.annotation.IEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 角色数据权限范围枚举。
 *
 * @author sunkailun
 * @className AuthDataScopeEnum
 * @time 2026/05/27
 */
@Getter
@AllArgsConstructor
public enum AuthDataScopeEnum implements IEnum<String> {

    /**
     * 全部数据。
     */
    ALL("ALL", "全部数据"),

    /**
     * 仅本人数据。
     */
    SELF("SELF", "仅本人数据"),

    /**
     * 本部门数据。
     */
    DEPT("DEPT", "本部门数据"),

    /**
     * 本部门及下级部门数据。
     */
    DEPT_TREE("DEPT_TREE", "本部门及下级部门数据"),

    /**
     * 自定义部门数据。
     */
    CUSTOM("CUSTOM", "自定义部门数据");

    /**
     * 数据库存储值。
     */
    private final String value;

    /**
     * 展示说明。
     */
    private final String desc;
}
