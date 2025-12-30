package com.gb.account.entity.enums;

import com.baomidou.mybatisplus.annotation.IEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Created with IntelliJ IDEA.
 * @author:     	孙凯伦
 * @since:   	    2021-11-03 03:57:55
 * @description:	TODO  来源值类型枚举
 * @source:  	    代码生成器
 */
@Getter
@AllArgsConstructor
public enum SourceValueTypeEnum implements IEnum<Integer> {

    /**
     * 默认值
     */
    默认(0, "默认"),
    ;

    /**
     * 值
     */
    private Integer value;

    /**
     * 描述
     */
    private String desc;


    /**
     * 通过value获得枚举
     *
     * @param value
     * @return
     */
    public static SourceValueTypeEnum getSourceValueTypeEnum(Integer value) {
        for (SourceValueTypeEnum sourceValueTypeEnum : SourceValueTypeEnum.values()) {
            if (sourceValueTypeEnum.getValue().equals(value)) {
                return sourceValueTypeEnum;
            }
        }
        return null;
    }


    /**
     * 获得备注
     *
     * @param value
     * @return
     */
    public static String getDesc(Integer value) {
        for (SourceValueTypeEnum sourceValueTypeEnum : SourceValueTypeEnum.values()) {
            if (sourceValueTypeEnum.getValue().equals(value)) {
                return sourceValueTypeEnum.getDesc();
            }
        }
        return null;
    }


    /**
     * 获得值
     *
     * @param desc
     * @return
     */
    public static Integer getDesc(String desc) {
        for (SourceValueTypeEnum sourceValueTypeEnum : SourceValueTypeEnum.values()) {
            if (sourceValueTypeEnum.getDesc().equals(desc)) {
                return sourceValueTypeEnum.getValue();
            }
        }
        return null;
    }
}