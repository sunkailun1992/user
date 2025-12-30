package com.gb.permissions.entity.enums;

import com.baomidou.mybatisplus.annotation.IEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Created with IntelliJ IDEA.
 * @author:     	孙凯伦
 * @since:   	    2021-10-21 01:59:43
 * @description:	TODO  角色表类型枚举
 * @source:  	    代码生成器
 */
@Getter
@AllArgsConstructor
public enum RoleStateEnum implements IEnum<Integer> {

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
    public static RoleStateEnum getRoleStateEnum(Integer value) {
        for (RoleStateEnum roleStateEnum : RoleStateEnum.values()) {
            if (roleStateEnum.getValue().equals(value)) {
                return roleStateEnum;
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
        for (RoleStateEnum roleStateEnum : RoleStateEnum.values()) {
            if (roleStateEnum.getValue().equals(value)) {
                return roleStateEnum.getDesc();
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
        for (RoleStateEnum roleStateEnum : RoleStateEnum.values()) {
            if (roleStateEnum.getDesc().equals(desc)) {
                return roleStateEnum.getValue();
            }
        }
        return null;
    }
}