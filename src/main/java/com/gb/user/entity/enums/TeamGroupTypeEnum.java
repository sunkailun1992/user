package com.gb.user.entity.enums;

import com.baomidou.mybatisplus.annotation.IEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;


/**
 * TODO 团队组别类型枚举
 * 代码生成器
 *
 * @author sunx
 * @className TeamGroupTypeEnum
 * @time 2022-08-31 10:59:01
 */
@Getter
@AllArgsConstructor
public enum TeamGroupTypeEnum implements IEnum<Integer> {
    // 默认
    默认(0, "默认"),
    ;

    private Integer value;
    private String desc;


    /**
     * TODO 通过value获得枚举
     *
     * @param value
     * @return TeamGroupTypeEnum
     * @author sunx
     * @methodName getTeamGroupTypeEnum
     * @time 2022-08-31 10:59:01
     */
    public static TeamGroupTypeEnum getTeamGroupTypeEnum(Integer value) {
        for (TeamGroupTypeEnum teamGroupTypeEnum : TeamGroupTypeEnum.values()) {
            if (teamGroupTypeEnum.getValue().equals(value)) {
                return teamGroupTypeEnum;
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
        for (TeamGroupTypeEnum teamGroupTypeEnum : TeamGroupTypeEnum.values()) {
            if (teamGroupTypeEnum.getValue().equals(value)) {
                return teamGroupTypeEnum.getDesc();
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
        for (TeamGroupTypeEnum teamGroupTypeEnum : TeamGroupTypeEnum.values()) {
            if (teamGroupTypeEnum.getDesc().equals(desc)) {
                return teamGroupTypeEnum.getValue();
            }
        }
        return null;
    }
}