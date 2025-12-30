package com.gb.platform.entity.enums;

import com.baomidou.mybatisplus.annotation.IEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;


/**
 * TODO 外部系统类型枚举
 * 代码生成器
 *
 * @author sunx
 * @className ExternalSystemStateEnum
 * @time 2022-12-16 03:10:08
 */
@Getter
@AllArgsConstructor
public enum ExternalSystemStateEnum implements IEnum<Integer> {
    // 默认
    默认(0, "默认"),
    ;

    private Integer value;
    private String desc;


    /**
     * TODO 通过value获得枚举
     *
     * @param value
     * @return ExternalSystemStateEnum
     * @author sunx
     * @methodName getExternalSystemStateEnum
     * @time 2022-12-16 03:10:08
     */
    public static ExternalSystemStateEnum getExternalSystemStateEnum(Integer value) {
        for (ExternalSystemStateEnum externalSystemStateEnum : ExternalSystemStateEnum.values()) {
            if (externalSystemStateEnum.getValue().equals(value)) {
                return externalSystemStateEnum;
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
        for (ExternalSystemStateEnum externalSystemStateEnum : ExternalSystemStateEnum.values()) {
            if (externalSystemStateEnum.getValue().equals(value)) {
                return externalSystemStateEnum.getDesc();
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
        for (ExternalSystemStateEnum externalSystemStateEnum : ExternalSystemStateEnum.values()) {
            if (externalSystemStateEnum.getDesc().equals(desc)) {
                return externalSystemStateEnum.getValue();
            }
        }
        return null;
    }
}