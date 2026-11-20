package com.kellen.auth.rpc;

import com.kellen.auth.entity.AuthResource;
import com.kellen.auth.entity.AuthRole;
import com.kellen.auth.entity.AuthUser;
import com.kellen.auth.entity.enums.AuthStateEnum;
import com.kellen.auth.mapper.AuthUserMapper;
import com.kellen.auth.service.AuthDataScopeResolver;
import com.kellen.auth.service.AuthGrantService;
import com.kellen.auth.service.AuthQueryScope;
import com.kellen.auth.service.AuthTenantAccess;
import com.kellen.security.SecurityUser;
import com.kellen.security.oauth2.SecurityContextResult;
import com.kellen.security.oauth2.SecurityContextRpcService;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.context.annotation.Primary;

import java.util.ArrayList;
import java.util.List;

/**
 * 按请求加载认证横切的业务上下文（各服务的资源服务器经 Dubbo 调用，user 自己直接用本地实现）。
 *
 * <p>活动租户：请求头指定且在用户可访问的启用租户里时用它，否则用户的归属租户；部门取该租户关系里登记的部门；
 * 权限码、角色与数据权限按该租户下的角色计算；平台超级管理员可选择任一启用租户。权限集合是后端权限码、
 * {@code ROLE_<角色编码>} 与 {@code ROLE_<管理员分类>}。</p>
 *
 * <p>标 {@link Primary}：本服务里同时有 utils 自动生成的 Dubbo 引用（{@code @DubboService} 由 Dubbo 扫描注册，自动配置的条件看不到它），
 * 资源服务器优先用本地实现。调用方的租户与数据权限上下文原样恢复。</p>
 *
 * @author 孙凯伦
 */
@Primary
@DubboService
public class SecurityContextRpcServiceImpl implements SecurityContextRpcService {

    private final AuthUserMapper authUserMapper;

    private final AuthTenantAccess tenantAccess;

    private final AuthGrantService authGrantService;

    private final AuthDataScopeResolver dataScopeResolver;

    /**
     * 构造业务上下文提供方。
     *
     * @param authUserMapper    用户 Mapper
     * @param tenantAccess      租户访问判断
     * @param authGrantService  授权服务（资源与权限码）
     * @param dataScopeResolver 数据权限范围计算
     */
    public SecurityContextRpcServiceImpl(AuthUserMapper authUserMapper, AuthTenantAccess tenantAccess, AuthGrantService authGrantService,
                                         AuthDataScopeResolver dataScopeResolver) {
        this.authUserMapper = authUserMapper;
        this.tenantAccess = tenantAccess;
        this.authGrantService = authGrantService;
        this.dataScopeResolver = dataScopeResolver;
    }

    @Override
    public SecurityContextResult resolve(String userId, String requestedTenantId) {
        if (StringUtils.isBlank(userId)) {
            return SecurityContextResult.of(SecurityContextResult.Status.USER_NOT_FOUND);
        }
        AuthUser user = AuthQueryScope.crossTenant(() -> authUserMapper.selectById(userId));
        if (user == null) {
            return SecurityContextResult.of(SecurityContextResult.Status.USER_NOT_FOUND);
        }
        if (user.getState() != AuthStateEnum.启用) {
            return SecurityContextResult.of(SecurityContextResult.Status.DISABLED);
        }
        List<String> tenantIds = tenantAccess.accessibleTenantIds(user);
        String tenantId = StringUtils.isBlank(requestedTenantId) ? user.getTenantId() : requestedTenantId.trim();
        if (tenantId == null || !tenantIds.contains(tenantId)) {
            return SecurityContextResult.of(SecurityContextResult.Status.TENANT_DENIED);
        }
        String deptId = tenantAccess.deptIdIn(user, tenantId);
        AuthDataScopeResolver.DataScope dataScope = dataScopeResolver.resolve(tenantId, userId, deptId);
        List<String> authorities = AuthQueryScope.inTenant(tenantId, () -> authorities(user));
        return SecurityContextResult.ok(SecurityUser.builder()
                .userId(user.getId())
                .username(user.getUsername())
                .tenantId(tenantId)
                .deptId(deptId)
                .dataScope(dataScope.dataScope())
                .dataScopeDeptIds(dataScope.deptIds())
                .authorities(authorities)
                .enabled(true)
                .tenantIds(tenantIds)
                .build());
    }

    private List<String> authorities(AuthUser user) {
        List<AuthResource> resources = authGrantService.findResourcesByUserId(user.getId());
        List<String> authorities = new ArrayList<>(authGrantService.toPermissionCodes(resources));
        for (AuthRole role : dataScopeResolver.rolesOf(user.getId())) {
            if (StringUtils.isNotBlank(role.getCode())) {
                authorities.add("ROLE_" + role.getCode());
            }
        }
        authorities.add("ROLE_" + tenantAccess.adminType(user).getValue());
        return authorities.stream().distinct().toList();
    }
}
