package com.gb.user.entity.query;


import com.gb.user.entity.enums.TeamUserDataPermissionsStateEnum;
import com.gb.user.entity.enums.TeamUserDataPermissionsTypeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;


/**
 * TODO 团队人员数据权限查询
 * 代码生成器
 *
 * @author sunx
 * @className TeamUserDataPermissionsQuery
 * @time 2022-08-30 04:44:18
 */
@Data
@ApiModel(value = "团队人员数据权限查询")
public class TeamUserDataPermissionsQuery implements Serializable {

    @ApiModelProperty(value = "序列")
    private String id;

    @ApiModelProperty(value = "用户人员id")
    private String teamUserId;

    @ApiModelProperty(value = "团队id")
    private String teamId;

    @ApiModelProperty(value = "说明")
    private String description;

    @ApiModelProperty(value = "类型（0：默认）")
    private TeamUserDataPermissionsTypeEnum type;

    @ApiModelProperty(value = "状态（0：默认）")
    private TeamUserDataPermissionsStateEnum state;

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


