package com.kellen.auth.controller;

import com.kellen.auth.entity.AuthUser;
import com.kellen.auth.entity.bo.AuthUserBO;
import com.kellen.auth.service.AuthUserService;
import com.kellen.utils.Json;
import com.kellen.utils.enumeration.ReturnCode;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
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
     * @param tenantId 租户ID
     * @return 用户列表
     * @author sunkailun
     * @DateTime 2026/05/26
     * @email 376253703@qq.com
     */
    @GetMapping
    public Json<List<AuthUser>> list(@RequestParam String tenantId) {
        // 查询指定租户的用户列表。
        return new Json<>(ReturnCode.成功, authUserService.list(tenantId));
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
    public Json<String> save(@Validated(AuthUserBO.Save.class) @RequestBody AuthUserBO bo) {
        // 新增用户并返回用户ID。
        return new Json<>(ReturnCode.成功, authUserService.save(bo));
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
    public Json<Boolean> update(@Validated(AuthUserBO.Update.class) @RequestBody AuthUserBO bo) {
        // 修改用户并使用version触发乐观锁。
        return new Json<>(ReturnCode.成功, authUserService.update(bo));
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
    public Json<Boolean> remove(@Validated(AuthUserBO.Remove.class) @RequestBody AuthUserBO bo) {
        // 逻辑删除用户。
        return new Json<>(ReturnCode.成功, authUserService.remove(bo));
    }
}
