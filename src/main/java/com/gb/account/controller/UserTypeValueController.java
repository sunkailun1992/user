package com.gb.account.controller;

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
import com.gb.account.service.UserTypeValueService;
import com.gb.account.entity.query.UserTypeValueQuery;
import com.gb.account.entity.vo.UserTypeValueVO;
import com.gb.account.entity.bo.UserTypeValueBO;


/**
 * Created with IntelliJ IDEA.
 * @author:     	孙凯伦
 * @since:   	    2021-10-21 01:49:04
 * @description:	TODO  用户类型值表，Comment请求层
 * @source:  	    代码生成器
 */
@Slf4j
@RequestRequired
@ApiSupport(author = "孙凯伦")
@Setter(onMethod_ = {@Autowired})
@RestController
@RequestMapping("/userTypeValue")
@Api(tags = "用户类型值表")
public class UserTypeValueController {


    /**
     * 用户类型值表
     */
    private UserTypeValueService userTypeValueService;
    /**
     * redis操作
     */
    private StringRedisTemplate stringRedisTemplate;


    /**
     * 用户类型值表集合分页查询
     *
     * @param current:
     * @param size:
     * @param userTypeValueQuery:
     * @return com.utils.Json
     * @author 孙凯伦
     * @since 2021-10-21 01:49:04
     */
    @Methods(methodsName = "用户类型值表集合分页查询", methods = "select")
    @ApiOperation(value = "用户类型值表集合分页查询", httpMethod = "GET", notes = "用户类型值表集合查询", response = Json.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "current", value = "当前页", dataType = "int", required = true),
            @ApiImplicitParam(name = "size", value = "分页显示数量", dataType = "int", required = true),
    })
    @GetMapping("/select")
    public Json<Page<UserTypeValueVO>> select(@Validated(value = UserTypeValueQuery.Select.class) UserTypeValueQuery userTypeValueQuery, Integer current, Integer size) {
        //返回内容
        return new Json(ReturnCode.成功, userTypeValueService.pageEnhance(new Page(current, size), userTypeValueQuery));
    }


    /**
     * 用户类型值表集合查询
     *
     * @param userTypeValueQuery:
     * @return com.utils.Json
     * @author 孙凯伦
     * @since 2021-10-21 01:49:04
     */
    @Methods(methodsName = "用户类型值表集合查询", methods = "selectList")
    @ApiOperation(value = "用户类型值表集合查询", httpMethod = "GET", notes = "用户类型值表集合查询", response = Json.class)
    @GetMapping("/selectList")
    public Json<List<UserTypeValueVO>> selectList(@Validated(value = UserTypeValueQuery.SelectList.class) UserTypeValueQuery userTypeValueQuery) {
        //返回内容
        return new Json(ReturnCode.成功, userTypeValueService.listEnhance(userTypeValueQuery));
    }


    /**
     * 用户类型值表单条查询
     *
     * @param userTypeValueQuery:
     * @return com.utils.Json
     * @author 孙凯伦
     * @since 2021-10-21 01:49:04
     */
    @Methods(methodsName = "用户类型值表单条查询", methods = "selectOne")
    @ApiOperation(value = "用户类型值表单条查询", httpMethod = "GET", notes = "用户类型值表单条查询", response = Json.class)
    @GetMapping("/selectOne")
    public Json<UserTypeValueVO> selectOne(@Validated(value = UserTypeValueQuery.SelectOne.class) UserTypeValueQuery userTypeValueQuery) {
        //返回内容
        return new Json(ReturnCode.成功, userTypeValueService.getOneEnhance(userTypeValueQuery));
    }


    /**
     * 用户类型值表总数查询
     *
     * @param userTypeValueQuery:
     * @return com.utils.Json
     * @author 孙凯伦
     * @since 2021-10-21 01:49:04
     */
    @Methods(methodsName = "用户类型值表总数查询", methods = "count")
    @ApiOperation(value = "用户类型值表总数查询", httpMethod = "GET", notes = "用户类型值表总数查询", response = Json.class)
    @GetMapping(value = "/count")
    public Json<Integer> count(@Validated(value = UserTypeValueQuery.Count.class) UserTypeValueQuery userTypeValueQuery) {
        //返回内容
        return new Json(ReturnCode.成功, userTypeValueService.countEnhance(userTypeValueQuery));
    }


   /**
    * 用户类型值表新增
    *
    * @param userTypeValueBO:
    * @return com.utils.Json
    * @author 孙凯伦
    * @since 2021-10-21 01:49:04
    */
    @PreventRepeat
    @Methods(methodsName = "用户类型值表新增", methods = "save")
    @ApiOperation(value = "用户类型值表新增", httpMethod = "POST", notes = "用户类型值表新增", response = Json.class)
    @ApiOperationSupport(ignoreParameters = {"id","createDateTime","createName","modifyDateTime","modifyName","isDelete","version"})
    @PostMapping("/save")
    public Json<String> save(@Validated(value = UserTypeValueBO.Save.class) UserTypeValueBO userTypeValueBO, HttpServletRequest httpServletRequest) {
        //缓存取出用户
        Map<String, Object> u = RedisUtils.getToken(stringRedisTemplate, httpServletRequest);
        if(u != null){
            userTypeValueBO.setCreateName(u.get("name") + "-" + u.get("id"));
        }
        //返回内容
        return new Json(ReturnCode.成功, userTypeValueService.saveEnhance(userTypeValueBO));
    }


    /**
     * 用户类型值表修改
     *
     * @param userTypeValueBO:
     * @return com.utils.Json
     * @author 孙凯伦
     * @since 2021-10-21 01:49:04
     */
    @PreventRepeat
    @Methods(methodsName = "用户类型值表修改", methods = "update")
    @ApiOperation(value = "用户类型值表修改", httpMethod = "PUT", notes = "用户类型值表修改", response = Json.class)
    @ApiOperationSupport(ignoreParameters = {"createDateTime","createName","modifyDateTime","modifyName","isDelete","version"})
    @PutMapping("/update")
    public Json<Boolean> update(@Validated(value = UserTypeValueBO.Update.class) UserTypeValueBO userTypeValueBO, HttpServletRequest httpServletRequest) {
        //缓存取出用户
        Map<String, Object> u = RedisUtils.getToken(stringRedisTemplate, httpServletRequest);
        if(u != null){
            userTypeValueBO.setModifyName(u.get("name") + "-" + u.get("id"));
        }
        //返回内容
        return new Json(ReturnCode.成功, userTypeValueService.updateEnhance(userTypeValueBO));
    }


    /**
     * 用户类型值表删除
     *
     * @param userTypeValueBO:
     * @return com.utils.Json
     * @author 孙凯伦
     * @since 2021-10-21 01:49:04
     */
    @Methods(methodsName = "用户类型值表删除", methods = "remove")
    @ApiOperation(value = "用户类型值表删除", httpMethod = "DELETE", notes = "用户类型值表删除", response = Json.class)
    @ApiOperationSupport(includeParameters = {"id"})
    @DeleteMapping("/remove")
    public Json<Boolean> remove(@Validated(value = UserTypeValueBO.Remove.class) UserTypeValueBO userTypeValueBO) {
        return new Json(ReturnCode.成功, userTypeValueService.removeEnhance(userTypeValueBO));
    }


}