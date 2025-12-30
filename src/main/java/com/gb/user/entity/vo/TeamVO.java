package com.gb.user.entity.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.gb.user.entity.TeamTreeNode;
import com.gb.user.entity.enums.TeamStateEnum;
import com.gb.user.entity.enums.TeamTypeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;


/**
 * TODO 团队渲染
 * 代码生成器
 *
 * @author sunx
 * @className TeamVO
 * @time 2022-08-30 04:44:17
 */
@Data
@ApiModel(value = "团队渲染")
public class TeamVO implements Serializable {

    @ApiModelProperty(value = "序列")
    private String id;

    @ApiModelProperty(value = "上级团队id")
    private String teamId;

    @ApiModelProperty(value = "上级团队名称")
    private String teamName;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "编码")
    private String code;

    @ApiModelProperty(value = "团队人员总数")
    private Integer teamUserTotal;

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
    private TeamTypeEnum type;

    @ApiModelProperty(value = "状态（0：默认）")
    private TeamStateEnum state;

    @ApiModelProperty(value = "标签")
    private String label;

    @ApiModelProperty(value = "排序")
    private Integer sorting;

    @ApiModelProperty(value = "版本号")
    private Integer version;

    @ApiModelProperty(value = "子节点")
    private List<TeamTreeNode> children;
}


