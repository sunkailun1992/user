package com.gb.quotation.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Setter;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import java.util.Map;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;
import com.gb.utils.annotations.RequestRequired;
import java.util.Optional;
import com.gb.utils.enumeration.ReturnCode;
import com.gb.utils.annotations.Methods;
import com.gb.utils.Json;
import com.gb.utils.RedisUtils;
import com.gb.utils.annotations.PreventRepeat;
import javax.servlet.http.HttpServletRequest;

import com.gb.quotation.service.QuotationSchemeSpuService;
import com.gb.quotation.entity.QuotationSchemeSpu;
/**
 * <p>
 * 报价方案产品 前端控制器
 * </p>
 *
 * @author 尹涛涛
 * @since 2021-05-22
 */
@Slf4j
@RequestRequired
@RestController
@Setter(onMethod_ = {@Autowired})
@RequestMapping("/quotation-scheme-spu")
@Api(tags = "报价方案产品")
public class QuotationSchemeSpuController {

    /**
     * 报价方案产品
     */
    private QuotationSchemeSpuService quotationSchemeSpuService;
    /**
     * redis操作
     */
    private StringRedisTemplate stringRedisTemplate;


    /**
     * 报价方案产品集合查询
     *
     * @param current:
     * @param size:
     * @param quotationSchemeSpu:
     * @return com.utils.Json
     * @author 尹涛涛
     * @since 2021-05-22
     */
    @Methods(methodsName = "报价方案产品集合查询", methods = "select")
    @ApiOperation(value = "报价方案产品集合查询", httpMethod = "GET", notes = "报价方案产品集合查询", response = Json.class)
    @ApiImplicitParams({
        @ApiImplicitParam(name = "current", value = "当前页", dataType = "int"),
        @ApiImplicitParam(name = "size", value = "分页显示数量", dataType = "int"),
    })
    @GetMapping("/select")
    public Json<IPage<QuotationSchemeSpu>> select(Integer current, Integer size, QuotationSchemeSpu quotationSchemeSpu) {
        if (current != null && size != null) {
            Page page = new Page(current, size);
            //返回内容
            return new Json(ReturnCode.成功, quotationSchemeSpuService.pageEnhance(page, quotationSchemeSpu));
        } else {
            //返回内容
            return new Json(ReturnCode.成功, quotationSchemeSpuService.listEnhance(quotationSchemeSpu));
        }
    }


    /**
     * 报价方案产品单条查询
     *
     * @param quotationSchemeSpu:
     * @return com.utils.Json
     * @author 尹涛涛
     * @since 2021-05-22
     */
    @Methods(methodsName = "报价方案产品单条查询", methods = "selectOne")
    @ApiOperation(value = "报价方案产品单条查询", httpMethod = "GET", notes = "报价方案产品单条查询", response = Json.class)
    @GetMapping("/selectOne")
    public Json<QuotationSchemeSpu> selectOne(QuotationSchemeSpu quotationSchemeSpu) {
        //返回内容
        return new Json(ReturnCode.成功, quotationSchemeSpuService.getOneEnhance(quotationSchemeSpu));
    }


    /**
     * 报价方案产品总数查询
     *
     * @param quotationSchemeSpu:
     * @return com.utils.Json
     * @author 尹涛涛
     * @since 2021-05-22
     */
    @Methods(methodsName = "报价方案产品总数查询", methods = "count")
    @ApiOperation(value = "报价方案产品总数查询", httpMethod = "GET", notes = "报价方案产品总数查询", response = Json.class)
    @GetMapping(value = "/count")
    public Json<Integer> count(QuotationSchemeSpu quotationSchemeSpu) {
        //返回内容
        return new Json(ReturnCode.成功, quotationSchemeSpuService.countEnhance(quotationSchemeSpu));
    }


   /**
    * 报价方案产品新增
    *
    * @param quotationSchemeSpu:
    * @return com.utils.Json
    * @author 尹涛涛
    * @since 2021-05-22
    */
    @PreventRepeat
    @Methods(methodsName = "报价方案产品新增", methods = "save")
    @ApiOperation(value = "报价方案产品新增", httpMethod = "POST", notes = "报价方案产品新增", response = Json.class)
    @PostMapping("/save")
    public Json<String> save(QuotationSchemeSpu quotationSchemeSpu, HttpServletRequest httpServletRequest) {
        //缓存取出用户
        Map<String, Object> u = RedisUtils.getToken(stringRedisTemplate, httpServletRequest);
        if(u != null){
            quotationSchemeSpu.setCreateName(u.get("name") + "-" + u.get("id"));
        }
        Boolean b = quotationSchemeSpuService.saveEnhance(quotationSchemeSpu);
        if (b) {
            //返回内容
            return new Json(ReturnCode.成功, Optional.of(quotationSchemeSpu.getId()));
        } else {
            //返回内容
            return new Json(ReturnCode.系统执行出错, "新增参数数量为空");
        }

    }


    /**
     * 报价方案产品修改
     *
     * @param quotationSchemeSpu:
     * @return com.utils.Json
     * @author 尹涛涛
     * @since 2021-05-22
     */
    @PreventRepeat
    @Methods(methodsName = "报价方案产品修改", methods = "update")
    @ApiOperation(value = "报价方案产品修改", httpMethod = "PUT", notes = "报价方案产品修改", response = Json.class)
    @PutMapping("/update")
    public Json<Boolean> update(QuotationSchemeSpu quotationSchemeSpu, HttpServletRequest httpServletRequest) {
        //缓存取出用户
        Map<String, Object> u = RedisUtils.getToken(stringRedisTemplate, httpServletRequest);
        if(u != null){
            quotationSchemeSpu.setModifyName(u.get("name") + "-" + u.get("id"));
        }
        //返回内容
        return new Json(ReturnCode.成功, quotationSchemeSpuService.updateEnhance(quotationSchemeSpu));
    }


    /**
     * 报价方案产品删除
     *
     * @param quotationSchemeSpu:
     * @return com.utils.Json
     * @author 尹涛涛
     * @since 2021-05-22
     */
    @Methods(methodsName = "报价方案产品删除", methods = "remove")
    @ApiOperation(value = "报价方案产品删除", httpMethod = "DELETE", notes = "报价方案产品删除", response = Json.class)
    @DeleteMapping("/remove")
    public Json<Boolean> remove(QuotationSchemeSpu quotationSchemeSpu) {
        return new Json(ReturnCode.成功, quotationSchemeSpuService.removeEnhance(quotationSchemeSpu));
    }


}
