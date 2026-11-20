package com.kellen.auth.service.impl;

import com.kellen.auth.entity.AuthResource;
import com.kellen.auth.entity.AuthUser;
import com.kellen.auth.entity.enums.AuthResourceCategoryEnum;
import com.kellen.auth.entity.vo.AuthCurrentResourceVO;
import com.kellen.auth.entity.vo.AuthTenantVO;
import com.kellen.auth.mapper.AuthUserMapper;
import com.kellen.auth.service.AuthCurrentUserService;
import com.kellen.auth.service.AuthGrantService;
import com.kellen.auth.service.AuthQueryScope;
import com.kellen.auth.service.AuthTenantAccess;
import com.kellen.security.SecurityUser;
import com.kellen.security.UserContextHolder;
import com.kellen.utils.enumeration.ReturnCode;
import com.kellen.utils.exception.UserException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 当前登录用户的资源与可切换租户：按资源服务器写入的当前用户与活动租户查询。
 *
 * @author 孙凯伦
 */
@Service
public class AuthCurrentUserServiceImpl implements AuthCurrentUserService {

    private final AuthUserMapper authUserMapper;

    private final AuthGrantService authGrantService;

    private final AuthTenantAccess tenantAccess;

    /**
     * 构造当前用户服务。
     *
     * @param authUserMapper   用户 Mapper
     * @param authGrantService 授权服务
     * @param tenantAccess     租户访问判断
     */
    public AuthCurrentUserServiceImpl(AuthUserMapper authUserMapper, AuthGrantService authGrantService, AuthTenantAccess tenantAccess) {
        this.authUserMapper = authUserMapper;
        this.authGrantService = authGrantService;
        this.tenantAccess = tenantAccess;
    }

    @Override
    public AuthCurrentResourceVO currentResources() {
        SecurityUser current = currentUser();
        AuthUser user = AuthQueryScope.crossTenant(() -> authUserMapper.selectById(current.getUserId()));
        List<AuthResource> resources = AuthQueryScope.inTenant(current.getTenantId(), () -> authGrantService.findResourcesByUserId(current.getUserId()));
        AuthCurrentResourceVO vo = new AuthCurrentResourceVO();
        vo.setUserId(current.getUserId());
        vo.setUsername(user == null ? null : user.getUsername());
        vo.setNickname(user == null ? null : user.getNickname());
        vo.setTenantId(current.getTenantId());
        vo.setDeptId(current.getDeptId());
        vo.setAdminType(user == null ? null : tenantAccess.adminType(user).getValue());
        vo.setAvailableTenants(tenantAccess.availableTenants(user));
        vo.setPermissions(authGrantService.toPermissionCodes(resources));
        vo.setFrontendResources(authGrantService.toResourceViews(resources, AuthResourceCategoryEnum.FRONTEND));
        vo.setBackendResources(authGrantService.toResourceViews(resources, AuthResourceCategoryEnum.BACKEND));
        return vo;
    }

    @Override
    public List<AuthTenantVO> currentTenants() {
        SecurityUser current = currentUser();
        return tenantAccess.availableTenants(AuthQueryScope.crossTenant(() -> authUserMapper.selectById(current.getUserId())));
    }

    private static SecurityUser currentUser() {
        SecurityUser current = UserContextHolder.get();
        if (current == null) {
            throw new UserException(ReturnCode.USER_AUTHENTICATION_FAILED, "用户未登录");
        }
        return current;
    }
}
