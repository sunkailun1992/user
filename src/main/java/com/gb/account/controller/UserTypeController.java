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
import com.gb.account.service.UserTypeService;
import com.gb.account.entity.query.UserTypeQuery;
import com.gb.account.entity.vo.UserTypeVO;
import com.gb.account.entity.bo.UserTypeBO;


/**
 * Created with IntelliJ IDEA.
 * @author:     	孙凯伦
 * @since:   	    2021-10-21 01:49:03
 * @description:	TODO  用户类型表，Comment请求层
 * @source:  	    代码生成器
 */
@Slf4j
@RequestRequired
@ApiSupport(author = "孙凯伦")
@Setter(onMethod_ = {@Autowired})
@RestController
@RequestMapping("/userType")
@Api(tags = "用户类型表")
public class UserTypeController {


    /**
     * 用户类型表
     */
    private UserTypeService userTypeService;
    /**
     * redis操作
     */
    private StringRedisTemplate stringRedisTemplate;


    /**
     * 用户类型表集合分页查询
     *
     * @param current:
     * @param size:
     * @param userTypeQuery:
     * @return com.utils.Json
     * @author 孙凯伦
     * @since 2021-10-21 01:49:03
     */
    @Methods(methodsName = "用户类型表集合分页查询", methods = "select")
    @ApiOperation(value = "用户类型表集合分页查询", httpMethod = "GET", notes = "用户类型表集合查询", response = Json.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "current", value = "当前页", dataType = "int", required = true),
            @ApiImplicitParam(name = "size", value = "分页显示数量", dataType = "int", required = true),
    })
    @GetMapping("/select")
    public Json<Page<UserTypeVO>> select(@Validated(value = UserTypeQuery.Select.class) UserTypeQuery userTypeQuery, Integer current, Integer size) {
        //返回内容
        return new Json(ReturnCode.成功, userTypeService.pageEnhance(new Page(current, size), userTypeQuery));
    }


    /**
     * 用户类型表集合查询
     *
     * @param userTypeQuery:
     * @return com.utils.Json
     * @author 孙凯伦
     * @since 2021-10-21 01:49:03
     */
    @Methods(methodsName = "用户类型表集合查询", methods = "selectList")
    @ApiOperation(value = "用户类型表集合查询", httpMethod = "GET", notes = "用户类型表集合查询", response = Json.class)
    @GetMapping("/selectList")
    public Json<List<UserTypeVO>> selectList(@Validated(value = UserTypeQuery.SelectList.class) UserTypeQuery userTypeQuery) {
        //返回内容
        return new Json(ReturnCode.成功, userTypeService.listEnhance(userTypeQuery));
    }


    /**
     * 用户类型表单条查询
     *
     * @param userTypeQuery:
     * @return com.utils.Json
     * @author 孙凯伦
     * @since 2021-10-21 01:49:03
     */
    @Methods(methodsName = "用户类型表单条查询", methods = "selectOne")
    @ApiOperation(value = "用户类型表单条查询", httpMethod = "GET", notes = "用户类型表单条查询", response = Json.class)
    @GetMapping("/selectOne")
    public Json<UserTypeVO> selectOne(@Validated(value = UserTypeQuery.SelectOne.class) UserTypeQuery userTypeQuery) {
        //返回内容
        return new Json(ReturnCode.成功, userTypeService.getOneEnhance(userTypeQuery));
    }


    /**
     * 用户类型表总数查询
     *
     * @param userTypeQuery:
     * @return com.utils.Json
     * @author 孙凯伦
     * @since 2021-10-21 01:49:03
     */
    @Methods(methodsName = "用户类型表总数查询", methods = "count")
    @ApiOperation(value = "用户类型表总数查询", httpMethod = "GET", notes = "用户类型表总数查询", response = Json.class)
    @GetMapping(value = "/count")
    public Json<Integer> count(@Validated(value = UserTypeQuery.Count.class) UserTypeQuery userTypeQuery) {
        //返回内容
        return new Json(ReturnCode.成功, userTypeService.countEnhance(userTypeQuery));
    }


   /**
    * 用户类型表新增
    *
    * @param userTypeBO:
    * @return com.utils.Json
    * @author 孙凯伦
    * @since 2021-10-21 01:49:03
    */
    @PreventRepeat
    @Methods(methodsName = "用户类型表新增", methods = "save")
    @ApiOperation(value = "用户类型表新增", httpMethod = "POST", notes = "用户类型表新增", response = Json.class)
    @ApiOperationSupport(ignoreParameters = {"id","createDateTime","createName","modifyDateTime","modifyName","isDelete","version"})
    @PostMapping("/save")
    public Json<String> save(@Validated(value = UserTypeBO.Save.class) UserTypeBO userTypeBO, HttpServletRequest httpServletRequest) {
        //缓存取出用户
        Map<String, Object> u = RedisUtils.getToken(stringRedisTemplate, httpServletRequest);
        if(u != null){
            userTypeBO.setCreateName(u.get("name") + "-" + u.get("id"));
        }
        //返回内容
        return new Json(ReturnCode.成功, userTypeService.saveEnhance(userTypeBO));
    }


    /**
     * 用户类型表修改
     *
     * @param userTypeBO:
     * @return com.utils.Json
     * @author 孙凯伦
     * @since 2021-10-21 01:49:03
     */
    @PreventRepeat
    @Methods(methodsName = "用户类型表修改", methods = "update")
    @ApiOperation(value = "用户类型表修改", httpMethod = "PUT", notes = "用户类型表修改", response = Json.class)
    @ApiOperationSupport(ignoreParameters = {"createDateTime","createName","modifyDateTime","modifyName","isDelete","version"})
    @PutMapping("/update")
    public Json<Boolean> update(@Validated(value = UserTypeBO.Update.class) UserTypeBO userTypeBO, HttpServletRequest httpServletRequest) {
        //缓存取出用户
        Map<String, Object> u = RedisUtils.getToken(stringRedisTemplate, httpServletRequest);
        if(u != null){
            userTypeBO.setModifyName(u.get("name") + "-" + u.get("id"));
        }
        //返回内容
        return new Json(ReturnCode.成功, userTypeService.updateEnhance(userTypeBO));
    }


    /**
     * 用户类型表删除
     *
     * @param userTypeBO:
     * @return com.utils.Json
     * @author 孙凯伦
     * @since 2021-10-21 01:49:03
     */
    @Methods(methodsName = "用户类型表删除", methods = "remove")
    @ApiOperation(value = "用户类型表删除", httpMethod = "DELETE", notes = "用户类型表删除", response = Json.class)
    @ApiOperationSupport(includeParameters = {"id"})
    @DeleteMapping("/remove")
    public Json<Boolean> remove(@Validated(value = UserTypeBO.Remove.class) UserTypeBO userTypeBO) {
        return new Json(ReturnCode.成功, userTypeService.removeEnhance(userTypeBO));
    }


}