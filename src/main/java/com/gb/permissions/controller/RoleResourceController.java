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
import com.gb.permissions.service.RoleResourceService;
import com.gb.permissions.entity.query.RoleResourceQuery;
import com.gb.permissions.entity.vo.RoleResourceVO;
import com.gb.permissions.entity.bo.RoleResourceBO;


/**
 * Created with IntelliJ IDEA.
 *
 * @author: 孙凯伦
 * @since: 2021-10-21 01:59:42
 * @description: TODO 角色资源表，Comment请求层
 * @source: 代码生成器
 */
@Slf4j
@RequestRequired
@ApiSupport(author = "孙凯伦")
@Setter(onMethod_ = {@Autowired})
@RestController
@RequestMapping("/roleResource")
@Api(tags = "角色资源表")
public class RoleResourceController {


    /**
     * 角色资源表
     */
    private RoleResourceService roleResourceService;
    /**
     * redis操作
     */
    private StringRedisTemplate stringRedisTemplate;


    /**
     * 角色资源表集合分页查询
     *
     * @param current:
     * @param size:
     * @param roleResourceQuery:
     * @return com.utils.Json
     * @author 孙凯伦
     * @since 2021-10-21 01:59:42
     */
    @Methods(methodsName = "角色资源表集合分页查询", methods = "select")
    @ApiOperation(value = "角色资源表集合分页查询", httpMethod = "GET", notes = "角色资源表集合查询", response = Json.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "current", value = "当前页", dataType = "int", required = true),
            @ApiImplicitParam(name = "size", value = "分页显示数量", dataType = "int", required = true),
    })
    @GetMapping("/select")
    public Json<Page<RoleResourceVO>> select(@Validated(value = RoleResourceQuery.Select.class) RoleResourceQuery roleResourceQuery, Integer current, Integer size) {
        //返回内容
        return new Json(ReturnCode.成功, roleResourceService.pageEnhance(new Page(current, size), roleResourceQuery));
    }


    /**
     * 角色资源表集合查询
     *
     * @param roleResourceQuery:
     * @return com.utils.Json
     * @author 孙凯伦
     * @since 2021-10-21 01:59:42
     */
    @Methods(methodsName = "角色资源表集合查询", methods = "selectList")
    @ApiOperation(value = "角色资源表集合查询", httpMethod = "GET", notes = "角色资源表集合查询", response = Json.class)
    @GetMapping("/selectList")
    public Json<List<RoleResourceVO>> selectList(@Validated(value = RoleResourceQuery.SelectList.class) RoleResourceQuery roleResourceQuery) {
        //返回内容
        return new Json(ReturnCode.成功, roleResourceService.listEnhance(roleResourceQuery));
    }


    /**
     * 角色资源表单条查询
     *
     * @param roleResourceQuery:
     * @return com.utils.Json
     * @author 孙凯伦
     * @since 2021-10-21 01:59:42
     */
    @Methods(methodsName = "角色资源表单条查询", methods = "selectOne")
    @ApiOperation(value = "角色资源表单条查询", httpMethod = "GET", notes = "角色资源表单条查询", response = Json.class)
    @GetMapping("/selectOne")
    public Json<RoleResourceVO> selectOne(@Validated(value = RoleResourceQuery.SelectOne.class) RoleResourceQuery roleResourceQuery) {
        //返回内容
        return new Json(ReturnCode.成功, roleResourceService.getOneEnhance(roleResourceQuery));
    }


    /**
     * 角色资源表总数查询
     *
     * @param roleResourceQuery:
     * @return com.utils.Json
     * @author 孙凯伦
     * @since 2021-10-21 01:59:42
     */
    @Methods(methodsName = "角色资源表总数查询", methods = "count")
    @ApiOperation(value = "角色资源表总数查询", httpMethod = "GET", notes = "角色资源表总数查询", response = Json.class)
    @GetMapping(value = "/count")
    public Json<Integer> count(@Validated(value = RoleResourceQuery.Count.class) RoleResourceQuery roleResourceQuery) {
        //返回内容
        return new Json(ReturnCode.成功, roleResourceService.countEnhance(roleResourceQuery));
    }


    /**
     * 角色资源表批量新增
     *
     * @param roleId
     * @param resourceList
     * @param httpServletRequest
     * @return
     */
    @PreventRepeat
    @Methods(methodsName = "角色资源表批量新增", methods = "saveBatch")
    @ApiOperation(value = "角色资源表批量新增", httpMethod = "POST", notes = "角色资源表新增", response = Json.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "roleId", value = "角色id", dataType = "String", required = true),
            @ApiImplicitParam(name = "resourceList", value = "资源集合", dataType = "array", required = true),
            @ApiImplicitParam(name = "api", value = "是否接口", dataType = "Boolean", required = true),
    })
    @PostMapping("/saveBatch")
    public Json<String> saveBatch(String roleId, String[] resourceList, Boolean api, HttpServletRequest httpServletRequest) {
        //返回内容
        return new Json(ReturnCode.成功, roleResourceService.saveBatchEnhance(roleId, resourceList, api, httpServletRequest));
    }


    /**
     * 角色资源表新增
     *
     * @param roleResourceBO:
     * @return com.utils.Json
     * @author 孙凯伦
     * @since 2021-10-21 01:59:42
     */
    @PreventRepeat
    @Methods(methodsName = "角色资源表新增", methods = "save")
    @ApiOperation(value = "角色资源表新增", httpMethod = "POST", notes = "角色资源表新增", response = Json.class)
    @ApiOperationSupport(ignoreParameters = {"id", "createDateTime", "createName", "modifyDateTime", "modifyName", "isDelete", "version"})
    @PostMapping("/save")
    public Json<String> save(@Validated(value = RoleResourceBO.Save.class) RoleResourceBO roleResourceBO, HttpServletRequest httpServletRequest) {
        //缓存取出用户
        Map<String, Object> u = RedisUtils.getToken(stringRedisTemplate, httpServletRequest);
        if (u != null) {
            roleResourceBO.setCreateName(u.get("name") + "-" + u.get("id"));
        }
        //返回内容
        return new Json(ReturnCode.成功, roleResourceService.saveEnhance(roleResourceBO));
    }


    /**
     * 角色资源表修改
     *
     * @param roleResourceBO:
     * @return com.utils.Json
     * @author 孙凯伦
     * @since 2021-10-21 01:59:42
     */
    @PreventRepeat
    @Methods(methodsName = "角色资源表修改", methods = "update")
    @ApiOperation(value = "角色资源表修改", httpMethod = "PUT", notes = "角色资源表修改", response = Json.class)
    @ApiOperationSupport(ignoreParameters = {"createDateTime", "createName", "modifyDateTime", "modifyName", "isDelete", "version"})
    @PutMapping("/update")
    public Json<Boolean> update(@Validated(value = RoleResourceBO.Update.class) RoleResourceBO roleResourceBO, HttpServletRequest httpServletRequest) {
        //缓存取出用户
        Map<String, Object> u = RedisUtils.getToken(stringRedisTemplate, httpServletRequest);
        if (u != null) {
            roleResourceBO.setModifyName(u.get("name") + "-" + u.get("id"));
        }
        //返回内容
        return new Json(ReturnCode.成功, roleResourceService.updateEnhance(roleResourceBO));
    }


    /**
     * 角色资源表删除
     *
     * @param roleResourceBO:
     * @return com.utils.Json
     * @author 孙凯伦
     * @since 2021-10-21 01:59:42
     */
    @Methods(methodsName = "角色资源表删除", methods = "remove")
    @ApiOperation(value = "角色资源表删除", httpMethod = "DELETE", notes = "角色资源表删除", response = Json.class)
    @ApiOperationSupport(includeParameters = {"id"})
    @DeleteMapping("/remove")
    public Json<Boolean> remove(@Validated(value = RoleResourceBO.Remove.class) RoleResourceBO roleResourceBO) {
        return new Json(ReturnCode.成功, roleResourceService.removeEnhance(roleResourceBO));
    }


}