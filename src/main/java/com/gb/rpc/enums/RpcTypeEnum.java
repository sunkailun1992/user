package com.gb.rpc.enums;

import lombok.Getter;

/**
 * @author: ranyang
 * @Date: 2021/3/15 15:09
 * @descript: rpc调用枚举
 */
@Getter
@SuppressWarnings("all")
public enum RpcTypeEnum {
    AREA_GET,
    CITY_GET,
    PROVINCE_GET,
    JJRAUTH_ORDERCLOSE,
    CERT_BROKER_CRM,
    CITY_GET_ONE,
    AREA_GET_ONE,
    PROMOTE_FORM_GET,
    DANGER_PLANTED_ONE,
    /**
     * 保险统计结果查询
     */
    INSURANCE_STATISTICS_QUERY;
}
