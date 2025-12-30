package com.gb.platform.entity;


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
import com.gb.platform.entity.enums.ExternalSystemTypeEnum;
import com.gb.platform.entity.enums.ExternalSystemStateEnum;
import com.gb.bean.EntityBase;


/**
 * TODO 外部系统对象
 * 代码生成器
 *
 * @author sunx
 * @className ExternalSystem
 * @time 2022-12-16 03:10:08
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("`external_system`")
@ApiModel(value = "ExternalSystem对象", description = "外部系统")
public class ExternalSystem extends EntityBase {

    @ApiModelProperty(value = "编码")
    private String code;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "类型（0：默认）")
    private ExternalSystemTypeEnum type;

    @ApiModelProperty(value = "状态（0：默认）")
    private ExternalSystemStateEnum state;

    @ApiModelProperty(value = "标签")
    private String label;
}
