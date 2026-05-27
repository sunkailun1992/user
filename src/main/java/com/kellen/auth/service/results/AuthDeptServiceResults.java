package com.kellen.auth.service.results;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kellen.auth.entity.AuthDept;
import com.kellen.auth.entity.vo.AuthDeptVO;
import com.kellen.utils.convert.GeneralConvertor;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

/**
 * 部门查询结果转换增强。
 *
 * @author sunkailun
 * @className AuthDeptServiceResults
 * @time 2026/05/27
 */
@Component
public class AuthDeptServiceResults {

    /**
     * 转换单条部门结果。
     *
     * @param recordDO 部门实体
     * @return 部门响应对象
     */
    public AuthDeptVO toVO(AuthDept recordDO) {
        if (recordDO == null) {
            return null; // 实体为空时返回空响应。
        }
        AuthDeptVO vo = GeneralConvertor.convertor(recordDO, AuthDeptVO.class); // 转换基础同名字段。
        vo.setStateDesc(recordDO.getState() == null ? null : recordDO.getState().getDesc()); // 补充状态说明。
        return vo; // 返回响应对象。
    }

    /**
     * 转换部门列表结果。
     *
     * @param recordsDO 部门实体列表
     * @return 部门响应列表
     */
    public List<AuthDeptVO> toListVO(List<AuthDept> recordsDO) {
        if (recordsDO == null || recordsDO.isEmpty()) {
            return Collections.emptyList(); // 空列表统一返回空集合。
        }
        return recordsDO.stream().map(this::toVO).toList(); // 逐条转换为响应对象。
    }

    /**
     * 转换部门分页结果。
     *
     * @param pageDO 部门实体分页
     * @return 部门响应分页
     */
    public Page<AuthDeptVO> toPageVO(Page<AuthDept> pageDO) {
        if (pageDO == null) {
            return new Page<>(); // 分页为空时返回空分页对象。
        }
        Page<AuthDeptVO> pageVO = new Page<>(pageDO.getCurrent(), pageDO.getSize(), pageDO.getTotal()); // 创建响应分页。
        pageVO.setRecords(toListVO(pageDO.getRecords())); // 设置响应记录。
        return pageVO; // 返回响应分页。
    }
}
