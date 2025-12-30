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
import com.gb.user.service.TeamGroupValueService;
import com.gb.user.entity.query.TeamGroupValueQuery;
import com.gb.user.entity.vo.TeamGroupValueVO;
import com.gb.user.entity.bo.TeamGroupValueBO;


/**
 * TODO 团队组别值，Comment请求层
 * 代码生成器
 *
 * @author sunx
 * @className TeamGroupValueController
 * @time 2022-08-31 10:59:01
 */
@Slf4j
@RequestRequired
@ApiSupport(author = "sunx")
@Setter(onMethod_ = {@Autowired})
@RestController
@RequestMapping("/team-group-value")
@Api(tags = "团队组别值")
public class TeamGroupValueController {


    /**
     * 团队组别值
     */
    private TeamGroupValueService teamGroupValueService;
    /**
     * redis操作
     */
    private StringRedisTemplate stringRedisTemplate;


    /**
     * TODO 分页
     *
     * @param teamGroupValueQuery 团队组别值
     * @param current
     * @param size
     * @return Json<Page<TeamGroupValueVO>>
     * @author sunx
     * @methodName select
     * @time 2022-08-31 10:59:01
     */
    @Methods(methodsName = "团队组别值集合分页查询", methods = "select")
    @ApiOperation(value = "团队组别值集合分页查询", httpMethod = "GET", notes = "团队组别值集合查询", response = Json.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "current", value = "当前页", dataType = "int", required = true),
            @ApiImplicitParam(name = "size", value = "分页显示数量", dataType = "int", required = true),
    })
    @GetMapping("/select")
    public Json<Page<TeamGroupValueVO>> select(@Validated(value = TeamGroupValueQuery.Select.class) TeamGroupValueQuery teamGroupValueQuery, Integer current, Integer size) {
        //返回内容
        return new Json(ReturnCode.成功, teamGroupValueService.pageEnhance(new Page(current, size), teamGroupValueQuery));
    }


    /**
     * TODO 集合
     *
     * @param teamGroupValueQuery 团队组别值
     * @return Json<List<TeamGroupValueVO>>
     * @author sunx
     * @methodName selectList
     * @time 2022-08-31 10:59:01
     */
    @Methods(methodsName = "团队组别值集合查询", methods = "selectList")
    @ApiOperation(value = "团队组别值集合查询", httpMethod = "GET", notes = "团队组别值集合查询", response = Json.class)
    @GetMapping("/selectList")
    public Json<List<TeamGroupValueVO>> selectList(@Validated(value = TeamGroupValueQuery.SelectList.class) TeamGroupValueQuery teamGroupValueQuery) {
        //返回内容
        return new Json(ReturnCode.成功, teamGroupValueService.listEnhance(teamGroupValueQuery));
    }


    /**
     * TODO 单条
     *
     * @param teamGroupValueQuery 团队组别值
     * @return Json<TeamGroupValueVO>
     * @author sunx
     * @methodName selectOne
     * @time 2022-08-31 10:59:01
     */
    @Methods(methodsName = "团队组别值单条查询", methods = "selectOne")
    @ApiOperation(value = "团队组别值单条查询", httpMethod = "GET", notes = "团队组别值单条查询", response = Json.class)
    @GetMapping("/selectOne")
    public Json<TeamGroupValueVO> selectOne(@Validated(value = TeamGroupValueQuery.SelectOne.class) TeamGroupValueQuery teamGroupValueQuery) {
        //返回内容
        return new Json(ReturnCode.成功, teamGroupValueService.getOneEnhance(teamGroupValueQuery));
    }


    /**
     * TODO 总数
     *
     * @param teamGroupValueQuery 团队组别值
     * @return Json<Integer>
     * @author sunx
     * @methodName count
     * @time 2022-08-31 10:59:01
     */
    @Methods(methodsName = "团队组别值总数查询", methods = "count")
    @ApiOperation(value = "团队组别值总数查询", httpMethod = "GET", notes = "团队组别值总数查询", response = Json.class)
    @GetMapping(value = "/count")
    public Json<Integer> count(@Validated(value = TeamGroupValueQuery.Count.class) TeamGroupValueQuery teamGroupValueQuery) {
        //返回内容
        return new Json(ReturnCode.成功, teamGroupValueService.countEnhance(teamGroupValueQuery));
    }


    /**
     * TODO 新增
     *
     * @param teamGroupValueBO 团队组别值
     * @return Json<String>
     * @author sunx
     * @methodName save
     * @time 2022-08-31 10:59:01
     */
    @PreventRepeat
    @Methods(methodsName = "团队组别值新增", methods = "save")
    @ApiOperation(value = "团队组别值新增", httpMethod = "POST", notes = "团队组别值新增", response = Json.class)
    @ApiOperationSupport(ignoreParameters = {"id", "createDateTime", "createName", "modifyDateTime", "modifyName", "isDelete", "version"})
    @PostMapping("/save")
    public Json<String> save(@Validated(value = TeamGroupValueBO.Save.class) TeamGroupValueBO teamGroupValueBO, HttpServletRequest httpServletRequest) {
        //缓存取出用户
        Map<String, Object> u = RedisUtils.getToken(stringRedisTemplate, httpServletRequest);
        if (u != null) {
            teamGroupValueBO.setCreateName(u.get("name") + "-" + u.get("id"));
        }
        //返回内容
        return new Json(ReturnCode.成功, teamGroupValueService.saveEnhance(teamGroupValueBO));
    }


    /**
     * TODO 修改
     *
     * @param teamGroupValueBO 团队组别值
     * @return Json<Boolean>
     * @author sunx
     * @methodName update
     * @time 2022-08-31 10:59:01
     */
    @PreventRepeat
    @Methods(methodsName = "团队组别值修改", methods = "update")
    @ApiOperation(value = "团队组别值修改", httpMethod = "PUT", notes = "团队组别值修改", response = Json.class)
    @ApiOperationSupport(ignoreParameters = {"createDateTime", "createName", "modifyDateTime", "modifyName", "isDelete", "version"})
    @PutMapping("/update")
    public Json<Boolean> update(@Validated(value = TeamGroupValueBO.Update.class) TeamGroupValueBO teamGroupValueBO, HttpServletRequest httpServletRequest) {
        //缓存取出用户
        Map<String, Object> u = RedisUtils.getToken(stringRedisTemplate, httpServletRequest);
        if (u != null) {
            teamGroupValueBO.setModifyName(u.get("name") + "-" + u.get("id"));
        }
        //返回内容
        return new Json(ReturnCode.成功, teamGroupValueService.updateEnhance(teamGroupValueBO));
    }


    /**
     * TODO 删除
     *
     * @param teamGroupValueBO 团队组别值
     * @return Json<Boolean>
     * @author sunx
     * @methodName remove
     * @time 2022-08-31 10:59:01
     */
    @Methods(methodsName = "团队组别值删除", methods = "remove")
    @ApiOperation(value = "团队组别值删除", httpMethod = "DELETE", notes = "团队组别值删除", response = Json.class)
    @ApiOperationSupport(includeParameters = {"id"})
    @DeleteMapping("/remove")
    public Json<Boolean> remove(@Validated(value = TeamGroupValueBO.Remove.class) TeamGroupValueBO teamGroupValueBO) {
        return new Json(ReturnCode.成功, teamGroupValueService.removeEnhance(teamGroupValueBO));
    }
}