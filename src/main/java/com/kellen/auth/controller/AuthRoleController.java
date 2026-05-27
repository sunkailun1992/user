package com.kellen.auth.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kellen.auth.entity.AuthRole;
import com.kellen.auth.entity.bo.AuthRoleBO;
import com.kellen.auth.entity.query.AuthRoleQuery;
import com.kellen.auth.entity.vo.AuthRoleVO;
import com.kellen.auth.service.AuthRoleService;
import com.kellen.utils.ApiResponse;
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
 * 角色维护请求层。
 *
 * @author sunkailun
 * @className AuthRoleController
 * @time 2026/05/26
 */
@RestController
@RequestMapping("/auth/manage/roles")
@PreAuthorize("hasAuthority('user:auth:manage')")
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
    @GetMapping
    public ApiResponse<List<AuthRoleVO>> list(@Validated AuthRoleQuery query) {
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
    @PostMapping("/page")
    public ApiResponse<Page<AuthRoleVO>> page(@Validated(AuthRoleQuery.Select.class) @RequestBody AuthRoleQuery query) {
        // 创建MyBatis-Plus分页对象。
        Page<AuthRole> page = new Page<>(query.getCurrent(), query.getSize());
        // 查询角色分页并转换为VO。
        return ApiResponse.success(authRoleService.page(page, query)); // 使用统一成功工厂方法组装 success、code、msg、data 和 timestamp。
    }

    /**
     * 新增角色。
     *
     * @param bo 角色写入参数
     * @return 角色ID
     * @author sunkailun
     * @DateTime 2026/05/26
     * @email 376253703@qq.com
     */
    @PostMapping
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
    @PutMapping
    public ApiResponse<Boolean> update(@Validated(AuthRoleBO.Update.class) @RequestBody AuthRoleBO bo) {
        // 修改角色并使用version触发乐观锁。
        return ApiResponse.success(authRoleService.update(bo)); // 使用统一成功工厂方法组装 success、code、msg、data 和 timestamp。
    }

    /**
     * 删除角色。
     *
     * @param bo 角色删除参数
     * @return 是否成功
     * @author sunkailun
     * @DateTime 2026/05/26
     * @email 376253703@qq.com
     */
    @PostMapping("/remove")
    public ApiResponse<Boolean> remove(@Validated(AuthRoleBO.Remove.class) @RequestBody AuthRoleBO bo) {
        // 逻辑删除角色。
        return ApiResponse.success(authRoleService.remove(bo)); // 使用统一成功工厂方法组装 success、code、msg、data 和 timestamp。
    }
}
