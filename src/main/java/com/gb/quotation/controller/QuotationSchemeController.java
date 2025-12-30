package com.gb.quotation.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gb.quotation.entity.QuotationScheme;
import com.gb.quotation.service.QuotationSchemeService;
import com.gb.utils.Json;
import com.gb.utils.RedisUtils;
import com.gb.utils.annotations.Methods;
import com.gb.utils.annotations.PreventRepeat;
import com.gb.utils.annotations.RequestRequired;
import com.gb.utils.enumeration.ReturnCode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.Optional;

/**
 * <p>
 * 报价方案 前端控制器
 * </p>
 *
 * @author 尹涛涛
 * @since 2021-05-22
 */
@Slf4j
@RequestRequired
@RestController
@Setter(onMethod_ = {@Autowired})
@RequestMapping("/quotation-scheme")
@Api(tags = "报价方案")
public class QuotationSchemeController {

    /**
     * 报价方案
     */
    private QuotationSchemeService quotationSchemeService;
    /**
     * redis操作
     */
    private StringRedisTemplate stringRedisTemplate;


    /**
     * 报价方案集合查询
     *
     * @param current:
     * @param size:
     * @param quotationScheme:
     * @return com.utils.Json
     * @author 尹涛涛
     * @since 2021-05-22
     */
    @Methods(methodsName = "报价方案集合查询", methods = "select")
    @ApiOperation(value = "报价方案集合查询", httpMethod = "GET", notes = "报价方案集合查询", response = Json.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "current", value = "当前页", dataType = "int"),
            @ApiImplicitParam(name = "size", value = "分页显示数量", dataType = "int"),
    })
    @GetMapping("/select")
    public Json<IPage<QuotationScheme>> select(Integer current, Integer size, QuotationScheme quotationScheme) {
        if (current != null && size != null) {
            Page page = new Page(current, size);
            //返回内容
            return new Json(ReturnCode.成功, quotationSchemeService.pageEnhance(page, quotationScheme));
        } else {
            //返回内容
            return new Json(ReturnCode.成功, quotationSchemeService.listEnhance(quotationScheme));
        }
    }


    /**
     * 报价方案单条查询
     *
     * @param quotationScheme:
     * @return com.utils.Json
     * @author 尹涛涛
     * @since 2021-05-22
     */
    @Methods(methodsName = "报价方案单条查询", methods = "selectOne")
    @ApiOperation(value = "报价方案单条查询", httpMethod = "GET", notes = "报价方案单条查询", response = Json.class)
    @GetMapping("/selectOne")
    public Json<QuotationScheme> selectOne(QuotationScheme quotationScheme) {
        //返回内容
        return new Json(ReturnCode.成功, quotationSchemeService.getOneEnhance(quotationScheme));
    }


    /**
     * 报价方案总数查询
     *
     * @param quotationScheme:
     * @return com.utils.Json
     * @author 尹涛涛
     * @since 2021-05-22
     */
    @Methods(methodsName = "报价方案总数查询", methods = "count")
    @ApiOperation(value = "报价方案总数查询", httpMethod = "GET", notes = "报价方案总数查询", response = Json.class)
    @GetMapping(value = "/count")
    public Json<Integer> count(QuotationScheme quotationScheme) {
        //返回内容
        return new Json(ReturnCode.成功, quotationSchemeService.countEnhance(quotationScheme));
    }


    /**
     * 报价方案新增
     *
     * @param quotationScheme:
     * @return com.utils.Json
     * @author 尹涛涛
     * @since 2021-05-22
     */
    @Methods(methodsName = "报价方案新增", methods = "save")
    @ApiOperation(value = "报价方案新增", httpMethod = "POST", notes = "报价方案新增", response = Json.class)
    @PostMapping("/save")
    public Json<String> save(@RequestBody QuotationScheme quotationScheme, HttpServletRequest httpServletRequest) throws Exception {
        //缓存取出用户
        Map<String, Object> u = RedisUtils.getToken(stringRedisTemplate, httpServletRequest);
        if (u != null) {
            quotationScheme.setCreateName(u.get("name") + "-" + u.get("id"));
        }
        Boolean b = quotationSchemeService.saveEnhance(quotationScheme);
        if (b) {
            //返回内容
            return new Json(ReturnCode.成功, Optional.of(quotationScheme.getId()));
        } else {
            //返回内容
            return new Json(ReturnCode.系统执行出错, "新增参数数量为空");
        }

    }


    /**
     * 报价方案修改
     *
     * @param quotationScheme:
     * @return com.utils.Json
     * @author 尹涛涛
     * @since 2021-05-22
     */
    @PreventRepeat
    @Methods(methodsName = "报价方案修改", methods = "update")
    @ApiOperation(value = "报价方案修改", httpMethod = "PUT", notes = "报价方案修改", response = Json.class)
    @PutMapping("/update")
    public Json<Boolean> update(QuotationScheme quotationScheme, HttpServletRequest httpServletRequest) {
        //缓存取出用户
        Map<String, Object> u = RedisUtils.getToken(stringRedisTemplate, httpServletRequest);
        if (u != null) {
            quotationScheme.setModifyName(u.get("name") + "-" + u.get("id"));
        }
        //返回内容
        return new Json(ReturnCode.成功, quotationSchemeService.updateEnhance(quotationScheme));
    }


    /**
     * 报价方案删除
     *
     * @param quotationScheme:
     * @return com.utils.Json
     * @author 尹涛涛
     * @since 2021-05-22
     */
    @Methods(methodsName = "报价方案删除", methods = "remove")
    @ApiOperation(value = "报价方案删除", httpMethod = "DELETE", notes = "报价方案删除", response = Json.class)
    @DeleteMapping("/remove")
    public Json<Boolean> remove(QuotationScheme quotationScheme) {
        return new Json(ReturnCode.成功, quotationSchemeService.removeEnhance(quotationScheme));
    }

    /**
     * 根据id查询报价方案详情
     * @param quotationScheme 报价方案
     * @return
     */
    @Methods(methodsName = "根据id查询报价方案详情", methods = "findQuotationSchemeInfoById")
    @ApiOperation(value = "根据id查询报价方案详情", httpMethod = "GET", notes = "根据id查询报价方案详情", response = Json.class)
    @GetMapping("/findQuotationSchemeInfoById")
    public Json<QuotationScheme> findQuotationSchemeInfoById(QuotationScheme quotationScheme){
        log.error("进入查询报价单详情的controller方法");
        return new Json(ReturnCode.成功, quotationSchemeService.findQuotationSchemeInfoById(quotationScheme));
    }


}
