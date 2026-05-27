package com.kellen.auth.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kellen.auth.entity.AuthRole;
import com.kellen.auth.entity.bo.AuthRoleBO;
import com.kellen.auth.entity.enums.AuthStateEnum;
import com.kellen.auth.entity.query.AuthRoleQuery;
import com.kellen.auth.entity.vo.AuthRoleVO;
import com.kellen.auth.mapper.AuthRoleMapper;
import com.kellen.auth.service.AuthRoleService;
import com.kellen.auth.service.query.AuthRoleServiceQuery;
import com.kellen.auth.service.results.AuthRoleServiceResults;
import com.kellen.utils.GeneralConvertor;
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
     * 角色查询增强。
     */
    private final AuthRoleServiceQuery authRoleServiceQuery;

    /**
     * 角色结果增强。
     */
    private final AuthRoleServiceResults authRoleServiceResults;

    /**
     * 构造角色业务服务。
     *
     * @param authRoleMapper         角色Mapper
     * @param authRoleServiceQuery   角色查询增强
     * @param authRoleServiceResults 角色结果增强
     */
    public AuthRoleServiceImpl(AuthRoleMapper authRoleMapper,
                               AuthRoleServiceQuery authRoleServiceQuery,
                               AuthRoleServiceResults authRoleServiceResults) {
        // 保存角色Mapper。
        this.authRoleMapper = authRoleMapper;
        // 保存角色查询增强。
        this.authRoleServiceQuery = authRoleServiceQuery;
        // 保存角色结果增强。
        this.authRoleServiceResults = authRoleServiceResults;
    }

    /**
     * 分页查询角色。
     *
     * @param page  分页对象
     * @param query 角色查询参数
     * @return 角色分页
     */
    @Override
    public Page<AuthRoleVO> page(Page<AuthRole> page, AuthRoleQuery query) {
        try {
            // 设置目标租户上下文。
            TenantContextHolder.setTenantId(query.getTenantId());
            // 构建完整查询包装器。
            QueryWrapper<AuthRole> queryWrapper = buildQueryWrapper(query);
            // 执行分页查询。
            Page<AuthRole> pageDO = authRoleMapper.selectPage(page, queryWrapper);
            // 转换为响应分页。
            Page<AuthRoleVO> pageVO = authRoleServiceResults.toPageVO(pageDO);
            // 根据查询参数决定是否执行结果增强。
            return needAssignment(query) ? authRoleServiceResults.assignment(pageVO) : pageVO;
        } finally {
            // 清理租户上下文。
            TenantContextHolder.clear();
        }
    }

    /**
     * 查询角色列表。
     *
     * @param query 角色查询参数
     * @return 角色列表
     */
    @Override
    public List<AuthRoleVO> list(AuthRoleQuery query) {
        try {
            // 设置目标租户上下文。
            TenantContextHolder.setTenantId(query.getTenantId());
            // 构建完整查询包装器。
            QueryWrapper<AuthRole> queryWrapper = buildQueryWrapper(query);
            // 查询角色实体列表。
            List<AuthRole> records = authRoleMapper.selectList(queryWrapper);
            // 转换为响应列表。
            List<AuthRoleVO> voRecords = authRoleServiceResults.toListVO(records);
            // 根据查询参数决定是否执行结果增强。
            return needAssignment(query) ? authRoleServiceResults.assignment(voRecords) : voRecords;
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
            // 将 BO 转换为实体。
            AuthRole role = GeneralConvertor.convertor(bo, AuthRole.class);
            // 清理空白ID，交给MyBatis-Plus生成。
            role.setId(StringUtils.trimToNull(bo.getId()));
            // 设置默认启用状态。
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
        try {
            // 设置目标租户上下文，避免更新依赖请求头隐式租户。
            TenantContextHolder.setTenantId(bo.getTenantId());
            // 将 BO 转换为实体，保留 version 触发乐观锁。
            AuthRole role = GeneralConvertor.convertor(bo, AuthRole.class);
            // 租户条件由租户插件处理，更新实体不主动写 tenant_id。
            role.setTenantId(null);
            // 使用updateById执行乐观锁更新。
            return authRoleMapper.updateById(role) > 0;
        } finally {
            // 清理租户上下文。
            TenantContextHolder.clear();
        }
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
        try {
            // 设置目标租户上下文，避免删除依赖请求头隐式租户。
            TenantContextHolder.setTenantId(bo.getTenantId());
            // 按ID逻辑删除角色。
            return authRoleMapper.deleteById(bo.getId()) > 0;
        } finally {
            // 清理租户上下文。
            TenantContextHolder.clear();
        }
    }

    /**
     * 构建角色查询包装器。
     *
     * @param query 角色查询参数
     * @return 查询包装器
     * @author sunkailun
     * @DateTime 2026/05/27
     * @email 376253703@qq.com
     */
    private QueryWrapper<AuthRole> buildQueryWrapper(AuthRoleQuery query) {
        // 将查询参数转换为实体，用于 QueryWrapper 自动拼接同名字段等值条件。
        AuthRole entity = GeneralConvertor.convertor(query, AuthRole.class);
        // 租户条件由 TenantContextHolder 和 MyBatis-Plus 租户插件处理，避免 QueryWrapper 重复拼 tenant_id。
        if (entity != null) {
            // 清理转换进实体的租户ID。
            entity.setTenantId(null);
        }
        // 创建查询包装器。
        QueryWrapper<AuthRole> queryWrapper = entity == null ? new QueryWrapper<>() : new QueryWrapper<>(entity);
        // 拼接自动查询条件。
        authRoleServiceQuery.query(query, queryWrapper);
        // 拼接人工查询条件。
        queryArtificial(query, queryWrapper);
        // 返回完整查询包装器。
        return queryWrapper;
    }

    /**
     * 拼接角色人工查询条件。
     *
     * @param query        角色查询参数
     * @param queryWrapper 查询包装器
     * @return 查询包装器
     */
    private QueryWrapper<AuthRole> queryArtificial(AuthRoleQuery query, QueryWrapper<AuthRole> queryWrapper) {
        // 查询对象为空或关键字为空时直接返回原包装器。
        if (query == null || StringUtils.isBlank(query.getQuery())) {
            // 返回调用方传入的包装器。
            return queryWrapper;
        }
        // 通用关键字匹配角色编码或名称。
        queryWrapper.and(wrapper -> wrapper.like("code", query.getQuery()).or().like("name", query.getQuery()));
        // 返回完整查询包装器。
        return queryWrapper;
    }

    /**
     * 判断是否需要结果增强。
     *
     * @param query 角色查询参数
     * @return boolean
     */
    private boolean needAssignment(AuthRoleQuery query) {
        // 查询对象为空时默认执行结果增强。
        if (query == null) {
            // 返回需要增强。
            return true;
        }
        // assignment 明确传 false 时跳过结果增强，其余情况默认增强。
        return !Boolean.FALSE.equals(query.getAssignment());
    }
}
