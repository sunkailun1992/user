package com.gb.user.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 邀请链接BO
 * </p>
 *
 * @author 孙馨
 * @since 2020-12-29
 */
@Data
@ApiModel(value = "邀请链接传输")
public class InviteLinkVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "类型  0：外部经纪人  1：业务经纪人")
    private Integer type;

    @ApiModelProperty(value = "用户唯一标志")
    private String userId;

    @ApiModelProperty(value = "用户邀请链接")
    private String userAppointLink;

    @ApiModelProperty(value = "业务预约链接")
    private String businessBookedLink;

    @ApiModelProperty(value = "证件号")
    private String certificateCode;

    @ApiModelProperty(value = "真实姓名")
    private String name;

    @ApiModelProperty(value = "手机号")
    private String mobile;

}
