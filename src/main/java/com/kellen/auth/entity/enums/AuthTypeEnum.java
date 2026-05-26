package com.kellen.auth.entity.enums;

import com.baomidou.mybatisplus.annotation.IEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 认证数据类型枚举
 *
 * @author sunkailun
 * @className AuthTypeEnum
 * @time 2026/05/25
 */
@Getter
@AllArgsConstructor
public enum AuthTypeEnum implements IEnum<Integer> {

    /**
     * 默认
     */
    默认(0, "默认");

    private final Integer value;

    private final String desc;

    /**
     * 通过value获得枚举。
     *
     * @param value 枚举值
     * @return AuthTypeEnum
     */
    public static AuthTypeEnum getAuthTypeEnum(Integer value) {
        for (AuthTypeEnum authTypeEnum : AuthTypeEnum.values()) {
            if (authTypeEnum.getValue().equals(value)) {
                return authTypeEnum;
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
        AuthTypeEnum authTypeEnum = getAuthTypeEnum(value);
        return authTypeEnum == null ? null : authTypeEnum.getDesc();
    }
}
