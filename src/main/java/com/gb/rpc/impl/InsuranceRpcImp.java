package com.gb.rpc.impl;

import com.gb.log.service.RpcLogService;
import com.gb.rpc.InsuranceRpc;
import com.gb.utils.Json;
import com.gb.utils.exception.RpcException;
import feign.hystrix.FallbackFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;

/**
 * @ClassName InsuranceRpcImp
 * @Description 投保rpc熔断
 * @Author 孙凯伦
 * @Mobile 13777579028
 * @Email 376253703@qq.com
 * @Time 2021/6/15 7:12 下午
 */
@Component
public class InsuranceRpcImp implements FallbackFactory<InsuranceRpc> {

    /**
     * rpc日志
     */
    @Autowired
    private RpcLogService rpcLogService;
    /**
     * 接收服务
     */
    private String receiveServer = "insurance";
    @Override
    public InsuranceRpc create(Throwable throwable) {
        return new InsuranceRpc() {
            @Override
            public Json selectBatch(String userNameList) {
                rpcLogService.rpcLog(receiveServer,"/enterprise-user/selectBatch", userNameList, throwable.getMessage());
                throw new RpcException(throwable.getMessage());
            }

            @Override
            public Optional<Json> closeAllCast(String userId) {
                rpcLogService.rpcLog(receiveServer,"/forUser/closeAllCast", userId, throwable.getMessage());
                throw new RpcException(throwable.getMessage());
            }

            @Override
            public Optional<Json> getInsuranceStatisticsResults(Map<String, Object> map) {
                rpcLogService.rpcLog(receiveServer,"/cast-insurance/getInsuranceStatisticsResults", map, throwable.getMessage());
                throw new RpcException(throwable.getMessage());
            }
        };
    }
}
