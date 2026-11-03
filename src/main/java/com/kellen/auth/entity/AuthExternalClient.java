package com.kellen.auth.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * 三方认证客户端。
 */
@Getter
@Setter
@TableName("auth_external_client")
@Schema(description = "三方认证客户端")
public class AuthExternalClient extends AuthEntity {

    @Schema(description = "外部系统编码")
    private String providerCode;

    @Schema(description = "三方客户端ID")
    private String clientId;

    @Schema(description = "客户端密钥")
    private String clientSecret;

    @Schema(description = "客户端名称")
    private String name;

    @Schema(description = "是否要求签名")
    private Boolean signatureRequired;

    @Schema(description = "允许的签名时钟偏差秒数")
    private Integer allowedClockSkewSeconds;
}
