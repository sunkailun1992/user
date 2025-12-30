package com.gb.platform.entity.bo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.gb.platform.entity.enums.ExternalPlatformStateEnum;
import com.gb.platform.entity.enums.ExternalPlatformTypeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.time.LocalDateTime;


/**
 * TODO 外部平台传输
 * 代码生成器
 *
 * @author sunx
 * @className ExternalPlatformBO
 * @time 2022-12-16 03:10:07
 */
@Data
@ApiModel(value = "外部平台传输")
public class ExternalPlatformBO implements Serializable {

    @ApiModelProperty(value = "序列")
    @NotBlank(groups = {ExternalPlatformBO.Update.class, ExternalPlatformBO.Remove.class}, message = "id不能为空")
    private String id;

    @ApiModelProperty(value = "编码")
    private String code;

    @ApiModelProperty(value = "名称")
    private String name;

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
    private ExternalPlatformTypeEnum type;

    @ApiModelProperty(value = "状态（0：默认）")
    private ExternalPlatformStateEnum state;

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


