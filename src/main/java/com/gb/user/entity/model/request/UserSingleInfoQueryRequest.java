package com.gb.user.entity.model.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 请求工保通单个用户信息查询Body类
 * </p>
 *
 * @author sunx
 * @since 2021-05-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class UserSingleInfoQueryRequest extends BaseUserRequest implements Serializable {

    @ApiModelProperty(value = "登录账号")
    private String account;

    @ApiModelProperty(value = "手机号")
    private String phone;

    @ApiModelProperty(value = "用户编码")
    private String userCode;

    @ApiModelProperty(value = "员工编号")
    private String empNo;

    @ApiModelProperty(value = "邮箱")
    private String email;
}
