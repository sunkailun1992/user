package com.gb.user.entity.enums;

import com.baomidou.mybatisplus.annotation.IEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;


/**
 * TODO 团队组别值类型枚举
 * 代码生成器
 *
 * @author sunx
 * @className TeamGroupValueStateEnum
 * @time 2022-08-31 10:59:01
 */
@Getter
@AllArgsConstructor
public enum TeamGroupValueStateEnum implements IEnum<Integer> {
    // 默认
    默认(0, "默认"),
    ;

    private Integer value;
    private String desc;


    /**
     * TODO 通过value获得枚举
     *
     * @param value
     * @return TeamGroupValueStateEnum
     * @author sunx
     * @methodName getTeamGroupValueStateEnum
     * @time 2022-08-31 10:59:01
     */
    public static TeamGroupValueStateEnum getTeamGroupValueStateEnum(Integer value) {
        for (TeamGroupValueStateEnum teamGroupValueStateEnum : TeamGroupValueStateEnum.values()) {
            if (teamGroupValueStateEnum.getValue().equals(value)) {
                return teamGroupValueStateEnum;
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
        for (TeamGroupValueStateEnum teamGroupValueStateEnum : TeamGroupValueStateEnum.values()) {
            if (teamGroupValueStateEnum.getValue().equals(value)) {
                return teamGroupValueStateEnum.getDesc();
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
        for (TeamGroupValueStateEnum teamGroupValueStateEnum : TeamGroupValueStateEnum.values()) {
            if (teamGroupValueStateEnum.getDesc().equals(desc)) {
                return teamGroupValueStateEnum.getValue();
            }
        }
        return null;
    }
}