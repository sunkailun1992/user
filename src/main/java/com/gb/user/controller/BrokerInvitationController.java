package com.gb.user.controller;

import com.gb.user.entity.bo.InviteLinkBO;
import com.gb.user.service.BrokerInvitationService;
import com.gb.utils.Json;
import com.gb.utils.RedisUtils;
import com.gb.utils.annotations.RequestRequired;
import com.gb.utils.constants.UniversalConstant;
import com.gb.utils.enumeration.ReturnCode;
import com.gb.utils.exception.UserException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.Objects;

/**
 * <p>
 * 经纪人邀请控制器
 * </p>
 *
 * @author 孙馨
 * @since 2020-12-29
 */
@Slf4j
@RequestRequired
@RestController
@Setter(onMethod_ = {@Autowired})
@RequestMapping("/invite")
@Api(tags = "经纪人邀请控制器")
public class BrokerInvitationController {

    private BrokerInvitationService brokerInvitationService;

    private StringRedisTemplate stringRedisTemplate;

    /**
     * 根据userId查询邀请链接相关信息
     *
     * @param bo: 类型  0：外部经纪人  1：业务经纪人
     * @return com.utils.Json
     * @author sunx
     * @since 2021/3/19  4:35 下午
     */
    @ResponseBody
    @ApiOperation(value = "根据userId查询邀请链接相关信息", httpMethod = "GET", notes = "根据userId查询邀请链接相关信息", response = String.class)
    @GetMapping("/queryInviteLinkByUserId")
    public Json<Object> queryInviteLinkByUserId(@Validated(value = InviteLinkBO.Select.class) InviteLinkBO bo) throws Exception {
        return new Json(ReturnCode.成功, brokerInvitationService.queryInviteLinkByUserId(bo));
    }

    /**
     * 下载二维码
     *
     * @param bo: 类型  0：外部经纪人  1：业务经纪人
     * @return com.utils.Json
     * @author sunx
     * @since 2021/3/19  4:35 下午
     */
    @ResponseBody
    @ApiOperation(value = "下载二维码", httpMethod = "POST", notes = "下载二维码", response = String.class)
    @PostMapping("/downQRCode")
    public Json<Object> downQrCode(HttpServletRequest httpServletRequest, @Validated(value = InviteLinkBO.Down.class) InviteLinkBO bo) {
        Map<String, Object> userMap = RedisUtils.getToken(stringRedisTemplate, httpServletRequest);
        if (MapUtils.isEmpty(userMap) || Objects.isNull(userMap.get(UniversalConstant.ID))) {
            log.error("请求参数的用户【token：{}】，缓存中未找到该用户信息！", httpServletRequest.getHeader(UniversalConstant.TOKEN));
            throw new UserException("用户登录信息异常！");
        }
        String userId = String.valueOf(userMap.get(UniversalConstant.ID));
        if (StringUtils.isBlank(userId) || !StringUtils.equals(userId, bo.getUserId())) {
            log.error("请求参数的用户【请求userId：{}】，缓存中的用户信息【缓存userId：{}】，不一致！", userId, bo.getUserId());
            throw new UserException("用户信息不一致！");
        }
        return new Json(ReturnCode.成功, brokerInvitationService.downQrCode(bo));
    }


}
