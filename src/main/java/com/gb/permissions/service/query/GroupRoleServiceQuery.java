package com.gb.permissions.service.query;

import com.gb.permissions.entity.GroupRole;
import com.gb.permissions.entity.query.GroupRoleQuery;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;


/**
 * Created with IntelliJ IDEA.
 * @author:     	孙凯伦
 * @since:   	    2021-10-21 01:59:44
 * @description:	TODO  角色用户组,Service查询实现
 * @source:  	    代码生成器
 */
@Slf4j
@Setter(onMethod_ = {@Autowired})
public class GroupRoleServiceQuery{


	/**
	 * 查询增强
     *
     * @author     	孙凯伦
	 * @since   	2021-10-21 01:59:44
	 * @param       groupRoleQuery 角色用户组
	 * @return      QueryWrapper
	 */
    public static QueryWrapper query(GroupRoleQuery groupRoleQuery, QueryWrapper<GroupRole> queryWrapper) {
        /**
         * 排序
         */
        if(groupRoleQuery.getCollation() != null && StringUtils.isNotBlank(groupRoleQuery.getCollationFields())){
            if(groupRoleQuery.getCollation()){
                queryWrapper.orderByAsc(groupRoleQuery.getCollationFields());
            }else{
                queryWrapper.orderByDesc(groupRoleQuery.getCollationFields());
            }
        }else{
            queryWrapper.orderByDesc(groupRoleQuery.getCollationFields());
        }

        /**
        * 显示字段
        */
        if(StringUtils.isNotBlank(groupRoleQuery.getFields())){
            queryWrapper.select(groupRoleQuery.getFields());
        }
        return queryWrapper;
    }


}
