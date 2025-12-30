package com.gb.user.entity.enums;

import com.baomidou.mybatisplus.annotation.IEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;


/**
 * TODO 机构高管类型枚举
 * 代码生成器
 *
 * @author sunxin
 * @className InstitutionsExecutivesStateEnum
 * @time 2022-07-04 10:48:36
 */
@Getter
@AllArgsConstructor
public enum InstitutionsExecutivesStateEnum implements IEnum<Integer> {
    // 默认
    默认(0, "默认"),
    ;

    private Integer value;
    private String desc;


    /**
     * TODO 通过value获得枚举
     *
     * @param value
     * @return InstitutionsExecutivesStateEnum
     * @author sunxin
     * @methodName getInstitutionsExecutivesStateEnum
     * @time 2022-07-04 10:48:36
     */
    public static InstitutionsExecutivesStateEnum getInstitutionsExecutivesStateEnum(Integer value) {
        for (InstitutionsExecutivesStateEnum institutionsExecutivesStateEnum : InstitutionsExecutivesStateEnum.values()) {
            if (institutionsExecutivesStateEnum.getValue().equals(value)) {
                return institutionsExecutivesStateEnum;
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
        for (InstitutionsExecutivesStateEnum institutionsExecutivesStateEnum : InstitutionsExecutivesStateEnum.values()) {
            if (institutionsExecutivesStateEnum.getValue().equals(value)) {
                return institutionsExecutivesStateEnum.getDesc();
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
        for (InstitutionsExecutivesStateEnum institutionsExecutivesStateEnum : InstitutionsExecutivesStateEnum.values()) {
            if (institutionsExecutivesStateEnum.getDesc().equals(desc)) {
                return institutionsExecutivesStateEnum.getValue();
            }
        }
        return null;
    }
}