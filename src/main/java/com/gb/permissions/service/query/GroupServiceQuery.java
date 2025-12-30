package com.gb.permissions.service.query;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gb.permissions.entity.Group;
import com.gb.permissions.entity.query.GroupQuery;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * Created with IntelliJ IDEA.
 * @author:     	孙凯伦
 * @since:   	    2021-11-02 04:34:51
 * @description:	TODO  组,Service查询实现
 * @source:  	    代码生成器
 */
@Slf4j
@Setter(onMethod_ = {@Autowired})
public class GroupServiceQuery{


	/**
	 * 查询增强
     *
     * @author     	孙凯伦
	 * @since   	2021-11-02 04:34:51
	 * @param       groupQuery 组
	 * @return      QueryWrapper
	 */
    public static QueryWrapper query(GroupQuery groupQuery, QueryWrapper<Group> queryWrapper) {
        /**
         * 排序
         */
        if(groupQuery.getCollation() != null && StringUtils.isNotBlank(groupQuery.getCollationFields())){
            if(groupQuery.getCollation()){
                queryWrapper.orderByAsc(groupQuery.getCollationFields());
            }else{
                queryWrapper.orderByDesc(groupQuery.getCollationFields());
            }
        }else{
            queryWrapper.orderByDesc(groupQuery.getCollationFields());
        }

        /**
        * 显示字段
        */
        if(StringUtils.isNotBlank(groupQuery.getFields())){
            queryWrapper.select(groupQuery.getFields());
        }


        /**
         * 代码生成器，模糊查询
         */
        if(StringUtils.isNotBlank(groupQuery.getQuery())){
            queryWrapper.likeRight("`name`", groupQuery.getQuery());
        }


        /**
         * 代码生成器，inSql查询
         */
        if(StringUtils.isNotBlank(groupQuery.getUserId())){
            queryWrapper.inSql("`id`", "select `group_id` from `user_group` where `is_delete` = 0 and `user_id` in(" + groupQuery.getUserId() + ")");
        }
        return queryWrapper;
    }


}
