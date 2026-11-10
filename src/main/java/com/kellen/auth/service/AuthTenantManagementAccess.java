package com.kellen.auth.service;

import com.kellen.auth.rpc.AuthSessionRpcServiceImpl;
import com.kellen.utils.auth.JwtUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.Collection;

/** 授权关系 HTTP 管理面统一访问边界；内部 bootstrap 绑定不依赖请求 JWT。 */
@Service
public class AuthTenantManagementAccess {
    private final AuthSessionRpcServiceImpl identities;

    public AuthTenantManagementAccess(AuthSessionRpcServiceImpl identities) {
        this.identities = identities;
    }

    /** 本租户管理保持；跨租户回显还必须由活跃的平台管理员发起，不能信任客户端租户或权限头。 */
    public void requireTenant(String tenantId, String authorization) {
        try {
            if (StringUtils.isBlank(tenantId) || authorization == null || !authorization.startsWith("Bearer ")) {
                throw new AccessDeniedException("角色资源访问被拒绝");
            }
            String token = authorization.substring(7);
            var identity = identities.resolveAccessIdentity(token);
            var claims = JwtUtils.parseJwt(token);
            if (identity == null || !identity.getUserId().equals(claims.get("userId"))
                    || !identity.getTenantId().equals(claims.get("tenantId"))
                    || !(claims.get("permissions") instanceof Collection<?> permissions)
                    || !permissions.contains("user:auth:manage")
                    || (!tenantId.equals(identity.getTenantId())
                    && !"PLATFORM_SUPER_ADMIN".equals(claims.get("adminType")))) {
                throw new AccessDeniedException("角色资源访问被拒绝");
            }
        } catch (RuntimeException rejected) {
            throw new AccessDeniedException("角色资源访问被拒绝");
        }
    }

}
