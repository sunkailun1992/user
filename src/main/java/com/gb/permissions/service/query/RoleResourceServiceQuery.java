package com.gb.permissions.service.query;

import com.gb.permissions.entity.RoleResource;
import com.gb.permissions.entity.query.RoleResourceQuery;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;


/**
 * Created with IntelliJ IDEA.
 * @author:     	孙凯伦
 * @since:   	    2021-10-21 01:59:42
 * @description:	TODO  角色资源表,Service查询实现
 * @source:  	    代码生成器
 */
@Slf4j
@Setter(onMethod_ = {@Autowired})
public class RoleResourceServiceQuery{


	/**
	 * 查询增强
     *
     * @author     	孙凯伦
	 * @since   	2021-10-21 01:59:42
	 * @param       roleResourceQuery 角色资源表
	 * @return      QueryWrapper
	 */
    public static QueryWrapper query(RoleResourceQuery roleResourceQuery, QueryWrapper<RoleResource> queryWrapper) {
        /**
         * 排序
         */
        if(roleResourceQuery.getCollation() != null && StringUtils.isNotBlank(roleResourceQuery.getCollationFields())){
            if(roleResourceQuery.getCollation()){
                queryWrapper.orderByAsc(roleResourceQuery.getCollationFields());
            }else{
                queryWrapper.orderByDesc(roleResourceQuery.getCollationFields());
            }
        }else{
            queryWrapper.orderByDesc(roleResourceQuery.getCollationFields());
        }

        /**
        * 显示字段
        */
        if(StringUtils.isNotBlank(roleResourceQuery.getFields())){
            queryWrapper.select(roleResourceQuery.getFields());
        }
        return queryWrapper;
    }


}
