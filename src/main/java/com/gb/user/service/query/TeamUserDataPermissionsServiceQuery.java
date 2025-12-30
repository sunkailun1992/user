package com.gb.user.service.query;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gb.user.entity.TeamUserDataPermissions;
import com.gb.user.entity.query.TeamUserDataPermissionsQuery;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;


/**
 * TODO 团队人员数据权限,Service查询实现
 * 代码生成器
 *
 * @author sunx
 * @className TeamUserDataPermissionsServiceQuery
 * @time 2022-08-30 04:44:18
 */
@Slf4j
@Service
@Setter(onMethod_ = {@Autowired})
public class TeamUserDataPermissionsServiceQuery {


	/**
     * TODO 查询增强
     *
     * @param teamUserDataPermissionsQuery 团队人员数据权限
     * @return QueryWrapper
     * @author sunx
     * @methodName query
	 * @time 2022-08-30 04:44:18
	 */
    public QueryWrapper query(TeamUserDataPermissionsQuery teamUserDataPermissionsQuery, QueryWrapper<TeamUserDataPermissions> queryWrapper) {
        /**
         * 排序
         */
        if(StringUtils.isNotBlank(teamUserDataPermissionsQuery.getCollationFields())) {
            if (Objects.nonNull(teamUserDataPermissionsQuery.getCollation()) && teamUserDataPermissionsQuery.getCollation()) {
                queryWrapper.orderByAsc(teamUserDataPermissionsQuery.getCollationFields());
            }
            queryWrapper.orderByDesc(teamUserDataPermissionsQuery.getCollationFields());
        }

        /**
         * 显示字段
         */
        if (StringUtils.isNotBlank(teamUserDataPermissionsQuery.getFields())) {
            queryWrapper.select(teamUserDataPermissionsQuery.getFields());
        }
        return queryWrapper;
    }
}
