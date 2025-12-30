package com.gb.user.entity.query;


import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Data;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.gb.user.entity.enums.InstitutionsExecutivesTypeEnum;
import com.gb.user.entity.enums.InstitutionsExecutivesStateEnum;


/**
 * TODO 机构高管查询
 * 代码生成器
 *
 * @author sunxin
 * @className InstitutionsExecutivesQuery
 * @time 2022-07-04 10:48:36
 */
@Data
@ApiModel(value = "机构高管查询")
public class InstitutionsExecutivesQuery implements Serializable {

    @ApiModelProperty(value = "序列")
    private String id;

    @ApiModelProperty(value = "机构id")
    private String institutionsId;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "岗位")
    private String jobs;

    @ApiModelProperty(value = "说明")
    private String description;

    @ApiModelProperty(value = "类型（0：默认）")
    private InstitutionsExecutivesTypeEnum type;

    @ApiModelProperty(value = "状态（0：默认）")
    private InstitutionsExecutivesStateEnum state;

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


