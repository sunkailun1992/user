package com.gb.account.service.query;

import com.gb.account.entity.UserGroup;
import com.gb.account.entity.query.UserGroupQuery;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;


/**
 * Created with IntelliJ IDEA.
 * @author:     	孙凯伦
 * @since:   	    2021-10-21 01:50:39
 * @description:	TODO  用户组,Service查询实现
 * @source:  	    代码生成器
 */
@Slf4j
@Setter(onMethod_ = {@Autowired})
public class UserGroupServiceQuery{


	/**
	 * 查询增强
     *
     * @author     	孙凯伦
	 * @since   	2021-10-21 01:50:39
	 * @param       userGroupQuery 用户组
	 * @return      QueryWrapper
	 */
    public static QueryWrapper query(UserGroupQuery userGroupQuery, QueryWrapper<UserGroup> queryWrapper) {
        /**
         * 排序
         */
        if(userGroupQuery.getCollation() != null && StringUtils.isNotBlank(userGroupQuery.getCollationFields())){
            if(userGroupQuery.getCollation()){
                queryWrapper.orderByAsc(userGroupQuery.getCollationFields());
            }else{
                queryWrapper.orderByDesc(userGroupQuery.getCollationFields());
            }
        }else{
            queryWrapper.orderByDesc(userGroupQuery.getCollationFields());
        }

        /**
        * 显示字段
        */
        if(StringUtils.isNotBlank(userGroupQuery.getFields())){
            queryWrapper.select(userGroupQuery.getFields());
        }
        return queryWrapper;
    }


}
