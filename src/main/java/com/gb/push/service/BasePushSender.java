package com.gb.push.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.gb.account.entity.User;
import com.gb.account.mapper.UserMapper;
import com.gb.push.param.SendRequest;
import com.gb.user.entity.UserToken;
import com.gb.user.mapper.UserTokenMapper;
import com.gb.utils.exception.BusinessException;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.CollectionUtils;

import java.util.Set;

/**
 * @Author: wgs
 * @Date 2022/1/20 11:05
 * @Classname PushAbstract
 * @Description
 */
@Setter(onMethod_ = {@Autowired})
@Slf4j
public abstract class BasePushSender {
    private UserTokenMapper userTokenMapper;
    private UserMapper userMapper;
    private StringRedisTemplate stringRedisTemplate;

    public static final String KEY = "*_%s_APP";

    /**
     * 推送单条
     */
    public void singleMsg(SendRequest sendRequest) {
        try {
            console(sendRequest);
            validate(sendRequest);
            buildData(sendRequest);
            checkToken(sendRequest);
            execute(sendRequest);
        } catch (Exception e) {
            log.debug("推送信息 msg: {}", e.getMessage());
        }
    }

    /**
     * 构建消息
     *
     * @param sendRequest
     */
    private void buildData(SendRequest sendRequest) {
        LambdaQueryWrapper<UserToken> lambda = new LambdaQueryWrapper<>();
        lambda.eq(UserToken::getUserId, sendRequest.getUserId());
        lambda.orderByDesc(UserToken::getModifyDateTime).last("limit 1");
        final UserToken userToken = userTokenMapper.selectOne(lambda);
        if (userToken == null || StringUtils.isBlank(userToken.getCid())) {
            throw new BusinessException("用户设备信息不存在 无需推送!");
        }

        sendRequest.setCid(userToken.getCid());
        sendRequest.setAlias(userToken.getUserId());
        sendRequest.setTokenType(userToken.getTokenType());
    }

    /**
     * 发送消息
     *
     * @param sendRequest
     */
    protected void checkToken(SendRequest sendRequest) {
        final User user = userMapper.selectById(sendRequest.getUserId());
        if (user == null) {
            throw new BusinessException("用户不存在 无需推送!");
        }
        // 缓存获取
        String key = String.format(KEY, user.getUserName());
        Set<String> set = stringRedisTemplate.keys(key);
        if (CollectionUtils.isEmpty(set)) {
            throw new BusinessException("用户token不存在 无需推送!");
        }
    }


    /**
     * 验证消息
     *
     * @param sendRequest
     */
    protected abstract void validate(SendRequest sendRequest);

    /**
     * 发送消息
     *
     * @param sendRequest
     */
    protected abstract void execute(SendRequest sendRequest);

    /**
     * 打印日志
     *
     * @param sendRequest
     */
    protected abstract void console(SendRequest sendRequest);
}
