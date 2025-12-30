package com.gb.user.enums;

import com.gb.utils.exception.ParameterNullException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.Optional;

/**
 * 查询类型
 *
 * @author: ranyang
 * @Date: 2021/04/06 14:25
 * @descript:
 */
@Getter
@AllArgsConstructor
@SuppressWarnings("all")
public enum UserMsgTypeEnum {
    //投保订单修改
    //投保订单待支付
    //订单完成
    //订单关闭
    //发票开具成功
    //退保审核通过
    //退保审核未通过
    //投保订单修改
    //投保订单待支付
    //订单完成
    //订单关闭
    //退保审核通过
    //退保审核未通过

    //投保订单审核
    //投保订单审核
    //发票开具申请
    //退保订单审核

    CAST_EDIT(1, "投保订单修改"),
    CAST_WAITING_PAY(2, "投保订单待支付"),
    CAST_COMPLETE(3, "订单完成"),
    CAST_CLOSED(4, "订单关闭"),
    INVOICE_SUCCESS(5, "发票开具成功"),
    PASS_REFUND_REVIEW(6, "退保审核通过"),
    REFUSED_REFUND_REVIEW(7, "退保审核未通过"),
    CAST_WAITING_REVIEW(8, "投保订单申请审核"),
    INVOICE_APPLY(9, "发票申请审核"),
    REFUND_REVIEW_APPLY(10, "退保申请审核"),

    ENTERPRISE_REVIEW_SUCCESS(11,"企业认证成功"),
    ENTERPRISE_REVIEW_FAIL(12,"企业认证拒绝"),
    ENTERPRISE_BOOK_VALID(13,"授权书过期提醒"),

    INSURANCE_CONSULTATION(1000, "投保咨询"),
    BROKER_CONSULTATION(1001, "经纪人咨询"),
    ;

    private int code;
    private String desc;

    public static String getAppCodeEnum(Integer code) {
        Optional<UserMsgTypeEnum> codeEnum = Arrays.stream(values()).filter(x -> x.getCode() == code).findFirst();
        return codeEnum.isPresent() ? codeEnum.get().getDesc() : StringUtils.EMPTY;
    }

}
