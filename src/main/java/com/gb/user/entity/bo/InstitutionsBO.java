package com.gb.user.entity.bo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.gb.user.entity.enums.InstitutionsStateEnum;
import com.gb.user.entity.enums.InstitutionsTypeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


/**
 * TODO 机构传输
 * 代码生成器
 *
 * @author sunxin
 * @className InstitutionsBO
 * @time 2022-07-04 10:48:36
 */
@Data
@ApiModel(value = "机构传输")
public class InstitutionsBO implements Serializable {

    @ApiModelProperty(value = "序列")
    @NotBlank(groups = {InstitutionsBO.Update.class, InstitutionsBO.Remove.class}, message = "id不能为空")
    private String id;

    @NotBlank(groups = {InstitutionsBO.Save.class}, message = "superiorId不能为空")
    @ApiModelProperty(value = "上级id")
    private String superiorId;

    @NotBlank(groups = {InstitutionsBO.Save.class}, message = "name不能为空")
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

    @ApiModelProperty(value = "成立时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private LocalDate startDate;

    @ApiModelProperty(value = "终止时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "GMT+8")
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

    @ApiModelProperty(value = "营业期限")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private LocalDate businessDate;

    @ApiModelProperty(value = "邮政")
    private String postal;

    @ApiModelProperty(value = "营业执照地址")
    private String businessLicense;

    @ApiModelProperty(value = "保险经纪牌照")
    private String insuranceBrokerLicense;

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

    @NotNull(groups = {InstitutionsBO.Save.class}, message = "type不能为空")
    @ApiModelProperty(value = "类型（0：总公司，1：省级分支机构，2：市级分支机构，3：区级分支机构）")
    private InstitutionsTypeEnum type;

    @NotNull(groups = {InstitutionsBO.Save.class}, message = "state不能为空")
    @ApiModelProperty(value = "状态（0：经营中，1：已退出，2：待开业）")
    private InstitutionsStateEnum state;

    @ApiModelProperty(value = "标签")
    private String label;

    @ApiModelProperty(value = "排序")
    private Integer sorting;

    @ApiModelProperty(value = "版本号")
    private Integer version;

    @ApiModelProperty(value = "机构高管列表")
    private List<InstitutionsExecutivesBO> institutionsExecutivesBOList;


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


