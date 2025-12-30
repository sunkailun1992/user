package com.gb.user.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gb.user.entity.UserInvoice;
import com.gb.user.handle.CommonHandle;
import com.gb.user.service.UserInvoiceService;
import com.gb.utils.Json;
import com.gb.utils.RedisUtils;
import com.gb.utils.annotations.Methods;
import com.gb.utils.annotations.PreventRepeat;
import com.gb.utils.annotations.RequestRequired;
import com.gb.utils.enumeration.ReturnCode;
import com.gb.utils.exception.ParameterNullException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
/**
 * <p>
 * 用户发票表 前端控制器
 * </p>
 *
 * @author sunx
 * @since 2021-05-25
 */
@Slf4j
@RequestRequired
@RestController
@Setter(onMethod_ = {@Autowired})
@RequestMapping("/user-invoice")
@Api(tags = "用户发票表")
public class UserInvoiceController {

    /**
     * 用户发票表
     */
    private UserInvoiceService userInvoiceService;
    /**
     * redis操作
     */
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 常规处理类
     */
    private CommonHandle commonHandle;

    /**
     * 用户发票表集合查询
     *
     * @param current:
     * @param size:
     * @param userInvoice:
     * @return com.utils.Json
     * @author sunx
     * @since 2021-05-25
     */
    @Methods(methodsName = "用户发票表集合查询", methods = "select")
    @ApiOperation(value = "用户发票表集合查询", httpMethod = "GET", notes = "用户发票表集合查询", response = Json.class)
    @ApiImplicitParams({
        @ApiImplicitParam(name = "current", value = "当前页", dataType = "int"),
        @ApiImplicitParam(name = "size", value = "分页显示数量", dataType = "int"),
        @ApiImplicitParam(name = "userId", value = "用户唯一标志", dataType = "String")
    })
    @GetMapping("/select")
    public Json<IPage<UserInvoice>> select(Integer current, Integer size, UserInvoice userInvoice) {
        if (current != null && size != null) {
            Page page = new Page(current, size);
            //返回内容
            return new Json(ReturnCode.成功, userInvoiceService.pageEnhance(page, userInvoice));
        } else {
            //返回内容
            return new Json(ReturnCode.成功, userInvoiceService.listEnhance(userInvoice));
        }
    }


    /**
     * 用户发票表单条查询
     *
     * @param userInvoice:
     * @return com.utils.Json
     * @author sunx
     * @since 2021-05-25
     */
    @Methods(methodsName = "用户发票表单条查询", methods = "selectOne")
    @ApiOperation(value = "用户发票表单条查询", httpMethod = "GET", notes = "用户发票表单条查询", response = Json.class)
    @GetMapping("/selectOne")
    public Json<UserInvoice> selectOne(UserInvoice userInvoice) {
        //返回内容
        return new Json(ReturnCode.成功, userInvoiceService.getOneEnhance(userInvoice));
    }


    /**
     * 用户发票表总数查询
     *
     * @param userInvoice:
     * @return com.utils.Json
     * @author sunx
     * @since 2021-05-25
     */
    @Methods(methodsName = "用户发票表总数查询", methods = "count")
    @ApiOperation(value = "用户发票表总数查询", httpMethod = "GET", notes = "用户发票表总数查询", response = Json.class)
    @GetMapping(value = "/count")
    public Json<Integer> count(UserInvoice userInvoice) {
        //返回内容
        return new Json(ReturnCode.成功, userInvoiceService.countEnhance(userInvoice));
    }


   /**
    * 用户发票表新增
    *
    * @param userInvoice:
    * @return com.utils.Json
    * @author sunx
    * @since 2021-05-25
    */
    @PreventRepeat
    @Methods(methodsName = "用户发票表新增", methods = "save")
    @ApiOperation(value = "用户发票表新增", httpMethod = "POST", notes = "用户发票表新增", response = Json.class)
    @PostMapping("/save")
    public Json<String> save(UserInvoice userInvoice, HttpServletRequest httpServletRequest) throws Exception {
        //1、根据类型判断是否是专票类型，校验参数
        Integer type = userInvoice.getType();
        if(null == type){
            log.error("发票类型为空！");
            throw new ParameterNullException("缺少新增发票抬头必要参数！");
        }
        if(1 == userInvoice.getType()){
            String errorMsg = commonHandle.validateParams(userInvoice);
            if(StringUtils.isNotBlank(errorMsg)){
                throw new ParameterNullException("缺少新增发票抬头必要参数！");
            }
        }
        //2、缓存取出用户
        Map<String, Object> u = RedisUtils.getToken(stringRedisTemplate, httpServletRequest);
        if(u != null){
            userInvoice.setCreateName(u.get("name") + "-" + u.get("id"));
        }
        userInvoiceService.saveEnhance(userInvoice);
        return new Json(ReturnCode.成功, userInvoice.getId());

    }


    /**
     * 用户发票表修改
     *
     * @param userInvoice:
     * @return com.utils.Json
     * @author sunx
     * @since 2021-05-25
     */
    @PreventRepeat
    @Methods(methodsName = "用户发票表修改", methods = "update")
    @ApiOperation(value = "用户发票表修改", httpMethod = "PUT", notes = "用户发票表修改", response = Json.class)
    @PutMapping("/update")
    public Json<Boolean> update(UserInvoice userInvoice, HttpServletRequest httpServletRequest) throws Exception {
        //1、根据类型判断是否是专票类型，校验参数
        Integer type = userInvoice.getType();
        if(null == type){
            log.error("发票类型为空！");
            throw new ParameterNullException("缺少编辑发票抬头必要参数！");
        }
        if(1 == userInvoice.getType()){
            String errorMsg = commonHandle.validateParams(userInvoice);
            if(StringUtils.isNotBlank(errorMsg)){
                throw new ParameterNullException("缺少编辑发票抬头必要参数！");
            }
        }
        //缓存取出用户
        Map<String, Object> u = RedisUtils.getToken(stringRedisTemplate, httpServletRequest);
        if(u != null){
            userInvoice.setModifyName(u.get("name") + "-" + u.get("id"));
        }
        //返回内容
        return new Json(ReturnCode.成功, userInvoiceService.updateEnhance(false, userInvoice));
    }

    /**
     * 设置默认状态
     *
     * @param state: 状态
     * @param id: 序列
     * @return com.utils.Json
     * @author sunx
     * @since 2021-05-25
     */
    @PreventRepeat
    @Methods(methodsName = "设置默认状态", methods = "setState")
    @ApiOperation(value = "设置默认状态", httpMethod = "PUT", notes = "设置默认状态", response = Json.class)
    @PutMapping("/setState")
    public Json<Boolean> setState(Integer state, String id, HttpServletRequest httpServletRequest) throws Exception {
        if(null == state || StringUtils.isBlank(id)){
            throw new ParameterNullException("缺少设置默认状态必要参数！");
        }
        String modifyName = StringUtils.EMPTY;
        //缓存取出用户
        Map<String, Object> u = RedisUtils.getToken(stringRedisTemplate, httpServletRequest);
        if(u != null){
            modifyName = (u.get("name") + "-" + u.get("id"));
        }
        UserInvoice userInvoice = new UserInvoice();
        userInvoice.setModifyName(modifyName);
        userInvoice.setState(state);
        userInvoice.setId(id);
        //返回内容
        return new Json(ReturnCode.成功, userInvoiceService.updateEnhance(true, userInvoice));
    }


    /**
     * 用户发票表删除
     *
     * @param userInvoice:
     * @return com.utils.Json
     * @author sunx
     * @since 2021-05-25
     */
    @Methods(methodsName = "用户发票表删除", methods = "remove")
    @ApiOperation(value = "用户发票表删除", httpMethod = "DELETE", notes = "用户发票表删除", response = Json.class)
    @DeleteMapping("/remove")
    public Json<Boolean> remove(UserInvoice userInvoice) {
        return new Json(ReturnCode.成功, userInvoiceService.removeEnhance(userInvoice));
    }


}
