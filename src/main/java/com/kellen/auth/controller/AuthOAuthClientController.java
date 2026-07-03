package com.kellen.auth.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kellen.auth.entity.AuthOAuthClient;
import com.kellen.auth.entity.bo.AuthOAuthClientBO;
import com.kellen.auth.entity.query.AuthOAuthClientQuery;
import com.kellen.auth.entity.vo.AuthOAuthClientVO;
import com.kellen.auth.service.AuthOAuthClientService;
import com.kellen.utils.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * OAuth 客户端维护请求层。
 */
@RestController
@RequestMapping("/auth/manage/oauth-clients")
@PreAuthorize("hasAuthority('user:auth:manage')")
@Tag(name = "OAuth 客户端管理", description = "维护 MCP 外部客户端、grant type、redirect URI、scope 和资源绑定")
public class AuthOAuthClientController {

    private final AuthOAuthClientService authOAuthClientService;

    public AuthOAuthClientController(AuthOAuthClientService authOAuthClientService) {
        this.authOAuthClientService = authOAuthClientService;
    }

    @GetMapping("/options")
    @Operation(summary = "查询 OAuth 客户端选项", description = "返回当前租户 OAuth 客户端轻量列表")
    public ApiResponse<List<AuthOAuthClientVO>> list(@ParameterObject @Validated AuthOAuthClientQuery query) {
        return ApiResponse.success(authOAuthClientService.list(query));
    }

    @GetMapping(params = {"current", "size"})
    @Operation(summary = "分页查询 OAuth 客户端", description = "按查询条件分页返回 OAuth 客户端")
    public ApiResponse<Page<AuthOAuthClientVO>> page(@ParameterObject @Validated(AuthOAuthClientQuery.Select.class) AuthOAuthClientQuery query) {
        Page<AuthOAuthClient> page = new Page<>(query.getCurrent(), query.getSize());
        return ApiResponse.success(authOAuthClientService.page(page, query));
    }

    @PostMapping
    @Operation(summary = "新增 OAuth 客户端", description = "创建 OAuth 客户端，密钥只在写入时接收，不会在列表中回显")
    public ApiResponse<String> save(@Validated(AuthOAuthClientBO.Save.class) @RequestBody AuthOAuthClientBO bo) {
        return ApiResponse.success(authOAuthClientService.save(bo));
    }

    @PutMapping("/{id}")
    @Operation(summary = "修改 OAuth 客户端", description = "修改 OAuth 客户端；clientSecret 为空时不轮换密钥")
    public ApiResponse<Boolean> update(@PathVariable String id,
                                       @Validated(AuthOAuthClientBO.Update.class) @RequestBody AuthOAuthClientBO bo) {
        bo.setId(id);
        return ApiResponse.success(authOAuthClientService.update(bo));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "删除 OAuth 客户端", description = "按 ID 逻辑删除 OAuth 客户端")
    public ApiResponse<Boolean> remove(@PathVariable String id, @RequestParam String tenantId) {
        return ApiResponse.success(authOAuthClientService.remove(tenantId, id));
    }
}
