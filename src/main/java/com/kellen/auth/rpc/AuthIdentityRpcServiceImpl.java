package com.kellen.auth.rpc;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.kellen.auth.entity.AuthExternalIdentity;
import com.kellen.auth.entity.enums.AuthStateEnum;
import com.kellen.auth.mapper.AuthExternalIdentityMapper;
import com.kellen.datapermission.DataPermissionContextHolder;
import com.kellen.rpc.auth.AuthIdentityRpcService;
import com.kellen.rpc.auth.ExternalIdentityRpcDTO;
import com.kellen.rpc.auth.ExternalIdentityRpcRequest;
import com.kellen.utils.context.TenantContextHolder;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboService;

/**
 * 统一认证身份 Dubbo RPC 服务实现。
 */
@DubboService
public class AuthIdentityRpcServiceImpl implements AuthIdentityRpcService {

    private final AuthExternalIdentityMapper authExternalIdentityMapper;

    public AuthIdentityRpcServiceImpl(AuthExternalIdentityMapper authExternalIdentityMapper) {
        this.authExternalIdentityMapper = authExternalIdentityMapper;
    }

    @Override
    public ExternalIdentityRpcDTO resolveExternalIdentity(ExternalIdentityRpcRequest request) {
        if (request == null || StringUtils.isBlank(request.getProviderCode())
                || StringUtils.isAllBlank(request.getExternalUserId(), request.getExternalPatientId())) {
            return null;
        }
        try {
            TenantContextHolder.ignore();
            DataPermissionContextHolder.ignore();
            AuthExternalIdentity identity = authExternalIdentityMapper.selectOne(new LambdaQueryWrapper<AuthExternalIdentity>()
                    .eq(AuthExternalIdentity::getProviderCode, request.getProviderCode())
                    .eq(AuthExternalIdentity::getExternalTenantId, normalize(request.getExternalTenantId()))
                    .eq(AuthExternalIdentity::getExternalUserId, normalize(request.getExternalUserId()))
                    .eq(AuthExternalIdentity::getExternalPatientId, normalize(request.getExternalPatientId()))
                    .eq(AuthExternalIdentity::getState, AuthStateEnum.启用)
                    .last("LIMIT 1"));
            return toRpcDTO(identity);
        } finally {
            TenantContextHolder.clearIgnore();
            DataPermissionContextHolder.clear();
        }
    }

    private ExternalIdentityRpcDTO toRpcDTO(AuthExternalIdentity identity) {
        if (identity == null) {
            return null;
        }
        ExternalIdentityRpcDTO dto = new ExternalIdentityRpcDTO();
        dto.setProviderCode(identity.getProviderCode());
        dto.setExternalTenantId(identity.getExternalTenantId());
        dto.setExternalUserId(identity.getExternalUserId());
        dto.setExternalPatientId(identity.getExternalPatientId());
        dto.setLocalTenantId(identity.getLocalTenantId());
        dto.setLocalUserId(identity.getLocalUserId());
        dto.setSubjectType(identity.getSubjectType());
        dto.setDisplayName(identity.getDisplayName());
        dto.setState(identity.getState() == null ? null : identity.getState().getValue());
        return dto;
    }

    private String normalize(String value) {
        return StringUtils.defaultString(value);
    }
}
