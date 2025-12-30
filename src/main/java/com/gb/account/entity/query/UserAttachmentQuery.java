package com.gb.account.entity.query;


import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Data;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.gb.account.entity.enums.UserAttachmentTypeEnum;
import com.gb.account.entity.enums.UserAttachmentStateEnum;


/**
 * TODO 用户附件查询
 * 代码生成器
 *
 * @author lijh
 * @className UserAttachmentQuery
 * @time 2022-04-14 10:04:04
 */
@Data
@ApiModel(value = "用户附件查询")
public class UserAttachmentQuery implements Serializable {

    @ApiModelProperty(value = "序列")
    private String id;

    @ApiModelProperty(value = "用户id")
    private String userId;

    @ApiModelProperty(value = "文件名称")
    private String name;

    @ApiModelProperty(value = "文件地址")
    private String address;

    @ApiModelProperty(value = "编码")
    private String code;

    @ApiModelProperty(value = "说明")
    private String description;

    @ApiModelProperty(value = "类型（0：默认）")
    private UserAttachmentTypeEnum type;

    @ApiModelProperty(value = "状态（0：默认）")
    private UserAttachmentStateEnum state;

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


