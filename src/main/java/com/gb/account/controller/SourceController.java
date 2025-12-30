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
import com.gb.account.service.SourceService;
import com.gb.account.entity.query.SourceQuery;
import com.gb.account.entity.vo.SourceVO;
import com.gb.account.entity.bo.SourceBO;


/**
 * Created with IntelliJ IDEA.
 * @author:     	孙凯伦
 * @since:   	    2021-11-03 03:57:55
 * @description:	TODO  来源，Comment请求层
 * @source:  	    代码生成器
 */
@Slf4j
@RequestRequired
@ApiSupport(author = "孙凯伦")
@Setter(onMethod_ = {@Autowired})
@RestController
@RequestMapping("/source")
@Api(tags = "来源")
public class SourceController {


    /**
     * 来源
     */
    private SourceService sourceService;
    /**
     * redis操作
     */
    private StringRedisTemplate stringRedisTemplate;


    /**
     * 来源集合分页查询
     *
     * @param current:
     * @param size:
     * @param sourceQuery:
     * @return com.utils.Json
     * @author 孙凯伦
     * @since 2021-11-03 03:57:55
     */
    @Methods(methodsName = "来源集合分页查询", methods = "select")
    @ApiOperation(value = "来源集合分页查询", httpMethod = "GET", notes = "来源集合查询", response = Json.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "current", value = "当前页", dataType = "int", required = true),
            @ApiImplicitParam(name = "size", value = "分页显示数量", dataType = "int", required = true),
    })
    @GetMapping("/select")
    public Json<Page<SourceVO>> select(@Validated(value = SourceQuery.Select.class) SourceQuery sourceQuery, Integer current, Integer size) {
        //返回内容
        return new Json(ReturnCode.成功, sourceService.pageEnhance(new Page(current, size), sourceQuery));
    }


    /**
     * 来源集合查询
     *
     * @param sourceQuery:
     * @return com.utils.Json
     * @author 孙凯伦
     * @since 2021-11-03 03:57:55
     */
    @Methods(methodsName = "来源集合查询", methods = "selectList")
    @ApiOperation(value = "来源集合查询", httpMethod = "GET", notes = "来源集合查询", response = Json.class)
    @GetMapping("/selectList")
    public Json<List<SourceVO>> selectList(@Validated(value = SourceQuery.SelectList.class) SourceQuery sourceQuery) {
        //返回内容
        return new Json(ReturnCode.成功, sourceService.listEnhance(sourceQuery));
    }


    /**
     * 来源单条查询
     *
     * @param sourceQuery:
     * @return com.utils.Json
     * @author 孙凯伦
     * @since 2021-11-03 03:57:55
     */
    @Methods(methodsName = "来源单条查询", methods = "selectOne")
    @ApiOperation(value = "来源单条查询", httpMethod = "GET", notes = "来源单条查询", response = Json.class)
    @GetMapping("/selectOne")
    public Json<SourceVO> selectOne(@Validated(value = SourceQuery.SelectOne.class) SourceQuery sourceQuery) {
        //返回内容
        return new Json(ReturnCode.成功, sourceService.getOneEnhance(sourceQuery));
    }


    /**
     * 来源总数查询
     *
     * @param sourceQuery:
     * @return com.utils.Json
     * @author 孙凯伦
     * @since 2021-11-03 03:57:55
     */
    @Methods(methodsName = "来源总数查询", methods = "count")
    @ApiOperation(value = "来源总数查询", httpMethod = "GET", notes = "来源总数查询", response = Json.class)
    @GetMapping(value = "/count")
    public Json<Integer> count(@Validated(value = SourceQuery.Count.class) SourceQuery sourceQuery) {
        //返回内容
        return new Json(ReturnCode.成功, sourceService.countEnhance(sourceQuery));
    }


   /**
    * 来源新增
    *
    * @param sourceBO:
    * @return com.utils.Json
    * @author 孙凯伦
    * @since 2021-11-03 03:57:55
    */
    @PreventRepeat
    @Methods(methodsName = "来源新增", methods = "save")
    @ApiOperation(value = "来源新增", httpMethod = "POST", notes = "来源新增", response = Json.class)
    @ApiOperationSupport(ignoreParameters = {"id","createDateTime","createName","modifyDateTime","modifyName","isDelete","version"})
    @PostMapping("/save")
    public Json<String> save(@Validated(value = SourceBO.Save.class) SourceBO sourceBO, HttpServletRequest httpServletRequest) {
        //缓存取出用户
        Map<String, Object> u = RedisUtils.getToken(stringRedisTemplate, httpServletRequest);
        if(u != null){
            sourceBO.setCreateName(u.get("name") + "-" + u.get("id"));
        }
        //返回内容
        return new Json(ReturnCode.成功, sourceService.saveEnhance(sourceBO));
    }


    /**
     * 来源修改
     *
     * @param sourceBO:
     * @return com.utils.Json
     * @author 孙凯伦
     * @since 2021-11-03 03:57:55
     */
    @PreventRepeat
    @Methods(methodsName = "来源修改", methods = "update")
    @ApiOperation(value = "来源修改", httpMethod = "PUT", notes = "来源修改", response = Json.class)
    @ApiOperationSupport(ignoreParameters = {"createDateTime","createName","modifyDateTime","modifyName","isDelete","version"})
    @PutMapping("/update")
    public Json<Boolean> update(@Validated(value = SourceBO.Update.class) SourceBO sourceBO, HttpServletRequest httpServletRequest) {
        //缓存取出用户
        Map<String, Object> u = RedisUtils.getToken(stringRedisTemplate, httpServletRequest);
        if(u != null){
            sourceBO.setModifyName(u.get("name") + "-" + u.get("id"));
        }
        //返回内容
        return new Json(ReturnCode.成功, sourceService.updateEnhance(sourceBO));
    }


    /**
     * 来源删除
     *
     * @param sourceBO:
     * @return com.utils.Json
     * @author 孙凯伦
     * @since 2021-11-03 03:57:55
     */
    @Methods(methodsName = "来源删除", methods = "remove")
    @ApiOperation(value = "来源删除", httpMethod = "DELETE", notes = "来源删除", response = Json.class)
    @ApiOperationSupport(includeParameters = {"id"})
    @DeleteMapping("/remove")
    public Json<Boolean> remove(@Validated(value = SourceBO.Remove.class) SourceBO sourceBO) {
        return new Json(ReturnCode.成功, sourceService.removeEnhance(sourceBO));
    }


}