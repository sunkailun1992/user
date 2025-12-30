package com.gb.account.entity.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.gb.account.entity.enums.UserTypeValueRelationshipStateEnum;
import com.gb.account.entity.enums.UserTypeValueRelationshipTypeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;


/**
 * Created with IntelliJ IDEA.
 * @author:     	孙凯伦
 * @since:   	    2021-10-21 01:49:05
 * @description:	TODO  用户类型值关联渲染
 * @source:  	    代码生成器
 */
@Data
@ApiModel(value = "用户类型值关联渲染")
public class UserTypeValueRelationshipVO implements Serializable {

    @ApiModelProperty(value = "序列")
    private String id;

    @ApiModelProperty(value = "用户id")
    private String userId;

    @ApiModelProperty(value = "用户类型值id")
    private String userTypeValueId;

    @ApiModelProperty(value = "用户类型值名称")
    private String userTypeValueName;

    @ApiModelProperty(value = "用户类型值码值")
    private String userTypeValueCode;

    @ApiModelProperty(value = "用户类型id")
    private String userTypeId;

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
    private UserTypeValueRelationshipTypeEnum type;

    @ApiModelProperty(value = "状态（0：默认）")
    private UserTypeValueRelationshipStateEnum state;

    @ApiModelProperty(value = "标签")
    private String label;

    @ApiModelProperty(value = "排序")
    private Integer sorting;

    @ApiModelProperty(value = "版本号")
    private Integer version;
}


