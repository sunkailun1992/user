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
import com.gb.account.service.SourceValueService;
import com.gb.account.entity.query.SourceValueQuery;
import com.gb.account.entity.vo.SourceValueVO;
import com.gb.account.entity.bo.SourceValueBO;


/**
 * Created with IntelliJ IDEA.
 * @author:     	孙凯伦
 * @since:   	    2021-11-03 03:57:55
 * @description:	TODO  来源值，Comment请求层
 * @source:  	    代码生成器
 */
@Slf4j
@RequestRequired
@ApiSupport(author = "孙凯伦")
@Setter(onMethod_ = {@Autowired})
@RestController
@RequestMapping("/sourceValue")
@Api(tags = "来源值")
public class SourceValueController {


    /**
     * 来源值
     */
    private SourceValueService sourceValueService;
    /**
     * redis操作
     */
    private StringRedisTemplate stringRedisTemplate;


    /**
     * 来源值集合分页查询
     *
     * @param current:
     * @param size:
     * @param sourceValueQuery:
     * @return com.utils.Json
     * @author 孙凯伦
     * @since 2021-11-03 03:57:55
     */
    @Methods(methodsName = "来源值集合分页查询", methods = "select")
    @ApiOperation(value = "来源值集合分页查询", httpMethod = "GET", notes = "来源值集合查询", response = Json.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "current", value = "当前页", dataType = "int", required = true),
            @ApiImplicitParam(name = "size", value = "分页显示数量", dataType = "int", required = true),
    })
    @GetMapping("/select")
    public Json<Page<SourceValueVO>> select(@Validated(value = SourceValueQuery.Select.class) SourceValueQuery sourceValueQuery, Integer current, Integer size) {
        //返回内容
        return new Json(ReturnCode.成功, sourceValueService.pageEnhance(new Page(current, size), sourceValueQuery));
    }


    /**
     * 来源值集合查询
     *
     * @param sourceValueQuery:
     * @return com.utils.Json
     * @author 孙凯伦
     * @since 2021-11-03 03:57:55
     */
    @Methods(methodsName = "来源值集合查询", methods = "selectList")
    @ApiOperation(value = "来源值集合查询", httpMethod = "GET", notes = "来源值集合查询", response = Json.class)
    @GetMapping("/selectList")
    public Json<List<SourceValueVO>> selectList(@Validated(value = SourceValueQuery.SelectList.class) SourceValueQuery sourceValueQuery) {
        //返回内容
        return new Json(ReturnCode.成功, sourceValueService.listEnhance(sourceValueQuery));
    }


    /**
     * 来源值单条查询
     *
     * @param sourceValueQuery:
     * @return com.utils.Json
     * @author 孙凯伦
     * @since 2021-11-03 03:57:55
     */
    @Methods(methodsName = "来源值单条查询", methods = "selectOne")
    @ApiOperation(value = "来源值单条查询", httpMethod = "GET", notes = "来源值单条查询", response = Json.class)
    @GetMapping("/selectOne")
    public Json<SourceValueVO> selectOne(@Validated(value = SourceValueQuery.SelectOne.class) SourceValueQuery sourceValueQuery) {
        //返回内容
        return new Json(ReturnCode.成功, sourceValueService.getOneEnhance(sourceValueQuery));
    }


    /**
     * 来源值总数查询
     *
     * @param sourceValueQuery:
     * @return com.utils.Json
     * @author 孙凯伦
     * @since 2021-11-03 03:57:55
     */
    @Methods(methodsName = "来源值总数查询", methods = "count")
    @ApiOperation(value = "来源值总数查询", httpMethod = "GET", notes = "来源值总数查询", response = Json.class)
    @GetMapping(value = "/count")
    public Json<Integer> count(@Validated(value = SourceValueQuery.Count.class) SourceValueQuery sourceValueQuery) {
        //返回内容
        return new Json(ReturnCode.成功, sourceValueService.countEnhance(sourceValueQuery));
    }


   /**
    * 来源值新增
    *
    * @param sourceValueBO:
    * @return com.utils.Json
    * @author 孙凯伦
    * @since 2021-11-03 03:57:55
    */
    @PreventRepeat
    @Methods(methodsName = "来源值新增", methods = "save")
    @ApiOperation(value = "来源值新增", httpMethod = "POST", notes = "来源值新增", response = Json.class)
    @ApiOperationSupport(ignoreParameters = {"id","createDateTime","createName","modifyDateTime","modifyName","isDelete","version"})
    @PostMapping("/save")
    public Json<String> save(@Validated(value = SourceValueBO.Save.class) SourceValueBO sourceValueBO, HttpServletRequest httpServletRequest) {
        //缓存取出用户
        Map<String, Object> u = RedisUtils.getToken(stringRedisTemplate, httpServletRequest);
        if(u != null){
            sourceValueBO.setCreateName(u.get("name") + "-" + u.get("id"));
        }
        //返回内容
        return new Json(ReturnCode.成功, sourceValueService.saveEnhance(sourceValueBO));
    }


    /**
     * 来源值修改
     *
     * @param sourceValueBO:
     * @return com.utils.Json
     * @author 孙凯伦
     * @since 2021-11-03 03:57:55
     */
    @PreventRepeat
    @Methods(methodsName = "来源值修改", methods = "update")
    @ApiOperation(value = "来源值修改", httpMethod = "PUT", notes = "来源值修改", response = Json.class)
    @ApiOperationSupport(ignoreParameters = {"createDateTime","createName","modifyDateTime","modifyName","isDelete","version"})
    @PutMapping("/update")
    public Json<Boolean> update(@Validated(value = SourceValueBO.Update.class) SourceValueBO sourceValueBO, HttpServletRequest httpServletRequest) {
        //缓存取出用户
        Map<String, Object> u = RedisUtils.getToken(stringRedisTemplate, httpServletRequest);
        if(u != null){
            sourceValueBO.setModifyName(u.get("name") + "-" + u.get("id"));
        }
        //返回内容
        return new Json(ReturnCode.成功, sourceValueService.updateEnhance(sourceValueBO));
    }


    /**
     * 来源值删除
     *
     * @param sourceValueBO:
     * @return com.utils.Json
     * @author 孙凯伦
     * @since 2021-11-03 03:57:55
     */
    @Methods(methodsName = "来源值删除", methods = "remove")
    @ApiOperation(value = "来源值删除", httpMethod = "DELETE", notes = "来源值删除", response = Json.class)
    @ApiOperationSupport(includeParameters = {"id"})
    @DeleteMapping("/remove")
    public Json<Boolean> remove(@Validated(value = SourceValueBO.Remove.class) SourceValueBO sourceValueBO) {
        return new Json(ReturnCode.成功, sourceValueService.removeEnhance(sourceValueBO));
    }


}