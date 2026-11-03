package com.kellen.auth.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * 外部身份与本地用户映射。
 */
@Getter
@Setter
@TableName("auth_external_identity")
@Schema(description = "外部身份与本地用户映射")
public class AuthExternalIdentity extends AuthEntity {

    @Schema(description = "外部系统编码")
    private String providerCode;

    @Schema(description = "外部租户ID")
    private String externalTenantId;

    @Schema(description = "外部用户ID")
    private String externalUserId;

    @Schema(description = "外部患者或业务主体ID")
    private String externalPatientId;

    @Schema(description = "本地租户ID")
    private String localTenantId;

    @Schema(description = "本地用户ID")
    private String localUserId;

    @Schema(description = "主体类型")
    private String subjectType;

    @Schema(description = "展示名称")
    private String displayName;

    @Schema(description = "三方扩展元数据JSON")
    private String metadataJson;
}
