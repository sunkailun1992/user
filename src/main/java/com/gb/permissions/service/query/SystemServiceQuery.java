package com.gb.permissions.service.query;

import com.gb.permissions.entity.System;
import com.gb.permissions.entity.query.SystemQuery;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;


/**
 * Created with IntelliJ IDEA.
 * @author:     	孙凯伦
 * @since:   	    2021-10-21 01:59:43
 * @description:	TODO  系统表,Service查询实现
 * @source:  	    代码生成器
 */
@Slf4j
@Setter(onMethod_ = {@Autowired})
public class SystemServiceQuery{


	/**
	 * 查询增强
     *
     * @author     	孙凯伦
	 * @since   	2021-10-21 01:59:43
	 * @param       systemQuery 系统表
	 * @return      QueryWrapper
	 */
    public static QueryWrapper query(SystemQuery systemQuery, QueryWrapper<System> queryWrapper) {
        /**
         * 排序
         */
        if(systemQuery.getCollation() != null && StringUtils.isNotBlank(systemQuery.getCollationFields())){
            if(systemQuery.getCollation()){
                queryWrapper.orderByAsc(systemQuery.getCollationFields());
            }else{
                queryWrapper.orderByDesc(systemQuery.getCollationFields());
            }
        }else{
            queryWrapper.orderByDesc(systemQuery.getCollationFields());
        }

        /**
        * 显示字段
        */
        if(StringUtils.isNotBlank(systemQuery.getFields())){
            queryWrapper.select(systemQuery.getFields());
        }
        return queryWrapper;
    }


}
