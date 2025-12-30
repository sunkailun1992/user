package com.gb.account.entity.query;


import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Data;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.gb.account.entity.enums.UserTypeValueRegionTypeEnum;
import com.gb.account.entity.enums.UserTypeValueRegionStateEnum;


/**
 * TODO 用户类型值地区查询
 * 代码生成器
 *
 * @author lijh
 * @className UserTypeValueRegionQuery
 * @time 2022-07-12 11:45:19
 */
@Data
@ApiModel(value = "用户类型值地区查询")
public class UserTypeValueRegionQuery implements Serializable {

    @ApiModelProperty(value = "序列")
    private String id;

    @ApiModelProperty(value = "用户类型值id")
    private String userTypeValueId;

    @ApiModelProperty(value = "省名称")
    private String provinceName;

    @ApiModelProperty(value = "省编码")
    private String provinceCode;

    @ApiModelProperty(value = "市名称")
    private String cityName;

    @ApiModelProperty(value = "市编码")
    private String cityCode;

    @ApiModelProperty(value = "区名称")
    private String areaName;

    @ApiModelProperty(value = "区编码")
    private String areaCode;

    @ApiModelProperty(value = "说明")
    private String description;

    @ApiModelProperty(value = "类型（0：默认）")
    private UserTypeValueRegionTypeEnum type;

    @ApiModelProperty(value = "状态（0：默认）")
    private UserTypeValueRegionStateEnum state;

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


