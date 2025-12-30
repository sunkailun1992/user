package com.gb.quotation.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.IdType;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import java.util.List;

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
 * 报价方案
 * </p>
 *
 * @author 尹涛涛
 * @since 2021-05-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("`quotation_scheme`")
@ApiModel(value="QuotationScheme对象", description="报价方案")
public class QuotationScheme implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "序列")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    @ApiModelProperty(value = "用户id")
    private String userId;

    @ApiModelProperty(value = "险种分类id")
    private String dangerPlantedCategoryId;

    @ApiModelProperty(value = "险种分类名称")
    private String dangerPlantedCategoryName;

    @ApiModelProperty(value = "险种id")
    private String dangerPlantedId;

    @TableField(exist = false)
    @ApiModelProperty(value = "险种Logo")
    private String dangerPlantedLogo;

    @ApiModelProperty(value = "险种名称")
    private String dangerPlantedName;

    @ApiModelProperty(value = "咨询人姓名")
    private String name;

    @ApiModelProperty(value = "咨询人电话号码")
    private String mobile;

    @TableField(exist = false)
    @ApiModelProperty(value = "报价方案产品")
    private List<QuotationSchemeSpu> quotationSchemeSpuList;

    @TableField(exist = false)
    @ApiModelProperty(value = "其他产品推荐")
    private List<QuotationSchemeOther> quotationSchemeOtherList;

    @ApiModelProperty(value = "企业资质")
    private String enterpriseQualification;

    @ApiModelProperty(value = "项目工期（月）")
    private Integer projectTimeLimit;

    @ApiModelProperty(value = "项目总投资")
    private String projectInvestment;

    @ApiModelProperty(value = "项目保证金")
    private BigDecimal projectMargin;

    @ApiModelProperty(value = "pdf下载地址")
    private String  downloadUrl;

    @TableField(exist = false)
    @ApiModelProperty(value = "经纪人")
    private String  agent;

    @TableField(exist = false)
    @ApiModelProperty(value = "工号")
    private String  workNo;

    @TableField(exist = false)
    @ApiModelProperty(value = "咨询人电话")
    private String  agentMobile;

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
    private String collationFields = "modify_date_time";

    @TableField(exist = false)
    @ApiModelProperty(value = "模糊查询")
    private String query;

    @TableField(exist = false)
    private String tempPath;

    @TableField(exist = false)
    private String pdfPath;

}
