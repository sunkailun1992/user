package com.gb.user.entity;


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
import com.gb.user.entity.enums.UserNotSpuTypeEnum;
import com.gb.user.entity.enums.UserNotSpuStateEnum;
import com.gb.bean.EntityBase;


/**
 * TODO 企业渠道用户排除产品对象
 * 代码生成器
 *
 * @author 孙凯伦
 * @className UserNotSpu
 * @time 2023-07-07 04:36:59
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("`user_not_spu`")
@ApiModel(value = "UserNotSpu对象", description = "企业渠道用户排除产品")
public class UserNotSpu extends EntityBase {

    @ApiModelProperty(value = "用户id")
    private String userId;

    @ApiModelProperty(value = "产品id")
    private String spuId;

    @ApiModelProperty(value = "公司渠道id")
    private String corporationChannelId;

    @ApiModelProperty(value = "类型（0：默认）")
    private UserNotSpuTypeEnum type;

    @ApiModelProperty(value = "状态（0：默认）")
    private UserNotSpuStateEnum state;

    @ApiModelProperty(value = "标签")
    private String label;
}
