package com.gb.account.entity.query;


import com.gb.account.entity.enums.UserTypeValueRelationshipStateEnum;
import com.gb.account.entity.enums.UserTypeValueRelationshipTypeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;


/**
 * Created with IntelliJ IDEA.
 * @author:     	孙凯伦
 * @since:   	    2021-10-21 01:49:05
 * @description:	TODO  用户类型值关联查询
 * @source:  	    代码生成器
 */
@Data
@ApiModel(value = "用户类型值关联查询")
public class UserTypeValueRelationshipQuery implements Serializable {

    @ApiModelProperty(value = "序列")
    private String id;

    @ApiModelProperty(value = "用户id")
    private String userId;

    @ApiModelProperty(value = "用户类型值id")
    private String userTypeValueId;

    @ApiModelProperty(value = "说明")
    private String description;

    @ApiModelProperty(value = "类型（0：默认）")
    private UserTypeValueRelationshipTypeEnum type;

    @ApiModelProperty(value = "状态（0：默认）")
    private UserTypeValueRelationshipStateEnum state;

    @ApiModelProperty(value = "标签")
    private String label;

    @ApiModelProperty(value = "排序")
    private Integer sorting;

    @ApiModelProperty(value = "显示字段")
    private String fields;

    @ApiModelProperty(value = "排序规则(0:desc,1:asc)")
    private Boolean collation;

    @ApiModelProperty(value = "排序字段")
    private String collationFields = "create_date_time";

    @ApiModelProperty(value = "模糊查询")
    private String query;

    @ApiModelProperty(value = "用户标签id")
    private String userTypeId;

    @ApiModelProperty(value = "用户标签值code")
    private String userTypeValueCode;

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


