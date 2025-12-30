package com.gb.user.service.query;

import com.gb.user.entity.InstitutionsExecutives;
import com.gb.user.entity.query.InstitutionsExecutivesQuery;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;


/**
 * TODO 机构高管,Service查询实现
 * 代码生成器
 *
 * @author sunxin
 * @className InstitutionsExecutivesServiceQuery
 * @time 2022-07-04 10:48:36
 */
@Slf4j
@Service
@Setter(onMethod_ = {@Autowired})
public class InstitutionsExecutivesServiceQuery {


	/**
     * TODO 查询增强
     *
     * @param institutionsExecutivesQuery 机构高管
     * @return QueryWrapper
     * @author sunxin
     * @methodName query
	 * @time 2022-07-04 10:48:36
	 */
    public QueryWrapper query(InstitutionsExecutivesQuery institutionsExecutivesQuery, QueryWrapper<InstitutionsExecutives> queryWrapper) {
        /**
         * 排序
         */
        if (institutionsExecutivesQuery.getCollation() != null && StringUtils.isNotBlank(institutionsExecutivesQuery.getCollationFields())) {
            if (institutionsExecutivesQuery.getCollation()) {
                queryWrapper.orderByAsc(institutionsExecutivesQuery.getCollationFields());
            } else {
                queryWrapper.orderByDesc(institutionsExecutivesQuery.getCollationFields());
            }
        } else {
            queryWrapper.orderByDesc(institutionsExecutivesQuery.getCollationFields());
        }

        /**
         * 显示字段
         */
        if (StringUtils.isNotBlank(institutionsExecutivesQuery.getFields())) {
            queryWrapper.select(institutionsExecutivesQuery.getFields());
        }
        return queryWrapper;
    }
}
