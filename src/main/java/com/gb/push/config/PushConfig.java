//package com.gb.push.config;
//
//import com.getui.push.v2.sdk.ApiHelper;
//import com.getui.push.v2.sdk.GtApiConfiguration;
//import com.getui.push.v2.sdk.api.PushApi;
//import com.getui.push.v2.sdk.api.UserApi;
//import lombok.Setter;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
///**
// * @Author: wgs
// * @Date 2022/1/20 09:38
// * @Classname PushConfig
// * @Description
// */
//@Configuration
//@Setter(onMethod_ = {@Autowired})
//public class PushConfig {
//    private GetuiConfig getuiConfig;
//
//    @Bean
//    public PushApi pushApi() {
//        // 设置httpClient最大连接数，当并发较大时建议调大此参数。
//        System.setProperty("http.maxConnections", "200");
//        GtApiConfiguration apiConfiguration = getGtApiConfiguration();
//
//        ApiHelper apiHelper = ApiHelper.build(apiConfiguration);
//        return apiHelper.creatApi(PushApi.class);
//    }
//
//
//    @Bean
//    public UserApi userApi() {
//        GtApiConfiguration apiConfiguration = getGtApiConfiguration();
//        apiConfiguration.setMaxHttpTryTime(0);
//        apiConfiguration.setOpenCheckHealthDataSwitch(true);
//        apiConfiguration.setOpenAnalyseStableDomainSwitch(true);
//        apiConfiguration.setSoTimeout(5000);
//        apiConfiguration.setConnectTimeout(5000);
//
//        ApiHelper apiHelper = ApiHelper.build(apiConfiguration);
//        return apiHelper.creatApi(UserApi.class);
//    }
//
//    private GtApiConfiguration getGtApiConfiguration() {
//        GtApiConfiguration apiConfiguration = new GtApiConfiguration();
//        // 填写应用配置
//        apiConfiguration.setAppId(getuiConfig.getAppId());
//        apiConfiguration.setAppKey(getuiConfig.getAppKey());
//        apiConfiguration.setMasterSecret(getuiConfig.getMasterSecret());
//        apiConfiguration.setDomain("https://restapi.getui.com/v2/");
//        return apiConfiguration;
//    }
//}
