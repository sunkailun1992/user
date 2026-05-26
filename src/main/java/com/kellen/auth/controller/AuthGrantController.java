package com.kellen.auth.controller;

import com.kellen.auth.entity.bo.AuthRoleResourceBO;
import com.kellen.auth.entity.bo.AuthUserRoleBO;
import com.kellen.auth.service.AuthGrantService;
import com.kellen.utils.Json;
import com.kellen.utils.enumeration.ReturnCode;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 授权关系维护请求层。
 *
 * @author sunkailun
 * @className AuthGrantController
 * @time 2026/05/26
 */
@RestController
@RequestMapping("/auth/manage")
@PreAuthorize("hasAuthority('user:auth:manage')")
public class AuthGrantController {

    /**
     * 认证授权业务服务。
     */
    private final AuthGrantService authGrantService;

    /**
     * 构造授权关系维护请求层。
     *
     * @param authGrantService 授权关系业务服务
     */
    public AuthGrantController(AuthGrantService authGrantService) {
        // 注入授权关系业务服务。
        this.authGrantService = authGrantService;
    }

    /**
     * 绑定用户角色。
     *
     * @param bo 用户角色授权参数
     * @return 是否成功
     * @author sunkailun
     * @DateTime 2026/05/26
     * @email 376253703@qq.com
     */
    @PostMapping("/user-roles")
    public Json<Boolean> bindUserRole(@Validated @RequestBody AuthUserRoleBO bo) {
        // 绑定用户和角色关系。
        return new Json<>(ReturnCode.成功, authGrantService.bindUserRole(bo));
    }

    /**
     * 绑定角色资源。
     *
     * @param bo 角色资源授权参数
     * @return 是否成功
     * @author sunkailun
     * @DateTime 2026/05/26
     * @email 376253703@qq.com
     */
    @PostMapping("/role-resources")
    public Json<Boolean> bindRoleResource(@Validated @RequestBody AuthRoleResourceBO bo) {
        // 绑定角色和权限资源关系。
        return new Json<>(ReturnCode.成功, authGrantService.bindRoleResource(bo));
    }
}
