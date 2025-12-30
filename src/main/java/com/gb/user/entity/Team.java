package com.gb.user.entity;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.gb.bean.EntityBase;
import com.gb.user.entity.enums.TeamStateEnum;
import com.gb.user.entity.enums.TeamTypeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


/**
 * TODO 团队对象
 * 代码生成器
 *
 * @author sunx
 * @className Team
 * @time 2022-08-30 04:44:17
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("`team`")
@ApiModel(value = "Team对象", description = "团队")
public class Team extends EntityBase {

    @ApiModelProperty(value = "上级团队id")
    private String teamId;

    @ApiModelProperty(value = "上级团队名称")
    @TableField("(select `name` from `team` u where u.`id` = `team`.`team_id`)")
    private String teamName;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "编码")
    private String code;

    @ApiModelProperty(value = "类型（0：默认）")
    private TeamTypeEnum type;

    @ApiModelProperty(value = "状态（0：默认）")
    private TeamStateEnum state;

    @ApiModelProperty(value = "标签")
    private String label;
}
