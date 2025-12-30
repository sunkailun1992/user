package com.gb.permissions.service.query;

import com.gb.permissions.entity.Resource;
import com.gb.permissions.entity.query.ResourceQuery;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;


/**
 * Created with IntelliJ IDEA.
 * @author:     	孙凯伦
 * @since:   	    2021-10-25 05:43:33
 * @description:	TODO  资源表,Service查询实现
 * @source:  	    代码生成器
 */
@Slf4j
@Setter(onMethod_ = {@Autowired})
public class ResourceServiceQuery{


	/**
	 * 查询增强
     *
     * @author     	孙凯伦
	 * @since   	2021-10-25 05:43:33
	 * @param       resourceQuery 资源表
	 * @return      QueryWrapper
	 */
    public static QueryWrapper query(ResourceQuery resourceQuery, QueryWrapper<Resource> queryWrapper) {
        /**
         * 排序
         */
        if(resourceQuery.getCollation() != null && StringUtils.isNotBlank(resourceQuery.getCollationFields())){
            if(resourceQuery.getCollation()){
                queryWrapper.orderByAsc(resourceQuery.getCollationFields());
            }else{
                queryWrapper.orderByDesc(resourceQuery.getCollationFields());
            }
        }else{
            queryWrapper.orderByDesc(resourceQuery.getCollationFields());
        }

        /**
        * 显示字段
        */
        if(StringUtils.isNotBlank(resourceQuery.getFields())){
            queryWrapper.select(resourceQuery.getFields());
        }


        /**
         * 代码生成器，inSql查询
         */
        if(StringUtils.isNotBlank(resourceQuery.getRoleId())){
            queryWrapper.inSql("`id`", "select `resource_id` from `role_resource` where `role_id` = "+resourceQuery.getRoleId()+" and `is_delete` = false");
        }

        return queryWrapper;
    }


}
