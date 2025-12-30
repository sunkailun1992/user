package com.gb.user.entity.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * <p>
 * 用户日志VO
 * </p>
 *
 * @author sunx
 * @since 2020-12-29
 */
@Data
public class UserLogVO implements Serializable {

    @ApiModelProperty(value = "日志序列")
    private String id;

    @ApiModelProperty(value = "登录ip")
    private String ip;

    @ApiModelProperty(value = "百度地区")
    private String baiduArea;

    @ApiModelProperty(value = "省名称")
    private String provinceName;

    @ApiModelProperty(value = "市名称")
    private String cityName;

    @ApiModelProperty(value = "区名称")
    private String areaName;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createDateTime;

}
