package com.gb.user.entity.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 用户登录BO
 * </p>
 *
 * @author 孙凯伦
 * @since 2020-12-29
 */
@Data
@Accessors(chain = true)
@ApiModel(value="用户验证码对象", description="用户验证码对象")
public class UserSmsCodeBO implements Serializable {

    /**
     * 验证类型
     * PHONE_LOGIN：手机登录
     * PHONE_REGISTER：手机注册
     * PHONE_PASSWORD：手机修改密码
     * PHONE_VERIFY：手机验证身份
     * PHONE_CHANGE：更换手机号
     * PHONE_BIND：绑定手机
     * EMAIL_PASSWORD：邮箱更改密码
     * EMAIL_CHANGE：更换邮箱
     * EMAIL_BIND：绑定邮箱
     * EMAIL_VERIFY：验证邮箱
     * EMAIL_REGISTER：邮箱注册
     */
    @ApiModelProperty(value = "验证类型")
    private String verifyType;

    @ApiModelProperty(value = "手机号")
    private String phone;

    @ApiModelProperty(value = "邮箱")
    private String email;

    @ApiModelProperty(value = "图形验证码")
    private String verifyCode;

    @ApiModelProperty(value = "不给手机号发送短信")
    private Boolean noSendCode;
}
