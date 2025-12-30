package com.gb.user.service.query;

import com.gb.user.entity.TeamGroupValueLimit;
import com.gb.user.entity.query.TeamGroupValueLimitQuery;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;


/**
 * TODO 团队组别限制,Service查询实现
 * 代码生成器
 *
 * @author sunx
 * @className TeamGroupValueLimitServiceQuery
 * @time 2022-08-31 10:59:01
 */
@Slf4j
@Service
@Setter(onMethod_ = {@Autowired})
public class TeamGroupValueLimitServiceQuery {


	/**
     * TODO 查询增强
     *
     * @param teamGroupValueLimitQuery 团队组别限制
     * @return QueryWrapper
     * @author sunx
     * @methodName query
	 * @time 2022-08-31 10:59:01
	 */
    public QueryWrapper query(TeamGroupValueLimitQuery teamGroupValueLimitQuery, QueryWrapper<TeamGroupValueLimit> queryWrapper) {
        /**
         * 排序
         */
        if (teamGroupValueLimitQuery.getCollation() != null && StringUtils.isNotBlank(teamGroupValueLimitQuery.getCollationFields())) {
            if (teamGroupValueLimitQuery.getCollation()) {
                queryWrapper.orderByAsc(teamGroupValueLimitQuery.getCollationFields());
            } else {
                queryWrapper.orderByDesc(teamGroupValueLimitQuery.getCollationFields());
            }
        } else {
            queryWrapper.orderByDesc(teamGroupValueLimitQuery.getCollationFields());
        }

        /**
         * 显示字段
         */
        if (StringUtils.isNotBlank(teamGroupValueLimitQuery.getFields())) {
            queryWrapper.select(teamGroupValueLimitQuery.getFields());
        }
        return queryWrapper;
    }
}
