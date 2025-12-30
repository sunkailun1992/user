package com.gb.user.entity;


import com.baomidou.mybatisplus.annotation.TableName;
import com.gb.bean.EntityBase;
import com.gb.user.entity.enums.TeamGroupStateEnum;
import com.gb.user.entity.enums.TeamGroupTypeEnum;
import com.gb.user.entity.enums.TeamUserTypeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


/**
 * TODO 团队组别对象
 * 代码生成器
 *
 * @author sunx
 * @className TeamGroup
 * @time 2022-08-31 10:59:01
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("`team_group`")
@ApiModel(value = "TeamGroup对象", description = "团队组别")
public class TeamGroup extends EntityBase {

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "编码")
    private String code;

    @ApiModelProperty(value = "类型（0：默认）")
    private TeamGroupTypeEnum type;

    @ApiModelProperty(value = "团队人员销售类型（0：分销，1：自营）")
    private TeamUserTypeEnum teamUserType;

    @ApiModelProperty(value = "状态（0：默认）")
    private TeamGroupStateEnum state;

    @ApiModelProperty(value = "标签")
    private String label;
}
