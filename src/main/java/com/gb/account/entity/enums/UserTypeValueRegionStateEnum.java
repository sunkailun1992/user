package com.gb.account.entity.enums;

import com.baomidou.mybatisplus.annotation.IEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;


/**
 * TODO 用户类型值地区类型枚举
 * 代码生成器
 *
 * @author lijh
 * @className UserTypeValueRegionStateEnum
 * @time 2022-07-12 11:45:19
 */
@Getter
@AllArgsConstructor
public enum UserTypeValueRegionStateEnum implements IEnum<Integer> {
    // 默认
    默认(0, "默认"),
    ;

    private Integer value;
    private String desc;


    /**
     * TODO 通过value获得枚举
     *
     * @param value
     * @return UserTypeValueRegionStateEnum
     * @author lijh
     * @methodName getUserTypeValueRegionStateEnum
     * @time 2022-07-12 11:45:19
     */
    public static UserTypeValueRegionStateEnum getUserTypeValueRegionStateEnum(Integer value) {
        for (UserTypeValueRegionStateEnum userTypeValueRegionStateEnum : UserTypeValueRegionStateEnum.values()) {
            if (userTypeValueRegionStateEnum.getValue().equals(value)) {
                return userTypeValueRegionStateEnum;
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
        for (UserTypeValueRegionStateEnum userTypeValueRegionStateEnum : UserTypeValueRegionStateEnum.values()) {
            if (userTypeValueRegionStateEnum.getValue().equals(value)) {
                return userTypeValueRegionStateEnum.getDesc();
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
        for (UserTypeValueRegionStateEnum userTypeValueRegionStateEnum : UserTypeValueRegionStateEnum.values()) {
            if (userTypeValueRegionStateEnum.getDesc().equals(desc)) {
                return userTypeValueRegionStateEnum.getValue();
            }
        }
        return null;
    }
}