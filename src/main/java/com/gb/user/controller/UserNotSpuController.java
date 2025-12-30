package com.gb.user.controller;

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
import com.gb.user.service.UserNotSpuService;
import com.gb.user.entity.query.UserNotSpuQuery;
import com.gb.user.entity.vo.UserNotSpuVO;
import com.gb.user.entity.bo.UserNotSpuBO;


/**
 * TODO 企业渠道用户排除产品，Comment请求层
 * 代码生成器
 *
 * @author 孙凯伦
 * @className UserNotSpuController
 * @time 2023-07-07 04:36:59
 */
@Slf4j
@RequestRequired
@ApiSupport(author = "孙凯伦")
@Setter(onMethod_ = {@Autowired})
@RestController
@RequestMapping("/user-not-spu")
@Api(tags = "企业渠道用户排除产品")
public class UserNotSpuController {


    /**
     * 企业渠道用户排除产品
     */
    private UserNotSpuService userNotSpuService;
    /**
     * redis操作
     */
    private StringRedisTemplate stringRedisTemplate;


    /**
     * TODO 分页
     *
     * @param userNotSpuQuery 企业渠道用户排除产品
     * @param current
     * @param size
     * @return Json<Page<UserNotSpuVO>>
     * @author 孙凯伦
     * @methodName select
     * @time 2023-07-07 04:36:59
     */
    @Methods(methodsName = "企业渠道用户排除产品集合分页查询", methods = "select")
    @ApiOperation(value = "企业渠道用户排除产品集合分页查询", httpMethod = "GET", notes = "企业渠道用户排除产品集合查询", response = Json.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "current", value = "当前页", dataType = "int", required = true),
            @ApiImplicitParam(name = "size", value = "分页显示数量", dataType = "int", required = true),
    })
    @GetMapping("/select")
    public Json<Page<UserNotSpuVO>> select(@Validated(value = UserNotSpuQuery.Select.class) UserNotSpuQuery userNotSpuQuery, Integer current, Integer size) {
        //返回内容
        return new Json(ReturnCode.成功, userNotSpuService.pageEnhance(new Page(current, size), userNotSpuQuery));
    }


    /**
     * TODO 集合
     *
     * @param userNotSpuQuery 企业渠道用户排除产品
     * @return Json<List<UserNotSpuVO>>
     * @author 孙凯伦
     * @methodName selectList
     * @time 2023-07-07 04:36:59
     */
    @Methods(methodsName = "企业渠道用户排除产品集合查询", methods = "selectList")
    @ApiOperation(value = "企业渠道用户排除产品集合查询", httpMethod = "GET", notes = "企业渠道用户排除产品集合查询", response = Json.class)
    @GetMapping("/selectList")
    public Json<List<UserNotSpuVO>> selectList(@Validated(value = UserNotSpuQuery.SelectList.class) UserNotSpuQuery userNotSpuQuery) {
        //返回内容
        return new Json(ReturnCode.成功, userNotSpuService.listEnhance(userNotSpuQuery));
    }


    /**
     * TODO 单条
     *
     * @param userNotSpuQuery 企业渠道用户排除产品
     * @return Json<UserNotSpuVO>
     * @author 孙凯伦
     * @methodName selectOne
     * @time 2023-07-07 04:36:59
     */
    @Methods(methodsName = "企业渠道用户排除产品单条查询", methods = "selectOne")
    @ApiOperation(value = "企业渠道用户排除产品单条查询", httpMethod = "GET", notes = "企业渠道用户排除产品单条查询", response = Json.class)
    @GetMapping("/selectOne")
    public Json<UserNotSpuVO> selectOne(@Validated(value = UserNotSpuQuery.SelectOne.class) UserNotSpuQuery userNotSpuQuery) {
        //返回内容
        return new Json(ReturnCode.成功, userNotSpuService.getOneEnhance(userNotSpuQuery));
    }


    /**
     * TODO 总数
     *
     * @param userNotSpuQuery 企业渠道用户排除产品
     * @return Json<Integer>
     * @author 孙凯伦
     * @methodName count
     * @time 2023-07-07 04:36:59
     */
    @Methods(methodsName = "企业渠道用户排除产品总数查询", methods = "count")
    @ApiOperation(value = "企业渠道用户排除产品总数查询", httpMethod = "GET", notes = "企业渠道用户排除产品总数查询", response = Json.class)
    @GetMapping(value = "/count")
    public Json<Integer> count(@Validated(value = UserNotSpuQuery.Count.class) UserNotSpuQuery userNotSpuQuery) {
        //返回内容
        return new Json(ReturnCode.成功, userNotSpuService.countEnhance(userNotSpuQuery));
    }


    /**
     * TODO 新增
     *
     * @param userNotSpuBO 企业渠道用户排除产品
     * @return Json<String>
     * @author 孙凯伦
     * @methodName save
     * @time 2023-07-07 04:36:59
     */
    @PreventRepeat
    @Methods(methodsName = "企业渠道用户排除产品新增", methods = "save")
    @ApiOperation(value = "企业渠道用户排除产品新增", httpMethod = "POST", notes = "企业渠道用户排除产品新增", response = Json.class)
    @ApiOperationSupport(ignoreParameters = {"id", "createDateTime", "createName", "modifyDateTime", "modifyName", "isDelete", "version"})
    @PostMapping("/save")
    public Json<String> save(@Validated(value = UserNotSpuBO.Save.class) UserNotSpuBO userNotSpuBO, HttpServletRequest httpServletRequest) {
        //缓存取出用户
        Map<String, Object> u = RedisUtils.getToken(stringRedisTemplate, httpServletRequest);
        if (u != null) {
            userNotSpuBO.setCreateName(u.get("name") + "-" + u.get("id"));
        }
        //返回内容
        return new Json(ReturnCode.成功, userNotSpuService.saveEnhance(userNotSpuBO));
    }


    /**
     * TODO 修改
     *
     * @param userNotSpuBO 企业渠道用户排除产品
     * @return Json<Boolean>
     * @author 孙凯伦
     * @methodName update
     * @time 2023-07-07 04:36:59
     */
    @PreventRepeat
    @Methods(methodsName = "企业渠道用户排除产品修改", methods = "update")
    @ApiOperation(value = "企业渠道用户排除产品修改", httpMethod = "PUT", notes = "企业渠道用户排除产品修改", response = Json.class)
    @ApiOperationSupport(ignoreParameters = {"createDateTime", "createName", "modifyDateTime", "modifyName", "isDelete", "version"})
    @PutMapping("/update")
    public Json<Boolean> update(@Validated(value = UserNotSpuBO.Update.class) UserNotSpuBO userNotSpuBO, HttpServletRequest httpServletRequest) {
        //缓存取出用户
        Map<String, Object> u = RedisUtils.getToken(stringRedisTemplate, httpServletRequest);
        if (u != null) {
            userNotSpuBO.setModifyName(u.get("name") + "-" + u.get("id"));
        }
        //返回内容
        return new Json(ReturnCode.成功, userNotSpuService.updateEnhance(userNotSpuBO));
    }


    /**
     * TODO 删除
     *
     * @param userNotSpuBO 企业渠道用户排除产品
     * @return Json<Boolean>
     * @author 孙凯伦
     * @methodName remove
     * @time 2023-07-07 04:36:59
     */
    @Methods(methodsName = "企业渠道用户排除产品删除", methods = "remove")
    @ApiOperation(value = "企业渠道用户排除产品删除", httpMethod = "DELETE", notes = "企业渠道用户排除产品删除", response = Json.class)
    @ApiOperationSupport(includeParameters = {"id"})
    @DeleteMapping("/remove")
    public Json<Boolean> remove(@Validated(value = UserNotSpuBO.Remove.class) UserNotSpuBO userNotSpuBO) {
        return new Json(ReturnCode.成功, userNotSpuService.removeEnhance(userNotSpuBO));
    }
}