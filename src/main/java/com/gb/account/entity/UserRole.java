package com.gb.account.entity;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.baomidou.mybatisplus.annotation.*;
import com.gb.account.entity.enums.UserRoleTypeEnum;
import com.gb.account.entity.enums.UserRoleStateEnum;
import com.gb.bean.EntityBase;
/**
 * Created with IntelliJ IDEA.
 * @author:     	孙凯伦
 * @since:   	    2021-10-21 01:50:37
 * @description:	TODO  用户角色表
 * @source:  	    代码生成器
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("`user_role`")
@ApiModel(value="UserRole对象", description="用户角色表")
public class UserRole extends EntityBase {

    @ApiModelProperty(value = "用户id")
    private String userId;

    @ApiModelProperty(value = "角色id")
    private String roleId;

    @ApiModelProperty(value = "类型（0：默认）")
    private UserRoleTypeEnum type;

    @ApiModelProperty(value = "状态（0：默认）")
    private UserRoleStateEnum state;

    @ApiModelProperty(value = "标签")
    private String label;

    @TableField("(select `user_name` from `user` where `user`.`id` = `user_role`.`user_id`)")
    @ApiModelProperty(value = "用户名")
    private String userName;

    @TableField("(select `name` from `user_extends` where `user_extends`.`user_id` = `user_role`.`user_id`)")
    @ApiModelProperty(value = "姓名")
    private String name;

    @TableField("(select `mobile` from `user_extends` where `user_extends`.`user_id` = `user_role`.`user_id`)")
    @ApiModelProperty(value = "手机号")
    private String mobile;
}
