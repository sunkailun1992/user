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
import com.gb.account.entity.enums.UserOauthsTypeEnum;
import com.gb.account.entity.enums.UserOauthsStateEnum;
import com.gb.bean.EntityBase;
/**
 * Created with IntelliJ IDEA.
 * @author:     	孙凯伦
 * @since:   	    2021-10-21 01:50:37
 * @description:	TODO  用户授权表
 * @source:  	    代码生成器
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("`user_oauths`")
@ApiModel(value="UserOauths对象", description="用户授权表")
public class UserOauths extends EntityBase {

    @ApiModelProperty(value = "用户id")
    private String userId;

    @ApiModelProperty(value = "授权编码")
    private String oauthId;

    @ApiModelProperty(value = "统一授权编码")
    private String unionId;

    @ApiModelProperty(value = "app跳转地址")
    private String appUrl;

    @ApiModelProperty(value = "app包名")
    private String appPackage;

    @ApiModelProperty(value = "app类名")
    private String appClass;

    @ApiModelProperty(value = "授权类型（0：微信，1：qq，2：微博，3：IOS，4：android）")
    private UserOauthsTypeEnum type;

    @ApiModelProperty(value = "状态（0：默认）")
    private UserOauthsStateEnum state;

    @ApiModelProperty(value = "标签")
    private String label;
}
