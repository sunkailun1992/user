package com.gb.mq.crm;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * Created with IntelliJ IDEA.
 * @author:     	sunx
 * @since:   	    2021-09-06 03:57:55
 * @description:	用户手机号修改、注销的时候，mqCRM
 */
@Data
@Accessors(chain = true)
public class BindUserEvent {
    /**
     * 用户id
     */
    private String userId;
    /**
     * 原手机号
     */
    private String mobile;
    /**
     * 新手机号
     */
    private String newMobile;
    /**
     * 原账户名
     */
    private String userName;
    /**
     * 新账户名
     */
    private String newUserName;
    /**
     * 人员常规状态枚举值（0：在职，1：注销，2：离职）
     */
    private Integer userFormalStateValue;
    /**
     * 用户类型值标签码值【多个值有逗号隔开】
     */
    private String userTypeValueCode;
}
