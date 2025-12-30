package com.gb.user.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gb.user.entity.UserInvoiceServiceRelation;
import com.gb.user.service.UserInvoiceServiceRelationService;
import com.gb.utils.Json;
import com.gb.utils.RedisUtils;
import com.gb.utils.annotations.Methods;
import com.gb.utils.annotations.PreventRepeat;
import com.gb.utils.annotations.RequestRequired;
import java.util.Optional;
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
/**
 * <p>
 * 用户发票与发票服务关联关系表 前端控制器
 * </p>
 *
 * @author sunx
 * @since 2021-05-27
 */
@Slf4j
@RequestRequired
@RestController
@Setter(onMethod_ = {@Autowired})
@RequestMapping("/user-invoice-service-relation")
@Api(tags = "用户发票与发票服务关联关系表")
public class UserInvoiceServiceRelationController {

    /**
     * 用户发票与发票服务关联关系表
     */
    private UserInvoiceServiceRelationService userInvoiceServiceRelationService;
    /**
     * redis操作
     */
    private StringRedisTemplate stringRedisTemplate;


    /**
     * 用户发票与发票服务关联关系表集合查询
     *
     * @param current:
     * @param size:
     * @param userInvoiceServiceRelation:
     * @return com.utils.Json
     * @author sunx
     * @since 2021-05-27
     */
    @Methods(methodsName = "用户发票与发票服务关联关系表集合查询", methods = "select")
    @ApiOperation(value = "用户发票与发票服务关联关系表集合查询", httpMethod = "GET", notes = "用户发票与发票服务关联关系表集合查询", response = Json.class)
    @ApiImplicitParams({
        @ApiImplicitParam(name = "current", value = "当前页", dataType = "int"),
        @ApiImplicitParam(name = "size", value = "分页显示数量", dataType = "int"),
    })
    @GetMapping("/select")
    public Json<IPage<UserInvoiceServiceRelation>> select(Integer current, Integer size, UserInvoiceServiceRelation userInvoiceServiceRelation) {
        if (current != null && size != null) {
            Page page = new Page(current, size);
            //返回内容
            return new Json(ReturnCode.成功, userInvoiceServiceRelationService.pageEnhance(page, userInvoiceServiceRelation));
        } else {
            //返回内容
            return new Json(ReturnCode.成功, userInvoiceServiceRelationService.listEnhance(userInvoiceServiceRelation));
        }
    }


    /**
     * 用户发票与发票服务关联关系表单条查询
     *
     * @param userInvoiceServiceRelation:
     * @return com.utils.Json
     * @author sunx
     * @since 2021-05-27
     */
    @Methods(methodsName = "用户发票与发票服务关联关系表单条查询", methods = "selectOne")
    @ApiOperation(value = "用户发票与发票服务关联关系表单条查询", httpMethod = "GET", notes = "用户发票与发票服务关联关系表单条查询", response = Json.class)
    @GetMapping("/selectOne")
    public Json<UserInvoiceServiceRelation> selectOne(UserInvoiceServiceRelation userInvoiceServiceRelation) {
        //返回内容
        return new Json(ReturnCode.成功, userInvoiceServiceRelationService.getOneEnhance(userInvoiceServiceRelation));
    }


    /**
     * 用户发票与发票服务关联关系表总数查询
     *
     * @param userInvoiceServiceRelation:
     * @return com.utils.Json
     * @author sunx
     * @since 2021-05-27
     */
    @Methods(methodsName = "用户发票与发票服务关联关系表总数查询", methods = "count")
    @ApiOperation(value = "用户发票与发票服务关联关系表总数查询", httpMethod = "GET", notes = "用户发票与发票服务关联关系表总数查询", response = Json.class)
    @GetMapping(value = "/count")
    public Json<Integer> count(UserInvoiceServiceRelation userInvoiceServiceRelation) {
        //返回内容
        return new Json(ReturnCode.成功, userInvoiceServiceRelationService.countEnhance(userInvoiceServiceRelation));
    }


   /**
    * 用户发票与发票服务关联关系表新增
    *
    * @param userInvoiceServiceRelation:
    * @return com.utils.Json
    * @author sunx
    * @since 2021-05-27
    */
    @PreventRepeat
    @Methods(methodsName = "用户发票与发票服务关联关系表新增", methods = "save")
    @ApiOperation(value = "用户发票与发票服务关联关系表新增", httpMethod = "POST", notes = "用户发票与发票服务关联关系表新增", response = Json.class)
    @PostMapping("/save")
    public Json<String> save(UserInvoiceServiceRelation userInvoiceServiceRelation, HttpServletRequest httpServletRequest) throws Exception {
        //缓存取出用户
        Map<String, Object> u = RedisUtils.getToken(stringRedisTemplate, httpServletRequest);
        if(u != null){
            userInvoiceServiceRelation.setCreateName(u.get("name") + "-" + u.get("id"));
        }
        Boolean b = userInvoiceServiceRelationService.saveEnhance(false, userInvoiceServiceRelation);
        if (b) {
            //返回内容
            return new Json(ReturnCode.成功, userInvoiceServiceRelation.getId());
        } else {
            //返回内容
            return new Json(ReturnCode.系统执行出错, null);
        }

    }


    /**
     * 用户发票与发票服务关联关系表修改
     *
     * @param userInvoiceServiceRelation:
     * @return com.utils.Json
     * @author sunx
     * @since 2021-05-27
     */
    @PreventRepeat
    @Methods(methodsName = "用户发票与发票服务关联关系表修改", methods = "update")
    @ApiOperation(value = "用户发票与发票服务关联关系表修改", httpMethod = "PUT", notes = "用户发票与发票服务关联关系表修改", response = Json.class)
    @PutMapping("/update")
    public Json<Boolean> update(UserInvoiceServiceRelation userInvoiceServiceRelation, HttpServletRequest httpServletRequest) {
        //缓存取出用户
        Map<String, Object> u = RedisUtils.getToken(stringRedisTemplate, httpServletRequest);
        if(u != null){
            userInvoiceServiceRelation.setModifyName(u.get("name") + "-" + u.get("id"));
        }
        //返回内容
        return new Json(ReturnCode.成功, userInvoiceServiceRelationService.updateEnhance(userInvoiceServiceRelation));
    }


    /**
     * 用户发票与发票服务关联关系表删除
     *
     * @param userInvoiceServiceRelation:
     * @return com.utils.Json
     * @author sunx
     * @since 2021-05-27
     */
    @Methods(methodsName = "用户发票与发票服务关联关系表删除", methods = "remove")
    @ApiOperation(value = "用户发票与发票服务关联关系表删除", httpMethod = "DELETE", notes = "用户发票与发票服务关联关系表删除", response = Json.class)
    @DeleteMapping("/remove")
    public Json<Boolean> remove(UserInvoiceServiceRelation userInvoiceServiceRelation) {
        return new Json(ReturnCode.成功, userInvoiceServiceRelationService.removeEnhance(userInvoiceServiceRelation));
    }


}
