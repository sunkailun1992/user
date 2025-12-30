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
public enum UserAgentCertificationStateEnum implements IEnum<Integer> {
    // 待认证
    待认证(0, "待认证"),
    // 认证成功
    认证成功(1, "认证成功"),
    // 认证失败
    认证失败(2, "认证失败"),
    //认证关闭
    认证关闭(3, "认证关闭"),
    ;

    private Integer value;
    private String desc;


    /**
     * TODO 通过value获得枚举
     *
     * @param value
     * @return UserAgentCertificationStateEnum
     * @author sunxin
     * @methodName getUserAgentCertificationStateEnum
     * @time 2022-07-04 10:48:36
     */
    public static UserAgentCertificationStateEnum getUserAgentCertificationStateEnum(Integer value) {
        for (UserAgentCertificationStateEnum userAgentCertificationStateEnum : UserAgentCertificationStateEnum.values()) {
            if (userAgentCertificationStateEnum.getValue().equals(value)) {
                return userAgentCertificationStateEnum;
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
        for (UserAgentCertificationStateEnum userAgentCertificationStateEnum : UserAgentCertificationStateEnum.values()) {
            if (userAgentCertificationStateEnum.getValue().equals(value)) {
                return userAgentCertificationStateEnum.getDesc();
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
        for (UserAgentCertificationStateEnum userAgentCertificationStateEnum : UserAgentCertificationStateEnum.values()) {
            if (userAgentCertificationStateEnum.getDesc().equals(desc)) {
                return userAgentCertificationStateEnum.getValue();
            }
        }
        return null;
    }
}