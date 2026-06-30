package com.kellen.auth.controller;

import com.kellen.auth.dto.LoginRequest;
import com.kellen.auth.dto.LogoutSessionRequest;
import com.kellen.auth.dto.OpenApiSignatureVerifyRequest;
import com.kellen.auth.dto.RefreshSessionRequest;
import com.kellen.auth.dto.ThirdPartySessionRequest;
import com.kellen.auth.entity.query.AuthTenantQuery;
import com.kellen.auth.entity.vo.AuthCurrentResourceVO;
import com.kellen.auth.entity.vo.AuthLoginVO;
import com.kellen.auth.entity.vo.AuthTenantVO;
import com.kellen.auth.service.AuthAuthenticationService;
import com.kellen.auth.service.AuthTenantService;
import com.kellen.auth.service.AuthThirdPartyAuthenticationService;
import com.kellen.utils.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestHeader;
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
@Tag(name = "认证登录", description = "提供登录、登录前租户查询和当前用户资源查询接口")
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
     * 三方认证业务服务。
     */
    private final AuthThirdPartyAuthenticationService authThirdPartyAuthenticationService;

    /**
     * 构造认证授权请求层。
     *
     * @param authAuthenticationService 认证登录业务服务
     * @param authTenantService         租户业务服务
     */
    public AuthController(AuthAuthenticationService authAuthenticationService,
                          AuthTenantService authTenantService,
                          AuthThirdPartyAuthenticationService authThirdPartyAuthenticationService) {
        // 注入认证登录业务服务。
        this.authAuthenticationService = authAuthenticationService;
        // 注入租户业务服务，用于登录前公开查询租户下拉数据。
        this.authTenantService = authTenantService;
        // 注入三方认证业务服务，用于外部系统签名换取统一会话。
        this.authThirdPartyAuthenticationService = authThirdPartyAuthenticationService;
    }

    /**
     * 登录前公开查询租户列表。
     * <p>
     * 该接口只返回租户展示数据，用于前端登录页租户下拉选择，不授予任何管理能力。
     *
     * @param query 租户查询参数
     * @return 租户列表
     * @author sunkailun
     */
    @GetMapping("/tenants")
    @Operation(summary = "登录前查询租户", description = "公开返回可选租户列表，用于登录页租户下拉选择")
    public ApiResponse<List<AuthTenantVO>> tenants(@ParameterObject AuthTenantQuery query) {
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
     */
    @PostMapping("/sessions")
    @Operation(summary = "创建登录会话", description = "校验租户、账号和密码，签发JWT并返回当前用户权限资源")
    public ApiResponse<AuthLoginVO> login(@RequestBody LoginRequest request) {
        // 调用业务服务完成租户解析、密码校验、JWT签发和资源组装。
        return ApiResponse.success(authAuthenticationService.login(request)); // 使用统一成功工厂方法组装 success、code、msg、data 和 timestamp。
    }

    /**
     * 刷新登录会话。
     *
     * @param request 刷新请求
     * @return 登录结果
     */
    @PostMapping("/sessions/refresh")
    @Operation(summary = "刷新登录会话", description = "校验refresh token后轮换签发新的JWT和refresh token")
    public ApiResponse<AuthLoginVO> refreshSession(@RequestBody RefreshSessionRequest request) {
        return ApiResponse.success(authAuthenticationService.refreshSession(request));
    }

    /**
     * 退出当前登录会话。
     *
     * @param authorization Authorization请求头
     * @param request       退出请求
     * @return 退出结果
     */
    @PostMapping("/sessions/logout")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "退出当前登录会话", description = "撤销当前access token，并按需撤销refresh token")
    public ApiResponse<Void> logout(@RequestHeader(name = "Authorization", required = false) String authorization,
                                    @RequestBody(required = false) LogoutSessionRequest request) {
        authAuthenticationService.logout(authorization, request);
        return ApiResponse.success();
    }

    /**
     * 三方认证登录。
     *
     * @param request 三方认证请求参数
     * @return 登录结果
     * @author sunkailun
     */
    @PostMapping("/third-party/sessions")
    @Operation(summary = "创建三方登录会话", description = "校验三方客户端签名和外部身份映射后，签发统一JWT")
    public ApiResponse<AuthLoginVO> thirdPartyLogin(@RequestBody ThirdPartySessionRequest request) {
        // 三方登录只接受已配置客户端签名和外部身份映射，不信任前端直接传入的本地用户信息。
        return ApiResponse.success(authThirdPartyAuthenticationService.createSession(request));
    }

    /**
     * 开放接口签名校验。
     *
     * @param request 开放接口签名校验请求
     * @return 校验结果
     * @author sunkailun
     */
    @PostMapping("/open/signatures/verify")
    @Operation(summary = "校验开放接口签名", description = "统一校验三方系统调用业务服务时携带的HMAC签名")
    public ApiResponse<Boolean> verifyOpenApiSignature(@RequestBody OpenApiSignatureVerifyRequest request) {
        authThirdPartyAuthenticationService.verifyOpenApiSignature(request);
        return ApiResponse.success(Boolean.TRUE);
    }

    /**
     * 当前用户资源。
     *
     * @return 当前用户资源
     * @author sunkailun
     */
    @GetMapping("/current/resources")
    @PreAuthorize("hasAuthority('user:auth:resources')")
    @Operation(summary = "查询当前用户资源", description = "根据当前认证用户返回前端菜单资源和后端权限码")
    public ApiResponse<AuthCurrentResourceVO> resources() {
        // 调用业务服务查询当前用户拥有的前端资源和后端权限码。
        return ApiResponse.success(authAuthenticationService.currentResources()); // 使用统一成功工厂方法组装 success、code、msg、data 和 timestamp。
    }

    /**
     * 当前用户可切换租户。
     *
     * @return 当前用户可切换租户
     * @author sunkailun
     */
    @GetMapping("/current/tenants")
    @PreAuthorize("hasAuthority('user:auth:resources')")
    @Operation(summary = "查询当前用户可切换租户", description = "根据当前登录用户名返回该用户在各租户下拥有启用账号的租户列表")
    public ApiResponse<List<AuthTenantVO>> currentTenants() {
        // 调用认证服务按当前登录用户名查询可切换租户。
        return ApiResponse.success(authAuthenticationService.currentTenants()); // 使用统一成功工厂方法组装 success、code、msg、data 和 timestamp。
    }
}
