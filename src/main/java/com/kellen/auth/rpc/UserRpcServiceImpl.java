package com.kellen.auth.rpc;

import com.kellen.auth.entity.AuthUser;
import com.kellen.auth.entity.enums.AuthAdminTypeEnum;
import com.kellen.auth.entity.enums.AuthStateEnum;
import com.kellen.auth.mapper.AuthUserMapper;
import com.kellen.rpc.user.UserRpcDTO;
import com.kellen.rpc.user.UserRpcService;
import com.kellen.utils.context.TenantContextHolder;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboService;

/**
 * 用户中心 Dubbo RPC 服务实现。
 */
@DubboService
public class UserRpcServiceImpl implements UserRpcService {

    /**
     * 用户 Mapper。
     */
    private final AuthUserMapper authUserMapper;

    /**
     * 构造用户 RPC 服务。
     *
     * @param authUserMapper 用户 Mapper
     */
    public UserRpcServiceImpl(AuthUserMapper authUserMapper) {
        this.authUserMapper = authUserMapper;
    }

    /**
     * 按租户和用户 ID 查询用户详情。
     *
     * @param tenantId 租户 ID
     * @param userId   用户 ID
     * @return 用户详情；不存在时返回 null
     */
    @Override
    public UserRpcDTO getUserById(String tenantId, String userId) {
        if (StringUtils.isAnyBlank(tenantId, userId)) {
            return null;
        }
        try {
            TenantContextHolder.setTenantId(tenantId);
            AuthUser user = authUserMapper.selectById(userId);
            return toRpcDTO(user);
        } finally {
            TenantContextHolder.clear();
        }
    }

    /**
     * 转换为 RPC DTO，避免把密码等内部实体字段暴露给调用方。
     *
     * @param user 用户实体
     * @return RPC DTO
     */
    private UserRpcDTO toRpcDTO(AuthUser user) {
        if (user == null) {
            return null;
        }
        UserRpcDTO dto = new UserRpcDTO();
        dto.setId(user.getId());
        dto.setTenantId(user.getTenantId());
        dto.setUsername(user.getUsername());
        dto.setNickname(user.getNickname());
        dto.setDeptId(user.getDeptId());
        dto.setVersion(user.getVersion());
        if (user.getAdminType() != null) {
            dto.setAdminType(user.getAdminType().getValue());
            dto.setAdminTypeDesc(AuthAdminTypeEnum.getDesc(user.getAdminType().getValue()));
        }
        if (user.getState() != null) {
            dto.setState(user.getState().getValue());
            dto.setStateDesc(AuthStateEnum.getDesc(user.getState().getValue()));
        }
        return dto;
    }
}
