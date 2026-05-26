package com.kellen.auth.entity.bo;

import com.kellen.auth.entity.enums.AuthStateEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;

/**
 * 认证用户写入参数。
 *
 * @author sunkailun
 * @className AuthUserBO
 * @time 2026/05/26
 */
@Data
@Schema(description = "认证用户写入参数")
public class AuthUserBO implements Serializable {

    /**
     * 用户主键。
     */
    @Schema(description = "用户主键")
    @NotBlank(groups = {Update.class, Remove.class}, message = "id不能为空")
    private String id;

    /**
     * 数据库版本号。
     */
    @Schema(description = "数据库版本号，修改时必传")
    @NotNull(groups = {Update.class}, message = "version不能为空")
    private Integer version;

    /**
     * 租户ID。
     */
    @Schema(description = "租户ID")
    @NotBlank(groups = {Save.class}, message = "tenantId不能为空")
    private String tenantId;

    /**
     * 用户名。
     */
    @Schema(description = "用户名")
    @NotBlank(groups = {Save.class}, message = "username不能为空")
    private String username;

    /**
     * 明文密码。
     */
    @Schema(description = "明文密码")
    @NotBlank(groups = {Save.class}, message = "password不能为空")
    private String password;

    /**
     * 用户昵称。
     */
    @Schema(description = "用户昵称")
    private String nickname;

    /**
     * 用户状态。
     */
    @Schema(description = "用户状态")
    private AuthStateEnum state;

    /**
     * 新增校验分组。
     */
    public interface Save {
    }

    /**
     * 修改校验分组。
     */
    public interface Update {
    }

    /**
     * 删除校验分组。
     */
    public interface Remove {
    }
}
