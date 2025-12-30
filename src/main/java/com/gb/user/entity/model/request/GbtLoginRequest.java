package com.gb.user.entity.model.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 请求工保通登录Body类
 * </p>
 *
 * @author sunx
 * @since 2021-05-25
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class GbtLoginRequest extends BaseUserRequest implements Serializable {

    @ApiModelProperty(value = "业务类型（MOBILE：手机登录，EMAIL：邮件登录，ACCOUNT：账号登录）")
    private String busType;

    @ApiModelProperty(value = "验证码")
    private String verifyCode;

    @ApiModelProperty(value = "账号（busType为ACCOUNT时必填）")
    private String account;

    @ApiModelProperty(value = "密码（busType为MOBILE时非必填）")
    private String password;

    @ApiModelProperty(value = "手机号（busType为MOBILE时必填）")
    private String phone;

    @ApiModelProperty(value = "邮箱（busType为EMAIL时必填）")
    private String email;
}
