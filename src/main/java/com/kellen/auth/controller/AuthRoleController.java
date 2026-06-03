package com.kellen.auth.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kellen.auth.entity.AuthRole;
import com.kellen.auth.entity.bo.AuthRoleBO;
import com.kellen.auth.entity.query.AuthRoleQuery;
import com.kellen.auth.entity.vo.AuthRoleVO;
import com.kellen.auth.service.AuthRoleService;
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
 * 角色维护请求层。
 *
 * @author sunkailun
 * @className AuthRoleController
 * @time 2026/05/26
 */
@RestController
@RequestMapping("/auth/manage/roles")
@PreAuthorize("hasAuthority('user:auth:manage')")
@Tag(name = "角色管理", description = "维护认证角色、角色数据范围和角色启停状态")
public class AuthRoleController {

    /**
     * 认证授权业务服务。
     */
    private final AuthRoleService authRoleService;

    /**
     * 构造角色维护请求层。
     *
     * @param authRoleService 角色业务服务
     */
    public AuthRoleController(AuthRoleService authRoleService) {
        // 注入角色业务服务。
        this.authRoleService = authRoleService;
    }

    /**
     * 查询角色列表。
     *
     * @param query 角色查询参数
     * @return 角色列表
     * @author sunkailun
     * @DateTime 2026/05/26
     * @email 376253703@qq.com
     */
    @GetMapping(params = "!current")
    @Operation(summary = "查询角色列表", description = "按查询条件返回当前租户下的角色列表，用于授权选择和轻量列表展示")
    public ApiResponse<List<AuthRoleVO>> list(@ParameterObject @Validated AuthRoleQuery query) {
        // 查询指定租户的角色列表。
        return ApiResponse.success(authRoleService.list(query)); // 使用统一成功工厂方法组装 success、code、msg、data 和 timestamp。
    }

    /**
     * 分页查询角色。
     *
     * @param query 角色查询参数
     * @return 角色分页
     * @author sunkailun
     * @DateTime 2026/05/27
     * @email 376253703@qq.com
     */
    @GetMapping(params = {"current", "size"})
    @Operation(summary = "分页查询角色", description = "按查询条件分页返回当前租户下的角色数据，用于角色管理列表")
    public ApiResponse<Page<AuthRoleVO>> page(@ParameterObject @Validated(AuthRoleQuery.Select.class) AuthRoleQuery query) {
        // 创建MyBatis-Plus分页对象。
        Page<AuthRole> page = new Page<>(query.getCurrent(), query.getSize());
        // 查询角色分页并转换为VO。
        return ApiResponse.success(authRoleService.page(page, query)); // 使用统一成功工厂方法组装 success、code、msg、data 和 timestamp。
    }

    /**
     * 新增角色。
     *
     * @param id 角色主键
     * @param bo 角色写入参数
     * @return 角色ID
     * @author sunkailun
     * @DateTime 2026/05/26
     * @email 376253703@qq.com
     */
    @PostMapping
    @Operation(summary = "新增角色", description = "创建认证角色并返回新角色ID")
    public ApiResponse<String> save(@Validated(AuthRoleBO.Save.class) @RequestBody AuthRoleBO bo) {
        // 新增角色并返回角色ID。
        return ApiResponse.success(authRoleService.save(bo)); // 使用统一成功工厂方法组装 success、code、msg、data 和 timestamp。
    }

    /**
     * 修改角色。
     *
     * @param bo 角色写入参数
     * @return 是否成功
     * @author sunkailun
     * @DateTime 2026/05/26
     * @email 376253703@qq.com
     */
    @PutMapping("/{id}")
    @Operation(summary = "修改角色", description = "根据角色ID和version修改角色资料，并通过乐观锁防止并发覆盖")
    public ApiResponse<Boolean> update(@PathVariable String id, @Validated(AuthRoleBO.Update.class) @RequestBody AuthRoleBO bo) {
        // 将路径主键写入 BO，避免请求体主键和路径主键不一致。
        bo.setId(id);
        // 修改角色并使用version触发乐观锁。
        return ApiResponse.success(authRoleService.update(bo)); // 使用统一成功工厂方法组装 success、code、msg、data 和 timestamp。
    }

    /**
     * 删除角色。
     *
     * @param id       角色主键
     * @param tenantId 租户ID
     * @return 是否成功
     * @author sunkailun
     * @DateTime 2026/05/26
     * @email 376253703@qq.com
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "删除角色", description = "根据角色ID逻辑删除角色，不物理删除历史数据")
    public ApiResponse<Boolean> remove(@PathVariable String id, @RequestParam String tenantId) {
        // 逻辑删除角色。
        return ApiResponse.success(authRoleService.remove(tenantId, id)); // 使用统一成功工厂方法组装 success、code、msg、data 和 timestamp。
    }
}
