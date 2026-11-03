package com.kellen.auth.entity.enums;

import com.baomidou.mybatisplus.annotation.IEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 管理员分类。
 *
 * @author sunkailun
 */
@Getter
@AllArgsConstructor
public enum AuthAdminTypeEnum implements IEnum<String> {

    /**
     * 平台超级管理员。
     */
    PLATFORM_SUPER_ADMIN("PLATFORM_SUPER_ADMIN", "平台超级管理员"),

    /**
     * 租户管理员。
     */
    TENANT_ADMIN("TENANT_ADMIN", "租户管理员");

    private final String value;

    private final String desc;

    /**
     * 根据数据库枚举值获取前端展示说明。
     *
     * @param value 数据库存储的管理员分类值
     * @return 管理员分类说明；未匹配时返回 null
     */
    public static String getDesc(String value) {
        for (AuthAdminTypeEnum item : AuthAdminTypeEnum.values()) {
            if (item.getValue().equals(value)) {
                return item.getDesc();
            }
        }
        return null;
    }
}
