package com.kellen.auth.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kellen.auth.entity.AuthDept;
import com.kellen.auth.entity.bo.AuthDeptBO;
import com.kellen.auth.entity.query.AuthDeptQuery;
import com.kellen.auth.entity.vo.AuthDeptVO;

import java.util.List;

/**
 * 部门业务服务。
 *
 * @author sunkailun
 * @className AuthDeptService
 * @time 2026/05/27
 */
public interface AuthDeptService {

    /**
     * 分页查询部门。
     *
     * @param page  分页对象
     * @param query 部门查询参数
     * @return 部门分页
     */
    Page<AuthDeptVO> page(Page<AuthDept> page, AuthDeptQuery query);

    /**
     * 查询部门列表。
     *
     * @param query 部门查询参数
     * @return 部门列表
     */
    List<AuthDeptVO> list(AuthDeptQuery query);

    /**
     * 新增部门。
     *
     * @param bo 部门写入参数
     * @return 部门ID
     */
    String save(AuthDeptBO bo);

    /**
     * 修改部门。
     *
     * @param bo 部门写入参数
     * @return 是否成功
     */
    Boolean update(AuthDeptBO bo);

    /**
     * 删除部门。
     *
     * @param tenantId 租户ID
     * @param id       部门主键
     * @return 是否成功
     */
    Boolean remove(String tenantId, String id);
}
