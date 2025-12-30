package com.gb.user.entity.model.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.TreeMap;

/**
 * <p>
 * 请求工保通类
 * </p>
 *
 * @author sunx
 * @since 2021-05-25
 */
@Data
@Accessors(chain = true)
public class GbtRequest implements Serializable {

    @ApiModelProperty(value = "系统编码")
    private String platformCode;

    @ApiModelProperty(value = "来源")
    private String source;

    @ApiModelProperty(value = "请求时间")
    @DateTimeFormat(pattern = "yyyyMMddHHmmss")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyyMMddHHmmss", timezone = "GMT+8")
    private LocalDateTime reqTime;

    @ApiModelProperty(value = "请求类型，默认为PROGRAM（PROGRAM-接口，WEB-网页， APP-移动应用）")
    private String reqType = "PROGRAM";

    @ApiModelProperty(value = "请求内容")
    private TreeMap<String, Object> reqBody;

    @ApiModelProperty(value = "签名")
    private String sign;

    @ApiModelProperty(value = "鉴权令牌")
    private String token;

    @ApiModelProperty(value = "IP地址")
    private String ipAddress;

}
