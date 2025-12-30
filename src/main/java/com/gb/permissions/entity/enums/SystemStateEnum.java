package com.gb.permissions.entity.enums;

import com.baomidou.mybatisplus.annotation.IEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Created with IntelliJ IDEA.
 * @author:     	孙凯伦
 * @since:   	    2021-10-21 01:59:43
 * @description:	TODO  系统表类型枚举
 * @source:  	    代码生成器
 */
@Getter
@AllArgsConstructor
public enum SystemStateEnum implements IEnum<Integer> {

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
    public static SystemStateEnum getSystemStateEnum(Integer value) {
        for (SystemStateEnum systemStateEnum : SystemStateEnum.values()) {
            if (systemStateEnum.getValue().equals(value)) {
                return systemStateEnum;
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
        for (SystemStateEnum systemStateEnum : SystemStateEnum.values()) {
            if (systemStateEnum.getValue().equals(value)) {
                return systemStateEnum.getDesc();
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
        for (SystemStateEnum systemStateEnum : SystemStateEnum.values()) {
            if (systemStateEnum.getDesc().equals(desc)) {
                return systemStateEnum.getValue();
            }
        }
        return null;
    }
}