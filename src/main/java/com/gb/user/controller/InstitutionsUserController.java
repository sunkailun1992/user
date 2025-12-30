package com.gb.user.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gb.user.entity.bo.InstitutionsUserBO;
import com.gb.user.entity.query.InstitutionsUserQuery;
import com.gb.user.entity.vo.InstitutionsUserVO;
import com.gb.user.service.InstitutionsUserService;
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
 * TODO 机构用户关联，Comment请求层
 * 代码生成器
 *
 * @author sunxin
 * @className InstitutionsUserController
 * @time 2022-07-04 10:48:37
 */
@Slf4j
@RequestRequired
@ApiSupport(author = "sunxin")
@Setter(onMethod_ = {@Autowired})
@RestController
@RequestMapping("/institutions-user")
@Api(tags = "机构用户关联")
public class InstitutionsUserController {


    /**
     * 机构用户关联
     */
    private InstitutionsUserService institutionsUserService;
    /**
     * redis操作
     */
    private StringRedisTemplate stringRedisTemplate;


    /**
     * TODO 分页
     *
     * @param institutionsUserQuery 机构用户关联
     * @param current
     * @param size
     * @return Json<Page<InstitutionsUserVO>>
     * @author sunxin
     * @methodName select
     * @time 2022-07-04 10:48:37
     */
    @Methods(methodsName = "机构用户关联集合分页查询", methods = "select")
    @ApiOperation(value = "机构用户关联集合分页查询", httpMethod = "GET", notes = "机构用户关联集合查询", response = Json.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "current", value = "当前页", dataType = "int", required = true),
            @ApiImplicitParam(name = "size", value = "分页显示数量", dataType = "int", required = true),
    })
    @GetMapping("/select")
    public Json<Page<InstitutionsUserVO>> select(@Validated(value = InstitutionsUserQuery.Select.class) InstitutionsUserQuery institutionsUserQuery, Integer current, Integer size) {
        if(Objects.isNull(current) || Objects.isNull(size)) {
            log.error("机构集合分页查询：缺少分页必要参数！");
            throw new ParameterNullException("缺少必要参数！");
        }
        //返回内容
        return new Json(ReturnCode.成功, institutionsUserService.pageEnhance(new Page(current, size), institutionsUserQuery));
    }


    /**
     * TODO 集合
     *
     * @param institutionsUserQuery 机构用户关联
     * @return Json<List<InstitutionsUserVO>>
     * @author sunxin
     * @methodName selectList
     * @time 2022-07-04 10:48:37
     */
    @Methods(methodsName = "机构用户关联集合查询", methods = "selectList")
    @ApiOperation(value = "机构用户关联集合查询", httpMethod = "GET", notes = "机构用户关联集合查询", response = Json.class)
    @GetMapping("/selectList")
    public Json<List<InstitutionsUserVO>> selectList(@Validated(value = InstitutionsUserQuery.SelectList.class) InstitutionsUserQuery institutionsUserQuery) {
        //返回内容
        return new Json(ReturnCode.成功, institutionsUserService.listEnhance(institutionsUserQuery));
    }


    /**
     * TODO 单条
     *
     * @param institutionsUserQuery 机构用户关联
     * @return Json<InstitutionsUserVO>
     * @author sunxin
     * @methodName selectOne
     * @time 2022-07-04 10:48:37
     */
    @Methods(methodsName = "机构用户关联单条查询", methods = "selectOne")
    @ApiOperation(value = "机构用户关联单条查询", httpMethod = "GET", notes = "机构用户关联单条查询", response = Json.class)
    @GetMapping("/selectOne")
    public Json<InstitutionsUserVO> selectOne(@Validated(value = InstitutionsUserQuery.SelectOne.class) InstitutionsUserQuery institutionsUserQuery) {
        //返回内容
        return new Json(ReturnCode.成功, institutionsUserService.getOneEnhance(institutionsUserQuery));
    }


    /**
     * TODO 总数
     *
     * @param institutionsUserQuery 机构用户关联
     * @return Json<Integer>
     * @author sunxin
     * @methodName count
     * @time 2022-07-04 10:48:37
     */
    @Methods(methodsName = "机构用户关联总数查询", methods = "count")
    @ApiOperation(value = "机构用户关联总数查询", httpMethod = "GET", notes = "机构用户关联总数查询", response = Json.class)
    @GetMapping(value = "/count")
    public Json<Integer> count(@Validated(value = InstitutionsUserQuery.Count.class) InstitutionsUserQuery institutionsUserQuery) {
        //返回内容
        return new Json(ReturnCode.成功, institutionsUserService.countEnhance(institutionsUserQuery));
    }


    /**
     * TODO 新增
     *
     * @param institutionsUserBO 机构用户关联
     * @return Json<String>
     * @author sunxin
     * @methodName save
     * @time 2022-07-04 10:48:37
     */
    @PreventRepeat
    @Methods(methodsName = "机构用户关联新增", methods = "save")
    @ApiOperation(value = "机构用户关联新增", httpMethod = "POST", notes = "机构用户关联新增", response = Json.class)
    @ApiOperationSupport(ignoreParameters = {"id", "createDateTime", "createName", "modifyDateTime", "modifyName", "isDelete", "version"})
    @PostMapping("/save")
    public Json<String> save(@Validated(value = InstitutionsUserBO.Save.class)  @RequestBody  InstitutionsUserBO institutionsUserBO, HttpServletRequest httpServletRequest) {
        //缓存取出用户
        Map<String, Object> u = RedisUtils.getToken(stringRedisTemplate, httpServletRequest);
        if (u != null) {
            institutionsUserBO.setCreateName(u.get(UniversalConstant.NAME) + "-" + u.get(UniversalConstant.ID));
        }
        //返回内容
        return new Json(ReturnCode.成功, institutionsUserService.saveEnhance(httpServletRequest, institutionsUserBO));
    }


    /**
     * TODO 修改
     *
     * @param institutionsUserBO 机构用户关联
     * @return Json<Boolean>
     * @author sunxin
     * @methodName update
     * @time 2022-07-04 10:48:37
     */
    @PreventRepeat
    @Methods(methodsName = "机构用户关联修改", methods = "update")
    @ApiOperation(value = "机构用户关联修改", httpMethod = "PUT", notes = "机构用户关联修改", response = Json.class)
    @ApiOperationSupport(ignoreParameters = {"createDateTime", "createName", "modifyDateTime", "modifyName", "isDelete", "version"})
    @PutMapping("/update")
    public Json<Boolean> update(@Validated(value = InstitutionsUserBO.Update.class) @RequestBody InstitutionsUserBO institutionsUserBO, HttpServletRequest httpServletRequest) {
        //缓存取出用户
        Map<String, Object> u = RedisUtils.getToken(stringRedisTemplate, httpServletRequest);
        if (u != null) {
            institutionsUserBO.setModifyName(u.get(UniversalConstant.NAME) + "-" + u.get(UniversalConstant.ID));
        }
        //返回内容
        return new Json(ReturnCode.成功, institutionsUserService.updateEnhance(httpServletRequest, institutionsUserBO));
    }


    /**
     * TODO 删除
     *
     * @param institutionsUserBO 机构用户关联
     * @return Json<Boolean>
     * @author sunxin
     * @methodName remove
     * @time 2022-07-04 10:48:37
     */
    @Methods(methodsName = "机构用户关联删除", methods = "remove")
    @ApiOperation(value = "机构用户关联删除", httpMethod = "DELETE", notes = "机构用户关联删除", response = Json.class)
    @ApiOperationSupport(includeParameters = {"id"})
    @DeleteMapping("/remove")
    public Json<Boolean> remove(@Validated(value = InstitutionsUserBO.Remove.class) InstitutionsUserBO institutionsUserBO) {
        return new Json(ReturnCode.成功, institutionsUserService.removeEnhance(institutionsUserBO));
    }


    /**
     * 经纪人集合导出
     *
     * @param institutionsUserQuery 机构用户关联
     * @return void
     * @author sunxin
     * @methodName selectList
     * @time 2022-07-04 10:48:36
     */
    @Methods(methodsName = "经纪人集合导出", methods = "exportExcel")
    @ApiOperation(value = "经纪人集合导出", httpMethod = "POST", notes = "经纪人集合导出", response = Json.class)
    @PostMapping("/exportExcel")
    @ResponseBody
    public void exportExcel(HttpServletResponse response, InstitutionsUserQuery institutionsUserQuery) {
        institutionsUserService.exportExcel(response, institutionsUserQuery);
    }
}