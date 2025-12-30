package com.gb.account.entity;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.gb.account.entity.enums.UserTypeValueStateEnum;
import com.gb.account.entity.enums.UserTypeValueTypeEnum;
import com.gb.bean.EntityBase;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
/**
 * Created with IntelliJ IDEA.
 * @author:     	孙凯伦
 * @since:   	    2021-10-21 01:49:04
 * @description:	TODO  用户类型值表
 * @source:  	    代码生成器
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("`user_type_value`")
@ApiModel(value="UserTypeValue对象", description="用户类型值表")
public class UserTypeValue extends EntityBase {

    @ApiModelProperty(value = "用户类型id")
    private String userTypeId;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "编码")
    private String code;

    @ApiModelProperty(value = "类型（0：默认）")
    private UserTypeValueTypeEnum type;

    @ApiModelProperty(value = "状态（0：默认）")
    private UserTypeValueStateEnum state;

    @ApiModelProperty(value = "标签")
    private String label;

    @TableField("(select `name` from `user_type` where `user_type`.`id` = `user_type_value`.`user_type_id`)")
    @ApiModelProperty(value = "用户类型名称")
    private String userTypeName;

    @TableField("(select `code` from `user_type` where `user_type`.`id` = `user_type_value`.`user_type_id`)")
    @ApiModelProperty(value = "用户类型码值")
    private String userTypeCode;
}
