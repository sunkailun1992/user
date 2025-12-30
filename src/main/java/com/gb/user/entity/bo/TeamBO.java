package com.gb.user.entity.bo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Data;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.gb.user.entity.enums.TeamTypeEnum;
import com.gb.user.entity.enums.TeamStateEnum;


/**
 * TODO 团队传输
 * 代码生成器
 *
 * @author sunx
 * @className TeamBO
 * @time 2022-08-30 04:44:17
 */
@Data
@ApiModel(value = "团队传输")
public class TeamBO implements Serializable {

    @ApiModelProperty(value = "序列")
    @NotBlank(groups = {TeamBO.Update.class, TeamBO.Remove.class}, message = "id不能为空")
    private String id;

    @ApiModelProperty(value = "团队id")
    @NotBlank(groups = {TeamBO.Save.class}, message = "teamId不能为空")
    private String teamId;

    @ApiModelProperty(value = "名称")
    @NotBlank(groups = {TeamBO.Save.class}, message = "name不能为空")
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
    private TeamTypeEnum type;

    @ApiModelProperty(value = "状态（0：默认）")
    private TeamStateEnum state;

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


