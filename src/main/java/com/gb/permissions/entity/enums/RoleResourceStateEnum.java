package com.gb.permissions.entity.enums;

import com.baomidou.mybatisplus.annotation.IEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Created with IntelliJ IDEA.
 * @author:     	孙凯伦
 * @since:   	    2021-10-21 01:59:42
 * @description:	TODO  角色资源表类型枚举
 * @source:  	    代码生成器
 */
@Getter
@AllArgsConstructor
public enum RoleResourceStateEnum implements IEnum<Integer> {

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
    public static RoleResourceStateEnum getRoleResourceStateEnum(Integer value) {
        for (RoleResourceStateEnum roleResourceStateEnum : RoleResourceStateEnum.values()) {
            if (roleResourceStateEnum.getValue().equals(value)) {
                return roleResourceStateEnum;
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
        for (RoleResourceStateEnum roleResourceStateEnum : RoleResourceStateEnum.values()) {
            if (roleResourceStateEnum.getValue().equals(value)) {
                return roleResourceStateEnum.getDesc();
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
        for (RoleResourceStateEnum roleResourceStateEnum : RoleResourceStateEnum.values()) {
            if (roleResourceStateEnum.getDesc().equals(desc)) {
                return roleResourceStateEnum.getValue();
            }
        }
        return null;
    }
}