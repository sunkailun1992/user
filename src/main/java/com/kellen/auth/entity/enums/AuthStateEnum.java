package com.kellen.auth.entity.enums;

import com.baomidou.mybatisplus.annotation.IEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 认证数据状态枚举
 *
 * @author sunkailun
 * @className AuthStateEnum
 * @time 2026/05/25
 */
@Getter
@AllArgsConstructor
public enum AuthStateEnum implements IEnum<Integer> {

    /**
     * 默认
     */
    默认(0, "默认"),

    /**
     * 启用
     */
    启用(1, "启用"),

    /**
     * 禁用
     */
    禁用(2, "禁用");

    private final Integer value;

    private final String desc;

    /**
     * 通过value获得枚举。
     *
     * @param value 枚举值
     * @return AuthStateEnum
     */
    public static AuthStateEnum getAuthStateEnum(Integer value) {
        for (AuthStateEnum authStateEnum : AuthStateEnum.values()) {
            if (authStateEnum.getValue().equals(value)) {
                return authStateEnum;
            }
        }
        return null;
    }

    /**
     * 获得备注。
     *
     * @param value 枚举值
     * @return String
     */
    public static String getDesc(Integer value) {
        AuthStateEnum authStateEnum = getAuthStateEnum(value);
        return authStateEnum == null ? null : authStateEnum.getDesc();
    }
}
