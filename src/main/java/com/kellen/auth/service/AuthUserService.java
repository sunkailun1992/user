package com.kellen.auth.service;

import com.kellen.auth.entity.AuthUser;
import com.kellen.auth.entity.bo.AuthUserBO;

import java.util.List;

/**
 * 用户业务服务。
 *
 * @author sunkailun
 * @className AuthUserService
 * @time 2026/05/26
 */
public interface AuthUserService {

    /**
     * 查询用户列表。
     *
     * @param tenantId 租户ID
     * @return 用户列表
     */
    List<AuthUser> list(String tenantId);

    /**
     * 新增用户。
     *
     * @param bo 用户写入参数
     * @return 用户ID
     */
    String save(AuthUserBO bo);

    /**
     * 修改用户。
     *
     * @param bo 用户写入参数
     * @return 是否成功
     */
    Boolean update(AuthUserBO bo);

    /**
     * 删除用户。
     *
     * @param bo 用户删除参数
     * @return 是否成功
     */
    Boolean remove(AuthUserBO bo);
}
