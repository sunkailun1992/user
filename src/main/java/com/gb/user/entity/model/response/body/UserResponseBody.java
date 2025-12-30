package com.gb.user.entity.model.response.body;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 响应工保通body类
 * </p>
 *
 * @author sunx
 * @since 2021-05-25
 */
@Data
@Accessors(chain = true)
public class UserResponseBody implements Serializable {

    @ApiModelProperty(value = "过期时间")
   private String id;

    @ApiModelProperty(value = "用户类型")
    private Integer userType;

    @ApiModelProperty(value = "用户编码")
    private String userCode;

    @ApiModelProperty(value = "鉴权令牌")
   private String token;

    @ApiModelProperty(value = "过期时间")
    private String expire;

    @ApiModelProperty(value = "多少秒后过期")
    private String expireUnit;

    @ApiModelProperty(value = "IP地址")
    private String ipAddress;

    @ApiModelProperty(value = "平台编码")
    private String  platformCode;

    @ApiModelProperty(value = "是否删除（0：正常，1：删除）")
    private String delete;

    @ApiModelProperty(value = "创建时间")
    private String createDate;

    @ApiModelProperty(value = "修改时间")
    private String updateDate;

    @ApiModelProperty(value = "扩展字段")
    private String ext;

}
