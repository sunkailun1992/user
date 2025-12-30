package com.gb.user.entity.enums;

import com.baomidou.mybatisplus.annotation.IEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;


/**
 * TODO 机构用户关联类型枚举
 * 代码生成器
 *
 * @author sunxin
 * @className InstitutionsUserStateEnum
 * @time 2022-07-04 10:48:37
 */
@Getter
@AllArgsConstructor
public enum InstitutionsUserStateEnum implements IEnum<Integer> {
    // 在职
    在职(0, "在职"),
    // 离职
    离职(1, "离职"),
    // 待入职
    待入职(2, "待入职"),
    ;

    private Integer value;
    private String desc;


    /**
     * TODO 通过value获得枚举
     *
     * @param value
     * @return InstitutionsUserStateEnum
     * @author sunxin
     * @methodName getInstitutionsUserStateEnum
     * @time 2022-07-04 10:48:37
     */
    public static InstitutionsUserStateEnum getInstitutionsUserStateEnum(Integer value) {
        for (InstitutionsUserStateEnum institutionsUserStateEnum : InstitutionsUserStateEnum.values()) {
            if (institutionsUserStateEnum.getValue().equals(value)) {
                return institutionsUserStateEnum;
            }
        }
        return null;
    }


    /**
     * TODO 获得备注
     *
     * @param value
     * @return String
     * @author sunxin
     * @methodName getDesc
     * @time 2022-07-04 10:48:37
     */
    public static String getDesc(Integer value) {
        for (InstitutionsUserStateEnum institutionsUserStateEnum : InstitutionsUserStateEnum.values()) {
            if (institutionsUserStateEnum.getValue().equals(value)) {
                return institutionsUserStateEnum.getDesc();
            }
        }
        return null;
    }


    /**
     * TODO 获得值
     *
     * @param desc
     * @return Integer
     * @author sunxin
     * @methodName getDesc
     * @time 2022-07-04 10:48:37
     */
    public static Integer getDesc(String desc) {
        for (InstitutionsUserStateEnum institutionsUserStateEnum : InstitutionsUserStateEnum.values()) {
            if (institutionsUserStateEnum.getDesc().equals(desc)) {
                return institutionsUserStateEnum.getValue();
            }
        }
        return null;
    }
}