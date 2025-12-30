package com.gb.user.controller;

import cn.hutool.core.convert.Convert;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gb.push.param.MessageParam;
import com.gb.push.param.SendRequest;
import com.gb.user.entity.UserMessage;
import com.gb.user.service.UserMessageService;
import com.gb.utils.Json;
import com.gb.utils.RedisUtils;
import com.gb.utils.annotations.Methods;
import com.gb.utils.annotations.PreventRepeat;
import com.gb.utils.annotations.RequestRequired;
import com.gb.utils.constants.UniversalConstant;
import com.gb.utils.enumeration.ReturnCode;
import com.gb.utils.exception.BusinessException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.Objects;

/**
 * <p>
 * 用户消息表 前端控制器
 * </p>
 *
 * @author ranyang
 * @since 2021-06-01
 */
@Slf4j
@RequestRequired
@RestController
@Setter(onMethod_ = {@Autowired})
@RequestMapping("/user-message")
@Api(tags = "用户消息表")
@SuppressWarnings("all")
public class UserMessageController {

    /**
     * 用户消息表
     */
    private UserMessageService userMessageService;
    /**
     * redis操作
     */
    private StringRedisTemplate stringRedisTemplate;


    /**
     * 用户消息表集合查询
     *
     * @param current:
     * @param size:
     * @param userMessage:
     * @return com.utils.Json
     * @author ranyang
     * @since 2021-06-01
     */
    @Methods(methodsName = "用户消息表集合查询", methods = "select")
    @ApiOperation(value = "用户消息表集合查询", httpMethod = "GET", notes = "用户消息表集合查询", response = Json.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "current", value = "当前页", dataType = "int"),
            @ApiImplicitParam(name = "size", value = "分页显示数量", dataType = "int"),
    })
    @GetMapping("/select")
    public Json<IPage<UserMessage>> select(Integer current, Integer size, UserMessage userMessage, HttpServletRequest request) {
        Map<String, Object> userMap = RedisUtils.getToken(stringRedisTemplate, request);
        if (MapUtils.isEmpty(userMap) || null == userMap.get(UniversalConstant.ID)) {
            throw new BusinessException("登陆已过期, 请刷新页面重新登陆");
        }
        userMessage.setUserId(userMap.get(UniversalConstant.ID).toString());
        if (current != null && size != null) {
            Page page = new Page(current, size);
            //返回内容
            return new Json(ReturnCode.成功, userMessageService.pageEnhance(page, userMessage));
        } else {
            //返回内容
            return new Json(ReturnCode.成功, userMessageService.listEnhance(userMessage));
        }
    }


    /**
     * 用户消息表单条查询
     *
     * @param userMessage:
     * @return com.utils.Json
     * @author ranyang
     * @since 2021-06-01
     */
    @Methods(methodsName = "用户消息表单条查询", methods = "selectOne")
    @ApiOperation(value = "用户消息表单条查询", httpMethod = "GET", notes = "用户消息表单条查询", response = Json.class)
    @GetMapping("/selectOne")
    public Json<UserMessage> selectOne(UserMessage userMessage) {
        //返回内容
        return new Json(ReturnCode.成功, userMessageService.getOneEnhance(userMessage));
    }


    /**
     * 用户消息表总数查询
     *
     * @param userMessage:
     * @return com.utils.Json
     * @author ranyang
     * @since 2021-06-01
     */
    @Methods(methodsName = "用户消息表总数查询", methods = "count")
    @ApiOperation(value = "用户消息表总数查询", httpMethod = "GET", notes = "用户消息表总数查询", response = Json.class)
    @GetMapping(value = "/count")
    public Json<Integer> count(UserMessage userMessage) {
        //返回内容
        return new Json(ReturnCode.成功, userMessageService.countEnhance(userMessage));
    }

    /**
     * 用户消息表修改
     *
     * @param userMessage:
     * @return com.utils.Json
     * @author ranyang
     * @since 2021-06-01
     */
    @PreventRepeat
    @Methods(methodsName = "用户消息表修改", methods = "update")
    @ApiOperation(value = "用户消息表修改", httpMethod = "PUT", notes = "用户消息表修改", response = Json.class)
    @PutMapping("/update")
    public Json<Boolean> update(UserMessage userMessage, HttpServletRequest httpServletRequest) {
        //缓存取出用户
        Map<String, Object> u = RedisUtils.getToken(stringRedisTemplate, httpServletRequest);
        if (u != null) {
            userMessage.setModifyName(u.get(UniversalConstant.NAME) + "-" + u.get(UniversalConstant.ID));
        }
        if (Objects.isNull(u) || StringUtils.isBlank(u.get(UniversalConstant.ID).toString())) {
            throw new BusinessException("登陆已过期, 请刷新页面重新登陆");
        }
        userMessage.setUserId(u.get(UniversalConstant.ID).toString());
        //返回内容
        return new Json(ReturnCode.成功, userMessageService.updateEnhance(userMessage));
    }

    /**
     * 用户消息已读
     *
     * @return com.utils.Json
     * @author ranyang
     * @since 2021-06-01
     */
    @PreventRepeat
    @Methods(methodsName = "用户消息已读", methods = "msgRead")
    @ApiOperation(value = "用户消息已读", httpMethod = "PUT", notes = "用户消息已读", response = Json.class)
    @PutMapping("/msgRead")
    public Json<Boolean> msgRead(UserMessage userMessage, HttpServletRequest httpServletRequest) {
        //缓存取出用户
        Map<String, Object> u = RedisUtils.getToken(stringRedisTemplate, httpServletRequest);
        if (u != null) {
            userMessage.setModifyName(u.get(UniversalConstant.NAME) + "-" + u.get(UniversalConstant.ID));
        }
        if (Objects.isNull(u) || StringUtils.isBlank(u.get(UniversalConstant.ID).toString())) {
            throw new BusinessException("登陆已过期, 请刷新页面重新登陆");
        }
        userMessage.setUserId(u.get(UniversalConstant.ID).toString());

        //返回内容
        return new Json(ReturnCode.成功, userMessageService.msgRead(userMessage));
    }

    /**
     * 未读消息数量
     *
     * @param userMessage
     * @param request
     * @return
     */
    @Methods(methodsName = "未读消息数量", methods = "unreadMsgNum")
    @ApiOperation(value = "未读消息数量", httpMethod = "GET", notes = "未读消息数量", response = Json.class)
    @GetMapping("/unreadMsgNum")
    public Json<Integer> unreadMsgNum(UserMessage userMessage, HttpServletRequest request) {
        Map<String, Object> userMap = RedisUtils.getToken(stringRedisTemplate, request);
        if (MapUtils.isEmpty(userMap) || null == userMap.get(UniversalConstant.ID)) {
            throw new BusinessException("登陆已过期, 请刷新页面重新登陆");
        }
        userMessage.setUserId(userMap.get(UniversalConstant.ID).toString());
        String sourceId = Convert.toStr(userMap.get("sourceId"));

        //返回内容
        return new Json(ReturnCode.成功, userMessageService.unreadMsgNum(userMessage));
    }

}
