package com.gb.user.entity.enums;

import com.baomidou.mybatisplus.annotation.IEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;


/**
 * TODO 团队人员类型枚举
 * 代码生成器
 *
 * @author sunx
 * @className TeamUserTypeEnum
 * @time 2022-08-31 11:01:59
 */
@Getter
@AllArgsConstructor
public enum TeamUserTypeEnum implements IEnum<Integer> {
    // 分销
    分销(0, "分销"),
    // 自营
    自营(1, "自营"),
    ;

    private Integer value;
    private String desc;


    /**
     * TODO 通过value获得枚举
     *
     * @param value
     * @return TeamUserTypeEnum
     * @author sunx
     * @methodName getTeamUserTypeEnum
     * @time 2022-08-31 11:01:59
     */
    public static TeamUserTypeEnum getTeamUserTypeEnum(Integer value) {
        for (TeamUserTypeEnum teamUserTypeEnum : TeamUserTypeEnum.values()) {
            if (teamUserTypeEnum.getValue().equals(value)) {
                return teamUserTypeEnum;
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
        for (TeamUserTypeEnum teamUserTypeEnum : TeamUserTypeEnum.values()) {
            if (teamUserTypeEnum.getValue().equals(value)) {
                return teamUserTypeEnum.getDesc();
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
        for (TeamUserTypeEnum teamUserTypeEnum : TeamUserTypeEnum.values()) {
            if (teamUserTypeEnum.getDesc().equals(desc)) {
                return teamUserTypeEnum.getValue();
            }
        }
        return null;
    }
}