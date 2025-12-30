package com.gb.user.entity.enums;

import com.baomidou.mybatisplus.annotation.IEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;


/**
 * TODO 团队组别类型枚举
 * 代码生成器
 *
 * @author sunx
 * @className TeamGroupStateEnum
 * @time 2022-08-31 10:59:01
 */
@Getter
@AllArgsConstructor
public enum TeamGroupStateEnum implements IEnum<Integer> {
    // 默认
    默认(0, "默认"),
    ;

    private Integer value;
    private String desc;


    /**
     * TODO 通过value获得枚举
     *
     * @param value
     * @return TeamGroupStateEnum
     * @author sunx
     * @methodName getTeamGroupStateEnum
     * @time 2022-08-31 10:59:01
     */
    public static TeamGroupStateEnum getTeamGroupStateEnum(Integer value) {
        for (TeamGroupStateEnum teamGroupStateEnum : TeamGroupStateEnum.values()) {
            if (teamGroupStateEnum.getValue().equals(value)) {
                return teamGroupStateEnum;
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
        for (TeamGroupStateEnum teamGroupStateEnum : TeamGroupStateEnum.values()) {
            if (teamGroupStateEnum.getValue().equals(value)) {
                return teamGroupStateEnum.getDesc();
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
        for (TeamGroupStateEnum teamGroupStateEnum : TeamGroupStateEnum.values()) {
            if (teamGroupStateEnum.getDesc().equals(desc)) {
                return teamGroupStateEnum.getValue();
            }
        }
        return null;
    }
}