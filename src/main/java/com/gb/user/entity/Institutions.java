package com.gb.user.entity;


import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.gb.bean.EntityBase;
import com.gb.user.entity.enums.InstitutionsStateEnum;
import com.gb.user.entity.enums.InstitutionsTypeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;


/**
 * TODO 机构对象
 * 代码生成器
 *
 * @author sunxin
 * @className Institutions
 * @time 2022-07-04 10:48:36
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("`institutions`")
@ApiModel(value = "Institutions对象", description = "机构")
public class Institutions extends EntityBase {

    @ApiModelProperty(value = "上级id")
    private String superiorId;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "编码")
    private String code;

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

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @ApiModelProperty(value = "成立时间")
    private LocalDate startDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @ApiModelProperty(value = "终止时间")
    private LocalDate endDate;

    @ApiModelProperty(value = "办公地址")
    private String address;

    @ApiModelProperty(value = "电话")
    private String phone;

    @ApiModelProperty(value = "传真")
    private String fax;

    @ApiModelProperty(value = "注册资本")
    private String registeredCapital;

    @ApiModelProperty(value = "法人机构")
    private Boolean institutionalInvestor;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @ApiModelProperty(value = "营业期限")
    private LocalDate businessDate;

    @ApiModelProperty(value = "邮政")
    private String postal;

    @ApiModelProperty(value = "营业执照地址")
    private String businessLicense;

    @ApiModelProperty(value = "保险经纪牌照")
    private String insuranceBrokerLicense;

    @ApiModelProperty(value = "类型（0：总公司，1：省级分支机构，2：市级分支机构，3：区级分支机构）")
    private InstitutionsTypeEnum type;

    @ApiModelProperty(value = "状态（0：经营中，1：已退出，2：待开业）")
    private InstitutionsStateEnum state;

    @ApiModelProperty(value = "标签")
    private String label;
}
