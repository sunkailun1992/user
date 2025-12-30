package com.gb.user.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;


/**
 * TODO 团队权限人员VO
 * 代码生成器
 *
 * @author sunx
 * @className TeamUserDataPermissionsVO
 * @time 2022-08-30 04:44:18
 */
@Data
@ApiModel(value = "所属团队人员VO")
public class TeamGroupUserVO implements Serializable {

    @ApiModelProperty(value = "团队id")
    private String teamId;

    @ApiModelProperty(value = "团队名称")
    private String teamName;

    @ApiModelProperty(value = "团队权限人员列表")
    List<TeamUserVO> teamUserList;
}