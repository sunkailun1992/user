package com.gb.user.entity.bo;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 批量用户信息查询BO
 * </p>
 *
 * @author 孙凯伦
 * @since 2020-12-29
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="批量用户信息查询BO", description="批量用户信息查询BO")
public class UserBatchQueryBO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户唯一标志列表")
    private List<String> userIdList;

    @ApiModelProperty(value = "用户标签码值")
    private String userTypeCode;

    @ApiModelProperty(value = "用户标签序列")
    private String userTypeId;
}
