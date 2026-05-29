package com.kellen.auth.entity.enums;

import com.baomidou.mybatisplus.annotation.IEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 管理员分类。
 *
 * @author sunkailun
 * @className AuthAdminTypeEnum
 * @time 2026/05/29
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

    public static String getDesc(String value) {
        for (AuthAdminTypeEnum item : AuthAdminTypeEnum.values()) {
            if (item.getValue().equals(value)) {
                return item.getDesc();
            }
        }
        return null;
    }
}
