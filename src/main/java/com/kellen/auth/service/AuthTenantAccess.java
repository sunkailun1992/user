package com.kellen.auth.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.kellen.auth.entity.AuthTenant;
import com.kellen.auth.entity.AuthUser;
import com.kellen.auth.entity.AuthUserTenant;
import com.kellen.auth.entity.enums.AuthAdminTypeEnum;
import com.kellen.auth.entity.enums.AuthStateEnum;
import com.kellen.auth.entity.vo.AuthTenantVO;
import com.kellen.auth.mapper.AuthTenantMapper;
import com.kellen.auth.mapper.AuthUserTenantMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * 用户可访问的租户：平台超级管理员可访问全部启用租户；其他用户只能访问归属租户与启用的租户关系里的启用租户。
 * 跨租户读取认证配置（全局主数据），调用方的租户与数据权限上下文原样恢复。
 *
 * @author 孙凯伦
 */
@Component
public class AuthTenantAccess {

    private final AuthTenantMapper authTenantMapper;

    private final AuthUserTenantMapper authUserTenantMapper;

    /**
     * 构造租户访问判断。
     *
     * @param authTenantMapper     租户 Mapper
     * @param authUserTenantMapper 用户租户关系 Mapper
     */
    public AuthTenantAccess(AuthTenantMapper authTenantMapper, AuthUserTenantMapper authUserTenantMapper) {
        this.authTenantMapper = authTenantMapper;
        this.authUserTenantMapper = authUserTenantMapper;
    }

    /**
     * 管理员分类，空值按租户管理员。
     *
     * @param user 用户
     * @return 管理员分类
     */
    public AuthAdminTypeEnum adminType(AuthUser user) {
        return user.getAdminType() == null ? AuthAdminTypeEnum.TENANT_ADMIN : user.getAdminType();
    }

    /**
     * 用户可访问的启用租户，按排序号与 ID 排序。
     *
     * @param user 用户
     * @return 租户
     */
    public List<AuthTenant> accessibleTenants(AuthUser user) {
        if (user == null || StringUtils.isBlank(user.getId())) {
            return List.of();
        }
        return AuthQueryScope.crossTenant(() -> {
            LambdaQueryWrapper<AuthTenant> query = new LambdaQueryWrapper<AuthTenant>().eq(AuthTenant::getState, AuthStateEnum.启用)
                    .orderByAsc(AuthTenant::getSorting).orderByAsc(AuthTenant::getId);
            if (AuthAdminTypeEnum.PLATFORM_SUPER_ADMIN == adminType(user)) {
                return authTenantMapper.selectList(query);
            }
            Set<String> tenantIds = new LinkedHashSet<>();
            if (StringUtils.isNotBlank(user.getTenantId())) {
                tenantIds.add(user.getTenantId());
            }
            authUserTenantMapper.selectList(new LambdaQueryWrapper<AuthUserTenant>().eq(AuthUserTenant::getUserId, user.getId())
                            .eq(AuthUserTenant::getState, AuthStateEnum.启用))
                    .stream().map(AuthUserTenant::getRelationTenantId).filter(StringUtils::isNotBlank).forEach(tenantIds::add);
            return tenantIds.isEmpty() ? List.of() : authTenantMapper.selectList(query.in(AuthTenant::getId, tenantIds));
        });
    }

    /**
     * 用户可访问的启用租户 ID。
     *
     * @param user 用户
     * @return 租户 ID
     */
    public List<String> accessibleTenantIds(AuthUser user) {
        return accessibleTenants(user).stream().map(AuthTenant::getId).toList();
    }

    /**
     * 用户可切换的租户（前端展示）。
     *
     * @param user 用户
     * @return 租户展示对象
     */
    public List<AuthTenantVO> availableTenants(AuthUser user) {
        return accessibleTenants(user).stream().map(AuthTenantAccess::toTenantVO).toList();
    }

    /**
     * 用户在指定租户下的部门：租户关系里登记的部门优先，归属租户没有登记时取账号上的部门。
     *
     * @param user     用户
     * @param tenantId 租户 ID
     * @return 部门 ID，可能为空
     */
    public String deptIdIn(AuthUser user, String tenantId) {
        String relationDept = AuthQueryScope.crossTenant(() -> authUserTenantMapper.selectList(new LambdaQueryWrapper<AuthUserTenant>()
                        .eq(AuthUserTenant::getUserId, user.getId()).eq(AuthUserTenant::getRelationTenantId, tenantId)
                        .eq(AuthUserTenant::getState, AuthStateEnum.启用))
                .stream().map(AuthUserTenant::getDeptId).filter(StringUtils::isNotBlank).findFirst().orElse(null));
        if (relationDept != null) {
            return relationDept;
        }
        return tenantId.equals(user.getTenantId()) ? user.getDeptId() : null;
    }

    private static AuthTenantVO toTenantVO(AuthTenant tenant) {
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
}
