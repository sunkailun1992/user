package com.gb.account.entity;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.baomidou.mybatisplus.annotation.*;
import com.gb.account.entity.enums.UserTypeValueRegionTypeEnum;
import com.gb.account.entity.enums.UserTypeValueRegionStateEnum;
import com.gb.bean.EntityBase;


/**
 * TODO 用户类型值地区对象
 * 代码生成器
 *
 * @author lijh
 * @className UserTypeValueRegion
 * @time 2022-07-12 11:45:19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("`user_type_value_region`")
@ApiModel(value = "UserTypeValueRegion对象", description = "用户类型值地区")
public class UserTypeValueRegion extends EntityBase {

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

    @ApiModelProperty(value = "类型（0：默认）")
    private UserTypeValueRegionTypeEnum type;

    @ApiModelProperty(value = "状态（0：默认）")
    private UserTypeValueRegionStateEnum state;

    @ApiModelProperty(value = "标签")
    private String label;
}
