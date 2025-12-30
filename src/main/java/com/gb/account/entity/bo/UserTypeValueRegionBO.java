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
import com.gb.account.entity.enums.UserTypeValueRegionTypeEnum;
import com.gb.account.entity.enums.UserTypeValueRegionStateEnum;


/**
 * TODO 用户类型值地区传输
 * 代码生成器
 *
 * @author lijh
 * @className UserTypeValueRegionBO
 * @time 2022-07-12 11:45:19
 */
@Data
@ApiModel(value = "用户类型值地区传输")
public class UserTypeValueRegionBO implements Serializable {

    @ApiModelProperty(value = "序列")
    @NotBlank(groups = {UserTypeValueRegionBO.Update.class, UserTypeValueRegionBO.Remove.class}, message = "id不能为空")
    private String id;

    @ApiModelProperty(value = "用户类型值id")
    private String userTypeValueId;

    @ApiModelProperty(value = "省名称")
    private String provinceName;

    @ApiModelProperty(value = "省编码")
    private String provinceCode;

    @ApiModelProperty(value = "市名称")
    private String cityName;

    @ApiModelProperty(value = "市编码")
    private String cityCode;

    @ApiModelProperty(value = "区名称")
    private String areaName;

    @ApiModelProperty(value = "区编码")
    private String areaCode;

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

    @ApiModelProperty(value = "删除")
    private Boolean isDelete;

    @ApiModelProperty(value = "类型（0：默认）")
    private UserTypeValueRegionTypeEnum type;

    @ApiModelProperty(value = "状态（0：默认）")
    private UserTypeValueRegionStateEnum state;

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


