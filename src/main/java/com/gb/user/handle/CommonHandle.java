package com.gb.user.handle;

import com.gb.user.entity.bo.RedisTokenBO;
import com.gb.utils.enumeration.SystemSourceEnum;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.utils.Lists;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 常规处理类
 * @author yyl
 */
@Component
@Slf4j
public class CommonHandle {

    public static List<String> filterSourceList;
    public static Map<String, RedisTokenBO> tokenTimeSettingMap;

    private CommonHandle() {
        filterSourceList = Lists.newArrayList();
        //通
        filterSourceList.add("GONG_BAO_TON");
        //金
        filterSourceList.add("GONG_BAO_JIN");
        //盾
        filterSourceList.add("GONG_BAO_DUN");
        //一体化
        filterSourceList.add("GONG_BAO_UNIFY");
        //财务结算
        filterSourceList.add("GONG_BAO_CWJS");
        //安责
        filterSourceList.add("GONG_BAO_AZ");
        //薪乐达
        filterSourceList.add("TRIPARTITE_XLD");
        //电子保函
        filterSourceList.add("GONG_BAO_EGUARANTEE");
        //会基
        filterSourceList.add("H_FOUNDATION");
        //华为
        filterSourceList.add("HUAWEI");

        //token设置
        setTokenTimeSettingMap();
    }

    /**
     * 设置token时间map
     */
    private void setTokenTimeSettingMap() {
        tokenTimeSettingMap = Maps.newHashMap();
        tokenTimeSettingMap.put(SystemSourceEnum.APP.getCode(), new RedisTokenBO(){{
            setKeyTail("_" + SystemSourceEnum.APP.getCode());
            setTimeUnit(TimeUnit.DAYS);
            setTimeOut(30L);
        }});
        tokenTimeSettingMap.put(SystemSourceEnum.OFFICIAL_ACCOUNT.getCode(), new RedisTokenBO(){{
            setKeyTail("_" + SystemSourceEnum.OFFICIAL_ACCOUNT.getCode());
            setTimeUnit(TimeUnit.DAYS);
            setTimeOut(30L);
        }});
        tokenTimeSettingMap.put(SystemSourceEnum.H5.getCode(), new RedisTokenBO(){{
            setKeyTail("_" + SystemSourceEnum.H5.getCode());
            setTimeUnit(TimeUnit.DAYS);
            setTimeOut(1L);
        }});
    }

    /**
     * 对请求参数进行校验
     * @param obj; 请求参数
     * @return String;
     * @throws Exception
     */
    public String validateParams(Object obj) throws Exception {
        try{
            String result = StringUtils.EMPTY;
            for(Field f : obj.getClass().getDeclaredFields()){
                f.setAccessible(true);
                String message = (null == (f.getAnnotation(NotNull.class)))
                        ? (null == f.getAnnotation(NotBlank.class) ? StringUtils.EMPTY : f.getAnnotation(NotBlank.class).message())
                        : f.getAnnotation(NotNull.class).message();
                if(StringUtils.isBlank(message)){
                    continue;
                }
                if(null == f.get(obj)){
                    result = message;
                    break;
                }
            }
            if(StringUtils.isNotBlank(result)){
                log.debug("必填参数校验：{}", result);
            }
            return result;
        }catch (Exception e){
            log.error("参数校验异常：", e);
            throw e;
        }
    }
}
