package com.gb.user.entity;

import cn.hutool.core.date.LocalDateTimeUtil;
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
 * 用户消息表
 * </p>
 *
 * @author ranyang
 * @since 2021-06-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("`user_message`")
@ApiModel(value="UserMessage对象", description="用户消息表")
public class UserMessage implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "序列")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    @ApiModelProperty(value = "用户id")
    private String userId;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "内容")
    private String content;

    @ApiModelProperty(value = "跳转地址")
    private String jumpAddress;

    @ApiModelProperty(value = "根据时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime accordingDate;

    @ApiModelProperty(value = "是否阅读（0：未读，1：已读）")
    private Boolean reading;

    @ApiModelProperty(value = "用户类型（0：前端用户，1：管理用户）")
    private Integer userType;

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

    @ApiModelProperty(value = "类型（0：普通通知，1：重要通知，2：紧急通知，3：提醒）")
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

    /**
     * 当前查询结果返回时间
     */
    @TableField(exist = false)
    private String currDate = LocalDateTimeUtil.formatNormal(LocalDateTime.now());
}
