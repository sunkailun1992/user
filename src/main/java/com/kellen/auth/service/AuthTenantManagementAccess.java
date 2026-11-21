package com.kellen.auth.service;

import com.kellen.auth.entity.query.AuthTenantQuery;
import com.kellen.security.SecurityUser;
import com.kellen.security.TenantBoundary;
import com.kellen.security.UserContextHolder;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

/**
 * 管理面（用户、角色、部门、资源、租户与授权关系接口）的统一访问边界：当前用户由资源服务器按 Keycloak 访问令牌与 user 的业务上下文写入，
 * 不读请求头里的任何身份、租户或权限。要求拥有 {@code user:auth:manage}；请求点名的租户只能是自己的活动租户，平台超级管理员
 * （{@code ROLE_PLATFORM_SUPER_ADMIN}）可以点名任一租户（规则在 utils {@link TenantBoundary}）。租户表本身是平台级数据，
 * 增删改只给平台超级管理员，其余用户只看得到自己的活动租户。
 *
 * <p>做法同芋道 {@code TenantSecurityWebFilter}（登录用户的租户与请求租户不一致即拒绝），依据 OWASP API Security Top 10 2023
 * 的 API1（对象级授权）与 API5（功能级授权）。</p>
 *
 * @author 孙凯伦
 */
@Service
public class AuthTenantManagementAccess {

    /**
     * 管理接口所需的权限码。
     */
    static final String MANAGE = "user:auth:manage";

    /**
     * 拒绝时的说明（客户端只看到统一的 403 报文）。
     */
    private static final String DENIED = "管理接口访问被拒绝";

    /**
     * 构造访问边界。
     */
    public AuthTenantManagementAccess() {
    }

    /**
     * 校验当前用户能否管理目标租户里的数据。
     *
     * @param tenantId 目标租户
     * @throws AccessDeniedException 没有管理权限、目标为空或越出自己的活动租户（平台超级管理员除外）时
     */
    public void requireTenant(String tenantId) {
        if (!hasManage() || !TenantBoundary.canAccess(tenantId)) {
            throw new AccessDeniedException(DENIED);
        }
    }

    /**
     * 校验当前用户是有管理权限的平台超级管理员（租户表的增删改）。
     *
     * @throws AccessDeniedException 不是时
     */
    public void requirePlatformSuperAdmin() {
        if (!hasManage() || !TenantBoundary.isPlatformSuperAdmin()) {
            throw new AccessDeniedException(DENIED);
        }
    }

    /**
     * 把租户查询限定在当前用户看得到的范围：平台超级管理员不限；其余没点名租户时只查自己的活动租户，点名的租户必须是它。
     *
     * @param query 租户查询参数（就地修改）
     * @throws AccessDeniedException 点名了别的租户，或没有活动租户时
     */
    public void scopeTenantQuery(AuthTenantQuery query) {
        if (TenantBoundary.isPlatformSuperAdmin()) {
            return;
        }
        if (StringUtils.isBlank(query.getId())) {
            SecurityUser user = UserContextHolder.get();
            if (user == null || StringUtils.isBlank(user.getTenantId())) {
                throw new AccessDeniedException(DENIED);
            }
            query.setId(user.getTenantId());
        } else {
            requireTenant(query.getId());
        }
    }

    /**
     * 当前用户能否管理该租户里的数据（自己的活动租户，或平台超级管理员）。
     *
     * @param tenantId 目标租户
     * @return 能管理时为 true
     */
    public boolean canManage(String tenantId) {
        return TenantBoundary.canAccess(tenantId);
    }

    /**
     * 当前用户是否平台超级管理员。
     *
     * @return 是时为 true
     */
    public boolean isPlatformSuperAdmin() {
        return TenantBoundary.isPlatformSuperAdmin();
    }

    private static boolean hasManage() {
        SecurityUser user = UserContextHolder.get();
        return user != null && user.getAuthorities() != null && user.getAuthorities().contains(MANAGE);
    }
}
