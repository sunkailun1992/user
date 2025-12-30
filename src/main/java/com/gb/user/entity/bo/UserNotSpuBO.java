package com.gb.user.entity.bo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Data;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.gb.user.entity.enums.UserNotSpuTypeEnum;
import com.gb.user.entity.enums.UserNotSpuStateEnum;


/**
 * TODO 企业渠道用户排除产品传输
 * 代码生成器
 *
 * @author 孙凯伦
 * @className UserNotSpuBO
 * @time 2023-07-07 04:36:59
 */
@Data
@ApiModel(value = "企业渠道用户排除产品传输")
public class UserNotSpuBO implements Serializable {

    @ApiModelProperty(value = "序列")
    @NotBlank(groups = {UserNotSpuBO.Update.class}, message = "id不能为空")
    private String id;

    @ApiModelProperty(value = "用户id")
    private String userId;

    @ApiModelProperty(value = "产品id")
    private String spuId;

    @ApiModelProperty(value = "公司渠道id")
    private String corporationChannelId;

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
    private UserNotSpuTypeEnum type;

    @ApiModelProperty(value = "状态（0：默认）")
    private UserNotSpuStateEnum state;

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


