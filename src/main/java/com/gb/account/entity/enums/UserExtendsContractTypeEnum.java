package com.gb.account.entity.enums;

import com.baomidou.mybatisplus.annotation.IEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;


/**
 * TODO 合同类型枚举
 * 代码生成器
 *
 * @author sunxin
 * @className UserExtendsContractTypeEnum
 * @time 2022-07-04 10:48:36
 */
@Getter
@AllArgsConstructor
public enum UserExtendsContractTypeEnum implements IEnum<Integer> {
    // 劳动合同
    劳动合同(0, "劳动合同"),
    // 劳务派遣合同
    劳务派遣合同(1, "劳务派遣合同"),
    // 代理合同-工保网业务核心系统
    代理合同(2, "代理合同"),
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
    public static UserExtendsContractTypeEnum getUserExtendsContractTypeEnum(Integer value) {
        for (UserExtendsContractTypeEnum userExtendsContractTypeEnum : UserExtendsContractTypeEnum.values()) {
            if (userExtendsContractTypeEnum.getValue().equals(value)) {
                return userExtendsContractTypeEnum;
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
        for (UserExtendsContractTypeEnum userExtendsContractTypeEnum : UserExtendsContractTypeEnum.values()) {
            if (userExtendsContractTypeEnum.getValue().equals(value)) {
                return userExtendsContractTypeEnum.getDesc();
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
        for (UserExtendsContractTypeEnum userExtendsContractTypeEnum : UserExtendsContractTypeEnum.values()) {
            if (userExtendsContractTypeEnum.getDesc().equals(desc)) {
                return userExtendsContractTypeEnum.getValue();
            }
        }
        return null;
    }
}