package com.kellen.auth.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.kellen.auth.dto.LoginRequest;
import com.kellen.auth.entity.AuthDept;
import com.kellen.auth.entity.AuthResource;
import com.kellen.auth.entity.AuthRole;
import com.kellen.auth.entity.AuthRoleDataScope;
import com.kellen.auth.entity.AuthTenant;
import com.kellen.auth.entity.AuthUser;
import com.kellen.auth.entity.AuthUserRole;
import com.kellen.auth.entity.enums.AuthDataScopeEnum;
import com.kellen.auth.entity.enums.AuthResourceCategoryEnum;
import com.kellen.auth.entity.enums.AuthStateEnum;
import com.kellen.auth.entity.vo.AuthCurrentResourceVO;
import com.kellen.auth.entity.vo.AuthLoginVO;
import com.kellen.auth.mapper.AuthDeptMapper;
import com.kellen.auth.mapper.AuthRoleDataScopeMapper;
import com.kellen.auth.mapper.AuthRoleMapper;
import com.kellen.auth.mapper.AuthTenantMapper;
import com.kellen.auth.mapper.AuthUserMapper;
import com.kellen.auth.mapper.AuthUserRoleMapper;
import com.kellen.auth.service.AuthAuthenticationService;
import com.kellen.auth.service.AuthGrantService;
import com.kellen.datapermission.DataPermissionContextHolder;
import com.kellen.security.SecurityUser;
import com.kellen.security.UserContextHolder;
import com.kellen.utils.auth.JwtUtils;
import com.kellen.utils.context.TenantContextHolder;
import com.kellen.utils.enumeration.ReturnCode;
import com.kellen.utils.exception.UserException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * 认证登录业务服务实现。
 *
 * @author sunkailun
 * @className AuthAuthenticationServiceImpl
 * @time 2026/05/26
 */
@Service
public class AuthAuthenticationServiceImpl implements AuthAuthenticationService {

    /**
     * 默认租户ID。
     */
    private static final String DEFAULT_TENANT_ID = "100";

    /**
     * 前端资源分类。
     */
    private static final AuthResourceCategoryEnum FRONTEND_RESOURCE = AuthResourceCategoryEnum.FRONTEND;

    /**
     * 后端资源分类。
     */
    private static final AuthResourceCategoryEnum BACKEND_RESOURCE = AuthResourceCategoryEnum.BACKEND;

    /**
     * 租户Mapper。
     */
    private final AuthTenantMapper authTenantMapper;

    /**
     * 用户Mapper。
     */
    private final AuthUserMapper authUserMapper;

    /**
     * 用户角色Mapper。
     */
    private final AuthUserRoleMapper authUserRoleMapper;

    /**
     * 角色Mapper。
     */
    private final AuthRoleMapper authRoleMapper;

    /**
     * 部门Mapper。
     */
    private final AuthDeptMapper authDeptMapper;

    /**
     * 角色自定义数据范围Mapper。
     */
    private final AuthRoleDataScopeMapper authRoleDataScopeMapper;

    /**
     * 授权关系业务服务。
     */
    private final AuthGrantService authGrantService;

    /**
     * 密码编码器。
     */
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    /**
     * 构造认证登录业务服务。
     *
     * @param authTenantMapper 租户Mapper
     * @param authUserMapper   用户Mapper
     * @param authUserRoleMapper       用户角色Mapper
     * @param authRoleMapper           角色Mapper
     * @param authDeptMapper           部门Mapper
     * @param authRoleDataScopeMapper  角色自定义数据范围Mapper
     * @param authGrantService         授权关系业务服务
     */
    public AuthAuthenticationServiceImpl(AuthTenantMapper authTenantMapper,
                                         AuthUserMapper authUserMapper,
                                         AuthUserRoleMapper authUserRoleMapper,
                                         AuthRoleMapper authRoleMapper,
                                         AuthDeptMapper authDeptMapper,
                                         AuthRoleDataScopeMapper authRoleDataScopeMapper,
                                         AuthGrantService authGrantService) {
        // 保存租户Mapper。
        this.authTenantMapper = authTenantMapper;
        // 保存用户Mapper。
        this.authUserMapper = authUserMapper;
        // 保存用户角色Mapper。
        this.authUserRoleMapper = authUserRoleMapper;
        // 保存角色Mapper。
        this.authRoleMapper = authRoleMapper;
        // 保存部门Mapper。
        this.authDeptMapper = authDeptMapper;
        // 保存角色自定义数据范围Mapper。
        this.authRoleDataScopeMapper = authRoleDataScopeMapper;
        // 保存授权关系业务服务。
        this.authGrantService = authGrantService;
    }

    /**
     * 用户登录。
     *
     * @param request 登录请求参数
     * @return 登录响应
     */
    @Override
    public AuthLoginVO login(LoginRequest request) {
        // 校验用户名。
        if (StringUtils.isBlank(request.getUsername())) {
            // 用户名为空时返回参数错误。
            throw new UserException(ReturnCode.请求必填参数为空, "用户名不能为空");
        }
        // 校验密码。
        if (StringUtils.isBlank(request.getPassword())) {
            // 密码为空时返回参数错误。
            throw new UserException(ReturnCode.请求必填参数为空, "密码不能为空");
        }
        // 解析租户ID。
        String tenantId = resolveTenantId(request);
        try {
            // 设置租户上下文。
            TenantContextHolder.setTenantId(tenantId);
            // 查询当前租户用户。
            AuthUser user = authUserMapper.selectOne(new LambdaQueryWrapper<AuthUser>().eq(AuthUser::getUsername, request.getUsername()).last("LIMIT 1"));
            // 校验用户和密码。
            if (user == null || !passwordEncoder.matches(request.getPassword(), user.getPassword())) {
                // 登录失败时不暴露账号是否存在。
                throw new UserException(ReturnCode.用户密码错误, "用户名或密码错误");
            }
            // 校验用户状态。
            if (AuthStateEnum.禁用 == user.getState()) {
                // 禁用账号不允许登录。
                throw new UserException(ReturnCode.用户账户被冻结, "用户已禁用");
            }
            // 查询用户资源。
            List<AuthResource> resources = authGrantService.findResourcesByUserId(user.getId());
            // 提取后端权限码。
            List<String> permissions = authGrantService.toPermissionCodes(resources);
            // 解析当前用户角色合并后的数据范围。
            DataScopeSnapshot dataScopeSnapshot = resolveDataScope(user);
            // 创建JWT声明。
            Map<String, Object> claims = new HashMap<>();
            // 写入用户ID。
            claims.put("userId", user.getId());
            // 写入用户名。
            claims.put("username", user.getUsername());
            // 写入租户ID。
            claims.put("tenantId", tenantId);
            // 写入部门ID。
            claims.put("deptId", user.getDeptId());
            // 写入数据权限范围。
            claims.put("dataScope", dataScopeSnapshot.dataScope());
            // 写入数据权限部门ID集合。
            claims.put("dataScopeDeptIds", dataScopeSnapshot.deptIds());
            // 写入权限码。
            claims.put("permissions", permissions);
            // 签发JWT。
            String token = JwtUtils.createJwt(UUID.randomUUID().toString(), user.getId(), claims);
            // 创建登录响应。
            AuthLoginVO vo = new AuthLoginVO();
            // 设置令牌。
            vo.setToken(token);
            // 设置令牌类型。
            vo.setTokenType("Bearer");
            // 设置用户ID。
            vo.setUserId(user.getId());
            // 设置用户名。
            vo.setUsername(user.getUsername());
            // 设置昵称。
            vo.setNickname(user.getNickname());
            // 设置租户ID。
            vo.setTenantId(tenantId);
            // 设置部门ID。
            vo.setDeptId(user.getDeptId());
            // 设置数据权限范围。
            vo.setDataScope(dataScopeSnapshot.dataScope());
            // 设置数据权限部门ID集合。
            vo.setDataScopeDeptIds(dataScopeSnapshot.deptIds());
            // 设置权限码。
            vo.setPermissions(permissions);
            // 设置前端资源。
            vo.setFrontendResources(authGrantService.toResourceViews(resources, FRONTEND_RESOURCE));
            // 设置后端资源。
            vo.setBackendResources(authGrantService.toResourceViews(resources, BACKEND_RESOURCE));
            // 返回登录响应。
            return vo;
        } finally {
            // 清理租户上下文。
            TenantContextHolder.clear();
        }
    }

    /**
     * 查询当前登录用户资源。
     *
     * @return 当前登录用户资源
     */
    @Override
    public AuthCurrentResourceVO currentResources() {
        // 获取当前登录用户。
        SecurityUser currentUser = UserContextHolder.get();
        // 校验用户是否登录。
        if (currentUser == null) {
            // 未登录时返回身份校验失败。
            throw new UserException(ReturnCode.用户身份校验失败, "用户未登录");
        }
        try {
            // 设置租户上下文。
            TenantContextHolder.setTenantId(currentUser.getTenantId());
            // 查询用户资源。
            List<AuthResource> resources = authGrantService.findResourcesByUserId(currentUser.getUserId());
            // 创建当前资源响应。
            AuthCurrentResourceVO vo = new AuthCurrentResourceVO();
            // 设置用户ID。
            vo.setUserId(currentUser.getUserId());
            // 设置租户ID。
            vo.setTenantId(currentUser.getTenantId());
            // 设置部门ID。
            vo.setDeptId(currentUser.getDeptId());
            // 设置权限码。
            vo.setPermissions(authGrantService.toPermissionCodes(resources));
            // 设置前端资源。
            vo.setFrontendResources(authGrantService.toResourceViews(resources, FRONTEND_RESOURCE));
            // 设置后端资源。
            vo.setBackendResources(authGrantService.toResourceViews(resources, BACKEND_RESOURCE));
            // 返回当前资源响应。
            return vo;
        } finally {
            // 清理租户上下文。
            TenantContextHolder.clear();
        }
    }

    /**
     * 根据登录请求解析租户ID。
     *
     * @param request 登录请求参数
     * @return 租户ID
     */
    private String resolveTenantId(LoginRequest request) {
        // 优先使用租户ID。
        if (StringUtils.isNotBlank(request.getTenantId())) {
            // 返回请求租户ID。
            return request.getTenantId();
        }
        // 没有租户编码时使用默认租户。
        if (StringUtils.isBlank(request.getTenantCode())) {
            // 返回默认租户ID。
            return DEFAULT_TENANT_ID;
        }
        try {
            // 租户表是全局主数据，按编码查询时忽略租户条件。
            TenantContextHolder.ignore();
            // 登录前租户解析不应被业务数据权限过滤。
            DataPermissionContextHolder.ignore();
            // 查询租户。
            AuthTenant tenant = authTenantMapper.selectOne(new LambdaQueryWrapper<AuthTenant>().eq(AuthTenant::getCode, request.getTenantCode()).last("LIMIT 1"));
            // 校验租户是否存在。
            if (tenant == null) {
                // 租户不存在按参数错误处理。
                throw new UserException(ReturnCode.用户请求参数错误, "租户不存在");
            }
            // 返回租户ID。
            return tenant.getId();
        } finally {
            // 清理忽略租户标记。
            TenantContextHolder.clearIgnore();
            // 清理数据权限忽略标记。
            DataPermissionContextHolder.clear();
        }
    }

    /**
     * 解析当前用户合并后的数据范围。
     *
     * @param user 当前登录用户
     * @return 数据范围快照
     * @author sunkailun
     * @DateTime 2026/05/27
     * @email 376253703@qq.com
     */
    private DataScopeSnapshot resolveDataScope(AuthUser user) {
        try {
            // 角色和部门授权配置用于计算数据范围自身，不能被尚未计算出的数据范围过滤。
            DataPermissionContextHolder.ignore();
            // 查询当前用户角色关系。
            List<AuthUserRole> userRoles = authUserRoleMapper.selectList(new LambdaQueryWrapper<AuthUserRole>().eq(AuthUserRole::getUserId, user.getId()));
            // 提取角色ID集合。
            Set<String> roleIds = userRoles.stream().map(AuthUserRole::getRoleId).filter(StringUtils::isNotBlank).collect(Collectors.toCollection(LinkedHashSet::new));
            // 没有角色时按本人数据范围兜底。
            if (roleIds.isEmpty()) {
                // 返回本人数据范围。
                return new DataScopeSnapshot(AuthDataScopeEnum.SELF.getValue(), List.of());
            }
            // 查询角色详情。
            List<AuthRole> roles = authRoleMapper.selectList(new LambdaQueryWrapper<AuthRole>().in(AuthRole::getId, roleIds));
            // 任一角色拥有全部数据时，合并结果就是全部数据。
            if (roles.stream().anyMatch(role -> AuthDataScopeEnum.ALL == role.getDataScope())) {
                // 返回全部数据范围。
                return new DataScopeSnapshot(AuthDataScopeEnum.ALL.getValue(), List.of());
            }
            // 创建可访问部门集合。
            Set<String> deptIds = new LinkedHashSet<>();
            // 遍历角色合并部门范围。
            for (AuthRole role : roles) {
                // 空数据范围按本人处理。
                AuthDataScopeEnum dataScope = role.getDataScope() == null ? AuthDataScopeEnum.SELF : role.getDataScope();
                // 本部门范围加入当前用户部门。
                if (AuthDataScopeEnum.DEPT == dataScope && StringUtils.isNotBlank(user.getDeptId())) {
                    deptIds.add(user.getDeptId());
                }
                // 本部门及下级部门范围加入部门树。
                if (AuthDataScopeEnum.DEPT_TREE == dataScope && StringUtils.isNotBlank(user.getDeptId())) {
                    deptIds.addAll(findDeptTreeIds(user.getDeptId()));
                }
                // 自定义范围加入角色绑定部门。
                if (AuthDataScopeEnum.CUSTOM == dataScope) {
                    deptIds.addAll(findCustomDeptIds(role.getId()));
                }
            }
            // 有部门范围时按自定义部门集合下发，SQL 层按集合过滤。
            if (!deptIds.isEmpty()) {
                // 返回自定义部门数据范围。
                return new DataScopeSnapshot(AuthDataScopeEnum.CUSTOM.getValue(), deptIds.stream().toList());
            }
            // 无部门范围时按本人数据范围兜底。
            return new DataScopeSnapshot(AuthDataScopeEnum.SELF.getValue(), List.of());
        } finally {
            // 清理数据权限忽略标记。
            DataPermissionContextHolder.clear();
        }
    }

    /**
     * 查询角色自定义部门ID集合。
     *
     * @param roleId 角色ID
     * @return 部门ID集合
     * @author sunkailun
     * @DateTime 2026/05/27
     * @email 376253703@qq.com
     */
    private List<String> findCustomDeptIds(String roleId) {
        // 查询角色绑定的数据范围部门。
        return authRoleDataScopeMapper.selectList(new LambdaQueryWrapper<AuthRoleDataScope>().eq(AuthRoleDataScope::getRoleId, roleId))
                .stream()
                .map(AuthRoleDataScope::getDeptId)
                .filter(StringUtils::isNotBlank)
                .distinct()
                .toList();
    }

    /**
     * 查询部门及下级部门ID集合。
     *
     * @param rootDeptId 根部门ID
     * @return 部门ID集合
     * @author sunkailun
     * @DateTime 2026/05/27
     * @email 376253703@qq.com
     */
    private List<String> findDeptTreeIds(String rootDeptId) {
        // 查询当前租户全部部门。
        List<AuthDept> depts = authDeptMapper.selectList(new LambdaQueryWrapper<>());
        // 创建部门树结果。
        Set<String> deptIds = new LinkedHashSet<>();
        // 加入根部门。
        deptIds.add(rootDeptId);
        // 循环扩展下级部门，直到没有新增部门。
        boolean changed;
        do {
            // 默认本轮没有变化。
            changed = false;
            // 遍历全部部门寻找已命中部门的直接子级。
            for (AuthDept dept : depts) {
                // 父级已命中且当前部门未命中时加入结果。
                if (deptIds.contains(dept.getParentId()) && deptIds.add(dept.getId())) {
                    changed = true;
                }
            }
        } while (changed);
        // 返回部门树ID集合。
        return deptIds.stream().toList();
    }

    /**
     * 数据范围快照。
     *
     * @param dataScope 数据范围
     * @param deptIds   部门ID集合
     * @author sunkailun
     * @DateTime 2026/05/27
     * @email 376253703@qq.com
     */
    private record DataScopeSnapshot(String dataScope, List<String> deptIds) {
    }
}
