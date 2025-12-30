package com.gb.permissions.entity.enums;

import com.baomidou.mybatisplus.annotation.IEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Created with IntelliJ IDEA.
 * @author:     	孙凯伦
 * @since:   	    2021-10-21 01:59:45
 * @description:	TODO  资源表类型枚举
 * @source:  	    代码生成器
 */
@Getter
@AllArgsConstructor
public enum ResourceApiTypeEnum implements IEnum<Integer> {

    /**
     * 待定
     */
    待定(0, "待定"),

    /**
     * get
     */
    get(1, "get"),

    /**
     * post
     */
    post(2, "post"),

    /**
     * 更新
     */
    put(3, "put"),

    /**
     * 删除
     */
    delete(4, "delete"),
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
    public static ResourceApiTypeEnum getResourceApiTypeEnum(Integer value) {
        for (ResourceApiTypeEnum resourceApiTypeEnum : ResourceApiTypeEnum.values()) {
            if (resourceApiTypeEnum.getValue().equals(value)) {
                return resourceApiTypeEnum;
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
        for (ResourceApiTypeEnum resourceApiTypeEnum : ResourceApiTypeEnum.values()) {
            if (resourceApiTypeEnum.getValue().equals(value)) {
                return resourceApiTypeEnum.getDesc();
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
        for (ResourceApiTypeEnum resourceApiTypeEnum : ResourceApiTypeEnum.values()) {
            if (resourceApiTypeEnum.getDesc().equals(desc)) {
                return resourceApiTypeEnum.getValue();
            }
        }
        return null;
    }
}