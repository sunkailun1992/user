package com.kellen.auth.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.kellen.auth.entity.AuthResource;
import com.kellen.auth.entity.AuthRoleResource;
import com.kellen.auth.entity.AuthUserRole;
import com.kellen.auth.entity.bo.AuthRoleResourceBO;
import com.kellen.auth.entity.bo.AuthUserRoleBO;
import com.kellen.auth.entity.enums.AuthResourceCategoryEnum;
import com.kellen.auth.entity.vo.AuthResourceVO;
import com.kellen.auth.mapper.AuthResourceMapper;
import com.kellen.auth.mapper.AuthRoleResourceMapper;
import com.kellen.auth.mapper.AuthUserRoleMapper;
import com.kellen.auth.service.AuthGrantService;
import com.kellen.auth.service.results.AuthResourceServiceResults;
import com.kellen.utils.context.TenantContextHolder;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 授权关系业务服务实现。
 *
 * @author sunkailun
 * @className AuthGrantServiceImpl
 * @time 2026/05/26
 */
@Service
public class AuthGrantServiceImpl implements AuthGrantService {

    /**
     * 后端资源分类。
     */
    private static final AuthResourceCategoryEnum BACKEND_RESOURCE = AuthResourceCategoryEnum.BACKEND;

    /**
     * 用户角色Mapper。
     */
    private final AuthUserRoleMapper authUserRoleMapper;

    /**
     * 角色资源Mapper。
     */
    private final AuthRoleResourceMapper authRoleResourceMapper;

    /**
     * 资源Mapper。
     */
    private final AuthResourceMapper authResourceMapper;

    /**
     * 资源结果转换增强。
     */
    private final AuthResourceServiceResults authResourceServiceResults;

    /**
     * 构造授权关系业务服务。
     *
     * @param authUserRoleMapper         用户角色Mapper
     * @param authRoleResourceMapper     角色资源Mapper
     * @param authResourceMapper         资源Mapper
     * @param authResourceServiceResults 资源结果转换增强
     */
    public AuthGrantServiceImpl(AuthUserRoleMapper authUserRoleMapper,
                                AuthRoleResourceMapper authRoleResourceMapper,
                                AuthResourceMapper authResourceMapper,
                                AuthResourceServiceResults authResourceServiceResults) {
        // 保存用户角色Mapper。
        this.authUserRoleMapper = authUserRoleMapper;
        // 保存角色资源Mapper。
        this.authRoleResourceMapper = authRoleResourceMapper;
        // 保存资源Mapper。
        this.authResourceMapper = authResourceMapper;
        // 保存资源结果转换增强。
        this.authResourceServiceResults = authResourceServiceResults;
    }

    /**
     * 绑定用户角色。
     *
     * @param bo 用户角色授权参数
     * @return 是否成功
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean bindUserRole(AuthUserRoleBO bo) {
        // 调用内部绑定方法。
        bindUserRole(bo.getTenantId(), bo.getUserId(), bo.getRoleId());
        // 返回成功。
        return true;
    }

    /**
     * 绑定角色资源。
     *
     * @param bo 角色资源授权参数
     * @return 是否成功
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean bindRoleResource(AuthRoleResourceBO bo) {
        // 调用内部绑定方法。
        bindRoleResource(bo.getTenantId(), bo.getRoleId(), bo.getResourceId());
        // 返回成功。
        return true;
    }

    /**
     * 查询用户拥有的资源。
     *
     * @param userId 用户ID
     * @return 资源列表
     */
    @Override
    public List<AuthResource> findResourcesByUserId(String userId) {
        // 查询用户角色关系。
        List<AuthUserRole> userRoles = authUserRoleMapper.selectList(new LambdaQueryWrapper<AuthUserRole>().eq(AuthUserRole::getUserId, userId));
        // 提取角色ID集合。
        Set<String> roleIds = userRoles.stream().map(AuthUserRole::getRoleId).collect(Collectors.toSet());
        // 无角色时返回空资源。
        if (roleIds.isEmpty()) {
            // 返回空列表。
            return List.of();
        }
        // 查询角色资源关系。
        List<AuthRoleResource> roleResources = authRoleResourceMapper.selectList(new LambdaQueryWrapper<AuthRoleResource>().in(AuthRoleResource::getRoleId, roleIds));
        // 提取资源ID集合。
        Set<String> resourceIds = roleResources.stream().map(AuthRoleResource::getResourceId).collect(Collectors.toSet());
        // 无资源时返回空列表。
        if (resourceIds.isEmpty()) {
            // 返回空列表。
            return List.of();
        }
        // 查询资源详情。
        return authResourceMapper.selectList(new LambdaQueryWrapper<AuthResource>().in(AuthResource::getId, resourceIds).orderByAsc(AuthResource::getSorting));
    }

    /**
     * 提取后端权限码。
     *
     * @param resources 资源列表
     * @return 权限码列表
     */
    @Override
    public List<String> toPermissionCodes(List<AuthResource> resources) {
        // 过滤后端资源并提取非空权限码。
        return resources.stream().filter(resource -> BACKEND_RESOURCE == resource.getResourceCategory()).map(AuthResource::getCode).filter(StringUtils::isNotBlank).distinct().toList();
    }

    /**
     * 转换指定分类的资源展示结果。
     *
     * @param resources 资源列表
     * @param category  资源分类
     * @return 资源展示结果
     */
    @Override
    public List<AuthResourceVO> toResourceViews(List<AuthResource> resources, AuthResourceCategoryEnum category) {
        // 按分类过滤并转换为资源响应对象。
        return resources.stream().filter(resource -> category == resource.getResourceCategory()).sorted(Comparator.comparing(resource -> resource.getSorting() == null ? 0 : resource.getSorting())).map(authResourceServiceResults::toVO).toList();
    }

    /**
     * 绑定用户角色。
     *
     * @param tenantId 租户ID
     * @param userId   用户ID
     * @param roleId   角色ID
     */
    public void bindUserRole(String tenantId, String userId, String roleId) {
        try {
            // 设置目标租户上下文。
            TenantContextHolder.setTenantId(tenantId);
            // 查询关系是否已存在。
            AuthUserRole exists = authUserRoleMapper.selectOne(new LambdaQueryWrapper<AuthUserRole>().eq(AuthUserRole::getUserId, userId).eq(AuthUserRole::getRoleId, roleId).last("LIMIT 1"));
            // 已存在则不重复插入。
            if (exists != null) {
                // 直接返回。
                return;
            }
            // 创建用户角色关系。
            AuthUserRole userRole = new AuthUserRole();
            // 设置用户ID。
            userRole.setUserId(userId);
            // 设置角色ID。
            userRole.setRoleId(roleId);
            // 设置关系编码。
            userRole.setCode(userId + ":" + roleId);
            // 插入关系。
            authUserRoleMapper.insert(userRole);
        } finally {
            // 清理租户上下文。
            TenantContextHolder.clear();
        }
    }

    /**
     * 绑定角色资源。
     *
     * @param tenantId   租户ID
     * @param roleId     角色ID
     * @param resourceId 资源ID
     */
    public void bindRoleResource(String tenantId, String roleId, String resourceId) {
        try {
            // 设置目标租户上下文。
            TenantContextHolder.setTenantId(tenantId);
            // 查询关系是否已存在。
            AuthRoleResource exists = authRoleResourceMapper.selectOne(new LambdaQueryWrapper<AuthRoleResource>().eq(AuthRoleResource::getRoleId, roleId).eq(AuthRoleResource::getResourceId, resourceId).last("LIMIT 1"));
            // 已存在则不重复插入。
            if (exists != null) {
                // 直接返回。
                return;
            }
            // 创建角色资源关系。
            AuthRoleResource roleResource = new AuthRoleResource();
            // 设置角色ID。
            roleResource.setRoleId(roleId);
            // 设置资源ID。
            roleResource.setResourceId(resourceId);
            // 设置关系编码。
            roleResource.setCode(roleId + ":" + resourceId);
            // 插入关系。
            authRoleResourceMapper.insert(roleResource);
        } finally {
            // 清理租户上下文。
            TenantContextHolder.clear();
        }
    }
}
