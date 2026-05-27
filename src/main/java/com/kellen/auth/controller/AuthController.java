package com.kellen.auth.controller;

import com.kellen.auth.dto.LoginRequest;
import com.kellen.auth.entity.query.AuthTenantQuery;
import com.kellen.auth.entity.vo.AuthCurrentResourceVO;
import com.kellen.auth.entity.vo.AuthLoginVO;
import com.kellen.auth.entity.vo.AuthTenantVO;
import com.kellen.auth.service.AuthAuthenticationService;
import com.kellen.auth.service.AuthTenantService;
import com.kellen.utils.response.ApiResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
     * 租户业务服务。
     */
    private final AuthTenantService authTenantService;

    /**
     * 构造认证授权请求层。
     *
     * @param authAuthenticationService 认证登录业务服务
     * @param authTenantService         租户业务服务
     */
    public AuthController(AuthAuthenticationService authAuthenticationService, AuthTenantService authTenantService) {
        // 注入认证登录业务服务。
        this.authAuthenticationService = authAuthenticationService;
        // 注入租户业务服务，用于登录前公开查询租户下拉数据。
        this.authTenantService = authTenantService;
    }

    /**
     * 登录前公开查询租户列表。
     * <p>
     * 该接口只返回租户展示数据，用于前端登录页租户下拉选择，不授予任何管理能力。
     *
     * @param query 租户查询参数
     * @return 租户列表
     * @author sunkailun
     * @DateTime 2026/05/27
     * @email 376253703@qq.com
     */
    @GetMapping("/tenants")
    public ApiResponse<List<AuthTenantVO>> tenants(AuthTenantQuery query) {
        // 标记执行结果增强，确保前端可以直接展示状态说明等补充字段。
        query.setAssignment(Boolean.TRUE);
        // 复用租户服务查询全局租户主数据，租户服务内部会忽略租户条件。
        return ApiResponse.success(authTenantService.list(query)); // 使用统一成功工厂方法组装 success、code、msg、data 和 timestamp。
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
