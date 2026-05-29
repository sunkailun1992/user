package com.kellen.auth.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kellen.auth.entity.AuthResource;
import com.kellen.auth.entity.bo.AuthResourceBO;
import com.kellen.auth.entity.query.AuthResourceQuery;
import com.kellen.auth.entity.vo.AuthResourceVO;
import com.kellen.auth.service.AuthResourceService;
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
 * 权限资源维护请求层。
 *
 * @author sunkailun
 * @className AuthResourceController
 * @time 2026/05/26
 */
@RestController
@RequestMapping("/auth/manage/resources")
@PreAuthorize("hasAuthority('user:auth:manage')")
@Tag(name = "权限资源管理", description = "维护菜单、按钮、接口权限码等认证资源")
public class AuthResourceController {

    /**
     * 认证授权业务服务。
     */
    private final AuthResourceService authResourceService;

    /**
     * 构造权限资源维护请求层。
     *
     * @param authResourceService 权限资源业务服务
     */
    public AuthResourceController(AuthResourceService authResourceService) {
        // 注入权限资源业务服务。
        this.authResourceService = authResourceService;
    }

    /**
     * 查询资源列表。
     *
     * @param query 资源查询参数
     * @return 资源列表
     * @author sunkailun
     * @DateTime 2026/05/26
     * @email 376253703@qq.com
     */
    @GetMapping
    @Operation(summary = "查询权限资源列表", description = "按查询条件返回当前租户下的权限资源列表，用于资源树和授权回显")
    public ApiResponse<List<AuthResourceVO>> list(@Validated AuthResourceQuery query) {
        // 查询指定租户的权限资源列表。
        return ApiResponse.success(authResourceService.list(query)); // 使用统一成功工厂方法组装 success、code、msg、data 和 timestamp。
    }

    /**
     * 分页查询资源。
     *
     * @param query 资源查询参数
     * @return 资源分页
     * @author sunkailun
     * @DateTime 2026/05/27
     * @email 376253703@qq.com
     */
    @PostMapping("/page")
    @Operation(summary = "分页查询权限资源", description = "按查询条件分页返回当前租户下的权限资源数据")
    public ApiResponse<Page<AuthResourceVO>> page(@Validated(AuthResourceQuery.Select.class) @RequestBody AuthResourceQuery query) {
        // 创建MyBatis-Plus分页对象。
        Page<AuthResource> page = new Page<>(query.getCurrent(), query.getSize());
        // 查询资源分页并转换为VO。
        return ApiResponse.success(authResourceService.page(page, query)); // 使用统一成功工厂方法组装 success、code、msg、data 和 timestamp。
    }

    /**
     * 新增资源。
     *
     * @param bo 资源写入参数
     * @return 资源ID
     * @author sunkailun
     * @DateTime 2026/05/26
     * @email 376253703@qq.com
     */
    @PostMapping
    @Operation(summary = "新增权限资源", description = "创建菜单、按钮或接口权限资源，并返回新资源ID")
    public ApiResponse<String> save(@Validated(AuthResourceBO.Save.class) @RequestBody AuthResourceBO bo) {
        // 新增权限资源并返回资源ID。
        return ApiResponse.success(authResourceService.save(bo)); // 使用统一成功工厂方法组装 success、code、msg、data 和 timestamp。
    }

    /**
     * 修改资源。
     *
     * @param bo 资源写入参数
     * @return 是否成功
     * @author sunkailun
     * @DateTime 2026/05/26
     * @email 376253703@qq.com
     */
    @PutMapping
    @Operation(summary = "修改权限资源", description = "根据资源ID和version修改权限资源，并通过乐观锁防止并发覆盖")
    public ApiResponse<Boolean> update(@Validated(AuthResourceBO.Update.class) @RequestBody AuthResourceBO bo) {
        // 修改权限资源并使用version触发乐观锁。
        return ApiResponse.success(authResourceService.update(bo)); // 使用统一成功工厂方法组装 success、code、msg、data 和 timestamp。
    }

    /**
     * 删除资源。
     *
     * @param bo 资源删除参数
     * @return 是否成功
     * @author sunkailun
     * @DateTime 2026/05/26
     * @email 376253703@qq.com
     */
    @PostMapping("/remove")
    @Operation(summary = "删除权限资源", description = "根据资源ID逻辑删除权限资源，不物理删除历史数据")
    public ApiResponse<Boolean> remove(@Validated(AuthResourceBO.Remove.class) @RequestBody AuthResourceBO bo) {
        // 逻辑删除权限资源。
        return ApiResponse.success(authResourceService.remove(bo)); // 使用统一成功工厂方法组装 success、code、msg、data 和 timestamp。
    }
}
