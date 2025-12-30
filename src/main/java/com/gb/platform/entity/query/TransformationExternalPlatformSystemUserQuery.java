package com.gb.platform.entity.query;


import com.gb.platform.entity.enums.TransformationExternalPlatformSystemUserStateEnum;
import com.gb.platform.entity.enums.TransformationExternalPlatformSystemUserTypeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;


/**
 * TODO 转化外部系统平台用户关联查询
 * 代码生成器
 *
 * @author sunx
 * @className TransformationExternalPlatformSystemUserQuery
 * @time 2022-12-16 03:10:09
 */
@Data
@ApiModel(value = "转化外部系统平台用户关联查询")
public class TransformationExternalPlatformSystemUserQuery implements Serializable {

    @ApiModelProperty(value = "序列")
    private String id;

    @ApiModelProperty(value = "转化外部系统平台id")
    private String transformationExternalPlatformSystemId;

    @ApiModelProperty(value = "线索")
    private Boolean clue;

    @ApiModelProperty(value = "用户id")
    private String userId;

    @ApiModelProperty(value = "说明")
    private String description;

    @ApiModelProperty(value = "类型（0：默认）")
    private TransformationExternalPlatformSystemUserTypeEnum type;

    @ApiModelProperty(value = "状态（0：默认）")
    private TransformationExternalPlatformSystemUserStateEnum state;

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

    @ApiModelProperty(value = "外部系统名称模糊查询")
    private String externalSystemNameQuery;

    @ApiModelProperty(value = "省编码")
    private String provinceCode;

    @ApiModelProperty(value = "市编码")
    private String cityCode;

    @ApiModelProperty(value = "区编码")
    private String areaCode;

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


