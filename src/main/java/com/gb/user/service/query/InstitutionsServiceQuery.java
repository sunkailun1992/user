package com.gb.user.service.query;

import com.gb.user.entity.Institutions;
import com.gb.user.entity.query.InstitutionsQuery;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;


/**
 * TODO 机构,Service查询实现
 * 代码生成器
 *
 * @author sunxin
 * @className InstitutionsServiceQuery
 * @time 2022-07-04 10:48:36
 */
@Slf4j
@Service
@Setter(onMethod_ = {@Autowired})
public class InstitutionsServiceQuery {


	/**
     * TODO 查询增强
     *
     * @param institutionsQuery 机构
     * @return QueryWrapper
     * @author sunxin
     * @methodName query
	 * @time 2022-07-04 10:48:36
	 */
    public QueryWrapper query(InstitutionsQuery institutionsQuery, QueryWrapper<Institutions> queryWrapper) {
        /**
         * 排序
         */
        if (institutionsQuery.getCollation() != null && StringUtils.isNotBlank(institutionsQuery.getCollationFields())) {
            if (institutionsQuery.getCollation()) {
                queryWrapper.orderByAsc(institutionsQuery.getCollationFields());
            } else {
                queryWrapper.orderByDesc(institutionsQuery.getCollationFields());
            }
        } else {
            queryWrapper.orderByDesc(institutionsQuery.getCollationFields());
        }

        /**
         * 显示字段
         */
        if (StringUtils.isNotBlank(institutionsQuery.getFields())) {
            queryWrapper.select(institutionsQuery.getFields());
        }
        return queryWrapper;
    }
}
