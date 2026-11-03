package com.kellen.auth.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.kellen.auth.entity.enums.AuthResourceCategoryEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * 认证资源对象
 * <p>
 * 资源分为前端资源和后端接口资源：前端资源控制菜单/页面显示，后端资源控制接口访问。
 *
 * @author sunkailun
 * @className AuthResource
 * @time 2026/05/25
 */
@Getter
@Setter
@TableName("auth_resource")
@Schema(description = "认证资源对象")
public class AuthResource extends AuthEntity {

    @Schema(description = "资源名称")
    private String name;

    @Schema(description = "资源分类：FRONTEND前端资源，BACKEND后端接口")
    private AuthResourceCategoryEnum resourceCategory;

    @Schema(description = "资源路径")
    private String path;

    @Schema(description = "后端接口请求方法")
    private String method;

    @Schema(description = "父级资源ID")
    private String parentId;
}
