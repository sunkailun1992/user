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
public enum SourceValueStateEnum implements IEnum<Integer> {

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
    public static SourceValueStateEnum getSourceValueStateEnum(Integer value) {
        for (SourceValueStateEnum sourceValueStateEnum : SourceValueStateEnum.values()) {
            if (sourceValueStateEnum.getValue().equals(value)) {
                return sourceValueStateEnum;
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
        for (SourceValueStateEnum sourceValueStateEnum : SourceValueStateEnum.values()) {
            if (sourceValueStateEnum.getValue().equals(value)) {
                return sourceValueStateEnum.getDesc();
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
        for (SourceValueStateEnum sourceValueStateEnum : SourceValueStateEnum.values()) {
            if (sourceValueStateEnum.getDesc().equals(desc)) {
                return sourceValueStateEnum.getValue();
            }
        }
        return null;
    }
}