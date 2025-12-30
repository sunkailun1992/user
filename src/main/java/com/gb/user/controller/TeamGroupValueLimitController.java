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
import com.gb.user.service.TeamGroupValueLimitService;
import com.gb.user.entity.query.TeamGroupValueLimitQuery;
import com.gb.user.entity.vo.TeamGroupValueLimitVO;
import com.gb.user.entity.bo.TeamGroupValueLimitBO;


/**
 * TODO 团队组别限制，Comment请求层
 * 代码生成器
 *
 * @author sunx
 * @className TeamGroupValueLimitController
 * @time 2022-08-31 10:59:01
 */
@Slf4j
@RequestRequired
@ApiSupport(author = "sunx")
@Setter(onMethod_ = {@Autowired})
@RestController
@RequestMapping("/team-group-value-limit")
@Api(tags = "团队组别限制")
public class TeamGroupValueLimitController {


    /**
     * 团队组别限制
     */
    private TeamGroupValueLimitService teamGroupValueLimitService;
    /**
     * redis操作
     */
    private StringRedisTemplate stringRedisTemplate;


    /**
     * TODO 分页
     *
     * @param teamGroupValueLimitQuery 团队组别限制
     * @param current
     * @param size
     * @return Json<Page<TeamGroupValueLimitVO>>
     * @author sunx
     * @methodName select
     * @time 2022-08-31 10:59:01
     */
    @Methods(methodsName = "团队组别限制集合分页查询", methods = "select")
    @ApiOperation(value = "团队组别限制集合分页查询", httpMethod = "GET", notes = "团队组别限制集合查询", response = Json.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "current", value = "当前页", dataType = "int", required = true),
            @ApiImplicitParam(name = "size", value = "分页显示数量", dataType = "int", required = true),
    })
    @GetMapping("/select")
    public Json<Page<TeamGroupValueLimitVO>> select(@Validated(value = TeamGroupValueLimitQuery.Select.class) TeamGroupValueLimitQuery teamGroupValueLimitQuery, Integer current, Integer size) {
        //返回内容
        return new Json(ReturnCode.成功, teamGroupValueLimitService.pageEnhance(new Page(current, size), teamGroupValueLimitQuery));
    }


    /**
     * TODO 集合
     *
     * @param teamGroupValueLimitQuery 团队组别限制
     * @return Json<List<TeamGroupValueLimitVO>>
     * @author sunx
     * @methodName selectList
     * @time 2022-08-31 10:59:01
     */
    @Methods(methodsName = "团队组别限制集合查询", methods = "selectList")
    @ApiOperation(value = "团队组别限制集合查询", httpMethod = "GET", notes = "团队组别限制集合查询", response = Json.class)
    @GetMapping("/selectList")
    public Json<List<TeamGroupValueLimitVO>> selectList(@Validated(value = TeamGroupValueLimitQuery.SelectList.class) TeamGroupValueLimitQuery teamGroupValueLimitQuery) {
        //返回内容
        return new Json(ReturnCode.成功, teamGroupValueLimitService.listEnhance(teamGroupValueLimitQuery));
    }


    /**
     * TODO 单条
     *
     * @param teamGroupValueLimitQuery 团队组别限制
     * @return Json<TeamGroupValueLimitVO>
     * @author sunx
     * @methodName selectOne
     * @time 2022-08-31 10:59:01
     */
    @Methods(methodsName = "团队组别限制单条查询", methods = "selectOne")
    @ApiOperation(value = "团队组别限制单条查询", httpMethod = "GET", notes = "团队组别限制单条查询", response = Json.class)
    @GetMapping("/selectOne")
    public Json<TeamGroupValueLimitVO> selectOne(@Validated(value = TeamGroupValueLimitQuery.SelectOne.class) TeamGroupValueLimitQuery teamGroupValueLimitQuery) {
        //返回内容
        return new Json(ReturnCode.成功, teamGroupValueLimitService.getOneEnhance(teamGroupValueLimitQuery));
    }


    /**
     * TODO 总数
     *
     * @param teamGroupValueLimitQuery 团队组别限制
     * @return Json<Integer>
     * @author sunx
     * @methodName count
     * @time 2022-08-31 10:59:01
     */
    @Methods(methodsName = "团队组别限制总数查询", methods = "count")
    @ApiOperation(value = "团队组别限制总数查询", httpMethod = "GET", notes = "团队组别限制总数查询", response = Json.class)
    @GetMapping(value = "/count")
    public Json<Integer> count(@Validated(value = TeamGroupValueLimitQuery.Count.class) TeamGroupValueLimitQuery teamGroupValueLimitQuery) {
        //返回内容
        return new Json(ReturnCode.成功, teamGroupValueLimitService.countEnhance(teamGroupValueLimitQuery));
    }


    /**
     * TODO 新增
     *
     * @param teamGroupValueLimitBO 团队组别限制
     * @return Json<String>
     * @author sunx
     * @methodName save
     * @time 2022-08-31 10:59:01
     */
    @PreventRepeat
    @Methods(methodsName = "团队组别限制新增", methods = "save")
    @ApiOperation(value = "团队组别限制新增", httpMethod = "POST", notes = "团队组别限制新增", response = Json.class)
    @ApiOperationSupport(ignoreParameters = {"id", "createDateTime", "createName", "modifyDateTime", "modifyName", "isDelete", "version"})
    @PostMapping("/save")
    public Json<String> save(@Validated(value = TeamGroupValueLimitBO.Save.class) TeamGroupValueLimitBO teamGroupValueLimitBO, HttpServletRequest httpServletRequest) {
        //缓存取出用户
        Map<String, Object> u = RedisUtils.getToken(stringRedisTemplate, httpServletRequest);
        if (u != null) {
            teamGroupValueLimitBO.setCreateName(u.get("name") + "-" + u.get("id"));
        }
        //返回内容
        return new Json(ReturnCode.成功, teamGroupValueLimitService.saveEnhance(teamGroupValueLimitBO));
    }


    /**
     * TODO 修改
     *
     * @param teamGroupValueLimitBO 团队组别限制
     * @return Json<Boolean>
     * @author sunx
     * @methodName update
     * @time 2022-08-31 10:59:01
     */
    @PreventRepeat
    @Methods(methodsName = "团队组别限制修改", methods = "update")
    @ApiOperation(value = "团队组别限制修改", httpMethod = "PUT", notes = "团队组别限制修改", response = Json.class)
    @ApiOperationSupport(ignoreParameters = {"createDateTime", "createName", "modifyDateTime", "modifyName", "isDelete", "version"})
    @PutMapping("/update")
    public Json<Boolean> update(@Validated(value = TeamGroupValueLimitBO.Update.class) TeamGroupValueLimitBO teamGroupValueLimitBO, HttpServletRequest httpServletRequest) {
        //缓存取出用户
        Map<String, Object> u = RedisUtils.getToken(stringRedisTemplate, httpServletRequest);
        if (u != null) {
            teamGroupValueLimitBO.setModifyName(u.get("name") + "-" + u.get("id"));
        }
        //返回内容
        return new Json(ReturnCode.成功, teamGroupValueLimitService.updateEnhance(teamGroupValueLimitBO));
    }


    /**
     * TODO 删除
     *
     * @param teamGroupValueLimitBO 团队组别限制
     * @return Json<Boolean>
     * @author sunx
     * @methodName remove
     * @time 2022-08-31 10:59:01
     */
    @Methods(methodsName = "团队组别限制删除", methods = "remove")
    @ApiOperation(value = "团队组别限制删除", httpMethod = "DELETE", notes = "团队组别限制删除", response = Json.class)
    @ApiOperationSupport(includeParameters = {"id"})
    @DeleteMapping("/remove")
    public Json<Boolean> remove(@Validated(value = TeamGroupValueLimitBO.Remove.class) TeamGroupValueLimitBO teamGroupValueLimitBO) {
        return new Json(ReturnCode.成功, teamGroupValueLimitService.removeEnhance(teamGroupValueLimitBO));
    }
}