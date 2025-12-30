package com.gb.account.entity.query;


import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Data;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.gb.account.entity.enums.UserOauthsStateEnum;
import com.gb.account.entity.enums.UserOauthsTypeEnum;


/**
 * Created with IntelliJ IDEA.
 * @author:     	孙凯伦
 * @since:   	    2021-10-21 01:50:37
 * @description:	TODO  用户授权表查询
 * @source:  	    代码生成器
 */
@Data
@ApiModel(value = "用户授权表查询")
public class UserOauthsQuery implements Serializable {

    @ApiModelProperty(value = "序列")
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

    @ApiModelProperty(value = "授权类型（0：微信，1：qq，2：微博，3：IOS，4：android）")
    private UserOauthsTypeEnum type;

    @ApiModelProperty(value = "状态（0：默认）")
    private UserOauthsStateEnum state;

    @ApiModelProperty(value = "标签")
    private String label;

    @ApiModelProperty(value = "排序")
    private Integer sorting;

    @ApiModelProperty(value = "显示字段")
    private String fields;

    @ApiModelProperty(value = "排序规则(0:desc,1:asc)")
    private Boolean collation;

    @ApiModelProperty(value = "排序字段")
    private String collationFields = "create_date_time";

    @ApiModelProperty(value = "模糊查询")
    private String query;

    /**
    * 查询分页方法
    */
    public interface Select{}

    /**
    * 查询方法
    */
    public interface SelectList{}

    /**
    * 单条查询
    */
    public interface SelectOne{}

    /**
    * 总数参数
    */
    public interface Count{}
}


