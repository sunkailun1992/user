package com.gb.user.entity.vo;

import com.gb.user.entity.enums.TeamUserTypeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;


/**
 * TODO 团队人员渲染
 * 代码生成器
 *
 * @author sunx
 * @className TeamUserVO
 * @time 2022-08-31 11:01:59
 */
@Data
@ApiModel(value = "团队人员渲染")
public class TeamUserPolicyVO implements Serializable {

    @ApiModelProperty(value = "序列")
    private String id;

    @ApiModelProperty(value = "团队id")
    private String teamId;

    @ApiModelProperty(value = "团队名称")
    private String teamName;

    @ApiModelProperty(value = "用户id")
    private String userId;

    @ApiModelProperty(value = "用户姓名")
    private String userName;

    @ApiModelProperty(value = "团队组别id")
    private String teamGroupId;

    @ApiModelProperty(value = "团队组别名称")
    private String teamGroupName;

    @ApiModelProperty(value = "团队组别值id")
    private String teamGroupValueId;

    @ApiModelProperty(value = "团队组别值名称")
    private String teamGroupValueName;

    @ApiModelProperty(value = "成交保费")
    private Double finalPremiums;

    @ApiModelProperty(value = "应交佣金")
    private Double insuranceSettlementPremium;

    @ApiModelProperty(value = "类型（0：分销，1：自营）")
    private TeamUserTypeEnum type;
}


