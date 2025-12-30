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
import com.gb.account.service.UserTypeValueRelationshipService;
import com.gb.account.entity.query.UserTypeValueRelationshipQuery;
import com.gb.account.entity.vo.UserTypeValueRelationshipVO;
import com.gb.account.entity.bo.UserTypeValueRelationshipBO;


/**
 * Created with IntelliJ IDEA.
 * @author:     	孙凯伦
 * @since:   	    2021-10-21 01:49:05
 * @description:	TODO  用户类型值关联，Comment请求层
 * @source:  	    代码生成器
 */
@Slf4j
@RequestRequired
@ApiSupport(author = "孙凯伦")
@Setter(onMethod_ = {@Autowired})
@RestController
@RequestMapping("/userTypeValueRelationship")
@Api(tags = "用户类型值关联")
public class UserTypeValueRelationshipController {


    /**
     * 用户类型值关联
     */
    private UserTypeValueRelationshipService userTypeValueRelationshipService;
    /**
     * redis操作
     */
    private StringRedisTemplate stringRedisTemplate;


    /**
     * 用户类型值关联集合分页查询
     *
     * @param current:
     * @param size:
     * @param userTypeValueRelationshipQuery:
     * @return com.utils.Json
     * @author 孙凯伦
     * @since 2021-10-21 01:49:05
     */
    @Methods(methodsName = "用户类型值关联集合分页查询", methods = "select")
    @ApiOperation(value = "用户类型值关联集合分页查询", httpMethod = "GET", notes = "用户类型值关联集合查询", response = Json.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "current", value = "当前页", dataType = "int", required = true),
            @ApiImplicitParam(name = "size", value = "分页显示数量", dataType = "int", required = true),
    })
    @GetMapping("/select")
    public Json<Page<UserTypeValueRelationshipVO>> select(@Validated(value = UserTypeValueRelationshipQuery.Select.class) UserTypeValueRelationshipQuery userTypeValueRelationshipQuery, Integer current, Integer size) {
        //返回内容
        return new Json(ReturnCode.成功, userTypeValueRelationshipService.pageEnhance(new Page(current, size), userTypeValueRelationshipQuery));
    }


    /**
     * 用户类型值关联集合查询
     *
     * @param userTypeValueRelationshipQuery:
     * @return com.utils.Json
     * @author 孙凯伦
     * @since 2021-10-21 01:49:05
     */
    @Methods(methodsName = "用户类型值关联集合查询", methods = "selectList")
    @ApiOperation(value = "用户类型值关联集合查询", httpMethod = "GET", notes = "用户类型值关联集合查询", response = Json.class)
    @GetMapping("/selectList")
    public Json<List<UserTypeValueRelationshipVO>> selectList(@Validated(value = UserTypeValueRelationshipQuery.SelectList.class) UserTypeValueRelationshipQuery userTypeValueRelationshipQuery) {
        //返回内容
        return new Json(ReturnCode.成功, userTypeValueRelationshipService.listEnhance(userTypeValueRelationshipQuery));
    }


    /**
     * 用户类型值关联单条查询
     *
     * @param userTypeValueRelationshipQuery:
     * @return com.utils.Json
     * @author 孙凯伦
     * @since 2021-10-21 01:49:05
     */
    @Methods(methodsName = "用户类型值关联单条查询", methods = "selectOne")
    @ApiOperation(value = "用户类型值关联单条查询", httpMethod = "GET", notes = "用户类型值关联单条查询", response = Json.class)
    @GetMapping("/selectOne")
    public Json<UserTypeValueRelationshipVO> selectOne(@Validated(value = UserTypeValueRelationshipQuery.SelectOne.class) UserTypeValueRelationshipQuery userTypeValueRelationshipQuery) {
        //返回内容
        return new Json(ReturnCode.成功, userTypeValueRelationshipService.getOneEnhance(userTypeValueRelationshipQuery));
    }


    /**
     * 用户类型值关联总数查询
     *
     * @param userTypeValueRelationshipQuery:
     * @return com.utils.Json
     * @author 孙凯伦
     * @since 2021-10-21 01:49:05
     */
    @Methods(methodsName = "用户类型值关联总数查询", methods = "count")
    @ApiOperation(value = "用户类型值关联总数查询", httpMethod = "GET", notes = "用户类型值关联总数查询", response = Json.class)
    @GetMapping(value = "/count")
    public Json<Integer> count(@Validated(value = UserTypeValueRelationshipQuery.Count.class) UserTypeValueRelationshipQuery userTypeValueRelationshipQuery) {
        //返回内容
        return new Json(ReturnCode.成功, userTypeValueRelationshipService.countEnhance(userTypeValueRelationshipQuery));
    }


   /**
    * 用户类型值关联新增
    *
    * @param userTypeValueRelationshipBO:
    * @return com.utils.Json
    * @author 孙凯伦
    * @since 2021-10-21 01:49:05
    */
    @PreventRepeat
    @Methods(methodsName = "用户类型值关联新增", methods = "save")
    @ApiOperation(value = "用户类型值关联新增", httpMethod = "POST", notes = "用户类型值关联新增", response = Json.class)
    @ApiOperationSupport(ignoreParameters = {"id","createDateTime","createName","modifyDateTime","modifyName","isDelete","version"})
    @PostMapping("/save")
    public Json<String> save(@Validated(value = UserTypeValueRelationshipBO.Save.class) UserTypeValueRelationshipBO userTypeValueRelationshipBO, HttpServletRequest httpServletRequest) {
        //缓存取出用户
        Map<String, Object> u = RedisUtils.getToken(stringRedisTemplate, httpServletRequest);
        if(u != null){
            userTypeValueRelationshipBO.setCreateName(u.get("name") + "-" + u.get("id"));
        }
        //返回内容
        return new Json(ReturnCode.成功, userTypeValueRelationshipService.saveEnhance(userTypeValueRelationshipBO));
    }


    /**
     * 用户类型值关联修改
     *
     * @param userTypeValueRelationshipBO:
     * @return com.utils.Json
     * @author 孙凯伦
     * @since 2021-10-21 01:49:05
     */
    @PreventRepeat
    @Methods(methodsName = "用户类型值关联修改", methods = "update")
    @ApiOperation(value = "用户类型值关联修改", httpMethod = "PUT", notes = "用户类型值关联修改", response = Json.class)
    @ApiOperationSupport(ignoreParameters = {"createDateTime","createName","modifyDateTime","modifyName","isDelete","version"})
    @PutMapping("/update")
    public Json<Boolean> update(@Validated(value = UserTypeValueRelationshipBO.Update.class) UserTypeValueRelationshipBO userTypeValueRelationshipBO, HttpServletRequest httpServletRequest) {
        //缓存取出用户
        Map<String, Object> u = RedisUtils.getToken(stringRedisTemplate, httpServletRequest);
        if(u != null){
            userTypeValueRelationshipBO.setModifyName(u.get("name") + "-" + u.get("id"));
        }
        //返回内容
        return new Json(ReturnCode.成功, userTypeValueRelationshipService.updateEnhance(userTypeValueRelationshipBO));
    }


    /**
     * 用户类型值关联删除
     *
     * @param userTypeValueRelationshipBO:
     * @return com.utils.Json
     * @author 孙凯伦
     * @since 2021-10-21 01:49:05
     */
    @Methods(methodsName = "用户类型值关联删除", methods = "remove")
    @ApiOperation(value = "用户类型值关联删除", httpMethod = "DELETE", notes = "用户类型值关联删除", response = Json.class)
    @ApiOperationSupport(includeParameters = {"id"})
    @DeleteMapping("/remove")
    public Json<Boolean> remove(@Validated(value = UserTypeValueRelationshipBO.Remove.class) UserTypeValueRelationshipBO userTypeValueRelationshipBO) {
        return new Json(ReturnCode.成功, userTypeValueRelationshipService.removeEnhance(userTypeValueRelationshipBO));
    }


}