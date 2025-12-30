package com.gb.platform.entity.enums;

import com.baomidou.mybatisplus.annotation.IEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;


/**
 * TODO 转化外部系统平台类型枚举
 * 代码生成器
 *
 * @author sunx
 * @className TransformationExternalPlatformSystemStateEnum
 * @time 2022-12-16 03:10:08
 */
@Getter
@AllArgsConstructor
public enum TransformationExternalPlatformSystemStateEnum implements IEnum<Integer> {
    // 启用
    启用(0, "启用"),
    // 不启用
    不启用(1, "不启用"),
    ;

    private Integer value;
    private String desc;


    /**
     * TODO 通过value获得枚举
     *
     * @param value
     * @return TransformationExternalPlatformSystemStateEnum
     * @author sunx
     * @methodName getTransformationExternalPlatformSystemStateEnum
     * @time 2022-12-16 03:10:08
     */
    public static TransformationExternalPlatformSystemStateEnum getTransformationExternalPlatformSystemStateEnum(Integer value) {
        for (TransformationExternalPlatformSystemStateEnum transformationExternalPlatformSystemStateEnum : TransformationExternalPlatformSystemStateEnum.values()) {
            if (transformationExternalPlatformSystemStateEnum.getValue().equals(value)) {
                return transformationExternalPlatformSystemStateEnum;
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
        for (TransformationExternalPlatformSystemStateEnum transformationExternalPlatformSystemStateEnum : TransformationExternalPlatformSystemStateEnum.values()) {
            if (transformationExternalPlatformSystemStateEnum.getValue().equals(value)) {
                return transformationExternalPlatformSystemStateEnum.getDesc();
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
        for (TransformationExternalPlatformSystemStateEnum transformationExternalPlatformSystemStateEnum : TransformationExternalPlatformSystemStateEnum.values()) {
            if (transformationExternalPlatformSystemStateEnum.getDesc().equals(desc)) {
                return transformationExternalPlatformSystemStateEnum.getValue();
            }
        }
        return null;
    }
}