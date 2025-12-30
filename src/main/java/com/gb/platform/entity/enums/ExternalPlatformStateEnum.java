package com.gb.platform.entity.enums;

import com.baomidou.mybatisplus.annotation.IEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;


/**
 * TODO 外部平台类型枚举
 * 代码生成器
 *
 * @author sunx
 * @className ExternalPlatformStateEnum
 * @time 2022-12-16 03:10:07
 */
@Getter
@AllArgsConstructor
public enum ExternalPlatformStateEnum implements IEnum<Integer> {
    // 默认
    默认(0, "默认"),
    ;

    private Integer value;
    private String desc;


    /**
     * TODO 通过value获得枚举
     *
     * @param value
     * @return ExternalPlatformStateEnum
     * @author sunx
     * @methodName getExternalPlatformStateEnum
     * @time 2022-12-16 03:10:07
     */
    public static ExternalPlatformStateEnum getExternalPlatformStateEnum(Integer value) {
        for (ExternalPlatformStateEnum externalPlatformStateEnum : ExternalPlatformStateEnum.values()) {
            if (externalPlatformStateEnum.getValue().equals(value)) {
                return externalPlatformStateEnum;
            }
        }
        return null;
    }


    /**
     * TODO 获得备注
     *
     * @param value
     * @return String
     * @author sunx
     * @methodName getDesc
     * @time 2022-12-16 03:10:07
     */
    public static String getDesc(Integer value) {
        for (ExternalPlatformStateEnum externalPlatformStateEnum : ExternalPlatformStateEnum.values()) {
            if (externalPlatformStateEnum.getValue().equals(value)) {
                return externalPlatformStateEnum.getDesc();
            }
        }
        return null;
    }


    /**
     * TODO 获得值
     *
     * @param desc
     * @return Integer
     * @author sunx
     * @methodName getDesc
     * @time 2022-12-16 03:10:07
     */
    public static Integer getDesc(String desc) {
        for (ExternalPlatformStateEnum externalPlatformStateEnum : ExternalPlatformStateEnum.values()) {
            if (externalPlatformStateEnum.getDesc().equals(desc)) {
                return externalPlatformStateEnum.getValue();
            }
        }
        return null;
    }
}