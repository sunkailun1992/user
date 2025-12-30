package com.gb.account.entity.query;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.gb.account.entity.enums.UserFormalStateEnum;
import com.gb.account.entity.enums.UserStateEnum;
import com.gb.account.entity.enums.UserTypeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;


/**
 * Created with IntelliJ IDEA.
 * @author:     	孙凯伦
 * @since:   	    2021-11-03 09:16:16
 * @description:	TODO  用户表查询
 * @source:  	    代码生成器
 */
@Data
@ApiModel(value = "用户表查询")
public class UserQuery implements Serializable {

    @ApiModelProperty(value = "序列")
    private String id;

    @ApiModelProperty(value = "来源id")
    private String sourceId;

    @ApiModelProperty(value = "来源code")
    private String sourceCode;

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

    @ApiModelProperty(value = "登录时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime loginDateTime;

    @ApiModelProperty(value = "之前登录ip")
    private String beforeIp;

    @ApiModelProperty(value = "之前登录时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime beforeLoginDateTime;

    @ApiModelProperty(value = "今天登录次数")
    private Integer todayLoginCount;

    @ApiModelProperty(value = "账户是否锁定")
    private Boolean isAccountLocked;

    @ApiModelProperty(value = "属性")
    private Integer attribute;

    @ApiModelProperty(value = "业务明细")
    private String businessDetails;

    @ApiModelProperty(value = "说明")
    private String description;

    @ApiModelProperty(value = "类型（0：默认）")
    private UserTypeEnum type;

    @ApiModelProperty(value = "状态（0：默认）")
    private UserStateEnum state;

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

    @ApiModelProperty(value = "用户类型值编码")
    private String typeValueCode;

    @ApiModelProperty(value = "用户类型值id")
    private String typeValueId;

    @ApiModelProperty(value = "账号查询")
    private String userNameQuery;

    @ApiModelProperty(value = "姓名查询")
    private String nameQuery;

    @ApiModelProperty(value = "手机号模糊查询")
    private String mobileQuery;

    @ApiModelProperty(value = "手机号查询")
    private String mobile;

    @ApiModelProperty(value = "开始时间起")
    private String createDateTimeStart;

    @ApiModelProperty(value = "开始时间止")
    private String createDateTimeEnd;

    @ApiModelProperty(value = "企业ID")
    private String enterpriseId;

    @ApiModelProperty(value = "用户序列列表")
    private List<String> userIdList;

    @ApiModelProperty(value = "用户类型码值")
    private String userTypeCode;

    @ApiModelProperty(value = "是否查询用户扩展信息，默认不查询")
    private boolean isQueryUserExtendsInfo;

    @ApiModelProperty(value = "是否查询企业信息，默认不查询")
    private boolean isQueryEnterpriseInfo;

    @ApiModelProperty(value = "排除用户类型值编码")
    private String neTypeValueCode;

    @ApiModelProperty(value = "人员常规状态枚举（0：在职，1：注销，2：离职，3：修改，4：修改新增）")
    private UserFormalStateEnum userFormalStateEnum;

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


