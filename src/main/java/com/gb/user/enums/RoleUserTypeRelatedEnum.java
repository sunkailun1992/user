package com.gb.user.enums;

import io.seata.common.util.StringUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Optional;

/**
 * 角色用户标签关联枚举类
 *
 * @author: sunx
 * @Date: 2021/04/06 14:25
 * @descript:
 */
@Getter
@AllArgsConstructor
@SuppressWarnings("all")
public enum RoleUserTypeRelatedEnum {
    非正式经纪人("informalBroker","213"),
    正式经纪人("officialBroker", null),
    工保网普通用户角色("gbwOrdinaryRole","average_user"),
    工保网管理端角色("gbwBackendRole","management_user"),
    工保网保险公司角色("gbwInsurerRole","insurance_company"),
    工保网SCRM角色("scrmRole",null),
    自营客户管理角色("349540",null),
    分销客户管理角色("784123",null),

    G端平台数据("externalPlatformRole",null),
    保险开放平台普通用户角色("256789","288888"),
    ;
    private String roleCode;
    private String userTypeValueCode;

    /**
     * 根据角色code查询对应的RoleUserTypeRelatedEnum
     * @param roleCode 角色code
     * @return RoleUserTypeRelatedEnum
     */
    public static RoleUserTypeRelatedEnum getByRoleCode(String roleCode) {

        Optional<RoleUserTypeRelatedEnum> codeEnum = Arrays.stream(values()).filter(x -> StringUtils.equals(x.getRoleCode(), roleCode)).findFirst();
        return codeEnum.get();
    }
}
