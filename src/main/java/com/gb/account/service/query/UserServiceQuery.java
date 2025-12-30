package com.gb.account.service.query;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gb.account.entity.User;
import com.gb.account.entity.query.UserQuery;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * Created with IntelliJ IDEA.
 * @author:     	孙凯伦
 * @since:   	    2021-11-03 09:16:16
 * @description:	TODO  用户表,Service查询实现
 * @source:  	    代码生成器
 */
@Slf4j
@Setter(onMethod_ = {@Autowired})
public class UserServiceQuery{


	/**
	 * 查询增强
     *
     * @author     	孙凯伦
	 * @since   	2021-11-03 09:16:16
	 * @param       userQuery 用户表
	 * @return      QueryWrapper
	 */
    public static QueryWrapper query(UserQuery userQuery, QueryWrapper<User> queryWrapper) {
        /**
         * 排序
         */
        if(userQuery.getCollation() != null && StringUtils.isNotBlank(userQuery.getCollationFields())){
            if(userQuery.getCollation()){
                queryWrapper.orderByAsc(userQuery.getCollationFields());
            }else{
                queryWrapper.orderByDesc(userQuery.getCollationFields());
            }
        }else{
            queryWrapper.orderByDesc(userQuery.getCollationFields());
        }

        /**
        * 显示字段
        */
        if(StringUtils.isNotBlank(userQuery.getFields())){
            queryWrapper.select(userQuery.getFields());
        }


        /**
         * 代码生成器，inSql查询
         */
        if(StringUtils.isNotBlank(userQuery.getTypeValueCode())){
            queryWrapper.inSql("`id`", "select `user_id` from `user_type_value_relationship` where `is_delete` = 0 and `user_type_value_id` in (select `id` from `user_type_value` where `code` in (" + com.gb.utils.StringUtils.in(userQuery.getTypeValueCode()) + "))");
        }


        /**
         * 代码生成器，inSql查询
         */
        if(StringUtils.isNotBlank(userQuery.getTypeValueId())){
            queryWrapper.inSql("`id`", "select `user_id` from `user_type_value_relationship` where `is_delete` = 0 and `user_type_value_id` in (select `id` from `user_type_value` where `id` in (" + com.gb.utils.StringUtils.in(userQuery.getTypeValueId()) + "))");
        }


        /**
         * 代码生成器，模糊查询
         */
        if(StringUtils.isNotBlank(userQuery.getUserNameQuery())){
            queryWrapper.likeRight("`user_name`", userQuery.getUserNameQuery());
        }


        /**
         * 代码生成器，inSql查询
         */
        if(StringUtils.isNotBlank(userQuery.getNameQuery())){
            queryWrapper.inSql("`id`", "select `user_id` from `user_extends` where `name` like \""+userQuery.getNameQuery()+"%\"");
        }


        /**
         * 前端-财务模块，模糊查询
         */
        if(StringUtils.isNotBlank(userQuery.getMobileQuery())){
            queryWrapper.inSql("`id`", "select `user_id` from `user_extends` where `mobile` like \""+userQuery.getMobileQuery()+"%\"");
        }


        /**
         * 手机号查询
         */
        if(StringUtils.isNotBlank(userQuery.getMobile())){
            queryWrapper.inSql("`id`", "select `user_id` from `user_extends` where `mobile` = \""+userQuery.getMobile()+"\"");
        }
        return queryWrapper;
    }


}
