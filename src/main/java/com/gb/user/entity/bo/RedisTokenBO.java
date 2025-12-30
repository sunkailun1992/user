package com.gb.user.entity.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;


/**
 * TODO Redis鉴权令牌BO
 * 代码生成器
 *
 * @author sunx
 * @className RedisTokenBO
 * @time 2022-01-20 03:40:09
 */
@Data
@ApiModel(value = "Redis鉴权令牌BO")
public class RedisTokenBO implements Serializable {

    @ApiModelProperty(value = "缓存KEY结尾")
    private String keyTail;

    @ApiModelProperty(value = "超时时间")
    private Long timeOut;

    @ApiModelProperty(value = "超时单位")
    private TimeUnit timeUnit;
}


