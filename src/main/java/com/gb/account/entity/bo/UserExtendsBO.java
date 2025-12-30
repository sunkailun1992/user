package com.gb.account.entity.bo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.gb.account.entity.enums.*;
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
 * @since:   	    2021-10-21 01:50:40
 * @description:	TODO  用户扩展表传输
 * @source:  	    代码生成器
 */
@Data
@ApiModel(value = "用户扩展表传输")
public class UserExtendsBO implements Serializable {

    @ApiModelProperty(value = "序列")
    @NotBlank(groups = {UserExtendsBO.Remove.class},message = "id不能为空")
    private String id;

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
    private String birthdayStr;

    @ApiModelProperty(value = "性别（0：男，1：女）")
    private Integer sex;

    @ApiModelProperty(value = "身份证号码")
    private String idCard;

    @ApiModelProperty(value = "证件永久有效")
    private Boolean certificatePermanent;

    @ApiModelProperty(value = "证件类型（0：身份证，1：士官证，2：护照）")
    private UserExtendsCertificateTypeEnum certificateType;

    @ApiModelProperty(value = "证件有效开始时间（用于前端对证件有效开始时间进行操作，可清空字符串）")
    private String certificateStartDateStr;

    @ApiModelProperty(value = "证件有效结束时间（用于前端对证件有效结束时间进行操作，可清空字符串）")
    private String certificateEndDateStr;

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

    @ApiModelProperty(value = "入职时间（用于前端对入职时间进行操作，可清空字符串）")
    private String onboardingDateTimeStr;

    @ApiModelProperty(value = "离职时间（用于前端对离职时间进行操作，可清空字符串）")
    private String leaveDateTimeStr;

    @ApiModelProperty(value = "银行卡号")
    private String bankCardNumber;

    @ApiModelProperty(value = "所属银行")
    private String bank;

    @ApiModelProperty(value = "银行卡开户地址")
    private String openAccountAddress;

    @ApiModelProperty(value = "银行卡开户名")
    private String openAccountName;

    @ApiModelProperty(value = "合同类型（0：劳动合同，1：劳务派遣合同）")
    private UserExtendsContractTypeEnum contractType;

    @ApiModelProperty(value = "工作性质（0：正式，1：兼职）")
    private UserExtendsNatureWorkEnum natureWork;

    @ApiModelProperty(value = "合同文件地址")
    private String contractFile;

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
    private UserExtendsTypeEnum type;

    @ApiModelProperty(value = "状态（0：默认）")
    private UserExtendsStateEnum state;

    @ApiModelProperty(value = "状态（0：在职，1：注销，2：离职，3：修改，4：修改新增）")
    private UserFormalStateEnum userFormalStateEnum;

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


