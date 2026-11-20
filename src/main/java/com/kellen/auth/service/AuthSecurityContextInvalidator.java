package com.kellen.auth.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.kellen.auth.entity.AuthRoleResource;
import com.kellen.auth.entity.AuthUser;
import com.kellen.auth.entity.AuthUserRole;
import com.kellen.auth.entity.AuthUserTenant;
import com.kellen.auth.entity.enums.AuthAdminTypeEnum;
import com.kellen.auth.mapper.AuthRoleResourceMapper;
import com.kellen.auth.mapper.AuthUserMapper;
import com.kellen.auth.mapper.AuthUserRoleMapper;
import com.kellen.auth.mapper.AuthUserTenantMapper;
import com.kellen.security.oauth2.SecurityUserCache;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

/**
 * 用户、角色、权限资源、部门或租户关系改动后，让受影响用户的业务上下文缓存换版本（utils 的 {@link SecurityUserCache}）：
 * 本实例立刻失效，其他服务实例在本地缓存过期后（最多 30 秒）读到新上下文。没有接入资源服务器时没有缓存，不做任何事。
 *
 * <p>受影响的用户在调用时按当前数据算出；有事务时等提交后才换版本，回滚不换。提交前换的话，并发请求可能读到还没提交的旧数据、
 * 又按新版本写进缓存，旧上下文会一直留到缓存过期（Spring 的 {@code TransactionAwareCacheDecorator} 同样在提交后才失效缓存）。</p>
 *
 * @author 孙凯伦
 */
@Component
public class AuthSecurityContextInvalidator {

    private final ObjectProvider<SecurityUserCache> cache;

    private final AuthUserMapper authUserMapper;

    private final AuthUserRoleMapper authUserRoleMapper;

    private final AuthRoleResourceMapper authRoleResourceMapper;

    private final AuthUserTenantMapper authUserTenantMapper;

    /**
     * 构造失效通知。
     *
     * @param cache                  业务上下文缓存（可无）
     * @param authUserMapper         用户 Mapper
     * @param authUserRoleMapper     用户角色 Mapper
     * @param authRoleResourceMapper 角色资源 Mapper
     * @param authUserTenantMapper   用户租户关系 Mapper
     */
    public AuthSecurityContextInvalidator(ObjectProvider<SecurityUserCache> cache, AuthUserMapper authUserMapper, AuthUserRoleMapper authUserRoleMapper,
                                          AuthRoleResourceMapper authRoleResourceMapper, AuthUserTenantMapper authUserTenantMapper) {
        this.cache = cache;
        this.authUserMapper = authUserMapper;
        this.authUserRoleMapper = authUserRoleMapper;
        this.authRoleResourceMapper = authRoleResourceMapper;
        this.authUserTenantMapper = authUserTenantMapper;
    }

    /**
     * 单个用户改动。
     *
     * @param userId 用户 ID
     */
    public void user(String userId) {
        users(userId == null ? List.of() : List.of(userId));
    }

    /**
     * 多个用户改动。
     *
     * @param userIds 用户 ID
     */
    public void users(Collection<String> userIds) {
        SecurityUserCache current = cache.getIfAvailable();
        if (current == null) {
            return;
        }
        List<String> ids = userIds.stream().filter(StringUtils::isNotBlank).distinct().toList();
        if (ids.isEmpty()) {
            return;
        }
        if (TransactionSynchronizationManager.isSynchronizationActive()) {
            TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
                @Override
                public void afterCommit() {
                    ids.forEach(current::bumpVersion);
                }
            });
            return;
        }
        ids.forEach(current::bumpVersion);
    }

    /**
     * 角色改动（数据范围、状态、资源或数据范围部门绑定）：角色下的全部用户。
     *
     * @param roleIds 角色 ID
     */
    public void roleMembers(Collection<String> roleIds) {
        Set<String> ids = new LinkedHashSet<>();
        roleIds.stream().filter(StringUtils::isNotBlank).forEach(ids::add);
        if (ids.isEmpty()) {
            return;
        }
        users(AuthQueryScope.crossTenant(() -> authUserRoleMapper.selectList(new LambdaQueryWrapper<AuthUserRole>().in(AuthUserRole::getRoleId, ids))
                .stream().map(AuthUserRole::getUserId).toList()));
    }

    /**
     * 权限资源改动（编码、分类、状态）：绑定了该资源的角色下的全部用户。
     *
     * @param resourceIds 资源 ID
     */
    public void resourceMembers(Collection<String> resourceIds) {
        Set<String> ids = new LinkedHashSet<>();
        resourceIds.stream().filter(StringUtils::isNotBlank).forEach(ids::add);
        if (ids.isEmpty()) {
            return;
        }
        roleMembers(AuthQueryScope.crossTenant(() -> authRoleResourceMapper.selectList(new LambdaQueryWrapper<AuthRoleResource>()
                .in(AuthRoleResource::getResourceId, ids)).stream().map(AuthRoleResource::getRoleId).toList()));
    }

    /**
     * 租户或部门树改动：归属该租户与关联了该租户的全部用户，以及可选择任一启用租户的平台超级管理员。
     *
     * @param tenantId 租户 ID
     */
    public void tenantMembers(String tenantId) {
        if (StringUtils.isBlank(tenantId)) {
            return;
        }
        Set<String> userIds = new LinkedHashSet<>();
        Consumer<List<String>> collect = userIds::addAll;
        collect.accept(AuthQueryScope.crossTenant(() -> authUserMapper.selectList(new LambdaQueryWrapper<AuthUser>().eq(AuthUser::getTenantId, tenantId))
                .stream().map(AuthUser::getId).toList()));
        collect.accept(AuthQueryScope.crossTenant(() -> authUserTenantMapper.selectList(new LambdaQueryWrapper<AuthUserTenant>()
                .eq(AuthUserTenant::getRelationTenantId, tenantId)).stream().map(AuthUserTenant::getUserId).toList()));
        collect.accept(platformAdminIds());
        users(userIds);
    }

    /**
     * 新建租户：平台超级管理员可选的启用租户多了一个。
     */
    public void platformAdmins() {
        users(platformAdminIds());
    }

    private List<String> platformAdminIds() {
        return AuthQueryScope.crossTenant(() -> authUserMapper.selectList(new LambdaQueryWrapper<AuthUser>()
                .eq(AuthUser::getAdminType, AuthAdminTypeEnum.PLATFORM_SUPER_ADMIN)).stream().map(AuthUser::getId).toList());
    }
}
