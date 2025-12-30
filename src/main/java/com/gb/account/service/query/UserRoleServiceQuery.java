package com.gb.account.service.query;

import com.gb.account.entity.UserRole;
import com.gb.account.entity.query.UserRoleQuery;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;


/**
 * Created with IntelliJ IDEA.
 * @author:     	孙凯伦
 * @since:   	    2021-10-21 01:50:37
 * @description:	TODO  用户角色表,Service查询实现
 * @source:  	    代码生成器
 */
@Slf4j
@Setter(onMethod_ = {@Autowired})
public class UserRoleServiceQuery{


	/**
	 * 查询增强
     *
     * @author     	孙凯伦
	 * @since   	2021-10-21 01:50:37
	 * @param       userRoleQuery 用户角色表
	 * @return      QueryWrapper
	 */
    public static QueryWrapper query(UserRoleQuery userRoleQuery, QueryWrapper<UserRole> queryWrapper) {
        /**
         * 排序
         */
        if(userRoleQuery.getCollation() != null && StringUtils.isNotBlank(userRoleQuery.getCollationFields())){
            if(userRoleQuery.getCollation()){
                queryWrapper.orderByAsc(userRoleQuery.getCollationFields());
            }else{
                queryWrapper.orderByDesc(userRoleQuery.getCollationFields());
            }
        }else{
            queryWrapper.orderByDesc(userRoleQuery.getCollationFields());
        }

        /**
        * 显示字段
        */
        if(StringUtils.isNotBlank(userRoleQuery.getFields())){
            queryWrapper.select(userRoleQuery.getFields());
        }
        return queryWrapper;
    }


}
