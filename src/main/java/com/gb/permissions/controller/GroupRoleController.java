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
import com.gb.permissions.service.GroupRoleService;
import com.gb.permissions.entity.query.GroupRoleQuery;
import com.gb.permissions.entity.vo.GroupRoleVO;
import com.gb.permissions.entity.bo.GroupRoleBO;


/**
 * Created with IntelliJ IDEA.
 * @author:     	孙凯伦
 * @since:   	    2021-10-21 01:59:44
 * @description:	TODO  角色用户组，Comment请求层
 * @source:  	    代码生成器
 */
@Slf4j
@RequestRequired
@ApiSupport(author = "孙凯伦")
@Setter(onMethod_ = {@Autowired})
@RestController
@RequestMapping("/groupRole")
@Api(tags = "角色用户组")
public class GroupRoleController {


    /**
     * 角色用户组
     */
    private GroupRoleService groupRoleService;
    /**
     * redis操作
     */
    private StringRedisTemplate stringRedisTemplate;


    /**
     * 角色用户组集合分页查询
     *
     * @param current:
     * @param size:
     * @param groupRoleQuery:
     * @return com.utils.Json
     * @author 孙凯伦
     * @since 2021-10-21 01:59:44
     */
    @Methods(methodsName = "角色用户组集合分页查询", methods = "select")
    @ApiOperation(value = "角色用户组集合分页查询", httpMethod = "GET", notes = "角色用户组集合查询", response = Json.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "current", value = "当前页", dataType = "int", required = true),
            @ApiImplicitParam(name = "size", value = "分页显示数量", dataType = "int", required = true),
    })
    @GetMapping("/select")
    public Json<Page<GroupRoleVO>> select(@Validated(value = GroupRoleQuery.Select.class) GroupRoleQuery groupRoleQuery, Integer current, Integer size) {
        //返回内容
        return new Json(ReturnCode.成功, groupRoleService.pageEnhance(new Page(current, size), groupRoleQuery));
    }


    /**
     * 角色用户组集合查询
     *
     * @param groupRoleQuery:
     * @return com.utils.Json
     * @author 孙凯伦
     * @since 2021-10-21 01:59:44
     */
    @Methods(methodsName = "角色用户组集合查询", methods = "selectList")
    @ApiOperation(value = "角色用户组集合查询", httpMethod = "GET", notes = "角色用户组集合查询", response = Json.class)
    @GetMapping("/selectList")
    public Json<List<GroupRoleVO>> selectList(@Validated(value = GroupRoleQuery.SelectList.class) GroupRoleQuery groupRoleQuery) {
        //返回内容
        return new Json(ReturnCode.成功, groupRoleService.listEnhance(groupRoleQuery));
    }


    /**
     * 角色用户组单条查询
     *
     * @param groupRoleQuery:
     * @return com.utils.Json
     * @author 孙凯伦
     * @since 2021-10-21 01:59:44
     */
    @Methods(methodsName = "角色用户组单条查询", methods = "selectOne")
    @ApiOperation(value = "角色用户组单条查询", httpMethod = "GET", notes = "角色用户组单条查询", response = Json.class)
    @GetMapping("/selectOne")
    public Json<GroupRoleVO> selectOne(@Validated(value = GroupRoleQuery.SelectOne.class) GroupRoleQuery groupRoleQuery) {
        //返回内容
        return new Json(ReturnCode.成功, groupRoleService.getOneEnhance(groupRoleQuery));
    }


    /**
     * 角色用户组总数查询
     *
     * @param groupRoleQuery:
     * @return com.utils.Json
     * @author 孙凯伦
     * @since 2021-10-21 01:59:44
     */
    @Methods(methodsName = "角色用户组总数查询", methods = "count")
    @ApiOperation(value = "角色用户组总数查询", httpMethod = "GET", notes = "角色用户组总数查询", response = Json.class)
    @GetMapping(value = "/count")
    public Json<Integer> count(@Validated(value = GroupRoleQuery.Count.class) GroupRoleQuery groupRoleQuery) {
        //返回内容
        return new Json(ReturnCode.成功, groupRoleService.countEnhance(groupRoleQuery));
    }


   /**
    * 角色用户组新增
    *
    * @param groupRoleBO:
    * @return com.utils.Json
    * @author 孙凯伦
    * @since 2021-10-21 01:59:44
    */
    @PreventRepeat
    @Methods(methodsName = "角色用户组新增", methods = "save")
    @ApiOperation(value = "角色用户组新增", httpMethod = "POST", notes = "角色用户组新增", response = Json.class)
    @ApiOperationSupport(ignoreParameters = {"id","createDateTime","createName","modifyDateTime","modifyName","isDelete","version"})
    @PostMapping("/save")
    public Json<String> save(@Validated(value = GroupRoleBO.Save.class) GroupRoleBO groupRoleBO, HttpServletRequest httpServletRequest) {
        //缓存取出用户
        Map<String, Object> u = RedisUtils.getToken(stringRedisTemplate, httpServletRequest);
        if(u != null){
            groupRoleBO.setCreateName(u.get("name") + "-" + u.get("id"));
        }
        //返回内容
        return new Json(ReturnCode.成功, groupRoleService.saveEnhance(groupRoleBO));
    }


    /**
     * 角色用户组修改
     *
     * @param groupRoleBO:
     * @return com.utils.Json
     * @author 孙凯伦
     * @since 2021-10-21 01:59:44
     */
    @PreventRepeat
    @Methods(methodsName = "角色用户组修改", methods = "update")
    @ApiOperation(value = "角色用户组修改", httpMethod = "PUT", notes = "角色用户组修改", response = Json.class)
    @ApiOperationSupport(ignoreParameters = {"createDateTime","createName","modifyDateTime","modifyName","isDelete","version"})
    @PutMapping("/update")
    public Json<Boolean> update(@Validated(value = GroupRoleBO.Update.class) GroupRoleBO groupRoleBO, HttpServletRequest httpServletRequest) {
        //缓存取出用户
        Map<String, Object> u = RedisUtils.getToken(stringRedisTemplate, httpServletRequest);
        if(u != null){
            groupRoleBO.setModifyName(u.get("name") + "-" + u.get("id"));
        }
        //返回内容
        return new Json(ReturnCode.成功, groupRoleService.updateEnhance(groupRoleBO));
    }


    /**
     * 角色用户组删除
     *
     * @param groupRoleBO:
     * @return com.utils.Json
     * @author 孙凯伦
     * @since 2021-10-21 01:59:44
     */
    @Methods(methodsName = "角色用户组删除", methods = "remove")
    @ApiOperation(value = "角色用户组删除", httpMethod = "DELETE", notes = "角色用户组删除", response = Json.class)
    @ApiOperationSupport(includeParameters = {"id"})
    @DeleteMapping("/remove")
    public Json<Boolean> remove(@Validated(value = GroupRoleBO.Remove.class) GroupRoleBO groupRoleBO) {
        return new Json(ReturnCode.成功, groupRoleService.removeEnhance(groupRoleBO));
    }


}