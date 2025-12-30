package com.gb.user.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gb.user.entity.bo.TeamUserDataPermissionsBO;
import com.gb.user.entity.query.TeamUserDataPermissionsQuery;
import com.gb.user.entity.vo.TeamUserDataPermissionsVO;
import com.gb.user.service.TeamUserDataPermissionsService;
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
import java.util.List;
import java.util.Map;


/**
 * TODO 团队人员数据权限，Comment请求层
 * 代码生成器
 *
 * @author sunx
 * @className TeamUserDataPermissionsController
 * @time 2022-08-30 04:44:18
 */
@Slf4j
@RequestRequired
@ApiSupport(author = "sunx")
@Setter(onMethod_ = {@Autowired})
@RestController
@RequestMapping("/team-user-data-permissions")
@Api(tags = "团队人员数据权限")
public class TeamUserDataPermissionsController {


    /**
     * 团队人员数据权限
     */
    private TeamUserDataPermissionsService teamUserDataPermissionsService;
    /**
     * redis操作
     */
    private StringRedisTemplate stringRedisTemplate;


    /**
     * TODO 分页
     *
     * @param teamUserDataPermissionsQuery 团队人员数据权限
     * @param current
     * @param size
     * @return Json<Page<TeamUserDataPermissionsVO>>
     * @author sunx
     * @methodName select
     * @time 2022-08-30 04:44:18
     */
    @Methods(methodsName = "团队人员数据权限集合分页查询", methods = "select")
    @ApiOperation(value = "团队人员数据权限集合分页查询", httpMethod = "GET", notes = "团队人员数据权限集合查询", response = Json.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "current", value = "当前页", dataType = "int", required = true),
            @ApiImplicitParam(name = "size", value = "分页显示数量", dataType = "int", required = true),
    })
    @GetMapping("/select")
    public Json<Page<TeamUserDataPermissionsVO>> select(@Validated(value = TeamUserDataPermissionsQuery.Select.class) TeamUserDataPermissionsQuery teamUserDataPermissionsQuery, Integer current, Integer size) {
        //返回内容
        return new Json(ReturnCode.成功, teamUserDataPermissionsService.pageEnhance(new Page(current, size), teamUserDataPermissionsQuery));
    }


    /**
     * TODO 集合
     *
     * @param teamUserDataPermissionsQuery 团队人员数据权限
     * @return Json<List<TeamUserDataPermissionsVO>>
     * @author sunx
     * @methodName selectList
     * @time 2022-08-30 04:44:18
     */
    @Methods(methodsName = "团队人员数据权限集合查询", methods = "selectList")
    @ApiOperation(value = "团队人员数据权限集合查询", httpMethod = "GET", notes = "团队人员数据权限集合查询", response = Json.class)
    @GetMapping("/selectList")
    public Json<List<TeamUserDataPermissionsVO>> selectList(@Validated(value = TeamUserDataPermissionsQuery.SelectList.class) TeamUserDataPermissionsQuery teamUserDataPermissionsQuery) {
        //返回内容
        return new Json(ReturnCode.成功, teamUserDataPermissionsService.listEnhance(teamUserDataPermissionsQuery));
    }


    /**
     * TODO 单条
     *
     * @param teamUserDataPermissionsQuery 团队人员数据权限
     * @return Json<TeamUserDataPermissionsVO>
     * @author sunx
     * @methodName selectOne
     * @time 2022-08-30 04:44:18
     */
    @Methods(methodsName = "团队人员数据权限单条查询", methods = "selectOne")
    @ApiOperation(value = "团队人员数据权限单条查询", httpMethod = "GET", notes = "团队人员数据权限单条查询", response = Json.class)
    @GetMapping("/selectOne")
    public Json<TeamUserDataPermissionsVO> selectOne(@Validated(value = TeamUserDataPermissionsQuery.SelectOne.class) TeamUserDataPermissionsQuery teamUserDataPermissionsQuery) {
        //返回内容
        return new Json(ReturnCode.成功, teamUserDataPermissionsService.getOneEnhance(teamUserDataPermissionsQuery));
    }


    /**
     * TODO 总数
     *
     * @param teamUserDataPermissionsQuery 团队人员数据权限
     * @return Json<Integer>
     * @author sunx
     * @methodName count
     * @time 2022-08-30 04:44:18
     */
    @Methods(methodsName = "团队人员数据权限总数查询", methods = "count")
    @ApiOperation(value = "团队人员数据权限总数查询", httpMethod = "GET", notes = "团队人员数据权限总数查询", response = Json.class)
    @GetMapping(value = "/count")
    public Json<Integer> count(@Validated(value = TeamUserDataPermissionsQuery.Count.class) TeamUserDataPermissionsQuery teamUserDataPermissionsQuery) {
        //返回内容
        return new Json(ReturnCode.成功, teamUserDataPermissionsService.countEnhance(teamUserDataPermissionsQuery));
    }


    /**
     * TODO 新增
     *
     * @param teamUserDataPermissionsBO 团队人员数据权限
     * @return Json<String>
     * @author sunx
     * @methodName save
     * @time 2022-08-30 04:44:18
     */
    @PreventRepeat
    @Methods(methodsName = "团队人员数据权限新增", methods = "save")
    @ApiOperation(value = "团队人员数据权限新增", httpMethod = "POST", notes = "团队人员数据权限新增", response = Json.class)
    @ApiOperationSupport(ignoreParameters = {"id", "createDateTime", "createName", "modifyDateTime", "modifyName", "isDelete", "version"})
    @PostMapping("/save")
    public Json<String> save(@Validated(value = TeamUserDataPermissionsBO.Save.class) TeamUserDataPermissionsBO teamUserDataPermissionsBO, HttpServletRequest httpServletRequest) {
        //缓存取出用户
        Map<String, Object> u = RedisUtils.getToken(stringRedisTemplate, httpServletRequest);
        if (u != null) {
            teamUserDataPermissionsBO.setCreateName(u.get("name") + "-" + u.get("id"));
        }
        //返回内容
        return new Json(ReturnCode.成功, teamUserDataPermissionsService.saveEnhance(teamUserDataPermissionsBO));
    }


    /**
     * TODO 修改
     *
     * @param teamUserDataPermissionsBO 团队人员数据权限
     * @return Json<Boolean>
     * @author sunx
     * @methodName update
     * @time 2022-08-30 04:44:18
     */
    @PreventRepeat
    @Methods(methodsName = "团队人员数据权限修改", methods = "update")
    @ApiOperation(value = "团队人员数据权限修改", httpMethod = "PUT", notes = "团队人员数据权限修改", response = Json.class)
    @ApiOperationSupport(ignoreParameters = {"createDateTime", "createName", "modifyDateTime", "modifyName", "isDelete", "version"})
    @PutMapping("/update")
    public Json<Boolean> update(@Validated(value = TeamUserDataPermissionsBO.Update.class) TeamUserDataPermissionsBO teamUserDataPermissionsBO, HttpServletRequest httpServletRequest) {
        //缓存取出用户
        Map<String, Object> u = RedisUtils.getToken(stringRedisTemplate, httpServletRequest);
        if (u != null) {
            teamUserDataPermissionsBO.setModifyName(u.get("name") + "-" + u.get("id"));
        }
        //返回内容
        return new Json(ReturnCode.成功, teamUserDataPermissionsService.updateEnhance(teamUserDataPermissionsBO));
    }


    /**
     * TODO 删除
     *
     * @param teamUserDataPermissionsBO 团队人员数据权限
     * @return Json<Boolean>
     * @author sunx
     * @methodName remove
     * @time 2022-08-30 04:44:18
     */
    @Methods(methodsName = "团队人员数据权限删除", methods = "remove")
    @ApiOperation(value = "团队人员数据权限删除", httpMethod = "DELETE", notes = "团队人员数据权限删除", response = Json.class)
    @ApiOperationSupport(includeParameters = {"id"})
    @DeleteMapping("/remove")
    public Json<Boolean> remove(@Validated(value = TeamUserDataPermissionsBO.Remove.class) TeamUserDataPermissionsBO teamUserDataPermissionsBO) {
        return new Json(ReturnCode.成功, teamUserDataPermissionsService.removeEnhance(teamUserDataPermissionsBO));
    }
}