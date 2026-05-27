package com.kellen.auth.controller;

import com.kellen.auth.entity.bo.AuthRoleResourceBO;
import com.kellen.auth.entity.bo.AuthRoleResourceSyncBO;
import com.kellen.auth.entity.bo.AuthUserRoleBO;
import com.kellen.auth.service.AuthGrantService;
import com.kellen.utils.response.ApiResponse;
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
    public ApiResponse<Boolean> bindUserRole(@Validated @RequestBody AuthUserRoleBO bo) {
        // 绑定用户和角色关系。
        return ApiResponse.success(authGrantService.bindUserRole(bo)); // 使用统一成功工厂方法组装 success、code、msg、data 和 timestamp。
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
    public ApiResponse<Boolean> bindRoleResource(@Validated @RequestBody AuthRoleResourceBO bo) {
        // 绑定角色和权限资源关系。
        return ApiResponse.success(authGrantService.bindRoleResource(bo)); // 使用统一成功工厂方法组装 success、code、msg、data 和 timestamp。
    }

    /**
     * 查询角色已绑定资源。
     *
     * @param tenantId 租户ID
     * @param roleId   角色ID
     * @return 资源ID列表
     * @author sunkailun
     * @DateTime 2026/05/27
     * @email 376253703@qq.com
     */
    @GetMapping("/role-resources")
    public ApiResponse<List<String>> listRoleResourceIds(@RequestParam String tenantId, @RequestParam String roleId) {
        // 返回当前角色已绑定的资源ID列表，用于前端树形授权回显。
        return ApiResponse.success(authGrantService.listRoleResourceIds(tenantId, roleId)); // 使用统一成功工厂方法组装 success、code、msg、data 和 timestamp。
    }

    /**
     * 同步角色资源。
     *
     * @param bo 角色资源同步授权参数
     * @return 是否成功
     * @author sunkailun
     * @DateTime 2026/05/27
     * @email 376253703@qq.com
     */
    @PutMapping("/role-resources")
    public ApiResponse<Boolean> syncRoleResources(@Validated @RequestBody AuthRoleResourceSyncBO bo) {
        // 按完整资源ID列表同步角色和权限资源关系。
        return ApiResponse.success(authGrantService.syncRoleResources(bo)); // 使用统一成功工厂方法组装 success、code、msg、data 和 timestamp。
    }
}
