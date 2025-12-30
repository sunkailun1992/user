package com.gb.permissions.entity.query;


import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Data;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.gb.permissions.entity.enums.ResourceStateEnum;
import com.gb.permissions.entity.enums.ResourceTypeEnum;


/**
 * Created with IntelliJ IDEA.
 * @author:     	孙凯伦
 * @since:   	    2021-10-26 10:26:28
 * @description:	TODO  资源表查询
 * @source:  	    代码生成器
 */
@Data
@ApiModel(value = "资源表查询")
public class ResourceQuery implements Serializable {

    @ApiModelProperty(value = "序列")
    private String id;

    @ApiModelProperty(value = "上级id")
    private String superiorsId;

    @ApiModelProperty(value = "系统id")
    private String systemId;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "url值")
    private String value;

    @ApiModelProperty(value = "接口资源")
    private Boolean api;

    @ApiModelProperty(value = "接口类型（0：待定，1：get，2：post，3：put，4：delete）")
    private Integer apiType;

    @ApiModelProperty(value = "按钮")
    private Boolean button;

    @ApiModelProperty(value = "导航栏")
    private Boolean navigation;

    @ApiModelProperty(value = "说明")
    private String description;

    @ApiModelProperty(value = "类型（0：默认）")
    private ResourceTypeEnum type;

    @ApiModelProperty(value = "状态（0：默认）")
    private ResourceStateEnum state;

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

    @ApiModelProperty(value = "角色id")
    private String roleId;

    @ApiModelProperty(value = "用户id")
    private String userId;

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


