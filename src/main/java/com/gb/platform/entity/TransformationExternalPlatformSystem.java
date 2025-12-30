package com.gb.platform.entity;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.gb.bean.EntityBase;
import com.gb.platform.entity.enums.TransformationExternalPlatformSystemStateEnum;
import com.gb.platform.entity.enums.TransformationExternalPlatformSystemTypeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


/**
 * TODO 转化外部系统平台对象
 * 代码生成器
 *
 * @author sunx
 * @className TransformationExternalPlatformSystem
 * @time 2022-12-16 03:10:08
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("`transformation_external_platform_system`")
@ApiModel(value = "TransformationExternalPlatformSystem对象", description = "转化外部系统平台")
public class TransformationExternalPlatformSystem extends EntityBase {

    @ApiModelProperty(value = "外部平台id")
    private String externalPlatformId;

    @ApiModelProperty(value = "外部平台编码")
    private String externalPlatformCode;

    @ApiModelProperty(value = "外部系统id")
    private String externalSystemId;

    @ApiModelProperty(value = "外部系统编码")
    private String externalSystemCode;

    @ApiModelProperty(value = "省编码")
    private String provinceCode;

    @ApiModelProperty(value = "省名称")
    private String provinceName;

    @ApiModelProperty(value = "市编码")
    private String cityCode;

    @ApiModelProperty(value = "市名称")
    private String cityName;

    @ApiModelProperty(value = "区编码")
    private String areaCode;

    @ApiModelProperty(value = "区名称")
    private String areaName;

    @ApiModelProperty(value = "外部平台系统地址")
    private String linkAddress;

    @ApiModelProperty(value = "类型（0：默认）")
    private TransformationExternalPlatformSystemTypeEnum type;

    @ApiModelProperty(value = "状态（0：启用，1：不启用）")
    private TransformationExternalPlatformSystemStateEnum state;

    @ApiModelProperty(value = "标签")
    private String label;

    @ApiModelProperty(value = "外部平台名称")
    @TableField("(select `name` from `external_platform` where `external_platform`.`code` = `transformation_external_platform_system`.`external_platform_code` limit 1)")
    private String externalPlatformName;

    @ApiModelProperty(value = "外部系统名称")
    @TableField("(select `name` from `external_system` where `external_system`.`id` = `transformation_external_platform_system`.`external_system_id`)")
    private String externalSystemName;
}
