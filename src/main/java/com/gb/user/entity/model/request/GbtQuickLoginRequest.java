package com.gb.user.entity.model.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 请求工保通快速登录Body类
 * </p>
 *
 * @author sunx
 * @since 2021-05-25
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper=false)
public class GbtQuickLoginRequest extends BaseUserRequest implements Serializable {

    @ApiModelProperty(value = "手机号")
    private String phone;

    @ApiModelProperty(value = "验证码")
    private String verifyCode;

    @ApiModelProperty(value = "密码")
    private String password;
}
