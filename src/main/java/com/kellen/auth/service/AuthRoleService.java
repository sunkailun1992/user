package com.kellen.auth.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kellen.auth.entity.AuthRole;
import com.kellen.auth.entity.bo.AuthRoleBO;
import com.kellen.auth.entity.query.AuthRoleQuery;
import com.kellen.auth.entity.vo.AuthRoleVO;

import java.util.List;

/**
 * 角色业务服务。
 *
 * @author sunkailun
 * @className AuthRoleService
 * @time 2026/05/26
 */
public interface AuthRoleService {

    /**
     * 查询角色列表。
     *
     * @param page  分页对象
     * @param query 角色查询参数
     * @return 角色分页
     */
    Page<AuthRoleVO> page(Page<AuthRole> page, AuthRoleQuery query);

    /**
     * 查询角色列表。
     *
     * @param query 角色查询参数
     * @return 角色列表
     */
    List<AuthRoleVO> list(AuthRoleQuery query);

    /**
     * 新增角色。
     *
     * @param bo 角色写入参数
     * @return 角色ID
     */
    String save(AuthRoleBO bo);

    /**
     * 修改角色。
     *
     * @param bo 角色写入参数
     * @return 是否成功
     */
    Boolean update(AuthRoleBO bo);

    /**
     * 删除角色。
     *
     * @param bo 角色删除参数
     * @return 是否成功
     */
    Boolean remove(AuthRoleBO bo);
}
