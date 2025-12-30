package com.gb.user.service.impl;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.LocalDateTimeUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gb.user.entity.UserMessage;
import com.gb.user.mapper.UserMessageMapper;
import com.gb.user.service.UserMessageService;
import com.gb.utils.exception.BusinessException;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * 用户消息表 服务实现类
 * </p>
 *
 * @author ranyang
 * @since 2021-06-01
 */
@Slf4j
@Service
@Setter(onMethod_ = {@Autowired})
public class UserMessageServiceImpl extends ServiceImpl<UserMessageMapper, UserMessage> implements UserMessageService {


    /**
     * 用户消息表
     */
    private UserMessageMapper userMessageMapper;


    /**
     * 集合条件查询
     *
     * @param userMessage:
     * @return java.util.List<com.entity.UserMessage>
     * @author ranyang
     * @since 2021-06-01
     */
    @Override
    public List<UserMessage> listEnhance(UserMessage userMessage) {
        QueryWrapper<UserMessage> queryWrapper = new QueryWrapper<>(userMessage);
        query(userMessage, queryWrapper);
        return assignment(userMessageMapper.selectList(queryWrapper));
    }


    /**
     * 分页条件查询
     *
     * @param userMessage:
     * @param page:
     * @return java.util.List<com.entity.UserMessage>
     * @author ranyang
     * @since 2021-06-01
     */
    @Override
    public IPage pageEnhance(Page page, UserMessage userMessage) {
        QueryWrapper<UserMessage> queryWrapper = new QueryWrapper<>(userMessage);
        query(userMessage, queryWrapper);
        return assignment(userMessageMapper.selectPage(page, queryWrapper));
    }


    /**
     * 单条条件查询
     *
     * @param userMessage:
     * @return java.util.List<com.entity.UserMessage>
     * @author ranyang
     * @since 2021-06-01
     */
    @Override
    public UserMessage getOneEnhance(UserMessage userMessage) {
        QueryWrapper<UserMessage> queryWrapper = new QueryWrapper<>(userMessage);
        query(userMessage, queryWrapper);
        return assignment(userMessageMapper.selectOne(queryWrapper));
    }


    /**
     * 总数
     *
     * @param userMessage:
     * @return java.util.List<com.entity.UserMessage>
     * @author ranyang
     * @since 2021-06-01
     */
    @Override
    public Long countEnhance(UserMessage userMessage) {
        QueryWrapper<UserMessage> queryWrapper = new QueryWrapper<>(userMessage);
        query(userMessage, queryWrapper);
        return userMessageMapper.selectCount(queryWrapper);
    }


    /**
     * 新增
     *
     * @param userMessage:
     * @return java.util.List<com.entity.UserMessage>
     * @author ranyang
     * @since 2021-06-01
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, timeout = 36000, rollbackFor = Exception.class)
    public String saveEnhance(UserMessage userMessage) {
        Integer i = userMessageMapper.insert(userMessage);
        return userMessage.getId();
    }


    /**
     * 修改
     *
     * @param userMessage:
     * @return java.util.List<com.entity.UserMessage>
     * @author ranyang
     * @since 2021-06-01
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, timeout = 36000, rollbackFor = Exception.class)
    public Boolean updateEnhance(UserMessage userMessage) {
        UpdateWrapper<UserMessage> updateWrapper = new UpdateWrapper<>();
        if (StringUtils.isNotBlank(userMessage.getId())) {
            updateWrapper.eq("id", userMessage.getId());
            userMessage.setReading(true);
        } else if (StringUtils.isNotBlank(userMessage.getCurrDate())) {
            userMessage.setReading(true);
            updateWrapper.le("create_date_time", LocalDateTimeUtil.parse(userMessage.getCurrDate(), DatePattern.NORM_DATETIME_PATTERN));
        } else {
            throw new BusinessException("参数异常");
        }
        updateWrapper.eq("user_id", userMessage.getUserId());
        Integer i = userMessageMapper.update(userMessage, updateWrapper);
        return i > 0 ? true : false;
    }


    /**
     * 删除
     *
     * @param userMessage:
     * @return java.util.List<com.entity.UserMessage>
     * @author ranyang
     * @since 2021-06-01
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, timeout = 36000, rollbackFor = Exception.class)
    public Boolean removeEnhance(UserMessage userMessage) {
        QueryWrapper<UserMessage> queryWrapper = new QueryWrapper<>(userMessage);
        Integer i = userMessageMapper.delete(queryWrapper);
        return i > 0 ? true : false;
    }

    @Override
    public Boolean msgRead(UserMessage userMessage) {
        UserMessage param = new UserMessage();
        param.setReading(false);

        UpdateWrapper<UserMessage> updateWrapper = new UpdateWrapper<UserMessage>();
        updateWrapper.eq(userMessage.getId() != null, "id", userMessage.getId());
        updateWrapper.eq("user_id", userMessage.getUserId());
        updateWrapper.eq("reading", false);

        param.setReading(true);
        return userMessageMapper.update(param, updateWrapper) > 0;

    }

    @Override
    public Long unreadMsgNum(UserMessage userMessage) {
        LambdaQueryWrapper<UserMessage> query = new LambdaQueryWrapper<UserMessage>();
        query.eq(UserMessage::getUserId, userMessage.getUserId());
        query.eq(UserMessage::getReading, false);
        return userMessageMapper.selectCount(query);

    }


    /**
     * 增强查询条件
     *
     * @param userMessage:
     * @param queryWrapper:
     * @return void
     * @author ranyang
     * @since 2021-06-01
     */
    private void query(UserMessage userMessage, QueryWrapper<UserMessage> queryWrapper) {
        /**
         * 排序
         */
        if (userMessage.getCollation() != null && StringUtils.isNotBlank(userMessage.getCollationFields())) {
            if (userMessage.getCollation()) {
                queryWrapper.orderByAsc(userMessage.getCollationFields());
            } else {
                queryWrapper.orderByDesc(userMessage.getCollationFields());
            }
        } else {
            queryWrapper.orderByDesc(userMessage.getCollationFields());
        }

        /**
         * 显示字段
         */
        if (StringUtils.isNotBlank(userMessage.getFields())) {
            queryWrapper.select(userMessage.getFields());
        }
    }


    /**
     * 单条，增强返回参数追加
     *
     * @param userMessage:
     * @return UserMessage
     * @author ranyang
     * @since 2021-06-01
     */
    private UserMessage assignment(UserMessage userMessage) {
        return userMessage;
    }

    /**
     * 分页,增强返回参数追加
     *
     * @param userMessageList:
     * @return UserMessage
     * @author ranyang
     * @since 2021-06-01
     */
    private IPage assignment(IPage<UserMessage> userMessageList) {
        userMessageList.getRecords().forEach(userMessage -> {
        });
        return userMessageList;
    }


    /**
     * 集合,增强返回参数追加
     *
     * @param userMessageList:
     * @return UserMessage
     * @author ranyang
     * @since 2021-06-01
     */
    private List<UserMessage> assignment(List<UserMessage> userMessageList) {
        userMessageList.forEach(userMessage -> {
        });
        return userMessageList;
    }
}
