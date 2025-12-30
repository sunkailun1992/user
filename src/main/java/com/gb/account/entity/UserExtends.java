package com.gb.account.entity;


import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.gb.account.entity.enums.*;
import com.gb.bean.EntityBase;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;
/**
 * Created with IntelliJ IDEA.
 * @author:     	孙凯伦
 * @since:   	    2021-10-21 01:50:40
 * @description:	TODO  用户扩展表
 * @source:  	    代码生成器
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("`user_extends`")
@ApiModel(value="UserExtends对象", description="用户扩展表")
public class UserExtends extends EntityBase {

    @ApiModelProperty(value = "用户id")
    private String userId;

    @ApiModelProperty(value = "分销上层用户id")
    private String distributionUserId;

    @ApiModelProperty(value = "姓名")
    private String name;

    @ApiModelProperty(value = "别名")
    private String alias;

    @ApiModelProperty(value = "员工编码")
    private String coding;

    @ApiModelProperty(value = "生日")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private LocalDate birthday;

    @ApiModelProperty(value = "性别（0：男，1：女）")
    private UserExtendsSexEnum sex;

    @ApiModelProperty(value = "身份证号码")
    private String idCard;

    @ApiModelProperty(value = "证件永久有效")
    private Boolean certificatePermanent;

    @ApiModelProperty(value = "证件有效开始时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private LocalDate certificateStartDate;

    @ApiModelProperty(value = "证件有效结束时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private LocalDate certificateEndDate;

    @ApiModelProperty(value = "证件类型（0：身份证，1：士官证，2：护照）")
    private UserExtendsCertificateTypeEnum certificateType;

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

    @ApiModelProperty(value = "离职时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime leaveDateTime;

    @ApiModelProperty(value = "银行卡号")
    private String bankCardNumber;

    @ApiModelProperty(value = "所属银行")
    private String bank;

    @ApiModelProperty(value = "银行卡开户地址")
    private String openAccountAddress;

    @ApiModelProperty(value = "银行卡开户名")
    private String openAccountName;

    @ApiModelProperty(value = "合同类型（0：劳动合同，1：劳动派遣合同）")
    private UserExtendsContractTypeEnum contractType;

    @ApiModelProperty(value = "工作性质（0：正式，1：兼职）")
    private UserExtendsNatureWorkEnum natureWork;

    @ApiModelProperty(value = "合同文件地址")
    private String contractFile;

    @ApiModelProperty(value = "类型（0：默认）")
    private UserExtendsTypeEnum type;

    @ApiModelProperty(value = "状态（0：默认）")
    private UserExtendsStateEnum state;

    @ApiModelProperty(value = "标签")
    private String label;
}
