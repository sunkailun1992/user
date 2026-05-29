package com.kellen.auth.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kellen.auth.entity.AuthUser;
import com.kellen.auth.entity.AuthUserTenant;
import com.kellen.auth.entity.bo.AuthUserBO;
import com.kellen.auth.entity.enums.AuthDataScopeEnum;
import com.kellen.auth.entity.enums.AuthAdminTypeEnum;
import com.kellen.auth.entity.enums.AuthStateEnum;
import com.kellen.auth.entity.query.AuthUserQuery;
import com.kellen.auth.entity.vo.AuthUserVO;
import com.kellen.auth.mapper.AuthUserMapper;
import com.kellen.auth.mapper.AuthUserTenantMapper;
import com.kellen.auth.service.AuthUserService;
import com.kellen.auth.service.query.AuthUserServiceQuery;
import com.kellen.auth.service.results.AuthUserServiceResults;
import com.kellen.datapermission.DataPermissionContextHolder;
import com.kellen.security.SecurityUser;
import com.kellen.security.UserContextHolder;
import com.kellen.utils.convert.GeneralConvertor;
import com.kellen.utils.context.TenantContextHolder;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

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
     * 用户租户关联Mapper。
     */
    private final AuthUserTenantMapper authUserTenantMapper;

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
     * @param authUserTenantMapper   用户租户关联Mapper
     */
    public AuthUserServiceImpl(AuthUserMapper authUserMapper,
                               AuthUserServiceQuery authUserServiceQuery,
                               AuthUserServiceResults authUserServiceResults,
                               AuthUserTenantMapper authUserTenantMapper) {
        // 保存用户Mapper。
        this.authUserMapper = authUserMapper;
        // 保存用户查询增强。
        this.authUserServiceQuery = authUserServiceQuery;
        // 保存用户结果增强。
        this.authUserServiceResults = authUserServiceResults;
        // 保存用户租户关联Mapper。
        this.authUserTenantMapper = authUserTenantMapper;
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
            DataPermissionContextHolder.ignore(); // 用户管理使用本服务按用户/部门处理数据范围，避免通用表规则重复叠加。
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
            // 清理数据权限忽略标记。
            DataPermissionContextHolder.clear();
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
            DataPermissionContextHolder.ignore(); // 用户管理使用本服务按用户/部门处理数据范围，避免通用表规则重复叠加。
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
            // 清理数据权限忽略标记。
            DataPermissionContextHolder.clear();
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
            // 设置默认管理员分类。
            user.setAdminType(bo.getAdminType() == null ? AuthAdminTypeEnum.TENANT_ADMIN : bo.getAdminType());
            // 插入用户。
            authUserMapper.insert(user);
            // 同步用户可访问租户。
            syncUserTenants(user.getId(), bo);
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
            boolean updated = authUserMapper.updateById(user) > 0;
            if (updated) {
                // 同步用户可访问租户。
                syncUserTenants(bo.getId(), bo);
            }
            // 返回更新结果。
            return updated;
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
        // 拼接当前登录用户数据范围条件。
        applyUserDataScope(queryWrapper);
        // 返回完整查询包装器。
        return queryWrapper;
    }

    /**
     * 同步用户租户关联。
     *
     * @param userId 用户ID
     * @param bo     用户写入参数
     */
    private void syncUserTenants(String userId, AuthUserBO bo) {
        if (StringUtils.isBlank(userId) || bo == null) {
            // 缺少必要参数时不处理关联。
            return;
        }
        try {
            // 用户租户关联是跨租户认证配置，写入时忽略当前租户插件条件。
            TenantContextHolder.ignore();
            // 删除旧关联。
            authUserTenantMapper.deleteByUserId(userId);
            // 创建待写入租户集合。
            Set<String> tenantIds = new LinkedHashSet<>();
            if (bo.getTenantIds() != null) {
                // 加入表单选择的关联租户。
                bo.getTenantIds().stream().filter(StringUtils::isNotBlank).forEach(tenantIds::add);
            }
            if (StringUtils.isNotBlank(bo.getTenantId())) {
                // 默认租户必须纳入关联范围。
                tenantIds.add(bo.getTenantId());
            }
            for (String tenantId : tenantIds) {
                // 创建用户租户关联。
                AuthUserTenant userTenant = new AuthUserTenant();
                userTenant.setId(UUID.randomUUID().toString());
                userTenant.setUserId(userId);
                userTenant.setRelationTenantId(tenantId);
                userTenant.setDeptId(tenantId.equals(bo.getTenantId()) ? bo.getDeptId() : null);
                userTenant.setDefaultTenant(tenantId.equals(bo.getTenantId()));
                userTenant.setCode(userId + ":" + tenantId);
                userTenant.setDescription("用户租户关联");
                userTenant.setState(AuthStateEnum.启用);
                userTenant.setTenantId(tenantId);
                authUserTenantMapper.insert(userTenant);
            }
        } finally {
            // 清理租户忽略标记。
            TenantContextHolder.clearIgnore();
        }
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
     * 按当前登录用户数据范围过滤用户列表。
     *
     * @param queryWrapper 查询包装器
     */
    private void applyUserDataScope(QueryWrapper<AuthUser> queryWrapper) {
        SecurityUser user = UserContextHolder.get(); // 读取认证过滤器写入的当前用户快照。
        if (user == null || StringUtils.isBlank(user.getDataScope())) {
            return; // 未登录或未携带数据范围时交给接口鉴权和租户隔离处理。
        }
        if (AuthDataScopeEnum.ALL.getValue().equalsIgnoreCase(user.getDataScope())) {
            return; // 全部数据不追加用户条件。
        }
        if (AuthDataScopeEnum.SELF.getValue().equalsIgnoreCase(user.getDataScope())) {
            queryWrapper.eq("id", user.getUserId()); // 仅本人数据只展示当前登录用户。
            return;
        }
        Set<String> deptIds = new LinkedHashSet<>(); // 创建可见部门集合。
        if (AuthDataScopeEnum.DEPT.getValue().equalsIgnoreCase(user.getDataScope())) {
            if (StringUtils.isNotBlank(user.getDeptId())) {
                deptIds.add(user.getDeptId()); // 本部门数据按用户所属部门过滤。
            }
        } else {
            if (user.getDataScopeDeptIds() != null) {
                user.getDataScopeDeptIds().stream().filter(StringUtils::isNotBlank).forEach(deptIds::add); // 加入角色计算出的可见部门。
            }
            if (StringUtils.isNotBlank(user.getDeptId())) {
                deptIds.add(user.getDeptId()); // 部门树和自定义范围保留当前所属部门。
            }
        }
        if (deptIds.isEmpty()) {
            queryWrapper.eq("id", "__NO_VISIBLE_USER__"); // 没有可见部门时强制返回空结果。
            return;
        }
        queryWrapper.in("dept_id", deptIds); // 用户表按所属部门过滤。
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
