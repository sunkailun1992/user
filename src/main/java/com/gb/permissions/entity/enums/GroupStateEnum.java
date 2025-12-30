package com.gb.permissions.entity.enums;

import com.baomidou.mybatisplus.annotation.IEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Created with IntelliJ IDEA.
 * @author:     	孙凯伦
 * @since:   	    2021-10-21 01:59:44
 * @description:	TODO  组类型枚举
 * @source:  	    代码生成器
 */
@Getter
@AllArgsConstructor
public enum GroupStateEnum implements IEnum<Integer> {

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
    public static GroupStateEnum getGroupStateEnum(Integer value) {
        for (GroupStateEnum groupStateEnum : GroupStateEnum.values()) {
            if (groupStateEnum.getValue().equals(value)) {
                return groupStateEnum;
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
        for (GroupStateEnum groupStateEnum : GroupStateEnum.values()) {
            if (groupStateEnum.getValue().equals(value)) {
                return groupStateEnum.getDesc();
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
        for (GroupStateEnum groupStateEnum : GroupStateEnum.values()) {
            if (groupStateEnum.getDesc().equals(desc)) {
                return groupStateEnum.getValue();
            }
        }
        return null;
    }
}