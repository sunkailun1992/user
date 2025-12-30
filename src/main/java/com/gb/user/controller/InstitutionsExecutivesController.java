package com.gb.user.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gb.user.entity.bo.InstitutionsExecutivesBO;
import com.gb.user.entity.query.InstitutionsExecutivesQuery;
import com.gb.user.entity.vo.InstitutionsExecutivesVO;
import com.gb.user.service.InstitutionsExecutivesService;
import com.gb.utils.Json;
import com.gb.utils.RedisUtils;
import com.gb.utils.annotations.Methods;
import com.gb.utils.annotations.PreventRepeat;
import com.gb.utils.annotations.RequestRequired;
import com.gb.utils.constants.UniversalConstant;
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
import java.util.List;
import java.util.Map;


/**
 * TODO 机构高管，Comment请求层
 * 代码生成器
 *
 * @author sunxin
 * @className InstitutionsExecutivesController
 * @time 2022-07-04 10:48:36
 */
@Slf4j
@RequestRequired
@ApiSupport(author = "sunxin")
@Setter(onMethod_ = {@Autowired})
@RestController
@RequestMapping("/institutions-executives")
@Api(tags = "机构高管")
public class InstitutionsExecutivesController {


    /**
     * 机构高管
     */
    private InstitutionsExecutivesService institutionsExecutivesService;
    /**
     * redis操作
     */
    private StringRedisTemplate stringRedisTemplate;


    /**
     * TODO 分页
     *
     * @param institutionsExecutivesQuery 机构高管
     * @param current
     * @param size
     * @return Json<Page<InstitutionsExecutivesVO>>
     * @author sunxin
     * @methodName select
     * @time 2022-07-04 10:48:36
     */
    @Methods(methodsName = "机构高管集合分页查询", methods = "select")
    @ApiOperation(value = "机构高管集合分页查询", httpMethod = "GET", notes = "机构高管集合查询", response = Json.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "current", value = "当前页", dataType = "int", required = true),
            @ApiImplicitParam(name = "size", value = "分页显示数量", dataType = "int", required = true),
    })
    @GetMapping("/select")
    public Json<Page<InstitutionsExecutivesVO>> select(@Validated(value = InstitutionsExecutivesQuery.Select.class) InstitutionsExecutivesQuery institutionsExecutivesQuery, Integer current, Integer size) {
        //返回内容
        return new Json(ReturnCode.成功, institutionsExecutivesService.pageEnhance(new Page(current, size), institutionsExecutivesQuery));
    }


    /**
     * TODO 集合
     *
     * @param institutionsExecutivesQuery 机构高管
     * @return Json<List<InstitutionsExecutivesVO>>
     * @author sunxin
     * @methodName selectList
     * @time 2022-07-04 10:48:36
     */
    @Methods(methodsName = "机构高管集合查询", methods = "selectList")
    @ApiOperation(value = "机构高管集合查询", httpMethod = "GET", notes = "机构高管集合查询", response = Json.class)
    @GetMapping("/selectList")
    public Json<List<InstitutionsExecutivesVO>> selectList(@Validated(value = InstitutionsExecutivesQuery.SelectList.class) InstitutionsExecutivesQuery institutionsExecutivesQuery) {
        //返回内容
        return new Json(ReturnCode.成功, institutionsExecutivesService.listEnhance(institutionsExecutivesQuery));
    }


    /**
     * TODO 单条
     *
     * @param institutionsExecutivesQuery 机构高管
     * @return Json<InstitutionsExecutivesVO>
     * @author sunxin
     * @methodName selectOne
     * @time 2022-07-04 10:48:36
     */
    @Methods(methodsName = "机构高管单条查询", methods = "selectOne")
    @ApiOperation(value = "机构高管单条查询", httpMethod = "GET", notes = "机构高管单条查询", response = Json.class)
    @GetMapping("/selectOne")
    public Json<InstitutionsExecutivesVO> selectOne(@Validated(value = InstitutionsExecutivesQuery.SelectOne.class) InstitutionsExecutivesQuery institutionsExecutivesQuery) {
        //返回内容
        return new Json(ReturnCode.成功, institutionsExecutivesService.getOneEnhance(institutionsExecutivesQuery));
    }


    /**
     * TODO 总数
     *
     * @param institutionsExecutivesQuery 机构高管
     * @return Json<Integer>
     * @author sunxin
     * @methodName count
     * @time 2022-07-04 10:48:36
     */
    @Methods(methodsName = "机构高管总数查询", methods = "count")
    @ApiOperation(value = "机构高管总数查询", httpMethod = "GET", notes = "机构高管总数查询", response = Json.class)
    @GetMapping(value = "/count")
    public Json<Integer> count(@Validated(value = InstitutionsExecutivesQuery.Count.class) InstitutionsExecutivesQuery institutionsExecutivesQuery) {
        //返回内容
        return new Json(ReturnCode.成功, institutionsExecutivesService.countEnhance(institutionsExecutivesQuery));
    }


    /**
     * TODO 新增
     *
     * @param institutionsExecutivesBO 机构高管
     * @return Json<String>
     * @author sunxin
     * @methodName save
     * @time 2022-07-04 10:48:36
     */
    @PreventRepeat
    @Methods(methodsName = "机构高管新增", methods = "save")
    @ApiOperation(value = "机构高管新增", httpMethod = "POST", notes = "机构高管新增", response = Json.class)
    @ApiOperationSupport(ignoreParameters = {"id", "createDateTime", "createName", "modifyDateTime", "modifyName", "isDelete", "version"})
    @PostMapping("/save")
    public Json<String> save(@Validated(value = InstitutionsExecutivesBO.Save.class) InstitutionsExecutivesBO institutionsExecutivesBO, HttpServletRequest httpServletRequest) {
        //缓存取出用户
        Map<String, Object> u = RedisUtils.getToken(stringRedisTemplate, httpServletRequest);
        if (u != null) {
            institutionsExecutivesBO.setCreateName(u.get(UniversalConstant.NAME) + "-" + u.get(UniversalConstant.ID));
        }
        //返回内容
        return new Json(ReturnCode.成功, institutionsExecutivesService.saveEnhance(institutionsExecutivesBO));
    }


    /**
     * TODO 修改
     *
     * @param institutionsExecutivesBO 机构高管
     * @return Json<Boolean>
     * @author sunxin
     * @methodName update
     * @time 2022-07-04 10:48:36
     */
    @PreventRepeat
    @Methods(methodsName = "机构高管修改", methods = "update")
    @ApiOperation(value = "机构高管修改", httpMethod = "PUT", notes = "机构高管修改", response = Json.class)
    @ApiOperationSupport(ignoreParameters = {"createDateTime", "createName", "modifyDateTime", "modifyName", "isDelete", "version"})
    @PutMapping("/update")
    public Json<Boolean> update(@Validated(value = InstitutionsExecutivesBO.Update.class) InstitutionsExecutivesBO institutionsExecutivesBO, HttpServletRequest httpServletRequest) {
        //缓存取出用户
        Map<String, Object> u = RedisUtils.getToken(stringRedisTemplate, httpServletRequest);
        if (u != null) {
            institutionsExecutivesBO.setModifyName(u.get(UniversalConstant.NAME) + "-" + u.get(UniversalConstant.ID));
        }
        //返回内容
        return new Json(ReturnCode.成功, institutionsExecutivesService.updateEnhance(institutionsExecutivesBO));
    }


    /**
     * TODO 删除
     *
     * @param institutionsExecutivesBO 机构高管
     * @return Json<Boolean>
     * @author sunxin
     * @methodName remove
     * @time 2022-07-04 10:48:36
     */
    @Methods(methodsName = "机构高管删除", methods = "remove")
    @ApiOperation(value = "机构高管删除", httpMethod = "DELETE", notes = "机构高管删除", response = Json.class)
    @ApiOperationSupport(includeParameters = {"id"})
    @DeleteMapping("/remove")
    public Json<Boolean> remove(@Validated(value = InstitutionsExecutivesBO.Remove.class) InstitutionsExecutivesBO institutionsExecutivesBO) {
        return new Json(ReturnCode.成功, institutionsExecutivesService.removeEnhance(institutionsExecutivesBO));
    }
}