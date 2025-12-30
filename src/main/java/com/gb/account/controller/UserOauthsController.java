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
import com.gb.account.service.UserOauthsService;
import com.gb.account.entity.query.UserOauthsQuery;
import com.gb.account.entity.vo.UserOauthsVO;
import com.gb.account.entity.bo.UserOauthsBO;


/**
 * Created with IntelliJ IDEA.
 * @author:     	孙凯伦
 * @since:   	    2021-10-21 01:50:37
 * @description:	TODO  用户授权表，Comment请求层
 * @source:  	    代码生成器
 */
@Slf4j
@RequestRequired
@ApiSupport(author = "孙凯伦")
@Setter(onMethod_ = {@Autowired})
@RestController
@RequestMapping("/userOauths")
@Api(tags = "用户授权表")
public class UserOauthsController {


    /**
     * 用户授权表
     */
    private UserOauthsService userOauthsService;
    /**
     * redis操作
     */
    private StringRedisTemplate stringRedisTemplate;


    /**
     * 用户授权表集合分页查询
     *
     * @param current:
     * @param size:
     * @param userOauthsQuery:
     * @return com.utils.Json
     * @author 孙凯伦
     * @since 2021-10-21 01:50:37
     */
    @Methods(methodsName = "用户授权表集合分页查询", methods = "select")
    @ApiOperation(value = "用户授权表集合分页查询", httpMethod = "GET", notes = "用户授权表集合查询", response = Json.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "current", value = "当前页", dataType = "int", required = true),
            @ApiImplicitParam(name = "size", value = "分页显示数量", dataType = "int", required = true),
    })
    @GetMapping("/select")
    public Json<Page<UserOauthsVO>> select(@Validated(value = UserOauthsQuery.Select.class) UserOauthsQuery userOauthsQuery, Integer current, Integer size) {
        //返回内容
        return new Json(ReturnCode.成功, userOauthsService.pageEnhance(new Page(current, size), userOauthsQuery));
    }


    /**
     * 用户授权表集合查询
     *
     * @param userOauthsQuery:
     * @return com.utils.Json
     * @author 孙凯伦
     * @since 2021-10-21 01:50:37
     */
    @Methods(methodsName = "用户授权表集合查询", methods = "selectList")
    @ApiOperation(value = "用户授权表集合查询", httpMethod = "GET", notes = "用户授权表集合查询", response = Json.class)
    @GetMapping("/selectList")
    public Json<List<UserOauthsVO>> selectList(@Validated(value = UserOauthsQuery.SelectList.class) UserOauthsQuery userOauthsQuery) {
        //返回内容
        return new Json(ReturnCode.成功, userOauthsService.listEnhance(userOauthsQuery));
    }


    /**
     * 用户授权表单条查询
     *
     * @param userOauthsQuery:
     * @return com.utils.Json
     * @author 孙凯伦
     * @since 2021-10-21 01:50:37
     */
    @Methods(methodsName = "用户授权表单条查询", methods = "selectOne")
    @ApiOperation(value = "用户授权表单条查询", httpMethod = "GET", notes = "用户授权表单条查询", response = Json.class)
    @GetMapping("/selectOne")
    public Json<UserOauthsVO> selectOne(@Validated(value = UserOauthsQuery.SelectOne.class) UserOauthsQuery userOauthsQuery) {
        //返回内容
        return new Json(ReturnCode.成功, userOauthsService.getOneEnhance(userOauthsQuery));
    }


    /**
     * 用户授权表总数查询
     *
     * @param userOauthsQuery:
     * @return com.utils.Json
     * @author 孙凯伦
     * @since 2021-10-21 01:50:37
     */
    @Methods(methodsName = "用户授权表总数查询", methods = "count")
    @ApiOperation(value = "用户授权表总数查询", httpMethod = "GET", notes = "用户授权表总数查询", response = Json.class)
    @GetMapping(value = "/count")
    public Json<Integer> count(@Validated(value = UserOauthsQuery.Count.class) UserOauthsQuery userOauthsQuery) {
        //返回内容
        return new Json(ReturnCode.成功, userOauthsService.countEnhance(userOauthsQuery));
    }


   /**
    * 用户授权表新增
    *
    * @param userOauthsBO:
    * @return com.utils.Json
    * @author 孙凯伦
    * @since 2021-10-21 01:50:37
    */
    @PreventRepeat
    @Methods(methodsName = "用户授权表新增", methods = "save")
    @ApiOperation(value = "用户授权表新增", httpMethod = "POST", notes = "用户授权表新增", response = Json.class)
    @ApiOperationSupport(ignoreParameters = {"id","createDateTime","createName","modifyDateTime","modifyName","isDelete","version"})
    @PostMapping("/save")
    public Json<String> save(@Validated(value = UserOauthsBO.Save.class) UserOauthsBO userOauthsBO, HttpServletRequest httpServletRequest) {
        //缓存取出用户
        Map<String, Object> u = RedisUtils.getToken(stringRedisTemplate, httpServletRequest);
        if(u != null){
            userOauthsBO.setCreateName(u.get("name") + "-" + u.get("id"));
        }
        //返回内容
        return new Json(ReturnCode.成功, userOauthsService.saveEnhance(userOauthsBO));
    }


    /**
     * 用户授权表修改
     *
     * @param userOauthsBO:
     * @return com.utils.Json
     * @author 孙凯伦
     * @since 2021-10-21 01:50:37
     */
    @PreventRepeat
    @Methods(methodsName = "用户授权表修改", methods = "update")
    @ApiOperation(value = "用户授权表修改", httpMethod = "PUT", notes = "用户授权表修改", response = Json.class)
    @ApiOperationSupport(ignoreParameters = {"createDateTime","createName","modifyDateTime","modifyName","isDelete","version"})
    @PutMapping("/update")
    public Json<Boolean> update(@Validated(value = UserOauthsBO.Update.class) UserOauthsBO userOauthsBO, HttpServletRequest httpServletRequest) {
        //缓存取出用户
        Map<String, Object> u = RedisUtils.getToken(stringRedisTemplate, httpServletRequest);
        if(u != null){
            userOauthsBO.setModifyName(u.get("name") + "-" + u.get("id"));
        }
        //返回内容
        return new Json(ReturnCode.成功, userOauthsService.updateEnhance(userOauthsBO));
    }


    /**
     * 用户授权表删除
     *
     * @param userOauthsBO:
     * @return com.utils.Json
     * @author 孙凯伦
     * @since 2021-10-21 01:50:37
     */
    @Methods(methodsName = "用户授权表删除", methods = "remove")
    @ApiOperation(value = "用户授权表删除", httpMethod = "DELETE", notes = "用户授权表删除", response = Json.class)
    @ApiOperationSupport(includeParameters = {"id"})
    @DeleteMapping("/remove")
    public Json<Boolean> remove(@Validated(value = UserOauthsBO.Remove.class) UserOauthsBO userOauthsBO) {
        return new Json(ReturnCode.成功, userOauthsService.removeEnhance(userOauthsBO));
    }


}