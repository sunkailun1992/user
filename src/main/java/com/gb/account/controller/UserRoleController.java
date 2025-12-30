package com.gb.account.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gb.account.entity.bo.UserRoleBO;
import com.gb.account.entity.query.UserRoleQuery;
import com.gb.account.entity.vo.UserRoleVO;
import com.gb.account.service.UserRoleService;
import com.gb.user.service.UserAgentCertificationService;
import com.gb.utils.Json;
import com.gb.utils.RedisUtils;
import com.gb.utils.annotations.Methods;
import com.gb.utils.annotations.PreventRepeat;
import com.gb.utils.annotations.RequestRequired;
import com.gb.utils.enumeration.ReturnCode;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import com.google.common.collect.Lists;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;


/**
 * Created with IntelliJ IDEA.
 *
 * @author: 孙凯伦
 * @since: 2021-10-21 01:50:37
 * @description: TODO 用户角色表，Comment请求层
 * @source: 代码生成器
 */
@Slf4j
@RequestRequired
@ApiSupport(author = "孙凯伦")
@Setter(onMethod_ = {@Autowired})
@RestController
@RequestMapping("/userRole")
@Api(tags = "用户角色表")
public class UserRoleController {


    /**
     * 用户角色表
     */
    private UserRoleService userRoleService;

    private UserAgentCertificationService userAgentCertificationService;

    /**
     * redis操作
     */
    private StringRedisTemplate stringRedisTemplate;


    /**
     * 用户角色表集合分页查询
     *
     * @param current:
     * @param size:
     * @param userRoleQuery:
     * @return com.utils.Json
     * @author 孙凯伦
     * @since 2021-10-21 01:50:37
     */
    @Methods(methodsName = "用户角色表集合分页查询", methods = "select")
    @ApiOperation(value = "用户角色表集合分页查询", httpMethod = "GET", notes = "用户角色表集合查询", response = Json.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "current", value = "当前页", dataType = "int", required = true),
            @ApiImplicitParam(name = "size", value = "分页显示数量", dataType = "int", required = true),
    })
    @GetMapping("/select")
    public Json<Page<UserRoleVO>> select(@Validated(value = UserRoleQuery.Select.class) UserRoleQuery userRoleQuery, Integer current, Integer size) {
        //返回内容
        return new Json(ReturnCode.成功, userRoleService.pageEnhance(new Page(current, size), userRoleQuery));
    }


    /**
     * 用户角色表集合查询
     *
     * @param userRoleQuery:
     * @return com.utils.Json
     * @author 孙凯伦
     * @since 2021-10-21 01:50:37
     */
    @Methods(methodsName = "用户角色表集合查询", methods = "selectList")
    @ApiOperation(value = "用户角色表集合查询", httpMethod = "GET", notes = "用户角色表集合查询", response = Json.class)
    @GetMapping("/selectList")
    public Json<List<UserRoleVO>> selectList(@Validated(value = UserRoleQuery.SelectList.class) UserRoleQuery userRoleQuery) {
        //返回内容
        return new Json(ReturnCode.成功, userRoleService.listEnhance(userRoleQuery));
    }


    /**
     * 用户角色表单条查询
     *
     * @param userRoleQuery:
     * @return com.utils.Json
     * @author 孙凯伦
     * @since 2021-10-21 01:50:37
     */
    @Methods(methodsName = "用户角色表单条查询", methods = "selectOne")
    @ApiOperation(value = "用户角色表单条查询", httpMethod = "GET", notes = "用户角色表单条查询", response = Json.class)
    @GetMapping("/selectOne")
    public Json<UserRoleVO> selectOne(@Validated(value = UserRoleQuery.SelectOne.class) UserRoleQuery userRoleQuery) {
        //返回内容
        return new Json(ReturnCode.成功, userRoleService.getOneEnhance(userRoleQuery));
    }


    /**
     * 用户角色表总数查询
     *
     * @param userRoleQuery:
     * @return com.utils.Json
     * @author 孙凯伦
     * @since 2021-10-21 01:50:37
     */
    @Methods(methodsName = "用户角色表总数查询", methods = "count")
    @ApiOperation(value = "用户角色表总数查询", httpMethod = "GET", notes = "用户角色表总数查询", response = Json.class)
    @GetMapping(value = "/count")
    public Json<Integer> count(@Validated(value = UserRoleQuery.Count.class) UserRoleQuery userRoleQuery) {
        //返回内容
        return new Json(ReturnCode.成功, userRoleService.countEnhance(userRoleQuery));
    }


    /**
     * 用户角色表新增
     *
     * @param userRoleBO:
     * @return com.utils.Json
     * @author 孙凯伦
     * @since 2021-10-21 01:50:37
     */
    @PreventRepeat
    @Methods(methodsName = "用户角色表新增", methods = "save")
    @ApiOperation(value = "用户角色表新增", httpMethod = "POST", notes = "用户角色表新增", response = Json.class)
    @ApiOperationSupport(ignoreParameters = {"id", "createDateTime", "createName", "modifyDateTime", "modifyName", "isDelete", "version"})
    @PostMapping("/save")
    public Json<String> save(@Validated(value = UserRoleBO.Save.class) UserRoleBO userRoleBO, HttpServletRequest httpServletRequest) {
        if(StringUtils.isBlank(userRoleBO.getRoleId()) && StringUtils.isBlank(userRoleBO.getRoleCode()) && CollectionUtils.isEmpty(userRoleBO.getRoleIdList())) {
            //返回内容
            return new Json(ReturnCode.请求必填参数为空, "缺少用户角色新增必要参数！");
        }
        //缓存取出用户
        Map<String, Object> u = RedisUtils.getToken(stringRedisTemplate, httpServletRequest);
        if (u != null) {
            userRoleBO.setCreateName(u.get("name") + "-" + u.get("id"));
        }
        //返回内容
        return new Json(ReturnCode.成功, userRoleService.saveEnhance(userRoleBO));
    }


    /**
     * 用户组新增
     *
     * @param userId:
     * @param roleId:
     * @return com.utils.Json
     * @author 孙凯伦
     * @since 2021-10-21 01:50:39
     */
    @PreventRepeat
    @Methods(methodsName = "用户组批量新增", methods = "saveEnhanceBatch")
    @ApiOperation(value = "用户组批量新增", httpMethod = "POST", notes = "用户组批量新增", response = Json.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户id", dataType = "String", required = true),
            @ApiImplicitParam(name = "roleId", value = "角色id", dataType = "String", required = true),
    })
    @PostMapping("/saveEnhanceBatch")
    public Json saveEnhanceBatch(String[] userId, String roleId, HttpServletRequest httpServletRequest) {
        //缓存取出用户
        Map<String, Object> u = RedisUtils.getToken(stringRedisTemplate, httpServletRequest);
        String createName = u.get("name") + "-" + u.get("id");
        //批量插入集合
        List<UserRoleBO> list = Lists.newArrayList();
        for (String id : userId) {
            //判断是否存在
            Long i = userRoleService.countEnhance(new UserRoleQuery() {{
                setUserId(id);
                setRoleId(roleId);
            }});
            //不存在放入批量插入
            if (i <= 0) {
                list.add(new UserRoleBO() {{
                    setUserId(id);
                    setRoleId(roleId);
                    if (u != null) {
                        setCreateName(createName);
                    }
                }});
            }
        }
        //批量插入
        if (list.size() > 0) {
            userRoleService.saveBatchEnhance(list);
            userAgentCertificationService.saveBatchEnhance(createName, roleId,  Lists.newArrayList(userId));
        }

        //返回内容
        return new Json(ReturnCode.成功);
    }


    /**
     * 用户角色表修改
     *
     * @param userRoleBO:
     * @return com.utils.Json
     * @author 孙凯伦
     * @since 2021-10-21 01:50:37
     */
    @PreventRepeat
    @Methods(methodsName = "用户角色表修改", methods = "update")
    @ApiOperation(value = "用户角色表修改", httpMethod = "PUT", notes = "用户角色表修改", response = Json.class)
    @ApiOperationSupport(ignoreParameters = {"createDateTime", "createName", "modifyDateTime", "modifyName", "isDelete", "version"})
    @PutMapping("/update")
    public Json<Boolean> update(@Validated(value = UserRoleBO.Update.class) UserRoleBO userRoleBO, HttpServletRequest httpServletRequest) {
        //缓存取出用户
        Map<String, Object> u = RedisUtils.getToken(stringRedisTemplate, httpServletRequest);
        if (u != null) {
            userRoleBO.setModifyName(u.get("name") + "-" + u.get("id"));
        }
        //返回内容
        return new Json(ReturnCode.成功, userRoleService.updateEnhance(userRoleBO));
    }


    /**
     * 用户角色表删除
     *
     * @param userRoleBO:
     * @return com.utils.Json
     * @author 孙凯伦
     * @since 2021-10-21 01:50:37
     */
    @Methods(methodsName = "用户角色表删除", methods = "remove")
    @ApiOperation(value = "用户角色表删除", httpMethod = "DELETE", notes = "用户角色表删除", response = Json.class)
    @ApiOperationSupport(includeParameters = {"id"})
    @DeleteMapping("/remove")
    public Json<Boolean> remove(@Validated(value = UserRoleBO.Remove.class) UserRoleBO userRoleBO) {
        return new Json(ReturnCode.成功, userRoleService.removeEnhance(userRoleBO));
    }


}