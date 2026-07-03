package com.kellen.auth.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.kellen.auth.dto.LoginRequest;
import com.kellen.auth.dto.LogoutSessionRequest;
import com.kellen.auth.dto.OAuthAuthorizeUser;
import com.kellen.auth.dto.RefreshSessionRequest;
import com.kellen.auth.entity.AuthDept;
import com.kellen.auth.entity.AuthResource;
import com.kellen.auth.entity.AuthRole;
import com.kellen.auth.entity.AuthRoleDataScope;
import com.kellen.auth.entity.AuthTenant;
import com.kellen.auth.entity.AuthUser;
import com.kellen.auth.entity.AuthUserRole;
import com.kellen.auth.entity.AuthUserTenant;
import com.kellen.auth.entity.enums.AuthAdminTypeEnum;
import com.kellen.auth.entity.enums.AuthDataScopeEnum;
import com.kellen.auth.entity.enums.AuthResourceCategoryEnum;
import com.kellen.auth.entity.enums.AuthStateEnum;
import com.kellen.auth.entity.vo.AuthCurrentResourceVO;
import com.kellen.auth.entity.vo.AuthLoginVO;
import com.kellen.auth.entity.vo.AuthTenantVO;
import com.kellen.auth.mapper.AuthDeptMapper;
import com.kellen.auth.mapper.AuthRoleDataScopeMapper;
import com.kellen.auth.mapper.AuthRoleMapper;
import com.kellen.auth.mapper.AuthTenantMapper;
import com.kellen.auth.mapper.AuthUserMapper;
import com.kellen.auth.mapper.AuthUserRoleMapper;
import com.kellen.auth.mapper.AuthUserTenantMapper;
import com.kellen.auth.service.AuthAuthenticationService;
import com.kellen.auth.service.AuthGrantService;
import com.kellen.auth.service.AuthTokenLifecycleService;
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
     * 用户租户关联Mapper。
     */
    private final AuthUserTenantMapper authUserTenantMapper;

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
     * token 生命周期服务。
     */
    private final AuthTokenLifecycleService authTokenLifecycleService;

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
     * @param authUserTenantMapper     用户租户关联Mapper
     * @param authRoleMapper           角色Mapper
     * @param authDeptMapper           部门Mapper
     * @param authRoleDataScopeMapper  角色自定义数据范围Mapper
     * @param authGrantService         授权关系业务服务
     * @param authTokenLifecycleService token 生命周期服务
     */
    public AuthAuthenticationServiceImpl(AuthTenantMapper authTenantMapper,
                                         AuthUserMapper authUserMapper,
                                         AuthUserRoleMapper authUserRoleMapper,
                                         AuthUserTenantMapper authUserTenantMapper,
                                         AuthRoleMapper authRoleMapper,
                                         AuthDeptMapper authDeptMapper,
                                         AuthRoleDataScopeMapper authRoleDataScopeMapper,
                                         AuthGrantService authGrantService,
                                         AuthTokenLifecycleService authTokenLifecycleService) {
        // 保存租户Mapper。
        this.authTenantMapper = authTenantMapper;
        // 保存用户Mapper。
        this.authUserMapper = authUserMapper;
        // 保存用户角色Mapper。
        this.authUserRoleMapper = authUserRoleMapper;
        // 保存用户租户关联Mapper。
        this.authUserTenantMapper = authUserTenantMapper;
        // 保存角色Mapper。
        this.authRoleMapper = authRoleMapper;
        // 保存部门Mapper。
        this.authDeptMapper = authDeptMapper;
        // 保存角色自定义数据范围Mapper。
        this.authRoleDataScopeMapper = authRoleDataScopeMapper;
        // 保存授权关系业务服务。
        this.authGrantService = authGrantService;
        // 保存 token 生命周期服务。
        this.authTokenLifecycleService = authTokenLifecycleService;
    }

    /**
     * 刷新登录会话。
     *
     * @param request 刷新请求
     * @return 登录响应
     */
    @Override
    public AuthLoginVO refreshSession(RefreshSessionRequest request) {
        AuthTokenLifecycleService.RefreshSession refreshSession = authTokenLifecycleService.consumeRefreshToken(request == null ? null : request.getRefreshToken());
        try {
            TenantContextHolder.setTenantId(refreshSession.tenantId());
            AuthUser user = findCurrentUser(refreshSession.userId());
            if (user == null) {
                throw new UserException(ReturnCode.用户身份校验失败, "用户不存在");
            }
            if (AuthStateEnum.禁用 == user.getState()) {
                authTokenLifecycleService.revokeUserTokens(user.getId());
                throw new UserException(ReturnCode.用户账户被冻结, "用户已禁用");
            }
            if (!canAccessTenant(user, refreshSession.tenantId())) {
                throw new UserException(ReturnCode.用户身份校验失败, "用户无当前租户登录权限");
            }
            return buildLoginResponse(user, refreshSession.tenantId(), "REFRESH", null);
        } finally {
            TenantContextHolder.clear();
        }
    }

    /**
     * 退出当前登录会话。
     *
     * @param authorization Authorization请求头
     * @param request       退出请求
     */
    @Override
    public void logout(String authorization, LogoutSessionRequest request) {
        authTokenLifecycleService.revokeAccessToken(authorization);
        if (request != null) {
            authTokenLifecycleService.revokeRefreshToken(request.getRefreshToken());
        }
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
            // 查询并校验当前登录用户。
            AuthUser user = findLoginUser(request.getUsername(), request.getPassword(), tenantId);
            // 校验用户和密码。
            if (user == null) {
                // 登录失败时不暴露账号是否存在。
                throw new UserException(ReturnCode.用户密码错误, "用户名或密码错误");
            }
            // 校验用户状态。
            if (AuthStateEnum.禁用 == user.getState()) {
                // 禁用账号不允许登录。
                throw new UserException(ReturnCode.用户账户被冻结, "用户已禁用");
            }
            // 复用统一会话签发逻辑。
            return buildLoginResponse(user, tenantId, "LOCAL", null);
        } finally {
            // 清理租户上下文。
            TenantContextHolder.clear();
        }
    }

    /**
     * OAuth 授权码流程校验资源所有者。
     *
     * @param request 登录请求参数
     * @return 授权用户快照
     */
    @Override
    public OAuthAuthorizeUser authenticateForOAuth(LoginRequest request) {
        if (StringUtils.isBlank(request.getUsername())) {
            throw new UserException(ReturnCode.请求必填参数为空, "用户名不能为空");
        }
        if (StringUtils.isBlank(request.getPassword())) {
            throw new UserException(ReturnCode.请求必填参数为空, "密码不能为空");
        }
        String tenantId = resolveTenantId(request);
        try {
            TenantContextHolder.setTenantId(tenantId);
            AuthUser user = findLoginUser(request.getUsername(), request.getPassword(), tenantId);
            if (user == null) {
                throw new UserException(ReturnCode.用户密码错误, "用户名或密码错误");
            }
            if (AuthStateEnum.禁用 == user.getState()) {
                throw new UserException(ReturnCode.用户账户被冻结, "用户已禁用");
            }
            if (!canAccessTenant(user, tenantId)) {
                throw new UserException(ReturnCode.用户身份校验失败, "用户无当前租户登录权限");
            }
            return new OAuthAuthorizeUser(user.getId(), user.getUsername(), tenantId);
        } finally {
            TenantContextHolder.clear();
        }
    }

    /**
     * 基于已完成外部身份校验的本地用户创建会话。
     *
     * @param tenantId      本地租户 ID
     * @param userId        本地用户 ID
     * @param loginProvider 登录来源
     * @param subjectType   主体类型
     * @return 登录响应
     */
    @Override
    public AuthLoginVO createSessionForUser(String tenantId, String userId, String loginProvider, String subjectType) {
        if (StringUtils.isAnyBlank(tenantId, userId)) {
            throw new UserException(ReturnCode.用户请求参数错误, "本地租户和用户不能为空");
        }
        try {
            TenantContextHolder.setTenantId(tenantId);
            AuthUser user = findCurrentUser(userId);
            if (user == null) {
                throw new UserException(ReturnCode.用户身份校验失败, "本地用户不存在");
            }
            if (AuthStateEnum.禁用 == user.getState()) {
                throw new UserException(ReturnCode.用户账户被冻结, "用户已禁用");
            }
            if (!canAccessTenant(user, tenantId)) {
                throw new UserException(ReturnCode.用户身份校验失败, "用户无当前租户登录权限");
            }
            return buildLoginResponse(user, tenantId, loginProvider, subjectType);
        } finally {
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
            // 查询当前用户实体。
            AuthUser user = findCurrentUser(currentUser.getUserId());
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
            // 设置管理员分类。
            vo.setAdminType(user == null ? null : resolveAdminType(user).getValue());
            // 设置当前用户可切换租户。
            vo.setAvailableTenants(findAvailableTenants(user));
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
     * 查询当前登录用户可切换租户。
     *
     * @return 当前登录用户可切换租户
     */
    @Override
    public List<AuthTenantVO> currentTenants() {
        // 获取当前登录用户。
        SecurityUser currentUser = UserContextHolder.get();
        // 校验用户是否登录。
        if (currentUser == null) {
            // 未登录时返回身份校验失败。
            throw new UserException(ReturnCode.用户身份校验失败, "用户未登录");
        }
        try {
            // 设置当前租户上下文。
            TenantContextHolder.setTenantId(currentUser.getTenantId());
            // 按当前登录用户查询可切换租户。
            return findAvailableTenants(findCurrentUser(currentUser.getUserId()));
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
     * 组装统一登录响应并签发 JWT。
     *
     * @param user          本地用户
     * @param tenantId      登录租户 ID
     * @param loginProvider 登录来源
     * @param subjectType   主体类型
     * @return 登录响应
     */
    private AuthLoginVO buildLoginResponse(AuthUser user, String tenantId, String loginProvider, String subjectType) {
        List<AuthResource> resources = authGrantService.findResourcesByUserId(user.getId());
        List<String> permissions = authGrantService.toPermissionCodes(resources);
        DataScopeSnapshot dataScopeSnapshot = resolveDataScope(user);
        AuthAdminTypeEnum adminType = resolveAdminType(user);
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", user.getId());
        claims.put("username", user.getUsername());
        claims.put("tenantId", tenantId);
        claims.put("deptId", user.getDeptId());
        claims.put("dataScope", dataScopeSnapshot.dataScope());
        claims.put("dataScopeDeptIds", dataScopeSnapshot.deptIds());
        claims.put("adminType", adminType.getValue());
        claims.put("permissions", permissions);
        claims.put("loginProvider", StringUtils.defaultIfBlank(loginProvider, "LOCAL"));
        if (StringUtils.isNotBlank(subjectType)) {
            claims.put("subjectType", subjectType);
        }
        AuthTokenLifecycleService.TokenPair tokenPair = authTokenLifecycleService.issueTokens(user.getId(), tenantId, loginProvider, subjectType, claims);
        AuthLoginVO vo = new AuthLoginVO();
        vo.setToken(tokenPair.accessToken());
        vo.setRefreshToken(tokenPair.refreshToken());
        vo.setExpiresIn(tokenPair.expiresIn());
        vo.setRefreshExpiresIn(tokenPair.refreshExpiresIn());
        vo.setTokenType("Bearer");
        vo.setUserId(user.getId());
        vo.setUsername(user.getUsername());
        vo.setNickname(user.getNickname());
        vo.setTenantId(tenantId);
        vo.setDeptId(user.getDeptId());
        vo.setDataScope(dataScopeSnapshot.dataScope());
        vo.setDataScopeDeptIds(dataScopeSnapshot.deptIds());
        vo.setAdminType(adminType.getValue());
        vo.setAvailableTenants(findAvailableTenants(user));
        vo.setPermissions(permissions);
        vo.setFrontendResources(authGrantService.toResourceViews(resources, FRONTEND_RESOURCE));
        vo.setBackendResources(authGrantService.toResourceViews(resources, BACKEND_RESOURCE));
        vo.setLoginProvider(StringUtils.defaultIfBlank(loginProvider, "LOCAL"));
        vo.setSubjectType(subjectType);
        return vo;
    }

    /**
     * 解析当前用户合并后的数据范围。
     *
     * @param user 当前登录用户
     * @return 数据范围快照
     * @author sunkailun
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
     * 查询当前用户可切换租户列表。
     *
     * @param user 当前用户
     * @return 可切换租户列表
     * @author sunkailun
     */
    private List<AuthTenantVO> findAvailableTenants(AuthUser user) {
        if (user == null || StringUtils.isBlank(user.getId())) {
            // 用户为空时不能推导租户关联。
            return List.of();
        }
        try {
            // 跨租户读取认证配置，用于计算当前用户可切换租户。
            TenantContextHolder.ignore();
            // 认证基础表查询不应被业务数据权限过滤。
            DataPermissionContextHolder.ignore();
            // 平台超级管理员可切换全部启用租户。
            if (AuthAdminTypeEnum.PLATFORM_SUPER_ADMIN == resolveAdminType(user)) {
                // 查询全部启用租户详情。
                return authTenantMapper.selectList(new LambdaQueryWrapper<AuthTenant>()
                                .eq(AuthTenant::getState, AuthStateEnum.启用)
                                .orderByAsc(AuthTenant::getSorting))
                        .stream()
                        .map(this::toTenantVO)
                        .toList();
            }
            // 查询显式绑定的启用租户ID。
            Set<String> tenantIds = authUserTenantMapper.selectList(new LambdaQueryWrapper<AuthUserTenant>()
                            .eq(AuthUserTenant::getUserId, user.getId())
                            .eq(AuthUserTenant::getState, AuthStateEnum.启用))
                    .stream()
                    .map(AuthUserTenant::getRelationTenantId)
                    .filter(StringUtils::isNotBlank)
                    .collect(Collectors.toCollection(LinkedHashSet::new));
            if (StringUtils.isNotBlank(user.getTenantId())) {
                // 历史账号默认租户兜底纳入可切换租户。
                tenantIds.add(user.getTenantId());
            }
            if (tenantIds.isEmpty()) {
                // 没有关联租户时返回空租户列表。
                return List.of();
            }
            // 查询启用租户详情。
            return authTenantMapper.selectList(new LambdaQueryWrapper<AuthTenant>()
                            .in(AuthTenant::getId, tenantIds)
                            .eq(AuthTenant::getState, AuthStateEnum.启用)
                            .orderByAsc(AuthTenant::getSorting))
                    .stream()
                    .map(this::toTenantVO)
                    .toList();
        } finally {
            // 清理租户忽略标记。
            TenantContextHolder.clearIgnore();
            // 清理数据权限忽略标记。
            DataPermissionContextHolder.clear();
        }
    }

    /**
     * 查询登录账号。
     *
     * @param username 用户名
     * @param password 明文密码
     * @param tenantId 登录租户ID
     * @return 登录用户
     */
    private AuthUser findLoginUser(String username, String password, String tenantId) {
        try {
            // 登录账号归属可能通过 auth_user_tenant 跨租户授权，先跨租户读取同名启用账号。
            TenantContextHolder.ignore();
            // 登录认证不应被数据权限过滤。
            DataPermissionContextHolder.ignore();
            // 查询同名启用账号。
            List<AuthUser> users = authUserMapper.selectList(new LambdaQueryWrapper<AuthUser>()
                    .eq(AuthUser::getUsername, username)
                    .eq(AuthUser::getState, AuthStateEnum.启用));
            // 先校验账号密码，避免把租户权限错误误判为密码错误。
            List<AuthUser> passwordMatchedUsers = users.stream()
                    .sorted((left, right) -> Boolean.compare(!tenantId.equals(left.getTenantId()), !tenantId.equals(right.getTenantId())))
                    .filter(user -> passwordEncoder.matches(password, user.getPassword()))
                    .toList();
            if (passwordMatchedUsers.isEmpty()) {
                // 没有密码匹配账号时按用户名或密码错误处理。
                return null;
            }
            // 再校验目标租户访问权限。
            AuthUser accessUser = passwordMatchedUsers.stream()
                    .filter(user -> canAccessTenant(user, tenantId))
                    .findFirst()
                    .orElse(null);
            if (accessUser == null) {
                // 账号密码正确但没有目标租户权限时给出明确提示。
                throw new UserException(ReturnCode.用户身份校验失败, "用户无当前租户登录权限");
            }
            // 返回可登录用户。
            return accessUser;
        } finally {
            // 清理忽略标记。
            TenantContextHolder.clearIgnore();
            DataPermissionContextHolder.clear();
        }
    }

    /**
     * 查询当前登录用户实体。
     *
     * @param userId 用户ID
     * @return 用户实体
     */
    private AuthUser findCurrentUser(String userId) {
        if (StringUtils.isBlank(userId)) {
            // 用户ID为空时返回空。
            return null;
        }
        return authUserMapper.selectById(userId);
    }

    /**
     * 判断用户是否可访问目标租户。
     *
     * @param user     用户实体
     * @param tenantId 目标租户ID
     * @return 是否可访问
     */
    private boolean canAccessTenant(AuthUser user, String tenantId) {
        if (user == null || StringUtils.isBlank(tenantId)) {
            // 缺少必要参数时不可访问。
            return false;
        }
        if (AuthAdminTypeEnum.PLATFORM_SUPER_ADMIN == resolveAdminType(user)) {
            // 平台超级管理员允许访问全部租户。
            return true;
        }
        if (tenantId.equals(user.getTenantId())) {
            // 用户默认租户允许访问。
            return true;
        }
        try {
            // 用户租户关系是跨租户认证配置，校验时忽略租户和数据权限过滤。
            TenantContextHolder.ignore();
            DataPermissionContextHolder.ignore();
            // 查询用户租户关联。
            return authUserTenantMapper.selectCount(new LambdaQueryWrapper<AuthUserTenant>()
                    .eq(AuthUserTenant::getUserId, user.getId())
                    .eq(AuthUserTenant::getRelationTenantId, tenantId)
                    .eq(AuthUserTenant::getState, AuthStateEnum.启用)) > 0;
        } finally {
            // 清理忽略标记。
            TenantContextHolder.clearIgnore();
            DataPermissionContextHolder.clear();
        }
    }

    /**
     * 解析用户管理员分类。
     *
     * @param user 用户实体
     * @return 管理员分类
     */
    private AuthAdminTypeEnum resolveAdminType(AuthUser user) {
        return user.getAdminType() == null ? AuthAdminTypeEnum.TENANT_ADMIN : user.getAdminType();
    }

    /**
     * 转换租户返回对象。
     *
     * @param tenant 租户实体
     * @return 租户返回对象
     * @author sunkailun
     */
    private AuthTenantVO toTenantVO(AuthTenant tenant) {
        AuthTenantVO vo = new AuthTenantVO();
        vo.setId(tenant.getId());
        vo.setCode(tenant.getCode());
        vo.setName(tenant.getName());
        vo.setState(tenant.getState());
        vo.setStateDesc(tenant.getState() == null ? null : tenant.getState().getDesc());
        vo.setSorting(tenant.getSorting());
        vo.setVersion(tenant.getVersion());
        return vo;
    }

    /**
     * 数据范围快照。
     *
     * @param dataScope 数据范围
     * @param deptIds   部门ID集合
     * @author sunkailun
     */
    private record DataScopeSnapshot(String dataScope, List<String> deptIds) {
    }
}
