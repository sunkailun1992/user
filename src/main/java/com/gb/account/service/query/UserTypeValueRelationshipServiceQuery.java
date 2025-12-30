package com.gb.account.service.query;

import com.gb.account.entity.UserTypeValueRelationship;
import com.gb.account.entity.query.UserTypeValueRelationshipQuery;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;


/**
 * Created with IntelliJ IDEA.
 * @author:     	孙凯伦
 * @since:   	    2021-10-21 01:49:05
 * @description:	TODO  用户类型值关联,Service查询实现
 * @source:  	    代码生成器
 */
@Slf4j
@Setter(onMethod_ = {@Autowired})
public class UserTypeValueRelationshipServiceQuery{


	/**
	 * 查询增强
     *
     * @author     	孙凯伦
	 * @since   	2021-10-21 01:49:05
	 * @param       userTypeValueRelationshipQuery 用户类型值关联
	 * @return      QueryWrapper
	 */
    public static QueryWrapper query(UserTypeValueRelationshipQuery userTypeValueRelationshipQuery, QueryWrapper<UserTypeValueRelationship> queryWrapper) {
        /**
         * 排序
         */
        if(userTypeValueRelationshipQuery.getCollation() != null && StringUtils.isNotBlank(userTypeValueRelationshipQuery.getCollationFields())){
            if(userTypeValueRelationshipQuery.getCollation()){
                queryWrapper.orderByAsc(userTypeValueRelationshipQuery.getCollationFields());
            }else{
                queryWrapper.orderByDesc(userTypeValueRelationshipQuery.getCollationFields());
            }
        }else{
            queryWrapper.orderByDesc(userTypeValueRelationshipQuery.getCollationFields());
        }

        /**
        * 显示字段
        */
        if(StringUtils.isNotBlank(userTypeValueRelationshipQuery.getFields())){
            queryWrapper.select(userTypeValueRelationshipQuery.getFields());
        }
        return queryWrapper;
    }


}
