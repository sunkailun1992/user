package com.kellen.auth.controller;

import com.kellen.auth.entity.AuthTenant;
import com.kellen.auth.entity.bo.AuthTenantBO;
import com.kellen.auth.service.AuthTenantService;
import com.kellen.utils.Json;
import com.kellen.utils.enumeration.ReturnCode;
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
     * @return 租户列表
     * @author sunkailun
     * @DateTime 2026/05/26
     * @email 376253703@qq.com
     */
    @GetMapping
    public Json<List<AuthTenant>> list() {
        // 查询全部租户主数据。
        return new Json<>(ReturnCode.成功, authTenantService.list());
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
    public Json<String> save(@Validated(AuthTenantBO.Save.class) @RequestBody AuthTenantBO bo) {
        // 新增租户并返回租户ID。
        return new Json<>(ReturnCode.成功, authTenantService.save(bo));
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
    public Json<Boolean> update(@Validated(AuthTenantBO.Update.class) @RequestBody AuthTenantBO bo) {
        // 修改租户并使用version触发乐观锁。
        return new Json<>(ReturnCode.成功, authTenantService.update(bo));
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
    public Json<Boolean> remove(@Validated(AuthTenantBO.Remove.class) @RequestBody AuthTenantBO bo) {
        // 逻辑删除租户。
        return new Json<>(ReturnCode.成功, authTenantService.remove(bo));
    }
}
