package com.gb.permissions.entity.query;


import com.gb.permissions.entity.enums.RoleStateEnum;
import com.gb.permissions.entity.enums.RoleTypeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;


/**
 * Created with IntelliJ IDEA.
 * @author:     	孙凯伦
 * @since:   	    2021-10-21 01:59:43
 * @description:	TODO  角色表查询
 * @source:  	    代码生成器
 */
@Data
@ApiModel(value = "角色表查询")
public class RoleQuery implements Serializable {

    @ApiModelProperty(value = "序列")
    private String id;

    @ApiModelProperty(value = "系统id")
    private String systemId;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "角色值")
    private String value;

    @ApiModelProperty(value = "说明")
    private String description;

    @ApiModelProperty(value = "类型（0：默认）")
    private RoleTypeEnum type;

    @ApiModelProperty(value = "状态（0：默认）")
    private RoleStateEnum state;

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

    @ApiModelProperty(value = "用户id")
    private String userId;

    @ApiModelProperty(value = "组id")
    private String groupId;

    @ApiModelProperty(value = "角色值列表")
    private List<String> valueList;

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