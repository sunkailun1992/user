package com.kellen.auth.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kellen.auth.entity.AuthUser;
import com.kellen.auth.entity.AuthUserTenant;
import com.kellen.auth.entity.bo.AuthUserBO;
import com.kellen.auth.entity.enums.AuthAdminTypeEnum;
import com.kellen.auth.entity.enums.AuthStateEnum;
import com.kellen.auth.entity.query.AuthUserQuery;
import com.kellen.auth.entity.vo.AuthUserVO;
import com.kellen.auth.mapper.AuthUserMapper;
import com.kellen.auth.mapper.AuthUserTenantMapper;
import com.kellen.auth.keycloak.KeycloakAccountClient;
import com.kellen.auth.mq.producer.UserAccountEventProducer;
import com.kellen.auth.service.AuthQueryScope;
import com.kellen.auth.service.AuthSecurityContextInvalidator;
import com.kellen.auth.service.AuthUserService;
import com.kellen.auth.service.query.AuthUserServiceQuery;
import com.kellen.auth.service.results.AuthUserServiceResults;
import com.kellen.utils.convert.GeneralConvertor;
import com.kellen.utils.context.TenantContextHolder;
import com.kellen.utils.enumeration.ReturnCode;
import com.kellen.utils.exception.UserException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

/**
 * 用户业务服务实现。
 *
 * <p>第5阶段起账号与口令在 Keycloak：建、改、停用、重置口令、删除账号时经 Admin REST API 同步（失败时本地事务回滚），
 * 并让业务上下文缓存换版本。user 库不再存口令。</p>
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
     * Keycloak 账号同步。
     */
    private final KeycloakAccountClient keycloakAccountClient;
    /**
     * 业务上下文缓存失效通知。
     */
    private final AuthSecurityContextInvalidator securityContextInvalidator;

    /**
     * “账号已创建”事件的生产者。
     */
    private final UserAccountEventProducer userAccountEventProducer;

    /**
     * 日志对象。
     */
    private static final Logger log = LoggerFactory.getLogger(AuthUserServiceImpl.class);

    /**
     * 构造用户业务服务。
     *
     * @param authUserMapper         用户Mapper
     * @param authUserServiceQuery   用户查询增强
     * @param authUserServiceResults 用户结果增强
     * @param authUserTenantMapper   用户租户关联Mapper
     * @param keycloakAccountClient      Keycloak 账号同步
     * @param securityContextInvalidator 业务上下文缓存失效通知
     * @param userAccountEventProducer  “账号已创建”事件的生产者
     */
    public AuthUserServiceImpl(AuthUserMapper authUserMapper,
                               AuthUserServiceQuery authUserServiceQuery,
                               AuthUserServiceResults authUserServiceResults,
                               AuthUserTenantMapper authUserTenantMapper,
                               KeycloakAccountClient keycloakAccountClient,
                               AuthSecurityContextInvalidator securityContextInvalidator,
                               UserAccountEventProducer userAccountEventProducer) {
        // 保存用户Mapper。
        this.authUserMapper = authUserMapper;
        // 保存用户查询增强。
        this.authUserServiceQuery = authUserServiceQuery;
        // 保存用户结果增强。
        this.authUserServiceResults = authUserServiceResults;
        // 保存用户租户关联Mapper。
        this.authUserTenantMapper = authUserTenantMapper;
        // 保存 Keycloak 账号同步。
        this.keycloakAccountClient = keycloakAccountClient;
        // 保存业务上下文缓存失效通知。
        this.securityContextInvalidator = securityContextInvalidator;
        // 保存“账号已创建”事件的生产者。
        this.userAccountEventProducer = userAccountEventProducer;
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
            // 新账号的初始口令在 Keycloak 里是临时口令，首次登录必须修改；user 库不存口令。
            if (StringUtils.isBlank(bo.getPassword())) {
                throw new UserException(ReturnCode.PASSWORD_VALIDATION_FAILED, "新账号需要临时口令");
            }
            // 将 BO 转换为实体。
            AuthUser user = GeneralConvertor.convertor(bo, AuthUser.class);
            // 设置默认启用状态。
            user.setState(bo.getState() == null ? AuthStateEnum.启用 : bo.getState());
            // 设置默认管理员分类。
            user.setAdminType(bo.getAdminType() == null ? AuthAdminTypeEnum.TENANT_ADMIN : bo.getAdminType());
            // 插入用户。
            authUserMapper.insert(user);
            // 同步用户可访问租户。
            syncUserTenants(user.getId(), bo);
            // 建 Keycloak 账号（失败时整个事务回滚）；本地事务之后若回滚，补偿删除已建的账号。
            String subject = keycloakAccountClient.createAccount(user.getId(), user.getUsername(), user.getNickname(), bo.getPassword());
            deleteAccountIfRolledBack(subject);
            if (user.getState() == AuthStateEnum.禁用) {
                keycloakAccountClient.setEnabled(subject, false);
            }
            // 回写 Keycloak 用户 ID。
            authUserMapper.update(null, new LambdaUpdateWrapper<AuthUser>().set(AuthUser::getIdpSubject, subject).eq(AuthUser::getId, user.getId()));
            // 事务提交后发“账号已创建”事件，回滚则不发。
            userAccountEventProducer.publishAccountCreated(user, bo.getTenantId());
            securityContextInvalidator.user(user.getId());
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
            // 改之前的账号，用来判断 Keycloak 里要同步哪些项。
            AuthUser before = AuthQueryScope.crossTenant(() -> authUserMapper.selectById(bo.getId()));
            // 将 BO 转换为实体，保留 version 触发乐观锁。
            AuthUser user = GeneralConvertor.convertor(bo, AuthUser.class);
            // 租户条件由租户插件处理，更新实体不主动写 tenant_id。
            user.setTenantId(null);
            // Keycloak 用户 ID 只由建账号与账号迁移写入。
            user.setIdpSubject(null);
            // 使用updateById执行乐观锁更新。
            boolean updated = authUserMapper.updateById(user) > 0;
            if (updated) {
                // 同步用户可访问租户。
                syncUserTenants(bo.getId(), bo);
                // 同步 Keycloak 账号（失败时整个事务回滚）。
                syncKeycloakAccount(before, bo);
                securityContextInvalidator.user(bo.getId());
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
     * @param tenantId 租户ID
     * @param id       用户主键
     * @return 是否成功
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean remove(String tenantId, String id) {
        try {
            // 设置目标租户上下文，避免删除依赖请求头隐式租户。
            TenantContextHolder.setTenantId(tenantId);
            // 删除前的账号，取 Keycloak 用户 ID（是否删得到仍由下面带租户条件的删除决定）。
            AuthUser before = AuthQueryScope.crossTenant(() -> authUserMapper.selectById(id));
            // 按ID逻辑删除用户。
            boolean removed = authUserMapper.deleteById(id) > 0;
            if (removed) {
                // 删除 Keycloak 账号（失败时整个事务回滚，用户保留）。
                if (before != null && StringUtils.isNotBlank(before.getIdpSubject())) {
                    keycloakAccountClient.deleteAccount(before.getIdpSubject());
                }
                securityContextInvalidator.user(id);
            }
            return removed;
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
     * 按改动同步 Keycloak 账号：用户名或昵称变了改资料；给了状态就同步启停；给了口令就重置为临时口令。
     * 还没有 Keycloak 账号的旧数据（账号迁移前）只改本地资料；这时改口令无处可改，明确报错而不是静默跳过。
     *
     * @param before 改之前的账号
     * @param bo     写入参数
     */
    private void syncKeycloakAccount(AuthUser before, AuthUserBO bo) {
        if (before == null || StringUtils.isBlank(before.getIdpSubject())) {
            if (StringUtils.isNotBlank(bo.getPassword())) {
                throw new UserException(ReturnCode.USER_ACCOUNT_NOT_FOUND, "该用户还没有 Keycloak 账号，不能重置口令；请先完成账号迁移");
            }
            log.warn("用户还没有 Keycloak 账号，只改本地资料：{}", bo.getId());
            return;
        }
        String subject = before.getIdpSubject();
        String username = StringUtils.defaultIfBlank(bo.getUsername(), before.getUsername());
        String nickname = bo.getNickname() == null ? before.getNickname() : bo.getNickname();
        if (!Objects.equals(username, before.getUsername()) || !Objects.equals(nickname, before.getNickname())) {
            keycloakAccountClient.updateProfile(subject, username, nickname);
        }
        if (bo.getState() != null) {
            keycloakAccountClient.setEnabled(subject, bo.getState() == AuthStateEnum.启用);
        }
        if (StringUtils.isNotBlank(bo.getPassword())) {
            keycloakAccountClient.resetPassword(subject, bo.getPassword());
        }
    }

    /**
     * 本地事务回滚时补偿删除刚建的 Keycloak 账号；补偿失败只记录 Keycloak 用户 ID，由维护者清理。
     *
     * @param subject Keycloak 用户 ID
     */
    private void deleteAccountIfRolledBack(String subject) {
        if (!TransactionSynchronizationManager.isSynchronizationActive()) {
            return;
        }
        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
            @Override
            public void afterCompletion(int status) {
                if (status != STATUS_ROLLED_BACK) {
                    return;
                }
                try {
                    keycloakAccountClient.deleteAccount(subject);
                } catch (RuntimeException ex) {
                    log.error("本地建账号回滚后，补偿删除 Keycloak 账号失败，请手工删除：{}", subject);
                }
            }
        });
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
