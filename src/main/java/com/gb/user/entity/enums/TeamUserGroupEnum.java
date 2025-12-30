package com.gb.user.entity.enums;

import com.baomidou.mybatisplus.annotation.IEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;


/**
 * TODO 团队人员类型枚举
 * 代码生成器
 *
 * @author sunx
 * @className TeamUserGroupEnum
 * @time 2022-08-30 04:44:18
 */
@Getter
@AllArgsConstructor
public enum TeamUserGroupEnum implements IEnum<Integer> {
    // 服务组
    服务组(0, "服务组"),
    // 自营组
    自营组(1, "自营组"),
    // 合伙人组
    合伙人组(2, "合伙人组"),
    ;

    private Integer value;
    private String desc;


    /**
     * TODO 通过value获得枚举
     *
     * @param value
     * @return TeamUserGroupEnum
     * @author sunx
     * @methodName getTeamUserGroupEnum
     * @time 2022-08-30 04:44:18
     */
    public static TeamUserGroupEnum getTeamUserGroupEnum(Integer value) {
        for (TeamUserGroupEnum teamUserGroupEnum : TeamUserGroupEnum.values()) {
            if (teamUserGroupEnum.getValue().equals(value)) {
                return teamUserGroupEnum;
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
        for (TeamUserGroupEnum teamUserGroupEnum : TeamUserGroupEnum.values()) {
            if (teamUserGroupEnum.getValue().equals(value)) {
                return teamUserGroupEnum.getDesc();
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
        for (TeamUserGroupEnum teamUserGroupEnum : TeamUserGroupEnum.values()) {
            if (teamUserGroupEnum.getDesc().equals(desc)) {
                return teamUserGroupEnum.getValue();
            }
        }
        return null;
    }
}