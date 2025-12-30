package com.gb.user.enums;

import com.alibaba.druid.util.StringUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.Optional;

/**
 * 用户标签信息枚举类
 *
 * @author: sunx
 * @Date: 2021/11/11 14:25
 * @descript: 转发处理枚举
 */
@Getter
@AllArgsConstructor
@SuppressWarnings("all")
@Slf4j
public enum UserTypeInfoEnum {
    经纪人("1"),
    用户("2"),
    保险公司("3"),
    管家("4");

    private String code;

    /**
     * 根据标签code查询标签枚举类
     * @param code 标签code
     * @return UserTypeInfoEnum
     */
    public static UserTypeInfoEnum getTypeInfoEnum(String code) {
        if(null == code) {
            log.debug("查询标签枚举类，请求参数code不能为空！");
            return null;
        }
        Optional<UserTypeInfoEnum> userTypeInfoEnum = Arrays.stream(values()).filter(x-> StringUtils.equals(x.getCode(), code)).findFirst();
        return userTypeInfoEnum.orElse(null);
    }
}
