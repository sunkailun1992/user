package com.gb.account.service.query;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gb.account.entity.UserTypeValue;
import com.gb.account.entity.query.UserTypeValueQuery;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * Created with IntelliJ IDEA.
 * @author:     	孙凯伦
 * @since:   	    2021-11-02 02:50:23
 * @description:	TODO  用户类型值表,Service查询实现
 * @source:  	    代码生成器
 */
@Slf4j
@Setter(onMethod_ = {@Autowired})
public class UserTypeValueServiceQuery{


	/**
	 * 查询增强
     *
     * @author     	孙凯伦
	 * @since   	2021-11-02 02:50:23
	 * @param       userTypeValueQuery 用户类型值表
	 * @return      QueryWrapper
	 */
    public static QueryWrapper query(UserTypeValueQuery userTypeValueQuery, QueryWrapper<UserTypeValue> queryWrapper) {
        /**
         * 排序
         */
        if(userTypeValueQuery.getCollation() != null && StringUtils.isNotBlank(userTypeValueQuery.getCollationFields())){
            if(userTypeValueQuery.getCollation()){
                queryWrapper.orderByAsc(userTypeValueQuery.getCollationFields());
            }else{
                queryWrapper.orderByDesc(userTypeValueQuery.getCollationFields());
            }
        }else{
            queryWrapper.orderByDesc(userTypeValueQuery.getCollationFields());
        }

        /**
        * 显示字段
        */
        if(StringUtils.isNotBlank(userTypeValueQuery.getFields())){
            queryWrapper.select(userTypeValueQuery.getFields());
        }


        /**
         * 代码生成器，inSql查询
         */
        if(StringUtils.isNotBlank(userTypeValueQuery.getUserId())){
            queryWrapper.inSql("`id`", "select `user_type_value_id` from `user_type_value_relationship` where `is_delete` = 0 and `user_id` in (" + userTypeValueQuery.getUserId() + ")");
        }
        return queryWrapper;
    }


}
