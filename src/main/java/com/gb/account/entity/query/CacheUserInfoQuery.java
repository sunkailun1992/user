package com.gb.account.entity.query;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;


/**
 * Created with IntelliJ IDEA.
 * @author:     	孙凯伦
 * @since:   	    2021-10-21 11:06:07
 * @description:	TODO  缓存用户信息查询
 * @source:  	    代码生成器
 */
@Data
@ApiModel(value = "缓存用户信息查询")
public class CacheUserInfoQuery implements Serializable {

    @ApiModelProperty(value = "鉴权令牌")
    private String token;

    @ApiModelProperty(value = "查询KEY")
    private String cacheKey;

    @ApiModelProperty(value = "模糊查询")
    private String query;
}


