package com.gb.account.service.query;

import com.gb.account.entity.UserType;
import com.gb.account.entity.query.UserTypeQuery;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;


/**
 * Created with IntelliJ IDEA.
 * @author:     	孙凯伦
 * @since:   	    2021-10-21 01:49:03
 * @description:	TODO  用户类型表,Service查询实现
 * @source:  	    代码生成器
 */
@Slf4j
@Setter(onMethod_ = {@Autowired})
public class UserTypeServiceQuery{


	/**
	 * 查询增强
     *
     * @author     	孙凯伦
	 * @since   	2021-10-21 01:49:03
	 * @param       userTypeQuery 用户类型表
	 * @return      QueryWrapper
	 */
    public static QueryWrapper query(UserTypeQuery userTypeQuery, QueryWrapper<UserType> queryWrapper) {
        /**
         * 排序
         */
        if(userTypeQuery.getCollation() != null && StringUtils.isNotBlank(userTypeQuery.getCollationFields())){
            if(userTypeQuery.getCollation()){
                queryWrapper.orderByAsc(userTypeQuery.getCollationFields());
            }else{
                queryWrapper.orderByDesc(userTypeQuery.getCollationFields());
            }
        }else{
            queryWrapper.orderByDesc(userTypeQuery.getCollationFields());
        }

        /**
        * 显示字段
        */
        if(StringUtils.isNotBlank(userTypeQuery.getFields())){
            queryWrapper.select(userTypeQuery.getFields());
        }
        return queryWrapper;
    }


}
