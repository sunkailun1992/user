package com.gb.user.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gb.user.entity.bo.InstitutionsBO;
import com.gb.user.entity.query.InstitutionsQuery;
import com.gb.user.entity.vo.InstitutionsVO;
import com.gb.user.service.InstitutionsService;
import com.gb.utils.Json;
import com.gb.utils.RedisUtils;
import com.gb.utils.annotations.Methods;
import com.gb.utils.annotations.PreventRepeat;
import com.gb.utils.annotations.RequestRequired;
import com.gb.utils.constants.UniversalConstant;
import com.gb.utils.enumeration.ReturnCode;
import com.gb.utils.exception.ParameterNullException;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;
import java.util.Objects;


/**
 * TODO 机构，Comment请求层
 * 代码生成器
 *
 * @author sunxin
 * @className InstitutionsController
 * @time 2022-07-04 10:48:36
 */
@Slf4j
@RequestRequired
@ApiSupport(author = "sunxin")
@Setter(onMethod_ = {@Autowired})
@RestController
@RequestMapping("/institutions")
@Api(tags = "机构")
public class InstitutionsController {


    /**
     * 机构
     */
    private InstitutionsService institutionsService;
    /**
     * redis操作
     */
    private StringRedisTemplate stringRedisTemplate;


    /**
     * TODO 分页
     *
     * @param institutionsQuery 机构
     * @param current
     * @param size
     * @return Json<Page<InstitutionsVO>>
     * @author sunxin
     * @methodName select
     * @time 2022-07-04 10:48:36
     */
    @Methods(methodsName = "机构集合分页查询", methods = "select")
    @ApiOperation(value = "机构集合分页查询", httpMethod = "GET", notes = "机构集合查询", response = Json.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "current", value = "当前页", dataType = "int", required = true),
            @ApiImplicitParam(name = "size", value = "分页显示数量", dataType = "int", required = true),
    })
    @GetMapping("/select")
    public Json<Page<InstitutionsVO>> select(@Validated(value = InstitutionsQuery.Select.class) InstitutionsQuery institutionsQuery, Integer current, Integer size) {
        if(Objects.isNull(current) || Objects.isNull(size)) {
            log.error("机构集合分页查询：缺少分页必要参数！");
            throw new ParameterNullException("缺少必要参数！");
        }
        //返回内容
        return new Json(ReturnCode.成功, institutionsService.pageEnhance(new Page(current, size), institutionsQuery));
    }


    /**
     * TODO 集合
     *
     * @param institutionsQuery 机构
     * @return Json<List<InstitutionsVO>>
     * @author sunxin
     * @methodName selectList
     * @time 2022-07-04 10:48:36
     */
    @Methods(methodsName = "机构集合查询", methods = "selectList")
    @ApiOperation(value = "机构集合查询", httpMethod = "GET", notes = "机构集合查询", response = Json.class)
    @GetMapping("/selectList")
    public Json<List<InstitutionsVO>> selectList(@Validated(value = InstitutionsQuery.SelectList.class) InstitutionsQuery institutionsQuery) {
        //返回内容
        return new Json(ReturnCode.成功, institutionsService.listEnhance(institutionsQuery));
    }


    /**
     * TODO 单条
     *
     * @param institutionsQuery 机构
     * @return Json<InstitutionsVO>
     * @author sunxin
     * @methodName selectOne
     * @time 2022-07-04 10:48:36
     */
    @Methods(methodsName = "机构单条查询", methods = "selectOne")
    @ApiOperation(value = "机构单条查询", httpMethod = "GET", notes = "机构单条查询", response = Json.class)
    @GetMapping("/selectOne")
    public Json<InstitutionsVO> selectOne(@Validated(value = InstitutionsQuery.SelectOne.class) InstitutionsQuery institutionsQuery) {
        if(StringUtils.isBlank(institutionsQuery.getId()) && StringUtils.isBlank(institutionsQuery.getName()) && StringUtils.isBlank(institutionsQuery.getCode())) {
            log.error("机构单条查询：缺少必要参数！请求参数为：{}", JSON.toJSONString(institutionsQuery));
            throw new ParameterNullException("缺少必要参数！");
        }
        //返回内容
        return new Json(ReturnCode.成功, institutionsService.getOneEnhance(institutionsQuery));
    }


    /**
     * TODO 总数
     *
     * @param institutionsQuery 机构
     * @return Json<Integer>
     * @author sunxin
     * @methodName count
     * @time 2022-07-04 10:48:36
     */
    @Methods(methodsName = "机构总数查询", methods = "count")
    @ApiOperation(value = "机构总数查询", httpMethod = "GET", notes = "机构总数查询", response = Json.class)
    @GetMapping(value = "/count")
    public Json<Integer> count(@Validated(value = InstitutionsQuery.Count.class) InstitutionsQuery institutionsQuery) {
        //返回内容
        return new Json(ReturnCode.成功, institutionsService.countEnhance(institutionsQuery));
    }


    /**
     * TODO 新增
     *
     * @param institutionsBO 机构
     * @return Json<String>
     * @author sunxin
     * @methodName save
     * @time 2022-07-04 10:48:36
     */
    @PreventRepeat
    @Methods(methodsName = "机构新增", methods = "save")
    @ApiOperation(value = "机构新增", httpMethod = "POST", notes = "机构新增", response = Json.class)
    @ApiOperationSupport(ignoreParameters = {"id", "createDateTime", "createName", "modifyDateTime", "modifyName", "isDelete", "version"})
    @PostMapping("/save")
    public Json<String> save(@Validated(value = InstitutionsBO.Save.class) InstitutionsBO institutionsBO, HttpServletRequest httpServletRequest) {
        //缓存取出用户
        Map<String, Object> u = RedisUtils.getToken(stringRedisTemplate, httpServletRequest);
        if (u != null) {
            institutionsBO.setCreateName(u.get(UniversalConstant.NAME) + "-" + u.get(UniversalConstant.ID));
        }
        //返回内容
        return new Json(ReturnCode.成功, institutionsService.saveEnhance(institutionsBO));
    }


    /**
     * TODO 修改
     *
     * @param institutionsBO 机构
     * @return Json<Boolean>
     * @author sunxin
     * @methodName update
     * @time 2022-07-04 10:48:36
     */
    @PreventRepeat
    @Methods(methodsName = "机构修改", methods = "update")
    @ApiOperation(value = "机构修改", httpMethod = "PUT", notes = "机构修改", response = Json.class)
    @ApiOperationSupport(ignoreParameters = {"createDateTime", "createName", "modifyDateTime", "modifyName", "isDelete", "version"})
    @PutMapping("/update")
    public Json<Boolean> update(@Validated(value = InstitutionsBO.Update.class) @RequestBody InstitutionsBO institutionsBO, HttpServletRequest httpServletRequest) {
        //缓存取出用户
        Map<String, Object> u = RedisUtils.getToken(stringRedisTemplate, httpServletRequest);
        if (u != null) {
            institutionsBO.setModifyName(u.get(UniversalConstant.NAME) + "-" + u.get(UniversalConstant.ID));
        }
        //返回内容
        return new Json(ReturnCode.成功, institutionsService.updateEnhance(institutionsBO));
    }


    /**
     * TODO 删除
     *
     * @param institutionsBO 机构
     * @return Json<Boolean>
     * @author sunxin
     * @methodName remove
     * @time 2022-07-04 10:48:36
     */
    @Methods(methodsName = "机构删除", methods = "remove")
    @ApiOperation(value = "机构删除", httpMethod = "DELETE", notes = "机构删除", response = Json.class)
    @ApiOperationSupport(includeParameters = {"id"})
    @DeleteMapping("/remove")
    public Json<Boolean> remove(@Validated(value = InstitutionsBO.Remove.class) InstitutionsBO institutionsBO) {
        return new Json(ReturnCode.成功, institutionsService.removeEnhance(institutionsBO));
    }


    /**
     * 机构集合导出
     *
     * @param institutionsQuery 机构
     * @return Json<List<InstitutionsVO>>
     * @author sunxin
     * @methodName selectList
     * @time 2022-07-04 10:48:36
     */
    @Methods(methodsName = "机构集合导出", methods = "exportExcel")
    @ApiOperation(value = "机构集合导出", httpMethod = "POST", notes = "机构集合导出", response = Json.class)
    @PostMapping("/exportExcel")
    @ResponseBody
    public void exportExcel(HttpServletResponse response, InstitutionsQuery institutionsQuery) {
        institutionsService.exportExcel(response, institutionsQuery);
    }
}