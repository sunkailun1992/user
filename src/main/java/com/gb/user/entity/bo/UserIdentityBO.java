package com.gb.user.entity.bo;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 用户身份认证BO
 * </p>
 *
 * @author sunx
 * @since 2020-12-29
 */
@Data
@Accessors(chain = true)
@ApiModel(value="用户身份认证BO", description="用户身份认证BO")
public class UserIdentityBO implements Serializable {

    @ApiModelProperty(value = "手机号")
    private String mobile;

    @ApiModelProperty(value = "验证类型（PHONE_LOGIN：手机登录，PHONE_REGISTER：手机注册，PHONE_PASSWORD：手机修改密码，PHONE_VERIFY：手机验证身份，PHONE_CHANGE：更换手机号，PHONE_BIND：绑定手机，EMAIL_LOGIN：邮箱登录，EMAIL_REGISTER：邮箱注册，EMAIL_PASSWORD：邮箱更改密码，EMAIL_VERIFY：验证邮箱，EMAIL_CHANGE：更换邮箱，EMAIL_BIND：绑定邮箱）")
    private String verifyType;

    @ApiModelProperty(value = "短信验证码")
    private String smsCode;

}