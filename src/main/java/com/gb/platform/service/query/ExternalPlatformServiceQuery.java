package com.gb.platform.service.query;

import com.gb.platform.entity.ExternalPlatform;
import com.gb.platform.entity.query.ExternalPlatformQuery;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;


/**
 * TODO 外部平台,Service查询实现
 * 代码生成器
 *
 * @author sunx
 * @className ExternalPlatformServiceQuery
 * @time 2022-12-16 03:10:07
 */
@Slf4j
@Service
@Setter(onMethod_ = {@Autowired})
public class ExternalPlatformServiceQuery {


	/**
     * TODO 查询增强
     *
     * @param externalPlatformQuery 外部平台
     * @return QueryWrapper
     * @author sunx
     * @methodName query
	 * @time 2022-12-16 03:10:07
	 */
    public QueryWrapper query(ExternalPlatformQuery externalPlatformQuery, QueryWrapper<ExternalPlatform> queryWrapper) {
        /**
         * 排序
         */
        if (externalPlatformQuery.getCollation() != null && StringUtils.isNotBlank(externalPlatformQuery.getCollationFields())) {
            if (externalPlatformQuery.getCollation()) {
                queryWrapper.orderByAsc(externalPlatformQuery.getCollationFields());
            } else {
                queryWrapper.orderByDesc(externalPlatformQuery.getCollationFields());
            }
        } else {
            queryWrapper.orderByDesc(externalPlatformQuery.getCollationFields());
        }

        /**
         * 显示字段
         */
        if (StringUtils.isNotBlank(externalPlatformQuery.getFields())) {
            queryWrapper.select(externalPlatformQuery.getFields());
        }
        return queryWrapper;
    }
}
