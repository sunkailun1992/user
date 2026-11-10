package com.kellen.auth.rpc;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.kellen.auth.entity.AuthUserTenant;
import com.kellen.auth.entity.enums.AuthAdminTypeEnum;
import com.kellen.auth.entity.enums.AuthStateEnum;
import com.kellen.auth.mapper.AuthTenantMapper;
import com.kellen.auth.mapper.AuthUserMapper;
import com.kellen.auth.mapper.AuthUserTenantMapper;
import com.kellen.auth.service.AuthTokenLifecycleService;
import com.kellen.datapermission.DataPermissionContextHolder;
import com.kellen.rpc.auth.AuthSessionIdentityRpcDTO;
import com.kellen.rpc.auth.AuthSessionRpcService;
import com.kellen.utils.context.TenantContextHolder;
import com.kellen.utils.enumeration.ReturnCode;
import com.kellen.utils.exception.UserException;
import org.apache.dubbo.config.annotation.DubboService;

/** 认证 bootstrap RPC：先验证 token，再以精确主键复核账号及租户授权，不依赖调用方租户。 */
@DubboService
public class AuthSessionRpcServiceImpl implements AuthSessionRpcService {
    private final AuthTokenLifecycleService tokens;
    private final AuthUserMapper users;
    private final AuthTenantMapper tenants;
    private final AuthUserTenantMapper memberships;

    public AuthSessionRpcServiceImpl(AuthTokenLifecycleService tokens, AuthUserMapper users,
                                    AuthTenantMapper tenants, AuthUserTenantMapper memberships) {
        this.tokens = tokens; this.users = users; this.tenants = tenants; this.memberships = memberships;
    }

    @Override
    public AuthSessionIdentityRpcDTO resolveAccessIdentity(String accessToken) {
        var identity = tokens.requireActiveAccessToken(accessToken);
        boolean oldTenantIgnore = TenantContextHolder.isIgnore();
        boolean oldDataIgnore = DataPermissionContextHolder.isIgnore();
        try {
            // 认证前没有可信业务上下文；忽略仅覆盖这些主键/授权关系查询，绝不用于业务数据。
            TenantContextHolder.ignore();
            DataPermissionContextHolder.ignore();
            var user = users.selectById(identity.userId());
            var tenant = tenants.selectById(identity.tenantId());
            if (user == null || tenant == null || user.getState() != AuthStateEnum.启用
                    || tenant.getState() != AuthStateEnum.启用
                    || !(identity.tenantId().equals(user.getTenantId())
                    || user.getAdminType() == AuthAdminTypeEnum.PLATFORM_SUPER_ADMIN
                    || memberships.selectCount(new LambdaQueryWrapper<AuthUserTenant>()
                    .eq(AuthUserTenant::getUserId, identity.userId())
                    .eq(AuthUserTenant::getRelationTenantId, identity.tenantId())
                    .eq(AuthUserTenant::getState, AuthStateEnum.启用)) > 0)) {
                throw new UserException(ReturnCode.用户身份校验失败, "访问身份验证失败");
            }
            AuthSessionIdentityRpcDTO result = new AuthSessionIdentityRpcDTO();
            result.setUserId(identity.userId()); result.setTenantId(identity.tenantId());
            return result;
        } finally {
            if (oldTenantIgnore) TenantContextHolder.ignore(); else TenantContextHolder.clearIgnore();
            if (oldDataIgnore) DataPermissionContextHolder.ignore(); else DataPermissionContextHolder.clear();
        }
    }
}
