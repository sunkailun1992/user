package com.gb.rpc;

import com.gb.rpc.impl.CustomerRpcImp;
import com.gb.utils.Json;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;
import java.util.Optional;

/**
 * Created with IntelliJ IDEA.
 *
 * @author sunkailun
 * @DateTime 2020/1/8  11:04 上午
 * @email 376253703@qq.com
 * @phone 13777579028
 * @explain
 */
@FeignClient(value = "customer", fallbackFactory = CustomerRpcImp.class)
public interface CustomerRpc {

    /**
     * 客服管理系统=认证经纪人
     *
     * @param map: 请求参数MAP
     * @return com.utils.Json
     * @author sunkailun
     * @DateTime 2018/5/8  下午4:58
     * @email 376253703@qq.com
     * @phone 13777579028
     */
    @RequestMapping(value = "/potential-customer/certificationAgentPeople", method = RequestMethod.PUT)
    Optional<Json> certAgentPeople(@RequestParam Map<String, String> map);

    /**
     * 获取客户表单信息
     *
     * @param map: 请求参数MAP
     * @return com.utils.Json
     * @author sunkailun
     * @DateTime 2018/5/8  下午4:58
     * @email 376253703@qq.com
     * @phone 13777579028
     */
    @RequestMapping(value = "/promote-form/selectOne", method = RequestMethod.GET)
    Optional<Json> getPromoteFormOne(@RequestParam Map<String, String> map);

}
