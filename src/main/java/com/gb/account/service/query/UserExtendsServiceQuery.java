package com.gb.account.service.query;

import com.gb.account.entity.UserExtends;
import com.gb.account.entity.query.UserExtendsQuery;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;


/**
 * Created with IntelliJ IDEA.
 * @author:     	孙凯伦
 * @since:   	    2021-10-21 01:50:40
 * @description:	TODO  用户扩展表,Service查询实现
 * @source:  	    代码生成器
 */
@Slf4j
@Setter(onMethod_ = {@Autowired})
public class UserExtendsServiceQuery{


	/**
	 * 查询增强
     *
     * @author     	孙凯伦
	 * @since   	2021-10-21 01:50:40
	 * @param       userExtendsQuery 用户扩展表
	 * @return      QueryWrapper
	 */
    public static QueryWrapper query(UserExtendsQuery userExtendsQuery, QueryWrapper<UserExtends> queryWrapper) {
        /**
         * 排序
         */
        if(userExtendsQuery.getCollation() != null && StringUtils.isNotBlank(userExtendsQuery.getCollationFields())){
            if(userExtendsQuery.getCollation()){
                queryWrapper.orderByAsc(userExtendsQuery.getCollationFields());
            }else{
                queryWrapper.orderByDesc(userExtendsQuery.getCollationFields());
            }
        }else{
            queryWrapper.orderByDesc(userExtendsQuery.getCollationFields());
        }

        /**
        * 显示字段
        */
        if(StringUtils.isNotBlank(userExtendsQuery.getFields())){
            queryWrapper.select(userExtendsQuery.getFields());
        }
        return queryWrapper;
    }


}
