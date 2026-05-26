package com.kellen.auth.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.kellen.auth.entity.AuthTenant;
import com.kellen.auth.entity.bo.AuthTenantBO;
import com.kellen.auth.entity.enums.AuthStateEnum;
import com.kellen.auth.mapper.AuthTenantMapper;
import com.kellen.auth.service.AuthTenantService;
import com.kellen.utils.TenantContextHolder;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

/**
 * 租户业务服务实现。
 *
 * @author sunkailun
 * @className AuthTenantServiceImpl
 * @time 2026/05/26
 */
@Service
public class AuthTenantServiceImpl implements AuthTenantService {

    /**
     * 租户Mapper。
     */
    private final AuthTenantMapper authTenantMapper;

    /**
     * 构造租户业务服务。
     *
     * @param authTenantMapper 租户Mapper
     */
    public AuthTenantServiceImpl(AuthTenantMapper authTenantMapper) {
        // 保存租户Mapper。
        this.authTenantMapper = authTenantMapper;
    }

    /**
     * 查询租户列表。
     *
     * @return 租户列表
     */
    @Override
    public List<AuthTenant> list() {
        try {
            // 租户是全局主数据，查询时忽略租户插件。
            TenantContextHolder.ignore();
            // 按排序查询租户列表。
            return authTenantMapper.selectList(new LambdaQueryWrapper<AuthTenant>().orderByAsc(AuthTenant::getSorting));
        } finally {
            // 清理租户忽略标记。
            TenantContextHolder.clearIgnore();
        }
    }

    /**
     * 新增租户。
     *
     * @param bo 租户写入参数
     * @return 租户ID
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public String save(AuthTenantBO bo) {
        try {
            // 租户是全局主数据，写入时忽略租户插件。
            TenantContextHolder.ignore();
            // 先按ID查询租户是否存在，避免重复插入同一个租户。
            AuthTenant exists = StringUtils.isBlank(bo.getId()) ? null : authTenantMapper.selectById(bo.getId());
            // 已存在则直接返回租户ID。
            if (exists != null) {
                // 返回已存在租户ID。
                return exists.getId();
            }
            // 创建租户实体。
            AuthTenant tenant = new AuthTenant();
            // 设置租户ID，未传时生成UUID。
            tenant.setId(StringUtils.defaultIfBlank(bo.getId(), UUID.randomUUID().toString()));
            // 租户自身tenantId使用自身ID。
            tenant.setTenantId(tenant.getId());
            // 设置租户编码。
            tenant.setCode(bo.getCode());
            // 设置租户名称。
            tenant.setName(bo.getName());
            // 设置租户状态。
            tenant.setState(bo.getState() == null ? AuthStateEnum.启用 : bo.getState());
            // 插入租户。
            authTenantMapper.insert(tenant);
            // 返回租户ID。
            return tenant.getId();
        } finally {
            // 清理租户上下文。
            TenantContextHolder.clear();
        }
    }

    /**
     * 修改租户。
     *
     * @param bo 租户写入参数
     * @return 是否成功
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean update(AuthTenantBO bo) {
        try {
            // 租户是全局主数据，更新时忽略租户插件。
            TenantContextHolder.ignore();
            // 创建租户更新实体。
            AuthTenant tenant = new AuthTenant();
            // 设置租户ID。
            tenant.setId(bo.getId());
            // 设置旧版本号，触发MyBatis-Plus乐观锁。
            tenant.setVersion(bo.getVersion());
            // 设置租户编码。
            tenant.setCode(bo.getCode());
            // 设置租户名称。
            tenant.setName(bo.getName());
            // 设置租户状态。
            tenant.setState(bo.getState());
            // 使用updateById执行乐观锁更新。
            return authTenantMapper.updateById(tenant) > 0;
        } finally {
            // 清理租户上下文。
            TenantContextHolder.clear();
        }
    }

    /**
     * 删除租户。
     *
     * @param bo 租户删除参数
     * @return 是否成功
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean remove(AuthTenantBO bo) {
        try {
            // 租户是全局主数据，删除时忽略租户插件。
            TenantContextHolder.ignore();
            // 按ID逻辑删除租户。
            return authTenantMapper.deleteById(bo.getId()) > 0;
        } finally {
            // 清理租户上下文。
            TenantContextHolder.clear();
        }
    }
}
