package com.gb.permissions.entity.bo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Data;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.gb.permissions.entity.enums.ResourceStateEnum;
import com.gb.permissions.entity.enums.ResourceTypeEnum;


/**
 * Created with IntelliJ IDEA.
 * @author:     	孙凯伦
 * @since:   	    2021-10-21 01:59:45
 * @description:	TODO  资源表传输
 * @source:  	    代码生成器
 */
@Data
@ApiModel(value = "资源表传输")
public class ResourceBO implements Serializable {

    @ApiModelProperty(value = "序列")
    @NotBlank(groups = {ResourceBO.Update.class,ResourceBO.Remove.class},message = "id不能为空")
    private String id;

    @ApiModelProperty(value = "上级id")
    private String superiorsId;

    @ApiModelProperty(value = "系统id")
    private String systemId;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "url值")
    private String value;

    @ApiModelProperty(value = "接口资源")
    private Boolean api;

    @ApiModelProperty(value = "接口类型（0：待定，1：get，2：post，3：put，4：delete）")
    private Integer apiType;

    @ApiModelProperty(value = "按钮")
    private Boolean button;

    @ApiModelProperty(value = "导航栏")
    private Boolean navigation;

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
    private ResourceTypeEnum type;

    @ApiModelProperty(value = "状态（0：默认）")
    private ResourceStateEnum state;

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


