package com.gb.user.entity.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 用户扩展信息BO
 * </p>
 *
 * @author 孙凯伦
 * @since 2020-12-29
 */
@Data
@Accessors(chain = true)
public class UserExtendsInfoBO implements Serializable {

    @ApiModelProperty(value = "用户名称")
    private String userName;

    @ApiModelProperty(value = "用户编码")
    private String userCode;

    @ApiModelProperty(value = "登录名称")
    private String account;

    @ApiModelProperty(value = "员工编号")
    private String empNo;

    @ApiModelProperty(value = "昵称")
    private String nickname;

    @ApiModelProperty(value = "用户类型")
    private String userType;

    @ApiModelProperty(value = "真实姓名")
    private String realName;

    @ApiModelProperty(value = "头像")
    private String avatar;

    @ApiModelProperty(value = "手机号")
    private String phone;

    @ApiModelProperty(value = "邮箱")
    private String email;

    @ApiModelProperty(value = "职位")
    private String positions;

    @ApiModelProperty(value = "用户来源")
    private String source;

    @ApiModelProperty(value = "状态（0：正常，1：禁用）")
    private Integer status;

    @ApiModelProperty(value = "性别（0：未知，1：男，2：女）")
    private Integer sex;

    @ApiModelProperty(value = "地址")
    private String address;

    @ApiModelProperty(value = "所在城市")
    private String cityCode;

    @ApiModelProperty(value = "生日")
    private String birthday;

}
