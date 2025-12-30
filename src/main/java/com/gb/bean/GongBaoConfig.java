package com.gb.bean;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 工保科技配置内容
 *
 * @author sunkailun
 * @DateTime 2021/3/17  11:04 上午
 * @email 376253703@qq.com
 * @phone 13777579028
 * @explain
 */
@Component
public class GongBaoConfig {

    /**
     * 服务名称
     */
    public static String serverName;
    /**
     * 工保通后端地址
     */
    public static String gbtBackUrl;
    /**
     * 工保通前端地址
     */
    public static String gbtFrontUrl;
    /**
     * 同步经纪人信息组的ID列表
     */
    public static String syncBrokerGroupIds;
    /**
     * crm权限用户ID列表
     */
    public static String crmAuthUserIds;
    /**
     * Rsa私钥
     */
    public static String privateKey;
    /**
     * 增值税专用发票服务ID列表
     */
    public static String specialInvoiceIds;
    /**
     * 增值税普通发票服务ID列表
     */
    public static String commonInvoiceIds;

    /**
     * 工保金跳转页面【用户已经是工保网经纪人】
     */
    public static String gongbaoJinJumpUrl;

    /**
     * 后台权限账户
     */
    @Value("${gongbao.bac_p_a}")
    public String bacPerAccount;

    /**
     * Rsa公钥
     */
    public static String publicKey;

    /**
     * 业务经纪人邀请链接
     */
    public static String brokerAppointLink;

    /**
     * 外部经纪人邀请链接
     */
    public static String outBrokerAppointLink;

    /**
     * 业务预约链接
     */
    public static String businessBookedLink;

    /**
     * 经纪人二维码模板地址
     */
    public static String brokerQRTempUrl;


    @Value("${spring.application.name}")
    public void setServerName(String serverName) {
        GongBaoConfig.serverName = serverName;
    }

    @Value("${gongbao.ton.backUrl}")
    public void setGbtBackUrl(String gbtBackUrl) {
        GongBaoConfig.gbtBackUrl = gbtBackUrl;
    }

    @Value("${gongbao.ton.frontUrl}")
    public void setGbtFrontUrl(String gbtFrontUrl) {
        GongBaoConfig.gbtFrontUrl = gbtFrontUrl;
    }

    @Value("${gongbao.privateKey}")
    public void setPrivateKey(String privateKey) {
        GongBaoConfig.privateKey = privateKey;
    }

    @Value("${gongbao.publicKey}")
    public void setPublicKey(String publicKey) {
        GongBaoConfig.publicKey = publicKey;
    }

    @Value("${invoice.specialInvoiceIds}")
    public void setSpecialInvoiceIds(String specialInvoiceIds){GongBaoConfig.specialInvoiceIds = specialInvoiceIds;}

    @Value("${invoice.commonInvoiceIds}")
    public void setCommonInvoiceIds(String commonInvoiceIds){GongBaoConfig.commonInvoiceIds = commonInvoiceIds;}

    @Value("${gongbao.syncBrokerGroupIds}")
    public void setSyncBrokerGroupIds(String syncBrokerGroupIds){GongBaoConfig.syncBrokerGroupIds = syncBrokerGroupIds;}

    @Value("${gongbao.crmAuthUserIds}")
    public void setAuthInsUserGroupIds(String crmAuthUserIds) {
        GongBaoConfig.crmAuthUserIds = crmAuthUserIds;
    }

    @Value("${gongbao.jin.jumpUrl}")
    public void setGongbaoJinJumpUrl(String gongbaoJinJumpUrl) {
        GongBaoConfig.gongbaoJinJumpUrl = gongbaoJinJumpUrl;
    }

    @Value("${gongbao.brokerAppointLink}")
    public void setBrokerAppointLink(String brokerAppointLink) {
        GongBaoConfig.brokerAppointLink = brokerAppointLink;
    }

    @Value("${gongbao.outBrokerAppointLink}")
    public void setOutBrokerAppointLink(String outBrokerAppointLink) {
        GongBaoConfig.outBrokerAppointLink = outBrokerAppointLink;
    }

    @Value("${gongbao.businessBookedLink}")
    public void setBusinessBookedLink(String businessBookedLink) {
        GongBaoConfig.businessBookedLink = businessBookedLink;
    }

    @Value("${gongbao.brokerQRTempUrl}")
    public void setBrokerQrTempUrl(String brokerQrTempUrl) {
        GongBaoConfig.brokerQRTempUrl = brokerQrTempUrl;
    }
}
