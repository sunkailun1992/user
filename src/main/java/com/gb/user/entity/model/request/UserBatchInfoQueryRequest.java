package com.gb.user.entity.model.request;

import com.gb.user.entity.model.request.UserSingleInfoQueryRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 请求工保通批量用户信息查询Body类
 * </p>
 *
 * @author sunx
 * @since 2021-05-25
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class UserBatchInfoQueryRequest extends UserSingleInfoQueryRequest implements Serializable {

    @ApiModelProperty(value = "分组/角色/标签编号")
    private String groupCode;

    @ApiModelProperty(value = "邀请人编码")
    private String inviteCode;

    @ApiModelProperty(value = "页码")
    private Integer pageNum;

    @ApiModelProperty(value = "数量")
    private Integer pageSize;
}
