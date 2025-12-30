package com.gb.account.service.query;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gb.account.entity.Source;
import com.gb.account.entity.query.SourceQuery;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * Created with IntelliJ IDEA.
 * @author:     	孙凯伦
 * @since:   	    2021-11-03 03:57:55
 * @description:	TODO  来源,Service查询实现
 * @source:  	    代码生成器
 */
@Slf4j
@Service
@Setter(onMethod_ = {@Autowired})
public class SourceServiceQuery{


	/**
	 * 查询增强
     *
     * @author     	孙凯伦
	 * @since   	2021-11-03 03:57:55
	 * @param       sourceQuery 来源
	 * @return      QueryWrapper
	 */
    public QueryWrapper query(SourceQuery sourceQuery, QueryWrapper<Source> queryWrapper) {
        /**
         * 排序
         */
        if(sourceQuery.getCollation() != null && StringUtils.isNotBlank(sourceQuery.getCollationFields())){
            if(sourceQuery.getCollation()){
                queryWrapper.orderByAsc(sourceQuery.getCollationFields());
            }else{
                queryWrapper.orderByDesc(sourceQuery.getCollationFields());
            }
        }else{
            queryWrapper.orderByDesc(sourceQuery.getCollationFields());
        }

        /**
        * 显示字段
        */
        if(StringUtils.isNotBlank(sourceQuery.getFields())){
            queryWrapper.select(sourceQuery.getFields());
        }
        return queryWrapper;
    }


}
