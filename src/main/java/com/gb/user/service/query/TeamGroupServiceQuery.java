package com.gb.user.service.query;

import com.gb.user.entity.TeamGroup;
import com.gb.user.entity.query.TeamGroupQuery;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;


/**
 * TODO 团队组别,Service查询实现
 * 代码生成器
 *
 * @author sunx
 * @className TeamGroupServiceQuery
 * @time 2022-08-31 10:59:01
 */
@Slf4j
@Service
@Setter(onMethod_ = {@Autowired})
public class TeamGroupServiceQuery {


	/**
     * TODO 查询增强
     *
     * @param teamGroupQuery 团队组别
     * @return QueryWrapper
     * @author sunx
     * @methodName query
	 * @time 2022-08-31 10:59:01
	 */
    public QueryWrapper query(TeamGroupQuery teamGroupQuery, QueryWrapper<TeamGroup> queryWrapper) {
        /**
         * 排序
         */
        if (teamGroupQuery.getCollation() != null && StringUtils.isNotBlank(teamGroupQuery.getCollationFields())) {
            if (teamGroupQuery.getCollation()) {
                queryWrapper.orderByAsc(teamGroupQuery.getCollationFields());
            } else {
                queryWrapper.orderByDesc(teamGroupQuery.getCollationFields());
            }
        } else {
            queryWrapper.orderByDesc(teamGroupQuery.getCollationFields());
        }

        /**
         * 显示字段
         */
        if (StringUtils.isNotBlank(teamGroupQuery.getFields())) {
            queryWrapper.select(teamGroupQuery.getFields());
        }
        return queryWrapper;
    }
}
