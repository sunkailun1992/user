package com.gb.user.entity.enums;

import com.baomidou.mybatisplus.annotation.IEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;


/**
 * TODO 团队人员类型枚举
 * 代码生成器
 *
 * @author sunx
 * @className TeamUserJobsEnum
 * @time 2022-08-30 04:44:18
 */
@Getter
@AllArgsConstructor
public enum TeamUserJobsEnum implements IEnum<Integer> {
    // 总监
    总监(0, "总监"),
    // 经理
    经理(1, "经理"),
    // 专员
    专员(2, "专员"),
    ;

    private Integer value;
    private String desc;


    /**
     * TODO 通过value获得枚举
     *
     * @param value
     * @return TeamUserJobsEnum
     * @author sunx
     * @methodName getTeamUserJobsEnum
     * @time 2022-08-30 04:44:18
     */
    public static TeamUserJobsEnum getTeamUserJobsEnum(Integer value) {
        for (TeamUserJobsEnum teamUserJobsEnum : TeamUserJobsEnum.values()) {
            if (teamUserJobsEnum.getValue().equals(value)) {
                return teamUserJobsEnum;
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
     * @time 2022-08-30 04:44:18
     */
    public static String getDesc(Integer value) {
        for (TeamUserJobsEnum teamUserJobsEnum : TeamUserJobsEnum.values()) {
            if (teamUserJobsEnum.getValue().equals(value)) {
                return teamUserJobsEnum.getDesc();
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
     * @time 2022-08-30 04:44:18
     */
    public static Integer getDesc(String desc) {
        for (TeamUserJobsEnum teamUserJobsEnum : TeamUserJobsEnum.values()) {
            if (teamUserJobsEnum.getDesc().equals(desc)) {
                return teamUserJobsEnum.getValue();
            }
        }
        return null;
    }
}