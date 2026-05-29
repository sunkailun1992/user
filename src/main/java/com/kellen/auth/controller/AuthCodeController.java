package com.kellen.auth.controller;

import com.kellen.auth.entity.query.AuthCodeGenerateQuery;
import com.kellen.auth.service.AuthCodeGenerateService;
import com.kellen.utils.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 认证编码生成请求层。
 *
 * @author sunkailun
 * @className AuthCodeController
 * @time 2026/05/27
 */
@RestController
@RequestMapping("/auth/manage/codes")
@PreAuthorize("hasAuthority('user:auth:manage')")
@Tag(name = "认证编码生成", description = "按认证模块编码规则生成租户、用户、角色、资源等业务编码")
public class AuthCodeController {

    /**
     * 认证编码生成服务。
     */
    private final AuthCodeGenerateService authCodeGenerateService;

    /**
     * 构造认证编码生成请求层。
     *
     * @param authCodeGenerateService 认证编码生成服务
     */
    public AuthCodeController(AuthCodeGenerateService authCodeGenerateService) {
        // 注入编码生成服务。
        this.authCodeGenerateService = authCodeGenerateService;
    }

    /**
     * 生成业务编码。
     *
     * @param query 编码生成查询参数
     * @return 业务编码
     * @author sunkailun
     * @DateTime 2026/05/27
     * @email 376253703@qq.com
     */
    @GetMapping("/generate")
    @Operation(summary = "生成业务编码", description = "根据编码类型生成认证模块统一业务编码")
    public ApiResponse<String> generate(@Validated AuthCodeGenerateQuery query) {
        // 调用业务服务生成后端统一编码，避免前端分散拼接规则。
        return ApiResponse.success(authCodeGenerateService.generate(query)); // 使用统一成功工厂方法组装 success、code、msg、data 和 timestamp。
    }
}
