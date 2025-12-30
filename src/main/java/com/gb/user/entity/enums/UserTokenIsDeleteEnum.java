package com.gb.user.entity.enums;

import com.baomidou.mybatisplus.annotation.IEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;


/**
 * TODO 用户设备信息表类型枚举
 * 代码生成器
 *
 * @author wgs
 * @className UserTokenIsDeleteEnum
 * @time 2022-01-20 03:40:09
 */
@Getter
@AllArgsConstructor
public enum UserTokenIsDeleteEnum implements IEnum<Integer> {
    // 未删除
    未删除(0, "未删除"),
    // 删除
    删除(1, "删除"),
    ;

    private Integer value;
    private String desc;


    /**
     * TODO 通过value获得枚举
     *
     * @param value
     * @return UserTokenIsDeleteEnum
     * @author wgs
     * @methodName getUserTokenIsDeleteEnum
     * @time 2022-01-20 03:40:09
     */
    public static UserTokenIsDeleteEnum getUserTokenIsDeleteEnum(Integer value) {
        for (UserTokenIsDeleteEnum userTokenIsDeleteEnum : UserTokenIsDeleteEnum.values()) {
            if (userTokenIsDeleteEnum.getValue().equals(value)) {
                return userTokenIsDeleteEnum;
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
        for (UserTokenIsDeleteEnum userTokenIsDeleteEnum : UserTokenIsDeleteEnum.values()) {
            if (userTokenIsDeleteEnum.getValue().equals(value)) {
                return userTokenIsDeleteEnum.getDesc();
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
        for (UserTokenIsDeleteEnum userTokenIsDeleteEnum : UserTokenIsDeleteEnum.values()) {
            if (userTokenIsDeleteEnum.getDesc().equals(desc)) {
                return userTokenIsDeleteEnum.getValue();
            }
        }
        return null;
    }
}