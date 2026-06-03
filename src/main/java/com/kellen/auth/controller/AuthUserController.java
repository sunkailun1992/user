package com.kellen.auth.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kellen.auth.entity.AuthUser;
import com.kellen.auth.entity.bo.AuthUserBO;
import com.kellen.auth.entity.query.AuthUserQuery;
import com.kellen.auth.entity.vo.AuthUserVO;
import com.kellen.auth.service.AuthUserService;
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
 * 用户维护请求层。
 *
 * @author sunkailun
 * @className AuthUserController
 * @time 2026/05/26
 */
@RestController
@RequestMapping("/auth/manage/users")
@PreAuthorize("hasAuthority('user:auth:manage')")
@Tag(name = "用户管理", description = "维护认证用户、所属部门、启用状态和角色授权基础数据")
public class AuthUserController {

    /**
     * 认证授权业务服务。
     */
    private final AuthUserService authUserService;

    /**
     * 构造用户维护请求层。
     *
     * @param authUserService 用户业务服务
     */
    public AuthUserController(AuthUserService authUserService) {
        // 注入用户业务服务。
        this.authUserService = authUserService;
    }

    /**
     * 查询用户选项。
     *
     * @param query 用户查询参数
     * @return 用户选项列表
     * @author sunkailun
     * @DateTime 2026/05/26
     * @email 376253703@qq.com
     */
    @GetMapping("/options")
    @Operation(summary = "查询用户选项", description = "按查询条件返回当前租户下的用户轻量选项列表，用于下拉选择和授权回显")
    public ApiResponse<List<AuthUserVO>> list(@ParameterObject @Validated AuthUserQuery query) {
        // 查询指定租户的用户轻量选项列表。
        return ApiResponse.success(authUserService.list(query)); // 使用统一成功工厂方法组装 success、code、msg、data 和 timestamp。
    }

    /**
     * 分页查询用户。
     *
     * @param query 用户查询参数
     * @return 用户分页
     * @author sunkailun
     * @DateTime 2026/05/27
     * @email 376253703@qq.com
     */
    @GetMapping(params = {"current", "size"})
    @Operation(summary = "分页查询用户", description = "按查询条件分页返回当前租户下的用户数据，用于用户管理列表")
    public ApiResponse<Page<AuthUserVO>> page(@ParameterObject @Validated(AuthUserQuery.Select.class) AuthUserQuery query) {
        // 创建MyBatis-Plus分页对象。
        Page<AuthUser> page = new Page<>(query.getCurrent(), query.getSize());
        // 查询用户分页并转换为VO。
        return ApiResponse.success(authUserService.page(page, query)); // 使用统一成功工厂方法组装 success、code、msg、data 和 timestamp。
    }

    /**
     * 新增用户。
     *
     * @param bo 用户写入参数
     * @return 用户ID
     * @author sunkailun
     * @DateTime 2026/05/26
     * @email 376253703@qq.com
     */
    @PostMapping
    @Operation(summary = "新增用户", description = "创建认证用户并绑定基础账号资料，返回新用户ID")
    public ApiResponse<String> save(@Validated(AuthUserBO.Save.class) @RequestBody AuthUserBO bo) {
        // 新增用户并返回用户ID。
        return ApiResponse.success(authUserService.save(bo)); // 使用统一成功工厂方法组装 success、code、msg、data 和 timestamp。
    }

    /**
     * 修改用户。
     *
     * @param bo 用户写入参数
     * @return 是否成功
     * @author sunkailun
     * @DateTime 2026/05/26
     * @email 376253703@qq.com
     */
    @PutMapping("/{id}")
    @Operation(summary = "修改用户", description = "根据用户ID和version修改认证用户资料，并通过乐观锁防止并发覆盖")
    public ApiResponse<Boolean> update(@PathVariable String id, @Validated(AuthUserBO.Update.class) @RequestBody AuthUserBO bo) {
        // 将路径主键写入 BO，避免请求体主键和路径主键不一致。
        bo.setId(id);
        // 修改用户并使用version触发乐观锁。
        return ApiResponse.success(authUserService.update(bo)); // 使用统一成功工厂方法组装 success、code、msg、data 和 timestamp。
    }

    /**
     * 删除用户。
     *
     * @param id       用户主键
     * @param tenantId 租户ID
     * @return 是否成功
     * @author sunkailun
     * @DateTime 2026/05/26
     * @email 376253703@qq.com
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "删除用户", description = "根据用户ID逻辑删除认证用户，不物理删除历史数据")
    public ApiResponse<Boolean> remove(@PathVariable String id, @RequestParam String tenantId) {
        // 逻辑删除用户。
        return ApiResponse.success(authUserService.remove(tenantId, id)); // 使用统一成功工厂方法组装 success、code、msg、data 和 timestamp。
    }
}
