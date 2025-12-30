package com.gb.account.entity;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.baomidou.mybatisplus.annotation.*;
import com.gb.account.entity.enums.UserAttachmentTypeEnum;
import com.gb.account.entity.enums.UserAttachmentStateEnum;
import com.gb.bean.EntityBase;


/**
 * TODO 用户附件对象
 * 代码生成器
 *
 * @author lijh
 * @className UserAttachment
 * @time 2022-04-14 10:04:04
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("`user_attachment`")
@ApiModel(value = "UserAttachment对象", description = "用户附件")
public class UserAttachment extends EntityBase {

    @ApiModelProperty(value = "用户id")
    private String userId;

    @ApiModelProperty(value = "文件名称")
    private String name;

    @ApiModelProperty(value = "文件地址")
    private String address;

    @ApiModelProperty(value = "编码")
    private String code;

    @ApiModelProperty(value = "类型（0：默认）")
    private UserAttachmentTypeEnum type;

    @ApiModelProperty(value = "状态（0：默认）")
    private UserAttachmentStateEnum state;

    @ApiModelProperty(value = "标签")
    private String label;
}
