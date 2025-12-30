package com.gb.account.entity.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.gb.account.entity.enums.UserStateEnum;
import com.gb.account.entity.enums.UserTypeEnum;
import com.gb.permissions.entity.vo.GroupVO;
import com.gb.permissions.entity.vo.RoleVO;
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
 * @since:   	    2021-10-21 11:06:07
 * @description:	TODO  用户表渲染
 * @source:  	    代码生成器
 */
@Data
@ApiModel(value = "用户表渲染")
public class UserVO implements Serializable {

    @ApiModelProperty(value = "序列")
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

    @ApiModelProperty(value = "之前登录时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
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

    @ApiModelProperty(value = "业务明细名称")
    private String userSource;

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

    @ApiModelProperty(value = "姓名")
    private String name;

    @ApiModelProperty(value = "手机号")
    private String mobile;

    @ApiModelProperty(value = "来源名称")
    private String sourceName;

    @ApiModelProperty(value = "来源值名称")
    private String sourceValueName;

    @ApiModelProperty(value = "当前登录平台")
    private String appCode;

    @ApiModelProperty(value = "用户扩展")
    private UserExtendsVO userExtends;

    @ApiModelProperty(value = "标签值")
    private List<UserTypeValueVO> valueList;

    @ApiModelProperty(value = "角色")
    private List<RoleVO> roleList;

    @ApiModelProperty(value = "组")
    private List<GroupVO> groupList;

    @ApiModelProperty(hidden = true, value = "关联企业名称列表")
    private List<Object> linkEnterpriseNameList;
}


