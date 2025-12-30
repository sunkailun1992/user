package com.gb.user.controller;

import cn.hutool.core.convert.Convert;
import com.alibaba.fastjson.JSONObject;
import com.gb.push.config.GetuiConfig;
import com.gb.push.param.MessageParam;
import com.gb.push.param.SendRequest;
import com.gb.utils.constants.UniversalConstant;
import com.gb.utils.exception.BusinessException;
import com.gb.utils.exception.UserException;
import com.getui.push.v2.sdk.common.ApiResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Setter;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;
import com.gb.utils.annotations.RequestRequired;
import com.gb.utils.annotations.Methods;
import com.gb.utils.Json;
import com.gb.utils.RedisUtils;
import com.gb.utils.annotations.PreventRepeat;

import javax.servlet.http.HttpServletRequest;

import org.springframework.validation.annotation.Validated;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import com.gb.utils.enumeration.ReturnCode;
import com.gb.user.service.UserTokenService;
import com.gb.user.entity.query.UserTokenQuery;
import com.gb.user.entity.vo.UserTokenVO;
import com.gb.user.entity.bo.UserTokenBO;


/**
 * TODO 用户设备信息表，Comment请求层
 * 代码生成器
 *
 * @author wgs
 * @className UserTokenController
 * @time 2022-01-20 03:40:09
 */
@Slf4j
@RequestRequired
@ApiSupport(author = "wgs")
@Setter(onMethod_ = {@Autowired})
@RestController
@RequestMapping("/user-token")
@Api(tags = "用户设备信息表")
public class UserTokenController {


    /**
     * 用户设备信息表
     */
    private UserTokenService userTokenService;
    /**
     * redis操作
     */
    private StringRedisTemplate stringRedisTemplate;

    GetuiConfig getuiConfig;


    /**
     * TODO 分页
     *
     * @param userTokenQuery 用户设备信息表
     * @param current
     * @param size
     * @return Json<Page < UserTokenVO>>
     * @author wgs
     * @methodName select
     * @time 2022-01-20 03:40:09
     */
    @Methods(methodsName = "用户设备信息表集合分页查询", methods = "select")
    @ApiOperation(value = "用户设备信息表集合分页查询", httpMethod = "GET", notes = "用户设备信息表集合查询", response = Json.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "current", value = "当前页", dataType = "int", required = true),
            @ApiImplicitParam(name = "size", value = "分页显示数量", dataType = "int", required = true),
    })
    @GetMapping("/select")
    public Json<Page<UserTokenVO>> select(@Validated(value = UserTokenQuery.Select.class) UserTokenQuery userTokenQuery, Integer current, Integer size) {
        //返回内容
        return new Json(ReturnCode.成功, userTokenService.pageEnhance(new Page(current, size), userTokenQuery));
    }


    /**
     * TODO 集合
     *
     * @param userTokenQuery 用户设备信息表
     * @return Json<List < UserTokenVO>>
     * @author wgs
     * @methodName selectList
     * @time 2022-01-20 03:40:09
     */
    @Methods(methodsName = "用户设备信息表集合查询", methods = "selectList")
    @ApiOperation(value = "用户设备信息表集合查询", httpMethod = "GET", notes = "用户设备信息表集合查询", response = Json.class)
    @GetMapping("/selectList")
    public Json<List<UserTokenVO>> selectList(@Validated(value = UserTokenQuery.SelectList.class) UserTokenQuery userTokenQuery) {
        //返回内容
        return new Json(ReturnCode.成功, userTokenService.listEnhance(userTokenQuery));
    }


    /**
     * TODO 单条
     *
     * @param userTokenQuery 用户设备信息表
     * @return Json<UserTokenVO>
     * @author wgs
     * @methodName selectOne
     * @time 2022-01-20 03:40:09
     */
    @Methods(methodsName = "用户设备信息表单条查询", methods = "selectOne")
    @ApiOperation(value = "用户设备信息表单条查询", httpMethod = "GET", notes = "用户设备信息表单条查询", response = Json.class)
    @GetMapping("/selectOne")
    public Json<UserTokenVO> selectOne(@Validated(value = UserTokenQuery.SelectOne.class) UserTokenQuery userTokenQuery) {
        //返回内容
        return new Json(ReturnCode.成功, userTokenService.getOneEnhance(userTokenQuery));
    }


    /**
     * TODO 总数
     *
     * @param userTokenQuery 用户设备信息表
     * @return Json<Integer>
     * @author wgs
     * @methodName count
     * @time 2022-01-20 03:40:09
     */
    @Methods(methodsName = "用户设备信息表总数查询", methods = "count")
    @ApiOperation(value = "用户设备信息表总数查询", httpMethod = "GET", notes = "用户设备信息表总数查询", response = Json.class)
    @GetMapping(value = "/count")
    public Json<Integer> count(@Validated(value = UserTokenQuery.Count.class) UserTokenQuery userTokenQuery) {
        //返回内容
        return new Json(ReturnCode.成功, userTokenService.countEnhance(userTokenQuery));
    }


    /**
     * TODO 新增
     *
     * @param userTokenBO 用户设备信息表
     * @return Json<String>
     * @author wgs
     * @methodName save
     * @time 2022-01-20 03:40:09
     */
    @PreventRepeat
    @Methods(methodsName = "用户设备信息绑定", methods = "bindToken")
    @ApiOperation(value = "用户设备信息绑定", httpMethod = "POST", notes = "用户设备信息绑定", response = Json.class)
    @ApiOperationSupport(ignoreParameters = {"id", "createDateTime", "createName", "modifyDateTime", "modifyName", "isDelete", "version"})
    @ApiImplicitParams({
            @ApiImplicitParam(name = "cid", value = "设备号", required = true, type = "string"),
            @ApiImplicitParam(name = "tokenType", value = "设备类型（0：IOS，1：Android）", required = true, type = "string"),
            @ApiImplicitParam(name = "cid", value = "验证类型", dataType = "int", required = true)
    })
    @PostMapping("/bindToken")
    public Json<String> bindToken(@Validated(value = UserTokenBO.Save.class) UserTokenBO userTokenBO, HttpServletRequest httpServletRequest) {
        //缓存取出用户
        Map<String, Object> u = RedisUtils.getToken(stringRedisTemplate, httpServletRequest);
        if (u == null) {
            throw new UserException("用户登录信息异常！");
        }
        userTokenBO.setCreateName(u.get("name") + "-" + u.get("id"));
        userTokenBO.setUserId(String.valueOf(u.get("id")));
        //返回内容
        return new Json(ReturnCode.成功, userTokenService.bindToken(userTokenBO));
    }


    @PreventRepeat
    @Methods(methodsName = "个推测试", methods = "pushMsg")
    @ApiOperation(value = "个推测试", httpMethod = "POST", notes = "个推测试", response = Json.class)
    @ApiOperationSupport(ignoreParameters = {"id", "createDateTime", "createName", "modifyDateTime", "modifyName", "isDelete", "version"})
    @PostMapping("/pushMsg")
    public Json<String> pushMsg(MessageParam messageParam, HttpServletRequest httpServletRequest) {
        //缓存取出用户
        Map<String, Object> u = RedisUtils.getToken(stringRedisTemplate, httpServletRequest);
        if (u == null) {
            throw new UserException("用户登录信息异常！");
        }
        SendRequest sendRequest = SendRequest.builder()
                .userId(Convert.toStr(u.get(UniversalConstant.ID)))
                .messageParam(messageParam)
                .build();

        //返回内容
        return new Json(ReturnCode.成功, sendRequest);
    }


    /**
     * TODO 新增
     *
     * @param userTokenBO 用户设备信息表
     * @return Json<String>
     * @author wgs
     * @methodName save
     * @time 2022-01-20 03:40:09
     */
    @PreventRepeat
    @Methods(methodsName = "用户设备信息表新增", methods = "save")
    @ApiOperation(value = "用户设备信息表新增", httpMethod = "POST", notes = "用户设备信息表新增", response = Json.class)
    @ApiOperationSupport(ignoreParameters = {"id", "createDateTime", "createName", "modifyDateTime", "modifyName", "isDelete", "version"})
    @PostMapping("/save")
    public Json<String> save(@Validated(value = UserTokenBO.Save.class) UserTokenBO userTokenBO, HttpServletRequest httpServletRequest) {
        //缓存取出用户
        Map<String, Object> u = RedisUtils.getToken(stringRedisTemplate, httpServletRequest);
        if (u != null) {
            userTokenBO.setCreateName(u.get("name") + "-" + u.get("id"));
        }
        //返回内容
        return new Json(ReturnCode.成功, userTokenService.saveEnhance(userTokenBO));
    }


    /**
     * TODO 修改
     *
     * @param userTokenBO 用户设备信息表
     * @return Json<Boolean>
     * @author wgs
     * @methodName update
     * @time 2022-01-20 03:40:09
     */
    @PreventRepeat
    @Methods(methodsName = "用户设备信息表修改", methods = "update")
    @ApiOperation(value = "用户设备信息表修改", httpMethod = "PUT", notes = "用户设备信息表修改", response = Json.class)
    @ApiOperationSupport(ignoreParameters = {"createDateTime", "createName", "modifyDateTime", "modifyName", "isDelete", "version"})
    @PutMapping("/update")
    public Json<Boolean> update(@Validated(value = UserTokenBO.Update.class) UserTokenBO userTokenBO, HttpServletRequest httpServletRequest) {
        //缓存取出用户
        Map<String, Object> u = RedisUtils.getToken(stringRedisTemplate, httpServletRequest);
        if (u != null) {
            userTokenBO.setModifyName(u.get("name") + "-" + u.get("id"));
        }
        //返回内容
        return new Json(ReturnCode.成功, userTokenService.updateEnhance(userTokenBO));
    }


    /**
     * TODO 删除
     *
     * @param userTokenBO 用户设备信息表
     * @return Json<Boolean>
     * @author wgs
     * @methodName remove
     * @time 2022-01-20 03:40:09
     */
    @Methods(methodsName = "用户设备信息表删除", methods = "remove")
    @ApiOperation(value = "用户设备信息表删除", httpMethod = "DELETE", notes = "用户设备信息表删除", response = Json.class)
    @ApiOperationSupport(includeParameters = {"id"})
    @DeleteMapping("/remove")
    public Json<Boolean> remove(@Validated(value = UserTokenBO.Remove.class) UserTokenBO userTokenBO) {
        return new Json(ReturnCode.成功, userTokenService.removeEnhance(userTokenBO));
    }

}