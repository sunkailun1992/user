package com.gb.user.entity.model.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 响应工保通类
 * </p>
 *
 * @author sunx
 * @since 2021-05-25
 */
@Data
@Accessors(chain = true)
public class GbtResponse implements Serializable {

    @ApiModelProperty(value = "状态")
    private Boolean success;

    @ApiModelProperty(value = "状态码")
    private String code;

    @ApiModelProperty(value = "信息")
    private String msg;

    @ApiModelProperty(value = "对象信息")
    private Object data;

}
