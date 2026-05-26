package com.kellen.auth.controller;

import com.kellen.auth.dto.LoginRequest;
import com.kellen.auth.entity.vo.AuthCurrentResourceVO;
import com.kellen.auth.entity.vo.AuthLoginVO;
import com.kellen.auth.service.AuthAuthenticationService;
import com.kellen.auth.service.AuthBootstrapService;
import com.kellen.utils.Json;
import com.kellen.utils.enumeration.ReturnCode;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 认证授权请求层。
 * <p>
 * 只保留正式认证入口：初始化真实基础数据、登录、当前用户资源查询。
 *
 * @author sunkailun
 * @className AuthController
 * @time 2026/05/26
 */
@RestController
@RequestMapping("/auth")
public class AuthController {

    /**
     * 认证体系初始化服务。
     */
    private final AuthBootstrapService authBootstrapService;

    /**
     * 认证登录业务服务。
     */
    private final AuthAuthenticationService authAuthenticationService;

    /**
     * 构造认证授权请求层。
     *
     * @param authBootstrapService      认证体系初始化服务
     * @param authAuthenticationService 认证登录业务服务
     */
    public AuthController(AuthBootstrapService authBootstrapService, AuthAuthenticationService authAuthenticationService) {
        // 注入认证体系初始化服务。
        this.authBootstrapService = authBootstrapService;
        // 注入认证登录业务服务。
        this.authAuthenticationService = authAuthenticationService;
    }

    /**
     * 初始化真实认证授权基础数据。
     *
     * @return 初始化结果
     * @author sunkailun
     * @DateTime 2026/05/26
     * @email 376253703@qq.com
     */
    @PostMapping("/init")
    public Json<Map<String, Object>> init() {
        // 调用业务服务初始化真实租户、用户、角色、资源和授权关系。
        return new Json<>(ReturnCode.成功, authBootstrapService.init());
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
    public Json<AuthLoginVO> login(@RequestBody LoginRequest request) {
        // 调用业务服务完成租户解析、密码校验、JWT签发和资源组装。
        return new Json<>(ReturnCode.成功, authAuthenticationService.login(request));
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
    public Json<AuthCurrentResourceVO> resources() {
        // 调用业务服务查询当前用户拥有的前端资源和后端权限码。
        return new Json<>(ReturnCode.成功, authAuthenticationService.currentResources());
    }
}
