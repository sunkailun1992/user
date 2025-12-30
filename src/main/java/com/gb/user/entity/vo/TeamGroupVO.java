package com.gb.user.entity.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.gb.user.entity.enums.TeamGroupStateEnum;
import com.gb.user.entity.enums.TeamGroupTypeEnum;
import com.gb.user.entity.enums.TeamUserTypeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;


/**
 * TODO 团队组别渲染
 * 代码生成器
 *
 * @author sunx
 * @className TeamGroupVO
 * @time 2022-08-31 10:59:01
 */
@Data
@ApiModel(value = "团队组别渲染")
public class TeamGroupVO implements Serializable {

    @ApiModelProperty(value = "序列")
    private String id;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "编码")
    private String code;

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
    private TeamGroupTypeEnum type;

    @ApiModelProperty(value = "团队人员销售类型（0：分销，1：自营）")
    private TeamUserTypeEnum teamUserType;

    @ApiModelProperty(value = "状态（0：默认）")
    private TeamGroupStateEnum state;

    @ApiModelProperty(value = "标签")
    private String label;

    @ApiModelProperty(value = "排序")
    private Integer sorting;

    @ApiModelProperty(value = "版本号")
    private Integer version;
}


