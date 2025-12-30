package com.gb.user.entity.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 用户快速登录BO
 * </p>
 *
 * @author 孙凯伦
 * @since 2020-12-29
 */
@Data
@Accessors(chain = true)
@ApiModel(value="用户快速登录对象", description="用户快速登录对象")
public class UserQuickLoginBO implements Serializable {

    @ApiModelProperty(value = "手机号")
    private String mobile;

    @ApiModelProperty(value = "短信验证码")
    private String smsCode;

    @ApiModelProperty(value = "系统编码")
    private String appCode;

    /**
     * 经纪人id
     * APP分享的時候，会用到---通知MQ的时候
     */
    @ApiModelProperty(value = "经纪人id")
    private String agentUserId;

//    /**
//     * 报价工具1.0.0-邀请好友id---通知MQ的时候
//     */
//    private String inviteUserId;

}
