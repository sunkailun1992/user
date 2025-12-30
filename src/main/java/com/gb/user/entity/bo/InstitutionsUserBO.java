package com.gb.user.entity.bo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.gb.account.entity.bo.UserExtendsBO;
import com.gb.user.entity.UserAgentCertification;
import com.gb.user.entity.enums.InstitutionsUserStateEnum;
import com.gb.user.entity.enums.InstitutionsUserTypeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.time.LocalDateTime;


/**
 * TODO 机构用户关联传输
 * 代码生成器
 *
 * @author sunxin
 * @className InstitutionsUserBO
 * @time 2022-07-04 10:48:37
 */
@Data
@ApiModel(value = "机构用户关联传输")
public class InstitutionsUserBO implements Serializable {

    @ApiModelProperty(value = "序列")
    @NotBlank(groups = {InstitutionsUserBO.Update.class, InstitutionsUserBO.Remove.class}, message = "id不能为空")
    private String id;

    @ApiModelProperty(value = "用户id")
    private String userId;

    @ApiModelProperty(value = "机构id")
    private String institutionsId;

    @ApiModelProperty(value = "推荐人")
    private String referees;

    @ApiModelProperty(value = "备案手机")
    private String recordMobile;

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

    @ApiModelProperty(value = "删除状态")
    private Boolean isDelete;

    @ApiModelProperty(value = "类型（0：默认）")
    private InstitutionsUserTypeEnum type;

    @ApiModelProperty(value = "状态（0：在职，1：离职，2：待入职）")
    private InstitutionsUserStateEnum state;

    @ApiModelProperty(value = "标签")
    private String label;

    @ApiModelProperty(value = "排序")
    private Integer sorting;

    @ApiModelProperty(value = "版本号")
    private Integer version;

    @ApiModelProperty(value = "用户扩展信息BO")
    private UserExtendsBO userExtendsBO;

    @ApiModelProperty(value = "用户经纪人认证")
    private UserAgentCertification userAgentCertification;

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


