package com.gb.user.entity.bo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.gb.user.entity.enums.TeamUserStateEnum;
import com.gb.user.entity.enums.TeamUserTypeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;


/**
 * TODO 团队人员传输
 * 代码生成器
 *
 * @author sunx
 * @className TeamUserBO
 * @time 2022-08-31 11:01:59
 */
@Data
@ApiModel(value = "团队人员传输")
public class TeamUserBO implements Serializable {

    @ApiModelProperty(value = "序列")
    @NotBlank(groups = {TeamUserBO.Update.class, TeamUserBO.Remove.class}, message = "id不能为空")
    private String id;

    @ApiModelProperty(value = "团队id")
    @NotBlank(groups = {TeamUserBO.Save.class}, message = "teamId不能为空")
    private String teamId;

    @ApiModelProperty(value = "用户id")
    @NotBlank(groups = {TeamUserBO.Save.class}, message = "userId不能为空")
    private String userId;

    @ApiModelProperty(value = "助理id")
    @NotBlank(groups = {TeamUserBO.Save.class}, message = "assistantUserId不能为空")
    private String assistantUserId;

    @ApiModelProperty(value = "团队组别id")
    private String teamGroupId;

    @ApiModelProperty(value = "团队组别值id")
    private String teamGroupValueId;

    @ApiModelProperty(value = "团队组别值限制id")
    private String teamGroupValueLimitId;

    @ApiModelProperty(value = "个人")
    @NotNull(groups = {TeamUserBO.Save.class}, message = "personal不能为空")
    private Boolean personal;

    @ApiModelProperty(value = "团体权限团队ID数组")
    private String[] authTeamIdArray;

    @ApiModelProperty(value = "渠道")
    private Boolean channel;

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

    @ApiModelProperty(value = "类型（0：分销，1：自营）")
    @NotNull(groups = {TeamUserBO.Save.class}, message = "type不能为空")
    private TeamUserTypeEnum type;

    @ApiModelProperty(value = "状态（0：默认）")
    private TeamUserStateEnum state;

    @ApiModelProperty(value = "标签")
    private String label;

    @ApiModelProperty(value = "排序")
    private Integer sorting;

    @ApiModelProperty(value = "版本号")
    private Integer version;


    /**
     * 新增
     */
    public interface Save{}

    /**
     * 修改
     */
    public interface Update{}

    /**
     * 删除
     */
    public interface Remove{}
}


