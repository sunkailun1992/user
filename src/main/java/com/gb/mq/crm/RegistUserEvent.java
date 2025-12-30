package com.gb.mq.crm;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * Created with IntelliJ IDEA.
 * @author:     	sunx
 * @since:   	    2021-09-06 03:57:55
 * @description:	注册的时候，用户mqCRM
 */
@Data
@Accessors(chain = true)
public class RegistUserEvent {
    /**
     * 用户id
     */
    private String userId;
    /**
     * 用户类型（0：用户，1：经纪人）
     */
    private Integer userType;
    /**
     * 手机号
     */
    private String mobile;
    /**
     * 姓名
     */
    private String name;
    /**
     * 经纪人id
     */
    private String agentUserId;
//    /**
//     * 报价工具1.0.0-邀请好友id---通知MQ的时候
//     */
//    private String inviteUserId;
//
//    /**
//     * 报价工具1.0.0-业务明细-报价表单id---通知MQ的时候
//     */
//    private String businessDetails;
}
