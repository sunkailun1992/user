package com.gb.user.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gb.user.entity.bo.TeamUserBO;
import com.gb.user.entity.query.TeamAuthBrokerQuery;
import com.gb.user.entity.query.TeamUserQuery;
import com.gb.user.entity.vo.TeamUserPolicyVO;
import com.gb.user.entity.vo.TeamUserVO;
import com.gb.user.service.TeamUserService;
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
import org.apache.commons.compress.utils.Lists;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.Objects;


/**
 * TODO 团队人员，Comment请求层
 * 代码生成器
 *
 * @author sunx
 * @className TeamUserController
 * @time 2022-08-31 11:01:59
 */
@Slf4j
@RequestRequired
@ApiSupport(author = "sunx")
@Setter(onMethod_ = {@Autowired})
@RestController
@RequestMapping("/team-user")
@Api(tags = "团队人员")
public class TeamUserController {


    /**
     * 团队人员
     */
    private TeamUserService teamUserService;
    /**
     * redis操作
     */
    private StringRedisTemplate stringRedisTemplate;


    /**
     * TODO 分页
     *
     * @param teamUserQuery 团队人员
     * @param current
     * @param size
     * @return Json<Page<TeamUserVO>>
     * @author sunx
     * @methodName select
     * @time 2022-08-31 11:01:59
     */
    @Methods(methodsName = "团队人员集合分页查询", methods = "select")
    @ApiOperation(value = "团队人员集合分页查询", httpMethod = "GET", notes = "团队人员集合查询", response = Json.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "current", value = "当前页", dataType = "int", required = true),
            @ApiImplicitParam(name = "size", value = "分页显示数量", dataType = "int", required = true),
    })
    @GetMapping("/select")
    public Json<Page<TeamUserVO>> select(@Validated(value = TeamUserQuery.Select.class) TeamUserQuery teamUserQuery, Integer current, Integer size) {
        if(Objects.isNull(current) || Objects.isNull(size)) {
            return new Json(ReturnCode.请求必填参数为空, "缺少分页必要参数！");
        }
        //返回内容
        return new Json(ReturnCode.成功, teamUserService.pageEnhance(new Page(current, size), teamUserQuery));
    }


    /**
     * TODO 集合
     *
     * @param teamUserQuery 团队人员
     * @return Json<List<TeamUserVO>>
     * @author sunx
     * @methodName selectList
     * @time 2022-08-31 11:01:59
     */
    @Methods(methodsName = "团队人员集合查询", methods = "selectList")
    @ApiOperation(value = "团队人员集合查询", httpMethod = "GET", notes = "团队人员集合查询", response = Json.class)
    @GetMapping("/selectList")
    public Json<Object> selectList(@Validated(value = TeamUserQuery.SelectList.class) TeamUserQuery teamUserQuery) {
        //返回内容
        return new Json(ReturnCode.成功, teamUserService.listEnhance(teamUserQuery));
    }


    /**
     * TODO 团队人员列表集合查询
     *
     * @param userIdList 团队人员列表
     * @return Json<List<TeamUserVO>>
     * @author sunx
     * @methodName selectListByUserIdList
     * @time 2022-08-31 11:01:59
     */
    @Methods(methodsName = "团队人员列表集合查询", methods = "selectListByUserIdList")
    @ApiOperation(value = "团队人员列表集合查询", httpMethod = "POST", notes = "团队人员列表集合查询", response = Json.class)
    @PostMapping("/selectListByUserIdList")
    public Json<List<TeamUserVO>> selectListByUserIdList(@RequestBody List<String> userIdList) {
        if(CollectionUtils.isEmpty(userIdList)) {
            return new Json(ReturnCode.成功, Lists.newArrayList());
        }
        //返回内容
        return new Json(ReturnCode.成功, teamUserService.listEnhance(new TeamUserQuery(){{
            setUserIdList(userIdList);
            setAssignment(false);
            setBackMapQueryResult(false);
        }}));
    }


    /**
     * TODO 单条
     *
     * @param teamUserQuery 团队人员
     * @return Json<TeamUserVO>
     * @author sunx
     * @methodName selectOne
     * @time 2022-08-31 11:01:59
     */
    @Methods(methodsName = "团队人员单条查询", methods = "selectOne")
    @ApiOperation(value = "团队人员单条查询", httpMethod = "GET", notes = "团队人员单条查询", response = Json.class)
    @GetMapping("/selectOne")
    public Json<TeamUserVO> selectOne(@Validated(value = TeamUserQuery.SelectOne.class) TeamUserQuery teamUserQuery) {
        if(StringUtils.isBlank(teamUserQuery.getUserId()) && StringUtils.isBlank(teamUserQuery.getId())) {
            return new Json(ReturnCode.请求必填参数为空, "缺少必要参数！");
        }
        //返回内容
        return new Json(ReturnCode.成功, teamUserService.getOneEnhance(teamUserQuery));
    }


    /**
     * TODO 总数
     *
     * @param teamUserQuery 团队人员
     * @return Json<Integer>
     * @author sunx
     * @methodName count
     * @time 2022-08-31 11:01:59
     */
    @Methods(methodsName = "团队人员总数查询", methods = "count")
    @ApiOperation(value = "团队人员总数查询", httpMethod = "GET", notes = "团队人员总数查询", response = Json.class)
    @GetMapping(value = "/count")
    public Json<Integer> count(@Validated(value = TeamUserQuery.Count.class) TeamUserQuery teamUserQuery) {
        //返回内容
        return new Json(ReturnCode.成功, teamUserService.countEnhance(teamUserQuery));
    }


    /**
     * TODO 新增
     *
     * @param teamUserBO 团队人员
     * @return Json<String>
     * @author sunx
     * @methodName save
     * @time 2022-08-31 11:01:59
     */
    @PreventRepeat
    @Methods(methodsName = "团队人员新增", methods = "save")
    @ApiOperation(value = "团队人员新增", httpMethod = "POST", notes = "团队人员新增", response = Json.class)
    @ApiOperationSupport(ignoreParameters = {"id", "createDateTime", "createName", "modifyDateTime", "modifyName", "isDelete", "version"})
    @PostMapping("/save")
    public Json<String> save(@Validated(value = TeamUserBO.Save.class) TeamUserBO teamUserBO, HttpServletRequest httpServletRequest) {
        //缓存取出用户
        Map<String, Object> u = RedisUtils.getToken(stringRedisTemplate, httpServletRequest);
        if (u != null) {
            teamUserBO.setCreateName(u.get("name") + "-" + u.get("id"));
        }
        //返回内容
        return new Json(ReturnCode.成功, teamUserService.saveEnhance(teamUserBO));
    }


    /**
     * TODO 修改
     *
     * @param teamUserBO 团队人员
     * @return Json<Boolean>
     * @author sunx
     * @methodName update
     * @time 2022-08-31 11:01:59
     */
    @PreventRepeat
    @Methods(methodsName = "团队人员修改", methods = "update")
    @ApiOperation(value = "团队人员修改", httpMethod = "PUT", notes = "团队人员修改", response = Json.class)
    @ApiOperationSupport(ignoreParameters = {"createDateTime", "createName", "modifyDateTime", "modifyName", "isDelete", "version"})
    @PutMapping("/update")
    public Json<Boolean> update(@Validated(value = TeamUserBO.Update.class) TeamUserBO teamUserBO, HttpServletRequest httpServletRequest) {
        //缓存取出用户
        Map<String, Object> u = RedisUtils.getToken(stringRedisTemplate, httpServletRequest);
        if (u != null) {
            teamUserBO.setModifyName(u.get("name") + "-" + u.get("id"));
        }
        teamUserService.updateEnhance(teamUserBO);
        //返回内容
        return new Json(ReturnCode.成功, true);
    }


    /**
     * TODO 删除
     *
     * @param teamUserBO 团队人员
     * @return Json<Boolean>
     * @author sunx
     * @methodName remove
     * @time 2022-08-31 11:01:59
     */
    @Methods(methodsName = "团队人员删除", methods = "remove")
    @ApiOperation(value = "团队人员删除", httpMethod = "DELETE", notes = "团队人员删除", response = Json.class)
    @ApiOperationSupport(includeParameters = {"id"})
    @DeleteMapping("/remove")
    public Json<Boolean> remove(@Validated(value = TeamUserBO.Remove.class) TeamUserBO teamUserBO) {
        return new Json(ReturnCode.成功, teamUserService.removeEnhance(teamUserBO));
    }


    /**
     * TODO 团队权限经纪人分页查询
     *
     * @param teamAuthBrokerQuery 团队权限经纪人分页查询参数
     * @param current 当前页
     * @param size 每页显示数量
     * @return Json<Page<TeamUserPolicyVO>>
     * @author sunx
     * @methodName teamAuthBrokerSelect
     * @time 2022-08-31 11:01:59
     */
    @Methods(methodsName = "团队权限经纪人分页查询", methods = "teamAuthBrokerSelect")
    @ApiOperation(value = "团队权限经纪人分页查询", httpMethod = "GET", notes = "团队权限经纪人分页查询", response = Json.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "current", value = "当前页", dataType = "int", required = true),
            @ApiImplicitParam(name = "size", value = "分页显示数量", dataType = "int", required = true),
    })
    @GetMapping("/teamAuthBrokerSelect")
    public Json<Page<TeamUserPolicyVO>> teamAuthBrokerSelect(@Validated(value = TeamAuthBrokerQuery.Select.class) TeamAuthBrokerQuery teamAuthBrokerQuery, Integer current, Integer size) {
        if(Objects.isNull(current) || Objects.isNull(size)) {
            return new Json(ReturnCode.请求必填参数为空, "缺少分页必要参数！");
        }
        if(Objects.nonNull(teamAuthBrokerQuery.getTeamLevelQuery()) && teamAuthBrokerQuery.getTeamLevelQuery() && StringUtils.isBlank(teamAuthBrokerQuery.getTeamId())) {
            return new Json(ReturnCode.请求必填参数为空, "缺少必填参数！");
        }
        if(StringUtils.isBlank(teamAuthBrokerQuery.getAuthUserId())) {
            log.debug("teamAuthBrokerSelect--请求参数：authUserId为空！");
            return new Json(ReturnCode.成功, null);
        }
        //返回内容
        return new Json(ReturnCode.成功, teamUserService.teamAuthBrokerSelect(new Page(current, size), teamAuthBrokerQuery));
    }


    /**
     * TODO 团队权限经纪人列表查询
     *
     * @param teamAuthBrokerQuery 团队权限经纪人列表查询参数
     * @return Json<List<TeamUserVO>>
     * @author sunx
     * @methodName teamAuthBrokerSelectList
     * @time 2022-08-31 11:01:59
     */
    @Methods(methodsName = "团队权限经纪人列表查询", methods = "teamAuthBrokerSelectList")
    @ApiOperation(value = "团队权限经纪人列表查询", httpMethod = "GET", notes = "团队权限经纪人列表查询", response = Json.class)
    @GetMapping("/teamAuthBrokerSelectList")
    public Json<List<TeamUserVO>> teamAuthBrokerSelectList(TeamAuthBrokerQuery teamAuthBrokerQuery) {
        if(Objects.nonNull(teamAuthBrokerQuery.getTeamLevelQuery()) && teamAuthBrokerQuery.getTeamLevelQuery() && StringUtils.isBlank(teamAuthBrokerQuery.getTeamId())) {
            return new Json(ReturnCode.请求必填参数为空, "缺少必填参数！");
        }
        if(StringUtils.isBlank(teamAuthBrokerQuery.getAuthUserId())) {
            log.debug("teamAuthBrokerSelect--请求参数：authUserId为空！");
            return new Json(ReturnCode.成功, null);
        }
        //返回内容
        return new Json(ReturnCode.成功, teamUserService.teamAuthBrokerSelectList(teamAuthBrokerQuery));
    }


    /**
     * TODO 团队权限经纪人统计查询
     *
     * @param teamAuthBrokerQuery 团队权限经纪人统计查询参数
     * @return Json<Map<String, Object>>
     * @author sunx
     * @methodName queryAuthBrokerCount
     * @time 2022-08-31 11:01:59
     */
    @Methods(methodsName = "团队权限经纪人统计查询", methods = "queryAuthBrokerCount")
    @ApiOperation(value = "团队权限经纪人统计查询", httpMethod = "GET", notes = "团队权限经纪人统计查询", response = Json.class)
    @GetMapping("/queryAuthBrokerCount")
    public Json<Map<String, Object>> queryAuthBrokerCount(@Validated(value = TeamAuthBrokerQuery.Select.class) TeamAuthBrokerQuery teamAuthBrokerQuery) {
        if(Objects.nonNull(teamAuthBrokerQuery.getTeamLevelQuery()) && teamAuthBrokerQuery.getTeamLevelQuery() && StringUtils.isBlank(teamAuthBrokerQuery.getTeamId())) {
            return new Json(ReturnCode.请求必填参数为空, "缺少必填参数！");
        }
        if(StringUtils.isBlank(teamAuthBrokerQuery.getAuthUserId())) {
            log.debug("teamAuthBrokerSelect--请求参数：authUserId为空！");
            return new Json(ReturnCode.成功, null);
        }
        //返回内容
        return new Json(ReturnCode.成功, teamUserService.queryAuthBrokerCount(teamAuthBrokerQuery));
    }
}