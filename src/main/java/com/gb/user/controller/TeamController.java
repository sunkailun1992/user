package com.gb.user.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gb.user.entity.TeamTreeNode;
import com.gb.user.entity.bo.TeamBO;
import com.gb.user.entity.query.TeamQuery;
import com.gb.user.entity.vo.TeamVO;
import com.gb.user.service.TeamService;
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
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.Objects;


/**
 * TODO 团队，Comment请求层
 * 代码生成器
 *
 * @author sunx
 * @className TeamController
 * @time 2022-08-30 04:44:17
 */
@Slf4j
@RequestRequired
@ApiSupport(author = "sunx")
@Setter(onMethod_ = {@Autowired})
@RestController
@RequestMapping("/team")
@Api(tags = "团队")
public class TeamController {


    /**
     * 团队
     */
    private TeamService teamService;
    /**
     * redis操作
     */
    private StringRedisTemplate stringRedisTemplate;


    /**
     * TODO 分页
     *
     * @param teamQuery 团队
     * @param current
     * @param size
     * @return Json<Page<TeamVO>>
     * @author sunx
     * @methodName select
     * @time 2022-08-30 04:44:17
     */
    @Methods(methodsName = "团队集合分页查询", methods = "select")
    @ApiOperation(value = "团队集合分页查询", httpMethod = "GET", notes = "团队集合查询", response = Json.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "current", value = "当前页", dataType = "int", required = true),
            @ApiImplicitParam(name = "size", value = "分页显示数量", dataType = "int", required = true),
    })
    @GetMapping("/select")
    public Json<Page<TeamVO>> select(@Validated(value = TeamQuery.Select.class) TeamQuery teamQuery, Integer current, Integer size) {
        if(Objects.isNull(current) || Objects.isNull(size)) {
            return new Json(ReturnCode.请求必填参数为空, "缺少分页必要参数！");
        }
        //返回内容
        return new Json(ReturnCode.成功, teamService.pageEnhance(new Page(current, size), teamQuery));
    }


    /**
     * TODO 集合
     *
     * @param teamQuery 团队
     * @return Json<List<TeamVO>>
     * @author sunx
     * @methodName selectList
     * @time 2022-08-30 04:44:17
     */
    @Methods(methodsName = "团队集合查询", methods = "selectList")
    @ApiOperation(value = "团队集合查询", httpMethod = "GET", notes = "团队集合查询", response = Json.class)
    @GetMapping("/selectList")
    public Json<List<TeamVO>> selectList(@Validated(value = TeamQuery.SelectList.class) TeamQuery teamQuery) {
        //返回内容
        return new Json(ReturnCode.成功, teamService.listEnhance(teamQuery));
    }


    /**
     * TODO 权限团队集合查询
     *
     * @param authUserId 权限用户id
     * @param nameQuery 团队名称模糊查询
     * @param teamLevelQuery 团队本级及下级查询
     * @return Json<List<TeamVO>>
     * @author sunx
     * @methodName teamAuthSelectList
     * @time 2022-08-30 04:44:17
     */
    @Methods(methodsName = "权限团队集合查询", methods = "teamAuthSelect")
    @ApiOperation(value = "权限团队集合查询", httpMethod = "GET", notes = "权限团队集合查询", response = Json.class)
    @GetMapping("/teamAuthSelectList")
    public Json<List<TeamVO>> teamAuthSelect(String authUserId, String nameQuery, Boolean teamLevelQuery) {
        if(StringUtils.isBlank(authUserId)) {
            return new Json(ReturnCode.请求必填参数为空, "authUserId不能为空！");
        }
        //返回内容
        return new Json(ReturnCode.成功, teamService.teamAuthSelect(authUserId, nameQuery, teamLevelQuery));
    }


    /**
     * TODO 单条
     *
     * @param teamQuery 团队
     * @return Json<TeamVO>
     * @author sunx
     * @methodName selectOne
     * @time 2022-08-30 04:44:17
     */
    @Methods(methodsName = "团队单条查询", methods = "selectOne")
    @ApiOperation(value = "团队单条查询", httpMethod = "GET", notes = "团队单条查询", response = Json.class)
    @GetMapping("/selectOne")
    public Json<TeamVO> selectOne(@Validated(value = TeamQuery.SelectOne.class) TeamQuery teamQuery) {
        //返回内容
        return new Json(ReturnCode.成功, teamService.getOneEnhance(teamQuery));
    }


    /**
     * TODO 总数
     *
     * @param teamQuery 团队
     * @return Json<Integer>
     * @author sunx
     * @methodName count
     * @time 2022-08-30 04:44:17
     */
    @Methods(methodsName = "团队总数查询", methods = "count")
    @ApiOperation(value = "团队总数查询", httpMethod = "GET", notes = "团队总数查询", response = Json.class)
    @GetMapping(value = "/count")
    public Json<Integer> count(@Validated(value = TeamQuery.Count.class) TeamQuery teamQuery) {
        //返回内容
        return new Json(ReturnCode.成功, teamService.countEnhance(teamQuery));
    }


    /**
     * TODO 新增
     *
     * @param teamBO 团队
     * @return Json<String>
     * @author sunx
     * @methodName save
     * @time 2022-08-30 04:44:17
     */
    @PreventRepeat
    @Methods(methodsName = "团队新增", methods = "save")
    @ApiOperation(value = "团队新增", httpMethod = "POST", notes = "团队新增", response = Json.class)
    @ApiOperationSupport(ignoreParameters = {"id", "createDateTime", "createName", "modifyDateTime", "modifyName", "isDelete", "version"})
    @PostMapping("/save")
    public Json<String> save(@Validated(value = TeamBO.Save.class) TeamBO teamBO, HttpServletRequest httpServletRequest) {
        //缓存取出用户
        Map<String, Object> u = RedisUtils.getToken(stringRedisTemplate, httpServletRequest);
        if (u != null) {
            teamBO.setCreateName(u.get("name") + "-" + u.get("id"));
        }
        //返回内容
        return new Json(ReturnCode.成功, teamService.saveEnhance(teamBO));
    }


    /**
     * TODO 修改
     *
     * @param teamBO 团队
     * @return Json<Boolean>
     * @author sunx
     * @methodName update
     * @time 2022-08-30 04:44:17
     */
    @PreventRepeat
    @Methods(methodsName = "团队修改", methods = "update")
    @ApiOperation(value = "团队修改", httpMethod = "PUT", notes = "团队修改", response = Json.class)
    @ApiOperationSupport(ignoreParameters = {"createDateTime", "createName", "modifyDateTime", "modifyName", "isDelete", "version"})
    @PutMapping("/update")
    public Json<Boolean> update(@Validated(value = TeamBO.Update.class) TeamBO teamBO, HttpServletRequest httpServletRequest) {
        //缓存取出用户
        Map<String, Object> u = RedisUtils.getToken(stringRedisTemplate, httpServletRequest);
        if (u != null) {
            teamBO.setModifyName(u.get("name") + "-" + u.get("id"));
        }
        //返回内容
        return new Json(ReturnCode.成功, teamService.updateEnhance(teamBO));
    }


    /**
     * TODO 删除
     *
     * @param teamBO 团队
     * @return Json<Boolean>
     * @author sunx
     * @methodName remove
     * @time 2022-08-30 04:44:17
     */
    @Methods(methodsName = "团队删除", methods = "remove")
    @ApiOperation(value = "团队删除", httpMethod = "DELETE", notes = "团队删除", response = Json.class)
    @ApiOperationSupport(includeParameters = {"id"})
    @DeleteMapping("/remove")
    public Json<Boolean> remove(@Validated(value = TeamBO.Remove.class) TeamBO teamBO) {
        return new Json(ReturnCode.成功, teamService.removeEnhance(teamBO));
    }


    /**
     * TODO 团队树
     *
     * @param advanceSelected 团队树查询
     * @param teamId 团队ID
     * @return Json<List<TreeNode>>
     * @author sunx
     * @methodName teamTree
     * @time 2022-08-30 04:44:17
     */
    @Methods(methodsName = "团队树", methods = "teamTree")
    @ApiOperation(value = "团队树", httpMethod = "GET", notes = "团队树", response = Json.class)
    @GetMapping("/teamTree")
    public Json<List<TeamTreeNode>> teamTree(Boolean advanceSelected, String teamId) {
        //返回内容
        return new Json(ReturnCode.成功, teamService.teamTree(advanceSelected, teamId));
    }


    /**
     * TODO 父级团队查询
     *
     * @param teamId 团队ID
     * @return TeamTreeNode
     * @author sunx
     * @methodName queryParentTeam
     * @time 2022-08-30 04:44:17
     */
    @Methods(methodsName = "父级团队查询", methods = "queryParentTeam")
    @ApiOperation(value = "父级团队查询", httpMethod = "GET", notes = "父级团队查询", response = Json.class)
    @GetMapping("/queryParentTeam")
    public Json<TeamTreeNode> queryParentTeam(String teamId) {
        if(StringUtils.isBlank(teamId)) {
            return new Json(ReturnCode.请求必填参数为空, "缺少查询必要参数！");
        }
        //返回内容
        return new Json(ReturnCode.成功, teamService.queryParentTeam(teamId));
    }
}