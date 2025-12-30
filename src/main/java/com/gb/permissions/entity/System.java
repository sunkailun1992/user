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
import com.gb.permissions.entity.enums.SystemTypeEnum;
import com.gb.permissions.entity.enums.SystemStateEnum;
import com.gb.bean.EntityBase;
/**
 * Created with IntelliJ IDEA.
 * @author:     	孙凯伦
 * @since:   	    2021-10-21 01:59:43
 * @description:	TODO  系统表
 * @source:  	    代码生成器
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("`system`")
@ApiModel(value="System对象", description="系统表")
public class System extends EntityBase {

    @ApiModelProperty(value = "系统url地址")
    private String url;

    @ApiModelProperty(value = "系统名字")
    private String name;

    @ApiModelProperty(value = "编码")
    private String code;

    @ApiModelProperty(value = "类型（0：默认）")
    private SystemTypeEnum type;

    @ApiModelProperty(value = "状态（0：默认）")
    private SystemStateEnum state;

    @ApiModelProperty(value = "标签")
    private String label;
}
