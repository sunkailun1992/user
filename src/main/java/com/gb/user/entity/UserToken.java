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
import com.gb.user.entity.enums.UserTokenTokenTypeEnum;
import com.gb.user.entity.enums.UserTokenIsDeleteEnum;
import com.gb.bean.EntityBase;


/**
 * TODO 用户设备信息表对象
 * 代码生成器
 *
 * @author wgs
 * @className UserToken
 * @time 2022-01-20 03:40:09
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("`user_token`")
@ApiModel(value = "UserToken对象", description = "用户设备信息表")
public class UserToken extends EntityBase {

    @ApiModelProperty(value = "用户id")
    private String userId;

    @ApiModelProperty(value = "设备类型（0：IOS，1：Android）")
    private Integer tokenType;

    @ApiModelProperty(value = "个推唯一标识")
    private String cid;

    @ApiModelProperty(value = "标签")
    private String label;
}
