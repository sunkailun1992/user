package com.gb.user.entity.model.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * <p>
 * 请求工保通类
 * </p>
 *
 * @author sunx
 * @since 2021-05-25
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class UserLoginRequest extends BaseUserRequest implements Serializable {
    @ApiModelProperty(value = "手机号 （手机号或账号必填一个）")
    private String phone;

    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "账号 （手机号或账号必填一个）")
    private String userName;

    @ApiModelProperty(value = "验证码")
    private String verifyCode;

    @ApiModelProperty(value = "业务类型（MOBILE：手机登录，EMAIL：邮件登录，ACCOUNT：账号登录）")
    private String busType;

    /**
     * 经纪人id（快速登录的时候，会用到）
     * APP分享的時候，会用到---通知MQ的时候
     */
    @ApiModelProperty(value = "经纪人id")
    private String agentUserId;

//    /**
//     * 报价工具1.0.0-邀请好友id---快速登录通知MQ的时候
//     */
//    private String inviteUserId;
}
