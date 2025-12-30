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
import com.gb.permissions.entity.enums.ResourceApiTypeEnum;
import com.gb.permissions.entity.enums.ResourceTypeEnum;
import com.gb.permissions.entity.enums.ResourceStateEnum;
import com.gb.bean.EntityBase;
/**
 * Created with IntelliJ IDEA.
 * @author:     	孙凯伦
 * @since:   	    2021-10-21 01:59:45
 * @description:	TODO  资源表
 * @source:  	    代码生成器
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("`resource`")
@ApiModel(value="Resource对象", description="资源表")
public class Resource extends EntityBase {

    @ApiModelProperty(value = "上级id")
    private String superiorsId;

    @ApiModelProperty(value = "系统id")
    private String systemId;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "url值")
    private String value;

    @ApiModelProperty(value = "接口资源")
    private Boolean api;

    @ApiModelProperty(value = "接口类型（0：待定，1：get，2：post，3：put，4：delete）")
    private ResourceApiTypeEnum apiType;

    @ApiModelProperty(value = "按钮")
    private Boolean button;

    @ApiModelProperty(value = "导航栏")
    private Boolean navigation;

    @ApiModelProperty(value = "类型（0：默认）")
    private ResourceTypeEnum type;

    @ApiModelProperty(value = "状态（0：默认）")
    private ResourceStateEnum state;

    @ApiModelProperty(value = "标签")
    private String label;
}
