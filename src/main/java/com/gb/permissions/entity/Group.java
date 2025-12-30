package com.gb.permissions.entity;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.baomidou.mybatisplus.annotation.*;
import com.gb.permissions.entity.enums.GroupTypeEnum;
import com.gb.permissions.entity.enums.GroupStateEnum;
import com.gb.bean.EntityBase;
/**
 * Created with IntelliJ IDEA.
 * @author:     	孙凯伦
 * @since:   	    2021-10-21 01:59:44
 * @description:	TODO  组
 * @source:  	    代码生成器
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("`group`")
@ApiModel(value="Group对象", description="组")
public class Group extends EntityBase {

    @ApiModelProperty(value = "系统id")
    private String systemId;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "类型（0：默认）")
    private GroupTypeEnum type;

    @ApiModelProperty(value = "状态（0：默认）")
    private GroupStateEnum state;

    @ApiModelProperty(value = "标签")
    private String label;

    @TableField("(select `name` from `system` where `system`.`id` = `group`.`system_id`)")
    @ApiModelProperty(value = "系统名称")
    private String systemName;
}
