package com.gb.account.entity.enums;

import com.baomidou.mybatisplus.annotation.IEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.Optional;

/**
 * Created with IntelliJ IDEA.
 * @author:     	孙凯伦
 * @since:   	    2021-10-21 01:50:40
 * @description:	TODO  用户扩展表类型枚举
 * @source:  	    代码生成器
 */
@Getter
@AllArgsConstructor
public enum UserExtendsSexEnum implements IEnum<Integer> {
    /**
     * 男
     */
    男(0, "MALE", "男"),

    /**
     * 女
     */
    女(1, "FEMALE", "女"),

    /**
     * 未知
     */
    未知(2, "UNKNOWN", "未知");

    /**
     * 码值
     */
    private Integer value;

    /**
     * 工保通码值
     */
    private String gbtCode;

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
    public static UserExtendsSexEnum getUserExtendsSexEnum(Integer value) {
        for (UserExtendsSexEnum userExtendsSexEnum : UserExtendsSexEnum.values()) {
            if (userExtendsSexEnum.getValue().equals(value)) {
                return userExtendsSexEnum;
            }
        }
        return null;
    }

    public static UserExtendsSexEnum getUserExtendsSexEnum(String gbtCode){
        if(StringUtils.isBlank(gbtCode)){
            return null;
        }
        Optional<UserExtendsSexEnum> userExtendsSexEnum = Arrays.stream(UserExtendsSexEnum.values()).filter(s -> StringUtils.equals(s.getGbtCode(), gbtCode)).findFirst();
        return userExtendsSexEnum.orElse(UserExtendsSexEnum.未知);

    }


    /**
     * 获得备注
     *
     * @param value
     * @return
     */
    public static String getDesc(Integer value) {
        for (UserExtendsSexEnum userExtendsSexEnum : UserExtendsSexEnum.values()) {
            if (userExtendsSexEnum.getValue().equals(value)) {
                return userExtendsSexEnum.getDesc();
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
        for (UserExtendsSexEnum userExtendsSexEnum : UserExtendsSexEnum.values()) {
            if (userExtendsSexEnum.getDesc().equals(desc)) {
                return userExtendsSexEnum.getValue();
            }
        }
        return null;
    }
}