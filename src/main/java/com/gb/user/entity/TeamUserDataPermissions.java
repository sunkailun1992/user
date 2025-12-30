package com.gb.user.entity;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.gb.bean.EntityBase;
import com.gb.user.entity.enums.TeamUserDataPermissionsStateEnum;
import com.gb.user.entity.enums.TeamUserDataPermissionsTypeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


/**
 * TODO 团队人员数据权限对象
 * 代码生成器
 *
 * @author sunx
 * @className TeamUserDataPermissions
 * @time 2022-08-30 04:44:18
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("`team_user_data_permissions`")
@ApiModel(value = "TeamUserDataPermissions对象", description = "团队人员数据权限")
public class TeamUserDataPermissions extends EntityBase {

    @ApiModelProperty(value = "用户人员id")
    private String teamUserId;

    @ApiModelProperty(value = "用户人员姓名")
    @TableField("(select `name` from `user_extends` where `user_extends`.`user_id` = `team_user_data_permissions`.`team_user_id`)")
    private String teamUserName;

    @ApiModelProperty(value = "团队id")
    private String teamId;

    @ApiModelProperty(value = "团队姓名")
    @TableField("(select `name` from `team` where `team`.`id` = `team_user_data_permissions`.`team_id`)")
    private String teamName;

    @ApiModelProperty(value = "类型（0：默认）")
    private TeamUserDataPermissionsTypeEnum type;

    @ApiModelProperty(value = "状态（0：默认）")
    private TeamUserDataPermissionsStateEnum state;

    @ApiModelProperty(value = "标签")
    private String label;
}
