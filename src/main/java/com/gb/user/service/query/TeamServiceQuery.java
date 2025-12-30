package com.gb.user.service.query;

import com.gb.user.entity.Team;
import com.gb.user.entity.query.TeamQuery;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;


/**
 * TODO 团队,Service查询实现
 * 代码生成器
 *
 * @author sunx
 * @className TeamServiceQuery
 * @time 2022-08-30 04:44:17
 */
@Slf4j
@Service
@Setter(onMethod_ = {@Autowired})
public class TeamServiceQuery {


	/**
     * TODO 查询增强
     *
     * @param teamQuery 团队
     * @return QueryWrapper
     * @author sunx
     * @methodName query
	 * @time 2022-08-30 04:44:17
	 */
    public QueryWrapper query(TeamQuery teamQuery, QueryWrapper<Team> queryWrapper) {
        /**
         * 排序
         */
        if (teamQuery.getCollation() != null && StringUtils.isNotBlank(teamQuery.getCollationFields())) {
            if (teamQuery.getCollation()) {
                queryWrapper.orderByAsc(teamQuery.getCollationFields());
            } else {
                queryWrapper.orderByDesc(teamQuery.getCollationFields());
            }
        } else {
            queryWrapper.orderByDesc(teamQuery.getCollationFields());
        }

        /**
         * 显示字段
         */
        if (StringUtils.isNotBlank(teamQuery.getFields())) {
            queryWrapper.select(teamQuery.getFields());
        }
        return queryWrapper;
    }
}
