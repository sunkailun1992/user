package com.gb.account.entity;


import com.baomidou.mybatisplus.annotation.TableName;
import com.gb.account.entity.enums.SourceValueStateEnum;
import com.gb.account.entity.enums.SourceValueTypeEnum;
import com.gb.bean.EntityBase;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
/**
 * Created with IntelliJ IDEA.
 * @author:     	孙凯伦
 * @since:   	    2021-11-03 03:57:55
 * @description:	TODO  来源值
 * @source:  	    代码生成器
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("`source_value`")
@ApiModel(value="SourceValue对象", description="来源值")
public class SourceValue extends EntityBase {

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "编码")
    private String code;

    @ApiModelProperty(value = "类型（0：默认）")
    private SourceValueTypeEnum type;

    @ApiModelProperty(value = "状态（0：默认）")
    private SourceValueStateEnum state;

    @ApiModelProperty(value = "标签")
    private String label;
}
