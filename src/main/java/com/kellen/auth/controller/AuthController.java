package com.kellen.auth.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.kellen.auth.dto.LoginRequest;
import com.kellen.auth.entity.AuthResource;
import com.kellen.auth.entity.enums.AuthResourceCategoryEnum;
import com.kellen.auth.entity.AuthRoleResource;
import com.kellen.auth.entity.AuthUser;
import com.kellen.auth.entity.AuthUserRole;
import com.kellen.auth.mapper.AuthResourceMapper;
import com.kellen.auth.mapper.AuthRoleResourceMapper;
import com.kellen.auth.mapper.AuthUserMapper;
import com.kellen.auth.mapper.AuthUserRoleMapper;
import com.kellen.security.SecurityUser;
import com.kellen.security.UserContextHolder;
import com.kellen.utils.Json;
import com.kellen.utils.JwtUtil;
import com.kellen.utils.TenantContextHolder;
import com.kellen.utils.enumeration.ReturnCode;
import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * 认证授权请求层
 * <p>
 * 提供演示数据初始化、用户登录、当前用户资源查询能力。
 * 登录成功后返回后端接口权限和前端资源，业务接口通过后端权限码做访问控制。
 *
 * @author sunkailun
 * @className AuthController
 * @time 2026/05/25
 */
@RestController
@RequestMapping("/auth")
public class AuthController {

    private static final String DEFAULT_TENANT_ID = "100";
    private static final AuthResourceCategoryEnum FRONTEND_RESOURCE = AuthResourceCategoryEnum.FRONTEND;
    private static final AuthResourceCategoryEnum BACKEND_RESOURCE = AuthResourceCategoryEnum.BACKEND;

    private final JdbcTemplate jdbcTemplate;
    private final AuthUserMapper authUserMapper;
    private final AuthUserRoleMapper authUserRoleMapper;
    private final AuthRoleResourceMapper authRoleResourceMapper;
    private final AuthResourceMapper authResourceMapper;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    /**
     * 构造认证授权请求层。
     *
     * @param jdbcTemplate            JDBC操作对象
     * @param authUserMapper          认证用户mapper
     * @param authUserRoleMapper      用户角色关系mapper
     * @param authRoleResourceMapper  角色资源关系mapper
     * @param authResourceMapper      认证资源mapper
     */
    public AuthController(JdbcTemplate jdbcTemplate,
                          AuthUserMapper authUserMapper,
                          AuthUserRoleMapper authUserRoleMapper,
                          AuthRoleResourceMapper authRoleResourceMapper,
                          AuthResourceMapper authResourceMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.authUserMapper = authUserMapper;
        this.authUserRoleMapper = authUserRoleMapper;
        this.authRoleResourceMapper = authRoleResourceMapper;
        this.authResourceMapper = authResourceMapper;
    }

    /**
     * 初始化认证授权演示数据。
     *
     * @return 初始化结果
     * @author sunkailun
     * @methodName initDemo
     * @time 2026/05/25
     */
    @PostMapping("/init-demo")
    public Json<Object> initDemo() {
        createTables();
        String password = passwordEncoder.encode("123456");
        jdbcTemplate.update("""
                INSERT IGNORE INTO auth_user
                (id, username, password, nickname, code, description, create_name, modify_name, tenant_id)
                VALUES
                ('u_admin_100', 'admin', ?, '管理员', 'admin', '演示管理员账号', 'system', 'system', ?)
                """, password, DEFAULT_TENANT_ID);
        jdbcTemplate.update("""
                INSERT IGNORE INTO auth_role
                (id, name, code, description, create_name, modify_name, tenant_id)
                VALUES
                ('r_admin_100', '管理员', 'admin', '演示管理员角色', 'system', 'system', ?)
                """, DEFAULT_TENANT_ID);
        jdbcTemplate.update("""
                INSERT IGNORE INTO auth_resource
                (id, name, resource_category, path, method, parent_id, code, description, create_name, modify_name, sorting, tenant_id)
                VALUES
                ('res_menu_user_100', '用户管理', 'FRONTEND', '/system/user', NULL, NULL, 'menu:user', '前端菜单资源', 'system', 'system', 10, ?),
                ('res_menu_role_100', '角色管理', 'FRONTEND', '/system/role', NULL, NULL, 'menu:role', '前端菜单资源', 'system', 'system', 20, ?),
                ('res_api_tenant_list_100', '租户演示列表', 'BACKEND', '/tenant-demo/list', 'GET', NULL, 'user:tenant-demo:list', '后端接口权限', 'system', 'system', 10, ?),
                ('res_api_auth_resource_100', '当前资源列表', 'BACKEND', '/auth/resources', 'GET', NULL, 'user:auth:resources', '后端接口权限', 'system', 'system', 20, ?)
                """, DEFAULT_TENANT_ID, DEFAULT_TENANT_ID, DEFAULT_TENANT_ID, DEFAULT_TENANT_ID);
        jdbcTemplate.update("""
                INSERT IGNORE INTO auth_user_role
                (id, user_id, role_id, code, description, create_name, modify_name, tenant_id)
                VALUES
                ('ur_admin_100', 'u_admin_100', 'r_admin_100', 'admin:admin', '演示用户角色关系', 'system', 'system', ?)
                """, DEFAULT_TENANT_ID);
        jdbcTemplate.update("""
                INSERT IGNORE INTO auth_role_resource
                (id, role_id, resource_id, code, description, create_name, modify_name, tenant_id)
                VALUES
                ('rr_admin_menu_user_100', 'r_admin_100', 'res_menu_user_100', 'admin:menu:user', '演示角色资源关系', 'system', 'system', ?),
                ('rr_admin_menu_role_100', 'r_admin_100', 'res_menu_role_100', 'admin:menu:role', '演示角色资源关系', 'system', 'system', ?),
                ('rr_admin_api_tenant_list_100', 'r_admin_100', 'res_api_tenant_list_100', 'admin:api:tenant-list', '演示角色资源关系', 'system', 'system', ?),
                ('rr_admin_api_auth_resource_100', 'r_admin_100', 'res_api_auth_resource_100', 'admin:api:auth-resource', '演示角色资源关系', 'system', 'system', ?)
                """, DEFAULT_TENANT_ID, DEFAULT_TENANT_ID, DEFAULT_TENANT_ID, DEFAULT_TENANT_ID);
        return success(Map.of("tenantId", DEFAULT_TENANT_ID, "username", "admin", "password", "123456"));
    }

    /**
     * 用户登录。
     * <p>
     * 登录成功后生成JWT，并返回前端资源和后端接口权限。
     *
     * @param request 登录请求参数
     * @return 登录结果
     * @author sunkailun
     * @methodName login
     * @time 2026/05/25
     */
    @PostMapping("/login")
    public Json<Object> login(@RequestBody LoginRequest request) {
        String tenantId = StringUtils.defaultIfBlank(request.getTenantId(), DEFAULT_TENANT_ID);
        if (StringUtils.isBlank(request.getUsername()) || StringUtils.isBlank(request.getPassword())) {
            return error(ReturnCode.请求必填参数为空, "用户名或密码不能为空");
        }
        try {
            TenantContextHolder.setTenantId(tenantId);
            AuthUser user = authUserMapper.selectOne(new LambdaQueryWrapper<AuthUser>()
                    .eq(AuthUser::getUsername, request.getUsername())
                    .last("LIMIT 1"));
            if (user == null || !passwordEncoder.matches(request.getPassword(), user.getPassword())) {
                return error(ReturnCode.用户密码错误, "用户名或密码错误");
            }
            List<AuthResource> resources = findResourcesByUserId(user.getId());
            List<String> permissions = resources.stream()
                    .filter(resource -> BACKEND_RESOURCE == resource.getResourceCategory())
                    .map(AuthResource::getCode)
                    .filter(StringUtils::isNotBlank)
                    .distinct()
                    .toList();

            Map<String, Object> claims = new HashMap<>();
            claims.put("userId", user.getId());
            claims.put("username", user.getUsername());
            claims.put("tenantId", tenantId);
            claims.put("permissions", permissions);
            String token = JwtUtil.createJwt(UUID.randomUUID().toString(), user.getId(), claims);

            Map<String, Object> data = new LinkedHashMap<>();
            data.put("token", token);
            data.put("tokenType", "Bearer");
            data.put("userId", user.getId());
            data.put("username", user.getUsername());
            data.put("nickname", user.getNickname());
            data.put("tenantId", tenantId);
            data.put("permissions", permissions);
            data.put("frontendResources", toResourceViews(resources, FRONTEND_RESOURCE));
            data.put("backendResources", toResourceViews(resources, BACKEND_RESOURCE));
            return success(data);
        } finally {
            TenantContextHolder.clear();
        }
    }

    /**
     * 当前用户资源。
     * <p>
     * 该接口自身也受后端权限码 user:auth:resources 控制。
     *
     * @return 当前用户资源
     * @author sunkailun
     * @methodName resources
     * @time 2026/05/25
     */
    @GetMapping("/resources")
    @PreAuthorize("hasAuthority('user:auth:resources')")
    public Json<Object> resources() {
        SecurityUser currentUser = UserContextHolder.get();
        if (currentUser == null) {
            return error(ReturnCode.用户身份校验失败, "用户未登录");
        }
        try {
            TenantContextHolder.setTenantId(currentUser.getTenantId());
            List<AuthResource> resources = findResourcesByUserId(currentUser.getUserId());
            Map<String, Object> data = new LinkedHashMap<>();
            data.put("userId", currentUser.getUserId());
            data.put("tenantId", currentUser.getTenantId());
            data.put("permissions", toPermissionCodes(resources));
            data.put("frontendResources", toResourceViews(resources, FRONTEND_RESOURCE));
            data.put("backendResources", toResourceViews(resources, BACKEND_RESOURCE));
            return success(data);
        } finally {
            TenantContextHolder.clear();
        }
    }

    /**
     * 根据用户ID查询用户拥有的全部资源。
     *
     * @param userId 用户ID
     * @return 资源列表
     */
    private List<AuthResource> findResourcesByUserId(String userId) {
        List<AuthUserRole> userRoles = authUserRoleMapper.selectList(new LambdaQueryWrapper<AuthUserRole>()
                .eq(AuthUserRole::getUserId, userId));
        Set<String> roleIds = userRoles.stream().map(AuthUserRole::getRoleId).collect(Collectors.toSet());
        if (roleIds.isEmpty()) {
            return List.of();
        }
        List<AuthRoleResource> roleResources = authRoleResourceMapper.selectList(new LambdaQueryWrapper<AuthRoleResource>()
                .in(AuthRoleResource::getRoleId, roleIds));
        Set<String> resourceIds = roleResources.stream().map(AuthRoleResource::getResourceId).collect(Collectors.toSet());
        if (resourceIds.isEmpty()) {
            return List.of();
        }
        return authResourceMapper.selectList(new LambdaQueryWrapper<AuthResource>()
                .in(AuthResource::getId, resourceIds)
                .orderByAsc(AuthResource::getSorting));
    }

    /**
     * 提取后端接口权限码。
     *
     * @param resources 资源列表
     * @return 后端接口权限码列表
     */
    private List<String> toPermissionCodes(List<AuthResource> resources) {
        return resources.stream()
                .filter(resource -> BACKEND_RESOURCE == resource.getResourceCategory())
                .map(AuthResource::getCode)
                .filter(StringUtils::isNotBlank)
                .distinct()
                .toList();
    }

    /**
     * 转换指定分类的资源展示结果。
     *
     * @param resources 资源列表
     * @param category  资源分类
     * @return 资源展示结果
     */
    private List<Map<String, Object>> toResourceViews(List<AuthResource> resources, AuthResourceCategoryEnum category) {
        return resources.stream()
                .filter(resource -> category == resource.getResourceCategory())
                .sorted(Comparator.comparing(resource -> resource.getSorting() == null ? 0 : resource.getSorting()))
                .map(resource -> {
                    Map<String, Object> item = new LinkedHashMap<>();
                    item.put("id", resource.getId());
                    item.put("code", resource.getCode());
                    item.put("name", resource.getName());
                    item.put("category", resource.getResourceCategory().getValue());
                    item.put("categoryDesc", resource.getResourceCategory().getDesc());
                    item.put("path", resource.getPath());
                    item.put("method", resource.getMethod());
                    item.put("parentId", resource.getParentId());
                    item.put("sorting", resource.getSorting());
                    return item;
                })
                .toList();
    }

    /**
     * 创建认证授权演示表。
     * <p>
     * 表结构保留历史实体公共字段，和 EntityBase 中的字段保持一致。
     */
    private void createTables() {
        String commonColumns = """
                    code varchar(255) DEFAULT NULL COMMENT '编码',
                    description varchar(255) DEFAULT NULL COMMENT '说明',
                    create_date_time datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                    create_name varchar(255) DEFAULT NULL COMMENT '创建人',
                    modify_date_time datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
                    modify_name varchar(255) DEFAULT NULL COMMENT '修改人',
                    is_delete bit(1) DEFAULT b'0' COMMENT '删除状态',
                    type int DEFAULT '0' COMMENT '类型（0：默认）',
                    state int DEFAULT '0' COMMENT '状态（0：默认）',
                    label varchar(255) DEFAULT NULL COMMENT '标签',
                    sorting int DEFAULT '0' COMMENT '排序',
                    version int DEFAULT '1' COMMENT '版本号',
                    tenant_id varchar(64) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '1' COMMENT '租户id'
                """;
        jdbcTemplate.execute("""
                CREATE TABLE IF NOT EXISTS auth_user (
                    id varchar(64) NOT NULL PRIMARY KEY,
                    username varchar(255) NOT NULL COMMENT '用户名',
                    password varchar(255) NOT NULL COMMENT '密码',
                    nickname varchar(255) DEFAULT NULL COMMENT '昵称',
                """ + commonColumns + """
                    ,
                    UNIQUE KEY uk_auth_user_tenant_username (tenant_id, username),
                    KEY idx_auth_user_tenant_id (tenant_id)
                ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='认证用户表'
                """);
        jdbcTemplate.execute("""
                CREATE TABLE IF NOT EXISTS auth_role (
                    id varchar(64) NOT NULL PRIMARY KEY,
                    name varchar(255) NOT NULL COMMENT '角色名称',
                """ + commonColumns + """
                    ,
                    UNIQUE KEY uk_auth_role_tenant_code (tenant_id, code),
                    KEY idx_auth_role_tenant_id (tenant_id)
                ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='认证角色表'
                """);
        jdbcTemplate.execute("""
                CREATE TABLE IF NOT EXISTS auth_resource (
                    id varchar(64) NOT NULL PRIMARY KEY,
                    name varchar(255) NOT NULL COMMENT '资源名称',
                    resource_category varchar(32) NOT NULL COMMENT '资源分类：FRONTEND/BACKEND',
                    path varchar(255) DEFAULT NULL COMMENT '资源路径',
                    method varchar(32) DEFAULT NULL COMMENT '请求方法',
                    parent_id varchar(64) DEFAULT NULL COMMENT '父级资源ID',
                """ + commonColumns + """
                    ,
                    UNIQUE KEY uk_auth_resource_tenant_code (tenant_id, code),
                    KEY idx_auth_resource_tenant_id (tenant_id)
                ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='认证资源表'
                """);
        jdbcTemplate.execute("""
                CREATE TABLE IF NOT EXISTS auth_user_role (
                    id varchar(64) NOT NULL PRIMARY KEY,
                    user_id varchar(64) NOT NULL COMMENT '用户ID',
                    role_id varchar(64) NOT NULL COMMENT '角色ID',
                """ + commonColumns + """
                    ,
                    UNIQUE KEY uk_auth_user_role_tenant_user_role (tenant_id, user_id, role_id),
                    KEY idx_auth_user_role_tenant_id (tenant_id)
                ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='认证用户角色关系表'
                """);
        jdbcTemplate.execute("""
                CREATE TABLE IF NOT EXISTS auth_role_resource (
                    id varchar(64) NOT NULL PRIMARY KEY,
                    role_id varchar(64) NOT NULL COMMENT '角色ID',
                    resource_id varchar(64) NOT NULL COMMENT '资源ID',
                """ + commonColumns + """
                    ,
                    UNIQUE KEY uk_auth_role_resource_tenant_role_resource (tenant_id, role_id, resource_id),
                    KEY idx_auth_role_resource_tenant_id (tenant_id)
                ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='认证角色资源关系表'
                """);
    }

    /**
     * 构建成功返回结果。
     *
     * @param data 返回数据
     * @return 统一返回结果
     */
    private Json<Object> success(Object data) {
        return new Json<>(ReturnCode.成功, data);
    }

    /**
     * 构建失败返回结果。
     *
     * @param returnCode 返回码
     * @param message    错误说明
     * @return 统一返回结果
     */
    private Json<Object> error(ReturnCode returnCode, String message) {
        return new Json<>(returnCode, null, message);
    }
}
