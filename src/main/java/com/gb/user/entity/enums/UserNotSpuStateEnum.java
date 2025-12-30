package com.gb.user.entity.enums;

import com.baomidou.mybatisplus.annotation.IEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;


/**
 * TODO 企业渠道用户排除产品类型枚举
 * 代码生成器
 *
 * @author 孙凯伦
 * @className UserNotSpuStateEnum
 * @time 2023-07-07 04:36:59
 */
@Getter
@AllArgsConstructor
public enum UserNotSpuStateEnum implements IEnum<Integer> {
    // 默认
    默认(0, "默认"),
    ;

    private Integer value;
    private String desc;


    /**
     * TODO 通过value获得枚举
     *
     * @param value
     * @return UserNotSpuStateEnum
     * @author 孙凯伦
     * @methodName getUserNotSpuStateEnum
     * @time 2023-07-07 04:36:59
     */
    public static UserNotSpuStateEnum getUserNotSpuStateEnum(Integer value) {
        for (UserNotSpuStateEnum userNotSpuStateEnum : UserNotSpuStateEnum.values()) {
            if (userNotSpuStateEnum.getValue().equals(value)) {
                return userNotSpuStateEnum;
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
        for (UserNotSpuStateEnum userNotSpuStateEnum : UserNotSpuStateEnum.values()) {
            if (userNotSpuStateEnum.getValue().equals(value)) {
                return userNotSpuStateEnum.getDesc();
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
        for (UserNotSpuStateEnum userNotSpuStateEnum : UserNotSpuStateEnum.values()) {
            if (userNotSpuStateEnum.getDesc().equals(desc)) {
                return userNotSpuStateEnum.getValue();
            }
        }
        return null;
    }
}