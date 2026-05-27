package com.kellen.auth.service.results;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kellen.auth.entity.AuthTenant;
import com.kellen.auth.entity.vo.AuthTenantVO;
import com.kellen.utils.convert.GeneralConvertor;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

/**
 * 租户查询结果转换增强。
 *
 * @author sunkailun
 * @className AuthTenantServiceResults
 * @time 2026/05/27
 */
@Component
public class AuthTenantServiceResults {

    /**
     * 转换单条租户结果。
     *
     * @param recordDO 租户实体
     * @return 租户响应对象
     * @author sunkailun
     * @DateTime 2026/05/27
     * @email 376253703@qq.com
     */
    public AuthTenantVO toVO(AuthTenant recordDO) {
        // 实体为空时返回空响应。
        if (recordDO == null) {
            // 返回空值。
            return null;
        }
        // 转换基础同名字段。
        AuthTenantVO vo = GeneralConvertor.convertor(recordDO, AuthTenantVO.class);
        // 补充状态说明。
        vo.setStateDesc(recordDO.getState() == null ? null : recordDO.getState().getDesc());
        // 返回响应对象。
        return vo;
    }

    /**
     * 转换租户列表结果。
     *
     * @param recordsDO 租户实体列表
     * @return 租户响应列表
     * @author sunkailun
     * @DateTime 2026/05/27
     * @email 376253703@qq.com
     */
    public List<AuthTenantVO> toListVO(List<AuthTenant> recordsDO) {
        // 列表为空时返回空集合。
        if (recordsDO == null || recordsDO.isEmpty()) {
            // 返回不可变空集合。
            return Collections.emptyList();
        }
        // 逐条转换为响应对象。
        return recordsDO.stream().map(this::toVO).toList();
    }

    /**
     * 转换租户分页结果。
     *
     * @param pageDO 租户实体分页
     * @return 租户响应分页
     * @author sunkailun
     * @DateTime 2026/05/27
     * @email 376253703@qq.com
     */
    public Page<AuthTenantVO> toPageVO(Page<AuthTenant> pageDO) {
        // 分页为空时返回空分页。
        if (pageDO == null) {
            // 返回空分页对象。
            return new Page<>();
        }
        // 创建响应分页。
        Page<AuthTenantVO> pageVO = new Page<>(pageDO.getCurrent(), pageDO.getSize(), pageDO.getTotal());
        // 设置响应记录。
        pageVO.setRecords(toListVO(pageDO.getRecords()));
        // 返回响应分页。
        return pageVO;
    }

    /**
     * 增强租户列表结果。
     *
     * @param records 租户响应列表
     * @return 租户响应列表
     */
    public List<AuthTenantVO> assignment(List<AuthTenantVO> records) {
        // 空列表统一返回空集合。
        return records == null ? Collections.emptyList() : records;
    }

    /**
     * 增强租户分页结果。
     *
     * @param page 租户响应分页
     * @return 租户响应分页
     */
    public Page<AuthTenantVO> assignment(Page<AuthTenantVO> page) {
        // 当前暂无额外补全，直接返回分页。
        return page;
    }
}
