package com.gb.user.handle;

import com.gb.utils.RedisUtils;
import com.gb.utils.enumeration.SystemSourceEnum;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

/**
 * 常规处理类
 * @author yyl
 */
@Component
@Slf4j
@Setter(onMethod_ = {@Autowired})
public class RedisHandle {

    private StringRedisTemplate stringRedisTemplate;

    /**
     * 获取缓存信息
     * @param userName
     * @param appCode
     * @param sourceCode
     * @param keyName
     * @return
     */
    public Object getRedisInfo(String userName, String appCode, String sourceCode, String keyName) {
       String key = appCode + "_" + userName;
        if(StringUtils.equals(sourceCode, SystemSourceEnum.APP.getCode())) {
           key = key + "_" + SystemSourceEnum.APP.getCode();
       }
       return RedisUtils.getMap(stringRedisTemplate, key, keyName);
    }
}
