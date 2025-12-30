package com.gb.user.entity.query;


import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Data;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.gb.user.entity.enums.UserTokenTokenTypeEnum;
import com.gb.user.entity.enums.UserTokenIsDeleteEnum;


/**
 * TODO 用户设备信息表查询
 * 代码生成器
 *
 * @author wgs
 * @className UserTokenQuery
 * @time 2022-01-20 03:40:09
 */
@Data
@ApiModel(value = "用户设备信息表查询")
public class UserTokenQuery implements Serializable {

    @ApiModelProperty(value = "序列")
    private String id;

    @ApiModelProperty(value = "用户id")
    private String userId;

    @ApiModelProperty(value = "设备类型（0：IOS，1：Android）")
    private UserTokenTokenTypeEnum tokenType;

    @ApiModelProperty(value = "个推唯一标识")
    private String cid;

    @ApiModelProperty(value = "说明")
    private String description;

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


