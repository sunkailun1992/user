package com.gb.account.entity.enums;

import com.baomidou.mybatisplus.annotation.IEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Created with IntelliJ IDEA.
 * @author:     	孙凯伦
 * @since:   	    2021-10-21 01:49:04
 * @description:	TODO  用户类型值表类型枚举
 * @source:  	    代码生成器
 */
@Getter
@AllArgsConstructor
public enum UserTypeValueTypeEnum implements IEnum<Integer> {

    /**
     * 默认值
     */
    默认(0, "默认"),
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
    public static UserTypeValueTypeEnum getUserTypeValueTypeEnum(Integer value) {
        for (UserTypeValueTypeEnum userTypeValueTypeEnum : UserTypeValueTypeEnum.values()) {
            if (userTypeValueTypeEnum.getValue().equals(value)) {
                return userTypeValueTypeEnum;
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
        for (UserTypeValueTypeEnum userTypeValueTypeEnum : UserTypeValueTypeEnum.values()) {
            if (userTypeValueTypeEnum.getValue().equals(value)) {
                return userTypeValueTypeEnum.getDesc();
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
        for (UserTypeValueTypeEnum userTypeValueTypeEnum : UserTypeValueTypeEnum.values()) {
            if (userTypeValueTypeEnum.getDesc().equals(desc)) {
                return userTypeValueTypeEnum.getValue();
            }
        }
        return null;
    }
}