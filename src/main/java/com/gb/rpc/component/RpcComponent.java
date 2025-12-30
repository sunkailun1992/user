package com.gb.rpc.component;

import com.alibaba.fastjson.JSON;
import com.gb.rpc.CustomerRpc;
import com.gb.rpc.InsuranceRpc;
import com.gb.rpc.ProductRpc;
import com.gb.rpc.enums.RpcTypeEnum;
import com.gb.utils.GeneralConvertor;
import com.gb.utils.Json;
import com.gb.utils.exception.BusinessException;
import com.gb.utils.exception.RpcException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @author: ranyang
 * @Date: 2021/03/26 14:12
 * @descript:
 */
@Component
@Slf4j
public class RpcComponent {
    @Resource
    private ProductRpc productRpc;

    @Resource
    private InsuranceRpc insuranceRpc;

    @Resource
    private CustomerRpc customerRpc;

    /**
     * rpc调用，返回单个
     * @param params
     * @param queryType
     * @param returnType
     * @param <T>
     * @return
     */
    public <T> T rpcQuery(Object params, RpcTypeEnum queryType, Class<T> returnType) {
        String paramJson = JSON.toJSONString(params);
        log.debug("远程调用【{}】，请求参数信息：{}", queryType.toString(), paramJson);
        Optional<Json> result = Optional.empty();
        try{
            switch (queryType) {
                case AREA_GET:
                    result = productRpc.queryAreaName((String)params);
                    break;
                case CITY_GET:
                    result = productRpc.selectCityInfo((Map)params);
                    break;
                case PROVINCE_GET:
                    result = productRpc.selectProvinceInfo((Map)params);
                    break;
                case JJRAUTH_ORDERCLOSE:
                    result = insuranceRpc.closeAllCast((String) params);
                    break;
                case CERT_BROKER_CRM:
                    result = customerRpc.certAgentPeople((Map) params);
                    break;
                case CITY_GET_ONE:
                    result = productRpc.selectCityOne((Map)params);
                    break;
                case AREA_GET_ONE:
                    result = productRpc.selectAreaOne((Map)params);
                    break;
                case DANGER_PLANTED_ONE:
                    result = productRpc.getDangerPlantedOne((Map)params);
                    break;
                case PROMOTE_FORM_GET:
                    result = customerRpc.getPromoteFormOne((Map)params);
                    break;
                case INSURANCE_STATISTICS_QUERY:
                    result = insuranceRpc.getInsuranceStatisticsResults((Map)params);
                    break;
                default:
                    break;
            }
            log.debug("远程调用【{}】结束，请求参数信息：{}，返回参数信息：{}", queryType, paramJson, JSON.toJSONString(result));
            String typeName = returnType.getTypeName();
            if(StringUtils.equals(typeName, Map.class.getTypeName())){
                return (T)Json.get(result.get());
            }
            if(StringUtils.equals(typeName, Integer.class.getTypeName())
                    ||StringUtils.equals(typeName, String.class.getTypeName())
                    ||StringUtils.equals(typeName, List.class.getTypeName())
                    ||StringUtils.equals(typeName, Boolean.class.getTypeName())){
                return (T)result.get().getObj();
            }
            if (result.isPresent() && result.get().getSuccess()) {
                return GeneralConvertor.convertor(Json.get(result.get()), returnType);
            }
        }catch (Exception e){
            log.error("RPC【{}】调用异常：【请求参数信息：{}】",queryType, paramJson, e);
            String errotTip =  StringUtils.isNotBlank(e.getMessage())? "：" + e.getMessage() : StringUtils.EMPTY;
            throw new RpcException(String.format("RPC【%s】调用异常%s", queryType, errotTip));
        }
        String msg = result.isPresent() ? String.format("RPC【%s】调用失败, 返回：%s", queryType, result.get().getMsg()) : StringUtils.EMPTY;
        throw new BusinessException(msg);
    }
}
