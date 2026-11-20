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

    /**
     * 角色编码是否与管理员分类重名（忽略大小写与首尾空白）。业务上下文把角色拼成 {@code ROLE_<角色编码>}、把管理员分类拼成
     * {@code ROLE_<分类>}，重名的角色会让持有者带上管理员分类的角色，所以这些名字只留给管理员分类。
     *
     * @param roleCode 角色编码
     * @return true 表示是保留名
     */
    public static boolean reservesRoleCode(String roleCode) {
        if (roleCode == null) {
            return false;
        }
        String code = roleCode.trim();
        for (AuthAdminTypeEnum item : AuthAdminTypeEnum.values()) {
            if (item.getValue().equalsIgnoreCase(code)) {
                return true;
            }
        }
        return false;
    }
}
