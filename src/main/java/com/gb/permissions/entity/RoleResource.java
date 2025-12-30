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
import com.gb.permissions.entity.enums.RoleResourceTypeEnum;
import com.gb.permissions.entity.enums.RoleResourceStateEnum;
import com.gb.bean.EntityBase;
/**
 * Created with IntelliJ IDEA.
 * @author:     	孙凯伦
 * @since:   	    2021-10-21 01:59:42
 * @description:	TODO  角色资源表
 * @source:  	    代码生成器
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("`role_resource`")
@ApiModel(value="RoleResource对象", description="角色资源表")
public class RoleResource extends EntityBase {

    @ApiModelProperty(value = "角色id")
    private String roleId;

    @ApiModelProperty(value = "资源id")
    private String resourceId;

    @ApiModelProperty(value = "类型（0：默认）")
    private RoleResourceTypeEnum type;

    @ApiModelProperty(value = "状态（0：默认）")
    private RoleResourceStateEnum state;

    @ApiModelProperty(value = "标签")
    private String label;
}
