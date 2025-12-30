package com.gb.user.entity.bo;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 用户信息BO
 * </p>
 *
 * @author 孙凯伦
 * @since 2020-12-29
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="角色请求BO", description="角色请求BO")
public class RoleRequestBO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "平台编码")
    @NotBlank(message = "平台编码不能为空")
    private String appCode;

    @ApiModelProperty(value = "用户ID")
    private Long userId;

    @ApiModelProperty(value = "角色名字列表")
    private List<String> roleNameList;

    @ApiModelProperty(hidden = true, value = "操作员")
    private String createName;

}
