package com.kellen.auth.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kellen.auth.entity.AuthDept;
import com.kellen.auth.entity.bo.AuthDeptBO;
import com.kellen.auth.entity.query.AuthDeptQuery;
import com.kellen.auth.entity.vo.AuthDeptVO;
import com.kellen.auth.service.AuthDeptService;
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
 * 部门维护请求层。
 *
 * @author sunkailun
 * @className AuthDeptController
 * @time 2026/05/27
 */
@RestController
@RequestMapping("/auth/manage/depts")
@PreAuthorize("hasAuthority('user:auth:manage')")
@Tag(name = "部门管理", description = "维护组织部门树和数据权限归属部门")
public class AuthDeptController {

    /**
     * 部门业务服务。
     */
    private final AuthDeptService authDeptService;

    /**
     * 构造部门维护请求层。
     *
     * @param authDeptService 部门业务服务
     */
    public AuthDeptController(AuthDeptService authDeptService) {
        this.authDeptService = authDeptService; // 注入部门业务服务。
    }

    /**
     * 查询部门选项。
     *
     * @param query 部门查询参数
     * @return 部门选项列表
     */
    @GetMapping("/options")
    @Operation(summary = "查询部门选项", description = "按查询条件返回当前租户下的部门轻量选项列表，用于组织树和数据权限选择")
    public ApiResponse<List<AuthDeptVO>> list(@ParameterObject @Validated AuthDeptQuery query) {
        return ApiResponse.success(authDeptService.list(query)); // 查询指定租户的部门轻量选项列表。
    }

    /**
     * 分页查询部门。
     *
     * @param query 部门查询参数
     * @return 部门分页
     */
    @GetMapping(params = {"current", "size"})
    @Operation(summary = "分页查询部门", description = "按查询条件分页返回当前租户下的部门数据")
    public ApiResponse<Page<AuthDeptVO>> page(@ParameterObject @Validated(AuthDeptQuery.Select.class) AuthDeptQuery query) {
        Page<AuthDept> page = new Page<>(query.getCurrent(), query.getSize()); // 创建 MyBatis-Plus 分页对象。
        return ApiResponse.success(authDeptService.page(page, query)); // 查询部门分页并返回统一响应。
    }

    /**
     * 新增部门。
     *
     * @param bo 部门写入参数
     * @return 部门ID
     */
    @PostMapping
    @Operation(summary = "新增部门", description = "创建组织部门并返回新部门ID")
    public ApiResponse<String> save(@Validated(AuthDeptBO.Save.class) @RequestBody AuthDeptBO bo) {
        return ApiResponse.success(authDeptService.save(bo)); // 新增部门并返回部门ID。
    }

    /**
     * 修改部门。
     *
     * @param bo 部门写入参数
     * @return 是否成功
     */
    @PutMapping("/{id}")
    @Operation(summary = "修改部门", description = "根据部门ID和version修改部门资料，并通过乐观锁防止并发覆盖")
    public ApiResponse<Boolean> update(@PathVariable String id, @Validated(AuthDeptBO.Update.class) @RequestBody AuthDeptBO bo) {
        bo.setId(id); // 将路径主键写入 BO，避免请求体主键和路径主键不一致。
        return ApiResponse.success(authDeptService.update(bo)); // 修改部门并使用 version 触发乐观锁。
    }

    /**
     * 删除部门。
     *
     * @param id       部门主键
     * @param tenantId 租户ID
     * @return 是否成功
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "删除部门", description = "根据部门ID逻辑删除部门，不物理删除历史数据")
    public ApiResponse<Boolean> remove(@PathVariable String id, @RequestParam String tenantId) {
        return ApiResponse.success(authDeptService.remove(tenantId, id)); // 逻辑删除部门。
    }
}
