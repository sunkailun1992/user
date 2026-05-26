package com.kellen.auth.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.kellen.auth.entity.AuthResource;
import com.kellen.auth.entity.bo.AuthResourceBO;
import com.kellen.auth.entity.enums.AuthStateEnum;
import com.kellen.auth.mapper.AuthResourceMapper;
import com.kellen.auth.service.AuthResourceService;
import com.kellen.utils.TenantContextHolder;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 权限资源业务服务实现。
 *
 * @author sunkailun
 * @className AuthResourceServiceImpl
 * @time 2026/05/26
 */
@Service
public class AuthResourceServiceImpl implements AuthResourceService {

    /**
     * 资源Mapper。
     */
    private final AuthResourceMapper authResourceMapper;

    /**
     * 构造权限资源业务服务。
     *
     * @param authResourceMapper 资源Mapper
     */
    public AuthResourceServiceImpl(AuthResourceMapper authResourceMapper) {
        // 保存资源Mapper。
        this.authResourceMapper = authResourceMapper;
    }

    /**
     * 查询资源列表。
     *
     * @param tenantId 租户ID
     * @return 资源列表
     */
    @Override
    public List<AuthResource> list(String tenantId) {
        try {
            // 设置目标租户上下文。
            TenantContextHolder.setTenantId(tenantId);
            // 查询当前租户资源列表。
            return authResourceMapper.selectList(new LambdaQueryWrapper<AuthResource>().orderByAsc(AuthResource::getSorting));
        } finally {
            // 清理租户上下文。
            TenantContextHolder.clear();
        }
    }

    /**
     * 新增资源。
     *
     * @param bo 资源写入参数
     * @return 资源ID
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public String save(AuthResourceBO bo) {
        try {
            // 设置目标租户上下文。
            TenantContextHolder.setTenantId(bo.getTenantId());
            // 查询同租户同编码资源是否已存在，避免重复插入同一个资源。
            AuthResource exists = authResourceMapper.selectOne(new LambdaQueryWrapper<AuthResource>().eq(AuthResource::getCode, bo.getCode()).last("LIMIT 1"));
            // 已存在则直接返回资源ID。
            if (exists != null) {
                // 返回已存在资源ID。
                return exists.getId();
            }
            // 创建资源实体。
            AuthResource resource = new AuthResource();
            // 设置资源ID，初始化场景可以传入固定ID。
            resource.setId(StringUtils.trimToNull(bo.getId()));
            // 设置权限编码。
            resource.setCode(bo.getCode());
            // 设置资源名称。
            resource.setName(bo.getName());
            // 设置资源分类。
            resource.setResourceCategory(bo.getResourceCategory());
            // 设置资源路径。
            resource.setPath(bo.getPath());
            // 设置请求方法。
            resource.setMethod(bo.getMethod());
            // 设置父级资源ID。
            resource.setParentId(bo.getParentId());
            // 设置排序。
            resource.setSorting(bo.getSorting());
            // 设置资源状态。
            resource.setState(bo.getState() == null ? AuthStateEnum.启用 : bo.getState());
            // 插入资源。
            authResourceMapper.insert(resource);
            // 返回资源ID。
            return resource.getId();
        } finally {
            // 清理租户上下文。
            TenantContextHolder.clear();
        }
    }

    /**
     * 修改资源。
     *
     * @param bo 资源写入参数
     * @return 是否成功
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean update(AuthResourceBO bo) {
        // 创建资源更新实体。
        AuthResource resource = new AuthResource();
        // 设置资源ID。
        resource.setId(bo.getId());
        // 设置旧版本号，触发MyBatis-Plus乐观锁。
        resource.setVersion(bo.getVersion());
        // 设置权限编码。
        resource.setCode(bo.getCode());
        // 设置资源名称。
        resource.setName(bo.getName());
        // 设置资源分类。
        resource.setResourceCategory(bo.getResourceCategory());
        // 设置资源路径。
        resource.setPath(bo.getPath());
        // 设置请求方法。
        resource.setMethod(bo.getMethod());
        // 设置父级资源ID。
        resource.setParentId(bo.getParentId());
        // 设置排序。
        resource.setSorting(bo.getSorting());
        // 设置资源状态。
        resource.setState(bo.getState());
        // 使用updateById执行乐观锁更新。
        return authResourceMapper.updateById(resource) > 0;
    }

    /**
     * 删除资源。
     *
     * @param bo 资源删除参数
     * @return 是否成功
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean remove(AuthResourceBO bo) {
        // 按ID逻辑删除资源。
        return authResourceMapper.deleteById(bo.getId()) > 0;
    }
}
