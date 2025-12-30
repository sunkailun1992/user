package com.gb.user.entity.model.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 请求工保通校验身份请求类
 * </p>
 *
 * @author sunx
 * @since 2021-05-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class UserVerifyIdentityRequest extends BaseUserRequest implements Serializable {

    @ApiModelProperty(value = "手机号")
    private String phone;

    @ApiModelProperty(value = "验证码")
    private String verifyCode;

    @ApiModelProperty(value = "验证类型（PHONE_LOGIN：手机登录，PHONE_REGISTER：手机注册，PHONE_PASSWORD：手机修改密码，PHONE_VERIFY：手机验证身份，PHONE_CHANGE：更换手机号，PHONE_BIND：绑定手机，EMAIL_LOGIN：邮箱登录，EMAIL_REGISTER：邮箱注册，EMAIL_PASSWORD：邮箱更改密码，EMAIL_VERIFY：验证邮箱，EMAIL_CHANGE：更换邮箱，EMAIL_BIND：绑定邮箱）")
    private String verifyType;
}
