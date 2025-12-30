package com.gb.user.enums;

import com.gb.utils.enumeration.ReturnCode;
import com.gb.utils.exception.ParameterNullException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.Optional;

/**
 * 转发处理返回码值枚举
 *
 * @author: sunx
 * @Date: 2021/04/06 14:25
 * @descript:
 */
@Getter
@AllArgsConstructor
@SuppressWarnings("all")
@Slf4j
public enum ForwardProcessCodeEnum {
    SUCCESS("00001", ReturnCode.成功),
    PASSWORD_ERROR("1305", ReturnCode.用户密码错误),
    PHONE_EXIST("13002", ReturnCode.该手机号已注册),
    PHONE_REGISTER("13029", ReturnCode.该手机号已注册),
    CODE_OUT("13003", ReturnCode.授权已过期),
    PARAM_NULL("13018", ReturnCode.请求必填参数为空),
    GBT_COMMON("00002", ReturnCode.用户端错误),
    GBT_SYS_EXC("00003", ReturnCode.用户请求服务异常),
    GBT_TOKEN_ERR("910", ReturnCode.无效TOKEN),
    USER_NOTEXIST("13004", ReturnCode.用户账户不存在);

    private String code;
    private ReturnCode returnCode;

    public static ReturnCode getGbwCodeEnum(String code) {
        if (StringUtils.isBlank(code)) {
            throw new ParameterNullException("查询本系统错误码对应的工保通错误码不能为空！");
        }
        Optional<ForwardProcessCodeEnum> codeEnum = Arrays.stream(values()).filter(x -> (StringUtils.equals(code, x.getCode()))).findFirst();
        return codeEnum.isPresent() ? codeEnum.get().getReturnCode() : ReturnCode.用户端错误;
    }

}
