package com.gb.account.service.query;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gb.account.entity.SourceValue;
import com.gb.account.entity.query.SourceValueQuery;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * Created with IntelliJ IDEA.
 * @author:     	孙凯伦
 * @since:   	    2021-11-03 03:57:55
 * @description:	TODO  来源值,Service查询实现
 * @source:  	    代码生成器
 */
@Slf4j
@Service
@Setter(onMethod_ = {@Autowired})
public class SourceValueServiceQuery{


	/**
	 * 查询增强
     *
     * @author     	孙凯伦
	 * @since   	2021-11-03 03:57:55
	 * @param       sourceValueQuery 来源值
	 * @return      QueryWrapper
	 */
    public QueryWrapper query(SourceValueQuery sourceValueQuery, QueryWrapper<SourceValue> queryWrapper) {
        /**
         * 排序
         */
        if(sourceValueQuery.getCollation() != null && StringUtils.isNotBlank(sourceValueQuery.getCollationFields())){
            if(sourceValueQuery.getCollation()){
                queryWrapper.orderByAsc(sourceValueQuery.getCollationFields());
            }else{
                queryWrapper.orderByDesc(sourceValueQuery.getCollationFields());
            }
        }else{
            queryWrapper.orderByDesc(sourceValueQuery.getCollationFields());
        }

        /**
        * 显示字段
        */
        if(StringUtils.isNotBlank(sourceValueQuery.getFields())){
            queryWrapper.select(sourceValueQuery.getFields());
        }
        return queryWrapper;
    }


}
