package com.gb.account.entity.enums;

import com.baomidou.mybatisplus.annotation.IEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;


/**
 * TODO 工作性质枚举
 * 代码生成器
 *
 * @author sunxin
 * @className UserExtendsNatureWorkEnum
 * @time 2022-07-04 10:48:36
 */
@Getter
@AllArgsConstructor
public enum UserExtendsNatureWorkEnum implements IEnum<Integer> {
    // 全职
    全职(0, "全职"),
    // 兼职
    兼职(1, "兼职"),
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
    public static UserExtendsNatureWorkEnum getUserExtendsNatureWorkEnum(Integer value) {
        for (UserExtendsNatureWorkEnum userExtendsNatureWorkEnum : UserExtendsNatureWorkEnum.values()) {
            if (userExtendsNatureWorkEnum.getValue().equals(value)) {
                return userExtendsNatureWorkEnum;
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
        for (UserExtendsNatureWorkEnum userExtendsNatureWorkEnum : UserExtendsNatureWorkEnum.values()) {
            if (userExtendsNatureWorkEnum.getValue().equals(value)) {
                return userExtendsNatureWorkEnum.getDesc();
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
        for (UserExtendsNatureWorkEnum userExtendsNatureWorkEnum : UserExtendsNatureWorkEnum.values()) {
            if (userExtendsNatureWorkEnum.getDesc().equals(desc)) {
                return userExtendsNatureWorkEnum.getValue();
            }
        }
        return null;
    }
}