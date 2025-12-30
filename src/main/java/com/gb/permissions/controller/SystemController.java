package com.gb.permissions.controller;

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
import com.gb.permissions.service.SystemService;
import com.gb.permissions.entity.query.SystemQuery;
import com.gb.permissions.entity.vo.SystemVO;
import com.gb.permissions.entity.bo.SystemBO;


/**
 * Created with IntelliJ IDEA.
 * @author:     	孙凯伦
 * @since:   	    2021-10-21 01:59:43
 * @description:	TODO  系统表，Comment请求层
 * @source:  	    代码生成器
 */
@Slf4j
@RequestRequired
@ApiSupport(author = "孙凯伦")
@Setter(onMethod_ = {@Autowired})
@RestController
@RequestMapping("/system")
@Api(tags = "系统表")
public class SystemController {


    /**
     * 系统表
     */
    private SystemService systemService;
    /**
     * redis操作
     */
    private StringRedisTemplate stringRedisTemplate;


    /**
     * 系统表集合分页查询
     *
     * @param current:
     * @param size:
     * @param systemQuery:
     * @return com.utils.Json
     * @author 孙凯伦
     * @since 2021-10-21 01:59:43
     */
    @Methods(methodsName = "系统表集合分页查询", methods = "select")
    @ApiOperation(value = "系统表集合分页查询", httpMethod = "GET", notes = "系统表集合查询", response = Json.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "current", value = "当前页", dataType = "int", required = true),
            @ApiImplicitParam(name = "size", value = "分页显示数量", dataType = "int", required = true),
    })
    @GetMapping("/select")
    public Json<Page<SystemVO>> select(@Validated(value = SystemQuery.Select.class) SystemQuery systemQuery, Integer current, Integer size) {
        //返回内容
        return new Json(ReturnCode.成功, systemService.pageEnhance(new Page(current, size), systemQuery));
    }


    /**
     * 系统表集合查询
     *
     * @param systemQuery:
     * @return com.utils.Json
     * @author 孙凯伦
     * @since 2021-10-21 01:59:43
     */
    @Methods(methodsName = "系统表集合查询", methods = "selectList")
    @ApiOperation(value = "系统表集合查询", httpMethod = "GET", notes = "系统表集合查询", response = Json.class)
    @GetMapping("/selectList")
    public Json<List<SystemVO>> selectList(@Validated(value = SystemQuery.SelectList.class) SystemQuery systemQuery) {
        //返回内容
        return new Json(ReturnCode.成功, systemService.listEnhance(systemQuery));
    }


    /**
     * 系统表单条查询
     *
     * @param systemQuery:
     * @return com.utils.Json
     * @author 孙凯伦
     * @since 2021-10-21 01:59:43
     */
    @Methods(methodsName = "系统表单条查询", methods = "selectOne")
    @ApiOperation(value = "系统表单条查询", httpMethod = "GET", notes = "系统表单条查询", response = Json.class)
    @GetMapping("/selectOne")
    public Json<SystemVO> selectOne(@Validated(value = SystemQuery.SelectOne.class) SystemQuery systemQuery) {
        //返回内容
        return new Json(ReturnCode.成功, systemService.getOneEnhance(systemQuery));
    }


    /**
     * 系统表总数查询
     *
     * @param systemQuery:
     * @return com.utils.Json
     * @author 孙凯伦
     * @since 2021-10-21 01:59:43
     */
    @Methods(methodsName = "系统表总数查询", methods = "count")
    @ApiOperation(value = "系统表总数查询", httpMethod = "GET", notes = "系统表总数查询", response = Json.class)
    @GetMapping(value = "/count")
    public Json<Integer> count(@Validated(value = SystemQuery.Count.class) SystemQuery systemQuery) {
        //返回内容
        return new Json(ReturnCode.成功, systemService.countEnhance(systemQuery));
    }


   /**
    * 系统表新增
    *
    * @param systemBO:
    * @return com.utils.Json
    * @author 孙凯伦
    * @since 2021-10-21 01:59:43
    */
    @PreventRepeat
    @Methods(methodsName = "系统表新增", methods = "save")
    @ApiOperation(value = "系统表新增", httpMethod = "POST", notes = "系统表新增", response = Json.class)
    @ApiOperationSupport(ignoreParameters = {"id","createDateTime","createName","modifyDateTime","modifyName","isDelete","version"})
    @PostMapping("/save")
    public Json<String> save(@Validated(value = SystemBO.Save.class) SystemBO systemBO, HttpServletRequest httpServletRequest) {
        //缓存取出用户
        Map<String, Object> u = RedisUtils.getToken(stringRedisTemplate, httpServletRequest);
        if(u != null){
            systemBO.setCreateName(u.get("name") + "-" + u.get("id"));
        }
        //返回内容
        return new Json(ReturnCode.成功, systemService.saveEnhance(systemBO));
    }


    /**
     * 系统表修改
     *
     * @param systemBO:
     * @return com.utils.Json
     * @author 孙凯伦
     * @since 2021-10-21 01:59:43
     */
    @PreventRepeat
    @Methods(methodsName = "系统表修改", methods = "update")
    @ApiOperation(value = "系统表修改", httpMethod = "PUT", notes = "系统表修改", response = Json.class)
    @ApiOperationSupport(ignoreParameters = {"createDateTime","createName","modifyDateTime","modifyName","isDelete","version"})
    @PutMapping("/update")
    public Json<Boolean> update(@Validated(value = SystemBO.Update.class) SystemBO systemBO, HttpServletRequest httpServletRequest) {
        //缓存取出用户
        Map<String, Object> u = RedisUtils.getToken(stringRedisTemplate, httpServletRequest);
        if(u != null){
            systemBO.setModifyName(u.get("name") + "-" + u.get("id"));
        }
        //返回内容
        return new Json(ReturnCode.成功, systemService.updateEnhance(systemBO));
    }


    /**
     * 系统表删除
     *
     * @param systemBO:
     * @return com.utils.Json
     * @author 孙凯伦
     * @since 2021-10-21 01:59:43
     */
    @Methods(methodsName = "系统表删除", methods = "remove")
    @ApiOperation(value = "系统表删除", httpMethod = "DELETE", notes = "系统表删除", response = Json.class)
    @ApiOperationSupport(includeParameters = {"id"})
    @DeleteMapping("/remove")
    public Json<Boolean> remove(@Validated(value = SystemBO.Remove.class) SystemBO systemBO) {
        return new Json(ReturnCode.成功, systemService.removeEnhance(systemBO));
    }


}