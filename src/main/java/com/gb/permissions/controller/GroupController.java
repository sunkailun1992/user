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
import com.gb.permissions.service.GroupService;
import com.gb.permissions.entity.query.GroupQuery;
import com.gb.permissions.entity.vo.GroupVO;
import com.gb.permissions.entity.bo.GroupBO;


/**
 * Created with IntelliJ IDEA.
 * @author:     	孙凯伦
 * @since:   	    2021-10-21 01:59:44
 * @description:	TODO  组，Comment请求层
 * @source:  	    代码生成器
 */
@Slf4j
@RequestRequired
@ApiSupport(author = "孙凯伦")
@Setter(onMethod_ = {@Autowired})
@RestController
@RequestMapping("/group")
@Api(tags = "组")
public class GroupController {


    /**
     * 组
     */
    private GroupService groupService;
    /**
     * redis操作
     */
    private StringRedisTemplate stringRedisTemplate;


    /**
     * 组集合分页查询
     *
     * @param current:
     * @param size:
     * @param groupQuery:
     * @return com.utils.Json
     * @author 孙凯伦
     * @since 2021-10-21 01:59:44
     */
    @Methods(methodsName = "组集合分页查询", methods = "select")
    @ApiOperation(value = "组集合分页查询", httpMethod = "GET", notes = "组集合查询", response = Json.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "current", value = "当前页", dataType = "int", required = true),
            @ApiImplicitParam(name = "size", value = "分页显示数量", dataType = "int", required = true),
    })
    @GetMapping("/select")
    public Json<Page<GroupVO>> select(@Validated(value = GroupQuery.Select.class) GroupQuery groupQuery, Integer current, Integer size) {
        //返回内容
        return new Json(ReturnCode.成功, groupService.pageEnhance(new Page(current, size), groupQuery));
    }


    /**
     * 组集合查询
     *
     * @param groupQuery:
     * @return com.utils.Json
     * @author 孙凯伦
     * @since 2021-10-21 01:59:44
     */
    @Methods(methodsName = "组集合查询", methods = "selectList")
    @ApiOperation(value = "组集合查询", httpMethod = "GET", notes = "组集合查询", response = Json.class)
    @GetMapping("/selectList")
    public Json<List<GroupVO>> selectList(@Validated(value = GroupQuery.SelectList.class) GroupQuery groupQuery) {
        //返回内容
        return new Json(ReturnCode.成功, groupService.listEnhance(groupQuery));
    }


    /**
     * 组单条查询
     *
     * @param groupQuery:
     * @return com.utils.Json
     * @author 孙凯伦
     * @since 2021-10-21 01:59:44
     */
    @Methods(methodsName = "组单条查询", methods = "selectOne")
    @ApiOperation(value = "组单条查询", httpMethod = "GET", notes = "组单条查询", response = Json.class)
    @GetMapping("/selectOne")
    public Json<GroupVO> selectOne(@Validated(value = GroupQuery.SelectOne.class) GroupQuery groupQuery) {
        //返回内容
        return new Json(ReturnCode.成功, groupService.getOneEnhance(groupQuery));
    }


    /**
     * 组总数查询
     *
     * @param groupQuery:
     * @return com.utils.Json
     * @author 孙凯伦
     * @since 2021-10-21 01:59:44
     */
    @Methods(methodsName = "组总数查询", methods = "count")
    @ApiOperation(value = "组总数查询", httpMethod = "GET", notes = "组总数查询", response = Json.class)
    @GetMapping(value = "/count")
    public Json<Integer> count(@Validated(value = GroupQuery.Count.class) GroupQuery groupQuery) {
        //返回内容
        return new Json(ReturnCode.成功, groupService.countEnhance(groupQuery));
    }


   /**
    * 组新增
    *
    * @param groupBO:
    * @return com.utils.Json
    * @author 孙凯伦
    * @since 2021-10-21 01:59:44
    */
    @PreventRepeat
    @Methods(methodsName = "组新增", methods = "save")
    @ApiOperation(value = "组新增", httpMethod = "POST", notes = "组新增", response = Json.class)
    @ApiOperationSupport(ignoreParameters = {"id","createDateTime","createName","modifyDateTime","modifyName","isDelete","version"})
    @PostMapping("/save")
    public Json<String> save(@Validated(value = GroupBO.Save.class) GroupBO groupBO, HttpServletRequest httpServletRequest) {
        //缓存取出用户
        Map<String, Object> u = RedisUtils.getToken(stringRedisTemplate, httpServletRequest);
        if(u != null){
            groupBO.setCreateName(u.get("name") + "-" + u.get("id"));
        }
        //返回内容
        return new Json(ReturnCode.成功, groupService.saveEnhance(groupBO));
    }


    /**
     * 组修改
     *
     * @param groupBO:
     * @return com.utils.Json
     * @author 孙凯伦
     * @since 2021-10-21 01:59:44
     */
    @PreventRepeat
    @Methods(methodsName = "组修改", methods = "update")
    @ApiOperation(value = "组修改", httpMethod = "PUT", notes = "组修改", response = Json.class)
    @ApiOperationSupport(ignoreParameters = {"createDateTime","createName","modifyDateTime","modifyName","isDelete","version"})
    @PutMapping("/update")
    public Json<Boolean> update(@Validated(value = GroupBO.Update.class) GroupBO groupBO, HttpServletRequest httpServletRequest) {
        //缓存取出用户
        Map<String, Object> u = RedisUtils.getToken(stringRedisTemplate, httpServletRequest);
        if(u != null){
            groupBO.setModifyName(u.get("name") + "-" + u.get("id"));
        }
        //返回内容
        return new Json(ReturnCode.成功, groupService.updateEnhance(groupBO));
    }


    /**
     * 组删除
     *
     * @param groupBO:
     * @return com.utils.Json
     * @author 孙凯伦
     * @since 2021-10-21 01:59:44
     */
    @Methods(methodsName = "组删除", methods = "remove")
    @ApiOperation(value = "组删除", httpMethod = "DELETE", notes = "组删除", response = Json.class)
    @ApiOperationSupport(includeParameters = {"id"})
    @DeleteMapping("/remove")
    public Json<Boolean> remove(@Validated(value = GroupBO.Remove.class) GroupBO groupBO) {
        return new Json(ReturnCode.成功, groupService.removeEnhance(groupBO));
    }


}