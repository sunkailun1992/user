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

import com.gb.quotation.service.QuotationSchemeOtherService;
import com.gb.quotation.entity.QuotationSchemeOther;
/**
 * <p>
 * 报价方案其他 前端控制器
 * </p>
 *
 * @author 尹涛涛
 * @since 2021-05-22
 */
@Slf4j
@RequestRequired
@RestController
@Setter(onMethod_ = {@Autowired})
@RequestMapping("/quotation-scheme-other")
@Api(tags = "报价方案其他")
public class QuotationSchemeOtherController {

    /**
     * 报价方案其他
     */
    private QuotationSchemeOtherService quotationSchemeOtherService;
    /**
     * redis操作
     */
    private StringRedisTemplate stringRedisTemplate;


    /**
     * 报价方案其他集合查询
     *
     * @param current:
     * @param size:
     * @param quotationSchemeOther:
     * @return com.utils.Json
     * @author 尹涛涛
     * @since 2021-05-22
     */
    @Methods(methodsName = "报价方案其他集合查询", methods = "select")
    @ApiOperation(value = "报价方案其他集合查询", httpMethod = "GET", notes = "报价方案其他集合查询", response = Json.class)
    @ApiImplicitParams({
        @ApiImplicitParam(name = "current", value = "当前页", dataType = "int"),
        @ApiImplicitParam(name = "size", value = "分页显示数量", dataType = "int"),
    })
    @GetMapping("/select")
    public Json<IPage<QuotationSchemeOther>> select(Integer current, Integer size, QuotationSchemeOther quotationSchemeOther) {
        if (current != null && size != null) {
            Page page = new Page(current, size);
            //返回内容
            return new Json(ReturnCode.成功, quotationSchemeOtherService.pageEnhance(page, quotationSchemeOther));
        } else {
            //返回内容
            return new Json(ReturnCode.成功, quotationSchemeOtherService.listEnhance(quotationSchemeOther));
        }
    }


    /**
     * 报价方案其他单条查询
     *
     * @param quotationSchemeOther:
     * @return com.utils.Json
     * @author 尹涛涛
     * @since 2021-05-22
     */
    @Methods(methodsName = "报价方案其他单条查询", methods = "selectOne")
    @ApiOperation(value = "报价方案其他单条查询", httpMethod = "GET", notes = "报价方案其他单条查询", response = Json.class)
    @GetMapping("/selectOne")
    public Json<QuotationSchemeOther> selectOne(QuotationSchemeOther quotationSchemeOther) {
        //返回内容
        return new Json(ReturnCode.成功, quotationSchemeOtherService.getOneEnhance(quotationSchemeOther));
    }


    /**
     * 报价方案其他总数查询
     *
     * @param quotationSchemeOther:
     * @return com.utils.Json
     * @author 尹涛涛
     * @since 2021-05-22
     */
    @Methods(methodsName = "报价方案其他总数查询", methods = "count")
    @ApiOperation(value = "报价方案其他总数查询", httpMethod = "GET", notes = "报价方案其他总数查询", response = Json.class)
    @GetMapping(value = "/count")
    public Json<Integer> count(QuotationSchemeOther quotationSchemeOther) {
        //返回内容
        return new Json(ReturnCode.成功, quotationSchemeOtherService.countEnhance(quotationSchemeOther));
    }


   /**
    * 报价方案其他新增
    *
    * @param quotationSchemeOther:
    * @return com.utils.Json
    * @author 尹涛涛
    * @since 2021-05-22
    */
    @PreventRepeat
    @Methods(methodsName = "报价方案其他新增", methods = "save")
    @ApiOperation(value = "报价方案其他新增", httpMethod = "POST", notes = "报价方案其他新增", response = Json.class)
    @PostMapping("/save")
    public Json<String> save(QuotationSchemeOther quotationSchemeOther, HttpServletRequest httpServletRequest) {
        //缓存取出用户
        Map<String, Object> u = RedisUtils.getToken(stringRedisTemplate, httpServletRequest);
        if(u != null){
            quotationSchemeOther.setCreateName(u.get("name") + "-" + u.get("id"));
        }
        Boolean b = quotationSchemeOtherService.saveEnhance(quotationSchemeOther);
        if (b) {
            //返回内容
            return new Json(ReturnCode.成功, Optional.of(quotationSchemeOther.getId()));
        } else {
            //返回内容
            return new Json(ReturnCode.系统执行出错, "新增参数数量为空");
        }

    }


    /**
     * 报价方案其他修改
     *
     * @param quotationSchemeOther:
     * @return com.utils.Json
     * @author 尹涛涛
     * @since 2021-05-22
     */
    @PreventRepeat
    @Methods(methodsName = "报价方案其他修改", methods = "update")
    @ApiOperation(value = "报价方案其他修改", httpMethod = "PUT", notes = "报价方案其他修改", response = Json.class)
    @PutMapping("/update")
    public Json<Boolean> update(QuotationSchemeOther quotationSchemeOther, HttpServletRequest httpServletRequest) {
        //缓存取出用户
        Map<String, Object> u = RedisUtils.getToken(stringRedisTemplate, httpServletRequest);
        if(u != null){
            quotationSchemeOther.setModifyName(u.get("name") + "-" + u.get("id"));
        }
        //返回内容
        return new Json(ReturnCode.成功, quotationSchemeOtherService.updateEnhance(quotationSchemeOther));
    }


    /**
     * 报价方案其他删除
     *
     * @param quotationSchemeOther:
     * @return com.utils.Json
     * @author 尹涛涛
     * @since 2021-05-22
     */
    @Methods(methodsName = "报价方案其他删除", methods = "remove")
    @ApiOperation(value = "报价方案其他删除", httpMethod = "DELETE", notes = "报价方案其他删除", response = Json.class)
    @DeleteMapping("/remove")
    public Json<Boolean> remove(QuotationSchemeOther quotationSchemeOther) {
        return new Json(ReturnCode.成功, quotationSchemeOtherService.removeEnhance(quotationSchemeOther));
    }


}
