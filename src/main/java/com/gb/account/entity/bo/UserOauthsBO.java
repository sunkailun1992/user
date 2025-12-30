package com.gb.account.entity.bo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Data;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.gb.account.entity.enums.UserOauthsStateEnum;
import com.gb.account.entity.enums.UserOauthsTypeEnum;


/**
 * Created with IntelliJ IDEA.
 * @author:     	孙凯伦
 * @since:   	    2021-10-21 01:50:37
 * @description:	TODO  用户授权表传输
 * @source:  	    代码生成器
 */
@Data
@ApiModel(value = "用户授权表传输")
public class UserOauthsBO implements Serializable {

    @ApiModelProperty(value = "序列")
    @NotBlank(groups = {UserOauthsBO.Update.class,UserOauthsBO.Remove.class},message = "id不能为空")
    private String id;

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

    @ApiModelProperty(value = "说明")
    private String description;

    @ApiModelProperty(value = "创建时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createDateTime;

    @ApiModelProperty(value = "创建人")
    private String createName;

    @ApiModelProperty(value = "修改时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime modifyDateTime;

    @ApiModelProperty(value = "修改人")
    private String modifyName;

    @ApiModelProperty(value = "删除状态")
    private Boolean isDelete;

    @ApiModelProperty(value = "授权类型（0：微信，1：qq，2：微博，3：IOS，4：android）")
    private UserOauthsTypeEnum type;

    @ApiModelProperty(value = "状态（0：默认）")
    private UserOauthsStateEnum state;

    @ApiModelProperty(value = "标签")
    private String label;

    @ApiModelProperty(value = "排序")
    private Integer sorting;

    @ApiModelProperty(value = "版本号")
    private Integer version;


    /**
    * 新增
    */
    public interface Save{}

    /**
    * 修改
    */
    public interface Update{}

    /**
    * 删除
    */
    public interface Remove{}
}


