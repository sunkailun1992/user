package com.kellen.auth.service;

import com.kellen.security.SecurityUser;
import com.kellen.security.UserContextHolder;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

/**
 * 授权关系 HTTP 管理面的统一访问边界：当前用户由资源服务器按 Keycloak 访问令牌与 user 的业务上下文写入，
 * 不读请求头里的任何身份、租户或权限。要求拥有 {@code user:auth:manage}，且目标租户是自己的活动租户，
 * 或自己是平台超级管理员（{@code ROLE_PLATFORM_SUPER_ADMIN}）。
 *
 * @author 孙凯伦
 */
@Service
public class AuthTenantManagementAccess {

    /**
     * 管理授权关系所需的权限码。
     */
    static final String MANAGE = "user:auth:manage";

    /**
     * 平台超级管理员的权限（管理员分类映射成的角色）。
     */
    static final String PLATFORM_SUPER_ADMIN = "ROLE_PLATFORM_SUPER_ADMIN";

    /**
     * 构造访问边界。
     */
    public AuthTenantManagementAccess() {
    }

    /**
     * 校验当前用户能否管理目标租户的授权关系。
     *
     * @param tenantId 目标租户
     * @throws AccessDeniedException 不能管理时
     */
    public void requireTenant(String tenantId) {
        SecurityUser user = UserContextHolder.get();
        if (StringUtils.isBlank(tenantId) || user == null || !user.getAuthorities().contains(MANAGE)) {
            throw new AccessDeniedException("角色资源访问被拒绝");
        }
        if (!tenantId.equals(user.getTenantId()) && !user.getAuthorities().contains(PLATFORM_SUPER_ADMIN)) {
            throw new AccessDeniedException("角色资源访问被拒绝");
        }
    }
}
