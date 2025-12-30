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
import com.gb.user.entity.enums.TeamGroupValueLimitTypeEnum;
import com.gb.user.entity.enums.TeamGroupValueLimitStateEnum;
import com.gb.bean.EntityBase;


/**
 * TODO 团队组别限制对象
 * 代码生成器
 *
 * @author sunx
 * @className TeamGroupValueLimit
 * @time 2022-08-31 10:59:01
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("`team_group_value_limit`")
@ApiModel(value = "TeamGroupValueLimit对象", description = "团队组别限制")
public class TeamGroupValueLimit extends EntityBase {

    @ApiModelProperty(value = "团队组别值id")
    private String teamGroupValueId;

    @ApiModelProperty(value = "限制数量")
    private Integer number;

    @ApiModelProperty(value = "类型（0：默认）")
    private TeamGroupValueLimitTypeEnum type;

    @ApiModelProperty(value = "状态（0：默认）")
    private TeamGroupValueLimitStateEnum state;

    @ApiModelProperty(value = "标签")
    private String label;
}
