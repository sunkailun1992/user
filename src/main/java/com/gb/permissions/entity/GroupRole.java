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
import com.gb.permissions.entity.enums.GroupRoleTypeEnum;
import com.gb.permissions.entity.enums.GroupRoleStateEnum;
import com.gb.bean.EntityBase;
/**
 * Created with IntelliJ IDEA.
 * @author:     	孙凯伦
 * @since:   	    2021-10-21 01:59:44
 * @description:	TODO  角色用户组
 * @source:  	    代码生成器
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("`group_role`")
@ApiModel(value="GroupRole对象", description="角色用户组")
public class GroupRole extends EntityBase {

    @ApiModelProperty(value = "组id")
    private String groupId;

    @ApiModelProperty(value = "角色id")
    private String roleId;

    @ApiModelProperty(value = "类型（0：默认）")
    private GroupRoleTypeEnum type;

    @ApiModelProperty(value = "状态（0：默认）")
    private GroupRoleStateEnum state;

    @ApiModelProperty(value = "标签")
    private String label;
}
