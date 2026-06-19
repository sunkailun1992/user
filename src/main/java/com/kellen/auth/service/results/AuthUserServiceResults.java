package com.kellen.auth.service.results;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kellen.auth.entity.AuthUser;
import com.kellen.auth.entity.AuthUserTenant;
import com.kellen.auth.entity.vo.AuthUserVO;
import com.kellen.auth.mapper.AuthUserTenantMapper;
import com.kellen.datapermission.DataPermissionContextHolder;
import com.kellen.utils.convert.GeneralConvertor;
import com.kellen.utils.context.TenantContextHolder;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 用户查询结果转换增强。
 *
 * @author sunkailun
 * @className AuthUserServiceResults
 * @time 2026/05/27
 */
@Component
public class AuthUserServiceResults {

    /**
     * 用户租户关联Mapper。
     */
    private final AuthUserTenantMapper authUserTenantMapper;

    /**
     * 构造用户查询结果转换增强。
     *
     * @param authUserTenantMapper 用户租户关联Mapper
     */
    public AuthUserServiceResults(AuthUserTenantMapper authUserTenantMapper) {
        this.authUserTenantMapper = authUserTenantMapper;
    }

    /**
     * 转换单条用户结果。
     *
     * @param recordDO 用户实体
     * @return 用户响应对象
     * @author sunkailun
     */
    public AuthUserVO toVO(AuthUser recordDO) {
        // 单条转换时复用带租户集合的转换方法，保持对外方法语义不变。
        return toVO(recordDO, recordDO == null ? Collections.emptyList() : listTenantIds(recordDO.getId()));
    }

    /**
     * 转换单条用户结果。
     *
     * @param recordDO  用户实体
     * @param tenantIds 用户关联租户ID集合
     * @return 用户响应对象
     * @author sunkailun
     */
    private AuthUserVO toVO(AuthUser recordDO, List<String> tenantIds) {
        // 实体为空时返回空响应。
        if (recordDO == null) {
            // 返回空值。
            return null;
        }
        // 转换基础同名字段。
        AuthUserVO vo = GeneralConvertor.convertor(recordDO, AuthUserVO.class);
        // 补充状态说明。
        vo.setStateDesc(recordDO.getState() == null ? null : recordDO.getState().getDesc());
        // 补充管理员分类说明。
        vo.setAdminTypeDesc(recordDO.getAdminType() == null ? null : recordDO.getAdminType().getDesc());
        // 补充调用方已经查询好的关联租户ID集合，避免列表转换重复查询数据库。
        vo.setTenantIds(tenantIds);
        // 返回响应对象。
        return vo;
    }

    /**
     * 查询用户关联租户ID集合。
     *
     * @param userId 用户ID
     * @return 租户ID集合
     * @author sunkailun
     */
    private List<String> listTenantIds(String userId) {
        // 用户ID为空时直接返回空集合，避免 List.of 空元素触发异常。
        if (userId == null) {
            // 返回不可变空集合。
            return Collections.emptyList();
        }
        // 委托批量查询方法，保证单用户查询和列表查询使用同一套租户忽略逻辑。
        return listTenantIdsMap(List.of(userId)).getOrDefault(userId, Collections.emptyList());
    }

    /**
     * 批量查询用户关联租户ID集合。
     *
     * @param userIds 用户ID集合
     * @return 用户ID和租户ID集合映射
     * @author sunkailun
     */
    private Map<String, List<String>> listTenantIdsMap(List<String> userIds) {
        // 用户ID集合为空时直接返回空映射，避免构造无意义 SQL。
        if (userIds == null || userIds.isEmpty()) {
            // 返回不可变空映射。
            return Collections.emptyMap();
        }
        try {
            TenantContextHolder.ignore(); // 用户租户关联是认证配置数据，转换结果时跨租户读取。
            DataPermissionContextHolder.ignore();
            return authUserTenantMapper.selectList(new LambdaQueryWrapper<AuthUserTenant>().in(AuthUserTenant::getUserId, userIds))
                    .stream()
                    .collect(Collectors.groupingBy(AuthUserTenant::getUserId,
                            Collectors.mapping(AuthUserTenant::getRelationTenantId,
                                    Collectors.collectingAndThen(Collectors.toList(), values -> values.stream().distinct().toList()))));
        } finally {
            TenantContextHolder.clearIgnore();
            DataPermissionContextHolder.clear();
        }
    }

    /**
     * 转换用户列表结果。
     *
     * @param recordsDO 用户实体列表
     * @return 用户响应列表
     * @author sunkailun
     */
    public List<AuthUserVO> toListVO(List<AuthUser> recordsDO) {
        // 列表为空时返回空集合。
        if (recordsDO == null || recordsDO.isEmpty()) {
            // 返回不可变空集合。
            return Collections.emptyList();
        }
        // 提取当前页用户ID集合，用于一次性查询用户租户关系。
        List<String> userIds = recordsDO.stream().map(AuthUser::getId).toList();
        // 批量查询用户租户关系，避免分页列表出现 N+1 查询。
        Map<String, List<String>> tenantIdsMap = listTenantIdsMap(userIds);
        // 逐条转换为响应对象，并复用批量查询出的租户ID集合。
        return recordsDO.stream().map(recordDO -> toVO(recordDO, tenantIdsMap.getOrDefault(recordDO.getId(), Collections.emptyList()))).toList();
    }

    /**
     * 转换用户分页结果。
     *
     * @param pageDO 用户实体分页
     * @return 用户响应分页
     */
    public Page<AuthUserVO> toPageVO(Page<AuthUser> pageDO) {
        // 分页为空时返回空分页。
        if (pageDO == null) {
            // 返回空分页对象。
            return new Page<>();
        }
        // 创建响应分页。
        Page<AuthUserVO> pageVO = new Page<>(pageDO.getCurrent(), pageDO.getSize(), pageDO.getTotal());
        // 设置响应记录。
        pageVO.setRecords(toListVO(pageDO.getRecords()));
        // 返回响应分页。
        return pageVO;
    }

    /**
     * 增强用户列表结果。
     *
     * @param records 用户响应列表
     * @return 用户响应列表
     */
    public List<AuthUserVO> assignment(List<AuthUserVO> records) {
        // 空列表统一返回空集合。
        return records == null ? Collections.emptyList() : records;
    }

    /**
     * 增强用户分页结果。
     *
     * @param page 用户响应分页
     * @return 用户响应分页
     */
    public Page<AuthUserVO> assignment(Page<AuthUserVO> page) {
        // 当前暂无额外补全，直接返回分页。
        return page;
    }
}
