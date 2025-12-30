package com.gb.account.entity.enums;

import com.baomidou.mybatisplus.annotation.IEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Created with IntelliJ IDEA.
 * @author:     	孙凯伦
 * @since:   	    2021-10-21 01:49:03
 * @description:	TODO  用户类型表类型枚举
 * @source:  	    代码生成器
 */
@Getter
@AllArgsConstructor
public enum UserTypeStateEnum implements IEnum<Integer> {

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
    public static UserTypeStateEnum getUserTypeStateEnum(Integer value) {
        for (UserTypeStateEnum userTypeStateEnum : UserTypeStateEnum.values()) {
            if (userTypeStateEnum.getValue().equals(value)) {
                return userTypeStateEnum;
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
        for (UserTypeStateEnum userTypeStateEnum : UserTypeStateEnum.values()) {
            if (userTypeStateEnum.getValue().equals(value)) {
                return userTypeStateEnum.getDesc();
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
        for (UserTypeStateEnum userTypeStateEnum : UserTypeStateEnum.values()) {
            if (userTypeStateEnum.getDesc().equals(desc)) {
                return userTypeStateEnum.getValue();
            }
        }
        return null;
    }
}