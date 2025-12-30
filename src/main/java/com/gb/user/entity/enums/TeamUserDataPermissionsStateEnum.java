package com.gb.user.entity.enums;

import com.baomidou.mybatisplus.annotation.IEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;


/**
 * TODO 团队人员数据权限类型枚举
 * 代码生成器
 *
 * @author sunx
 * @className TeamUserDataPermissionsStateEnum
 * @time 2022-08-30 04:44:18
 */
@Getter
@AllArgsConstructor
public enum TeamUserDataPermissionsStateEnum implements IEnum<Integer> {
    // 默认
    默认(0, "默认"),
    ;

    private Integer value;
    private String desc;


    /**
     * TODO 通过value获得枚举
     *
     * @param value
     * @return TeamUserDataPermissionsStateEnum
     * @author sunx
     * @methodName getTeamUserDataPermissionsStateEnum
     * @time 2022-08-30 04:44:18
     */
    public static TeamUserDataPermissionsStateEnum getTeamUserDataPermissionsStateEnum(Integer value) {
        for (TeamUserDataPermissionsStateEnum teamUserDataPermissionsStateEnum : TeamUserDataPermissionsStateEnum.values()) {
            if (teamUserDataPermissionsStateEnum.getValue().equals(value)) {
                return teamUserDataPermissionsStateEnum;
            }
        }
        return null;
    }


    /**
     * TODO 获得备注
     *
     * @param value
     * @return String
     * @author sunx
     * @methodName getDesc
     * @time 2022-08-30 04:44:18
     */
    public static String getDesc(Integer value) {
        for (TeamUserDataPermissionsStateEnum teamUserDataPermissionsStateEnum : TeamUserDataPermissionsStateEnum.values()) {
            if (teamUserDataPermissionsStateEnum.getValue().equals(value)) {
                return teamUserDataPermissionsStateEnum.getDesc();
            }
        }
        return null;
    }


    /**
     * TODO 获得值
     *
     * @param desc
     * @return Integer
     * @author sunx
     * @methodName getDesc
     * @time 2022-08-30 04:44:18
     */
    public static Integer getDesc(String desc) {
        for (TeamUserDataPermissionsStateEnum teamUserDataPermissionsStateEnum : TeamUserDataPermissionsStateEnum.values()) {
            if (teamUserDataPermissionsStateEnum.getDesc().equals(desc)) {
                return teamUserDataPermissionsStateEnum.getValue();
            }
        }
        return null;
    }
}