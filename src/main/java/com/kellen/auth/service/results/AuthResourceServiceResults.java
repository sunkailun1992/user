package com.kellen.auth.service.results;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kellen.auth.entity.AuthResource;
import com.kellen.auth.entity.vo.AuthResourceVO;
import com.kellen.utils.convert.GeneralConvertor;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

/**
 * 权限资源查询结果转换增强。
 *
 * @author sunkailun
 * @className AuthResourceServiceResults
 * @time 2026/05/27
 */
@Component
public class AuthResourceServiceResults {

    /**
     * 转换单条权限资源结果。
     *
     * @param recordDO 权限资源实体
     * @return 权限资源响应对象
     */
    public AuthResourceVO toVO(AuthResource recordDO) {
        // 实体为空时返回空响应。
        if (recordDO == null) {
            // 返回空值。
            return null;
        }
        // 转换基础同名字段。
        AuthResourceVO vo = GeneralConvertor.convertor(recordDO, AuthResourceVO.class);
        // 设置资源分类值。
        vo.setCategory(recordDO.getResourceCategory() == null ? null : recordDO.getResourceCategory().getValue());
        // 设置资源分类说明。
        vo.setCategoryDesc(recordDO.getResourceCategory() == null ? null : recordDO.getResourceCategory().getDesc());
        // 补充状态说明。
        vo.setStateDesc(recordDO.getState() == null ? null : recordDO.getState().getDesc());
        // 返回响应对象。
        return vo;
    }

    /**
     * 转换权限资源列表结果。
     *
     * @param recordsDO 权限资源实体列表
     * @return 权限资源响应列表
     */
    public List<AuthResourceVO> toListVO(List<AuthResource> recordsDO) {
        // 列表为空时返回空集合。
        if (recordsDO == null || recordsDO.isEmpty()) {
            // 返回不可变空集合。
            return Collections.emptyList();
        }
        // 逐条转换为响应对象。
        return recordsDO.stream().map(this::toVO).toList();
    }

    /**
     * 转换权限资源分页结果。
     *
     * @param pageDO 权限资源实体分页
     * @return 权限资源响应分页
     */
    public Page<AuthResourceVO> toPageVO(Page<AuthResource> pageDO) {
        // 分页为空时返回空分页。
        if (pageDO == null) {
            // 返回空分页对象。
            return new Page<>();
        }
        // 创建响应分页。
        Page<AuthResourceVO> pageVO = new Page<>(pageDO.getCurrent(), pageDO.getSize(), pageDO.getTotal());
        // 设置响应记录。
        pageVO.setRecords(toListVO(pageDO.getRecords()));
        // 返回响应分页。
        return pageVO;
    }

    /**
     * 增强权限资源列表结果。
     *
     * @param records 权限资源响应列表
     * @return 权限资源响应列表
     */
    public List<AuthResourceVO> assignment(List<AuthResourceVO> records) {
        // 空列表统一返回空集合。
        return records == null ? Collections.emptyList() : records;
    }

    /**
     * 增强权限资源分页结果。
     *
     * @param page 权限资源响应分页
     * @return 权限资源响应分页
     */
    public Page<AuthResourceVO> assignment(Page<AuthResourceVO> page) {
        // 当前暂无额外补全，直接返回分页。
        return page;
    }
}
