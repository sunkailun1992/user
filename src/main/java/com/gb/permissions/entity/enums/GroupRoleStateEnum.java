package com.gb.permissions.entity.enums;

import com.baomidou.mybatisplus.annotation.IEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Created with IntelliJ IDEA.
 * @author:     	孙凯伦
 * @since:   	    2021-10-21 01:59:44
 * @description:	TODO  角色用户组类型枚举
 * @source:  	    代码生成器
 */
@Getter
@AllArgsConstructor
public enum GroupRoleStateEnum implements IEnum<Integer> {

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
    public static GroupRoleStateEnum getGroupRoleStateEnum(Integer value) {
        for (GroupRoleStateEnum groupRoleStateEnum : GroupRoleStateEnum.values()) {
            if (groupRoleStateEnum.getValue().equals(value)) {
                return groupRoleStateEnum;
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
        for (GroupRoleStateEnum groupRoleStateEnum : GroupRoleStateEnum.values()) {
            if (groupRoleStateEnum.getValue().equals(value)) {
                return groupRoleStateEnum.getDesc();
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
        for (GroupRoleStateEnum groupRoleStateEnum : GroupRoleStateEnum.values()) {
            if (groupRoleStateEnum.getDesc().equals(desc)) {
                return groupRoleStateEnum.getValue();
            }
        }
        return null;
    }
}