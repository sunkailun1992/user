package com.kellen.auth.controller;

import com.kellen.auth.entity.AuthResource;
import com.kellen.auth.entity.bo.AuthResourceBO;
import com.kellen.auth.service.AuthResourceService;
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
 * 权限资源维护请求层。
 *
 * @author sunkailun
 * @className AuthResourceController
 * @time 2026/05/26
 */
@RestController
@RequestMapping("/auth/manage/resources")
@PreAuthorize("hasAuthority('user:auth:manage')")
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
     * @param tenantId 租户ID
     * @return 资源列表
     * @author sunkailun
     * @DateTime 2026/05/26
     * @email 376253703@qq.com
     */
    @GetMapping
    public Json<List<AuthResource>> list(@RequestParam String tenantId) {
        // 查询指定租户的权限资源列表。
        return new Json<>(ReturnCode.成功, authResourceService.list(tenantId));
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
    public Json<String> save(@Validated(AuthResourceBO.Save.class) @RequestBody AuthResourceBO bo) {
        // 新增权限资源并返回资源ID。
        return new Json<>(ReturnCode.成功, authResourceService.save(bo));
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
    public Json<Boolean> update(@Validated(AuthResourceBO.Update.class) @RequestBody AuthResourceBO bo) {
        // 修改权限资源并使用version触发乐观锁。
        return new Json<>(ReturnCode.成功, authResourceService.update(bo));
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
    public Json<Boolean> remove(@Validated(AuthResourceBO.Remove.class) @RequestBody AuthResourceBO bo) {
        // 逻辑删除权限资源。
        return new Json<>(ReturnCode.成功, authResourceService.remove(bo));
    }
}
