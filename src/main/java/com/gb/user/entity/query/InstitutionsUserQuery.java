package com.gb.user.entity.query;


import com.gb.user.entity.enums.InstitutionsUserStateEnum;
import com.gb.user.entity.enums.InstitutionsUserTypeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;


/**
 * TODO 机构用户关联查询
 * 代码生成器
 *
 * @author sunxin
 * @className InstitutionsUserQuery
 * @time 2022-07-04 10:48:37
 */
@Data
@ApiModel(value = "机构用户关联查询")
public class InstitutionsUserQuery implements Serializable {

    @ApiModelProperty(value = "序列")
    private String id;

    @ApiModelProperty(value = "用户id")
    private String userId;

    @ApiModelProperty(value = "机构id")
    private String institutionsId;

    @ApiModelProperty(value = "经纪人姓名模糊查询")
    private String nameQuery;

    @ApiModelProperty(value = "推荐人")
    private String referees;

    @ApiModelProperty(value = "备案手机")
    private String recordMobile;

    @ApiModelProperty(value = "说明")
    private String description;

    @ApiModelProperty(value = "类型（0：默认）")
    private InstitutionsUserTypeEnum type;

    @ApiModelProperty(value = "状态（0：在职，1：离职，2：待入职）")
    private InstitutionsUserStateEnum state;

    @ApiModelProperty(value = "标签")
    private String label;

    @ApiModelProperty(value = "排序")
    private Integer sorting;

    @ApiModelProperty(value = "显示字段")
    private String fields;

    @ApiModelProperty(value = "排序规则(0:desc,1:asc)")
    private Boolean collation;

    @ApiModelProperty(value = "排序字段")
    private String collationFields = "create_date_time";

    @ApiModelProperty(value = "模糊查询")
    private String query;

    /**
     * 查询分页方法
     */
    public interface Select{}

    /**
     * 查询方法
     */
    public interface SelectList{}

    /**
     * 单条查询
     */
    public interface SelectOne{}

    /**
     * 总数参数
     */
    public interface Count{}
}


