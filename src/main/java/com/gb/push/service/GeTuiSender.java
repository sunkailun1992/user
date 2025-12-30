//package com.gb.push.service;
//
//import cn.hutool.core.date.DateUtil;
//import cn.hutool.core.util.RandomUtil;
//import com.alibaba.fastjson.JSONObject;
//import com.gb.push.config.GetuiConfig;
//import com.gb.push.config.PushConfig;
//import com.gb.push.param.SendRequest;
//import com.gb.utils.exception.BusinessException;
//import com.getui.push.v2.sdk.api.PushApi;
//import com.getui.push.v2.sdk.api.UserApi;
//import com.getui.push.v2.sdk.common.ApiResult;
//import com.getui.push.v2.sdk.dto.CommonEnum;
//import com.getui.push.v2.sdk.dto.req.Audience;
//import com.getui.push.v2.sdk.dto.req.CidAliasListDTO;
//import com.getui.push.v2.sdk.dto.req.message.PushChannel;
//import com.getui.push.v2.sdk.dto.req.message.PushDTO;
//import com.getui.push.v2.sdk.dto.req.message.PushMessage;
//import com.getui.push.v2.sdk.dto.req.message.android.AndroidDTO;
//import com.getui.push.v2.sdk.dto.req.message.android.GTNotification;
//import com.getui.push.v2.sdk.dto.req.message.android.ThirdNotification;
//import com.getui.push.v2.sdk.dto.req.message.android.Ups;
//import com.getui.push.v2.sdk.dto.req.message.ios.Alert;
//import com.getui.push.v2.sdk.dto.req.message.ios.Aps;
//import com.getui.push.v2.sdk.dto.req.message.ios.IosDTO;
//import com.getui.push.v2.sdk.dto.res.QueryCidResDTO;
//import lombok.Setter;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.Map;
//
///**
// * @Author: wgs
// * @Date 2022/1/20 11:14
// * @Classname GeTuiSender
// * @Description
// */
//@Service
//@Slf4j
//@Setter(onMethod_ = {@Autowired})
//@SuppressWarnings("all")
//public class GeTuiSender extends BasePushSender {
//    private PushConfig pushConfig;
//    private PushApi pushApi;
//    private UserApi userApi;
//    private GetuiConfig getuiConfig;
//
//    @Override
//    protected void validate(SendRequest sendRequest) {
//        if (!getuiConfig.isAliasFlag()) {
//            return;
//        }
//        ApiResult<QueryCidResDTO> apiResult = this.queryCidByAlias(sendRequest.getUserId());
//        if (!apiResult.isSuccess()) {
//            throw new BusinessException("别名未绑定,无需推送");
//        }
//    }
//
//
//    @Override
//    protected void execute(SendRequest sendRequest) {
//        pushToSingleByAlias(sendRequest);
//    }
//
//    /**
//     * cid 推送
//     *
//     * @param sendRequest
//     */
//    protected void pushToSingleByCid(SendRequest sendRequest) {
//        PushDTO<Audience> pushDTO = pushDTO(sendRequest);
//        fullCid(pushDTO, sendRequest.getCid());
//        // 进行cid单推
//        ApiResult<Map<String, Map<String, String>>> apiResult = pushApi.pushToSingleByCid(pushDTO);
//        if (!apiResult.isSuccess()) {
//            // failed
//            log.debug("【个推】推送失败响应参数 data: {}", JSONObject.toJSONString(apiResult, true));
//        }
//    }
//
//    /**
//     * 别名推送
//     *
//     * @param sendRequest
//     */
//    protected void pushToSingleByAlias(SendRequest sendRequest) {
//        PushDTO<Audience> pushDTO = pushDTO(sendRequest);
//        fullAlias(pushDTO, sendRequest.getAlias());
//        // 别名单推
//        ApiResult<Map<String, Map<String, String>>> apiResult = pushApi.pushToSingleByAlias(pushDTO);
//        log.debug("【个推】别名推送 请求参数:{} 响应参数: {}", JSONObject.toJSONString(pushDTO, true), JSONObject.toJSONString(apiResult, true));
//    }
//
//    private PushDTO<Audience> pushDTO(SendRequest sendRequest) {
//        PushDTO<Audience> pushDTO = new PushDTO<Audience>();
//        // 设置推送参数
//        pushDTO.setRequestId(System.currentTimeMillis() + "");
//        /**** 设置个推通道参数 *****/
//        PushMessage pushMessage = new PushMessage();
//        pushDTO.setPushMessage(pushMessage);
//        GTNotification notification = new GTNotification();
//        pushMessage.setNotification(notification);
//        notification.setTitle(sendRequest.getMessageParam().getTitle());
//        notification.setBody(sendRequest.getMessageParam().getBody());
//        notification.setClickType(CommonEnum.ClickTypeEnum.TYPE_PAYLOAD.type);
//
//        JSONObject json = new JSONObject();
//        json.put("type", "1");
//        json.put("msg", "这是一条透传消息");
//        json.put("msgId", sendRequest.getMsgId());
//        notification.setPayload(json.toJSONString());
//        //notification.setUrl("https://www.getui.com");
//
//
//        /**** 设置厂商相关参数 ****/
//        PushChannel pushChannel = new PushChannel();
//        pushDTO.setPushChannel(pushChannel);
//        /*配置安卓厂商参数*/
//        buildAndroid(pushChannel, sendRequest);
//        /*设置ios厂商参数*/
//        buildIos(pushChannel, sendRequest);
//
//        return pushDTO;
//    }
//
//    private void buildIos(PushChannel pushChannel, SendRequest sendRequest) {
//        IosDTO iosDTO = new IosDTO();
//        pushChannel.setIos(iosDTO);
//        // 相同的collapseId会覆盖之前的消息
//        iosDTO.setApnsCollapseId(RandomUtil.randomNumbers(6));
//        Aps aps = new Aps();
//        iosDTO.setAps(aps);
//        Alert alert = new Alert();
//        aps.setAlert(alert);
//        alert.setTitle(sendRequest.getMessageParam().getTitle());
//        alert.setBody(sendRequest.getMessageParam().getBody());
//    }
//
//    private void buildAndroid(PushChannel pushChannel, SendRequest sendRequest) {
//        AndroidDTO androidDTO = new AndroidDTO();
//        pushChannel.setAndroid(androidDTO);
//        Ups ups = new Ups();
//        //ups.setTransmission("透传消息");
//        ThirdNotification thirdNotification = new ThirdNotification();
//        ups.setNotification(thirdNotification);
//        thirdNotification.setTitle(sendRequest.getMessageParam().getTitle());
//        thirdNotification.setBody(sendRequest.getMessageParam().getBody());
//        thirdNotification.setClickType(CommonEnum.ClickTypeEnum.TYPE_STARTAPP.type);
//        androidDTO.setUps(ups);
//    }
//
//    /**
//     * 绑定别名
//     *
//     * @param cid
//     * @param alias
//     */
//    public ApiResult<Void> bindAlias(String cid, String alias) {
//        CidAliasListDTO cidAliasListDTO = new CidAliasListDTO();
//        cidAliasListDTO.add(new CidAliasListDTO.CidAlias(cid, alias));
//        ApiResult<Void> apiResult = userApi.bindAlias(cidAliasListDTO);
//        if (!apiResult.isSuccess()) {
//            // failed
//            log.debug("【个推】绑定别名失败 \n cid:{} alias:{} data: {}", cid, alias, JSONObject.toJSONString(apiResult, true));
//        }
//
//        return apiResult;
//    }
//
//    /**
//     * 解绑别名
//     *
//     * @param alias
//     */
//    public ApiResult<Void> unbindAllAlias(String alias) {
//        ApiResult<Void> apiResult = userApi.unbindAllAlias(alias);
//        log.debug("【个推】解绑别名 data: {}", JSONObject.toJSONString(apiResult));
//        return apiResult;
//    }
//
//    /**
//     * 解绑别名
//     *
//     * @param cid
//     * @param alias
//     */
//    public ApiResult<Void> batchUnboundAlias(String cid, String alias) {
//        CidAliasListDTO cidAliasListDTO = new CidAliasListDTO();
//        cidAliasListDTO.add(new CidAliasListDTO.CidAlias(cid, alias));
//        ApiResult<Void> apiResult = userApi.batchUnbindAlias(cidAliasListDTO);
//        log.debug("【个推】解绑别名cid:{} alias:{} data: {}", cid, alias, JSONObject.toJSONString(apiResult, true));
//        return apiResult;
//    }
//
//    /**
//     * 根据别名查询cid
//     *
//     * @param alias
//     * @return
//     */
//    public ApiResult<QueryCidResDTO> queryCidByAlias(String alias) {
//        ApiResult<QueryCidResDTO> apiResult = userApi.queryCidByAlias(alias);
//        log.debug("【个推】根据别名查询 alias:{} data:{}", alias, JSONObject.toJSONString(apiResult, true));
//        return apiResult;
//    }
//
//    private void fullCid(PushDTO<Audience> pushDTO, String cid) {
//        Audience audience = new Audience();
//        audience.addCid(cid);
//        pushDTO.setAudience(audience);
//    }
//
//    private void fullAlias(PushDTO<Audience> pushDTO, String alias) {
//        Audience audience = new Audience();
//        audience.addAlias(alias);
//        pushDTO.setAudience(audience);
//    }
//
//    @Override
//    protected void console(SendRequest sendRequest) {
//        log.debug("【个推】推送参数 userId:{} data: {},time:{}", sendRequest.getUserId(), JSONObject.toJSONString(sendRequest, true), DateUtil.now());
//    }
//
//}
