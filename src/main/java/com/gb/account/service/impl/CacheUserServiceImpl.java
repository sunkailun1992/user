package com.gb.account.service.impl;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.RandomUtil;
import com.gb.account.entity.bo.CacheUserInfoBO;
import com.gb.account.entity.vo.CacheUserInfoVO;
import com.gb.account.service.CacheUserService;
import com.gb.aliyun.sms.SmsUtils;
import com.gb.bean.GongBaoUserConfig;
import com.gb.permissions.entity.vo.ResourceVO;
import com.gb.permissions.service.ResourceService;
import com.gb.user.constant.RedisConstant;
import com.gb.user.entity.bo.RedisTokenBO;
import com.gb.user.handle.CommonHandle;
import com.gb.utils.JsonUtil;
import com.gb.utils.RedisUtils;
import com.gb.utils.constants.UniversalConstant;
import com.gb.utils.enumeration.AppCodeEnum;
import com.gb.utils.enumeration.ReturnCode;
import com.gb.utils.enumeration.SmsEnum;
import com.gb.utils.exception.ParameterNullException;
import com.gb.utils.exception.SmsException;
import com.gb.utils.exception.UserException;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: sunx
 * @since: 2021-10-21 01:50:40
 * @description: 缓存用户信息Service服务实现层
 * @source: 代码生成器
 */
@Slf4j
@Service
@Setter(onMethod_ = {@Autowired})
public class CacheUserServiceImpl implements CacheUserService {

    /**
     * redis操作
     */
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 资源
     */
    private ResourceService resourceService;

    private GongBaoUserConfig gongBaoUserConfig;

    @Override
    public void smsSendLogin(String mobile) {
        if(StringUtils.isBlank(mobile)) {
            throw new ParameterNullException("手机号不能为空！");
        }
        Map<String, Object> map = Maps.newHashMap();
        map.put("code", RandomUtil.randomNumbers(6));
        Boolean b = SmsUtils.sendMessage(mobile, SmsEnum.用户登录, JsonUtil.json(map));
        if (b) {
            RedisUtils.add(stringRedisTemplate, "login:" + mobile, String.valueOf(map.get("code")), 5, TimeUnit.MINUTES);
        } else {
            throw new SmsException("登录短信发送异常");
        }
    }


    @Override
    public CacheUserInfoVO getOneEnhance(String token) {
        if(StringUtils.isBlank(token)) {
            throw new UserException(ReturnCode.无效TOKEN, "token空！");
        }
        Map<String, Object> userInfoMap = RedisUtils.getToken(stringRedisTemplate, token);
        if(MapUtil.isEmpty(userInfoMap) || Objects.isNull(userInfoMap.get(UniversalConstant.USERNAME))) {
            return null;
        }
        return Convert.convert(CacheUserInfoVO.class, userInfoMap);
    }


    @Override
    public Object getCacheParamsInfo(String cacheKey, String paramsKey) {
        if(StringUtils.isBlank(cacheKey) || StringUtils.isBlank(paramsKey)) {
            throw new ParameterNullException("查询缓存的key或参数key不能为空！");
        }
        return RedisUtils.getMap(stringRedisTemplate, cacheKey, paramsKey);
    }


    @Override
    public void saveEnhance(String token, String businessDetails, CacheUserInfoBO cacheUserInfoBO) {
        //网关层用到--针对同一个账号,不同sourceCode，同一个TOKEN失效，对应的appCode+userName未失效情况
        List<ResourceVO> resource = Lists.newArrayList();
        List<ResourceVO> api = Lists.newArrayList();
        boolean isGateWay = false;
        if(StringUtils.isNotBlank(token) && StringUtils.isNotBlank(businessDetails) && StringUtils.equals(businessDetails, RedisConstant.GATEWAY_BUSINESSDETAILS)) {
            CacheUserInfoVO cacheUserInfoVO = getOneEnhance(token);
            if(Objects.nonNull(cacheUserInfoVO)) {
               return;
            }
            isGateWay = true;
        }
        //获取权限信息
        //TODO: nacos配置权限排除过滤
        if(StringUtils.equalsIgnoreCase(cacheUserInfoBO.getAppCode(), AppCodeEnum.NET_BACKEND.getCode()) && StringUtils.isNotBlank(gongBaoUserConfig.getBacPerAccount()) && gongBaoUserConfig.getBacPerAccount().contains(cacheUserInfoBO.getMobile())) {
            resource = resourceService.bigPermissionResource(true,false);
            api = resourceService.bigPermissionResource(true, true);
        }
        String cacheKey = StringUtils.EMPTY;
        if(CollectionUtils.isEmpty(resource) || CollectionUtils.isEmpty(api)) {
            Map<String, Object> resultMap = getResourceOrApi(cacheUserInfoBO.getId(), cacheUserInfoBO.getAppCode(), isGateWay);
            api = (List<ResourceVO>)resultMap.get("api");
            resource = (List<ResourceVO>)resultMap.get("resource");
            if(Objects.nonNull(resultMap.get(RedisConstant.CACHE_KEY))) {
                cacheKey = String.valueOf(resultMap.get(RedisConstant.CACHE_KEY)) + "_" + cacheUserInfoBO.getUserName();
            }
        }
        if (CollectionUtils.isEmpty(resource) || CollectionUtils.isEmpty(api)) {
            throw new UserException(ReturnCode.无权限使用, "资源权限或API权限无！");
        }
        //设置缓存key
        if(!isGateWay) {
            cacheKey = cacheUserInfoBO.getAppCode() + "_" + cacheUserInfoBO.getUserName();
        }
        if(StringUtils.isBlank(cacheKey)) {
            throw new UserException(ReturnCode.无权限使用, "缓存key值未找到，资源权限或API权限无！");
        }
        //检查原来的token和现在的token是否相同，如果不相同，删除原来的，保存现在的
        Object oldToken = RedisUtils.getMap(stringRedisTemplate, cacheKey, UniversalConstant.TOKEN);
        if(Objects.nonNull(oldToken) && !StringUtils.equals(token, String.valueOf(oldToken))) {
            RedisUtils.delete(stringRedisTemplate, cacheKey);
            RedisUtils.delete(stringRedisTemplate, String.valueOf(oldToken));
        }
        //键值对缓存,通过账号获得token
        addCache(cacheKey, token, resource, api, cacheUserInfoBO);
    }


    @Override
    public CacheUserInfoVO removeEnchane(String token, String sourceCode) {
        CacheUserInfoVO cacheUserInfoVO = getOneEnhance(token);
        //取出本地缓存用户相关等信息--进行删除
        if (StringUtils.isNotBlank(cacheUserInfoVO.getUserName()) && StringUtils.isNotBlank(cacheUserInfoVO.getAppCode())) {
            String cacheKey = cacheUserInfoVO.getAppCode() + "_" + cacheUserInfoVO.getUserName();
            if(CommonHandle.tokenTimeSettingMap.containsKey(sourceCode)) {
                cacheKey = cacheKey + CommonHandle.tokenTimeSettingMap.get(sourceCode).getKeyTail();
            }
            //删除token缓存
            RedisUtils.delete(stringRedisTemplate, cacheKey);
            RedisUtils.delete(stringRedisTemplate, token);
        }
        return cacheUserInfoVO;
    }


    /**
     * 放入缓存
     * @param cacheKey 缓存key
     * @param token 鉴权令牌
     * @param resource 资源
     * @param api api
     * @param cacheUserInfoBO 缓存用户信息BO
     * @return map
     */
    private Map<String, Object> addCache(String cacheKey, String token, List<ResourceVO> resource, List<ResourceVO> api, CacheUserInfoBO cacheUserInfoBO) {
        //键值对缓存,通过账号获得token
        Map<String, Object> map = Maps.newHashMap();
        //放入当前token登录的appCode
        map.put("user", cacheUserInfoBO);
        map.put(UniversalConstant.TOKEN, token);
        map.put("resource", resource);
        map.put("api", api);
        map.put(UniversalConstant.IP, cacheUserInfoBO.getIp());
        map.put(UniversalConstant.SOURCECODE, cacheUserInfoBO.getLoginSourceCode());
        //H5是30天，APP是30天，公众号30天，其他都是30分钟
        if(CommonHandle.tokenTimeSettingMap.containsKey(cacheUserInfoBO.getLoginSourceCode())) {
            RedisTokenBO redisTokenBO = CommonHandle.tokenTimeSettingMap.get(cacheUserInfoBO.getLoginSourceCode());
            cacheKey = cacheKey + redisTokenBO.getKeyTail();
            RedisUtils.add(stringRedisTemplate, token, cacheUserInfoBO, redisTokenBO.getTimeOut(), redisTokenBO.getTimeUnit());
            RedisUtils.addMap(stringRedisTemplate, cacheKey, map, redisTokenBO.getTimeOut(), redisTokenBO.getTimeUnit());
        } else {
            RedisUtils.add(stringRedisTemplate, token, cacheUserInfoBO, 30, TimeUnit.DAYS);
            RedisUtils.addMap(stringRedisTemplate, cacheKey, map, 30, TimeUnit.DAYS);
        }
        return map;
    }

    /**
     * 获取资源或API（查询SQL）
     * @param userId
     * @param appCode
     * @param isGateWay
     * @return List<ResourceVO>
     */
    private Map<String, Object> getResourceOrApi(String userId, String appCode, Boolean isGateWay) {
        //TODO: 只考虑PC情况，暂时不考虑其他情况
        Map<String, Object> resultMap = Maps.newHashMap();
        resultMap.put("api", resourceService.userResource(userId, true, appCode, null));
        resultMap.put("resource", resourceService.userResource(userId, false, appCode, null));
        resultMap.put(RedisConstant.CACHE_KEY, appCode);
        return resultMap;
//        String cacheKey = null;
//        String[] appCodeArrays = {AppCodeEnum.NET_AGENT.getCode(), AppCodeEnum.NET_USER.getCode(), AppCodeEnum.NET_INS.getCode()};
//        List<ResourceVO> resourceList = Lists.newArrayList();
//        List<ResourceVO> apiList = Lists.newArrayList();
//        for(String a: appCodeArrays) {
//            cacheKey = a;
//            apiList = resourceService.userResource(userId, true, a, null);
//            resourceList = resourceService.userResource(userId, false, a, null);
//            if(CollectionUtils.isNotEmpty(apiList) || CollectionUtils.isNotEmpty(resourceList)) {
//                break;
//            }
//        }
//        resultMap.put("api", apiList);
//        resultMap.put("resource", resourceList);
//        resultMap.put("cacheKey", cacheKey);
//        log.debug("isGateWay is true，userId：{}，查库权限资源：{}", userId, JSON.toJSONString(resultMap));
//        return resultMap;
    }
}