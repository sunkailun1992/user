package com.gb.platform.entity.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;


/**
 * TODO 外部系统平台BO
 * 代码生成器
 *
 * @author sunx
 * @className ExternalPlatformSystemBO
 * @time 2022-12-16 03:10:09
 */
@Data
@ApiModel(value = "外部系统平台BO")
public class ExternalPlatformSystemBO implements Serializable {

    @ApiModelProperty(value = "外部系统Code")
    private String externalSystemCode;

    @ApiModelProperty(value = "外部平台Code")
    private String externalPlatformCode;

    @ApiModelProperty(value = "线索")
    private Boolean clue;
}


