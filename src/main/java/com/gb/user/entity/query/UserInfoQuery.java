package com.gb.user.entity.query;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;


/**
 * Created with IntelliJ IDEA.
 * @author:     	孙凯伦
 * @since:   	    2021-11-03 09:16:16
 * @description:	TODO  用户表查询
 * @source:  	    代码生成器
 */
@Data
@ApiModel(value = "用户表查询")
public class UserInfoQuery implements Serializable {

    @ApiModelProperty(value = "用户标签值码值")
    private String userTypeValueCode;

    @ApiModelProperty(value = "用户标签码值")
    private String userTypeCode;

    @ApiModelProperty(value = "用户标签码值")
    private String userTypeId;

    @ApiModelProperty(value = "用户标签码值")
    private String userTypeValueId;

    @ApiModelProperty(value = "查询类型（0-代表只查询标签内用户信息，1-代表查询标签内已经认证的用户信息）")
    @NotNull(groups = {QueryLabelInUserByTypeParams.class},message = "queryType不能为空")
    private Integer queryType;

    /**
     * 查询标签内用户信息集合
     */
    public interface QueryLabelInUserByTypeParams {}

}


