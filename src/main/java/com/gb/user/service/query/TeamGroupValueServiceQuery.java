package com.gb.user.service.query;

import com.gb.user.entity.TeamGroupValue;
import com.gb.user.entity.query.TeamGroupValueQuery;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;


/**
 * TODO 团队组别值,Service查询实现
 * 代码生成器
 *
 * @author sunx
 * @className TeamGroupValueServiceQuery
 * @time 2022-08-31 10:59:01
 */
@Slf4j
@Service
@Setter(onMethod_ = {@Autowired})
public class TeamGroupValueServiceQuery {


	/**
     * TODO 查询增强
     *
     * @param teamGroupValueQuery 团队组别值
     * @return QueryWrapper
     * @author sunx
     * @methodName query
	 * @time 2022-08-31 10:59:01
	 */
    public QueryWrapper query(TeamGroupValueQuery teamGroupValueQuery, QueryWrapper<TeamGroupValue> queryWrapper) {
        /**
         * 排序
         */
        if (teamGroupValueQuery.getCollation() != null && StringUtils.isNotBlank(teamGroupValueQuery.getCollationFields())) {
            if (teamGroupValueQuery.getCollation()) {
                queryWrapper.orderByAsc(teamGroupValueQuery.getCollationFields());
            } else {
                queryWrapper.orderByDesc(teamGroupValueQuery.getCollationFields());
            }
        } else {
            queryWrapper.orderByDesc(teamGroupValueQuery.getCollationFields());
        }

        /**
         * 显示字段
         */
        if (StringUtils.isNotBlank(teamGroupValueQuery.getFields())) {
            queryWrapper.select(teamGroupValueQuery.getFields());
        }
        return queryWrapper;
    }
}
