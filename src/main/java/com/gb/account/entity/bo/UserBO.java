package com.gb.account.entity.bo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.gb.account.entity.enums.UserFormalStateEnum;
import com.gb.account.entity.enums.UserStateEnum;
import com.gb.account.entity.enums.UserTypeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.time.LocalDateTime;


/**
 * Created with IntelliJ IDEA.
 * @author:     	孙凯伦
 * @since:   	    2021-10-21 11:06:07
 * @description:	TODO  用户表传输
 * @source:  	    代码生成器
 */
@Data
@ApiModel(value = "用户表传输")
public class UserBO implements Serializable {

    @ApiModelProperty(value = "序列")
    @NotBlank(groups = {UserBO.Update.class,UserBO.Remove.class},message = "id不能为空")
    private String id;

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

    @ApiModelProperty(value = "登录时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
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

    @ApiModelProperty(value = "类型（0：默认）")
    private UserTypeEnum type;

    @ApiModelProperty(value = "状态（0：默认）")
    private UserStateEnum state;

    @ApiModelProperty(value = "标签")
    private String label;

    @ApiModelProperty(value = "排序")
    private Integer sorting;

    @ApiModelProperty(value = "版本号")
    private Integer version;

    @ApiModelProperty(value = "角色Id集合")
    private String[] roleIdList;

    @ApiModelProperty(value = "角色code集合")
    private String[] roleCode;

    @ApiModelProperty(value = "组集合")
    private String[] groupList;

    @ApiModelProperty(value = "用户类型值id集合")
    private String[] typeValueId;

    @ApiModelProperty(value = "用户类型值code集合")
    private String[] typeValueCode;

    @ApiModelProperty(value = "用户扩展")
    private UserExtendsBO userExtendsBO;

    @ApiModelProperty(value = "更新类型（UPDATE_USER-修改个人信息，UPDATE_PASSWORD-修改密码，UPDATE_PHONE-更换手机号，UPDATE_EMAIL-更换邮箱，UPDATE_PROFILE- 账号设置）")
    private String updateType;

    @ApiModelProperty(value = "人员常规状态枚举（0：在职，1：注销，2：离职，3：修改, 4：修改新增）")
    private UserFormalStateEnum userFormalStateEnum;

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


