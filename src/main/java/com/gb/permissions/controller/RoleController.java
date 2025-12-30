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
import com.gb.permissions.service.RoleService;
import com.gb.permissions.entity.query.RoleQuery;
import com.gb.permissions.entity.vo.RoleVO;
import com.gb.permissions.entity.bo.RoleBO;


/**
 * Created with IntelliJ IDEA.
 * @author:     	孙凯伦
 * @since:   	    2021-10-21 01:59:43
 * @description:	TODO  角色表，Comment请求层
 * @source:  	    代码生成器
 */
@Slf4j
@RequestRequired
@ApiSupport(author = "孙凯伦")
@Setter(onMethod_ = {@Autowired})
@RestController
@RequestMapping("/role")
@Api(tags = "角色表")
public class RoleController {


    /**
     * 角色表
     */
    private RoleService roleService;
    /**
     * redis操作
     */
    private StringRedisTemplate stringRedisTemplate;


    /**
     * 角色表集合分页查询
     *
     * @param current:
     * @param size:
     * @param roleQuery:
     * @return com.utils.Json
     * @author 孙凯伦
     * @since 2021-10-21 01:59:43
     */
    @Methods(methodsName = "角色表集合分页查询", methods = "select")
    @ApiOperation(value = "角色表集合分页查询", httpMethod = "GET", notes = "角色表集合查询", response = Json.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "current", value = "当前页", dataType = "int", required = true),
            @ApiImplicitParam(name = "size", value = "分页显示数量", dataType = "int", required = true),
    })
    @GetMapping("/select")
    public Json<Page<RoleVO>> select(@Validated(value = RoleQuery.Select.class) RoleQuery roleQuery, Integer current, Integer size) {
        //返回内容
        return new Json(ReturnCode.成功, roleService.pageEnhance(new Page(current, size), roleQuery));
    }


    /**
     * 角色表集合查询
     *
     * @param roleQuery:
     * @return com.utils.Json
     * @author 孙凯伦
     * @since 2021-10-21 01:59:43
     */
    @Methods(methodsName = "角色表集合查询", methods = "selectList")
    @ApiOperation(value = "角色表集合查询", httpMethod = "GET", notes = "角色表集合查询", response = Json.class)
    @GetMapping("/selectList")
    public Json<List<RoleVO>> selectList(@Validated(value = RoleQuery.SelectList.class) RoleQuery roleQuery) {
        //返回内容
        return new Json(ReturnCode.成功, roleService.listEnhance(roleQuery));
    }


    /**
     * 角色表单条查询
     *
     * @param roleQuery:
     * @return com.utils.Json
     * @author 孙凯伦
     * @since 2021-10-21 01:59:43
     */
    @Methods(methodsName = "角色表单条查询", methods = "selectOne")
    @ApiOperation(value = "角色表单条查询", httpMethod = "GET", notes = "角色表单条查询", response = Json.class)
    @GetMapping("/selectOne")
    public Json<RoleVO> selectOne(@Validated(value = RoleQuery.SelectOne.class) RoleQuery roleQuery) {
        //返回内容
        return new Json(ReturnCode.成功, roleService.getOneEnhance(roleQuery));
    }


    /**
     * 角色表总数查询
     *
     * @param roleQuery:
     * @return com.utils.Json
     * @author 孙凯伦
     * @since 2021-10-21 01:59:43
     */
    @Methods(methodsName = "角色表总数查询", methods = "count")
    @ApiOperation(value = "角色表总数查询", httpMethod = "GET", notes = "角色表总数查询", response = Json.class)
    @GetMapping(value = "/count")
    public Json<Integer> count(@Validated(value = RoleQuery.Count.class) RoleQuery roleQuery) {
        //返回内容
        return new Json(ReturnCode.成功, roleService.countEnhance(roleQuery));
    }


   /**
    * 角色表新增
    *
    * @param roleBO:
    * @return com.utils.Json
    * @author 孙凯伦
    * @since 2021-10-21 01:59:43
    */
    @PreventRepeat
    @Methods(methodsName = "角色表新增", methods = "save")
    @ApiOperation(value = "角色表新增", httpMethod = "POST", notes = "角色表新增", response = Json.class)
    @ApiOperationSupport(ignoreParameters = {"id","createDateTime","createName","modifyDateTime","modifyName","isDelete","version"})
    @PostMapping("/save")
    public Json<String> save(@Validated(value = RoleBO.Save.class) RoleBO roleBO, HttpServletRequest httpServletRequest) {
        //缓存取出用户
        Map<String, Object> u = RedisUtils.getToken(stringRedisTemplate, httpServletRequest);
        if(u != null){
            roleBO.setCreateName(u.get("name") + "-" + u.get("id"));
        }
        //返回内容
        return new Json(ReturnCode.成功, roleService.saveEnhance(roleBO));
    }


    /**
     * 角色表修改
     *
     * @param roleBO:
     * @return com.utils.Json
     * @author 孙凯伦
     * @since 2021-10-21 01:59:43
     */
    @PreventRepeat
    @Methods(methodsName = "角色表修改", methods = "update")
    @ApiOperation(value = "角色表修改", httpMethod = "PUT", notes = "角色表修改", response = Json.class)
    @ApiOperationSupport(ignoreParameters = {"createDateTime","createName","modifyDateTime","modifyName","isDelete","version"})
    @PutMapping("/update")
    public Json<Boolean> update(@Validated(value = RoleBO.Update.class) RoleBO roleBO, HttpServletRequest httpServletRequest) {
        //缓存取出用户
        Map<String, Object> u = RedisUtils.getToken(stringRedisTemplate, httpServletRequest);
        if(u != null){
            roleBO.setModifyName(u.get("name") + "-" + u.get("id"));
        }
        //返回内容
        return new Json(ReturnCode.成功, roleService.updateEnhance(roleBO));
    }


    /**
     * 角色表删除
     *
     * @param roleBO:
     * @return com.utils.Json
     * @author 孙凯伦
     * @since 2021-10-21 01:59:43
     */
    @Methods(methodsName = "角色表删除", methods = "remove")
    @ApiOperation(value = "角色表删除", httpMethod = "DELETE", notes = "角色表删除", response = Json.class)
    @ApiOperationSupport(includeParameters = {"id"})
    @DeleteMapping("/remove")
    public Json<Boolean> remove(@Validated(value = RoleBO.Remove.class) RoleBO roleBO) {
        return new Json(ReturnCode.成功, roleService.removeEnhance(roleBO));
    }


}