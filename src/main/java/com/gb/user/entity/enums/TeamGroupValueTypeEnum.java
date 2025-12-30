package com.gb.user.entity.enums;

import com.baomidou.mybatisplus.annotation.IEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;


/**
 * TODO 团队组别值类型枚举
 * 代码生成器
 *
 * @author sunx
 * @className TeamGroupValueTypeEnum
 * @time 2022-08-31 10:59:01
 */
@Getter
@AllArgsConstructor
public enum TeamGroupValueTypeEnum implements IEnum<Integer> {
    // 默认
    默认(0, "默认"),
    ;

    private Integer value;
    private String desc;


    /**
     * TODO 通过value获得枚举
     *
     * @param value
     * @return TeamGroupValueTypeEnum
     * @author sunx
     * @methodName getTeamGroupValueTypeEnum
     * @time 2022-08-31 10:59:01
     */
    public static TeamGroupValueTypeEnum getTeamGroupValueTypeEnum(Integer value) {
        for (TeamGroupValueTypeEnum teamGroupValueTypeEnum : TeamGroupValueTypeEnum.values()) {
            if (teamGroupValueTypeEnum.getValue().equals(value)) {
                return teamGroupValueTypeEnum;
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
        for (TeamGroupValueTypeEnum teamGroupValueTypeEnum : TeamGroupValueTypeEnum.values()) {
            if (teamGroupValueTypeEnum.getValue().equals(value)) {
                return teamGroupValueTypeEnum.getDesc();
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
        for (TeamGroupValueTypeEnum teamGroupValueTypeEnum : TeamGroupValueTypeEnum.values()) {
            if (teamGroupValueTypeEnum.getDesc().equals(desc)) {
                return teamGroupValueTypeEnum.getValue();
            }
        }
        return null;
    }
}