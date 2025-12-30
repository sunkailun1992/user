package com.gb.user.entity.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 用户基本信息BO
 * </p>
 *
 * @author sunx
 * @since 2020-12-29
 */
@Data
@Accessors(chain = true)
@ApiModel(value="用户基本信息BO", description="用户基本信息BO")
public class UserBasicInfoBO implements Serializable {

    @ApiModelProperty(value = "用户序列")
    private Long id;

    @ApiModelProperty(value = "手机号")
    private String mobile;

    @ApiModelProperty(value = "姓名")
    private String name;

    @ApiModelProperty(value = "用户账户")
    private String userName;

    @ApiModelProperty(value = "昵称")
    private String alias;

    @ApiModelProperty(value = "用户头像地址")
    private String avatar;

    @ApiModelProperty(value = "邮箱")
    private String email;

    @ApiModelProperty(value = "职位")
    private String positions;

    @ApiModelProperty(value = "最后一次登录时间")
    private String lastLogin;

    @ApiModelProperty(value = "用户来源Code")
    private String sourceCode;

    @ApiModelProperty(value = "平台编码")
    private String platformCode;

    @ApiModelProperty(value = "平台名称")
    private String platformName;

    @ApiModelProperty(value = "用户web客户端IP地址")
    private String ip;

    @ApiModelProperty(value = "组列表")
    private Object groupList;

    @ApiModelProperty(value = "系统标志")
    private String appCode;

    @ApiModelProperty(value = "创建时间")
    private String createDate;

    @ApiModelProperty(value = "性别")
    private String sex;

    @ApiModelProperty(value = "出生日期")
    private String birthday;

    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "来源ID")
    private String sourceId;

    @ApiModelProperty(value = "来源值ID")
    private String sourceValueId;

    @ApiModelProperty(value = "业务明细")
    private String businessDetails;

}
