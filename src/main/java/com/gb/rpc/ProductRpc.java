package com.gb.rpc;

import com.gb.utils.Json;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;
import java.util.Optional;

/**
 * @author: ranyang
 * @Date: 2021/3/15 11:05
 * @descript: 产品相关查询
 */
@FeignClient(value = "product")
public interface ProductRpc {

    /**
     * 保险用户企业列表查询
     *
     * @param current 当前页
     * @param size 分页数量
     * @param userNameList 用户ID列表
     * @param enterpriseId 保司ID
     * @return
     */
    @GetMapping(value = "/insurance-enterprise-user/selectBatch")
    Json selectBatch(@RequestParam(value = "current") Integer current, @RequestParam(value = "size") Integer size, @RequestParam(value = "userNameList") String userNameList, @RequestParam(value = "enterpriseId")String enterpriseId);

    /**
     * 保险用户企业信息查询
     *
     * @param map
     * @return
     */
    @GetMapping(value = "/insurance-enterprise-user/selectOne")
    Json selectInsuranceUserOne(@RequestParam Map<String, String> map);

    /**
     * 根据区码获取省市区
     *
     * @param areaList 区代码列表
     * @return
     */
    @RequestMapping(value = "/area/queryAreaName", method = RequestMethod.GET)
    Optional<Json> queryAreaName(@RequestParam(value = "areaList") String areaList);

    /**
     * 根据id查询险种
     * @param map
     * @return
     */
    @RequestMapping(value = "/product-danger-planted/selectOne", method = RequestMethod.GET)
    Json<Map<String,Object>> findProductDangerPlantedById(@RequestParam Map<String, String> map);

    /**
     * 根据市码值获取省市区
     *
     * @param map 区代码列表
     * @return
     */
    @RequestMapping(value = "/city/selectCityInfo", method = RequestMethod.GET)
    Optional<Json> selectCityInfo(@RequestParam Map<String, String> map);

    /**
     * 根据省码值获取省信息
     *
     * @param map 省代码列表  省名称
     * @return
     */
    @RequestMapping(value = "/province/selectOne", method = RequestMethod.GET)
    Optional<Json> selectProvinceInfo(@RequestParam Map<String, String> map);

    /**
     * 获取指定市信息
     *
     * @param map 市名称 / 省code
     * @return
     */
    @RequestMapping(value = "/city/selectOne", method = RequestMethod.GET)
    Optional<Json> selectCityOne(@RequestParam Map<String, String> map);

    /**
     * 获取指定区信息
     *
     * @param map 区名称 / 市code
     * @return
     */
    @RequestMapping(value = "/area/selectOne", method = RequestMethod.GET)
    Optional<Json> selectAreaOne(@RequestParam Map<String, Object> map);

    /**
     * 获取指定险种
     *
     * @param map 险种查询map
     * @return
     */
    @RequestMapping(value = "/product-danger-planted/selectOne", method = RequestMethod.GET)
    Optional<Json> getDangerPlantedOne(@RequestParam Map<String, Object> map);
}
