package com.kellen.auth.controller;

import com.kellen.auth.dto.LoginRequest;
import com.kellen.auth.entity.vo.AuthCurrentResourceVO;
import com.kellen.auth.entity.vo.AuthLoginVO;
import com.kellen.auth.service.AuthAuthenticationService;
import com.kellen.utils.ApiResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 认证授权请求层。
 * <p>
 * 只保留正式认证入口：登录、当前用户资源查询。
 *
 * @author sunkailun
 * @className AuthController
 * @time 2026/05/26
 */
@RestController
@RequestMapping("/auth")
public class AuthController {

    /**
     * 认证登录业务服务。
     */
    private final AuthAuthenticationService authAuthenticationService;

    /**
     * 构造认证授权请求层。
     *
     * @param authAuthenticationService 认证登录业务服务
     */
    public AuthController(AuthAuthenticationService authAuthenticationService) {
        // 注入认证登录业务服务。
        this.authAuthenticationService = authAuthenticationService;
    }

    /**
     * 用户登录。
     *
     * @param request 登录请求参数
     * @return 登录结果
     * @author sunkailun
     * @DateTime 2026/05/26
     * @email 376253703@qq.com
     */
    @PostMapping("/login")
    public ApiResponse<AuthLoginVO> login(@RequestBody LoginRequest request) {
        // 调用业务服务完成租户解析、密码校验、JWT签发和资源组装。
        return ApiResponse.success(authAuthenticationService.login(request)); // 使用统一成功工厂方法组装 success、code、msg、data 和 timestamp。
    }

    /**
     * 当前用户资源。
     *
     * @return 当前用户资源
     * @author sunkailun
     * @DateTime 2026/05/26
     * @email 376253703@qq.com
     */
    @GetMapping("/resources")
    @PreAuthorize("hasAuthority('user:auth:resources')")
    public ApiResponse<AuthCurrentResourceVO> resources() {
        // 调用业务服务查询当前用户拥有的前端资源和后端权限码。
        return ApiResponse.success(authAuthenticationService.currentResources()); // 使用统一成功工厂方法组装 success、code、msg、data 和 timestamp。
    }
}
