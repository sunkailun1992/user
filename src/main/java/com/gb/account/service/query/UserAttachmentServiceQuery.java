package com.gb.account.service.query;

import com.gb.account.entity.UserAttachment;
import com.gb.account.entity.query.UserAttachmentQuery;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;


/**
 * TODO 用户附件,Service查询实现
 * 代码生成器
 *
 * @author lijh
 * @className UserAttachmentServiceQuery
 * @time 2022-04-14 10:04:04
 */
@Slf4j
@Service
@Setter(onMethod_ = {@Autowired})
public class UserAttachmentServiceQuery {


	/**
     * TODO 查询增强
     *
     * @param userAttachmentQuery 用户附件
     * @return QueryWrapper
     * @author lijh
     * @methodName query
	 * @time 2022-04-14 10:04:04
	 */
    public QueryWrapper query(UserAttachmentQuery userAttachmentQuery, QueryWrapper<UserAttachment> queryWrapper) {
        /**
         * 排序
         */
        if (userAttachmentQuery.getCollation() != null && StringUtils.isNotBlank(userAttachmentQuery.getCollationFields())) {
            if (userAttachmentQuery.getCollation()) {
                queryWrapper.orderByAsc(userAttachmentQuery.getCollationFields());
            } else {
                queryWrapper.orderByDesc(userAttachmentQuery.getCollationFields());
            }
        } else {
            queryWrapper.orderByDesc(userAttachmentQuery.getCollationFields());
        }

        /**
         * 显示字段
         */
        if (StringUtils.isNotBlank(userAttachmentQuery.getFields())) {
            queryWrapper.select(userAttachmentQuery.getFields());
        }
        return queryWrapper;
    }
}
