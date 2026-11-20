package com.kellen.auth.controller;

import com.kellen.auth.entity.vo.AuthCurrentResourceVO;
import com.kellen.auth.entity.vo.AuthTenantVO;
import com.kellen.auth.service.AuthCurrentUserService;
import com.kellen.utils.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 当前登录用户请求层。
 * <p>
 * 第5阶段起登录、令牌、注销与第三方会话都交给 Keycloak（浏览器经网关 BFF 登录），这里只保留当前用户的资源与可切换租户查询；
 * 当前用户与活动租户由资源服务器按访问令牌与请求头 {@code X-Tenant-Id} 写入。
 *
 * @author sunkailun
 * @className AuthController
 * @time 2026/05/26
 */
@RestController
@RequestMapping("/auth")
@Tag(name = "当前用户", description = "查询当前登录用户的资源与可切换租户")
public class AuthController {

    private final AuthCurrentUserService authCurrentUserService;

    /**
     * 构造当前用户请求层。
     *
     * @param authCurrentUserService 当前用户服务
     */
    public AuthController(AuthCurrentUserService authCurrentUserService) {
        this.authCurrentUserService = authCurrentUserService;
    }

    /**
     * 查询当前用户资源。
     *
     * @return 当前用户在活动租户下的前端菜单、后端权限码与可切换租户
     */
    @GetMapping("/current/resources")
    @PreAuthorize("hasAuthority('user:auth:resources')")
    @Operation(summary = "查询当前用户资源", description = "根据当前认证用户返回前端菜单资源和后端权限码")
    public ApiResponse<AuthCurrentResourceVO> resources() {
        return ApiResponse.success(authCurrentUserService.currentResources());
    }

    /**
     * 查询当前用户可切换租户。
     *
     * @return 可切换租户
     */
    @GetMapping("/current/tenants")
    @PreAuthorize("hasAuthority('user:auth:resources')")
    @Operation(summary = "查询当前用户可切换租户", description = "返回当前登录用户可访问的启用租户，切换时在请求头 X-Tenant-Id 里指定")
    public ApiResponse<List<AuthTenantVO>> currentTenants() {
        return ApiResponse.success(authCurrentUserService.currentTenants());
    }
}
