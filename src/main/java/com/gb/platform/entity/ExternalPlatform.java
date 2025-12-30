package com.gb.platform.entity;


import com.baomidou.mybatisplus.annotation.TableName;
import com.gb.bean.EntityBase;
import com.gb.platform.entity.enums.ExternalPlatformStateEnum;
import com.gb.platform.entity.enums.ExternalPlatformTypeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


/**
 * TODO 外部平台对象
 * 代码生成器
 *
 * @author sunx
 * @className ExternalPlatform
 * @time 2022-12-16 03:10:07
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("`external_platform`")
@ApiModel(value = "ExternalPlatform对象", description = "外部平台")
public class ExternalPlatform extends EntityBase {

    @ApiModelProperty(value = "系统code")
    private String externalSystemCode;

    @ApiModelProperty(value = "系统名称")
    private String externalSystemName;

    @ApiModelProperty(value = "编码")
    private String code;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "类型（0：默认）")
    private ExternalPlatformTypeEnum type;

    @ApiModelProperty(value = "状态（0：默认）")
    private ExternalPlatformStateEnum state;

    @ApiModelProperty(value = "标签")
    private String label;
}
