package com.gb.user.entity.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * <p>
 * 用户注册BO
 * </p>
 *
 * @author 孙凯伦
 * @since 2020-12-29
 */
@Data
@Accessors(chain = true)
@ApiModel(value="用户注册对象", description="用户注册对象")
public class UserRegisterBO implements Serializable {

    @ApiModelProperty(value = "手机号")
    @NotBlank(message = "手机号不能为空")
    private String phone;

    @ApiModelProperty(value = "密码")
    @NotBlank(message = "密码不能为空")
    private String password;

    @ApiModelProperty(value = "短信验证码")
    @NotBlank(message = "短信验证码不能为空")
    private String smsCode;

    @ApiModelProperty(value = "用户名")
    private String userName;

    @ApiModelProperty(value = "系统编码")
    private String appCode;

    /**
     * 经纪人id
     * APP分享的時候，会用到---通知MQ的时候
     */
    @ApiModelProperty(hidden = true, value = "经纪人id")
    private String agentUserId;
}
