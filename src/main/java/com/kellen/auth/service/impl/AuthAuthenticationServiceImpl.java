package com.kellen.auth.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.kellen.auth.dto.LoginRequest;
import com.kellen.auth.entity.AuthResource;
import com.kellen.auth.entity.AuthTenant;
import com.kellen.auth.entity.AuthUser;
import com.kellen.auth.entity.enums.AuthResourceCategoryEnum;
import com.kellen.auth.entity.enums.AuthStateEnum;
import com.kellen.auth.entity.vo.AuthCurrentResourceVO;
import com.kellen.auth.entity.vo.AuthLoginVO;
import com.kellen.auth.mapper.AuthTenantMapper;
import com.kellen.auth.mapper.AuthUserMapper;
import com.kellen.auth.service.AuthAuthenticationService;
import com.kellen.auth.service.AuthGrantService;
import com.kellen.security.SecurityUser;
import com.kellen.security.UserContextHolder;
import com.kellen.utils.JwtUtils;
import com.kellen.utils.TenantContextHolder;
import com.kellen.utils.enumeration.ReturnCode;
import com.kellen.utils.exception.UserException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

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
     * @param authGrantService 授权关系业务服务
     */
    public AuthAuthenticationServiceImpl(AuthTenantMapper authTenantMapper,
                                         AuthUserMapper authUserMapper,
                                         AuthGrantService authGrantService) {
        // 保存租户Mapper。
        this.authTenantMapper = authTenantMapper;
        // 保存用户Mapper。
        this.authUserMapper = authUserMapper;
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
            // 创建JWT声明。
            Map<String, Object> claims = new HashMap<>();
            // 写入用户ID。
            claims.put("userId", user.getId());
            // 写入用户名。
            claims.put("username", user.getUsername());
            // 写入租户ID。
            claims.put("tenantId", tenantId);
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
        }
    }
}
