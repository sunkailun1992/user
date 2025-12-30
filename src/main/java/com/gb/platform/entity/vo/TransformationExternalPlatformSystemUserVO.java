package com.gb.platform.entity.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.gb.platform.entity.enums.TransformationExternalPlatformSystemUserStateEnum;
import com.gb.platform.entity.enums.TransformationExternalPlatformSystemUserTypeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;


/**
 * TODO 转化外部系统平台用户关联渲染
 * 代码生成器
 *
 * @author sunx
 * @className TransformationExternalPlatformSystemUserVO
 * @time 2022-12-16 03:10:09
 */
@Data
@ApiModel(value = "转化外部系统平台用户关联渲染")
public class TransformationExternalPlatformSystemUserVO implements Serializable {

    @ApiModelProperty(value = "序列")
    private String id;

    @ApiModelProperty(value = "转化外部系统平台id")
    private String transformationExternalPlatformSystemId;

    @ApiModelProperty(value = "用户id")
    private String userId;

    @ApiModelProperty(value = "线索")
    private Boolean clue;

    @ApiModelProperty(value = "说明")
    private String description;

    @ApiModelProperty(value = "创建时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createDateTime;

    @ApiModelProperty(value = "创建人")
    private String createName;

    @ApiModelProperty(value = "修改时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime modifyDateTime;

    @ApiModelProperty(value = "修改人")
    private String modifyName;

    @ApiModelProperty(value = "删除状态")
    private Boolean isDelete;

    @ApiModelProperty(value = "类型（0：默认）")
    private TransformationExternalPlatformSystemUserTypeEnum type;

    @ApiModelProperty(value = "状态（0：默认）")
    private TransformationExternalPlatformSystemUserStateEnum state;

    @ApiModelProperty(value = "标签")
    private String label;

    @ApiModelProperty(value = "排序")
    private Integer sorting;

    @ApiModelProperty(value = "版本号")
    private Integer version;

    @ApiModelProperty(value = "外部平台id")
    private String externalPlatformId;

    @ApiModelProperty(value = "外部平台名称")
    private String externalPlatformName;

    @ApiModelProperty(value = "外部平台编码")
    private String externalPlatformCode;

    @ApiModelProperty(value = "外部系统id")
    private String externalSystemId;

    @ApiModelProperty(value = "外部系统名称")
    private String externalSystemName;

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
}


