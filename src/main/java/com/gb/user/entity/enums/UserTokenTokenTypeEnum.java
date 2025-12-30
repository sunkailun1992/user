package com.gb.user.entity.enums;

import com.baomidou.mybatisplus.annotation.IEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;


/**
 * TODO 用户设备信息表类型枚举
 * 代码生成器
 *
 * @author wgs
 * @className UserTokenTokenTypeEnum
 * @time 2022-01-20 03:40:09
 */
@Getter
@AllArgsConstructor
public enum UserTokenTokenTypeEnum implements IEnum<Integer> {
    // IOS
    IOS(0, "IOS"),
    // Android
    Android(1, "Android"),
    ;

    private Integer value;
    private String desc;


    /**
     * TODO 通过value获得枚举
     *
     * @param value
     * @return UserTokenTokenTypeEnum
     * @author wgs
     * @methodName getUserTokenTokenTypeEnum
     * @time 2022-01-20 03:40:09
     */
    public static UserTokenTokenTypeEnum getUserTokenTokenTypeEnum(Integer value) {
        for (UserTokenTokenTypeEnum userTokenTokenTypeEnum : UserTokenTokenTypeEnum.values()) {
            if (userTokenTokenTypeEnum.getValue().equals(value)) {
                return userTokenTokenTypeEnum;
            }
        }
        return null;
    }


    /**
     * TODO 获得备注
     *
     * @param value
     * @return String
     * @author wgs
     * @methodName getDesc
     * @time 2022-01-20 03:40:09
     */
    public static String getDesc(Integer value) {
        for (UserTokenTokenTypeEnum userTokenTokenTypeEnum : UserTokenTokenTypeEnum.values()) {
            if (userTokenTokenTypeEnum.getValue().equals(value)) {
                return userTokenTokenTypeEnum.getDesc();
            }
        }
        return null;
    }


    /**
     * TODO 获得值
     *
     * @param desc
     * @return Integer
     * @author wgs
     * @methodName getDesc
     * @time 2022-01-20 03:40:09
     */
    public static Integer getDesc(String desc) {
        for (UserTokenTokenTypeEnum userTokenTokenTypeEnum : UserTokenTokenTypeEnum.values()) {
            if (userTokenTokenTypeEnum.getDesc().equals(desc)) {
                return userTokenTokenTypeEnum.getValue();
            }
        }
        return null;
    }
}