package com.gb.user.entity.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 用户信息BO
 * </p>
 *
 * @author sunx
 * @since 2020-12-29
 */
@Data
@Accessors(chain = true)
@ApiModel(value="用户信息BO", description="用户信息BO")
public class UserInfoBO implements Serializable {
    @ApiModelProperty(value = "token")
    private String token;

    @ApiModelProperty(value = "userBasicInfoBO")
    private UserBasicInfoBO userBasicInfoBO;
}
