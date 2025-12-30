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
public enum UserTypeValueStateEnum implements IEnum<Integer> {

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
    public static UserTypeValueStateEnum getUserTypeValueStateEnum(Integer value) {
        for (UserTypeValueStateEnum userTypeValueStateEnum : UserTypeValueStateEnum.values()) {
            if (userTypeValueStateEnum.getValue().equals(value)) {
                return userTypeValueStateEnum;
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
        for (UserTypeValueStateEnum userTypeValueStateEnum : UserTypeValueStateEnum.values()) {
            if (userTypeValueStateEnum.getValue().equals(value)) {
                return userTypeValueStateEnum.getDesc();
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
        for (UserTypeValueStateEnum userTypeValueStateEnum : UserTypeValueStateEnum.values()) {
            if (userTypeValueStateEnum.getDesc().equals(desc)) {
                return userTypeValueStateEnum.getValue();
            }
        }
        return null;
    }
}