package com.gb.user.entity.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * <p>
 * 邀请链接BO
 * </p>
 *
 * @author 孙馨
 * @since 2020-12-29
 */
@Data
@ApiModel(value = "邀请链接传输")
public class InviteLinkBO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "类型  0：外部经纪人  1：业务经纪人")
    @NotNull(groups = {InviteLinkBO.Select.class, InviteLinkBO.Down.class}, message = "type不能为空")
    private Integer type;

    @ApiModelProperty(value = "用户唯一标志")
    @NotBlank(groups = {InviteLinkBO.Select.class, InviteLinkBO.Down.class}, message = "userId不能为空")
    private String userId;

    @ApiModelProperty(value = "证件号")
    @NotBlank(groups = {InviteLinkBO.Down.class}, message = "certificateCode不能为空")
    private String certificateCode;

    @ApiModelProperty(value = "真实姓名")
    @NotBlank(groups = {InviteLinkBO.Down.class}, message = "name不能为空")
    private String name;

    /**
     * 查询
     */
    public interface Select{}

    /**
     * 下载
     */
    public interface Down{}

    /**
     * 新增
     */
    public interface Save{}

    /**
     * 修改
     */
    public interface Update{}

    /**
     * 删除
     */
    public interface Remove{}

}
