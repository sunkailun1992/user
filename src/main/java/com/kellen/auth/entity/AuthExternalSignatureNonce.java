package com.kellen.auth.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * 三方开放接口签名 nonce。
 */
@Getter
@Setter
@TableName("auth_external_signature_nonce")
@Schema(description = "三方开放接口签名 nonce")
public class AuthExternalSignatureNonce extends AuthEntity {

    @Schema(description = "外部系统编码")
    private String providerCode;

    @Schema(description = "三方客户端ID")
    private String clientId;

    @Schema(description = "随机串")
    private String nonce;

    @Schema(description = "请求时间戳，毫秒")
    private Long timestampMillis;

    @Schema(description = "过期时间")
    private LocalDateTime expireDateTime;
}
