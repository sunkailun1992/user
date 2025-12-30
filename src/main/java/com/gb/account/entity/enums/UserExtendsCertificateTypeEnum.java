package com.gb.account.entity.enums;

import com.baomidou.mybatisplus.annotation.IEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;


/**
 * TODO 证件类型枚举
 * 代码生成器
 *
 * @author sunxin
 * @className UserExtendsCertificateTypeEnum
 * @time 2022-07-04 10:48:36
 */
@Getter
@AllArgsConstructor
public enum UserExtendsCertificateTypeEnum implements IEnum<Integer> {
    // 在职
    身份证(0, "身份证"),
    // 离职
    士官证(1, "士官证"),
    // 待入职
    护照(2, "护照"),
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
    public static UserExtendsCertificateTypeEnum getUserExtendsCertificateTypeEnum(Integer value) {
        for (UserExtendsCertificateTypeEnum userExtendsCertificateTypeEnum : UserExtendsCertificateTypeEnum.values()) {
            if (userExtendsCertificateTypeEnum.getValue().equals(value)) {
                return userExtendsCertificateTypeEnum;
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
        for (UserExtendsCertificateTypeEnum userExtendsCertificateTypeEnum : UserExtendsCertificateTypeEnum.values()) {
            if (userExtendsCertificateTypeEnum.getValue().equals(value)) {
                return userExtendsCertificateTypeEnum.getDesc();
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
        for (UserExtendsCertificateTypeEnum userExtendsCertificateTypeEnum : UserExtendsCertificateTypeEnum.values()) {
            if (userExtendsCertificateTypeEnum.getDesc().equals(desc)) {
                return userExtendsCertificateTypeEnum.getValue();
            }
        }
        return null;
    }
}