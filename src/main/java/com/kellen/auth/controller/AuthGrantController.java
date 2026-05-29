package com.kellen.auth.controller;

import com.kellen.auth.entity.bo.AuthRoleResourceBO;
import com.kellen.auth.entity.bo.AuthRoleResourceSyncBO;
import com.kellen.auth.entity.bo.AuthRoleDataScopeSyncBO;
import com.kellen.auth.entity.bo.AuthUserRoleBO;
import com.kellen.auth.entity.bo.AuthUserRoleSyncBO;
import com.kellen.auth.service.AuthGrantService;
import com.kellen.utils.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "授权关系管理", description = "维护用户角色、角色资源和角色自定义数据范围关系")
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
    @Operation(summary = "绑定用户角色", description = "为用户追加绑定指定角色关系")
    public ApiResponse<Boolean> bindUserRole(@Validated @RequestBody AuthUserRoleBO bo) {
        // 绑定用户和角色关系。
        return ApiResponse.success(authGrantService.bindUserRole(bo)); // 使用统一成功工厂方法组装 success、code、msg、data 和 timestamp。
    }

    /**
     * 查询用户已绑定角色。
     *
     * @param tenantId 租户ID
     * @param userId   用户ID
     * @return 角色ID列表
     * @author sunkailun
     * @DateTime 2026/05/29
     * @email 376253703@qq.com
     */
    @GetMapping("/user-roles")
    @Operation(summary = "查询用户角色", description = "查询用户已绑定的角色ID列表，用于用户授权回显")
    public ApiResponse<List<String>> listUserRoleIds(@RequestParam String tenantId, @RequestParam String userId) {
        // 返回当前用户已绑定的角色ID列表，用于前端多选授权回显。
        return ApiResponse.success(authGrantService.listUserRoleIds(tenantId, userId)); // 使用统一成功工厂方法组装 success、code、msg、data 和 timestamp。
    }

    /**
     * 同步用户角色。
     *
     * @param bo 用户角色同步授权参数
     * @return 是否成功
     * @author sunkailun
     * @DateTime 2026/05/29
     * @email 376253703@qq.com
     */
    @PutMapping("/user-roles")
    @Operation(summary = "同步用户角色", description = "按完整角色ID列表覆盖同步用户角色关系")
    public ApiResponse<Boolean> syncUserRoles(@Validated @RequestBody AuthUserRoleSyncBO bo) {
        // 按完整角色ID列表同步用户和角色关系。
        return ApiResponse.success(authGrantService.syncUserRoles(bo)); // 使用统一成功工厂方法组装 success、code、msg、data 和 timestamp。
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
    @Operation(summary = "绑定角色资源", description = "为角色追加绑定指定权限资源关系")
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
    @Operation(summary = "查询角色资源", description = "查询角色已绑定的权限资源ID列表，用于授权树回显")
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
    @Operation(summary = "同步角色资源", description = "按完整资源ID列表覆盖同步角色权限资源关系")
    public ApiResponse<Boolean> syncRoleResources(@Validated @RequestBody AuthRoleResourceSyncBO bo) {
        // 按完整资源ID列表同步角色和权限资源关系。
        return ApiResponse.success(authGrantService.syncRoleResources(bo)); // 使用统一成功工厂方法组装 success、code、msg、data 和 timestamp。
    }

    /**
     * 查询角色自定义数据范围部门。
     *
     * @param tenantId 租户ID
     * @param roleId   角色ID
     * @return 部门ID列表
     * @author sunkailun
     * @DateTime 2026/05/27
     * @email 376253703@qq.com
     */
    @GetMapping("/role-data-scopes")
    @Operation(summary = "查询角色数据范围部门", description = "查询角色自定义数据范围下已绑定的部门ID列表")
    public ApiResponse<List<String>> listRoleDataScopeDeptIds(@RequestParam String tenantId, @RequestParam String roleId) {
        // 返回当前角色自定义可见部门ID列表，用于前端部门树回显。
        return ApiResponse.success(authGrantService.listRoleDataScopeDeptIds(tenantId, roleId)); // 使用统一成功工厂方法组装 success、code、msg、data 和 timestamp。
    }

    /**
     * 同步角色自定义数据范围。
     *
     * @param bo 角色数据范围同步参数
     * @return 是否成功
     * @author sunkailun
     * @DateTime 2026/05/27
     * @email 376253703@qq.com
     */
    @PutMapping("/role-data-scopes")
    @Operation(summary = "同步角色数据范围部门", description = "按完整部门ID列表覆盖同步角色自定义数据范围")
    public ApiResponse<Boolean> syncRoleDataScopes(@Validated @RequestBody AuthRoleDataScopeSyncBO bo) {
        // 按完整部门ID列表同步角色自定义数据范围。
        return ApiResponse.success(authGrantService.syncRoleDataScopes(bo)); // 使用统一成功工厂方法组装 success、code、msg、data 和 timestamp。
    }
}
