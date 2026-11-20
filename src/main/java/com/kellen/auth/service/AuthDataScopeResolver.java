package com.kellen.auth.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.kellen.auth.entity.AuthDept;
import com.kellen.auth.entity.AuthRole;
import com.kellen.auth.entity.AuthRoleDataScope;
import com.kellen.auth.entity.AuthUserRole;
import com.kellen.auth.entity.enums.AuthDataScopeEnum;
import com.kellen.auth.mapper.AuthDeptMapper;
import com.kellen.auth.mapper.AuthRoleDataScopeMapper;
import com.kellen.auth.mapper.AuthRoleMapper;
import com.kellen.auth.mapper.AuthUserRoleMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 按用户在活动租户下的角色合并数据权限范围：任一角色是全部数据即为全部；本部门、本部门及下级、自定义部门合并为部门集合；
 * 否则为仅本人。在指定租户下查询，调用方的上下文原样恢复。
 *
 * @author 孙凯伦
 */
@Component
public class AuthDataScopeResolver {

    private final AuthUserRoleMapper authUserRoleMapper;

    private final AuthRoleMapper authRoleMapper;

    private final AuthDeptMapper authDeptMapper;

    private final AuthRoleDataScopeMapper authRoleDataScopeMapper;

    /**
     * 构造数据权限范围计算。
     *
     * @param authUserRoleMapper      用户角色 Mapper
     * @param authRoleMapper          角色 Mapper
     * @param authDeptMapper          部门 Mapper
     * @param authRoleDataScopeMapper 角色数据范围 Mapper
     */
    public AuthDataScopeResolver(AuthUserRoleMapper authUserRoleMapper, AuthRoleMapper authRoleMapper, AuthDeptMapper authDeptMapper,
                                 AuthRoleDataScopeMapper authRoleDataScopeMapper) {
        this.authUserRoleMapper = authUserRoleMapper;
        this.authRoleMapper = authRoleMapper;
        this.authDeptMapper = authDeptMapper;
        this.authRoleDataScopeMapper = authRoleDataScopeMapper;
    }

    /**
     * 计算数据权限范围。
     *
     * @param tenantId 活动租户
     * @param userId   用户 ID
     * @param deptId   用户在该租户下的部门
     * @return 数据权限范围
     */
    public DataScope resolve(String tenantId, String userId, String deptId) {
        return AuthQueryScope.inTenant(tenantId, () -> {
            List<AuthRole> roles = rolesOf(userId);
            if (roles.isEmpty()) {
                return new DataScope(AuthDataScopeEnum.SELF.getValue(), List.of());
            }
            if (roles.stream().anyMatch(role -> AuthDataScopeEnum.ALL == role.getDataScope())) {
                return new DataScope(AuthDataScopeEnum.ALL.getValue(), List.of());
            }
            Set<String> deptIds = new LinkedHashSet<>();
            for (AuthRole role : roles) {
                AuthDataScopeEnum scope = role.getDataScope() == null ? AuthDataScopeEnum.SELF : role.getDataScope();
                if (AuthDataScopeEnum.DEPT == scope && StringUtils.isNotBlank(deptId)) {
                    deptIds.add(deptId);
                }
                if (AuthDataScopeEnum.DEPT_TREE == scope && StringUtils.isNotBlank(deptId)) {
                    deptIds.addAll(deptTree(deptId));
                }
                if (AuthDataScopeEnum.CUSTOM == scope) {
                    deptIds.addAll(customDepts(role.getId()));
                }
            }
            return deptIds.isEmpty() ? new DataScope(AuthDataScopeEnum.SELF.getValue(), List.of())
                    : new DataScope(AuthDataScopeEnum.CUSTOM.getValue(), List.copyOf(deptIds));
        });
    }

    /**
     * 用户在当前租户下的角色（租户插件按调用方设置的租户拼条件）。
     *
     * @param userId 用户 ID
     * @return 角色
     */
    public List<AuthRole> rolesOf(String userId) {
        Set<String> roleIds = authUserRoleMapper.selectList(new LambdaQueryWrapper<AuthUserRole>().eq(AuthUserRole::getUserId, userId))
                .stream().map(AuthUserRole::getRoleId).filter(StringUtils::isNotBlank).collect(Collectors.toCollection(LinkedHashSet::new));
        return roleIds.isEmpty() ? List.of() : authRoleMapper.selectList(new LambdaQueryWrapper<AuthRole>().in(AuthRole::getId, roleIds));
    }

    private List<String> customDepts(String roleId) {
        return authRoleDataScopeMapper.selectList(new LambdaQueryWrapper<AuthRoleDataScope>().eq(AuthRoleDataScope::getRoleId, roleId))
                .stream().map(AuthRoleDataScope::getDeptId).filter(StringUtils::isNotBlank).distinct().toList();
    }

    private List<String> deptTree(String rootDeptId) {
        List<AuthDept> depts = authDeptMapper.selectList(new LambdaQueryWrapper<>());
        Set<String> deptIds = new LinkedHashSet<>();
        deptIds.add(rootDeptId);
        boolean changed;
        do {
            changed = false;
            for (AuthDept dept : depts) {
                if (deptIds.contains(dept.getParentId()) && deptIds.add(dept.getId())) {
                    changed = true;
                }
            }
        } while (changed);
        return List.copyOf(deptIds);
    }

    /**
     * 数据权限范围。
     *
     * @param dataScope 范围
     * @param deptIds   部门集合
     */
    public record DataScope(String dataScope, List<String> deptIds) {
    }
}
