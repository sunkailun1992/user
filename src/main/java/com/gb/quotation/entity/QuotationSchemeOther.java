package com.gb.quotation.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.IdType;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.baomidou.mybatisplus.annotation.*;

/**
 * <p>
 * 报价方案其他
 * </p>
 *
 * @author 尹涛涛
 * @since 2021-05-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("`quotation_scheme_other`")
@ApiModel(value="QuotationSchemeOther对象", description="报价方案其他")
public class QuotationSchemeOther implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "序列")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    @ApiModelProperty(value = "报价方案id")
    private String quotationSchemeId;

    @ApiModelProperty(value = "险种名称")
    private String dangerPlantedName;

    @ApiModelProperty(value = "保证金金额")
    private BigDecimal marginAmount;

    @ApiModelProperty(value = "保障期限")
    private Integer insuranceTimeLimit;

    @ApiModelProperty(value = "费率")
    private BigDecimal fixedRate;

    @ApiModelProperty(value = "保费金额")
    private BigDecimal payMoney;

    @ApiModelProperty(value = "说明")
    private String description;

    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createDateTime;

    @ApiModelProperty(value = "创建人")
    private String createName;

    @ApiModelProperty(value = "修改时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime modifyDateTime;

    @ApiModelProperty(value = "修改人")
    private String modifyName;

    @ApiModelProperty(value = "删除状态（0：未删除，1：删除）")
    @TableLogic
    private Boolean isDelete;

    @ApiModelProperty(value = "类型")
    private Integer type;

    @ApiModelProperty(value = "状态")
    private Integer state;

    @ApiModelProperty(value = "标签")
    private String label;

    @ApiModelProperty(value = "排序")
    private Integer sorting;
    
    @Version
    @ApiModelProperty(hidden = true,value = "版本号")
    private Integer version;

    @TableField(exist = false)
    @ApiModelProperty(value = "显示字段")
    private String fields;

    @TableField(exist = false)
    @ApiModelProperty(value = "排序规则(0:desc,1:asc)")
    private Boolean collation;

    @TableField(exist = false)
    @ApiModelProperty(value = "排序字段")
    private String collationFields = "create_date_time";

    @TableField(exist = false)
    @ApiModelProperty(value = "模糊查询")
    private String query;


}
