package com.gb.user.service.query;

import com.gb.user.entity.TeamUser;
import com.gb.user.entity.query.TeamUserQuery;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;


/**
 * TODO 团队人员,Service查询实现
 * 代码生成器
 *
 * @author sunx
 * @className TeamUserServiceQuery
 * @time 2022-08-31 11:01:59
 */
@Slf4j
@Service
@Setter(onMethod_ = {@Autowired})
public class TeamUserServiceQuery {


	/**
     * TODO 查询增强
     *
     * @param teamUserQuery 团队人员
     * @return QueryWrapper
     * @author sunx
     * @methodName query
	 * @time 2022-08-31 11:01:59
	 */
    public QueryWrapper query(TeamUserQuery teamUserQuery, QueryWrapper<TeamUser> queryWrapper) {
        /**
         * 排序
         */
        if (teamUserQuery.getCollation() != null && StringUtils.isNotBlank(teamUserQuery.getCollationFields())) {
            if (teamUserQuery.getCollation()) {
                queryWrapper.orderByAsc(teamUserQuery.getCollationFields());
            } else {
                queryWrapper.orderByDesc(teamUserQuery.getCollationFields());
            }
        } else {
            queryWrapper.orderByDesc(teamUserQuery.getCollationFields());
        }

        /**
         * 显示字段
         */
        if (StringUtils.isNotBlank(teamUserQuery.getFields())) {
            queryWrapper.select(teamUserQuery.getFields());
        }
        return queryWrapper;
    }
}
