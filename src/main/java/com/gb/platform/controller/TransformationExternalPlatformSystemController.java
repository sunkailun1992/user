package com.gb.platform.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gb.platform.entity.bo.TransformationExternalPlatformSystemBO;
import com.gb.platform.entity.query.TransformationExternalPlatformSystemQuery;
import com.gb.platform.entity.vo.TransformationExternalPlatformSystemVO;
import com.gb.platform.service.TransformationExternalPlatformSystemService;
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
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.Objects;


/**
 * TODO 转化外部系统平台，Comment请求层
 * 代码生成器
 *
 * @author sunx
 * @className TransformationExternalPlatformSystemController
 * @time 2022-12-16 03:10:08
 */
@Slf4j
@RequestRequired
@ApiSupport(author = "sunx")
@Setter(onMethod_ = {@Autowired})
@RestController
@RequestMapping("/transformation-external-platform-system")
@Api(tags = "转化外部系统平台")
public class TransformationExternalPlatformSystemController {


    /**
     * 转化外部系统平台
     */
    private TransformationExternalPlatformSystemService transformationExternalPlatformSystemService;
    /**
     * redis操作
     */
    private StringRedisTemplate stringRedisTemplate;


    /**
     * TODO 分页
     *
     * @param transformationExternalPlatformSystemQuery 转化外部系统平台
     * @param current
     * @param size
     * @return Json<Page<TransformationExternalPlatformSystemVO>>
     * @author sunx
     * @methodName select
     * @time 2022-12-16 03:10:08
     */
    @Methods(methodsName = "转化外部系统平台集合分页查询", methods = "select")
    @ApiOperation(value = "转化外部系统平台集合分页查询", httpMethod = "GET", notes = "转化外部系统平台集合查询", response = Json.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "current", value = "当前页", dataType = "int", required = true),
            @ApiImplicitParam(name = "size", value = "分页显示数量", dataType = "int", required = true),
    })
    @GetMapping("/select")
    public Json<Page<TransformationExternalPlatformSystemVO>> select(@Validated(value = TransformationExternalPlatformSystemQuery.Select.class) TransformationExternalPlatformSystemQuery transformationExternalPlatformSystemQuery, Integer current, Integer size) {
        //返回内容
        if(Objects.isNull(current) || Objects.isNull(size)) {
            return new Json(ReturnCode.请求必填参数为空, "缺少分页必传参数！");

        }
        return new Json(ReturnCode.成功, transformationExternalPlatformSystemService.pageEnhance(new Page(current, size), transformationExternalPlatformSystemQuery));
    }


    /**
     * TODO 集合
     *
     * @param transformationExternalPlatformSystemQuery 转化外部系统平台
     * @return Json<List<TransformationExternalPlatformSystemVO>>
     * @author sunx
     * @methodName selectList
     * @time 2022-12-16 03:10:08
     */
    @Methods(methodsName = "转化外部系统平台集合查询", methods = "selectList")
    @ApiOperation(value = "转化外部系统平台集合查询", httpMethod = "GET", notes = "转化外部系统平台集合查询", response = Json.class)
    @GetMapping("/selectList")
    public Json<List<TransformationExternalPlatformSystemVO>> selectList(@Validated(value = TransformationExternalPlatformSystemQuery.SelectList.class) TransformationExternalPlatformSystemQuery transformationExternalPlatformSystemQuery) {
        //返回内容
        return new Json(ReturnCode.成功, transformationExternalPlatformSystemService.listEnhance(transformationExternalPlatformSystemQuery));
    }


    /**
     * TODO 单条
     *
     * @param transformationExternalPlatformSystemQuery 转化外部系统平台
     * @return Json<TransformationExternalPlatformSystemVO>
     * @author sunx
     * @methodName selectOne
     * @time 2022-12-16 03:10:08
     */
    @Methods(methodsName = "转化外部系统平台单条查询", methods = "selectOne")
    @ApiOperation(value = "转化外部系统平台单条查询", httpMethod = "GET", notes = "转化外部系统平台单条查询", response = Json.class)
    @GetMapping("/selectOne")
    public Json<TransformationExternalPlatformSystemVO> selectOne(@Validated(value = TransformationExternalPlatformSystemQuery.SelectOne.class) TransformationExternalPlatformSystemQuery transformationExternalPlatformSystemQuery) {
        //返回内容
        return new Json(ReturnCode.成功, transformationExternalPlatformSystemService.getOneEnhance(transformationExternalPlatformSystemQuery));
    }


    /**
     * TODO 总数
     *
     * @param transformationExternalPlatformSystemQuery 转化外部系统平台
     * @return Json<Integer>
     * @author sunx
     * @methodName count
     * @time 2022-12-16 03:10:08
     */
    @Methods(methodsName = "转化外部系统平台总数查询", methods = "count")
    @ApiOperation(value = "转化外部系统平台总数查询", httpMethod = "GET", notes = "转化外部系统平台总数查询", response = Json.class)
    @GetMapping(value = "/count")
    public Json<Integer> count(@Validated(value = TransformationExternalPlatformSystemQuery.Count.class) TransformationExternalPlatformSystemQuery transformationExternalPlatformSystemQuery) {
        //返回内容
        return new Json(ReturnCode.成功, transformationExternalPlatformSystemService.countEnhance(transformationExternalPlatformSystemQuery));
    }


    /**
     * TODO 新增
     *
     * @param transformationExternalPlatformSystemBO 转化外部系统平台
     * @return Json<String>
     * @author sunx
     * @methodName save
     * @time 2022-12-16 03:10:08
     */
    @PreventRepeat
    @Methods(methodsName = "转化外部系统平台新增", methods = "save")
    @ApiOperation(value = "转化外部系统平台新增", httpMethod = "POST", notes = "转化外部系统平台新增", response = Json.class)
    @ApiOperationSupport(ignoreParameters = {"id", "createDateTime", "createName", "modifyDateTime", "modifyName", "isDelete", "version"})
    @PostMapping("/save")
    public Json<String> save(@Validated(value = TransformationExternalPlatformSystemBO.Save.class) TransformationExternalPlatformSystemBO transformationExternalPlatformSystemBO, HttpServletRequest httpServletRequest) {
        if(StringUtils.isBlank(transformationExternalPlatformSystemBO.getProvinceCode()) && StringUtils.isBlank(transformationExternalPlatformSystemBO.getCityCode()) && StringUtils.isBlank(transformationExternalPlatformSystemBO.getAreaCode())) {
            return new Json(ReturnCode.请求必填参数为空, "地区不能为空！");
        }
        //缓存取出用户
        Map<String, Object> u = RedisUtils.getToken(stringRedisTemplate, httpServletRequest);
        if (u != null) {
            transformationExternalPlatformSystemBO.setCreateName(u.get("name") + "-" + u.get("id"));
        }
        //返回内容
        return new Json(ReturnCode.成功, transformationExternalPlatformSystemService.saveEnhance(transformationExternalPlatformSystemBO));
    }


    /**
     * TODO 修改
     *
     * @param transformationExternalPlatformSystemBO 转化外部系统平台
     * @return Json<Boolean>
     * @author sunx
     * @methodName update
     * @time 2022-12-16 03:10:08
     */
    @PreventRepeat
    @Methods(methodsName = "转化外部系统平台修改", methods = "update")
    @ApiOperation(value = "转化外部系统平台修改", httpMethod = "PUT", notes = "转化外部系统平台修改", response = Json.class)
    @ApiOperationSupport(ignoreParameters = {"createDateTime", "createName", "modifyDateTime", "modifyName", "isDelete", "version"})
    @PutMapping("/update")
    public Json<Boolean> update(@Validated(value = TransformationExternalPlatformSystemBO.Update.class) TransformationExternalPlatformSystemBO transformationExternalPlatformSystemBO, HttpServletRequest httpServletRequest) {
        //缓存取出用户
        Map<String, Object> u = RedisUtils.getToken(stringRedisTemplate, httpServletRequest);
        if (u != null) {
            transformationExternalPlatformSystemBO.setModifyName(u.get("name") + "-" + u.get("id"));
        }
        //返回内容
        return new Json(ReturnCode.成功, transformationExternalPlatformSystemService.updateEnhance(transformationExternalPlatformSystemBO));
    }


    /**
     * TODO 删除
     *
     * @param transformationExternalPlatformSystemBO 转化外部系统平台
     * @return Json<Boolean>
     * @author sunx
     * @methodName remove
     * @time 2022-12-16 03:10:08
     */
    @Methods(methodsName = "转化外部系统平台删除", methods = "remove")
    @ApiOperation(value = "转化外部系统平台删除", httpMethod = "DELETE", notes = "转化外部系统平台删除", response = Json.class)
    @ApiOperationSupport(includeParameters = {"id"})
    @DeleteMapping("/remove")
    public Json<Boolean> remove(@Validated(value = TransformationExternalPlatformSystemBO.Remove.class) TransformationExternalPlatformSystemBO transformationExternalPlatformSystemBO) {
        return new Json(ReturnCode.成功, transformationExternalPlatformSystemService.removeEnhance(transformationExternalPlatformSystemBO));
    }
}