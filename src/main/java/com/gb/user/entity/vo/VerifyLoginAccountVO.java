package com.gb.user.entity.vo;

import com.gb.account.entity.vo.UserVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 校验登录账户VO
 * </p>
 *
 * @author 孙凯伦
 * @since 2020-12-29
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "校验登录账户VO", description = "校验登录账户VO")
public class VerifyLoginAccountVO implements Serializable {

    @ApiModelProperty(value = "用户")
    private UserVO userVO;

    @ApiModelProperty(value = "系统编码")
    private String appCode;

}
