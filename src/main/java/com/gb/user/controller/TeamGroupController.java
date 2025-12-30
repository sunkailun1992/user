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
import com.gb.user.service.TeamGroupService;
import com.gb.user.entity.query.TeamGroupQuery;
import com.gb.user.entity.vo.TeamGroupVO;
import com.gb.user.entity.bo.TeamGroupBO;


/**
 * TODO 团队组别，Comment请求层
 * 代码生成器
 *
 * @author sunx
 * @className TeamGroupController
 * @time 2022-08-31 10:59:01
 */
@Slf4j
@RequestRequired
@ApiSupport(author = "sunx")
@Setter(onMethod_ = {@Autowired})
@RestController
@RequestMapping("/team-group")
@Api(tags = "团队组别")
public class TeamGroupController {


    /**
     * 团队组别
     */
    private TeamGroupService teamGroupService;
    /**
     * redis操作
     */
    private StringRedisTemplate stringRedisTemplate;


    /**
     * TODO 分页
     *
     * @param teamGroupQuery 团队组别
     * @param current
     * @param size
     * @return Json<Page<TeamGroupVO>>
     * @author sunx
     * @methodName select
     * @time 2022-08-31 10:59:01
     */
    @Methods(methodsName = "团队组别集合分页查询", methods = "select")
    @ApiOperation(value = "团队组别集合分页查询", httpMethod = "GET", notes = "团队组别集合查询", response = Json.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "current", value = "当前页", dataType = "int", required = true),
            @ApiImplicitParam(name = "size", value = "分页显示数量", dataType = "int", required = true),
    })
    @GetMapping("/select")
    public Json<Page<TeamGroupVO>> select(@Validated(value = TeamGroupQuery.Select.class) TeamGroupQuery teamGroupQuery, Integer current, Integer size) {
        //返回内容
        return new Json(ReturnCode.成功, teamGroupService.pageEnhance(new Page(current, size), teamGroupQuery));
    }


    /**
     * TODO 集合
     *
     * @param teamGroupQuery 团队组别
     * @return Json<List<TeamGroupVO>>
     * @author sunx
     * @methodName selectList
     * @time 2022-08-31 10:59:01
     */
    @Methods(methodsName = "团队组别集合查询", methods = "selectList")
    @ApiOperation(value = "团队组别集合查询", httpMethod = "GET", notes = "团队组别集合查询", response = Json.class)
    @GetMapping("/selectList")
    public Json<List<TeamGroupVO>> selectList(@Validated(value = TeamGroupQuery.SelectList.class) TeamGroupQuery teamGroupQuery) {
        //返回内容
        return new Json(ReturnCode.成功, teamGroupService.listEnhance(teamGroupQuery));
    }


    /**
     * TODO 单条
     *
     * @param teamGroupQuery 团队组别
     * @return Json<TeamGroupVO>
     * @author sunx
     * @methodName selectOne
     * @time 2022-08-31 10:59:01
     */
    @Methods(methodsName = "团队组别单条查询", methods = "selectOne")
    @ApiOperation(value = "团队组别单条查询", httpMethod = "GET", notes = "团队组别单条查询", response = Json.class)
    @GetMapping("/selectOne")
    public Json<TeamGroupVO> selectOne(@Validated(value = TeamGroupQuery.SelectOne.class) TeamGroupQuery teamGroupQuery) {
        //返回内容
        return new Json(ReturnCode.成功, teamGroupService.getOneEnhance(teamGroupQuery));
    }


    /**
     * TODO 总数
     *
     * @param teamGroupQuery 团队组别
     * @return Json<Integer>
     * @author sunx
     * @methodName count
     * @time 2022-08-31 10:59:01
     */
    @Methods(methodsName = "团队组别总数查询", methods = "count")
    @ApiOperation(value = "团队组别总数查询", httpMethod = "GET", notes = "团队组别总数查询", response = Json.class)
    @GetMapping(value = "/count")
    public Json<Integer> count(@Validated(value = TeamGroupQuery.Count.class) TeamGroupQuery teamGroupQuery) {
        //返回内容
        return new Json(ReturnCode.成功, teamGroupService.countEnhance(teamGroupQuery));
    }


    /**
     * TODO 新增
     *
     * @param teamGroupBO 团队组别
     * @return Json<String>
     * @author sunx
     * @methodName save
     * @time 2022-08-31 10:59:01
     */
    @PreventRepeat
    @Methods(methodsName = "团队组别新增", methods = "save")
    @ApiOperation(value = "团队组别新增", httpMethod = "POST", notes = "团队组别新增", response = Json.class)
    @ApiOperationSupport(ignoreParameters = {"id", "createDateTime", "createName", "modifyDateTime", "modifyName", "isDelete", "version"})
    @PostMapping("/save")
    public Json<String> save(@Validated(value = TeamGroupBO.Save.class) TeamGroupBO teamGroupBO, HttpServletRequest httpServletRequest) {
        //缓存取出用户
        Map<String, Object> u = RedisUtils.getToken(stringRedisTemplate, httpServletRequest);
        if (u != null) {
            teamGroupBO.setCreateName(u.get("name") + "-" + u.get("id"));
        }
        //返回内容
        return new Json(ReturnCode.成功, teamGroupService.saveEnhance(teamGroupBO));
    }


    /**
     * TODO 修改
     *
     * @param teamGroupBO 团队组别
     * @return Json<Boolean>
     * @author sunx
     * @methodName update
     * @time 2022-08-31 10:59:01
     */
    @PreventRepeat
    @Methods(methodsName = "团队组别修改", methods = "update")
    @ApiOperation(value = "团队组别修改", httpMethod = "PUT", notes = "团队组别修改", response = Json.class)
    @ApiOperationSupport(ignoreParameters = {"createDateTime", "createName", "modifyDateTime", "modifyName", "isDelete", "version"})
    @PutMapping("/update")
    public Json<Boolean> update(@Validated(value = TeamGroupBO.Update.class) TeamGroupBO teamGroupBO, HttpServletRequest httpServletRequest) {
        //缓存取出用户
        Map<String, Object> u = RedisUtils.getToken(stringRedisTemplate, httpServletRequest);
        if (u != null) {
            teamGroupBO.setModifyName(u.get("name") + "-" + u.get("id"));
        }
        //返回内容
        return new Json(ReturnCode.成功, teamGroupService.updateEnhance(teamGroupBO));
    }


    /**
     * TODO 删除
     *
     * @param teamGroupBO 团队组别
     * @return Json<Boolean>
     * @author sunx
     * @methodName remove
     * @time 2022-08-31 10:59:01
     */
    @Methods(methodsName = "团队组别删除", methods = "remove")
    @ApiOperation(value = "团队组别删除", httpMethod = "DELETE", notes = "团队组别删除", response = Json.class)
    @ApiOperationSupport(includeParameters = {"id"})
    @DeleteMapping("/remove")
    public Json<Boolean> remove(@Validated(value = TeamGroupBO.Remove.class) TeamGroupBO teamGroupBO) {
        return new Json(ReturnCode.成功, teamGroupService.removeEnhance(teamGroupBO));
    }
}