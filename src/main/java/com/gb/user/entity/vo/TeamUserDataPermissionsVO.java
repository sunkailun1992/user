package com.gb.user.entity.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.gb.user.entity.enums.TeamUserDataPermissionsStateEnum;
import com.gb.user.entity.enums.TeamUserDataPermissionsTypeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;


/**
 * TODO 团队人员数据权限渲染
 * 代码生成器
 *
 * @author sunx
 * @className TeamUserDataPermissionsVO
 * @time 2022-08-30 04:44:18
 */
@Data
@ApiModel(value = "团队人员数据权限渲染")
public class TeamUserDataPermissionsVO implements Serializable {

    @ApiModelProperty(value = "序列")
    private String id;

    @ApiModelProperty(value = "用户人员id")
    private String teamUserId;

    @ApiModelProperty(value = "用户人员姓名")
    private String teamUserName;

    @ApiModelProperty(value = "团队id")
    private String teamId;

    @ApiModelProperty(value = "团队名称")
    private String teamName;

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
}


