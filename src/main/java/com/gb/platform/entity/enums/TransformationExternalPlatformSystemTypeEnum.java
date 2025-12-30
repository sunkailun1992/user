package com.gb.platform.entity.enums;

import com.baomidou.mybatisplus.annotation.IEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;


/**
 * TODO 转化外部系统平台类型枚举
 * 代码生成器
 *
 * @author sunx
 * @className TransformationExternalPlatformSystemTypeEnum
 * @time 2022-12-16 03:10:08
 */
@Getter
@AllArgsConstructor
public enum TransformationExternalPlatformSystemTypeEnum implements IEnum<Integer> {
    // 默认
    默认(0, "默认"),
    ;

    private Integer value;
    private String desc;


    /**
     * TODO 通过value获得枚举
     *
     * @param value
     * @return TransformationExternalPlatformSystemTypeEnum
     * @author sunx
     * @methodName getTransformationExternalPlatformSystemTypeEnum
     * @time 2022-12-16 03:10:08
     */
    public static TransformationExternalPlatformSystemTypeEnum getTransformationExternalPlatformSystemTypeEnum(Integer value) {
        for (TransformationExternalPlatformSystemTypeEnum transformationExternalPlatformSystemTypeEnum : TransformationExternalPlatformSystemTypeEnum.values()) {
            if (transformationExternalPlatformSystemTypeEnum.getValue().equals(value)) {
                return transformationExternalPlatformSystemTypeEnum;
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
     * @time 2022-12-16 03:10:08
     */
    public static String getDesc(Integer value) {
        for (TransformationExternalPlatformSystemTypeEnum transformationExternalPlatformSystemTypeEnum : TransformationExternalPlatformSystemTypeEnum.values()) {
            if (transformationExternalPlatformSystemTypeEnum.getValue().equals(value)) {
                return transformationExternalPlatformSystemTypeEnum.getDesc();
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
     * @time 2022-12-16 03:10:08
     */
    public static Integer getDesc(String desc) {
        for (TransformationExternalPlatformSystemTypeEnum transformationExternalPlatformSystemTypeEnum : TransformationExternalPlatformSystemTypeEnum.values()) {
            if (transformationExternalPlatformSystemTypeEnum.getDesc().equals(desc)) {
                return transformationExternalPlatformSystemTypeEnum.getValue();
            }
        }
        return null;
    }
}