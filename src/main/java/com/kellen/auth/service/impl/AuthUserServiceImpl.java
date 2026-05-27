package com.kellen.auth.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kellen.auth.entity.AuthUser;
import com.kellen.auth.entity.bo.AuthUserBO;
import com.kellen.auth.entity.enums.AuthStateEnum;
import com.kellen.auth.entity.query.AuthUserQuery;
import com.kellen.auth.entity.vo.AuthUserVO;
import com.kellen.auth.mapper.AuthUserMapper;
import com.kellen.auth.service.AuthUserService;
import com.kellen.auth.service.query.AuthUserServiceQuery;
import com.kellen.auth.service.results.AuthUserServiceResults;
import com.kellen.utils.convert.GeneralConvertor;
import com.kellen.utils.context.TenantContextHolder;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 用户业务服务实现。
 *
 * @author sunkailun
 * @className AuthUserServiceImpl
 * @time 2026/05/26
 */
@Service
public class AuthUserServiceImpl implements AuthUserService {

    /**
     * 用户Mapper。
     */
    private final AuthUserMapper authUserMapper;

    /**
     * 用户查询增强。
     */
    private final AuthUserServiceQuery authUserServiceQuery;

    /**
     * 用户结果增强。
     */
    private final AuthUserServiceResults authUserServiceResults;

    /**
     * 密码编码器。
     */
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    /**
     * 构造用户业务服务。
     *
     * @param authUserMapper         用户Mapper
     * @param authUserServiceQuery   用户查询增强
     * @param authUserServiceResults 用户结果增强
     */
    public AuthUserServiceImpl(AuthUserMapper authUserMapper,
                               AuthUserServiceQuery authUserServiceQuery,
                               AuthUserServiceResults authUserServiceResults) {
        // 保存用户Mapper。
        this.authUserMapper = authUserMapper;
        // 保存用户查询增强。
        this.authUserServiceQuery = authUserServiceQuery;
        // 保存用户结果增强。
        this.authUserServiceResults = authUserServiceResults;
    }

    /**
     * 分页查询用户。
     *
     * @param page  分页对象
     * @param query 用户查询参数
     * @return 用户分页
     */
    @Override
    public Page<AuthUserVO> page(Page<AuthUser> page, AuthUserQuery query) {
        try {
            // 设置目标租户上下文。
            TenantContextHolder.setTenantId(query.getTenantId());
            // 构建完整查询包装器。
            QueryWrapper<AuthUser> queryWrapper = buildQueryWrapper(query);
            // 执行分页查询。
            Page<AuthUser> pageDO = authUserMapper.selectPage(page, queryWrapper);
            // 转换为响应分页。
            Page<AuthUserVO> pageVO = authUserServiceResults.toPageVO(pageDO);
            // 根据查询参数决定是否执行结果增强。
            return needAssignment(query) ? authUserServiceResults.assignment(pageVO) : pageVO;
        } finally {
            // 清理租户上下文。
            TenantContextHolder.clear();
        }
    }

    /**
     * 查询用户列表。
     *
     * @param query 用户查询参数
     * @return 用户列表
     */
    @Override
    public List<AuthUserVO> list(AuthUserQuery query) {
        try {
            // 设置目标租户上下文。
            TenantContextHolder.setTenantId(query.getTenantId());
            // 构建完整查询包装器。
            QueryWrapper<AuthUser> queryWrapper = buildQueryWrapper(query);
            // 查询用户实体列表。
            List<AuthUser> records = authUserMapper.selectList(queryWrapper);
            // 转换为响应列表。
            List<AuthUserVO> voRecords = authUserServiceResults.toListVO(records);
            // 根据查询参数决定是否执行结果增强。
            return needAssignment(query) ? authUserServiceResults.assignment(voRecords) : voRecords;
        } finally {
            // 清理租户上下文。
            TenantContextHolder.clear();
        }
    }

    /**
     * 新增用户。
     *
     * @param bo 用户写入参数
     * @return 用户ID
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public String save(AuthUserBO bo) {
        try {
            // 设置目标租户上下文。
            TenantContextHolder.setTenantId(bo.getTenantId());
            // 查询同租户同用户名用户是否已存在，避免重复插入同一个用户。
            AuthUser exists = authUserMapper.selectOne(new LambdaQueryWrapper<AuthUser>().eq(AuthUser::getUsername, bo.getUsername()).last("LIMIT 1"));
            // 已存在则直接返回用户ID。
            if (exists != null) {
                // 返回已存在用户ID。
                return exists.getId();
            }
            // 将 BO 转换为实体。
            AuthUser user = GeneralConvertor.convertor(bo, AuthUser.class);
            // 加密密码。
            user.setPassword(passwordEncoder.encode(bo.getPassword()));
            // 设置默认启用状态。
            user.setState(bo.getState() == null ? AuthStateEnum.启用 : bo.getState());
            // 插入用户。
            authUserMapper.insert(user);
            // 返回用户ID。
            return user.getId();
        } finally {
            // 清理租户上下文。
            TenantContextHolder.clear();
        }
    }

    /**
     * 修改用户。
     *
     * @param bo 用户写入参数
     * @return 是否成功
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean update(AuthUserBO bo) {
        try {
            // 设置目标租户上下文，避免更新依赖请求头隐式租户。
            TenantContextHolder.setTenantId(bo.getTenantId());
            // 将 BO 转换为实体，保留 version 触发乐观锁。
            AuthUser user = GeneralConvertor.convertor(bo, AuthUser.class);
            // 租户条件由租户插件处理，更新实体不主动写 tenant_id。
            user.setTenantId(null);
            // 修改时空密码不覆盖原密码。
            if (StringUtils.isBlank(bo.getPassword())) {
                // 清理空密码字段。
                user.setPassword(null);
            } else {
                // 加密并设置新密码。
                user.setPassword(passwordEncoder.encode(bo.getPassword()));
            }
            // 使用updateById执行乐观锁更新。
            return authUserMapper.updateById(user) > 0;
        } finally {
            // 清理租户上下文。
            TenantContextHolder.clear();
        }
    }

    /**
     * 删除用户。
     *
     * @param bo 用户删除参数
     * @return 是否成功
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean remove(AuthUserBO bo) {
        try {
            // 设置目标租户上下文，避免删除依赖请求头隐式租户。
            TenantContextHolder.setTenantId(bo.getTenantId());
            // 按ID逻辑删除用户。
            return authUserMapper.deleteById(bo.getId()) > 0;
        } finally {
            // 清理租户上下文。
            TenantContextHolder.clear();
        }
    }

    /**
     * 构建用户查询包装器。
     *
     * @param query 用户查询参数
     * @return 查询包装器
     * @author sunkailun
     * @DateTime 2026/05/27
     * @email 376253703@qq.com
     */
    private QueryWrapper<AuthUser> buildQueryWrapper(AuthUserQuery query) {
        // 将查询参数转换为实体，用于 QueryWrapper 自动拼接同名字段等值条件。
        AuthUser entity = GeneralConvertor.convertor(query, AuthUser.class);
        // 租户条件由 TenantContextHolder 和 MyBatis-Plus 租户插件处理，避免 QueryWrapper 重复拼 tenant_id。
        if (entity != null) {
            // 清理转换进实体的租户ID。
            entity.setTenantId(null);
        }
        // 创建查询包装器。
        QueryWrapper<AuthUser> queryWrapper = entity == null ? new QueryWrapper<>() : new QueryWrapper<>(entity);
        // 拼接自动查询条件。
        authUserServiceQuery.query(query, queryWrapper);
        // 拼接人工查询条件。
        queryArtificial(query, queryWrapper);
        // 返回完整查询包装器。
        return queryWrapper;
    }

    /**
     * 拼接用户人工查询条件。
     *
     * @param query        用户查询参数
     * @param queryWrapper 查询包装器
     * @return 查询包装器
     */
    private QueryWrapper<AuthUser> queryArtificial(AuthUserQuery query, QueryWrapper<AuthUser> queryWrapper) {
        // 查询对象为空或关键字为空时直接返回原包装器。
        if (query == null || StringUtils.isBlank(query.getQuery())) {
            // 返回调用方传入的包装器。
            return queryWrapper;
        }
        // 通用关键字匹配用户名或昵称。
        queryWrapper.and(wrapper -> wrapper.like("username", query.getQuery()).or().like("nickname", query.getQuery()));
        // 返回完整查询包装器。
        return queryWrapper;
    }

    /**
     * 判断是否需要结果增强。
     *
     * @param query 用户查询参数
     * @return boolean
     */
    private boolean needAssignment(AuthUserQuery query) {
        // 查询对象为空时默认执行结果增强。
        if (query == null) {
            // 返回需要增强。
            return true;
        }
        // assignment 明确传 false 时跳过结果增强，其余情况默认增强。
        return !Boolean.FALSE.equals(query.getAssignment());
    }
}
