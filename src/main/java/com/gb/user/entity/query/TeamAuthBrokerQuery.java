package com.gb.user.entity.query;


import com.gb.user.entity.enums.TeamUserTypeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;


/**
 * TODO 团队人员查询
 * 代码生成器
 *
 * @author sunx
 * @className TeamAuthBrokerQuery
 * @time 2022-08-31 11:01:59
 */
@Data
@ApiModel(value = "团队权限经纪人")
public class TeamAuthBrokerQuery implements Serializable {

    @ApiModelProperty(value = "团队id")
    private String teamId;

    @ApiModelProperty(value = "团队成员id")
    private String teamUserId;

    @ApiModelProperty(value = "权限用户id")
    private String authUserId;

    @ApiModelProperty(value = "团队成员名称模糊查询")
    private String teamUserNameQuery;

    @ApiModelProperty(value = "签单开始时间")
    @NotBlank(groups = {TeamAuthBrokerQuery.Select.class}, message = "signingStartDate不能为空")
    private String signingStartDate;

    @ApiModelProperty(value = "签单结束时间")
    @NotBlank(groups = {TeamAuthBrokerQuery.Select.class}, message = "signingEndDate不能为空")
    private String signingEndDate;

    @ApiModelProperty(value = "查询结果是否分组")
    private Boolean backResultGroup;

    @ApiModelProperty(value = "团队本级及下级查询")
    private Boolean teamLevelQuery;

    @ApiModelProperty(value = "类型（0：分销，1：直营）")
    private TeamUserTypeEnum type;

    /**
     *  团队权限经纪人查询方法
     */
    public interface Select {}
}