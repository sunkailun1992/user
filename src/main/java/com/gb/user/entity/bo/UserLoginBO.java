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
@ApiModel(value="用户登录对象", description="用户登录对象")
public class UserLoginBO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "手机号或账号必填一个）")
    private String phone;

    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "账号 （手机号或账号必填一个）")
    private String userName;

    @ApiModelProperty(value = "验证码")
    private String verifyCode;

    @ApiModelProperty(value = "平台授权码")
    private String appCode;
}
