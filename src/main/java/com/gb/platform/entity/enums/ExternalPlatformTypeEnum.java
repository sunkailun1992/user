package com.gb.platform.entity.enums;

import com.baomidou.mybatisplus.annotation.IEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;


/**
 * TODO 外部平台类型枚举
 * 代码生成器
 *
 * @author sunx
 * @className ExternalPlatformTypeEnum
 * @time 2022-12-16 03:10:07
 */
@Getter
@AllArgsConstructor
public enum ExternalPlatformTypeEnum implements IEnum<Integer> {
    // 默认
    默认(0, "默认"),
    ;

    private Integer value;
    private String desc;


    /**
     * TODO 通过value获得枚举
     *
     * @param value
     * @return ExternalPlatformTypeEnum
     * @author sunx
     * @methodName getExternalPlatformTypeEnum
     * @time 2022-12-16 03:10:07
     */
    public static ExternalPlatformTypeEnum getExternalPlatformTypeEnum(Integer value) {
        for (ExternalPlatformTypeEnum externalPlatformTypeEnum : ExternalPlatformTypeEnum.values()) {
            if (externalPlatformTypeEnum.getValue().equals(value)) {
                return externalPlatformTypeEnum;
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
        for (ExternalPlatformTypeEnum externalPlatformTypeEnum : ExternalPlatformTypeEnum.values()) {
            if (externalPlatformTypeEnum.getValue().equals(value)) {
                return externalPlatformTypeEnum.getDesc();
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
        for (ExternalPlatformTypeEnum externalPlatformTypeEnum : ExternalPlatformTypeEnum.values()) {
            if (externalPlatformTypeEnum.getDesc().equals(desc)) {
                return externalPlatformTypeEnum.getValue();
            }
        }
        return null;
    }
}