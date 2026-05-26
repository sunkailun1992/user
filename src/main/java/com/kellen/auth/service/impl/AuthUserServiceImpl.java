package com.kellen.auth.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.kellen.auth.entity.AuthUser;
import com.kellen.auth.entity.bo.AuthUserBO;
import com.kellen.auth.entity.enums.AuthStateEnum;
import com.kellen.auth.mapper.AuthUserMapper;
import com.kellen.auth.service.AuthUserService;
import com.kellen.utils.TenantContextHolder;
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
     * 密码编码器。
     */
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    /**
     * 构造用户业务服务。
     *
     * @param authUserMapper 用户Mapper
     */
    public AuthUserServiceImpl(AuthUserMapper authUserMapper) {
        // 保存用户Mapper。
        this.authUserMapper = authUserMapper;
    }

    /**
     * 查询用户列表。
     *
     * @param tenantId 租户ID
     * @return 用户列表
     */
    @Override
    public List<AuthUser> list(String tenantId) {
        try {
            // 设置目标租户上下文。
            TenantContextHolder.setTenantId(tenantId);
            // 查询当前租户用户列表。
            return authUserMapper.selectList(new LambdaQueryWrapper<AuthUser>().orderByAsc(AuthUser::getUsername));
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
            // 查询同租户同用户名用户是否已存在，保证初始化接口可重复调用。
            AuthUser exists = authUserMapper.selectOne(new LambdaQueryWrapper<AuthUser>().eq(AuthUser::getUsername, bo.getUsername()).last("LIMIT 1"));
            // 已存在则直接返回用户ID。
            if (exists != null) {
                // 返回已存在用户ID。
                return exists.getId();
            }
            // 创建用户实体。
            AuthUser user = new AuthUser();
            // 设置用户ID，初始化场景可以传入固定ID。
            user.setId(bo.getId());
            // 设置用户名。
            user.setUsername(bo.getUsername());
            // 加密密码。
            user.setPassword(passwordEncoder.encode(bo.getPassword()));
            // 设置昵称。
            user.setNickname(bo.getNickname());
            // 设置状态。
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
        // 创建用户更新实体。
        AuthUser user = new AuthUser();
        // 设置用户ID。
        user.setId(bo.getId());
        // 设置旧版本号，触发MyBatis-Plus乐观锁。
        user.setVersion(bo.getVersion());
        // 设置用户名。
        user.setUsername(bo.getUsername());
        // 设置昵称。
        user.setNickname(bo.getNickname());
        // 设置状态。
        user.setState(bo.getState());
        // 判断是否需要更新密码。
        if (StringUtils.isNotBlank(bo.getPassword())) {
            // 加密并设置新密码。
            user.setPassword(passwordEncoder.encode(bo.getPassword()));
        }
        // 使用updateById执行乐观锁更新。
        return authUserMapper.updateById(user) > 0;
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
        // 按ID逻辑删除用户。
        return authUserMapper.deleteById(bo.getId()) > 0;
    }
}
