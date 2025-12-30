package com.gb.account.controller;

import com.google.common.collect.Lists;
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
import com.gb.account.service.UserGroupService;
import com.gb.account.entity.query.UserGroupQuery;
import com.gb.account.entity.vo.UserGroupVO;
import com.gb.account.entity.bo.UserGroupBO;


/**
 * Created with IntelliJ IDEA.
 * @author:     	孙凯伦
 * @since:   	    2021-10-21 01:50:39
 * @description:	TODO  用户组，Comment请求层
 * @source:  	    代码生成器
 */
@Slf4j
@RequestRequired
@ApiSupport(author = "孙凯伦")
@Setter(onMethod_ = {@Autowired})
@RestController
@RequestMapping("/userGroup")
@Api(tags = "用户组")
public class UserGroupController {


    /**
     * 用户组
     */
    private UserGroupService userGroupService;
    /**
     * redis操作
     */
    private StringRedisTemplate stringRedisTemplate;


    /**
     * 用户组集合分页查询
     *
     * @param current:
     * @param size:
     * @param userGroupQuery:
     * @return com.utils.Json
     * @author 孙凯伦
     * @since 2021-10-21 01:50:39
     */
    @Methods(methodsName = "用户组集合分页查询", methods = "select")
    @ApiOperation(value = "用户组集合分页查询", httpMethod = "GET", notes = "用户组集合查询", response = Json.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "current", value = "当前页", dataType = "int", required = true),
            @ApiImplicitParam(name = "size", value = "分页显示数量", dataType = "int", required = true),
    })
    @GetMapping("/select")
    public Json<Page<UserGroupVO>> select(@Validated(value = UserGroupQuery.Select.class) UserGroupQuery userGroupQuery, Integer current, Integer size) {
        //返回内容
        return new Json(ReturnCode.成功, userGroupService.pageEnhance(new Page(current, size), userGroupQuery));
    }


    /**
     * 用户组集合查询
     *
     * @param userGroupQuery:
     * @return com.utils.Json
     * @author 孙凯伦
     * @since 2021-10-21 01:50:39
     */
    @Methods(methodsName = "用户组集合查询", methods = "selectList")
    @ApiOperation(value = "用户组集合查询", httpMethod = "GET", notes = "用户组集合查询", response = Json.class)
    @GetMapping("/selectList")
    public Json<List<UserGroupVO>> selectList(@Validated(value = UserGroupQuery.SelectList.class) UserGroupQuery userGroupQuery) {
        //返回内容
        return new Json(ReturnCode.成功, userGroupService.listEnhance(userGroupQuery));
    }


    /**
     * 用户组单条查询
     *
     * @param userGroupQuery:
     * @return com.utils.Json
     * @author 孙凯伦
     * @since 2021-10-21 01:50:39
     */
    @Methods(methodsName = "用户组单条查询", methods = "selectOne")
    @ApiOperation(value = "用户组单条查询", httpMethod = "GET", notes = "用户组单条查询", response = Json.class)
    @GetMapping("/selectOne")
    public Json<UserGroupVO> selectOne(@Validated(value = UserGroupQuery.SelectOne.class) UserGroupQuery userGroupQuery) {
        //返回内容
        return new Json(ReturnCode.成功, userGroupService.getOneEnhance(userGroupQuery));
    }


    /**
     * 用户组总数查询
     *
     * @param userGroupQuery:
     * @return com.utils.Json
     * @author 孙凯伦
     * @since 2021-10-21 01:50:39
     */
    @Methods(methodsName = "用户组总数查询", methods = "count")
    @ApiOperation(value = "用户组总数查询", httpMethod = "GET", notes = "用户组总数查询", response = Json.class)
    @GetMapping(value = "/count")
    public Json<Integer> count(@Validated(value = UserGroupQuery.Count.class) UserGroupQuery userGroupQuery) {
        //返回内容
        return new Json(ReturnCode.成功, userGroupService.countEnhance(userGroupQuery));
    }


    /**
     * 用户组新增
     *
     * @param userId:
     * @param groupId:
     * @return com.utils.Json
     * @author 孙凯伦
     * @since 2021-10-21 01:50:39
     */
    @PreventRepeat
    @Methods(methodsName = "用户组批量新增", methods = "saveEnhanceBatch")
    @ApiOperation(value = "用户组批量新增", httpMethod = "POST", notes = "用户组批量新增", response = Json.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户id", dataType = "String", required = true),
            @ApiImplicitParam(name = "groupId", value = "组id", dataType = "String", required = true),
    })
    @PostMapping("/saveEnhanceBatch")
    public Json saveEnhanceBatch(String[] userId,String groupId, HttpServletRequest httpServletRequest) {
        userGroupService.saveEnhanceBatch(userId,groupId,httpServletRequest);
        //返回内容
        return new Json(ReturnCode.成功);
    }


   /**
    * 用户组新增
    *
    * @param userGroupBO:
    * @return com.utils.Json
    * @author 孙凯伦
    * @since 2021-10-21 01:50:39
    */
    @PreventRepeat
    @Methods(methodsName = "用户组新增", methods = "save")
    @ApiOperation(value = "用户组新增", httpMethod = "POST", notes = "用户组新增", response = Json.class)
    @ApiOperationSupport(ignoreParameters = {"id","createDateTime","createName","modifyDateTime","modifyName","isDelete","version"})
    @PostMapping("/save")
    public Json<String> save(@Validated(value = UserGroupBO.Save.class) UserGroupBO userGroupBO, HttpServletRequest httpServletRequest) {
        //缓存取出用户
        Map<String, Object> u = RedisUtils.getToken(stringRedisTemplate, httpServletRequest);
        if(u != null){
            userGroupBO.setCreateName(u.get("name") + "-" + u.get("id"));
        }
        //返回内容
        return new Json(ReturnCode.成功, userGroupService.saveEnhance(userGroupBO));
    }


    /**
     * 用户组修改
     *
     * @param userGroupBO:
     * @return com.utils.Json
     * @author 孙凯伦
     * @since 2021-10-21 01:50:39
     */
    @PreventRepeat
    @Methods(methodsName = "用户组修改", methods = "update")
    @ApiOperation(value = "用户组修改", httpMethod = "PUT", notes = "用户组修改", response = Json.class)
    @ApiOperationSupport(ignoreParameters = {"createDateTime","createName","modifyDateTime","modifyName","isDelete","version"})
    @PutMapping("/update")
    public Json<Boolean> update(@Validated(value = UserGroupBO.Update.class) UserGroupBO userGroupBO, HttpServletRequest httpServletRequest) {
        //缓存取出用户
        Map<String, Object> u = RedisUtils.getToken(stringRedisTemplate, httpServletRequest);
        if(u != null){
            userGroupBO.setModifyName(u.get("name") + "-" + u.get("id"));
        }
        //返回内容
        return new Json(ReturnCode.成功, userGroupService.updateEnhance(userGroupBO));
    }


    /**
     * 用户组删除
     *
     * @param userGroupBO:
     * @return com.utils.Json
     * @author 孙凯伦
     * @since 2021-10-21 01:50:39
     */
    @Methods(methodsName = "用户组删除", methods = "remove")
    @ApiOperation(value = "用户组删除", httpMethod = "DELETE", notes = "用户组删除", response = Json.class)
    @ApiOperationSupport(includeParameters = {"id"})
    @DeleteMapping("/remove")
    public Json<Boolean> remove(@Validated(value = UserGroupBO.Remove.class) UserGroupBO userGroupBO) {
        return new Json(ReturnCode.成功, userGroupService.removeEnhance(userGroupBO));
    }


}