package com.kellen.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * 三方认证会话创建请求。
 */
@Data
@Schema(description = "三方认证会话创建请求")
public class ThirdPartySessionRequest implements Serializable {

    @Schema(description = "外部系统编码")
    private String providerCode;

    @Schema(description = "三方客户端ID")
    private String clientId;

    @Schema(description = "外部租户ID")
    private String externalTenantId;

    @Schema(description = "外部用户ID")
    private String externalUserId;

    @Schema(description = "外部患者或业务主体ID")
    private String externalPatientId;

    @Schema(description = "主体类型")
    private String subjectType;

    @Schema(description = "签名时间戳，毫秒")
    private Long timestamp;

    @Schema(description = "随机串")
    private String nonce;

    @Schema(description = "HMAC-SHA256签名")
    private String signature;
}
