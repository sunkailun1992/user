package com.gb.user.entity.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 区对象
 * </p>
 *
 * @author 孙凯伦
 * @since 2020-12-29
 */
@Data
public class AreaBO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "省代码")
    private String provinceCode;

    @ApiModelProperty(value = "省名称")
    private  String provinceName;

    @ApiModelProperty(value = "市代码")
    private String cityCode;

    @ApiModelProperty(value = "市名称")
    private  String cityName;

    @ApiModelProperty(value = "区代码")
    private String areaCode;

    @ApiModelProperty(value = "区名称")
    private  String areaName;




}
