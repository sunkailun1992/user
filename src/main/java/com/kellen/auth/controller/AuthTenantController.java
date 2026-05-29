package com.kellen.auth.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kellen.auth.entity.AuthTenant;
import com.kellen.auth.entity.bo.AuthTenantBO;
import com.kellen.auth.entity.query.AuthTenantQuery;
import com.kellen.auth.entity.vo.AuthTenantVO;
import com.kellen.auth.service.AuthTenantService;
import com.kellen.utils.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 租户维护请求层。
 *
 * @author sunkailun
 * @className AuthTenantController
 * @time 2026/05/26
 */
@RestController
@RequestMapping("/auth/manage/tenants")
@PreAuthorize("hasAuthority('user:auth:manage')")
@Tag(name = "租户管理", description = "维护认证租户基础资料和租户启停状态")
public class AuthTenantController {

    /**
     * 认证授权业务服务。
     */
    private final AuthTenantService authTenantService;

    /**
     * 构造租户维护请求层。
     *
     * @param authTenantService 租户业务服务
     */
    public AuthTenantController(AuthTenantService authTenantService) {
        // 注入租户业务服务。
        this.authTenantService = authTenantService;
    }

    /**
     * 查询租户列表。
     *
     * @param query 租户查询参数
     * @return 租户列表
     * @author sunkailun
     * @DateTime 2026/05/26
     * @email 376253703@qq.com
     */
    @GetMapping
    @Operation(summary = "查询租户列表", description = "按查询条件返回租户列表，用于登录页选择和管理端轻量列表展示")
    public ApiResponse<List<AuthTenantVO>> list(@Validated AuthTenantQuery query) {
        // 查询全部租户主数据。
        return ApiResponse.success(authTenantService.list(query)); // 使用统一成功工厂方法组装 success、code、msg、data 和 timestamp。
    }

    /**
     * 分页查询租户。
     *
     * @param query 租户查询参数
     * @return 租户分页
     * @author sunkailun
     * @DateTime 2026/05/27
     * @email 376253703@qq.com
     */
    @PostMapping("/page")
    @Operation(summary = "分页查询租户", description = "按查询条件分页返回租户主数据，用于租户管理列表")
    public ApiResponse<Page<AuthTenantVO>> page(@Validated(AuthTenantQuery.Select.class) @RequestBody AuthTenantQuery query) {
        // 创建MyBatis-Plus分页对象。
        Page<AuthTenant> page = new Page<>(query.getCurrent(), query.getSize());
        // 查询租户分页并转换为VO。
        return ApiResponse.success(authTenantService.page(page, query)); // 使用统一成功工厂方法组装 success、code、msg、data 和 timestamp。
    }

    /**
     * 新增租户。
     *
     * @param bo 租户写入参数
     * @return 租户ID
     * @author sunkailun
     * @DateTime 2026/05/26
     * @email 376253703@qq.com
     */
    @PostMapping
    @Operation(summary = "新增租户", description = "创建租户主数据并返回新租户ID")
    public ApiResponse<String> save(@Validated(AuthTenantBO.Save.class) @RequestBody AuthTenantBO bo) {
        // 新增租户并返回租户ID。
        return ApiResponse.success(authTenantService.save(bo)); // 使用统一成功工厂方法组装 success、code、msg、data 和 timestamp。
    }

    /**
     * 修改租户。
     *
     * @param bo 租户写入参数
     * @return 是否成功
     * @author sunkailun
     * @DateTime 2026/05/26
     * @email 376253703@qq.com
     */
    @PutMapping
    @Operation(summary = "修改租户", description = "根据租户ID和version修改租户资料，并通过乐观锁防止并发覆盖")
    public ApiResponse<Boolean> update(@Validated(AuthTenantBO.Update.class) @RequestBody AuthTenantBO bo) {
        // 修改租户并使用version触发乐观锁。
        return ApiResponse.success(authTenantService.update(bo)); // 使用统一成功工厂方法组装 success、code、msg、data 和 timestamp。
    }

    /**
     * 删除租户。
     *
     * @param bo 租户删除参数
     * @return 是否成功
     * @author sunkailun
     * @DateTime 2026/05/26
     * @email 376253703@qq.com
     */
    @PostMapping("/remove")
    @Operation(summary = "删除租户", description = "根据租户ID逻辑删除租户，不物理删除历史数据")
    public ApiResponse<Boolean> remove(@Validated(AuthTenantBO.Remove.class) @RequestBody AuthTenantBO bo) {
        // 逻辑删除租户。
        return ApiResponse.success(authTenantService.remove(bo)); // 使用统一成功工厂方法组装 success、code、msg、data 和 timestamp。
    }
}
