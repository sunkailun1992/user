package com.gb.account.entity.enums;

import com.baomidou.mybatisplus.annotation.IEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Created with IntelliJ IDEA.
 * @author:     	孙凯伦
 * @since:   	    2021-11-03 03:57:55
 * @description:	TODO  来源类型枚举
 * @source:  	    代码生成器
 */
@Getter
@AllArgsConstructor
public enum SourceTypeEnum implements IEnum<Integer> {

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
    public static SourceTypeEnum getSourceTypeEnum(Integer value) {
        for (SourceTypeEnum sourceTypeEnum : SourceTypeEnum.values()) {
            if (sourceTypeEnum.getValue().equals(value)) {
                return sourceTypeEnum;
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
        for (SourceTypeEnum sourceTypeEnum : SourceTypeEnum.values()) {
            if (sourceTypeEnum.getValue().equals(value)) {
                return sourceTypeEnum.getDesc();
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
        for (SourceTypeEnum sourceTypeEnum : SourceTypeEnum.values()) {
            if (sourceTypeEnum.getDesc().equals(desc)) {
                return sourceTypeEnum.getValue();
            }
        }
        return null;
    }
}