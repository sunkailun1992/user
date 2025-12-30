package com.gb.user.entity.enums;

import com.baomidou.mybatisplus.annotation.IEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;


/**
 * TODO 机构类型枚举
 * 代码生成器
 *
 * @author sunxin
 * @className InstitutionsStateEnum
 * @time 2022-07-04 10:48:36
 */
@Getter
@AllArgsConstructor
public enum InstitutionsStateEnum implements IEnum<Integer> {
    // 经营中
    经营中(0, "经营中"),
    // 已退出
    已退出(1, "已退出"),
    // 待开业
    待开业(2, "待开业"),
    ;

    private Integer value;
    private String desc;


    /**
     * TODO 通过value获得枚举
     *
     * @param value
     * @return InstitutionsStateEnum
     * @author sunxin
     * @methodName getInstitutionsStateEnum
     * @time 2022-07-04 10:48:36
     */
    public static InstitutionsStateEnum getInstitutionsStateEnum(Integer value) {
        for (InstitutionsStateEnum institutionsStateEnum : InstitutionsStateEnum.values()) {
            if (institutionsStateEnum.getValue().equals(value)) {
                return institutionsStateEnum;
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
        for (InstitutionsStateEnum institutionsStateEnum : InstitutionsStateEnum.values()) {
            if (institutionsStateEnum.getValue().equals(value)) {
                return institutionsStateEnum.getDesc();
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
        for (InstitutionsStateEnum institutionsStateEnum : InstitutionsStateEnum.values()) {
            if (institutionsStateEnum.getDesc().equals(desc)) {
                return institutionsStateEnum.getValue();
            }
        }
        return null;
    }
}