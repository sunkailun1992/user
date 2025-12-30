package com.gb.platform.entity.query;


import com.gb.platform.entity.enums.TransformationExternalPlatformSystemStateEnum;
import com.gb.platform.entity.enums.TransformationExternalPlatformSystemTypeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;


/**
 * TODO 转化外部系统平台查询
 * 代码生成器
 *
 * @author sunx
 * @className TransformationExternalPlatformSystemQuery
 * @time 2022-12-16 03:10:08
 */
@Data
@ApiModel(value = "转化外部系统平台查询")
public class TransformationExternalPlatformSystemQuery implements Serializable {

    @ApiModelProperty(value = "序列")
    private String id;

    @ApiModelProperty(value = "外部平台id")
    private String externalPlatformId;

    @ApiModelProperty(value = "外部平台名称模糊查询")
    private String externalPlatformNameQuery;

    @ApiModelProperty(value = "外部平台编码")
    private String externalPlatformCode;

    @ApiModelProperty(value = "外部系统id")
    private String externalSystemId;

    @ApiModelProperty(value = "外部系统名称模糊查询")
    private String externalSystemNameQuery;

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

    @ApiModelProperty(value = "说明")
    private String description;

    @ApiModelProperty(value = "类型（0：默认）")
    private TransformationExternalPlatformSystemTypeEnum type;

    @ApiModelProperty(value = "状态（0：启用，1：不启用）")
    private TransformationExternalPlatformSystemStateEnum state;

    @ApiModelProperty(value = "标签")
    private String label;

    @ApiModelProperty(value = "排序")
    private Integer sorting;

    @ApiModelProperty(value = "追加")
    private Boolean assignment;

    @ApiModelProperty(value = "显示字段")
    private String fields;

    @ApiModelProperty(value = "排序规则(0:desc,1:asc)")
    private Boolean collation;

    @ApiModelProperty(value = "排序字段")
    private String collationFields = "create_date_time";

    @ApiModelProperty(value = "模糊查询")
    private String query;

    @ApiModelProperty(value = "权限用户id")
    private String authUserId;

    /**
     * 查询分页方法
     */
    public interface Select{}

    /**
     * 查询方法
     */
    public interface SelectList{}

    /**
     * 单条查询
     */
    public interface SelectOne{}

    /**
     * 总数参数
     */
    public interface Count{}
}


