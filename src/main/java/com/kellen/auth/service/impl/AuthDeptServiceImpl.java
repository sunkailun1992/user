package com.kellen.auth.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kellen.auth.entity.AuthDept;
import com.kellen.auth.entity.bo.AuthDeptBO;
import com.kellen.auth.entity.enums.AuthDataScopeEnum;
import com.kellen.auth.entity.enums.AuthStateEnum;
import com.kellen.auth.entity.query.AuthDeptQuery;
import com.kellen.auth.entity.vo.AuthDeptVO;
import com.kellen.auth.mapper.AuthDeptMapper;
import com.kellen.auth.service.AuthDeptService;
import com.kellen.auth.service.query.AuthDeptServiceQuery;
import com.kellen.auth.service.results.AuthDeptServiceResults;
import com.kellen.datapermission.DataPermissionContextHolder;
import com.kellen.security.SecurityUser;
import com.kellen.security.UserContextHolder;
import com.kellen.utils.context.TenantContextHolder;
import com.kellen.utils.convert.GeneralConvertor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * 部门业务服务实现。
 *
 * @author sunkailun
 * @className AuthDeptServiceImpl
 * @time 2026/05/27
 */
@Service
public class AuthDeptServiceImpl implements AuthDeptService {

    /**
     * 部门Mapper。
     */
    private final AuthDeptMapper authDeptMapper;

    /**
     * 部门查询增强。
     */
    private final AuthDeptServiceQuery authDeptServiceQuery;

    /**
     * 部门结果增强。
     */
    private final AuthDeptServiceResults authDeptServiceResults;

    /**
     * 构造部门业务服务。
     *
     * @param authDeptMapper         部门Mapper
     * @param authDeptServiceQuery   部门查询增强
     * @param authDeptServiceResults 部门结果增强
     */
    public AuthDeptServiceImpl(AuthDeptMapper authDeptMapper, AuthDeptServiceQuery authDeptServiceQuery, AuthDeptServiceResults authDeptServiceResults) {
        this.authDeptMapper = authDeptMapper; // 保存部门Mapper。
        this.authDeptServiceQuery = authDeptServiceQuery; // 保存部门查询增强。
        this.authDeptServiceResults = authDeptServiceResults; // 保存部门结果增强。
    }

    /**
     * 分页查询部门。
     *
     * @param page  分页对象
     * @param query 部门查询参数
     * @return 部门分页
     */
    @Override
    public Page<AuthDeptVO> page(Page<AuthDept> page, AuthDeptQuery query) {
        try {
            DataPermissionContextHolder.ignore(); // 部门管理使用本服务按部门ID处理组织树数据范围，避免通用表规则重复叠加。
            TenantContextHolder.setTenantId(query.getTenantId()); // 设置目标租户上下文。
            Page<AuthDept> pageDO = authDeptMapper.selectPage(page, buildQueryWrapper(query)); // 执行分页查询。
            return authDeptServiceResults.toPageVO(pageDO); // 转换为响应分页。
        } finally {
            DataPermissionContextHolder.clear(); // 清理数据权限忽略标记。
            TenantContextHolder.clear(); // 清理租户上下文。
        }
    }

    /**
     * 查询部门列表。
     *
     * @param query 部门查询参数
     * @return 部门列表
     */
    @Override
    public List<AuthDeptVO> list(AuthDeptQuery query) {
        try {
            DataPermissionContextHolder.ignore(); // 部门管理使用本服务按部门ID处理组织树数据范围，避免通用表规则重复叠加。
            TenantContextHolder.setTenantId(query.getTenantId()); // 设置目标租户上下文。
            List<AuthDept> records = authDeptMapper.selectList(buildQueryWrapper(query)); // 查询部门实体列表。
            return authDeptServiceResults.toListVO(records); // 转换为响应列表。
        } finally {
            DataPermissionContextHolder.clear(); // 清理数据权限忽略标记。
            TenantContextHolder.clear(); // 清理租户上下文。
        }
    }

    /**
     * 新增部门。
     *
     * @param bo 部门写入参数
     * @return 部门ID
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public String save(AuthDeptBO bo) {
        try {
            TenantContextHolder.setTenantId(bo.getTenantId()); // 设置目标租户上下文。
            AuthDept exists = authDeptMapper.selectOne(new LambdaQueryWrapper<AuthDept>().eq(AuthDept::getCode, bo.getCode()).last("LIMIT 1")); // 查询同编码部门。
            if (exists != null) {
                return exists.getId(); // 已存在时直接返回部门ID。
            }
            AuthDept dept = GeneralConvertor.convertor(bo, AuthDept.class); // 将 BO 转换为实体。
            dept.setId(StringUtils.trimToNull(bo.getId())); // 空白ID交给 MyBatis-Plus 自动生成。
            dept.setState(bo.getState() == null ? AuthStateEnum.启用 : bo.getState()); // 设置默认启用状态。
            authDeptMapper.insert(dept); // 插入部门。
            return dept.getId(); // 返回部门ID。
        } finally {
            TenantContextHolder.clear(); // 清理租户上下文。
        }
    }

    /**
     * 修改部门。
     *
     * @param bo 部门写入参数
     * @return 是否成功
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean update(AuthDeptBO bo) {
        try {
            TenantContextHolder.setTenantId(bo.getTenantId()); // 设置目标租户上下文。
            AuthDept dept = GeneralConvertor.convertor(bo, AuthDept.class); // 将 BO 转换为实体。
            dept.setTenantId(null); // 租户条件由租户插件处理，避免更新 tenant_id。
            return authDeptMapper.updateById(dept) > 0; // 使用 updateById 触发乐观锁。
        } finally {
            TenantContextHolder.clear(); // 清理租户上下文。
        }
    }

    /**
     * 删除部门。
     *
     * @param bo 部门删除参数
     * @return 是否成功
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean remove(AuthDeptBO bo) {
        try {
            TenantContextHolder.setTenantId(bo.getTenantId()); // 设置目标租户上下文。
            return authDeptMapper.deleteById(bo.getId()) > 0; // 按ID逻辑删除部门。
        } finally {
            TenantContextHolder.clear(); // 清理租户上下文。
        }
    }

    /**
     * 构建部门查询包装器。
     *
     * @param query 部门查询参数
     * @return 查询包装器
     */
    private QueryWrapper<AuthDept> buildQueryWrapper(AuthDeptQuery query) {
        AuthDept entity = GeneralConvertor.convertor(query, AuthDept.class); // 将查询参数转换为实体。
        if (entity != null) {
            entity.setTenantId(null); // 租户条件由租户插件处理，避免重复拼 tenant_id。
        }
        QueryWrapper<AuthDept> queryWrapper = entity == null ? new QueryWrapper<>() : new QueryWrapper<>(entity); // 创建查询包装器。
        authDeptServiceQuery.query(query, queryWrapper); // 拼接公共查询条件。
        if (query != null && StringUtils.isNotBlank(query.getQuery())) {
            queryWrapper.and(wrapper -> wrapper.like("code", query.getQuery()).or().like("name", query.getQuery())); // 拼接关键字查询。
        }
        applyDeptDataScope(queryWrapper); // 部门表没有 dept_id，按部门自身ID应用当前用户数据范围。
        return queryWrapper; // 返回完整查询包装器。
    }

    /**
     * 按当前登录用户数据范围过滤部门树。
     *
     * @param queryWrapper 查询包装器
     */
    private void applyDeptDataScope(QueryWrapper<AuthDept> queryWrapper) {
        SecurityUser user = UserContextHolder.get(); // 读取认证过滤器写入的当前用户快照。
        if (user == null || StringUtils.isBlank(user.getDataScope())) {
            return; // 未登录或未携带数据范围时交给接口鉴权和租户隔离处理。
        }
        if (AuthDataScopeEnum.ALL.getValue().equalsIgnoreCase(user.getDataScope())) {
            return; // 全部数据不追加部门条件。
        }
        Set<String> deptIds = new LinkedHashSet<>(); // 创建可见部门集合。
        if (AuthDataScopeEnum.SELF.getValue().equalsIgnoreCase(user.getDataScope())
                || AuthDataScopeEnum.DEPT.getValue().equalsIgnoreCase(user.getDataScope())) {
            if (StringUtils.isNotBlank(user.getDeptId())) {
                deptIds.add(user.getDeptId()); // 本人和本部门在组织树中只展示用户所在部门。
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
            queryWrapper.eq("id", "__NO_VISIBLE_DEPT__"); // 没有可见部门时强制返回空结果。
            return;
        }
        queryWrapper.in("id", deptIds); // 部门表按自身ID过滤。
    }
}
