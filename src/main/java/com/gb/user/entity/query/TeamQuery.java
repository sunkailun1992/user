package com.gb.user.entity.query;


import com.gb.user.entity.enums.TeamStateEnum;
import com.gb.user.entity.enums.TeamTypeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;


/**
 * TODO 团队查询
 * 代码生成器
 *
 * @author sunx
 * @className TeamQuery
 * @time 2022-08-30 04:44:17
 */
@Data
@ApiModel(value = "团队查询")
public class TeamQuery implements Serializable {

    @ApiModelProperty(value = "序列")
    private String id;

    @ApiModelProperty(value = "团队id")
    private String teamId;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "编码")
    private String code;

    @ApiModelProperty(value = "说明")
    private String description;

    @ApiModelProperty(value = "类型（0：默认）")
    private TeamTypeEnum type;

    @ApiModelProperty(value = "状态（0：默认）")
    private TeamStateEnum state;

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

    @ApiModelProperty(value = "名称模糊查询")
    private String nameQuery;

    @ApiModelProperty(value = "权限用户Id")
    private String authUserId;

    @ApiModelProperty(value = "团队序列List")
    private List<String> idList;

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


