package com.gb.user.entity.bo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Data;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.gb.user.entity.enums.UserTokenTokenTypeEnum;
import com.gb.user.entity.enums.UserTokenIsDeleteEnum;


/**
 * TODO 用户设备信息表传输
 * 代码生成器
 *
 * @author wgs
 * @className UserTokenBO
 * @time 2022-01-20 03:40:09
 */
@Data
@ApiModel(value = "用户设备信息表传输")
public class UserTokenBO implements Serializable {

    @ApiModelProperty(value = "序列")
    @NotBlank(groups = {UserTokenBO.Update.class, UserTokenBO.Remove.class}, message = "id不能为空")
    private String id;

    @ApiModelProperty(value = "用户id")
    private String userId;

    @NotNull(groups = {UserTokenBO.Save.class}, message = "设备类型不能为空")
    @ApiModelProperty(value = "设备类型（0：IOS，1：Android）")
    private Integer tokenType;

    @NotBlank(groups = {UserTokenBO.Save.class}, message = "cid唯一标识号不能为空")
    @ApiModelProperty(value = "个推唯一标识")
    private String cid;

    @ApiModelProperty(value = "说明")
    private String description;

    @ApiModelProperty(value = "创建时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createDateTime;

    @ApiModelProperty(value = "创建人")
    private String createName;

    @ApiModelProperty(value = "修改时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime modifyDateTime;

    @ApiModelProperty(value = "修改人")
    private String modifyName;

    @ApiModelProperty(value = "删除状态（0：未删除，1：删除）")
    private UserTokenIsDeleteEnum isDelete;

    @ApiModelProperty(value = "标签")
    private String label;

    @ApiModelProperty(value = "排序")
    private Integer sorting;

    @ApiModelProperty(value = "版本号")
    private Integer version;


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


