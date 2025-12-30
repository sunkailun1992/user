package com.gb.user.entity.enums;

import com.baomidou.mybatisplus.annotation.IEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;


/**
 * TODO 企业渠道用户排除产品类型枚举
 * 代码生成器
 *
 * @author 孙凯伦
 * @className UserNotSpuTypeEnum
 * @time 2023-07-07 04:36:59
 */
@Getter
@AllArgsConstructor
public enum UserNotSpuTypeEnum implements IEnum<Integer> {
    // 默认
    默认(0, "默认"),
    ;

    private Integer value;
    private String desc;


    /**
     * TODO 通过value获得枚举
     *
     * @param value
     * @return UserNotSpuTypeEnum
     * @author 孙凯伦
     * @methodName getUserNotSpuTypeEnum
     * @time 2023-07-07 04:36:59
     */
    public static UserNotSpuTypeEnum getUserNotSpuTypeEnum(Integer value) {
        for (UserNotSpuTypeEnum userNotSpuTypeEnum : UserNotSpuTypeEnum.values()) {
            if (userNotSpuTypeEnum.getValue().equals(value)) {
                return userNotSpuTypeEnum;
            }
        }
        return null;
    }


    /**
     * TODO 获得备注
     *
     * @param value
     * @return String
     * @author 孙凯伦
     * @methodName getDesc
     * @time 2023-07-07 04:36:59
     */
    public static String getDesc(Integer value) {
        for (UserNotSpuTypeEnum userNotSpuTypeEnum : UserNotSpuTypeEnum.values()) {
            if (userNotSpuTypeEnum.getValue().equals(value)) {
                return userNotSpuTypeEnum.getDesc();
            }
        }
        return null;
    }


    /**
     * TODO 获得值
     *
     * @param desc
     * @return Integer
     * @author 孙凯伦
     * @methodName getDesc
     * @time 2023-07-07 04:36:59
     */
    public static Integer getDesc(String desc) {
        for (UserNotSpuTypeEnum userNotSpuTypeEnum : UserNotSpuTypeEnum.values()) {
            if (userNotSpuTypeEnum.getDesc().equals(desc)) {
                return userNotSpuTypeEnum.getValue();
            }
        }
        return null;
    }
}