package com.gb.account.service.query;

import com.gb.account.entity.UserOauths;
import com.gb.account.entity.query.UserOauthsQuery;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;


/**
 * Created with IntelliJ IDEA.
 * @author:     	孙凯伦
 * @since:   	    2021-10-21 01:50:37
 * @description:	TODO  用户授权表,Service查询实现
 * @source:  	    代码生成器
 */
@Slf4j
@Setter(onMethod_ = {@Autowired})
public class UserOauthsServiceQuery{


	/**
	 * 查询增强
     *
     * @author     	孙凯伦
	 * @since   	2021-10-21 01:50:37
	 * @param       userOauthsQuery 用户授权表
	 * @return      QueryWrapper
	 */
    public static QueryWrapper query(UserOauthsQuery userOauthsQuery, QueryWrapper<UserOauths> queryWrapper) {
        /**
         * 排序
         */
        if(userOauthsQuery.getCollation() != null && StringUtils.isNotBlank(userOauthsQuery.getCollationFields())){
            if(userOauthsQuery.getCollation()){
                queryWrapper.orderByAsc(userOauthsQuery.getCollationFields());
            }else{
                queryWrapper.orderByDesc(userOauthsQuery.getCollationFields());
            }
        }else{
            queryWrapper.orderByDesc(userOauthsQuery.getCollationFields());
        }

        /**
        * 显示字段
        */
        if(StringUtils.isNotBlank(userOauthsQuery.getFields())){
            queryWrapper.select(userOauthsQuery.getFields());
        }
        return queryWrapper;
    }


}
