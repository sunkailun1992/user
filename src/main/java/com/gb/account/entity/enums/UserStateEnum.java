package com.gb.account.entity.enums;

import com.baomidou.mybatisplus.annotation.IEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Created with IntelliJ IDEA.
 * @author:     	孙凯伦
 * @since:   	    2021-10-21 11:06:07
 * @description:	TODO  用户表类型枚举--状态（0：正常，1：注销）
 * @source:  	    代码生成器
 */
@Getter
@AllArgsConstructor
public enum UserStateEnum implements IEnum<Integer> {

    /**
     * 默认值
     */
    正常(0, "正常"),
    注销(1, "注销"),
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
    public static UserStateEnum getUserStateEnum(Integer value) {
        for (UserStateEnum userStateEnum : UserStateEnum.values()) {
            if (userStateEnum.getValue().equals(value)) {
                return userStateEnum;
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
        for (UserStateEnum userStateEnum : UserStateEnum.values()) {
            if (userStateEnum.getValue().equals(value)) {
                return userStateEnum.getDesc();
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
        for (UserStateEnum userStateEnum : UserStateEnum.values()) {
            if (userStateEnum.getDesc().equals(desc)) {
                return userStateEnum.getValue();
            }
        }
        return null;
    }
}