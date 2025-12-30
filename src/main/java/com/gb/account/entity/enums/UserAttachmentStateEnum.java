package com.gb.account.entity.enums;

import com.baomidou.mybatisplus.annotation.IEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;


/**
 * TODO 用户附件类型枚举
 * 代码生成器
 *
 * @author lijh
 * @className UserAttachmentStateEnum
 * @time 2022-04-14 10:04:04
 */
@Getter
@AllArgsConstructor
public enum UserAttachmentStateEnum implements IEnum<Integer> {
    // 默认
    默认(0, "默认"),
    ;

    private Integer value;
    private String desc;


    /**
     * TODO 通过value获得枚举
     *
     * @param value
     * @return UserAttachmentStateEnum
     * @author lijh
     * @methodName getUserAttachmentStateEnum
     * @time 2022-04-14 10:04:04
     */
    public static UserAttachmentStateEnum getUserAttachmentStateEnum(Integer value) {
        for (UserAttachmentStateEnum userAttachmentStateEnum : UserAttachmentStateEnum.values()) {
            if (userAttachmentStateEnum.getValue().equals(value)) {
                return userAttachmentStateEnum;
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
     * @time 2022-04-14 10:04:04
     */
    public static String getDesc(Integer value) {
        for (UserAttachmentStateEnum userAttachmentStateEnum : UserAttachmentStateEnum.values()) {
            if (userAttachmentStateEnum.getValue().equals(value)) {
                return userAttachmentStateEnum.getDesc();
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
     * @time 2022-04-14 10:04:04
     */
    public static Integer getDesc(String desc) {
        for (UserAttachmentStateEnum userAttachmentStateEnum : UserAttachmentStateEnum.values()) {
            if (userAttachmentStateEnum.getDesc().equals(desc)) {
                return userAttachmentStateEnum.getValue();
            }
        }
        return null;
    }
}