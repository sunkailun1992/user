package com.gb.platform.entity.enums;

import com.baomidou.mybatisplus.annotation.IEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;


/**
 * TODO 转化外部系统平台用户关联类型枚举
 * 代码生成器
 *
 * @author sunx
 * @className TransformationExternalPlatformSystemUserTypeEnum
 * @time 2022-12-16 03:10:09
 */
@Getter
@AllArgsConstructor
public enum TransformationExternalPlatformSystemUserTypeEnum implements IEnum<Integer> {
    // 默认
    默认(0, "默认"),
    ;

    private Integer value;
    private String desc;


    /**
     * TODO 通过value获得枚举
     *
     * @param value
     * @return TransformationExternalPlatformSystemUserTypeEnum
     * @author sunx
     * @methodName getTransformationExternalPlatformSystemUserTypeEnum
     * @time 2022-12-16 03:10:09
     */
    public static TransformationExternalPlatformSystemUserTypeEnum getTransformationExternalPlatformSystemUserTypeEnum(Integer value) {
        for (TransformationExternalPlatformSystemUserTypeEnum transformationExternalPlatformSystemUserTypeEnum : TransformationExternalPlatformSystemUserTypeEnum.values()) {
            if (transformationExternalPlatformSystemUserTypeEnum.getValue().equals(value)) {
                return transformationExternalPlatformSystemUserTypeEnum;
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
     * @time 2022-12-16 03:10:09
     */
    public static String getDesc(Integer value) {
        for (TransformationExternalPlatformSystemUserTypeEnum transformationExternalPlatformSystemUserTypeEnum : TransformationExternalPlatformSystemUserTypeEnum.values()) {
            if (transformationExternalPlatformSystemUserTypeEnum.getValue().equals(value)) {
                return transformationExternalPlatformSystemUserTypeEnum.getDesc();
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
     * @time 2022-12-16 03:10:09
     */
    public static Integer getDesc(String desc) {
        for (TransformationExternalPlatformSystemUserTypeEnum transformationExternalPlatformSystemUserTypeEnum : TransformationExternalPlatformSystemUserTypeEnum.values()) {
            if (transformationExternalPlatformSystemUserTypeEnum.getDesc().equals(desc)) {
                return transformationExternalPlatformSystemUserTypeEnum.getValue();
            }
        }
        return null;
    }
}