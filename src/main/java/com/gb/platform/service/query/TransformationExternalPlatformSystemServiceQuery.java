package com.gb.platform.service.query;

import com.gb.platform.entity.TransformationExternalPlatformSystem;
import com.gb.platform.entity.query.TransformationExternalPlatformSystemQuery;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;


/**
 * TODO 转化外部系统平台,Service查询实现
 * 代码生成器
 *
 * @author sunx
 * @className TransformationExternalPlatformSystemServiceQuery
 * @time 2022-12-16 03:10:08
 */
@Slf4j
@Service
@Setter(onMethod_ = {@Autowired})
public class TransformationExternalPlatformSystemServiceQuery {


	/**
     * TODO 查询增强
     *
     * @param transformationExternalPlatformSystemQuery 转化外部系统平台
     * @return QueryWrapper
     * @author sunx
     * @methodName query
	 * @time 2022-12-16 03:10:08
	 */
    public QueryWrapper query(TransformationExternalPlatformSystemQuery transformationExternalPlatformSystemQuery, QueryWrapper<TransformationExternalPlatformSystem> queryWrapper) {
        /**
         * 排序
         */
        if (transformationExternalPlatformSystemQuery.getCollation() != null && StringUtils.isNotBlank(transformationExternalPlatformSystemQuery.getCollationFields())) {
            if (transformationExternalPlatformSystemQuery.getCollation()) {
                queryWrapper.orderByAsc(transformationExternalPlatformSystemQuery.getCollationFields());
            } else {
                queryWrapper.orderByDesc(transformationExternalPlatformSystemQuery.getCollationFields());
            }
        } else {
            queryWrapper.orderByDesc(transformationExternalPlatformSystemQuery.getCollationFields());
        }

        /**
         * 显示字段
         */
        if (StringUtils.isNotBlank(transformationExternalPlatformSystemQuery.getFields())) {
            queryWrapper.select(transformationExternalPlatformSystemQuery.getFields());
        }
        return queryWrapper;
    }
}
