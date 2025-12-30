package com.gb.rpc.impl;

import com.gb.log.service.RpcLogService;
import com.gb.rpc.CustomerRpc;
import com.gb.utils.Json;
import com.gb.utils.exception.RpcException;
import feign.hystrix.FallbackFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;

/**
 * @ClassName InsuranceRpcImp
 * @Description 客服管理系统rpc熔断
 * @Author 孙凯伦
 * @Mobile 13777579028
 * @Email 376253703@qq.com
 * @Time 2021/6/15 7:12 下午
 */
@Component
public class CustomerRpcImp implements FallbackFactory<CustomerRpc> {

    /**
     * rpc日志
     */
    @Autowired
    private RpcLogService rpcLogService;
    /**
     * 接收服务
     */
    private String receiveServer = "customer";


    @Override
    public CustomerRpc create(Throwable cause) {
        return new CustomerRpc() {
            @Override
            public Optional<Json> certAgentPeople(Map<String, String> map) {
                rpcLogService.rpcLog(receiveServer,"/potential-customer/certificationAgentPeople", map, cause.getMessage());
                throw new RpcException(cause.getMessage());
            }

            @Override
            public Optional<Json> getPromoteFormOne(Map<String, String> map) {
                rpcLogService.rpcLog(receiveServer,"/promote-form/selectOne", map, cause.getMessage());
                throw new RpcException(cause.getMessage());
            }
        };
    }
}
