package com.kellen.auth.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.kellen.auth.entity.AuthRole;
import com.kellen.auth.entity.bo.AuthRoleBO;
import com.kellen.auth.entity.enums.AuthStateEnum;
import com.kellen.auth.mapper.AuthRoleMapper;
import com.kellen.auth.service.AuthRoleService;
import com.kellen.utils.TenantContextHolder;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 角色业务服务实现。
 *
 * @author sunkailun
 * @className AuthRoleServiceImpl
 * @time 2026/05/26
 */
@Service
public class AuthRoleServiceImpl implements AuthRoleService {

    /**
     * 角色Mapper。
     */
    private final AuthRoleMapper authRoleMapper;

    /**
     * 构造角色业务服务。
     *
     * @param authRoleMapper 角色Mapper
     */
    public AuthRoleServiceImpl(AuthRoleMapper authRoleMapper) {
        // 保存角色Mapper。
        this.authRoleMapper = authRoleMapper;
    }

    /**
     * 查询角色列表。
     *
     * @param tenantId 租户ID
     * @return 角色列表
     */
    @Override
    public List<AuthRole> list(String tenantId) {
        try {
            // 设置目标租户上下文。
            TenantContextHolder.setTenantId(tenantId);
            // 查询当前租户角色列表。
            return authRoleMapper.selectList(new LambdaQueryWrapper<AuthRole>().orderByAsc(AuthRole::getSorting));
        } finally {
            // 清理租户上下文。
            TenantContextHolder.clear();
        }
    }

    /**
     * 新增角色。
     *
     * @param bo 角色写入参数
     * @return 角色ID
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public String save(AuthRoleBO bo) {
        try {
            // 设置目标租户上下文。
            TenantContextHolder.setTenantId(bo.getTenantId());
            // 查询同租户同编码角色是否已存在，避免重复插入同一个角色。
            AuthRole exists = authRoleMapper.selectOne(new LambdaQueryWrapper<AuthRole>().eq(AuthRole::getCode, bo.getCode()).last("LIMIT 1"));
            // 已存在则直接返回角色ID。
            if (exists != null) {
                // 返回已存在角色ID。
                return exists.getId();
            }
            // 创建角色实体。
            AuthRole role = new AuthRole();
            // 设置角色ID，初始化场景可以传入固定ID。
            role.setId(StringUtils.trimToNull(bo.getId()));
            // 设置角色编码。
            role.setCode(bo.getCode());
            // 设置角色名称。
            role.setName(bo.getName());
            // 设置角色状态。
            role.setState(bo.getState() == null ? AuthStateEnum.启用 : bo.getState());
            // 插入角色。
            authRoleMapper.insert(role);
            // 返回角色ID。
            return role.getId();
        } finally {
            // 清理租户上下文。
            TenantContextHolder.clear();
        }
    }

    /**
     * 修改角色。
     *
     * @param bo 角色写入参数
     * @return 是否成功
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean update(AuthRoleBO bo) {
        // 创建角色更新实体。
        AuthRole role = new AuthRole();
        // 设置角色ID。
        role.setId(bo.getId());
        // 设置旧版本号，触发MyBatis-Plus乐观锁。
        role.setVersion(bo.getVersion());
        // 设置角色编码。
        role.setCode(bo.getCode());
        // 设置角色名称。
        role.setName(bo.getName());
        // 设置角色状态。
        role.setState(bo.getState());
        // 使用updateById执行乐观锁更新。
        return authRoleMapper.updateById(role) > 0;
    }

    /**
     * 删除角色。
     *
     * @param bo 角色删除参数
     * @return 是否成功
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean remove(AuthRoleBO bo) {
        // 按ID逻辑删除角色。
        return authRoleMapper.deleteById(bo.getId()) > 0;
    }
}
