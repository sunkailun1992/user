package com.gb.platform.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gb.platform.entity.bo.ExternalPlatformBO;
import com.gb.platform.entity.query.ExternalPlatformQuery;
import com.gb.platform.entity.vo.ExternalPlatformVO;
import com.gb.platform.service.ExternalPlatformService;
import com.gb.utils.Json;
import com.gb.utils.RedisUtils;
import com.gb.utils.annotations.Methods;
import com.gb.utils.annotations.PreventRepeat;
import com.gb.utils.annotations.RequestRequired;
import com.gb.utils.enumeration.ReturnCode;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;


/**
 * TODO 外部平台，Comment请求层
 * 代码生成器
 *
 * @author sunx
 * @className ExternalPlatformController
 * @time 2022-12-16 03:10:07
 */
@Slf4j
@RequestRequired
@ApiSupport(author = "sunx")
@Setter(onMethod_ = {@Autowired})
@RestController
@RequestMapping("/external-platform")
@Api(tags = "外部平台")
public class ExternalPlatformController {


    /**
     * 外部平台
     */
    private ExternalPlatformService externalPlatformService;
    /**
     * redis操作
     */
    private StringRedisTemplate stringRedisTemplate;


    /**
     * TODO 分页
     *
     * @param externalPlatformQuery 外部平台
     * @param current
     * @param size
     * @return Json<Page<ExternalPlatformVO>>
     * @author sunx
     * @methodName select
     * @time 2022-12-16 03:10:07
     */
    @Methods(methodsName = "外部平台集合分页查询", methods = "select")
    @ApiOperation(value = "外部平台集合分页查询", httpMethod = "GET", notes = "外部平台集合查询", response = Json.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "current", value = "当前页", dataType = "int", required = true),
            @ApiImplicitParam(name = "size", value = "分页显示数量", dataType = "int", required = true),
    })
    @GetMapping("/select")
    public Json<Page<ExternalPlatformVO>> select(@Validated(value = ExternalPlatformQuery.Select.class) ExternalPlatformQuery externalPlatformQuery, Integer current, Integer size) {
        //返回内容
        return new Json(ReturnCode.成功, externalPlatformService.pageEnhance(new Page(current, size), externalPlatformQuery));
    }


    /**
     * TODO 集合
     *
     * @param externalPlatformQuery 外部平台
     * @return Json<List<ExternalPlatformVO>>
     * @author sunx
     * @methodName selectList
     * @time 2022-12-16 03:10:07
     */
    @Methods(methodsName = "外部平台集合查询", methods = "selectList")
    @ApiOperation(value = "外部平台集合查询", httpMethod = "GET", notes = "外部平台集合查询", response = Json.class)
    @GetMapping("/selectList")
    public Json<Object> selectList(@Validated(value = ExternalPlatformQuery.SelectList.class) ExternalPlatformQuery externalPlatformQuery) {
        //返回内容
        return new Json(ReturnCode.成功, externalPlatformService.listEnhance(externalPlatformQuery));
    }


    /**
     * TODO 单条
     *
     * @param externalPlatformQuery 外部平台
     * @return Json<ExternalPlatformVO>
     * @author sunx
     * @methodName selectOne
     * @time 2022-12-16 03:10:07
     */
    @Methods(methodsName = "外部平台单条查询", methods = "selectOne")
    @ApiOperation(value = "外部平台单条查询", httpMethod = "GET", notes = "外部平台单条查询", response = Json.class)
    @GetMapping("/selectOne")
    public Json<ExternalPlatformVO> selectOne(@Validated(value = ExternalPlatformQuery.SelectOne.class) ExternalPlatformQuery externalPlatformQuery) {
        //返回内容
        return new Json(ReturnCode.成功, externalPlatformService.getOneEnhance(externalPlatformQuery));
    }


    /**
     * TODO 总数
     *
     * @param externalPlatformQuery 外部平台
     * @return Json<Integer>
     * @author sunx
     * @methodName count
     * @time 2022-12-16 03:10:07
     */
    @Methods(methodsName = "外部平台总数查询", methods = "count")
    @ApiOperation(value = "外部平台总数查询", httpMethod = "GET", notes = "外部平台总数查询", response = Json.class)
    @GetMapping(value = "/count")
    public Json<Integer> count(@Validated(value = ExternalPlatformQuery.Count.class) ExternalPlatformQuery externalPlatformQuery) {
        //返回内容
        return new Json(ReturnCode.成功, externalPlatformService.countEnhance(externalPlatformQuery));
    }


    /**
     * TODO 新增
     *
     * @param externalPlatformBO 外部平台
     * @return Json<String>
     * @author sunx
     * @methodName save
     * @time 2022-12-16 03:10:07
     */
    @PreventRepeat
    @Methods(methodsName = "外部平台新增", methods = "save")
    @ApiOperation(value = "外部平台新增", httpMethod = "POST", notes = "外部平台新增", response = Json.class)
    @ApiOperationSupport(ignoreParameters = {"id", "createDateTime", "createName", "modifyDateTime", "modifyName", "isDelete", "version"})
    @PostMapping("/save")
    public Json<String> save(@Validated(value = ExternalPlatformBO.Save.class) ExternalPlatformBO externalPlatformBO, HttpServletRequest httpServletRequest) {
        //缓存取出用户
        Map<String, Object> u = RedisUtils.getToken(stringRedisTemplate, httpServletRequest);
        if (u != null) {
            externalPlatformBO.setCreateName(u.get("name") + "-" + u.get("id"));
        }
        //返回内容
        return new Json(ReturnCode.成功, externalPlatformService.saveEnhance(externalPlatformBO));
    }


    /**
     * TODO 修改
     *
     * @param externalPlatformBO 外部平台
     * @return Json<Boolean>
     * @author sunx
     * @methodName update
     * @time 2022-12-16 03:10:07
     */
    @PreventRepeat
    @Methods(methodsName = "外部平台修改", methods = "update")
    @ApiOperation(value = "外部平台修改", httpMethod = "PUT", notes = "外部平台修改", response = Json.class)
    @ApiOperationSupport(ignoreParameters = {"createDateTime", "createName", "modifyDateTime", "modifyName", "isDelete", "version"})
    @PutMapping("/update")
    public Json<Boolean> update(@Validated(value = ExternalPlatformBO.Update.class) ExternalPlatformBO externalPlatformBO, HttpServletRequest httpServletRequest) {
        //缓存取出用户
        Map<String, Object> u = RedisUtils.getToken(stringRedisTemplate, httpServletRequest);
        if (u != null) {
            externalPlatformBO.setModifyName(u.get("name") + "-" + u.get("id"));
        }
        //返回内容
        return new Json(ReturnCode.成功, externalPlatformService.updateEnhance(externalPlatformBO));
    }


    /**
     * TODO 删除
     *
     * @param externalPlatformBO 外部平台
     * @return Json<Boolean>
     * @author sunx
     * @methodName remove
     * @time 2022-12-16 03:10:07
     */
    @Methods(methodsName = "外部平台删除", methods = "remove")
    @ApiOperation(value = "外部平台删除", httpMethod = "DELETE", notes = "外部平台删除", response = Json.class)
    @ApiOperationSupport(includeParameters = {"id"})
    @DeleteMapping("/remove")
    public Json<Boolean> remove(@Validated(value = ExternalPlatformBO.Remove.class) ExternalPlatformBO externalPlatformBO) {
        return new Json(ReturnCode.成功, externalPlatformService.removeEnhance(externalPlatformBO));
    }
}