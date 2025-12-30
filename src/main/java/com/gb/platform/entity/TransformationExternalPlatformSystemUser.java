package com.gb.platform.entity;


import com.baomidou.mybatisplus.annotation.TableName;
import com.gb.bean.EntityBase;
import com.gb.platform.entity.enums.TransformationExternalPlatformSystemUserStateEnum;
import com.gb.platform.entity.enums.TransformationExternalPlatformSystemUserTypeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


/**
 * TODO 转化外部系统平台用户关联对象
 * 代码生成器
 *
 * @author sunx
 * @className TransformationExternalPlatformSystemUser
 * @time 2022-12-16 03:10:09
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("`transformation_external_platform_system_user`")
@ApiModel(value = "TransformationExternalPlatformSystemUser对象", description = "转化外部系统平台用户关联")
public class TransformationExternalPlatformSystemUser extends EntityBase {

    @ApiModelProperty(value = "转化外部系统平台id")
    private String transformationExternalPlatformSystemId;

    @ApiModelProperty(value = "用户id")
    private String userId;

    @ApiModelProperty(value = "线索")
    private Boolean clue;

    @ApiModelProperty(value = "类型（0：默认）")
    private TransformationExternalPlatformSystemUserTypeEnum type;

    @ApiModelProperty(value = "状态（0：默认）")
    private TransformationExternalPlatformSystemUserStateEnum state;

    @ApiModelProperty(value = "标签")
    private String label;
}
