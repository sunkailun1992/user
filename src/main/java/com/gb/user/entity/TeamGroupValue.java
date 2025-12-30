package com.gb.user.entity;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.baomidou.mybatisplus.annotation.*;
import com.gb.user.entity.enums.TeamGroupValueTypeEnum;
import com.gb.user.entity.enums.TeamGroupValueStateEnum;
import com.gb.bean.EntityBase;


/**
 * TODO 团队组别值对象
 * 代码生成器
 *
 * @author sunx
 * @className TeamGroupValue
 * @time 2022-08-31 10:59:01
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("`team_group_value`")
@ApiModel(value = "TeamGroupValue对象", description = "团队组别值")
public class TeamGroupValue extends EntityBase {

    @ApiModelProperty(value = "团队组别id")
    private String teamGroupId;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "编码")
    private String code;

    @ApiModelProperty(value = "类型（0：默认）")
    private TeamGroupValueTypeEnum type;

    @ApiModelProperty(value = "状态（0：默认）")
    private TeamGroupValueStateEnum state;

    @ApiModelProperty(value = "标签")
    private String label;
}
