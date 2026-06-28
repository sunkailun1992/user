package com.kellen.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * 开放接口签名校验请求。
 */
@Data
@Schema(description = "开放接口签名校验请求")
public class OpenApiSignatureVerifyRequest implements Serializable {

    @Schema(description = "外部系统编码")
    private String providerCode;

    @Schema(description = "三方客户端ID")
    private String clientId;

    @Schema(description = "签名时间戳，毫秒")
    private String timestamp;

    @Schema(description = "随机串")
    private String nonce;

    @Schema(description = "HMAC-SHA256签名")
    private String signature;

    @Schema(description = "原始请求体")
    private String body;
}
