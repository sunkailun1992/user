package com.gb.permissions.service.query;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gb.permissions.entity.Role;
import com.gb.permissions.entity.query.RoleQuery;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * Created with IntelliJ IDEA.
 * @author:     	孙凯伦
 * @since:   	    2021-11-02 05:02:49
 * @description:	TODO  角色表,Service查询实现
 * @source:  	    代码生成器
 */
@Slf4j
@Setter(onMethod_ = {@Autowired})
public class RoleServiceQuery{


	/**
	 * 查询增强
     *
     * @author     	孙凯伦
	 * @since   	2021-11-02 05:02:49
	 * @param       roleQuery 角色表
	 * @return      QueryWrapper
	 */
    public static QueryWrapper query(RoleQuery roleQuery, QueryWrapper<Role> queryWrapper) {
        /**
         * 排序
         */
        if(roleQuery.getCollation() != null && StringUtils.isNotBlank(roleQuery.getCollationFields())){
            if(roleQuery.getCollation()){
                queryWrapper.orderByAsc(roleQuery.getCollationFields());
            }else{
                queryWrapper.orderByDesc(roleQuery.getCollationFields());
            }
        }else{
            queryWrapper.orderByDesc(roleQuery.getCollationFields());
        }

        /**
        * 显示字段
        */
        if(StringUtils.isNotBlank(roleQuery.getFields())){
            queryWrapper.select(roleQuery.getFields());
        }


        /**
         * 代码生成器，模糊查询
         */
        if(StringUtils.isNotBlank(roleQuery.getQuery())){
            queryWrapper.likeRight("`name`", roleQuery.getQuery());
        }


        /**
         * 代码生成器，inSql查询
         */
        if(StringUtils.isNotBlank(roleQuery.getUserId())){
            queryWrapper.inSql("`id`", "select `role_id` from `user_role` where `is_delete` = 0 and`user_id` in(" + roleQuery.getUserId() + ")");
        }
        return queryWrapper;
    }


}
