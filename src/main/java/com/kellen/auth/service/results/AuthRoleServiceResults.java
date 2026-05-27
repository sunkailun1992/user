package com.kellen.auth.service.results;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kellen.auth.entity.AuthRole;
import com.kellen.auth.entity.vo.AuthRoleVO;
import com.kellen.utils.GeneralConvertor;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

/**
 * 角色查询结果转换增强。
 *
 * @author sunkailun
 * @className AuthRoleServiceResults
 * @time 2026/05/27
 */
@Component
public class AuthRoleServiceResults {

    /**
     * 转换单条角色结果。
     *
     * @param recordDO 角色实体
     * @return 角色响应对象
     */
    public AuthRoleVO toVO(AuthRole recordDO) {
        // 实体为空时返回空响应。
        if (recordDO == null) {
            // 返回空值。
            return null;
        }
        // 转换基础同名字段。
        AuthRoleVO vo = GeneralConvertor.convertor(recordDO, AuthRoleVO.class);
        // 补充状态说明。
        vo.setStateDesc(recordDO.getState() == null ? null : recordDO.getState().getDesc());
        // 返回响应对象。
        return vo;
    }

    /**
     * 转换角色列表结果。
     *
     * @param recordsDO 角色实体列表
     * @return 角色响应列表
     */
    public List<AuthRoleVO> toListVO(List<AuthRole> recordsDO) {
        // 列表为空时返回空集合。
        if (recordsDO == null || recordsDO.isEmpty()) {
            // 返回不可变空集合。
            return Collections.emptyList();
        }
        // 逐条转换为响应对象。
        return recordsDO.stream().map(this::toVO).toList();
    }

    /**
     * 转换角色分页结果。
     *
     * @param pageDO 角色实体分页
     * @return 角色响应分页
     */
    public Page<AuthRoleVO> toPageVO(Page<AuthRole> pageDO) {
        // 分页为空时返回空分页。
        if (pageDO == null) {
            // 返回空分页对象。
            return new Page<>();
        }
        // 创建响应分页。
        Page<AuthRoleVO> pageVO = new Page<>(pageDO.getCurrent(), pageDO.getSize(), pageDO.getTotal());
        // 设置响应记录。
        pageVO.setRecords(toListVO(pageDO.getRecords()));
        // 返回响应分页。
        return pageVO;
    }

    /**
     * 增强角色列表结果。
     *
     * @param records 角色响应列表
     * @return 角色响应列表
     */
    public List<AuthRoleVO> assignment(List<AuthRoleVO> records) {
        // 空列表统一返回空集合。
        return records == null ? Collections.emptyList() : records;
    }

    /**
     * 增强角色分页结果。
     *
     * @param page 角色响应分页
     * @return 角色响应分页
     */
    public Page<AuthRoleVO> assignment(Page<AuthRoleVO> page) {
        // 当前暂无额外补全，直接返回分页。
        return page;
    }
}
