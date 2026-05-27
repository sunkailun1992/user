package com.kellen.auth.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kellen.auth.entity.AuthUser;
import com.kellen.auth.entity.bo.AuthUserBO;
import com.kellen.auth.entity.query.AuthUserQuery;
import com.kellen.auth.entity.vo.AuthUserVO;
import com.kellen.auth.service.AuthUserService;
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
 * 用户维护请求层。
 *
 * @author sunkailun
 * @className AuthUserController
 * @time 2026/05/26
 */
@RestController
@RequestMapping("/auth/manage/users")
@PreAuthorize("hasAuthority('user:auth:manage')")
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
     * 查询用户列表。
     *
     * @param query 用户查询参数
     * @return 用户列表
     * @author sunkailun
     * @DateTime 2026/05/26
     * @email 376253703@qq.com
     */
    @GetMapping
    public ApiResponse<List<AuthUserVO>> list(@Validated AuthUserQuery query) {
        // 查询指定租户的用户列表。
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
    @PostMapping("/page")
    public ApiResponse<Page<AuthUserVO>> page(@Validated(AuthUserQuery.Select.class) @RequestBody AuthUserQuery query) {
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
    @PutMapping
    public ApiResponse<Boolean> update(@Validated(AuthUserBO.Update.class) @RequestBody AuthUserBO bo) {
        // 修改用户并使用version触发乐观锁。
        return ApiResponse.success(authUserService.update(bo)); // 使用统一成功工厂方法组装 success、code、msg、data 和 timestamp。
    }

    /**
     * 删除用户。
     *
     * @param bo 用户删除参数
     * @return 是否成功
     * @author sunkailun
     * @DateTime 2026/05/26
     * @email 376253703@qq.com
     */
    @PostMapping("/remove")
    public ApiResponse<Boolean> remove(@Validated(AuthUserBO.Remove.class) @RequestBody AuthUserBO bo) {
        // 逻辑删除用户。
        return ApiResponse.success(authUserService.remove(bo)); // 使用统一成功工厂方法组装 success、code、msg、data 和 timestamp。
    }
}
