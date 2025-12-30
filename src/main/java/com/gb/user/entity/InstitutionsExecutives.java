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
import com.gb.user.entity.enums.InstitutionsExecutivesTypeEnum;
import com.gb.user.entity.enums.InstitutionsExecutivesStateEnum;
import com.gb.bean.EntityBase;


/**
 * TODO 机构高管对象
 * 代码生成器
 *
 * @author sunxin
 * @className InstitutionsExecutives
 * @time 2022-07-04 10:48:36
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("`institutions_executives`")
@ApiModel(value = "InstitutionsExecutives对象", description = "机构高管")
public class InstitutionsExecutives extends EntityBase {

    @ApiModelProperty(value = "机构id")
    private String institutionsId;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "岗位")
    private String jobs;

    @ApiModelProperty(value = "类型（0：默认）")
    private InstitutionsExecutivesTypeEnum type;

    @ApiModelProperty(value = "状态（0：默认）")
    private InstitutionsExecutivesStateEnum state;

    @ApiModelProperty(value = "标签")
    private String label;
}
