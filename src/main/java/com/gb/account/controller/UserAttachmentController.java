package com.gb.account.controller;

import com.alibaba.schedulerx.shade.org.apache.commons.collections.CollectionUtils;
import com.gb.account.entity.UserAttachment;
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
import com.gb.account.service.UserAttachmentService;
import com.gb.account.entity.query.UserAttachmentQuery;
import com.gb.account.entity.vo.UserAttachmentVO;
import com.gb.account.entity.bo.UserAttachmentBO;


/**
 * TODO 用户附件，Comment请求层
 * 代码生成器
 *
 * @author lijh
 * @className UserAttachmentController
 * @time 2022-04-14 10:04:04
 */
@Slf4j
@RequestRequired
@ApiSupport(author = "lijh")
@Setter(onMethod_ = {@Autowired})
@RestController
@RequestMapping("/user-attachment")
@Api(tags = "用户附件")
public class UserAttachmentController {


    /**
     * 用户附件
     */
    private UserAttachmentService userAttachmentService;
    /**
     * redis操作
     */
    private StringRedisTemplate stringRedisTemplate;


    /**
     * TODO 分页
     *
     * @param userAttachmentQuery 用户附件
     * @param current
     * @param size
     * @return Json<Page < UserAttachmentVO>>
     * @author lijh
     * @methodName select
     * @time 2022-04-14 10:04:04
     */
    @Methods(methodsName = "用户附件集合分页查询", methods = "select")
    @ApiOperation(value = "用户附件集合分页查询", httpMethod = "GET", notes = "用户附件集合查询", response = Json.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "current", value = "当前页", dataType = "int", required = true),
            @ApiImplicitParam(name = "size", value = "分页显示数量", dataType = "int", required = true),
    })
    @GetMapping("/select")
    public Json<Page<UserAttachmentVO>> select(@Validated(value = UserAttachmentQuery.Select.class) UserAttachmentQuery userAttachmentQuery, Integer current, Integer size) {
        //返回内容
        return new Json(ReturnCode.成功, userAttachmentService.pageEnhance(new Page(current, size), userAttachmentQuery));
    }


    /**
     * TODO 集合
     *
     * @param userAttachmentQuery 用户附件
     * @return Json<List < UserAttachmentVO>>
     * @author lijh
     * @methodName selectList
     * @time 2022-04-14 10:04:04
     */
    @Methods(methodsName = "用户附件集合查询", methods = "selectList")
    @ApiOperation(value = "用户附件集合查询", httpMethod = "GET", notes = "用户附件集合查询", response = Json.class)
    @GetMapping("/selectList")
    public Json<List<UserAttachmentVO>> selectList(@Validated(value = UserAttachmentQuery.SelectList.class) UserAttachmentQuery userAttachmentQuery) {
        //返回内容
        return new Json(ReturnCode.成功, userAttachmentService.listEnhance(userAttachmentQuery));
    }


    /**
     * TODO 单条
     *
     * @param userAttachmentQuery 用户附件
     * @return Json<UserAttachmentVO>
     * @author lijh
     * @methodName selectOne
     * @time 2022-04-14 10:04:04
     */
    @Methods(methodsName = "用户附件单条查询", methods = "selectOne")
    @ApiOperation(value = "用户附件单条查询", httpMethod = "GET", notes = "用户附件单条查询", response = Json.class)
    @GetMapping("/selectOne")
    public Json<UserAttachmentVO> selectOne(@Validated(value = UserAttachmentQuery.SelectOne.class) UserAttachmentQuery userAttachmentQuery) {
        //返回内容
        return new Json(ReturnCode.成功, userAttachmentService.getOneEnhance(userAttachmentQuery));
    }


    /**
     * TODO 总数
     *
     * @param userAttachmentQuery 用户附件
     * @return Json<Integer>
     * @author lijh
     * @methodName count
     * @time 2022-04-14 10:04:04
     */
    @Methods(methodsName = "用户附件总数查询", methods = "count")
    @ApiOperation(value = "用户附件总数查询", httpMethod = "GET", notes = "用户附件总数查询", response = Json.class)
    @GetMapping(value = "/count")
    public Json<Integer> count(@Validated(value = UserAttachmentQuery.Count.class) UserAttachmentQuery userAttachmentQuery) {
        //返回内容
        return new Json(ReturnCode.成功, userAttachmentService.countEnhance(userAttachmentQuery));
    }


    /**
     * TODO 新增
     *
     * @param userAttachmentBO 用户附件
     * @return Json<String>
     * @author lijh
     * @methodName save
     * @time 2022-04-14 10:04:04
     */
    @PreventRepeat
    @Methods(methodsName = "用户附件新增", methods = "save")
    @ApiOperation(value = "用户附件新增", httpMethod = "POST", notes = "用户附件新增", response = Json.class)
    @ApiOperationSupport(ignoreParameters = {"id", "createDateTime", "createName", "modifyDateTime", "modifyName", "isDelete", "version"})
    @PostMapping("/save")
    public Json<String> save(@Validated(value = UserAttachmentBO.Save.class) UserAttachmentBO userAttachmentBO, HttpServletRequest httpServletRequest) {
        //缓存取出用户
        Map<String, Object> u = RedisUtils.getToken(stringRedisTemplate, httpServletRequest);
        if (u != null) {
            userAttachmentBO.setCreateName(u.get("name") + "-" + u.get("id"));
        }
        //返回内容
        return new Json(ReturnCode.成功, userAttachmentService.saveEnhance(userAttachmentBO));
    }


    /**
     * TODO 修改
     *
     * @param userAttachmentBO 用户附件
     * @return Json<Boolean>
     * @author lijh
     * @methodName update
     * @time 2022-04-14 10:04:04
     */
    @PreventRepeat
    @Methods(methodsName = "用户附件修改", methods = "update")
    @ApiOperation(value = "用户附件修改", httpMethod = "PUT", notes = "用户附件修改", response = Json.class)
    @ApiOperationSupport(ignoreParameters = {"createDateTime", "createName", "modifyDateTime", "modifyName", "isDelete", "version"})
    @PutMapping("/update")
    public Json<Boolean> update(@Validated(value = UserAttachmentBO.Update.class) UserAttachmentBO userAttachmentBO, HttpServletRequest httpServletRequest) {
        //缓存取出用户
        Map<String, Object> u = RedisUtils.getToken(stringRedisTemplate, httpServletRequest);
        if (u != null) {
            userAttachmentBO.setModifyName(u.get("name") + "-" + u.get("id"));
        }
        //返回内容
        return new Json(ReturnCode.成功, userAttachmentService.updateEnhance(userAttachmentBO));
    }


    /**
     * TODO 删除
     *
     * @param userAttachmentBO 用户附件
     * @return Json<Boolean>
     * @author lijh
     * @methodName remove
     * @time 2022-04-14 10:04:04
     */
    @Methods(methodsName = "用户附件删除", methods = "remove")
    @ApiOperation(value = "用户附件删除", httpMethod = "DELETE", notes = "用户附件删除", response = Json.class)
    @ApiOperationSupport(includeParameters = {"id"})
    @DeleteMapping("/remove")
    public Json<Boolean> remove(@Validated(value = UserAttachmentBO.Remove.class) UserAttachmentBO userAttachmentBO) {
        return new Json(ReturnCode.成功, userAttachmentService.removeEnhance(userAttachmentBO));
    }

    @PreventRepeat
    @Methods(methodsName = "用户附件批量新增", methods = "save")
    @ApiOperation(value = "用户附件批量新增", httpMethod = "POST", notes = "用户附件批量新增", response = Json.class)
    @PostMapping("/saveEnhanceBatch")
    public Json<String> saveEnhanceBatch(@RequestBody List<UserAttachment> userAttachmentList, HttpServletRequest httpServletRequest) {
        if (CollectionUtils.isEmpty(userAttachmentList)) {
            return new Json(ReturnCode.用户端错误, "用户附件信息为空");
        }
        //缓存取出用户
        Map<String, Object> u = RedisUtils.getToken(stringRedisTemplate, httpServletRequest);
        if (u != null) {
            userAttachmentList.forEach(userAttachment -> userAttachment.setCreateName(u.get("name") + "-" + u.get("id")));
        }
        //返回内容
        return new Json(ReturnCode.成功, userAttachmentService.saveBatch(userAttachmentList));
    }
}