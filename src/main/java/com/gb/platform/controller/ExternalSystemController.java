package com.gb.platform.controller;

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
import com.gb.platform.service.ExternalSystemService;
import com.gb.platform.entity.query.ExternalSystemQuery;
import com.gb.platform.entity.vo.ExternalSystemVO;
import com.gb.platform.entity.bo.ExternalSystemBO;


/**
 * TODO 外部系统，Comment请求层
 * 代码生成器
 *
 * @author sunx
 * @className ExternalSystemController
 * @time 2022-12-16 03:10:08
 */
@Slf4j
@RequestRequired
@ApiSupport(author = "sunx")
@Setter(onMethod_ = {@Autowired})
@RestController
@RequestMapping("/external-system")
@Api(tags = "外部系统")
public class ExternalSystemController {


    /**
     * 外部系统
     */
    private ExternalSystemService externalSystemService;
    /**
     * redis操作
     */
    private StringRedisTemplate stringRedisTemplate;


    /**
     * TODO 分页
     *
     * @param externalSystemQuery 外部系统
     * @param current
     * @param size
     * @return Json<Page<ExternalSystemVO>>
     * @author sunx
     * @methodName select
     * @time 2022-12-16 03:10:08
     */
    @Methods(methodsName = "外部系统集合分页查询", methods = "select")
    @ApiOperation(value = "外部系统集合分页查询", httpMethod = "GET", notes = "外部系统集合查询", response = Json.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "current", value = "当前页", dataType = "int", required = true),
            @ApiImplicitParam(name = "size", value = "分页显示数量", dataType = "int", required = true),
    })
    @GetMapping("/select")
    public Json<Page<ExternalSystemVO>> select(@Validated(value = ExternalSystemQuery.Select.class) ExternalSystemQuery externalSystemQuery, Integer current, Integer size) {
        //返回内容
        return new Json(ReturnCode.成功, externalSystemService.pageEnhance(new Page(current, size), externalSystemQuery));
    }


    /**
     * TODO 集合
     *
     * @param externalSystemQuery 外部系统
     * @return Json<List<ExternalSystemVO>>
     * @author sunx
     * @methodName selectList
     * @time 2022-12-16 03:10:08
     */
    @Methods(methodsName = "外部系统集合查询", methods = "selectList")
    @ApiOperation(value = "外部系统集合查询", httpMethod = "GET", notes = "外部系统集合查询", response = Json.class)
    @GetMapping("/selectList")
    public Json<List<ExternalSystemVO>> selectList(@Validated(value = ExternalSystemQuery.SelectList.class) ExternalSystemQuery externalSystemQuery) {
        //返回内容
        return new Json(ReturnCode.成功, externalSystemService.listEnhance(externalSystemQuery));
    }


    /**
     * TODO 单条
     *
     * @param externalSystemQuery 外部系统
     * @return Json<ExternalSystemVO>
     * @author sunx
     * @methodName selectOne
     * @time 2022-12-16 03:10:08
     */
    @Methods(methodsName = "外部系统单条查询", methods = "selectOne")
    @ApiOperation(value = "外部系统单条查询", httpMethod = "GET", notes = "外部系统单条查询", response = Json.class)
    @GetMapping("/selectOne")
    public Json<ExternalSystemVO> selectOne(@Validated(value = ExternalSystemQuery.SelectOne.class) ExternalSystemQuery externalSystemQuery) {
        //返回内容
        return new Json(ReturnCode.成功, externalSystemService.getOneEnhance(externalSystemQuery));
    }


    /**
     * TODO 总数
     *
     * @param externalSystemQuery 外部系统
     * @return Json<Integer>
     * @author sunx
     * @methodName count
     * @time 2022-12-16 03:10:08
     */
    @Methods(methodsName = "外部系统总数查询", methods = "count")
    @ApiOperation(value = "外部系统总数查询", httpMethod = "GET", notes = "外部系统总数查询", response = Json.class)
    @GetMapping(value = "/count")
    public Json<Integer> count(@Validated(value = ExternalSystemQuery.Count.class) ExternalSystemQuery externalSystemQuery) {
        //返回内容
        return new Json(ReturnCode.成功, externalSystemService.countEnhance(externalSystemQuery));
    }


    /**
     * TODO 新增
     *
     * @param externalSystemBO 外部系统
     * @return Json<String>
     * @author sunx
     * @methodName save
     * @time 2022-12-16 03:10:08
     */
    @PreventRepeat
    @Methods(methodsName = "外部系统新增", methods = "save")
    @ApiOperation(value = "外部系统新增", httpMethod = "POST", notes = "外部系统新增", response = Json.class)
    @ApiOperationSupport(ignoreParameters = {"id", "createDateTime", "createName", "modifyDateTime", "modifyName", "isDelete", "version"})
    @PostMapping("/save")
    public Json<String> save(@Validated(value = ExternalSystemBO.Save.class) ExternalSystemBO externalSystemBO, HttpServletRequest httpServletRequest) {
        //缓存取出用户
        Map<String, Object> u = RedisUtils.getToken(stringRedisTemplate, httpServletRequest);
        if (u != null) {
            externalSystemBO.setCreateName(u.get("name") + "-" + u.get("id"));
        }
        //返回内容
        return new Json(ReturnCode.成功, externalSystemService.saveEnhance(externalSystemBO));
    }


    /**
     * TODO 修改
     *
     * @param externalSystemBO 外部系统
     * @return Json<Boolean>
     * @author sunx
     * @methodName update
     * @time 2022-12-16 03:10:08
     */
    @PreventRepeat
    @Methods(methodsName = "外部系统修改", methods = "update")
    @ApiOperation(value = "外部系统修改", httpMethod = "PUT", notes = "外部系统修改", response = Json.class)
    @ApiOperationSupport(ignoreParameters = {"createDateTime", "createName", "modifyDateTime", "modifyName", "isDelete", "version"})
    @PutMapping("/update")
    public Json<Boolean> update(@Validated(value = ExternalSystemBO.Update.class) ExternalSystemBO externalSystemBO, HttpServletRequest httpServletRequest) {
        //缓存取出用户
        Map<String, Object> u = RedisUtils.getToken(stringRedisTemplate, httpServletRequest);
        if (u != null) {
            externalSystemBO.setModifyName(u.get("name") + "-" + u.get("id"));
        }
        //返回内容
        return new Json(ReturnCode.成功, externalSystemService.updateEnhance(externalSystemBO));
    }


    /**
     * TODO 删除
     *
     * @param externalSystemBO 外部系统
     * @return Json<Boolean>
     * @author sunx
     * @methodName remove
     * @time 2022-12-16 03:10:08
     */
    @Methods(methodsName = "外部系统删除", methods = "remove")
    @ApiOperation(value = "外部系统删除", httpMethod = "DELETE", notes = "外部系统删除", response = Json.class)
    @ApiOperationSupport(includeParameters = {"id"})
    @DeleteMapping("/remove")
    public Json<Boolean> remove(@Validated(value = ExternalSystemBO.Remove.class) ExternalSystemBO externalSystemBO) {
        return new Json(ReturnCode.成功, externalSystemService.removeEnhance(externalSystemBO));
    }
}