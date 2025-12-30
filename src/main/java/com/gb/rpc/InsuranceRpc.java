package com.gb.rpc;

import com.gb.rpc.impl.InsuranceRpcImp;
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
@FeignClient(value = "insurance", fallbackFactory = InsuranceRpcImp.class)
public interface InsuranceRpc {
    /**
     * 风控审核同步结果订单ID
     *
     * @param userNameList: 用户唯一标志列表
     * @return com.utils.Json
     * @author sunkailun
     * @DateTime 2018/5/8  下午4:58
     * @email 376253703@qq.com
     * @phone 13777579028
     */
    @RequestMapping(value = "/enterprise-user/selectBatch", method = RequestMethod.GET)
    Json selectBatch(@RequestParam(value = "userNameList") String userNameList);

    /**
     * 根据userId关系订单
     *
     * @param userId: 用户唯一标志列表
     * @return com.utils.Json
     * @author sunkailun
     * @DateTime 2018/5/8  下午4:58
     * @email 376253703@qq.com
     * @phone 13777579028
     */
    @RequestMapping(value = "/forUser/closeAllCast", method = RequestMethod.POST)
    Optional<Json> closeAllCast(@RequestParam(value = "userId") String userId);

    /**
     * 获取保险统计结果
     *
     * @param map：请求参数
     * @return com.utils.Json
     * @author sunkailun
     * @DateTime 2018/5/8  下午4:58
     * @email 376253703@qq.com
     * @phone 13777579028
     */
    @RequestMapping(value = "/statistic/teamPolicyStatistics", method = RequestMethod.POST)
    Optional<Json> getInsuranceStatisticsResults(Map<String, Object> map);

}
