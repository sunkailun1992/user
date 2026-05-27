package com.kellen.auth.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kellen.auth.entity.AuthDept;
import com.kellen.auth.entity.bo.AuthDeptBO;
import com.kellen.auth.entity.query.AuthDeptQuery;
import com.kellen.auth.entity.vo.AuthDeptVO;
import com.kellen.auth.service.AuthDeptService;
import com.kellen.utils.response.ApiResponse;
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
 * 部门维护请求层。
 *
 * @author sunkailun
 * @className AuthDeptController
 * @time 2026/05/27
 */
@RestController
@RequestMapping("/auth/manage/depts")
@PreAuthorize("hasAuthority('user:auth:manage')")
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
     * 查询部门列表。
     *
     * @param query 部门查询参数
     * @return 部门列表
     */
    @GetMapping
    public ApiResponse<List<AuthDeptVO>> list(@Validated AuthDeptQuery query) {
        return ApiResponse.success(authDeptService.list(query)); // 查询指定租户的部门列表。
    }

    /**
     * 分页查询部门。
     *
     * @param query 部门查询参数
     * @return 部门分页
     */
    @PostMapping("/page")
    public ApiResponse<Page<AuthDeptVO>> page(@Validated(AuthDeptQuery.Select.class) @RequestBody AuthDeptQuery query) {
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
    public ApiResponse<String> save(@Validated(AuthDeptBO.Save.class) @RequestBody AuthDeptBO bo) {
        return ApiResponse.success(authDeptService.save(bo)); // 新增部门并返回部门ID。
    }

    /**
     * 修改部门。
     *
     * @param bo 部门写入参数
     * @return 是否成功
     */
    @PutMapping
    public ApiResponse<Boolean> update(@Validated(AuthDeptBO.Update.class) @RequestBody AuthDeptBO bo) {
        return ApiResponse.success(authDeptService.update(bo)); // 修改部门并使用 version 触发乐观锁。
    }

    /**
     * 删除部门。
     *
     * @param bo 部门删除参数
     * @return 是否成功
     */
    @PostMapping("/remove")
    public ApiResponse<Boolean> remove(@Validated(AuthDeptBO.Remove.class) @RequestBody AuthDeptBO bo) {
        return ApiResponse.success(authDeptService.remove(bo)); // 逻辑删除部门。
    }
}
