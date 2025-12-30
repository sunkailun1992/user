package com.gb.account.entity;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.gb.account.entity.enums.UserStateEnum;
import com.gb.account.entity.enums.UserTypeEnum;
import com.gb.bean.EntityBase;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
/**
 * Created with IntelliJ IDEA.
 * @author:     	孙凯伦
 * @since:   	    2021-10-21 11:06:07
 * @description:	TODO  用户表
 * @source:  	    代码生成器
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("`user`")
@ApiModel(value="User对象", description="用户表")
public class User extends EntityBase {

    @ApiModelProperty(value = "来源id")
    private String sourceId;

    @ApiModelProperty(value = "来源值id")
    private String sourceValueId;

    @ApiModelProperty(value = "用户名")
    private String userName;

    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "用户头像地址")
    private String avatarUrl;

    @ApiModelProperty(value = "登录ip")
    private String ip;

    @ApiModelProperty(value = "登录ip地址")
    private String ipAddress;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(value = "登录时间")
    private LocalDateTime loginDateTime;

    @ApiModelProperty(value = "之前登录ip")
    private String beforeIp;

    @ApiModelProperty(value = "之前登录ip地址")
    private String beforeIpAddress;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(value = "之前登录时间")
    private LocalDateTime beforeLoginDateTime;

    @ApiModelProperty(value = "今天登录次数")
    private Integer todayLoginCount;

    @ApiModelProperty(value = "月登录次数")
    private Integer monthlyLoginCount;

    @ApiModelProperty(value = "年登录次数")
    private Integer yearsLoginCount;

    @ApiModelProperty(value = "总登录次数")
    private Integer loginCount;

    @ApiModelProperty(value = "账户是否锁定")
    private Boolean isAccountLocked;

    @ApiModelProperty(value = "属性")
    private Integer attribute;

    @ApiModelProperty(value = "业务明细")
    private String businessDetails;

    @ApiModelProperty(value = "类型（0：默认）")
    private UserTypeEnum type;

    @ApiModelProperty(value = "状态（0：正常，1：注销）")
    private UserStateEnum state;

    @ApiModelProperty(value = "标签")
    private String label;

    @TableField("(select `name` from `user_extends` where `user_extends`.`user_id` = `user`.`id`)")
    @ApiModelProperty(value = "姓名")
    private String name;

    @TableField("(select `mobile` from `user_extends` where `user_extends`.`user_id` = `user`.`id`)")
    @ApiModelProperty(value = "手机号")
    private String mobile;

    @TableField("(select `name` from `source` where `source`.`id` = `user`.`source_id`)")
    @ApiModelProperty(value = "来源名称")
    private String sourceName;

    @TableField("(select `name` from `source_value` where `source_value`.`id` = `user`.`source_value_id`)")
    @ApiModelProperty(value = "来源值名称")
    private String sourceValueName;
}
