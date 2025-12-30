package com.gb.user.service.query;

import com.gb.user.entity.UserToken;
import com.gb.user.entity.query.UserTokenQuery;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;


/**
 * TODO 用户设备信息表,Service查询实现
 * 代码生成器
 *
 * @author wgs
 * @className UserTokenServiceQuery
 * @time 2022-01-20 03:40:09
 */
@Slf4j
@Service
@Setter(onMethod_ = {@Autowired})
public class UserTokenServiceQuery {


	/**
     * TODO 查询增强
     *
     * @param userTokenQuery 用户设备信息表
     * @return QueryWrapper
     * @author wgs
     * @methodName query
	 * @time 2022-01-20 03:40:09
	 */
    public QueryWrapper query(UserTokenQuery userTokenQuery, QueryWrapper<UserToken> queryWrapper) {
        /**
         * 排序
         */
        if (userTokenQuery.getCollation() != null && StringUtils.isNotBlank(userTokenQuery.getCollationFields())) {
            if (userTokenQuery.getCollation()) {
                queryWrapper.orderByAsc(userTokenQuery.getCollationFields());
            } else {
                queryWrapper.orderByDesc(userTokenQuery.getCollationFields());
            }
        } else {
            queryWrapper.orderByDesc(userTokenQuery.getCollationFields());
        }

        /**
         * 显示字段
         */
        if (StringUtils.isNotBlank(userTokenQuery.getFields())) {
            queryWrapper.select(userTokenQuery.getFields());
        }
        return queryWrapper;
    }
}
