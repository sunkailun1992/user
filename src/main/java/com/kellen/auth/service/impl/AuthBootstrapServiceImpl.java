package com.kellen.auth.service.impl;

import com.kellen.auth.entity.bo.AuthResourceBO;
import com.kellen.auth.entity.bo.AuthRoleBO;
import com.kellen.auth.entity.bo.AuthRoleResourceBO;
import com.kellen.auth.entity.bo.AuthTenantBO;
import com.kellen.auth.entity.bo.AuthUserBO;
import com.kellen.auth.entity.bo.AuthUserRoleBO;
import com.kellen.auth.entity.enums.AuthResourceCategoryEnum;
import com.kellen.auth.entity.enums.AuthStateEnum;
import com.kellen.auth.service.AuthBootstrapService;
import com.kellen.auth.service.AuthGrantService;
import com.kellen.auth.service.AuthResourceService;
import com.kellen.auth.service.AuthRoleService;
import com.kellen.auth.service.AuthTenantService;
import com.kellen.auth.service.AuthUserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

/**
 * 认证体系初始化服务实现。
 *
 * @author sunkailun
 * @className AuthBootstrapServiceImpl
 * @time 2026/05/26
 */
@Service
public class AuthBootstrapServiceImpl implements AuthBootstrapService {

    /**
     * 默认租户ID。
     */
    private static final String DEFAULT_TENANT_ID = "100";

    /**
     * 默认管理员用户名。
     */
    private static final String DEFAULT_ADMIN_USERNAME = "admin";

    /**
     * 默认管理员密码。
     */
    private static final String DEFAULT_ADMIN_PASSWORD = "123456";

    /**
     * 租户业务服务。
     */
    private final AuthTenantService authTenantService;

    /**
     * 用户业务服务。
     */
    private final AuthUserService authUserService;

    /**
     * 角色业务服务。
     */
    private final AuthRoleService authRoleService;

    /**
     * 权限资源业务服务。
     */
    private final AuthResourceService authResourceService;

    /**
     * 授权关系业务服务。
     */
    private final AuthGrantService authGrantService;

    /**
     * 构造认证体系初始化服务。
     *
     * @param authTenantService   租户业务服务
     * @param authUserService     用户业务服务
     * @param authRoleService     角色业务服务
     * @param authResourceService 权限资源业务服务
     * @param authGrantService    授权关系业务服务
     */
    public AuthBootstrapServiceImpl(AuthTenantService authTenantService,
                                    AuthUserService authUserService,
                                    AuthRoleService authRoleService,
                                    AuthResourceService authResourceService,
                                    AuthGrantService authGrantService) {
        // 保存租户业务服务。
        this.authTenantService = authTenantService;
        // 保存用户业务服务。
        this.authUserService = authUserService;
        // 保存角色业务服务。
        this.authRoleService = authRoleService;
        // 保存权限资源业务服务。
        this.authResourceService = authResourceService;
        // 保存授权关系业务服务。
        this.authGrantService = authGrantService;
    }

    /**
     * 初始化真实认证授权基础数据。
     *
     * @return 初始化结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Map<String, Object> init() {
        // 初始化默认租户。
        saveTenant();
        // 初始化默认管理员用户。
        saveAdminUser();
        // 初始化默认管理员角色。
        saveAdminRole();
        // 初始化默认权限资源。
        saveResources();
        // 初始化用户角色绑定。
        bindUserRole("u_admin_100", "r_admin_100");
        // 初始化角色资源绑定。
        bindRoleResource("r_admin_100", "res_menu_tenant_100");
        // 初始化角色资源绑定。
        bindRoleResource("r_admin_100", "res_menu_user_100");
        // 初始化角色资源绑定。
        bindRoleResource("r_admin_100", "res_menu_role_100");
        // 初始化角色资源绑定。
        bindRoleResource("r_admin_100", "res_menu_resource_100");
        // 初始化角色资源绑定。
        bindRoleResource("r_admin_100", "res_api_auth_resource_100");
        // 初始化角色资源绑定。
        bindRoleResource("r_admin_100", "res_api_auth_manage_100");
        // 返回默认登录信息。
        return Map.of("tenantId", DEFAULT_TENANT_ID, "username", DEFAULT_ADMIN_USERNAME, "password", DEFAULT_ADMIN_PASSWORD);
    }

    /**
     * 初始化默认租户。
     */
    private void saveTenant() {
        // 创建默认租户参数。
        AuthTenantBO bo = new AuthTenantBO();
        // 设置默认租户ID。
        bo.setId(DEFAULT_TENANT_ID);
        // 设置默认租户编码。
        bo.setCode("default");
        // 设置默认租户名称。
        bo.setName("默认租户");
        // 设置默认租户状态。
        bo.setState(AuthStateEnum.启用);
        // 新增默认租户。
        authTenantService.save(bo);
    }

    /**
     * 初始化默认管理员用户。
     */
    private void saveAdminUser() {
        // 创建默认管理员参数。
        AuthUserBO bo = new AuthUserBO();
        // 设置管理员固定ID。
        bo.setId("u_admin_100");
        // 设置默认租户ID。
        bo.setTenantId(DEFAULT_TENANT_ID);
        // 设置管理员用户名。
        bo.setUsername(DEFAULT_ADMIN_USERNAME);
        // 设置管理员密码。
        bo.setPassword(DEFAULT_ADMIN_PASSWORD);
        // 设置管理员昵称。
        bo.setNickname("管理员");
        // 设置管理员状态。
        bo.setState(AuthStateEnum.启用);
        // 新增管理员用户。
        authUserService.save(bo);
    }

    /**
     * 初始化默认管理员角色。
     */
    private void saveAdminRole() {
        // 创建默认角色参数。
        AuthRoleBO bo = new AuthRoleBO();
        // 设置管理员角色固定ID。
        bo.setId("r_admin_100");
        // 设置默认租户ID。
        bo.setTenantId(DEFAULT_TENANT_ID);
        // 设置角色编码。
        bo.setCode("admin");
        // 设置角色名称。
        bo.setName("管理员");
        // 设置角色状态。
        bo.setState(AuthStateEnum.启用);
        // 新增管理员角色。
        authRoleService.save(bo);
    }

    /**
     * 初始化默认权限资源。
     */
    private void saveResources() {
        // 初始化租户菜单。
        saveResource("res_menu_tenant_100", "menu:tenant", "租户管理", AuthResourceCategoryEnum.FRONTEND, "/system/tenant", null, 10);
        // 初始化用户菜单。
        saveResource("res_menu_user_100", "menu:user", "用户管理", AuthResourceCategoryEnum.FRONTEND, "/system/user", null, 20);
        // 初始化角色菜单。
        saveResource("res_menu_role_100", "menu:role", "角色管理", AuthResourceCategoryEnum.FRONTEND, "/system/role", null, 30);
        // 初始化资源菜单。
        saveResource("res_menu_resource_100", "menu:resource", "权限资源", AuthResourceCategoryEnum.FRONTEND, "/system/resource", null, 40);
        // 初始化当前资源接口。
        saveResource("res_api_auth_resource_100", "user:auth:resources", "当前资源列表", AuthResourceCategoryEnum.BACKEND, "/auth/resources", "GET", 10);
        // 初始化认证维护接口。
        saveResource("res_api_auth_manage_100", "user:auth:manage", "认证体系维护", AuthResourceCategoryEnum.BACKEND, "/auth/manage/**", "*", 20);
    }

    /**
     * 初始化单个权限资源。
     *
     * @param id       资源ID
     * @param code     权限编码
     * @param name     资源名称
     * @param category 资源分类
     * @param path     资源路径
     * @param method   请求方法
     * @param sorting  排序
     */
    private void saveResource(String id, String code, String name, AuthResourceCategoryEnum category, String path, String method, Integer sorting) {
        // 创建资源参数。
        AuthResourceBO bo = new AuthResourceBO();
        // 设置资源固定ID。
        bo.setId(id);
        // 设置默认租户ID。
        bo.setTenantId(DEFAULT_TENANT_ID);
        // 设置权限编码。
        bo.setCode(code);
        // 设置资源名称。
        bo.setName(name);
        // 设置资源分类。
        bo.setResourceCategory(category);
        // 设置资源路径。
        bo.setPath(path);
        // 设置请求方法。
        bo.setMethod(method);
        // 设置排序。
        bo.setSorting(sorting);
        // 设置资源状态。
        bo.setState(AuthStateEnum.启用);
        // 新增权限资源。
        authResourceService.save(bo);
    }

    /**
     * 初始化用户角色关系。
     *
     * @param userId 用户ID
     * @param roleId 角色ID
     */
    private void bindUserRole(String userId, String roleId) {
        // 创建用户角色绑定参数。
        AuthUserRoleBO bo = new AuthUserRoleBO();
        // 设置默认租户ID。
        bo.setTenantId(DEFAULT_TENANT_ID);
        // 设置用户ID。
        bo.setUserId(userId);
        // 设置角色ID。
        bo.setRoleId(roleId);
        // 绑定用户角色。
        authGrantService.bindUserRole(bo);
    }

    /**
     * 初始化角色资源关系。
     *
     * @param roleId     角色ID
     * @param resourceId 资源ID
     */
    private void bindRoleResource(String roleId, String resourceId) {
        // 创建角色资源绑定参数。
        AuthRoleResourceBO bo = new AuthRoleResourceBO();
        // 设置默认租户ID。
        bo.setTenantId(DEFAULT_TENANT_ID);
        // 设置角色ID。
        bo.setRoleId(roleId);
        // 设置资源ID。
        bo.setResourceId(resourceId);
        // 绑定角色资源。
        authGrantService.bindRoleResource(bo);
    }
}
