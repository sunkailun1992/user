package com.gb.user.entity.enums;

import com.baomidou.mybatisplus.annotation.IEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;


/**
 * TODO 机构类型枚举
 * 代码生成器
 *
 * @author sunxin
 * @className InstitutionsTypeEnum
 * @time 2022-07-04 10:48:36
 */
@Getter
@AllArgsConstructor
public enum InstitutionsTypeEnum implements IEnum<Integer> {
    // 总公司
    总公司(0, "总公司"),
    // 省级分支机构
    省级分支机构(1, "省级分支机构"),
    // 市级分支机构
    市级分支机构(2, "市级分支机构"),
    // 区级分支机构
    区级分支机构(3, "区级分支机构"),
    ;

    private Integer value;
    private String desc;


    /**
     * TODO 通过value获得枚举
     *
     * @param value
     * @return InstitutionsTypeEnum
     * @author sunxin
     * @methodName getInstitutionsTypeEnum
     * @time 2022-07-04 10:48:36
     */
    public static InstitutionsTypeEnum getInstitutionsTypeEnum(Integer value) {
        for (InstitutionsTypeEnum institutionsTypeEnum : InstitutionsTypeEnum.values()) {
            if (institutionsTypeEnum.getValue().equals(value)) {
                return institutionsTypeEnum;
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
        for (InstitutionsTypeEnum institutionsTypeEnum : InstitutionsTypeEnum.values()) {
            if (institutionsTypeEnum.getValue().equals(value)) {
                return institutionsTypeEnum.getDesc();
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
        for (InstitutionsTypeEnum institutionsTypeEnum : InstitutionsTypeEnum.values()) {
            if (institutionsTypeEnum.getDesc().equals(desc)) {
                return institutionsTypeEnum.getValue();
            }
        }
        return null;
    }
}