package com.gb.user.entity.enums;

import com.baomidou.mybatisplus.annotation.IEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;


/**
 * TODO 团队类型枚举
 * 代码生成器
 *
 * @author sunx
 * @className TeamTypeEnum
 * @time 2022-08-30 04:44:17
 */
@Getter
@AllArgsConstructor
public enum TeamTypeEnum implements IEnum<Integer> {
    // 默认
    默认(0, "默认"),
    ;

    private Integer value;
    private String desc;


    /**
     * TODO 通过value获得枚举
     *
     * @param value
     * @return TeamTypeEnum
     * @author sunx
     * @methodName getTeamTypeEnum
     * @time 2022-08-30 04:44:17
     */
    public static TeamTypeEnum getTeamTypeEnum(Integer value) {
        for (TeamTypeEnum teamTypeEnum : TeamTypeEnum.values()) {
            if (teamTypeEnum.getValue().equals(value)) {
                return teamTypeEnum;
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
        for (TeamTypeEnum teamTypeEnum : TeamTypeEnum.values()) {
            if (teamTypeEnum.getValue().equals(value)) {
                return teamTypeEnum.getDesc();
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
        for (TeamTypeEnum teamTypeEnum : TeamTypeEnum.values()) {
            if (teamTypeEnum.getDesc().equals(desc)) {
                return teamTypeEnum.getValue();
            }
        }
        return null;
    }
}