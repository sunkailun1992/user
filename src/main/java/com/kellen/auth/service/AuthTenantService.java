package com.kellen.auth.service;

import com.kellen.auth.entity.AuthTenant;
import com.kellen.auth.entity.bo.AuthTenantBO;

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
     * @return 租户列表
     */
    List<AuthTenant> list();

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
     * @param bo 租户删除参数
     * @return 是否成功
     */
    Boolean remove(AuthTenantBO bo);
}
