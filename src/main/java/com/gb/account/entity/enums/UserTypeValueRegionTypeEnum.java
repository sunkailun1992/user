package com.gb.account.entity.enums;

import com.baomidou.mybatisplus.annotation.IEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;


/**
 * TODO 用户类型值地区类型枚举
 * 代码生成器
 *
 * @author lijh
 * @className UserTypeValueRegionTypeEnum
 * @time 2022-07-12 11:45:19
 */
@Getter
@AllArgsConstructor
public enum UserTypeValueRegionTypeEnum implements IEnum<Integer> {
    // 默认
    默认(0, "默认"),
    ;

    private Integer value;
    private String desc;


    /**
     * TODO 通过value获得枚举
     *
     * @param value
     * @return UserTypeValueRegionTypeEnum
     * @author lijh
     * @methodName getUserTypeValueRegionTypeEnum
     * @time 2022-07-12 11:45:19
     */
    public static UserTypeValueRegionTypeEnum getUserTypeValueRegionTypeEnum(Integer value) {
        for (UserTypeValueRegionTypeEnum userTypeValueRegionTypeEnum : UserTypeValueRegionTypeEnum.values()) {
            if (userTypeValueRegionTypeEnum.getValue().equals(value)) {
                return userTypeValueRegionTypeEnum;
            }
        }
        return null;
    }


    /**
     * TODO 获得备注
     *
     * @param value
     * @return String
     * @author lijh
     * @methodName getDesc
     * @time 2022-07-12 11:45:19
     */
    public static String getDesc(Integer value) {
        for (UserTypeValueRegionTypeEnum userTypeValueRegionTypeEnum : UserTypeValueRegionTypeEnum.values()) {
            if (userTypeValueRegionTypeEnum.getValue().equals(value)) {
                return userTypeValueRegionTypeEnum.getDesc();
            }
        }
        return null;
    }


    /**
     * TODO 获得值
     *
     * @param desc
     * @return Integer
     * @author lijh
     * @methodName getDesc
     * @time 2022-07-12 11:45:19
     */
    public static Integer getDesc(String desc) {
        for (UserTypeValueRegionTypeEnum userTypeValueRegionTypeEnum : UserTypeValueRegionTypeEnum.values()) {
            if (userTypeValueRegionTypeEnum.getDesc().equals(desc)) {
                return userTypeValueRegionTypeEnum.getValue();
            }
        }
        return null;
    }
}