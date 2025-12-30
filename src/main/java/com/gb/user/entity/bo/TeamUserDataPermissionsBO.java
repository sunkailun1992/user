package com.gb.user.entity.bo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.gb.user.entity.enums.TeamUserDataPermissionsStateEnum;
import com.gb.user.entity.enums.TeamUserDataPermissionsTypeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.time.LocalDateTime;


/**
 * TODO 团队人员数据权限传输
 * 代码生成器
 *
 * @author sunx
 * @className TeamUserDataPermissionsBO
 * @time 2022-08-30 04:44:18
 */
@Data
@ApiModel(value = "团队人员数据权限传输")
public class TeamUserDataPermissionsBO implements Serializable {

    @ApiModelProperty(value = "序列")
    @NotBlank(groups = {TeamUserDataPermissionsBO.Update.class, TeamUserDataPermissionsBO.Remove.class}, message = "id不能为空")
    private String id;

    @ApiModelProperty(value = "用户人员id")
    @NotBlank(groups = {TeamUserDataPermissionsBO.Update.class, TeamUserDataPermissionsBO.Save.class}, message = "teamUserId不能为空")
    private String teamUserId;

    @ApiModelProperty(value = "团队id")
    @NotBlank(groups = {TeamUserDataPermissionsBO.Update.class, TeamUserDataPermissionsBO.Save.class}, message = "teamId不能为空")
    private String teamId;

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
    private TeamUserDataPermissionsTypeEnum type;

    @ApiModelProperty(value = "状态（0：默认）")
    private TeamUserDataPermissionsStateEnum state;

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


