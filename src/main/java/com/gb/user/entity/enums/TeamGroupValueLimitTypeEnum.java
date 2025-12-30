package com.gb.user.entity.enums;

import com.baomidou.mybatisplus.annotation.IEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;


/**
 * TODO 团队组别限制类型枚举
 * 代码生成器
 *
 * @author sunx
 * @className TeamGroupValueLimitTypeEnum
 * @time 2022-08-31 10:59:01
 */
@Getter
@AllArgsConstructor
public enum TeamGroupValueLimitTypeEnum implements IEnum<Integer> {
    // 默认
    默认(0, "默认"),
    ;

    private Integer value;
    private String desc;


    /**
     * TODO 通过value获得枚举
     *
     * @param value
     * @return TeamGroupValueLimitTypeEnum
     * @author sunx
     * @methodName getTeamGroupValueLimitTypeEnum
     * @time 2022-08-31 10:59:01
     */
    public static TeamGroupValueLimitTypeEnum getTeamGroupValueLimitTypeEnum(Integer value) {
        for (TeamGroupValueLimitTypeEnum teamGroupValueLimitTypeEnum : TeamGroupValueLimitTypeEnum.values()) {
            if (teamGroupValueLimitTypeEnum.getValue().equals(value)) {
                return teamGroupValueLimitTypeEnum;
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
        for (TeamGroupValueLimitTypeEnum teamGroupValueLimitTypeEnum : TeamGroupValueLimitTypeEnum.values()) {
            if (teamGroupValueLimitTypeEnum.getValue().equals(value)) {
                return teamGroupValueLimitTypeEnum.getDesc();
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
        for (TeamGroupValueLimitTypeEnum teamGroupValueLimitTypeEnum : TeamGroupValueLimitTypeEnum.values()) {
            if (teamGroupValueLimitTypeEnum.getDesc().equals(desc)) {
                return teamGroupValueLimitTypeEnum.getValue();
            }
        }
        return null;
    }
}