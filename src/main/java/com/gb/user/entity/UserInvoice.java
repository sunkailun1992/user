package com.gb.user.entity;

import cn.hutool.core.lang.Validator;
import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.StringUtils;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 用户发票表
 * </p>
 *
 * @author sunx
 * @since 2021-05-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("`user_invoice`")
@ApiModel(value = "UserInvoice对象", description = "用户发票表")
public class UserInvoice implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "序列")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    @ApiModelProperty(value = "用户id")
    private String userId;

    @ApiModelProperty(value = "发票抬头")
    private String invoiceLookedUp;

    @ApiModelProperty(value = "发票税号")
    private String invoiceTaxCoding;

    @ApiModelProperty(value = "发票收件邮箱")
    private String invoiceEmail;

    @ApiModelProperty(value = "注册地址")
    @NotBlank(message = "注册地址不能为空")
    private String registeredAddress;

    @ApiModelProperty(value = "注册电话")
    @NotBlank(message = "注册电话不能为空")
    private String registeredMobile;

    @ApiModelProperty(value = "开户银行")
    @NotBlank(message = "开户银行不能为空")
    private String openAccountBank;

    @ApiModelProperty(value = "银行账户")
    @NotBlank(message = "银行账户不能为空")
    private String bankCard;

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

    @ApiModelProperty(hidden = true, value = "类型（0：普票，1：专票）")
    private Integer type = null;

    @ApiModelProperty(value = "状态")
    private Integer state = null;

    @ApiModelProperty(value = "标签")
    private String label;

    @ApiModelProperty(value = "排序")
    private Integer sorting;

    @Version
    @ApiModelProperty(hidden = true, value = "版本号")
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

    @TableField(exist = false)
    @ApiModelProperty(value = "发票服务ID")
    private String invoiceServiceId;

    @TableField(exist = false)
    @ApiModelProperty(value = "投保主体（0：企业，1：个人）")
    private Integer mainBody;

    public Integer getMainBody() {
        return Validator.isCreditCode(this.invoiceTaxCoding) ? 0 : 1;
    }

}
