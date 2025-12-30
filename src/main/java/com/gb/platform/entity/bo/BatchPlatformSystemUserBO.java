package com.gb.platform.entity.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;


/**
 * TODO 批量外部系统更新用户BO
 * 代码生成器
 *
 * @author sunx
 * @className BatchPlatformSystemUserBO
 * @time 2022-12-16 03:10:09
 */
@Data
@ApiModel(value = "批量外部系统更新用户BO")
public class BatchPlatformSystemUserBO implements Serializable {

    @ApiModelProperty(value = "用户id")
    private String userId;

    @ApiModelProperty(value = "外部平台系统列表")
    private List<ExternalPlatformSystemBO> externalPlatformSystemList;

    @ApiModelProperty(value = "修改人名称")
    private String modifyName;

    @ApiModelProperty(value = "全部")
    private Boolean all;
}


