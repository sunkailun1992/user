package com.gb.user.service.query;

import com.gb.user.entity.UserNotSpu;
import com.gb.user.entity.query.UserNotSpuQuery;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;


/**
 * TODO 企业渠道用户排除产品,Service查询实现
 * 代码生成器
 *
 * @author 孙凯伦
 * @className UserNotSpuServiceQuery
 * @time 2023-07-07 04:36:59
 */
@Slf4j
@Service
@Setter(onMethod_ = {@Autowired})
public class UserNotSpuServiceQuery {


	/**
     * TODO 查询增强
     *
     * @param userNotSpuQuery 企业渠道用户排除产品
     * @return QueryWrapper
     * @author 孙凯伦
     * @methodName query
	 * @time 2023-07-07 04:36:59
	 */
    public QueryWrapper query(UserNotSpuQuery userNotSpuQuery, QueryWrapper<UserNotSpu> queryWrapper) {
        /**
         * 排序
         */
        if (userNotSpuQuery.getCollation() != null && StringUtils.isNotBlank(userNotSpuQuery.getCollationFields())) {
            if (userNotSpuQuery.getCollation()) {
                queryWrapper.orderByAsc(userNotSpuQuery.getCollationFields());
            } else {
                queryWrapper.orderByDesc(userNotSpuQuery.getCollationFields());
            }
        } else {
            queryWrapper.orderByDesc(userNotSpuQuery.getCollationFields());
        }

        /**
         * 显示字段
         */
        if (StringUtils.isNotBlank(userNotSpuQuery.getFields())) {
            queryWrapper.select(userNotSpuQuery.getFields());
        }
        return queryWrapper;
    }
}
