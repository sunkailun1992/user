package com.gb.user.service.query;

import com.gb.user.entity.InstitutionsUser;
import com.gb.user.entity.query.InstitutionsUserQuery;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;


/**
 * TODO 机构用户关联,Service查询实现
 * 代码生成器
 *
 * @author sunxin
 * @className InstitutionsUserServiceQuery
 * @time 2022-07-04 10:48:37
 */
@Slf4j
@Service
@Setter(onMethod_ = {@Autowired})
public class InstitutionsUserServiceQuery {


	/**
     * TODO 查询增强
     *
     * @param institutionsUserQuery 机构用户关联
     * @return QueryWrapper
     * @author sunxin
     * @methodName query
	 * @time 2022-07-04 10:48:37
	 */
    public QueryWrapper query(InstitutionsUserQuery institutionsUserQuery, QueryWrapper<InstitutionsUser> queryWrapper) {
        /**
         * 排序
         */
        if (institutionsUserQuery.getCollation() != null && StringUtils.isNotBlank(institutionsUserQuery.getCollationFields())) {
            if (institutionsUserQuery.getCollation()) {
                queryWrapper.orderByAsc(institutionsUserQuery.getCollationFields());
            } else {
                queryWrapper.orderByDesc(institutionsUserQuery.getCollationFields());
            }
        } else {
            queryWrapper.orderByDesc(institutionsUserQuery.getCollationFields());
        }

        /**
         * 显示字段
         */
        if (StringUtils.isNotBlank(institutionsUserQuery.getFields())) {
            queryWrapper.select(institutionsUserQuery.getFields());
        }
        return queryWrapper;
    }
}
