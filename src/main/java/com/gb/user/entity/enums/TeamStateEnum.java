package com.gb.user.entity.enums;

import com.baomidou.mybatisplus.annotation.IEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;


/**
 * TODO 团队类型枚举
 * 代码生成器
 *
 * @author sunx
 * @className TeamStateEnum
 * @time 2022-08-30 04:44:17
 */
@Getter
@AllArgsConstructor
public enum TeamStateEnum implements IEnum<Integer> {
    // 默认
    默认(0, "默认"),
    ;

    private Integer value;
    private String desc;


    /**
     * TODO 通过value获得枚举
     *
     * @param value
     * @return TeamStateEnum
     * @author sunx
     * @methodName getTeamStateEnum
     * @time 2022-08-30 04:44:17
     */
    public static TeamStateEnum getTeamStateEnum(Integer value) {
        for (TeamStateEnum teamStateEnum : TeamStateEnum.values()) {
            if (teamStateEnum.getValue().equals(value)) {
                return teamStateEnum;
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
     * @time 2022-08-30 04:44:17
     */
    public static String getDesc(Integer value) {
        for (TeamStateEnum teamStateEnum : TeamStateEnum.values()) {
            if (teamStateEnum.getValue().equals(value)) {
                return teamStateEnum.getDesc();
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
     * @time 2022-08-30 04:44:17
     */
    public static Integer getDesc(String desc) {
        for (TeamStateEnum teamStateEnum : TeamStateEnum.values()) {
            if (teamStateEnum.getDesc().equals(desc)) {
                return teamStateEnum.getValue();
            }
        }
        return null;
    }
}