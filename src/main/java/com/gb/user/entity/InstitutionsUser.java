package com.gb.user.entity;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.gb.bean.EntityBase;
import com.gb.user.entity.enums.InstitutionsUserStateEnum;
import com.gb.user.entity.enums.InstitutionsUserTypeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


/**
 * TODO 机构用户关联对象
 * 代码生成器
 *
 * @author sunxin
 * @className InstitutionsUser
 * @time 2022-07-04 10:48:37
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("`institutions_user`")
@ApiModel(value = "InstitutionsUser对象", description = "机构用户关联")
public class InstitutionsUser extends EntityBase {

    @ApiModelProperty(value = "用户id")
    private String userId;

    @ApiModelProperty(value = "机构id")
    private String institutionsId;

    @ApiModelProperty(value = "推荐人")
    private String referees;

    @ApiModelProperty(value = "备案手机")
    private String recordMobile;

    @ApiModelProperty(value = "类型（0：默认）")
    private InstitutionsUserTypeEnum type;

    @ApiModelProperty(value = "状态（0：在职，1：离职，2：待入职）")
    private InstitutionsUserStateEnum state;

    @ApiModelProperty(value = "标签")
    private String label;

    @ApiModelProperty(value = "机构姓名")
    @TableField("(select `name` from `institutions` where `institutions`.`id` = `institutions_user`.`institutions_id` and `institutions`.`is_delete` = 0)")
    private String institutionsName;
}
