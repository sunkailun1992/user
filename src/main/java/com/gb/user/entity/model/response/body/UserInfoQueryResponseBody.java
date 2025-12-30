package com.gb.user.entity.model.response.body;

import com.gb.user.entity.bo.UserExtendsInfoBO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 响应工保通用户信息查询Body类
 * </p>
 *
 * @author sunx
 * @since 2021-05-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class UserInfoQueryResponseBody extends UserExtendsInfoBO implements Serializable {

    @ApiModelProperty(value = "序列")
    private String id;

    @ApiModelProperty(value = "邀请人编码")
    private String inviteCode;

    @ApiModelProperty(value = "平台编码")
    private String platformCode;

    @ApiModelProperty(value = "是否删除（0：正常 1：删除）")
    private Integer delete;

    @ApiModelProperty(value = "创建时间")
    private String createDate;

    @ApiModelProperty(value = "修改时间")
    private String updateDate;

    @ApiModelProperty(value = "扩展字段")
    private String ext;

    @ApiModelProperty(value = "鉴权令牌")
    private String token;

    @ApiModelProperty(value = "过期时间")
    private String expire;

    @ApiModelProperty(value = "密码")
    private String password;
}
