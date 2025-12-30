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
import com.gb.account.service.UserTypeValueRegionService;
import com.gb.account.entity.query.UserTypeValueRegionQuery;
import com.gb.account.entity.vo.UserTypeValueRegionVO;
import com.gb.account.entity.bo.UserTypeValueRegionBO;


/**
 * TODO 用户类型值地区，Comment请求层
 * 代码生成器
 *
 * @author lijh
 * @className UserTypeValueRegionController
 * @time 2022-07-12 11:45:19
 */
@Slf4j
@RequestRequired
@ApiSupport(author = "lijh")
@Setter(onMethod_ = {@Autowired})
@RestController
@RequestMapping("/user-type-value-region")
@Api(tags = "用户类型值地区")
public class UserTypeValueRegionController {


    /**
     * 用户类型值地区
     */
    private UserTypeValueRegionService userTypeValueRegionService;
    /**
     * redis操作
     */
    private StringRedisTemplate stringRedisTemplate;


    /**
     * TODO 分页
     *
     * @param userTypeValueRegionQuery 用户类型值地区
     * @param current
     * @param size
     * @return Json<Page<UserTypeValueRegionVO>>
     * @author lijh
     * @methodName select
     * @time 2022-07-12 11:45:19
     */
    @Methods(methodsName = "用户类型值地区集合分页查询", methods = "select")
    @ApiOperation(value = "用户类型值地区集合分页查询", httpMethod = "GET", notes = "用户类型值地区集合查询", response = Json.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "current", value = "当前页", dataType = "int", required = true),
            @ApiImplicitParam(name = "size", value = "分页显示数量", dataType = "int", required = true),
    })
    @GetMapping("/select")
    public Json<Page<UserTypeValueRegionVO>> select(@Validated(value = UserTypeValueRegionQuery.Select.class) UserTypeValueRegionQuery userTypeValueRegionQuery, Integer current, Integer size) {
        //返回内容
        return new Json(ReturnCode.成功, userTypeValueRegionService.pageEnhance(new Page(current, size), userTypeValueRegionQuery));
    }


    /**
     * TODO 集合
     *
     * @param userTypeValueRegionQuery 用户类型值地区
     * @return Json<List<UserTypeValueRegionVO>>
     * @author lijh
     * @methodName selectList
     * @time 2022-07-12 11:45:19
     */
    @Methods(methodsName = "用户类型值地区集合查询", methods = "selectList")
    @ApiOperation(value = "用户类型值地区集合查询", httpMethod = "GET", notes = "用户类型值地区集合查询", response = Json.class)
    @GetMapping("/selectList")
    public Json<List<UserTypeValueRegionVO>> selectList(@Validated(value = UserTypeValueRegionQuery.SelectList.class) UserTypeValueRegionQuery userTypeValueRegionQuery) {
        //返回内容
        return new Json(ReturnCode.成功, userTypeValueRegionService.listEnhance(userTypeValueRegionQuery));
    }


    /**
     * TODO 单条
     *
     * @param userTypeValueRegionQuery 用户类型值地区
     * @return Json<UserTypeValueRegionVO>
     * @author lijh
     * @methodName selectOne
     * @time 2022-07-12 11:45:19
     */
    @Methods(methodsName = "用户类型值地区单条查询", methods = "selectOne")
    @ApiOperation(value = "用户类型值地区单条查询", httpMethod = "GET", notes = "用户类型值地区单条查询", response = Json.class)
    @GetMapping("/selectOne")
    public Json<UserTypeValueRegionVO> selectOne(@Validated(value = UserTypeValueRegionQuery.SelectOne.class) UserTypeValueRegionQuery userTypeValueRegionQuery) {
        //返回内容
        return new Json(ReturnCode.成功, userTypeValueRegionService.getOneEnhance(userTypeValueRegionQuery));
    }


    /**
     * TODO 总数
     *
     * @param userTypeValueRegionQuery 用户类型值地区
     * @return Json<Integer>
     * @author lijh
     * @methodName count
     * @time 2022-07-12 11:45:19
     */
    @Methods(methodsName = "用户类型值地区总数查询", methods = "count")
    @ApiOperation(value = "用户类型值地区总数查询", httpMethod = "GET", notes = "用户类型值地区总数查询", response = Json.class)
    @GetMapping(value = "/count")
    public Json<Integer> count(@Validated(value = UserTypeValueRegionQuery.Count.class) UserTypeValueRegionQuery userTypeValueRegionQuery) {
        //返回内容
        return new Json(ReturnCode.成功, userTypeValueRegionService.countEnhance(userTypeValueRegionQuery));
    }


    /**
     * TODO 新增
     *
     * @param userTypeValueRegionBO 用户类型值地区
     * @return Json<String>
     * @author lijh
     * @methodName save
     * @time 2022-07-12 11:45:19
     */
    @PreventRepeat
    @Methods(methodsName = "用户类型值地区新增", methods = "save")
    @ApiOperation(value = "用户类型值地区新增", httpMethod = "POST", notes = "用户类型值地区新增", response = Json.class)
    @ApiOperationSupport(ignoreParameters = {"id", "createDateTime", "createName", "modifyDateTime", "modifyName", "isDelete", "version"})
    @PostMapping("/save")
    public Json<String> save(@Validated(value = UserTypeValueRegionBO.Save.class) UserTypeValueRegionBO userTypeValueRegionBO, HttpServletRequest httpServletRequest) {
        //缓存取出用户
        Map<String, Object> u = RedisUtils.getToken(stringRedisTemplate, httpServletRequest);
        if (u != null) {
            userTypeValueRegionBO.setCreateName(u.get("name") + "-" + u.get("id"));
        }
        //返回内容
        return new Json(ReturnCode.成功, userTypeValueRegionService.saveEnhance(userTypeValueRegionBO));
    }


    /**
     * TODO 修改
     *
     * @param userTypeValueRegionBO 用户类型值地区
     * @return Json<Boolean>
     * @author lijh
     * @methodName update
     * @time 2022-07-12 11:45:19
     */
    @PreventRepeat
    @Methods(methodsName = "用户类型值地区修改", methods = "update")
    @ApiOperation(value = "用户类型值地区修改", httpMethod = "PUT", notes = "用户类型值地区修改", response = Json.class)
    @ApiOperationSupport(ignoreParameters = {"createDateTime", "createName", "modifyDateTime", "modifyName", "isDelete", "version"})
    @PutMapping("/update")
    public Json<Boolean> update(@Validated(value = UserTypeValueRegionBO.Update.class) UserTypeValueRegionBO userTypeValueRegionBO, HttpServletRequest httpServletRequest) {
        //缓存取出用户
        Map<String, Object> u = RedisUtils.getToken(stringRedisTemplate, httpServletRequest);
        if (u != null) {
            userTypeValueRegionBO.setModifyName(u.get("name") + "-" + u.get("id"));
        }
        //返回内容
        return new Json(ReturnCode.成功, userTypeValueRegionService.updateEnhance(userTypeValueRegionBO));
    }


    /**
     * TODO 删除
     *
     * @param userTypeValueRegionBO 用户类型值地区
     * @return Json<Boolean>
     * @author lijh
     * @methodName remove
     * @time 2022-07-12 11:45:19
     */
    @Methods(methodsName = "用户类型值地区删除", methods = "remove")
    @ApiOperation(value = "用户类型值地区删除", httpMethod = "DELETE", notes = "用户类型值地区删除", response = Json.class)
    @ApiOperationSupport(includeParameters = {"id"})
    @DeleteMapping("/remove")
    public Json<Boolean> remove(@Validated(value = UserTypeValueRegionBO.Remove.class) UserTypeValueRegionBO userTypeValueRegionBO) {
        return new Json(ReturnCode.成功, userTypeValueRegionService.removeEnhance(userTypeValueRegionBO));
    }
}