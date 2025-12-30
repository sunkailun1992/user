package com.gb.account.entity.query;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.gb.account.entity.enums.UserExtendsStateEnum;
import com.gb.account.entity.enums.UserExtendsTypeEnum;
import com.gb.account.entity.enums.UserFormalStateEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


/**
 * Created with IntelliJ IDEA.
 * @author:     	孙凯伦
 * @since:   	    2021-10-21 01:50:40
 * @description:	TODO  用户扩展表查询
 * @source:  	    代码生成器
 */
@Data
@ApiModel(value = "用户扩展表查询")
public class UserExtendsQuery implements Serializable {

    @ApiModelProperty(value = "序列")
    private String id;

    @ApiModelProperty(value = "用户id")
    private String userId;

    @ApiModelProperty(value = "分销上层用户id")
    private String distributionUserId;

    @ApiModelProperty(value = "姓名")
    private String name;

    @ApiModelProperty(value = "员工编码")
    private String coding;

    @ApiModelProperty(value = "生日")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private LocalDate birthday;

    @ApiModelProperty(value = "性别（0：男，1：女）")
    private Integer sex;

    @ApiModelProperty(value = "身份证号码")
    private String idCard;

    @ApiModelProperty(value = "籍贯")
    private String nativePlace;

    @ApiModelProperty(value = "省编码")
    private String provinceCode;

    @ApiModelProperty(value = "省名称")
    private String provinceName;

    @ApiModelProperty(value = "市编码")
    private String cityCode;

    @ApiModelProperty(value = "市名称")
    private String cityName;

    @ApiModelProperty(value = "区编码")
    private String areaCode;

    @ApiModelProperty(value = "区名称")
    private String areaName;

    @ApiModelProperty(value = "住址")
    private String address;

    @ApiModelProperty(value = "手机号")
    private String mobile;

    @ApiModelProperty(value = "别称")
    private String alias;

    @ApiModelProperty(value = "电话")
    private String phone;

    @ApiModelProperty(value = "邮箱")
    private String email;

    @ApiModelProperty(value = "QQ")
    private String qq;

    @ApiModelProperty(value = "微信")
    private String wechat;

    @ApiModelProperty(value = "紧急联系人")
    private String emergencyContactName;

    @ApiModelProperty(value = "紧急联系人手机")
    private String emergencyContactMobile;

    @ApiModelProperty(value = "入职时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime onboardingDateTime;

    @ApiModelProperty(value = "银行卡号")
    private String bankCardNumber;

    @ApiModelProperty(value = "所属银行")
    private String bank;

    @ApiModelProperty(value = "银行卡开户地址")
    private String openAccountAddress;

    @ApiModelProperty(value = "银行卡开户名")
    private String openAccountName;

    @ApiModelProperty(value = "说明")
    private String description;

    @ApiModelProperty(value = "类型（0：默认）")
    private UserExtendsTypeEnum type;

    @ApiModelProperty(value = "状态（0：默认）")
    private UserExtendsStateEnum state;

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

    @ApiModelProperty(value = "用户序列列表")
    private List<String> userIdList;

    @ApiModelProperty(value = "手机号列表")
    private List<String> mobileList;

    @ApiModelProperty(value = "手机号模糊查询")
    private String mobileQuery;

    @ApiModelProperty(value = "姓名模糊查询")
    private String nameQuery;

    @ApiModelProperty(value = "用户昵称模糊查询")
    private String aliasQuery;

    @ApiModelProperty(value = "状态（0：在职，1：注销，2：离职，3：修改，4：修改新增）")
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