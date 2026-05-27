package com.kellen.auth.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kellen.auth.entity.AuthUser;
import com.kellen.auth.entity.bo.AuthUserBO;
import com.kellen.auth.entity.query.AuthUserQuery;
import com.kellen.auth.entity.vo.AuthUserVO;

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
     * @param page  分页对象
     * @param query 用户查询参数
     * @return 用户分页
     */
    Page<AuthUserVO> page(Page<AuthUser> page, AuthUserQuery query);

    /**
     * 查询用户列表。
     *
     * @param query 用户查询参数
     * @return 用户列表
     */
    List<AuthUserVO> list(AuthUserQuery query);

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
