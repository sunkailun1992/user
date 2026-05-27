package com.kellen.auth.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kellen.auth.entity.AuthResource;
import com.kellen.auth.entity.bo.AuthResourceBO;
import com.kellen.auth.entity.enums.AuthStateEnum;
import com.kellen.auth.entity.query.AuthResourceQuery;
import com.kellen.auth.entity.vo.AuthResourceVO;
import com.kellen.auth.mapper.AuthResourceMapper;
import com.kellen.auth.service.AuthResourceService;
import com.kellen.auth.service.query.AuthResourceServiceQuery;
import com.kellen.auth.service.results.AuthResourceServiceResults;
import com.kellen.utils.GeneralConvertor;
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
     * 资源查询增强。
     */
    private final AuthResourceServiceQuery authResourceServiceQuery;

    /**
     * 资源结果增强。
     */
    private final AuthResourceServiceResults authResourceServiceResults;

    /**
     * 构造权限资源业务服务。
     *
     * @param authResourceMapper         资源Mapper
     * @param authResourceServiceQuery   资源查询增强
     * @param authResourceServiceResults 资源结果增强
     */
    public AuthResourceServiceImpl(AuthResourceMapper authResourceMapper,
                                   AuthResourceServiceQuery authResourceServiceQuery,
                                   AuthResourceServiceResults authResourceServiceResults) {
        // 保存资源Mapper。
        this.authResourceMapper = authResourceMapper;
        // 保存资源查询增强。
        this.authResourceServiceQuery = authResourceServiceQuery;
        // 保存资源结果增强。
        this.authResourceServiceResults = authResourceServiceResults;
    }

    /**
     * 分页查询资源。
     *
     * @param page  分页对象
     * @param query 资源查询参数
     * @return 资源分页
     */
    @Override
    public Page<AuthResourceVO> page(Page<AuthResource> page, AuthResourceQuery query) {
        try {
            // 设置目标租户上下文。
            TenantContextHolder.setTenantId(query.getTenantId());
            // 构建完整查询包装器。
            QueryWrapper<AuthResource> queryWrapper = buildQueryWrapper(query);
            // 执行分页查询。
            Page<AuthResource> pageDO = authResourceMapper.selectPage(page, queryWrapper);
            // 转换为响应分页。
            Page<AuthResourceVO> pageVO = authResourceServiceResults.toPageVO(pageDO);
            // 根据查询参数决定是否执行结果增强。
            return needAssignment(query) ? authResourceServiceResults.assignment(pageVO) : pageVO;
        } finally {
            // 清理租户上下文。
            TenantContextHolder.clear();
        }
    }

    /**
     * 查询资源列表。
     *
     * @param query 资源查询参数
     * @return 资源列表
     */
    @Override
    public List<AuthResourceVO> list(AuthResourceQuery query) {
        try {
            // 设置目标租户上下文。
            TenantContextHolder.setTenantId(query.getTenantId());
            // 构建完整查询包装器。
            QueryWrapper<AuthResource> queryWrapper = buildQueryWrapper(query);
            // 查询资源实体列表。
            List<AuthResource> records = authResourceMapper.selectList(queryWrapper);
            // 转换为响应列表。
            List<AuthResourceVO> voRecords = authResourceServiceResults.toListVO(records);
            // 根据查询参数决定是否执行结果增强。
            return needAssignment(query) ? authResourceServiceResults.assignment(voRecords) : voRecords;
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
            // 将 BO 转换为实体。
            AuthResource resource = GeneralConvertor.convertor(bo, AuthResource.class);
            // 清理空白ID，交给MyBatis-Plus生成。
            resource.setId(StringUtils.trimToNull(bo.getId()));
            // 设置默认启用状态。
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
        try {
            // 设置目标租户上下文，避免更新依赖请求头隐式租户。
            TenantContextHolder.setTenantId(bo.getTenantId());
            // 将 BO 转换为实体，保留 version 触发乐观锁。
            AuthResource resource = GeneralConvertor.convertor(bo, AuthResource.class);
            // 租户条件由租户插件处理，更新实体不主动写 tenant_id。
            resource.setTenantId(null);
            // 使用updateById执行乐观锁更新。
            return authResourceMapper.updateById(resource) > 0;
        } finally {
            // 清理租户上下文。
            TenantContextHolder.clear();
        }
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
        try {
            // 设置目标租户上下文，避免删除依赖请求头隐式租户。
            TenantContextHolder.setTenantId(bo.getTenantId());
            // 按ID逻辑删除资源。
            return authResourceMapper.deleteById(bo.getId()) > 0;
        } finally {
            // 清理租户上下文。
            TenantContextHolder.clear();
        }
    }

    /**
     * 构建资源查询包装器。
     *
     * @param query 资源查询参数
     * @return 查询包装器
     * @author sunkailun
     * @DateTime 2026/05/27
     * @email 376253703@qq.com
     */
    private QueryWrapper<AuthResource> buildQueryWrapper(AuthResourceQuery query) {
        // 将查询参数转换为实体，用于 QueryWrapper 自动拼接同名字段等值条件。
        AuthResource entity = GeneralConvertor.convertor(query, AuthResource.class);
        // 租户条件由 TenantContextHolder 和 MyBatis-Plus 租户插件处理，避免 QueryWrapper 重复拼 tenant_id。
        if (entity != null) {
            // 清理转换进实体的租户ID。
            entity.setTenantId(null);
        }
        // 创建查询包装器。
        QueryWrapper<AuthResource> queryWrapper = entity == null ? new QueryWrapper<>() : new QueryWrapper<>(entity);
        // 拼接自动查询条件。
        authResourceServiceQuery.query(query, queryWrapper);
        // 拼接人工查询条件。
        queryArtificial(query, queryWrapper);
        // 返回完整查询包装器。
        return queryWrapper;
    }

    /**
     * 拼接资源人工查询条件。
     *
     * @param query        资源查询参数
     * @param queryWrapper 查询包装器
     * @return 查询包装器
     */
    private QueryWrapper<AuthResource> queryArtificial(AuthResourceQuery query, QueryWrapper<AuthResource> queryWrapper) {
        // 查询对象为空或关键字为空时直接返回原包装器。
        if (query == null || StringUtils.isBlank(query.getQuery())) {
            // 返回调用方传入的包装器。
            return queryWrapper;
        }
        // 通用关键字匹配权限编码、资源名称或路径。
        queryWrapper.and(wrapper -> wrapper.like("code", query.getQuery()).or().like("name", query.getQuery()).or().like("path", query.getQuery()));
        // 返回完整查询包装器。
        return queryWrapper;
    }

    /**
     * 判断是否需要结果增强。
     *
     * @param query 资源查询参数
     * @return boolean
     */
    private boolean needAssignment(AuthResourceQuery query) {
        // 查询对象为空时默认执行结果增强。
        if (query == null) {
            // 返回需要增强。
            return true;
        }
        // assignment 明确传 false 时跳过结果增强，其余情况默认增强。
        return !Boolean.FALSE.equals(query.getAssignment());
    }
}
