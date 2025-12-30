package com.gb.account.entity.enums;

import com.baomidou.mybatisplus.annotation.IEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Created with IntelliJ IDEA.
 * @author:     	孙凯伦
 * @since:   	    2021-10-21 01:50:37
 * @description:	TODO  用户授权表类型枚举
 * @source:  	    代码生成器
 */
@Getter
@AllArgsConstructor
public enum UserOauthsTypeEnum implements IEnum<Integer> {

    /**
     * 微信
     */
    微信(0, "微信"),

    /**
     * QQ
     */
    qq(1, "qq"),

    /**
     * 微博
     */
    微博(2, "微博"),

    /**
     * IOS
     */
    IOS(3, "IOS"),

    /**
     * 安卓
     */
    android(4, "android"),
    ;

    /**
     * 值
     */
    private Integer value;

    /**
     * 描述
     */
    private String desc;


    /**
     * 通过value获得枚举
     *
     * @param value
     * @return
     */
    public static UserOauthsTypeEnum getUserOauthsTypeEnum(Integer value) {
        for (UserOauthsTypeEnum userOauthsTypeEnum : UserOauthsTypeEnum.values()) {
            if (userOauthsTypeEnum.getValue().equals(value)) {
                return userOauthsTypeEnum;
            }
        }
        return null;
    }


    /**
     * 获得备注
     *
     * @param value
     * @return
     */
    public static String getDesc(Integer value) {
        for (UserOauthsTypeEnum userOauthsTypeEnum : UserOauthsTypeEnum.values()) {
            if (userOauthsTypeEnum.getValue().equals(value)) {
                return userOauthsTypeEnum.getDesc();
            }
        }
        return null;
    }


    /**
     * 获得值
     *
     * @param desc
     * @return
     */
    public static Integer getDesc(String desc) {
        for (UserOauthsTypeEnum userOauthsTypeEnum : UserOauthsTypeEnum.values()) {
            if (userOauthsTypeEnum.getDesc().equals(desc)) {
                return userOauthsTypeEnum.getValue();
            }
        }
        return null;
    }
}