package com.kellen.auth.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kellen.auth.entity.AuthTenant;
import com.kellen.auth.entity.bo.AuthTenantBO;
import com.kellen.auth.entity.enums.AuthStateEnum;
import com.kellen.auth.entity.query.AuthTenantQuery;
import com.kellen.auth.entity.vo.AuthTenantVO;
import com.kellen.auth.mapper.AuthTenantMapper;
import com.kellen.auth.service.AuthTenantService;
import com.kellen.auth.service.query.AuthTenantServiceQuery;
import com.kellen.auth.service.results.AuthTenantServiceResults;
import com.kellen.datapermission.DataPermissionContextHolder;
import com.kellen.utils.convert.GeneralConvertor;
import com.kellen.utils.context.TenantContextHolder;
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
     * 租户查询增强。
     */
    private final AuthTenantServiceQuery authTenantServiceQuery;

    /**
     * 租户结果增强。
     */
    private final AuthTenantServiceResults authTenantServiceResults;

    /**
     * 构造租户业务服务。
     *
     * @param authTenantMapper         租户Mapper
     * @param authTenantServiceQuery   租户查询增强
     * @param authTenantServiceResults 租户结果增强
     */
    public AuthTenantServiceImpl(AuthTenantMapper authTenantMapper,
                                 AuthTenantServiceQuery authTenantServiceQuery,
                                 AuthTenantServiceResults authTenantServiceResults) {
        // 保存租户Mapper。
        this.authTenantMapper = authTenantMapper;
        // 保存租户查询增强。
        this.authTenantServiceQuery = authTenantServiceQuery;
        // 保存租户结果增强。
        this.authTenantServiceResults = authTenantServiceResults;
    }

    /**
     * 分页查询租户。
     *
     * @param page  分页对象
     * @param query 租户查询参数
     * @return 租户分页
     */
    @Override
    public Page<AuthTenantVO> page(Page<AuthTenant> page, AuthTenantQuery query) {
        try {
            // 租户是全局主数据，查询时忽略租户插件。
            TenantContextHolder.ignore();
            // 租户下拉和租户管理不能被业务数据权限过滤。
            DataPermissionContextHolder.ignore();
            // 构建完整查询包装器。
            QueryWrapper<AuthTenant> queryWrapper = buildQueryWrapper(query);
            // 执行分页查询。
            Page<AuthTenant> pageDO = authTenantMapper.selectPage(page, queryWrapper);
            // 转换为响应分页。
            Page<AuthTenantVO> pageVO = authTenantServiceResults.toPageVO(pageDO);
            // 根据查询参数决定是否执行结果增强。
            return needAssignment(query) ? authTenantServiceResults.assignment(pageVO) : pageVO;
        } finally {
            // 清理租户忽略标记。
            TenantContextHolder.clearIgnore();
            // 清理数据权限忽略标记。
            DataPermissionContextHolder.clear();
        }
    }

    /**
     * 查询租户列表。
     *
     * @param query 租户查询参数
     * @return 租户列表
     */
    @Override
    public List<AuthTenantVO> list(AuthTenantQuery query) {
        try {
            // 租户是全局主数据，查询时忽略租户插件。
            TenantContextHolder.ignore();
            // 租户下拉和租户管理不能被业务数据权限过滤。
            DataPermissionContextHolder.ignore();
            // 构建完整查询包装器。
            QueryWrapper<AuthTenant> queryWrapper = buildQueryWrapper(query);
            // 查询租户实体列表。
            List<AuthTenant> records = authTenantMapper.selectList(queryWrapper);
            // 转换为响应列表。
            List<AuthTenantVO> voRecords = authTenantServiceResults.toListVO(records);
            // 根据查询参数决定是否执行结果增强。
            return needAssignment(query) ? authTenantServiceResults.assignment(voRecords) : voRecords;
        } finally {
            // 清理租户忽略标记。
            TenantContextHolder.clearIgnore();
            // 清理数据权限忽略标记。
            DataPermissionContextHolder.clear();
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
            // 将 BO 转换为实体。
            AuthTenant tenant = GeneralConvertor.convertor(bo, AuthTenant.class);
            // 设置租户ID，未传时生成UUID。
            tenant.setId(StringUtils.defaultIfBlank(bo.getId(), UUID.randomUUID().toString()));
            // 租户自身tenantId使用自身ID。
            tenant.setTenantId(tenant.getId());
            // 设置默认启用状态。
            tenant.setState(bo.getState() == null ? AuthStateEnum.启用 : bo.getState());
            // 插入租户。
            authTenantMapper.insert(tenant);
            // 返回租户ID。
            return tenant.getId();
        } finally {
            // 清理租户忽略标记。
            TenantContextHolder.clearIgnore();
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
            // 将 BO 转换为实体，保留 version 触发乐观锁。
            AuthTenant tenant = GeneralConvertor.convertor(bo, AuthTenant.class);
            // 使用updateById执行乐观锁更新。
            return authTenantMapper.updateById(tenant) > 0;
        } finally {
            // 清理租户忽略标记。
            TenantContextHolder.clearIgnore();
        }
    }

    /**
     * 删除租户。
     *
     * @param id 租户主键
     * @return 是否成功
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean remove(String id) {
        try {
            // 租户是全局主数据，删除时忽略租户插件。
            TenantContextHolder.ignore();
            // 按ID逻辑删除租户。
            return authTenantMapper.deleteById(id) > 0;
        } finally {
            // 清理租户忽略标记。
            TenantContextHolder.clearIgnore();
        }
    }

    /**
     * 构建租户查询包装器。
     *
     * @param query 租户查询参数
     * @return 查询包装器
     * @author sunkailun
     */
    private QueryWrapper<AuthTenant> buildQueryWrapper(AuthTenantQuery query) {
        // 将查询参数转换为实体，用于 QueryWrapper 自动拼接同名字段等值条件。
        AuthTenant entity = GeneralConvertor.convertor(query, AuthTenant.class);
        // 创建查询包装器。
        QueryWrapper<AuthTenant> queryWrapper = entity == null ? new QueryWrapper<>() : new QueryWrapper<>(entity);
        // 拼接自动查询条件。
        authTenantServiceQuery.query(query, queryWrapper);
        // 拼接人工查询条件。
        queryArtificial(query, queryWrapper);
        // 返回完整查询包装器。
        return queryWrapper;
    }

    /**
     * 拼接租户人工查询条件。
     *
     * @param query        租户查询参数
     * @param queryWrapper 查询包装器
     * @return 查询包装器
     */
    private QueryWrapper<AuthTenant> queryArtificial(AuthTenantQuery query, QueryWrapper<AuthTenant> queryWrapper) {
        // 查询对象为空时直接返回原包装器。
        if (query == null || StringUtils.isBlank(query.getQuery())) {
            // 返回调用方传入的包装器。
            return queryWrapper;
        }
        // 通用关键字匹配租户编码或名称。
        queryWrapper.and(wrapper -> wrapper.like("code", query.getQuery()).or().like("name", query.getQuery()));
        // 返回完整查询包装器。
        return queryWrapper;
    }

    /**
     * 判断是否需要结果增强。
     *
     * @param query 租户查询参数
     * @return boolean
     */
    private boolean needAssignment(AuthTenantQuery query) {
        // 查询对象为空时默认执行结果增强。
        if (query == null) {
            // 返回需要增强。
            return true;
        }
        // assignment 明确传 false 时跳过结果增强，其余情况默认增强。
        return !Boolean.FALSE.equals(query.getAssignment());
    }
}
