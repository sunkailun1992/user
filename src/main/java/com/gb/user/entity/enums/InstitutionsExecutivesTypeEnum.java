package com.gb.user.entity.enums;

import com.baomidou.mybatisplus.annotation.IEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;


/**
 * TODO 机构高管类型枚举
 * 代码生成器
 *
 * @author sunxin
 * @className InstitutionsExecutivesTypeEnum
 * @time 2022-07-04 10:48:36
 */
@Getter
@AllArgsConstructor
public enum InstitutionsExecutivesTypeEnum implements IEnum<Integer> {
    // 默认
    默认(0, "默认"),
    ;

    private Integer value;
    private String desc;


    /**
     * TODO 通过value获得枚举
     *
     * @param value
     * @return InstitutionsExecutivesTypeEnum
     * @author sunxin
     * @methodName getInstitutionsExecutivesTypeEnum
     * @time 2022-07-04 10:48:36
     */
    public static InstitutionsExecutivesTypeEnum getInstitutionsExecutivesTypeEnum(Integer value) {
        for (InstitutionsExecutivesTypeEnum institutionsExecutivesTypeEnum : InstitutionsExecutivesTypeEnum.values()) {
            if (institutionsExecutivesTypeEnum.getValue().equals(value)) {
                return institutionsExecutivesTypeEnum;
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
     * @time 2022-07-04 10:48:36
     */
    public static String getDesc(Integer value) {
        for (InstitutionsExecutivesTypeEnum institutionsExecutivesTypeEnum : InstitutionsExecutivesTypeEnum.values()) {
            if (institutionsExecutivesTypeEnum.getValue().equals(value)) {
                return institutionsExecutivesTypeEnum.getDesc();
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
     * @time 2022-07-04 10:48:36
     */
    public static Integer getDesc(String desc) {
        for (InstitutionsExecutivesTypeEnum institutionsExecutivesTypeEnum : InstitutionsExecutivesTypeEnum.values()) {
            if (institutionsExecutivesTypeEnum.getDesc().equals(desc)) {
                return institutionsExecutivesTypeEnum.getValue();
            }
        }
        return null;
    }
}