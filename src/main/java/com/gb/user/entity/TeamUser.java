package com.gb.user.entity;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.gb.bean.EntityBase;
import com.gb.user.entity.enums.TeamUserStateEnum;
import com.gb.user.entity.enums.TeamUserTypeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


/**
 * TODO 团队人员对象
 * 代码生成器
 *
 * @author sunx
 * @className TeamUser
 * @time 2022-08-31 11:01:59
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("`team_user`")
@ApiModel(value = "TeamUser对象", description = "团队人员")
public class TeamUser extends EntityBase {

    @ApiModelProperty(value = "团队id")
    private String teamId;

    @ApiModelProperty(value = "团队名称")
    @TableField("(select `name` from `team` where `team`.`id` = `team_user`.`team_id`)")
    private String teamName;

    @ApiModelProperty(value = "用户id")
    private String userId;

    @ApiModelProperty(value = "用户姓名")
    @TableField("(select `name` from `user_extends` where `user_extends`.`user_id` = `team_user`.`user_id`)")
    private String userName;

    @ApiModelProperty(value = "助理id")
    private String assistantUserId;

    @ApiModelProperty(value = "团队组别id")
    private String teamGroupId;

    @ApiModelProperty(value = "团队组别名称")
    @TableField("(select `name` from `team_group` where `team_group`.`id` = `team_user`.`team_group_id`)")
    private String teamGroupName;

    @ApiModelProperty(value = "团队组别值id")
    private String teamGroupValueId;

    @ApiModelProperty(value = "团队组别值名称")
    @TableField("(select `name` from `team_group_value` where `team_group_value`.`id` = `team_user`.`team_group_value_id`)")
    private String teamGroupValueName;

    @ApiModelProperty(value = "团队组别值限制id")
    private String teamGroupValueLimitId;

    @ApiModelProperty(value = "团队组别值限制数量")
    @TableField("(select `number` from `team_group_value_limit` where `team_group_value_limit`.`id` = `team_user`.`team_group_value_limit_id`)")
    private Integer teamGroupValueLimitNum;

    @ApiModelProperty(value = "个人")
    private Boolean personal;

    @ApiModelProperty(value = "渠道")
    private Boolean channel;

    @ApiModelProperty(value = "类型（0：分销，1：直营）")
    private TeamUserTypeEnum type;

    @ApiModelProperty(value = "状态（0：默认）")
    private TeamUserStateEnum state;

    @ApiModelProperty(value = "标签")
    private String label;
}
