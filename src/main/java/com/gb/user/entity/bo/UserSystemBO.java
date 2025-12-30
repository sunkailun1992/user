package com.gb.user.entity.bo;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 用户体系BO
 * </p>
 *
 * @author sunx
 * @since 2021-12-29
 */
@Data
@Accessors(chain = true)
@ApiModel(value="用户体系BO", description="用户体系BO")
public class UserSystemBO implements Serializable {

    @ApiModelProperty(value = "鉴权令牌")
    private String token;

}