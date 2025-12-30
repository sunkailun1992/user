package com.gb.platform.service.query;

import com.gb.platform.entity.TransformationExternalPlatformSystemUser;
import com.gb.platform.entity.query.TransformationExternalPlatformSystemUserQuery;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;


/**
 * TODO 转化外部系统平台用户关联,Service查询实现
 * 代码生成器
 *
 * @author sunx
 * @className TransformationExternalPlatformSystemUserServiceQuery
 * @time 2022-12-16 03:10:09
 */
@Slf4j
@Service
@Setter(onMethod_ = {@Autowired})
public class TransformationExternalPlatformSystemUserServiceQuery {


	/**
     * TODO 查询增强
     *
     * @param transformationExternalPlatformSystemUserQuery 转化外部系统平台用户关联
     * @return QueryWrapper
     * @author sunx
     * @methodName query
	 * @time 2022-12-16 03:10:09
	 */
    public QueryWrapper query(TransformationExternalPlatformSystemUserQuery transformationExternalPlatformSystemUserQuery, QueryWrapper<TransformationExternalPlatformSystemUser> queryWrapper) {
        /**
         * 排序
         */
        if (transformationExternalPlatformSystemUserQuery.getCollation() != null && StringUtils.isNotBlank(transformationExternalPlatformSystemUserQuery.getCollationFields())) {
            if (transformationExternalPlatformSystemUserQuery.getCollation()) {
                queryWrapper.orderByAsc(transformationExternalPlatformSystemUserQuery.getCollationFields());
            } else {
                queryWrapper.orderByDesc(transformationExternalPlatformSystemUserQuery.getCollationFields());
            }
        } else {
            queryWrapper.orderByDesc(transformationExternalPlatformSystemUserQuery.getCollationFields());
        }

        /**
         * 显示字段
         */
        if (StringUtils.isNotBlank(transformationExternalPlatformSystemUserQuery.getFields())) {
            queryWrapper.select(transformationExternalPlatformSystemUserQuery.getFields());
        }
        return queryWrapper;
    }
}
