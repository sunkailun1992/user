package com.gb.user.entity.enums;

import com.baomidou.mybatisplus.annotation.IEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;


/**
 * TODO 团队人员类型枚举
 * 代码生成器
 *
 * @author sunx
 * @className TeamUserStateEnum
 * @time 2022-08-31 11:01:59
 */
@Getter
@AllArgsConstructor
public enum TeamUserStateEnum implements IEnum<Integer> {
    // 默认
    默认(0, "默认"),
    ;

    private Integer value;
    private String desc;


    /**
     * TODO 通过value获得枚举
     *
     * @param value
     * @return TeamUserStateEnum
     * @author sunx
     * @methodName getTeamUserStateEnum
     * @time 2022-08-31 11:01:59
     */
    public static TeamUserStateEnum getTeamUserStateEnum(Integer value) {
        for (TeamUserStateEnum teamUserStateEnum : TeamUserStateEnum.values()) {
            if (teamUserStateEnum.getValue().equals(value)) {
                return teamUserStateEnum;
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
     * @time 2022-08-31 11:01:59
     */
    public static String getDesc(Integer value) {
        for (TeamUserStateEnum teamUserStateEnum : TeamUserStateEnum.values()) {
            if (teamUserStateEnum.getValue().equals(value)) {
                return teamUserStateEnum.getDesc();
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
     * @time 2022-08-31 11:01:59
     */
    public static Integer getDesc(String desc) {
        for (TeamUserStateEnum teamUserStateEnum : TeamUserStateEnum.values()) {
            if (teamUserStateEnum.getDesc().equals(desc)) {
                return teamUserStateEnum.getValue();
            }
        }
        return null;
    }
}