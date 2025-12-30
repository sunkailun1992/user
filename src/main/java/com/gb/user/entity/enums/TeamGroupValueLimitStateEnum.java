package com.gb.user.entity.enums;

import com.baomidou.mybatisplus.annotation.IEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;


/**
 * TODO 团队组别限制类型枚举
 * 代码生成器
 *
 * @author sunx
 * @className TeamGroupValueLimitStateEnum
 * @time 2022-08-31 10:59:01
 */
@Getter
@AllArgsConstructor
public enum TeamGroupValueLimitStateEnum implements IEnum<Integer> {
    // 默认
    默认(0, "默认"),
    ;

    private Integer value;
    private String desc;


    /**
     * TODO 通过value获得枚举
     *
     * @param value
     * @return TeamGroupValueLimitStateEnum
     * @author sunx
     * @methodName getTeamGroupValueLimitStateEnum
     * @time 2022-08-31 10:59:01
     */
    public static TeamGroupValueLimitStateEnum getTeamGroupValueLimitStateEnum(Integer value) {
        for (TeamGroupValueLimitStateEnum teamGroupValueLimitStateEnum : TeamGroupValueLimitStateEnum.values()) {
            if (teamGroupValueLimitStateEnum.getValue().equals(value)) {
                return teamGroupValueLimitStateEnum;
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
     * @time 2022-08-31 10:59:01
     */
    public static String getDesc(Integer value) {
        for (TeamGroupValueLimitStateEnum teamGroupValueLimitStateEnum : TeamGroupValueLimitStateEnum.values()) {
            if (teamGroupValueLimitStateEnum.getValue().equals(value)) {
                return teamGroupValueLimitStateEnum.getDesc();
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
     * @time 2022-08-31 10:59:01
     */
    public static Integer getDesc(String desc) {
        for (TeamGroupValueLimitStateEnum teamGroupValueLimitStateEnum : TeamGroupValueLimitStateEnum.values()) {
            if (teamGroupValueLimitStateEnum.getDesc().equals(desc)) {
                return teamGroupValueLimitStateEnum.getValue();
            }
        }
        return null;
    }
}