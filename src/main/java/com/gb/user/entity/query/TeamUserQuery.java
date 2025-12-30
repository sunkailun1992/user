package com.gb.user.entity.query;


import com.gb.account.entity.enums.UserFormalStateEnum;
import com.gb.user.entity.enums.TeamUserStateEnum;
import com.gb.user.entity.enums.TeamUserTypeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;


/**
 * TODO 团队人员查询
 * 代码生成器
 *
 * @author sunx
 * @className TeamUserQuery
 * @time 2022-08-31 11:01:59
 */
@Data
@ApiModel(value = "团队人员查询")
public class TeamUserQuery implements Serializable {

    @ApiModelProperty(value = "序列")
    private String id;

    @ApiModelProperty(value = "团队id")
    private String teamId;

    @ApiModelProperty(value = "用户id")
    private String userId;

    @ApiModelProperty(value = "用户姓名")
    private String userName;

    @ApiModelProperty(value = "手机号")
    private String mobile;

    @ApiModelProperty(value = "助理id")
    private String assistantUserId;

    @ApiModelProperty(value = "团队组别id")
    private String teamGroupId;

    @ApiModelProperty(value = "团队组别值id")
    private String teamGroupValueId;

    @ApiModelProperty(value = "团队组别值限制id")
    private String teamGroupValueLimitId;

    @ApiModelProperty(value = "个人")
    private Boolean personal;

    @ApiModelProperty(value = "渠道")
    private Boolean channel;

    @ApiModelProperty(value = "说明")
    private String description;

    @ApiModelProperty(value = "类型（0：分销，1：直营）")
    private TeamUserTypeEnum type;

    @ApiModelProperty(value = "状态（0：默认）")
    private TeamUserStateEnum state;

    @ApiModelProperty(value = "标签")
    private String label;

    @ApiModelProperty(value = "排序")
    private Integer sorting;

    @ApiModelProperty(value = "追加")
    private Boolean assignment;

    @ApiModelProperty(value = "团队本级及下级查询")
    private Boolean teamLevelQuery;

    @ApiModelProperty(value = "显示字段")
    private String fields;

    @ApiModelProperty(value = "排序规则(0:desc,1:asc)")
    private Boolean collation;

    @ApiModelProperty(value = "排序字段")
    private String collationFields = "create_date_time";

    @ApiModelProperty(value = "用户姓名模糊查询")
    private String userNameQuery;

    @ApiModelProperty(value = "团队ID列表")
    private List<String> teamIdList;

    @ApiModelProperty(value = "查询结果Map返回")
    private Boolean backMapQueryResult;

    @ApiModelProperty(value = "查询结果Map返回")
    private UserFormalStateEnum userFormalStateEnum;

    @ApiModelProperty(value = "外部平台code")
    private String externalPlatformCode;

    @ApiModelProperty(value = "外部系统id")
    private String externalSystemId;

    @ApiModelProperty(value = "转化外部系统平台id")
    private String transformationExternalPlatformSystemId;

    @ApiModelProperty(value = "用户id列表")
    private List<String> userIdList;

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


