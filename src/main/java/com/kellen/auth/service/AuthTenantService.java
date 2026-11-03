package com.kellen.auth.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kellen.auth.entity.AuthTenant;
import com.kellen.auth.entity.bo.AuthTenantBO;
import com.kellen.auth.entity.query.AuthTenantQuery;
import com.kellen.auth.entity.vo.AuthTenantVO;

import java.util.List;

/**
 * 租户业务服务。
 *
 * @author sunkailun
 * @className AuthTenantService
 * @time 2026/05/26
 */
public interface AuthTenantService {

    /**
     * 查询租户列表。
     *
     * @param page  分页对象
     * @param query 租户查询参数
     * @return 租户分页
     */
    Page<AuthTenantVO> page(Page<AuthTenant> page, AuthTenantQuery query);

    /**
     * 查询租户列表。
     *
     * @param query 租户查询参数
     * @return 租户列表
     */
    List<AuthTenantVO> list(AuthTenantQuery query);

    /**
     * 新增租户。
     *
     * @param bo 租户写入参数
     * @return 租户ID
     */
    String save(AuthTenantBO bo);

    /**
     * 修改租户。
     *
     * @param bo 租户写入参数
     * @return 是否成功
     */
    Boolean update(AuthTenantBO bo);

    /**
     * 删除租户。
     *
     * @param id 租户主键
     * @return 是否成功
     */
    Boolean remove(String id);
}
