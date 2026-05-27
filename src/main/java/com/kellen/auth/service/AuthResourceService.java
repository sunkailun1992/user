package com.kellen.auth.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kellen.auth.entity.AuthResource;
import com.kellen.auth.entity.bo.AuthResourceBO;
import com.kellen.auth.entity.query.AuthResourceQuery;
import com.kellen.auth.entity.vo.AuthResourceVO;

import java.util.List;

/**
 * 权限资源业务服务。
 *
 * @author sunkailun
 * @className AuthResourceService
 * @time 2026/05/26
 */
public interface AuthResourceService {

    /**
     * 查询资源列表。
     *
     * @param page  分页对象
     * @param query 资源查询参数
     * @return 资源分页
     */
    Page<AuthResourceVO> page(Page<AuthResource> page, AuthResourceQuery query);

    /**
     * 查询资源列表。
     *
     * @param query 资源查询参数
     * @return 资源列表
     */
    List<AuthResourceVO> list(AuthResourceQuery query);

    /**
     * 新增资源。
     *
     * @param bo 资源写入参数
     * @return 资源ID
     */
    String save(AuthResourceBO bo);

    /**
     * 修改资源。
     *
     * @param bo 资源写入参数
     * @return 是否成功
     */
    Boolean update(AuthResourceBO bo);

    /**
     * 删除资源。
     *
     * @param bo 资源删除参数
     * @return 是否成功
     */
    Boolean remove(AuthResourceBO bo);
}
