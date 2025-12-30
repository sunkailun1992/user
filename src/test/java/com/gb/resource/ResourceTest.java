package com.gb.resource;

import com.gb.SpringTest;
import com.gb.permissions.entity.bo.ResourceBO;
import com.gb.permissions.service.ResourceService;
import com.gb.utils.JsonUtil;
import com.google.common.collect.Lists;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @ClassName ResourceTest
 * @Description 资源测试
 * @Author 孙凯伦
 * @Mobile 13777579028
 * @Email 376253703@qq.com
 * @Time 2021/11/8 9:07 AM
 */
public class ResourceTest extends SpringTest {


    /**
     * 资源表
     */
    @Autowired
    private ResourceService resourceService;


    @Test
    public void saveTest(){
        String json = "[\n" +
                "    {\n" +
                "        \"id\": \"4001\",\n" +
                "        \"superiorsId\": \"0\",\n" +
                "        \"name\": \"购物车\",\n" +
                "        \"value\": \"SHOPPING_CART\",\n" +
                "        \"button\": false,\n" +
                "        \"navigation\": true,\n" +
                "        \"systemId\": \"3\",\n" +
                "        \"api\": false,\n" +
                "        \"apiType\": 0\n" +
                "    },\n" +
                "    {\n" +
                "        \"id\": \"4002\",\n" +
                "        \"superiorsId\": \"4001\",\n" +
                "        \"name\": \"购物车列表\",\n" +
                "        \"value\": \"SHOPPING_CART_LIST\",\n" +
                "        \"button\": false,\n" +
                "        \"navigation\": true,\n" +
                "        \"systemId\": \"3\",\n" +
                "        \"api\": false,\n" +
                "        \"apiType\": 0\n" +
                "    },\n" +
                "    {\n" +
                "        \"id\": \"4003\",\n" +
                "        \"superiorsId\": \"4002\",\n" +
                "        \"name\": \"投保单\",\n" +
                "        \"value\": \"SHOPPING_CART_INSURANCE\",\n" +
                "        \"button\": true,\n" +
                "        \"navigation\": false,\n" +
                "        \"systemId\": \"3\",\n" +
                "        \"api\": false,\n" +
                "        \"apiType\": 0\n" +
                "    },\n" +
                "    {\n" +
                "        \"id\": \"4004\",\n" +
                "        \"superiorsId\": \"4002\",\n" +
                "        \"name\": \"反担保\",\n" +
                "        \"value\": \"SHOPPING_CART_GUARANTEE\",\n" +
                "        \"button\": true,\n" +
                "        \"navigation\": false,\n" +
                "        \"systemId\": \"3\",\n" +
                "        \"api\": false,\n" +
                "        \"apiType\": 0\n" +
                "    },\n" +
                "    {\n" +
                "        \"id\": \"4005\",\n" +
                "        \"superiorsId\": \"4002\",\n" +
                "        \"name\": \"审核成功待支付\",\n" +
                "        \"value\": \"SHOPPING_CART_AUDIT_SUCCESS\",\n" +
                "        \"button\": true,\n" +
                "        \"navigation\": false,\n" +
                "        \"systemId\": \"3\",\n" +
                "        \"api\": false,\n" +
                "        \"apiType\": 0\n" +
                "    },\n" +
                "    {\n" +
                "        \"id\": \"4006\",\n" +
                "        \"superiorsId\": \"4002\",\n" +
                "        \"name\": \"风控控制\",\n" +
                "        \"value\": \"SHOPPING_CART_RISk_CONTROL\",\n" +
                "        \"button\": true,\n" +
                "        \"navigation\": false,\n" +
                "        \"systemId\": \"3\",\n" +
                "        \"api\": false,\n" +
                "        \"apiType\": 0\n" +
                "    },\n" +
                "    {\n" +
                "        \"id\": \"4007\",\n" +
                "        \"superiorsId\": \"4002\",\n" +
                "        \"name\": \"审核未通过\",\n" +
                "        \"value\": \"SHOPPING_CART_AUDIT_FAILED\",\n" +
                "        \"button\": true,\n" +
                "        \"navigation\": false,\n" +
                "        \"systemId\": \"3\",\n" +
                "        \"api\": false,\n" +
                "        \"apiType\": 0\n" +
                "    },\n" +
                "    {\n" +
                "        \"id\": \"4008\",\n" +
                "        \"superiorsId\": \"4002\",\n" +
                "        \"name\": \"重新投保\",\n" +
                "        \"value\": \"SHOPPING_CART_REINSURE\",\n" +
                "        \"button\": true,\n" +
                "        \"navigation\": false,\n" +
                "        \"systemId\": \"3\",\n" +
                "        \"api\": false,\n" +
                "        \"apiType\": 0\n" +
                "    },\n" +
                "    {\n" +
                "        \"id\": \"4009\",\n" +
                "        \"superiorsId\": \"4002\",\n" +
                "        \"name\": \"完善投保信息\",\n" +
                "        \"value\": \"SHOPPING_CART_IMPROVE_INFO\",\n" +
                "        \"button\": true,\n" +
                "        \"navigation\": false,\n" +
                "        \"systemId\": \"3\",\n" +
                "        \"api\": false,\n" +
                "        \"apiType\": 0\n" +
                "    },\n" +
                "    {\n" +
                "        \"id\": \"4010\",\n" +
                "        \"superiorsId\": \"4002\",\n" +
                "        \"name\": \"查看投保详情\",\n" +
                "        \"value\": \"SHOPPING_CART_VIEW_INSURANCE_DETAIL\",\n" +
                "        \"button\": true,\n" +
                "        \"navigation\": false,\n" +
                "        \"systemId\": \"3\",\n" +
                "        \"api\": false,\n" +
                "        \"apiType\": 0\n" +
                "    },\n" +
                "    {\n" +
                "        \"id\": \"4011\",\n" +
                "        \"superiorsId\": \"4002\",\n" +
                "        \"name\": \"待投保人确认\",\n" +
                "        \"value\": \"SHOPPING_CART_INSURE_CONFIRM\",\n" +
                "        \"button\": true,\n" +
                "        \"navigation\": false,\n" +
                "        \"systemId\": \"3\",\n" +
                "        \"api\": false,\n" +
                "        \"apiType\": 0\n" +
                "    },\n" +
                "    {\n" +
                "        \"id\": \"4012\",\n" +
                "        \"superiorsId\": \"4002\",\n" +
                "        \"name\": \"修改投保信息\",\n" +
                "        \"value\": \"SHOPPING_CART_EDIT_INSURE\",\n" +
                "        \"button\": true,\n" +
                "        \"navigation\": false,\n" +
                "        \"systemId\": \"3\",\n" +
                "        \"api\": false,\n" +
                "        \"apiType\": 0\n" +
                "    },\n" +
                "    {\n" +
                "        \"id\": \"4013\",\n" +
                "        \"superiorsId\": \"4002\",\n" +
                "        \"name\": \"更新投保单\",\n" +
                "        \"value\": \"SHOPPING_CART_UPDATE_INSURE\",\n" +
                "        \"button\": true,\n" +
                "        \"navigation\": false,\n" +
                "        \"systemId\": \"3\",\n" +
                "        \"api\": false,\n" +
                "        \"apiType\": 0\n" +
                "    },\n" +
                "    {\n" +
                "        \"id\": \"4014\",\n" +
                "        \"superiorsId\": \"4002\",\n" +
                "        \"name\": \"删除\",\n" +
                "        \"value\": \"SHOPPING_CART_DELETE\",\n" +
                "        \"button\": true,\n" +
                "        \"navigation\": false,\n" +
                "        \"systemId\": \"3\",\n" +
                "        \"api\": false,\n" +
                "        \"apiType\": 0\n" +
                "    },\n" +
                "    {\n" +
                "        \"id\": \"4015\",\n" +
                "        \"superiorsId\": \"4001\",\n" +
                "        \"name\": \"去结算\",\n" +
                "        \"value\": \"SHOPPING_CART_SETTLE\",\n" +
                "        \"button\": false,\n" +
                "        \"navigation\": true,\n" +
                "        \"systemId\": \"3\",\n" +
                "        \"api\": false,\n" +
                "        \"apiType\": 0\n" +
                "    },\n" +
                "    {\n" +
                "        \"id\": \"4016\",\n" +
                "        \"superiorsId\": \"4015\",\n" +
                "        \"name\": \"立即支付\",\n" +
                "        \"value\": \"SHOPPING_CART_IMMEDIATE_PAYMENT\",\n" +
                "        \"button\": true,\n" +
                "        \"navigation\": false,\n" +
                "        \"systemId\": \"3\",\n" +
                "        \"api\": false,\n" +
                "        \"apiType\": 0\n" +
                "    },\n" +
                "    {\n" +
                "        \"id\": \"4017\",\n" +
                "        \"superiorsId\": \"0\",\n" +
                "        \"name\": \"支付\",\n" +
                "        \"value\": \"PAY\",\n" +
                "        \"button\": false,\n" +
                "        \"navigation\": true,\n" +
                "        \"systemId\": \"3\",\n" +
                "        \"api\": false,\n" +
                "        \"apiType\": 0\n" +
                "    },\n" +
                "    {\n" +
                "        \"id\": \"4018\",\n" +
                "        \"superiorsId\": \"4017\",\n" +
                "        \"name\": \"支付详情\",\n" +
                "        \"value\": \"IMMEDIATE_PAYMENT\",\n" +
                "        \"button\": false,\n" +
                "        \"navigation\": true,\n" +
                "        \"systemId\": \"3\",\n" +
                "        \"api\": false,\n" +
                "        \"apiType\": 0\n" +
                "    },\n" +
                "    {\n" +
                "        \"id\": \"4019\",\n" +
                "        \"superiorsId\": \"4017\",\n" +
                "        \"name\": \"已支付详情\",\n" +
                "        \"value\": \"PAYMENT_PAID\",\n" +
                "        \"button\": false,\n" +
                "        \"navigation\": true,\n" +
                "        \"systemId\": \"3\",\n" +
                "        \"api\": false,\n" +
                "        \"apiType\": 0\n" +
                "    },\n" +
                "    {\n" +
                "        \"id\": \"4020\",\n" +
                "        \"superiorsId\": \"4017\",\n" +
                "        \"name\": \"支付完成详情\",\n" +
                "        \"value\": \"PAYMENT_COMPLETED\",\n" +
                "        \"button\": false,\n" +
                "        \"navigation\": true,\n" +
                "        \"systemId\": \"3\",\n" +
                "        \"api\": false,\n" +
                "        \"apiType\": 0\n" +
                "    },\n" +
                "    {\n" +
                "        \"id\": \"4021\",\n" +
                "        \"superiorsId\": \"4017\",\n" +
                "        \"name\": \"支付成功详情\",\n" +
                "        \"value\": \"PAYMENT_SUCCESS\",\n" +
                "        \"button\": false,\n" +
                "        \"navigation\": true,\n" +
                "        \"systemId\": \"3\",\n" +
                "        \"api\": false,\n" +
                "        \"apiType\": 0\n" +
                "    },\n" +
                "    {\n" +
                "        \"id\": \"4022\",\n" +
                "        \"superiorsId\": \"0\",\n" +
                "        \"name\": \"个人中心\",\n" +
                "        \"value\": \"PERSONAL_CENTER\",\n" +
                "        \"button\": false,\n" +
                "        \"navigation\": true,\n" +
                "        \"systemId\": \"3\",\n" +
                "        \"api\": false,\n" +
                "        \"apiType\": 0\n" +
                "    },\n" +
                "    {\n" +
                "        \"id\": \"4023\",\n" +
                "        \"superiorsId\": \"4022\",\n" +
                "        \"name\": \"我的工保\",\n" +
                "        \"value\": \"PERSONAL_CENTER_MY_GN\",\n" +
                "        \"button\": false,\n" +
                "        \"navigation\": true,\n" +
                "        \"systemId\": \"3\",\n" +
                "        \"api\": false,\n" +
                "        \"apiType\": 0\n" +
                "    },\n" +
                "    {\n" +
                "        \"id\": \"4024\",\n" +
                "        \"superiorsId\": \"4023\",\n" +
                "        \"name\": \"订单管理\",\n" +
                "        \"value\": \"INSURER_ORDER_MANAGE\",\n" +
                "        \"button\": false,\n" +
                "        \"navigation\": true,\n" +
                "        \"systemId\": \"3\",\n" +
                "        \"api\": false,\n" +
                "        \"apiType\": 0\n" +
                "    },\n" +
                "    {\n" +
                "        \"id\": \"4025\",\n" +
                "        \"superiorsId\": \"4024\",\n" +
                "        \"name\": \"投保订单\",\n" +
                "        \"value\": \"INSURER_ORDER_MANAGE_INSURE_ORDER\",\n" +
                "        \"button\": false,\n" +
                "        \"navigation\": true,\n" +
                "        \"systemId\": \"3\",\n" +
                "        \"api\": false,\n" +
                "        \"apiType\": 0\n" +
                "    },\n" +
                "    {\n" +
                "        \"id\": \"4026\",\n" +
                "        \"superiorsId\": \"4025\",\n" +
                "        \"name\": \"投保订单列表\",\n" +
                "        \"value\": \"INSURANCE_ORDER_LIST\",\n" +
                "        \"button\": false,\n" +
                "        \"navigation\": true,\n" +
                "        \"systemId\": \"3\",\n" +
                "        \"api\": false,\n" +
                "        \"apiType\": 0\n" +
                "    },\n" +
                "    {\n" +
                "        \"id\": \"4027\",\n" +
                "        \"superiorsId\": \"4025\",\n" +
                "        \"name\": \"投保订单详情\",\n" +
                "        \"value\": \"INSURANCE_ORDER_DETAIL\",\n" +
                "        \"button\": false,\n" +
                "        \"navigation\": true,\n" +
                "        \"systemId\": \"3\",\n" +
                "        \"api\": false,\n" +
                "        \"apiType\": 0\n" +
                "    },\n" +
                "    {\n" +
                "        \"id\": \"4028\",\n" +
                "        \"superiorsId\": \"4027\",\n" +
                "        \"name\": \"审核支付凭证\",\n" +
                "        \"value\": \"INSURER_ORDER_DETAIL_PAYMENT_VOUCHER\",\n" +
                "        \"button\": true,\n" +
                "        \"navigation\": false,\n" +
                "        \"systemId\": \"3\",\n" +
                "        \"api\": false,\n" +
                "        \"apiType\": 0\n" +
                "    },\n" +
                "    {\n" +
                "        \"id\": \"4029\",\n" +
                "        \"superiorsId\": \"4027\",\n" +
                "        \"name\": \"下载投保单\",\n" +
                "        \"value\": \"INSURER_ORDER_DETAIL_DOWNLOAD_APPLICATION\",\n" +
                "        \"button\": true,\n" +
                "        \"navigation\": false,\n" +
                "        \"systemId\": \"3\",\n" +
                "        \"api\": false,\n" +
                "        \"apiType\": 0\n" +
                "    },\n" +
                "    {\n" +
                "        \"id\": \"4030\",\n" +
                "        \"superiorsId\": \"4027\",\n" +
                "        \"name\": \"下载反担保\",\n" +
                "        \"value\": \"INSURER_ORDER_DETAIL_DOWNLOAD_COUNTER_GUARANTEE\",\n" +
                "        \"button\": true,\n" +
                "        \"navigation\": false,\n" +
                "        \"systemId\": \"3\",\n" +
                "        \"api\": false,\n" +
                "        \"apiType\": 0\n" +
                "    },\n" +
                "    {\n" +
                "        \"id\": \"4031\",\n" +
                "        \"superiorsId\": \"4025\",\n" +
                "        \"name\": \"配置\",\n" +
                "        \"value\": \"INSURANCE_SETTING_ABOUT\",\n" +
                "        \"button\": false,\n" +
                "        \"navigation\": true,\n" +
                "        \"systemId\": \"3\",\n" +
                "        \"api\": false,\n" +
                "        \"apiType\": 0\n" +
                "    },\n" +
                "    {\n" +
                "        \"id\": \"4032\",\n" +
                "        \"superiorsId\": \"4031\",\n" +
                "        \"name\": \"配置支付方式\",\n" +
                "        \"value\": \"INSURER_ORDER_DETAIL_CONFIG_PAY_METHOD\",\n" +
                "        \"button\": true,\n" +
                "        \"navigation\": false,\n" +
                "        \"systemId\": \"3\",\n" +
                "        \"api\": false,\n" +
                "        \"apiType\": 0\n" +
                "    },\n" +
                "    {\n" +
                "        \"id\": \"4033\",\n" +
                "        \"superiorsId\": \"4031\",\n" +
                "        \"name\": \"配置保单信息\",\n" +
                "        \"value\": \"INSURER_ORDER_DETAIL_CONFIG_POLICY_INFO\",\n" +
                "        \"button\": true,\n" +
                "        \"navigation\": false,\n" +
                "        \"systemId\": \"3\",\n" +
                "        \"api\": false,\n" +
                "        \"apiType\": 0\n" +
                "    },\n" +
                "    {\n" +
                "        \"id\": \"4034\",\n" +
                "        \"superiorsId\": \"4025\",\n" +
                "        \"name\": \"保险审核\",\n" +
                "        \"value\": \"INSURANCE_INSURER_AUDIT\",\n" +
                "        \"button\": false,\n" +
                "        \"navigation\": true,\n" +
                "        \"systemId\": \"3\",\n" +
                "        \"api\": false,\n" +
                "        \"apiType\": 0\n" +
                "    },\n" +
                "    {\n" +
                "        \"id\": \"4035\",\n" +
                "        \"superiorsId\": \"4034\",\n" +
                "        \"name\": \"拒保\",\n" +
                "        \"value\": \"INSURE_AUDIT_REFUSE_INSURANCE\",\n" +
                "        \"button\": true,\n" +
                "        \"navigation\": false,\n" +
                "        \"systemId\": \"3\",\n" +
                "        \"api\": false,\n" +
                "        \"apiType\": 0\n" +
                "    },\n" +
                "    {\n" +
                "        \"id\": \"4036\",\n" +
                "        \"superiorsId\": \"4034\",\n" +
                "        \"name\": \"批量审核\",\n" +
                "        \"value\": \"INSURE_AUDIT_BATCH_AUDIT\",\n" +
                "        \"button\": true,\n" +
                "        \"navigation\": false,\n" +
                "        \"systemId\": \"3\",\n" +
                "        \"api\": false,\n" +
                "        \"apiType\": 0\n" +
                "    },\n" +
                "    {\n" +
                "        \"id\": \"4037\",\n" +
                "        \"superiorsId\": \"4034\",\n" +
                "        \"name\": \"全部通过\",\n" +
                "        \"value\": \"INSURE_AUDIT_ALL_PASS_DATA\",\n" +
                "        \"button\": true,\n" +
                "        \"navigation\": false,\n" +
                "        \"systemId\": \"3\",\n" +
                "        \"api\": false,\n" +
                "        \"apiType\": 0\n" +
                "    },\n" +
                "    {\n" +
                "        \"id\": \"4038\",\n" +
                "        \"superiorsId\": \"4034\",\n" +
                "        \"name\": \"补充资料\",\n" +
                "        \"value\": \"INSURE_AUDIT_SUPPLY_INFO\",\n" +
                "        \"button\": true,\n" +
                "        \"navigation\": false,\n" +
                "        \"systemId\": \"3\",\n" +
                "        \"api\": false,\n" +
                "        \"apiType\": 0\n" +
                "    },\n" +
                "    {\n" +
                "        \"id\": \"4039\",\n" +
                "        \"superiorsId\": \"4034\",\n" +
                "        \"name\": \"提交审核结果\",\n" +
                "        \"value\": \"INSURE_AUDIT_SUBMIT_AUDIT_RESULT\",\n" +
                "        \"button\": true,\n" +
                "        \"navigation\": false,\n" +
                "        \"systemId\": \"3\",\n" +
                "        \"api\": false,\n" +
                "        \"apiType\": 0\n" +
                "    },\n" +
                "    {\n" +
                "        \"id\": \"4040\",\n" +
                "        \"superiorsId\": \"4023\",\n" +
                "        \"name\": \"客户服务\",\n" +
                "        \"value\": \"INSURER_CUSTOMER_SERVICE\",\n" +
                "        \"button\": false,\n" +
                "        \"navigation\": true,\n" +
                "        \"systemId\": \"3\",\n" +
                "        \"api\": false,\n" +
                "        \"apiType\": 0\n" +
                "    },\n" +
                "    {\n" +
                "        \"id\": \"4041\",\n" +
                "        \"superiorsId\": \"4040\",\n" +
                "        \"name\": \"发票管理\",\n" +
                "        \"value\": \"INSURER_CUSTOMER_SERVICE_INVOICE\",\n" +
                "        \"button\": false,\n" +
                "        \"navigation\": true,\n" +
                "        \"systemId\": \"3\",\n" +
                "        \"api\": false,\n" +
                "        \"apiType\": 0\n" +
                "    },\n" +
                "    {\n" +
                "        \"id\": \"4042\",\n" +
                "        \"superiorsId\": \"4041\",\n" +
                "        \"name\": \"发票列表\",\n" +
                "        \"value\": \"INSURANCE_INVOICE_LIST\",\n" +
                "        \"button\": false,\n" +
                "        \"navigation\": true,\n" +
                "        \"systemId\": \"3\",\n" +
                "        \"api\": false,\n" +
                "        \"apiType\": 0\n" +
                "    },\n" +
                "    {\n" +
                "        \"id\": \"4043\",\n" +
                "        \"superiorsId\": \"4041\",\n" +
                "        \"name\": \"发票详情\",\n" +
                "        \"value\": \"INSURANCE_INVOICE_DETAIL\",\n" +
                "        \"button\": false,\n" +
                "        \"navigation\": true,\n" +
                "        \"systemId\": \"3\",\n" +
                "        \"api\": false,\n" +
                "        \"apiType\": 0\n" +
                "    },\n" +
                "    {\n" +
                "        \"id\": \"4044\",\n" +
                "        \"superiorsId\": \"4043\",\n" +
                "        \"name\": \"开票设置\",\n" +
                "        \"value\": \"INSURER_INVOICE_DETAIL_INVOICE_SETTINGS\",\n" +
                "        \"button\": true,\n" +
                "        \"navigation\": true,\n" +
                "        \"systemId\": \"3\",\n" +
                "        \"api\": false,\n" +
                "        \"apiType\": 0\n" +
                "    },\n" +
                "    {\n" +
                "        \"id\": \"4045\",\n" +
                "        \"superiorsId\": \"4043\",\n" +
                "        \"name\": \"快递信息\",\n" +
                "        \"value\": \"INSURER_INVOICE_DETAIL_EXPRESS_INFO\",\n" +
                "        \"button\": true,\n" +
                "        \"navigation\": false,\n" +
                "        \"systemId\": \"3\",\n" +
                "        \"api\": false,\n" +
                "        \"apiType\": 0\n" +
                "    },\n" +
                "    {\n" +
                "        \"id\": \"4046\",\n" +
                "        \"superiorsId\": \"4040\",\n" +
                "        \"name\": \"批改服务\",\n" +
                "        \"value\": \"INSURER_CUSTOMER_SERVICE_CORRECT\",\n" +
                "        \"button\": false,\n" +
                "        \"navigation\": true,\n" +
                "        \"systemId\": \"3\",\n" +
                "        \"api\": false,\n" +
                "        \"apiType\": 0\n" +
                "    },\n" +
                "    {\n" +
                "        \"id\": \"4047\",\n" +
                "        \"superiorsId\": \"4046\",\n" +
                "        \"name\": \"批改列表\",\n" +
                "        \"value\": \"INSURANCE_CORRECTION_LIS\",\n" +
                "        \"button\": false,\n" +
                "        \"navigation\": true,\n" +
                "        \"systemId\": \"3\",\n" +
                "        \"api\": false,\n" +
                "        \"apiType\": 0\n" +
                "    },\n" +
                "    {\n" +
                "        \"id\": \"4048\",\n" +
                "        \"superiorsId\": \"4046\",\n" +
                "        \"name\": \"批改详情\",\n" +
                "        \"value\": \"INSURANCE_CORRECTION_DETAIL\",\n" +
                "        \"button\": false,\n" +
                "        \"navigation\": true,\n" +
                "        \"systemId\": \"3\",\n" +
                "        \"api\": false,\n" +
                "        \"apiType\": 0\n" +
                "    },\n" +
                "    {\n" +
                "        \"id\": \"4049\",\n" +
                "        \"superiorsId\": \"4048\",\n" +
                "        \"name\": \"拒绝退保\",\n" +
                "        \"value\": \"CORRECT_REFUSE_SURRENDER\",\n" +
                "        \"button\": true,\n" +
                "        \"navigation\": false,\n" +
                "        \"systemId\": \"3\",\n" +
                "        \"api\": false,\n" +
                "        \"apiType\": 0\n" +
                "    },\n" +
                "    {\n" +
                "        \"id\": \"4050\",\n" +
                "        \"superiorsId\": \"4048\",\n" +
                "        \"name\": \"通过退保\",\n" +
                "        \"value\": \"CORRECT_PASS_SURRENDER\",\n" +
                "        \"button\": true,\n" +
                "        \"navigation\": false,\n" +
                "        \"systemId\": \"3\",\n" +
                "        \"api\": false,\n" +
                "        \"apiType\": 0\n" +
                "    },\n" +
                "    {\n" +
                "        \"id\": \"4051\",\n" +
                "        \"superiorsId\": \"4022\",\n" +
                "        \"name\": \"账户设置\",\n" +
                "        \"value\": \"ACCOUNT_SET_MY_ACCOUNT\",\n" +
                "        \"button\": false,\n" +
                "        \"navigation\": true,\n" +
                "        \"systemId\": \"3\",\n" +
                "        \"api\": false,\n" +
                "        \"apiType\": 0\n" +
                "    },\n" +
                "    {\n" +
                "        \"id\": \"4052\",\n" +
                "        \"superiorsId\": \"4051\",\n" +
                "        \"name\": \"个人信息\",\n" +
                "        \"value\": \"ACCOUNT_SET_MY_ACCOUNT_INFO\",\n" +
                "        \"button\": false,\n" +
                "        \"navigation\": true,\n" +
                "        \"systemId\": \"3\",\n" +
                "        \"api\": false,\n" +
                "        \"apiType\": 0\n" +
                "    },\n" +
                "    {\n" +
                "        \"id\": \"4053\",\n" +
                "        \"superiorsId\": \"4051\",\n" +
                "        \"name\": \"账户安全\",\n" +
                "        \"value\": \"ACCOUNT_SET_MY_ACCOUNT_SECURITY\",\n" +
                "        \"button\": false,\n" +
                "        \"navigation\": true,\n" +
                "        \"systemId\": \"3\",\n" +
                "        \"api\": false,\n" +
                "        \"apiType\": 0\n" +
                "    },\n" +
                "    {\n" +
                "        \"id\": \"4054\",\n" +
                "        \"superiorsId\": \"4053\",\n" +
                "        \"name\": \"修改密码\",\n" +
                "        \"value\": \"ACCOUNT_SECURITY_UPDATE_PASSWORD\",\n" +
                "        \"button\": false,\n" +
                "        \"navigation\": true,\n" +
                "        \"systemId\": \"3\",\n" +
                "        \"api\": false,\n" +
                "        \"apiType\": 0\n" +
                "    },\n" +
                "    {\n" +
                "        \"id\": \"4055\",\n" +
                "        \"superiorsId\": \"4053\",\n" +
                "        \"name\": \"修改手机号\",\n" +
                "        \"value\": \"ACCOUNT_SECURITY_UPDATE_MOBILE\",\n" +
                "        \"button\": false,\n" +
                "        \"navigation\": true,\n" +
                "        \"systemId\": \"3\",\n" +
                "        \"api\": false,\n" +
                "        \"apiType\": 0\n" +
                "    },\n" +
                "    {\n" +
                "        \"id\": \"4056\",\n" +
                "        \"superiorsId\": \"4051\",\n" +
                "        \"name\": \"发票抬头\",\n" +
                "        \"value\": \"ACCOUNT_SET_MY_ACCOUNT_INVOICE\",\n" +
                "        \"button\": false,\n" +
                "        \"navigation\": true,\n" +
                "        \"systemId\": \"3\",\n" +
                "        \"api\": false,\n" +
                "        \"apiType\": 0\n" +
                "    },\n" +
                "    {\n" +
                "        \"id\": \"4057\",\n" +
                "        \"superiorsId\": \"4056\",\n" +
                "        \"name\": \"发票抬头列表\",\n" +
                "        \"value\": \"INVOICE_HEADER\",\n" +
                "        \"button\": false,\n" +
                "        \"navigation\": true,\n" +
                "        \"systemId\": \"3\",\n" +
                "        \"api\": false,\n" +
                "        \"apiType\": 0\n" +
                "    },\n" +
                "    {\n" +
                "        \"id\": \"4058\",\n" +
                "        \"superiorsId\": \"4056\",\n" +
                "        \"name\": \"新增发票抬头\",\n" +
                "        \"value\": \"MY_COUNT_SETTING_ADD_INVOICE\",\n" +
                "        \"button\": true,\n" +
                "        \"navigation\": false,\n" +
                "        \"systemId\": \"3\",\n" +
                "        \"api\": false,\n" +
                "        \"apiType\": 0\n" +
                "    },\n" +
                "    {\n" +
                "        \"id\": \"4059\",\n" +
                "        \"superiorsId\": \"4056\",\n" +
                "        \"name\": \"编辑\",\n" +
                "        \"value\": \"MY_COUNT_SETTING_EDIT_INVOICE\",\n" +
                "        \"button\": true,\n" +
                "        \"navigation\": false,\n" +
                "        \"systemId\": \"3\",\n" +
                "        \"api\": false,\n" +
                "        \"apiType\": 0\n" +
                "    },\n" +
                "    {\n" +
                "        \"id\": \"4060\",\n" +
                "        \"superiorsId\": \"4056\",\n" +
                "        \"name\": \"删除\",\n" +
                "        \"value\": \"MY_COUNT_SETTING_DELETE_INVOICE\",\n" +
                "        \"button\": true,\n" +
                "        \"navigation\": false,\n" +
                "        \"systemId\": \"3\",\n" +
                "        \"api\": false,\n" +
                "        \"apiType\": 0\n" +
                "    },\n" +
                "    {\n" +
                "        \"id\": \"4061\",\n" +
                "        \"superiorsId\": \"4056\",\n" +
                "        \"name\": \"设为默认\",\n" +
                "        \"value\": \"MY_COUNT_SETTING_DEFAULT_INVOICE\",\n" +
                "        \"button\": true,\n" +
                "        \"navigation\": false,\n" +
                "        \"systemId\": \"3\",\n" +
                "        \"api\": false,\n" +
                "        \"apiType\": 0\n" +
                "    },\n" +
                "    {\n" +
                "        \"id\": \"4062\",\n" +
                "        \"superiorsId\": \"4051\",\n" +
                "        \"name\": \"收件地址\",\n" +
                "        \"value\": \"ACCOUNT_SET_MY_ACCOUNT_ADDRESS\",\n" +
                "        \"button\": false,\n" +
                "        \"navigation\": true,\n" +
                "        \"systemId\": \"3\",\n" +
                "        \"api\": false,\n" +
                "        \"apiType\": 0\n" +
                "    },\n" +
                "    {\n" +
                "        \"id\": \"4063\",\n" +
                "        \"superiorsId\": \"4062\",\n" +
                "        \"name\": \"收件地址列表\",\n" +
                "        \"value\": \"RECEIVING_ADDRESS\",\n" +
                "        \"button\": false,\n" +
                "        \"navigation\": true,\n" +
                "        \"systemId\": \"3\",\n" +
                "        \"api\": false,\n" +
                "        \"apiType\": 0\n" +
                "    },\n" +
                "    {\n" +
                "        \"id\": \"4064\",\n" +
                "        \"superiorsId\": \"4062\",\n" +
                "        \"name\": \"新增收件地址\",\n" +
                "        \"value\": \"MY_COUNT_SETTING_ADD_ADDRESS\",\n" +
                "        \"button\": true,\n" +
                "        \"navigation\": false,\n" +
                "        \"systemId\": \"3\",\n" +
                "        \"api\": false,\n" +
                "        \"apiType\": 0\n" +
                "    },\n" +
                "    {\n" +
                "        \"id\": \"4065\",\n" +
                "        \"superiorsId\": \"4062\",\n" +
                "        \"name\": \"编辑\",\n" +
                "        \"value\": \"MY_COUNT_SETTING_EDIT_ADDRESS\",\n" +
                "        \"button\": true,\n" +
                "        \"navigation\": false,\n" +
                "        \"systemId\": \"3\",\n" +
                "        \"api\": false,\n" +
                "        \"apiType\": 0\n" +
                "    },\n" +
                "    {\n" +
                "        \"id\": \"4066\",\n" +
                "        \"superiorsId\": \"4062\",\n" +
                "        \"name\": \"删除\",\n" +
                "        \"value\": \"MY_COUNT_SETTING_DELETE_ADDRESS\",\n" +
                "        \"button\": true,\n" +
                "        \"navigation\": false,\n" +
                "        \"systemId\": \"3\",\n" +
                "        \"api\": false,\n" +
                "        \"apiType\": 0\n" +
                "    },\n" +
                "    {\n" +
                "        \"id\": \"4067\",\n" +
                "        \"superiorsId\": \"4062\",\n" +
                "        \"name\": \"设为默认\",\n" +
                "        \"value\": \"MY_COUNT_SETTING_DEFAULT_ADDRESS\",\n" +
                "        \"button\": true,\n" +
                "        \"navigation\": false,\n" +
                "        \"systemId\": \"3\",\n" +
                "        \"api\": false,\n" +
                "        \"apiType\": 0\n" +
                "    },\n" +
                "    {\n" +
                "        \"id\": \"4068\",\n" +
                "        \"superiorsId\": \"4022\",\n" +
                "        \"name\": \"消息中心\",\n" +
                "        \"value\": \"MESSAGE_CENTER\",\n" +
                "        \"button\": false,\n" +
                "        \"navigation\": true,\n" +
                "        \"systemId\": \"3\",\n" +
                "        \"api\": false,\n" +
                "        \"apiType\": 0\n" +
                "    },\n" +
                "    {\n" +
                "        \"id\": \"4069\",\n" +
                "        \"superiorsId\": \"4068\",\n" +
                "        \"name\": \"消息列表\",\n" +
                "        \"value\": \"MESSAGE_CENTER_LIST\",\n" +
                "        \"button\": false,\n" +
                "        \"navigation\": true,\n" +
                "        \"systemId\": \"3\",\n" +
                "        \"api\": false,\n" +
                "        \"apiType\": 0\n" +
                "    },\n" +
                "    {\n" +
                "        \"id\": \"4070\",\n" +
                "        \"superiorsId\": \"4068\",\n" +
                "        \"name\": \"查看\",\n" +
                "        \"value\": \"MESSAGE_CENTER_VIEW\",\n" +
                "        \"button\": false,\n" +
                "        \"navigation\": true,\n" +
                "        \"systemId\": \"3\",\n" +
                "        \"api\": false,\n" +
                "        \"apiType\": 0\n" +
                "    },\n" +
                "    {\n" +
                "        \"id\": \"4071\",\n" +
                "        \"superiorsId\": \"4068\",\n" +
                "        \"name\": \"已读\",\n" +
                "        \"value\": \"MESSAGE_CENTER_READ\",\n" +
                "        \"button\": true,\n" +
                "        \"navigation\": false,\n" +
                "        \"systemId\": \"3\",\n" +
                "        \"api\": false,\n" +
                "        \"apiType\": 0\n" +
                "    },\n" +
                "    {\n" +
                "        \"id\": \"4072\",\n" +
                "        \"superiorsId\": \"4068\",\n" +
                "        \"name\": \"全部标为已读\",\n" +
                "        \"value\": \"MESSAGE_CENTER_READ_ALL\",\n" +
                "        \"button\": true,\n" +
                "        \"navigation\": false,\n" +
                "        \"systemId\": \"3\",\n" +
                "        \"api\": false,\n" +
                "        \"apiType\": 0\n" +
                "    }\n" +
                "]";
        List<ResourceBO> list = JsonUtil.list(json,ResourceBO.class);
        //新增
        list.stream().forEach(resource -> {{
            resourceService.saveEnhance(resource);
        }});
    }
}
