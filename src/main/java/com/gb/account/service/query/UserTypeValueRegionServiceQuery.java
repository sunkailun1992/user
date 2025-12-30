package com.gb.account.service.query;

import com.gb.account.entity.UserTypeValueRegion;
import com.gb.account.entity.query.UserTypeValueRegionQuery;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;


/**
 * TODO 用户类型值地区,Service查询实现
 * 代码生成器
 *
 * @author lijh
 * @className UserTypeValueRegionServiceQuery
 * @time 2022-07-12 11:45:19
 */
@Slf4j
@Service
@Setter(onMethod_ = {@Autowired})
public class UserTypeValueRegionServiceQuery {


	/**
     * TODO 查询增强
     *
     * @param userTypeValueRegionQuery 用户类型值地区
     * @return QueryWrapper
     * @author lijh
     * @methodName query
	 * @time 2022-07-12 11:45:19
	 */
    public QueryWrapper query(UserTypeValueRegionQuery userTypeValueRegionQuery, QueryWrapper<UserTypeValueRegion> queryWrapper) {
        /**
         * 排序
         */
        if (userTypeValueRegionQuery.getCollation() != null && StringUtils.isNotBlank(userTypeValueRegionQuery.getCollationFields())) {
            if (userTypeValueRegionQuery.getCollation()) {
                queryWrapper.orderByAsc(userTypeValueRegionQuery.getCollationFields());
            } else {
                queryWrapper.orderByDesc(userTypeValueRegionQuery.getCollationFields());
            }
        } else {
            queryWrapper.orderByDesc(userTypeValueRegionQuery.getCollationFields());
        }

        /**
         * 显示字段
         */
        if (StringUtils.isNotBlank(userTypeValueRegionQuery.getFields())) {
            queryWrapper.select(userTypeValueRegionQuery.getFields());
        }
        return queryWrapper;
    }
}
