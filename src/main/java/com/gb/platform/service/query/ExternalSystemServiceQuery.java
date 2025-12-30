package com.gb.platform.service.query;

import com.gb.platform.entity.ExternalSystem;
import com.gb.platform.entity.query.ExternalSystemQuery;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;


/**
 * TODO 外部系统,Service查询实现
 * 代码生成器
 *
 * @author sunx
 * @className ExternalSystemServiceQuery
 * @time 2022-12-16 03:10:08
 */
@Slf4j
@Service
@Setter(onMethod_ = {@Autowired})
public class ExternalSystemServiceQuery {


	/**
     * TODO 查询增强
     *
     * @param externalSystemQuery 外部系统
     * @return QueryWrapper
     * @author sunx
     * @methodName query
	 * @time 2022-12-16 03:10:08
	 */
    public QueryWrapper query(ExternalSystemQuery externalSystemQuery, QueryWrapper<ExternalSystem> queryWrapper) {
        /**
         * 排序
         */
        if (externalSystemQuery.getCollation() != null && StringUtils.isNotBlank(externalSystemQuery.getCollationFields())) {
            if (externalSystemQuery.getCollation()) {
                queryWrapper.orderByAsc(externalSystemQuery.getCollationFields());
            } else {
                queryWrapper.orderByDesc(externalSystemQuery.getCollationFields());
            }
        } else {
            queryWrapper.orderByDesc(externalSystemQuery.getCollationFields());
        }

        /**
         * 显示字段
         */
        if (StringUtils.isNotBlank(externalSystemQuery.getFields())) {
            queryWrapper.select(externalSystemQuery.getFields());
        }
        return queryWrapper;
    }
}
