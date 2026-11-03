package com.kellen.auth.entity.enums;

import com.baomidou.mybatisplus.annotation.IEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 认证资源分类枚举
 *
 * @author sunkailun
 * @className AuthResourceCategoryEnum
 * @time 2026/05/25
 */
@Getter
@AllArgsConstructor
public enum AuthResourceCategoryEnum implements IEnum<String> {

    /**
     * 前端资源
     */
    FRONTEND("FRONTEND", "前端资源"),

    /**
     * 后端接口
     */
    BACKEND("BACKEND", "后端接口");

    private final String value;

    private final String desc;

    /**
     * 通过value获得枚举。
     *
     * @param value 枚举值
     * @return AuthResourceCategoryEnum
     */
    public static AuthResourceCategoryEnum getAuthResourceCategoryEnum(String value) {
        for (AuthResourceCategoryEnum authResourceCategoryEnum : AuthResourceCategoryEnum.values()) {
            if (authResourceCategoryEnum.getValue().equals(value)) {
                return authResourceCategoryEnum;
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
    public static String getDesc(String value) {
        AuthResourceCategoryEnum authResourceCategoryEnum = getAuthResourceCategoryEnum(value);
        return authResourceCategoryEnum == null ? null : authResourceCategoryEnum.getDesc();
    }
}
