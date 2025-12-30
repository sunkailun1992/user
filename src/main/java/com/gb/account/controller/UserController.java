package com.gb.account.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.TimeInterval;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gb.account.entity.bo.UserBO;
import com.gb.account.entity.query.UserQuery;
import com.gb.account.entity.vo.UserVO;
import com.gb.account.service.UserService;
import com.gb.user.entity.model.request.UserInfoUpdateRequest;
import com.gb.user.service.AsyncRegisterService;
import com.gb.user.service.GbtTransferProcessService;
import com.gb.utils.Json;
import com.gb.utils.RedisUtils;
import com.gb.utils.annotations.Methods;
import com.gb.utils.annotations.PreventRepeat;
import com.gb.utils.annotations.RequestRequired;
import com.gb.utils.constants.UniversalConstant;
import com.gb.utils.enumeration.ReturnCode;
import com.gb.utils.exception.ParameterNullException;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

import static com.gb.user.enums.ForwardProcesEnum.用户信息更新;


/**
 * Created with IntelliJ IDEA.
 * @author:     	孙凯伦
 * @since:   	    2021-10-21 11:06:07
 * @description:	TODO  用户表，Comment请求层
 * @source:  	    代码生成器
 */
@Slf4j
@RequestRequired
@ApiSupport(author = "孙凯伦")
@Setter(onMethod_ = {@Autowired})
@RestController
@RequestMapping("/user")
@Api(tags = "用户表")
public class UserController {


    /**
     * 用户表
     */
    private UserService userService;

    /**
     * 请求工保通服务中转处理服务
     */
    private GbtTransferProcessService gbtTransferProcessService;

    /**
     * redis操作
     */
    private StringRedisTemplate stringRedisTemplate;

    /**
     * redis操作
     */
    private AsyncRegisterService asyncRegisterService;


    /**
     * 用户表集合分页查询
     *
     * @param current:
     * @param size:
     * @param userQuery:
     * @return com.utils.Json
     * @author 孙凯伦
     * @since 2021-10-21 11:06:07
     */
    @Methods(methodsName = "用户表集合分页查询", methods = "select")
    @ApiOperation(value = "用户表集合分页查询", httpMethod = "GET", notes = "用户表集合查询", response = Json.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "current", value = "当前页", dataType = "int", required = true),
            @ApiImplicitParam(name = "size", value = "分页显示数量", dataType = "int", required = true),
    })
    @GetMapping("/select")
    public Json<Page<UserVO>> select(@Validated(value = UserQuery.Select.class) UserQuery userQuery, Integer current, Integer size) throws Exception {
        //返回内容
        return new Json(ReturnCode.成功, userService.pageEnhance(new Page(current, size), userQuery));
    }


    /**
     * 用户表集合查询
     *
     * @param userQuery:
     * @return com.utils.Json
     * @author 孙凯伦
     * @since 2021-10-21 11:06:07
     */
    @Methods(methodsName = "用户表集合查询", methods = "selectList")
    @ApiOperation(value = "用户表集合查询", httpMethod = "GET", notes = "用户表集合查询", response = Json.class)
    @GetMapping("/selectList")
    public Json<List<UserVO>> selectList(@Validated(value = UserQuery.SelectList.class) UserQuery userQuery) {
        //返回内容
        return new Json(ReturnCode.成功, userService.listEnhance(userQuery));
    }


    /**
     * 用户表单条查询
     *
     * @param userQuery:
     * @return com.utils.Json
     * @author 孙凯伦
     * @since 2021-10-21 11:06:07
     */
    @Methods(methodsName = "用户表单条查询", methods = "selectOne")
    @ApiOperation(value = "用户表单条查询", httpMethod = "GET", notes = "用户表单条查询", response = Json.class)
    @GetMapping("/selectOne")
    public Json<UserVO> selectOne(@Validated(value = UserQuery.SelectOne.class) UserQuery userQuery) {
        //返回内容
        return new Json(ReturnCode.成功, userService.getOneEnhance(userQuery));
    }


    /**
     * 用户表总数查询
     *
     * @param userQuery:
     * @return com.utils.Json
     * @author 孙凯伦
     * @since 2021-10-21 11:06:07
     */
    @Methods(methodsName = "用户表总数查询", methods = "count")
    @ApiOperation(value = "用户表总数查询", httpMethod = "GET", notes = "用户表总数查询", response = Json.class)
    @GetMapping(value = "/count")
    public Json<Integer> count(@Validated(value = UserQuery.Count.class) UserQuery userQuery) {
        //返回内容
        return new Json(ReturnCode.成功, userService.countEnhance(userQuery));
    }


    /**
     * 用户表新增
     *
     * @param userBO:
     * @return com.utils.Json
     * @author 孙凯伦
     * @since 2021-10-21 11:06:07
     */
    @PreventRepeat
    @Methods(methodsName = "用户表新增", methods = "save")
    @ApiOperation(value = "用户表新增", httpMethod = "POST", notes = "用户表新增", response = Json.class)
    @ApiOperationSupport(ignoreParameters = {"id","createDateTime","createName","modifyDateTime","modifyName","isDelete","version"})
    @PostMapping("/save")
    public Json<String> save(@Validated(value = UserBO.Save.class) UserBO userBO, HttpServletRequest httpServletRequest) {
        //1、校验
        if(StringUtils.isBlank(httpServletRequest.getHeader(UniversalConstant.BUSINESS_DETAILS)) || StringUtils.isBlank(httpServletRequest.getHeader(UniversalConstant.SOURCE_VALUE_CODE))) {
            throw new ParameterNullException("请求头缺少必要参数！");
        }
        //2、缓存取出用户
        Map<String, Object> u = RedisUtils.getToken(stringRedisTemplate, httpServletRequest);
        if(u != null){
            userBO.setCreateName(u.get(UniversalConstant.NAME) + "-" + u.get(UniversalConstant.ID));
        }
        //3、校验账户信息是否存在重复数据
        userService.checkUserRepeat(null, userBO.getUserName(), userBO.getUserExtendsBO());
        UserBO bo = gbtTransferProcessService.saveUserInfo(httpServletRequest, userBO);
        //4、返回内容
        return new Json(ReturnCode.成功, userService.saveEnhance(bo));
    }


    /**
     * 根据手机号查询对应的用户ID
     *
     * @param userInfoList: G端保单融合请求的用户信息列表
     * @return com.utils.Json
     * @author sunx
     * @since 2021-10-21 11:06:07
     */
    @Methods(methodsName = "根据手机号查询对应的用户ID", methods = "queryUserIdsByMobiles")
    @ApiOperation(value = "根据手机号查询对应的用户ID", httpMethod = "POST", notes = "根据手机号查询对应的用户ID", response = Json.class)
    @PostMapping("/queryUserIdsByMobiles")
    public Json<String> queryUserIdsByMobiles(@RequestBody List<Map<String, String>> userInfoList, HttpServletRequest httpServletRequest) {
        TimeInterval timeInterval = DateUtil.timer();
        if(CollectionUtils.isEmpty(userInfoList)) {
            throw new ParameterNullException("缺少必传参数！");
        }
        Map<String, String> resultMap = gbtTransferProcessService.queryUserIdsByMobiles(httpServletRequest, userInfoList);
        log.debug("queryUserIdsByMobiles-userInfoList长度：{}，执行时间：{}ms", userInfoList.size(), timeInterval.intervalMs());
        return new Json(ReturnCode.成功,  resultMap);
    }

    /**
     * 根据手机号异步注册
     *
     * @param userInfoList: G端保单融合请求的用户信息列表
     * @return com.utils.Json
     * @author sunx
     * @since 2021-10-21 11:06:07
     */
    @Methods(methodsName = "根据手机号异步注册", methods = "asyncRegisterByMobile")
    @ApiOperation(value = "根据手机号异步注册", httpMethod = "POST", notes = "根据手机号异步注册", response = Json.class)
    @PostMapping("/asyncRegisterByMobile")
    public Json<Boolean> asyncRegisterByMobile(@RequestBody List<Map<String, String>> userInfoList, HttpServletRequest httpServletRequest) {
        TimeInterval timeInterval = DateUtil.timer();
        if(CollectionUtils.isEmpty(userInfoList)) {
            throw new ParameterNullException("缺少必传参数！");
        }
        asyncRegisterService.asyncRegisterByMobile(httpServletRequest, userInfoList);
        log.debug("asyncRegisterByMobile-userInfoList长度：{}，执行时间：{}ms", userInfoList.size(), timeInterval.intervalMs());
        return new Json(ReturnCode.成功,  true);
    }


    /**
     * 用户表修改
     *
     * @param userBO:
     * @return com.utils.Json
     * @author 孙凯伦
     * @since 2021-10-21 11:06:07
     */
    @PreventRepeat
    @Methods(methodsName = "用户表修改", methods = "update")
    @ApiOperation(value = "用户表修改", httpMethod = "PUT", notes = "用户表修改", response = Json.class)
    @ApiOperationSupport(ignoreParameters = {"createDateTime","createName","modifyDateTime","modifyName","isDelete","version"})
    @PutMapping("/update")
    public Json<Boolean> update(@Validated(value = UserBO.Update.class) UserBO userBO, HttpServletRequest httpServletRequest) {
        log.debug("工保网-修改用户信息入口-请求参数：{}", JSON.toJSONString(userBO));
        //0、缓存取出用户
        Map<String, Object> u = RedisUtils.getToken(stringRedisTemplate, httpServletRequest);
        if(u != null){
            userBO.setModifyName(u.get(UniversalConstant.NAME) + "-" + u.get(UniversalConstant.ID));
        }
        //1、校验账户信息是否存在重复数据
        userService.checkUserRepeat(userBO.getId(), userBO.getUserName(), userBO.getUserExtendsBO());
        gbtTransferProcessService.updateUserInfo(httpServletRequest, userBO);
        //2、返回内容
        return new Json(ReturnCode.成功, userService.updateEnhance(userBO));
    }

    /**
     * 修改密码
     *
     * @param id: 用户序列
     * @param password: 密码
     * @param rePassword: 确认密码
     * @return com.utils.Json
     * @author 孙凯伦
     * @since 2021-10-21 11:06:07
     */
    @PreventRepeat
    @Methods(methodsName = "修改密码", methods = "password")
    @ApiOperation(value = "修改密码", httpMethod = "PUT", notes = "修改密码", response = Json.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "用户id", dataType = "String", required = true),
            @ApiImplicitParam(name = "password", value = "密码", dataType = "String", required = true),
            @ApiImplicitParam(name = "rePassword", value = "密码", dataType = "String", required = true),
    })
    @PutMapping("/password")
    public Json<Boolean> password(String id, String password, String rePassword, HttpServletRequest httpServletRequest) {
        Map<String, Object> u = RedisUtils.getToken(stringRedisTemplate, httpServletRequest);
        if(MapUtils.isEmpty(u)){
            throw new ParameterNullException("token无效，请重新登录");
        }
        if(StringUtils.isBlank(id) || StringUtils.isBlank(password) || StringUtils.isBlank(rePassword) ){
            throw new ParameterNullException("修改密码必传参数不能为空!");
        }
        if(!StringUtils.equals(rePassword, password)){
            throw new ParameterNullException("密码与确认密码不一致!");
        }
        UserInfoUpdateRequest userInfoUpdateRequest = new UserInfoUpdateRequest();
        userInfoUpdateRequest.setRePassword(rePassword).setPassword(password).setUserCode(id).setUpdateType("UPDATE_PASSWORD");
        gbtTransferProcessService.dealWith(用户信息更新, httpServletRequest, userInfoUpdateRequest);
        //返回内容
        return new Json(ReturnCode.成功, userService.password(new UserBO(){{
            setModifyName(u.get(UniversalConstant.NAME) + "-" + u.get(UniversalConstant.ID));
            setId(id);
            setPassword(password);
        }}));
    }


    /**
     * 用户表删除
     *
     * @param userBO:
     * @return com.utils.Json
     * @author 孙凯伦
     * @since 2021-10-21 11:06:07
     */
    @Methods(methodsName = "用户表删除", methods = "remove")
    @ApiOperation(value = "用户表删除", httpMethod = "DELETE", notes = "用户表删除", response = Json.class)
    @ApiOperationSupport(includeParameters = {"id"})
    @DeleteMapping("/remove")
    public Json<Boolean> remove(@Validated(value = UserBO.Remove.class) UserBO userBO) {
        return new Json(ReturnCode.成功, userService.removeEnhance(userBO));
    }

}