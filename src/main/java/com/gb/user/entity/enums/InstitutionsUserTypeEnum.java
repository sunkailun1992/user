package com.gb.user.entity.enums;

import com.baomidou.mybatisplus.annotation.IEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;


/**
 * TODO 机构用户关联类型枚举
 * 代码生成器
 *
 * @author sunxin
 * @className InstitutionsUserTypeEnum
 * @time 2022-07-04 10:48:37
 */
@Getter
@AllArgsConstructor
public enum InstitutionsUserTypeEnum implements IEnum<Integer> {
    // 默认
    默认(0, "默认"),
    ;

    private Integer value;
    private String desc;


    /**
     * TODO 通过value获得枚举
     *
     * @param value
     * @return InstitutionsUserTypeEnum
     * @author sunxin
     * @methodName getInstitutionsUserTypeEnum
     * @time 2022-07-04 10:48:37
     */
    public static InstitutionsUserTypeEnum getInstitutionsUserTypeEnum(Integer value) {
        for (InstitutionsUserTypeEnum institutionsUserTypeEnum : InstitutionsUserTypeEnum.values()) {
            if (institutionsUserTypeEnum.getValue().equals(value)) {
                return institutionsUserTypeEnum;
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
        for (InstitutionsUserTypeEnum institutionsUserTypeEnum : InstitutionsUserTypeEnum.values()) {
            if (institutionsUserTypeEnum.getValue().equals(value)) {
                return institutionsUserTypeEnum.getDesc();
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
        for (InstitutionsUserTypeEnum institutionsUserTypeEnum : InstitutionsUserTypeEnum.values()) {
            if (institutionsUserTypeEnum.getDesc().equals(desc)) {
                return institutionsUserTypeEnum.getValue();
            }
        }
        return null;
    }
}