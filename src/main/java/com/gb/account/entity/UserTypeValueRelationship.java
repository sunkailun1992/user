package com.gb.account.entity;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.gb.account.entity.enums.UserTypeValueRelationshipStateEnum;
import com.gb.account.entity.enums.UserTypeValueRelationshipTypeEnum;
import com.gb.bean.EntityBase;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
/**
 * Created with IntelliJ IDEA.
 * @author:     	孙凯伦
 * @since:   	    2021-10-21 01:49:05
 * @description:	TODO  用户类型值关联
 * @source:  	    代码生成器
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("`user_type_value_relationship`")
@ApiModel(value="UserTypeValueRelationship对象", description="用户类型值关联")
public class UserTypeValueRelationship extends EntityBase {

    @ApiModelProperty(value = "用户id")
    private String userId;

    @ApiModelProperty(value = "用户类型值id")
    private String userTypeValueId;

    @TableField("(select `name` from `user_type_value` where `user_type_value`.`id` = `user_type_value_relationship`.`user_type_value_id`)")
    @ApiModelProperty(value = "用户类型值名称")
    private String userTypeValueName;

    @TableField("(select `code` from `user_type_value` where `user_type_value`.`id` = `user_type_value_relationship`.`user_type_value_id`)")
    @ApiModelProperty(value = "用户类型值码值")
    private String userTypeValueCode;

    @ApiModelProperty(value = "类型（0：默认）")
    private UserTypeValueRelationshipTypeEnum type;

    @ApiModelProperty(value = "状态（0：默认）")
    private UserTypeValueRelationshipStateEnum state;

    @ApiModelProperty(value = "标签")
    private String label;
}
