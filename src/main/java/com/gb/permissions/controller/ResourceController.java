package com.gb.permissions.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gb.permissions.entity.TreeNode;
import com.gb.permissions.entity.bo.ResourceBO;
import com.gb.permissions.entity.query.ResourceQuery;
import com.gb.permissions.entity.vo.ResourceVO;
import com.gb.permissions.service.ResourceService;
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
 * Created with IntelliJ IDEA.
 *
 * @author: 孙凯伦
 * @since: 2021-10-21 01:59:45
 * @description: TODO 资源表，Comment请求层
 * @source: 代码生成器
 */
@Slf4j
@RequestRequired
@ApiSupport(author = "孙凯伦")
@Setter(onMethod_ = {@Autowired})
@RestController
@RequestMapping("/resource")
@Api(tags = "资源表")
public class ResourceController {


    /**
     * 资源表
     */
    private ResourceService resourceService;
    /**
     * redis操作
     */
    private StringRedisTemplate stringRedisTemplate;


    /**
     * @param userId
     * @param advanceSelected
     * @param roleId
     * @param systemId
     * @auther: 孙凯伦
     * @mobile: 13777579028
     * @email: 376253703@qq.com
     * @name: roleResource
     * @description: TODO  角色资源
     * @return: java.util.List<com.gb.permissions.entity.TreeNode>
     * @date: 2021/10/26 4:46 下午
     */
    @ApiOperation(value = "角色资源", httpMethod = "POST", notes = "角色资源", response = Json.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户id", dataType = "String", required = true),
            @ApiImplicitParam(name = "advanceSelected", value = "是否预选", dataType = "Boolean", required = true),
            @ApiImplicitParam(name = "roleId", value = "角色id", dataType = "String", required = true),
            @ApiImplicitParam(name = "systemId", value = "系统编码", dataType = "String", required = true),
            @ApiImplicitParam(name = "api", value = "接口", dataType = "Boolean", required = true),
    })
    @PostMapping("/roleResource")
    public Json<List<TreeNode>> roleResource(String userId, Boolean advanceSelected, String roleId, String systemId, Boolean api) {
        //返回内容
        return new Json(ReturnCode.成功, resourceService.roleResource(userId, advanceSelected, roleId, systemId, api));
    }


    /**
     * 资源表集合分页查询
     *
     * @param current:
     * @param size:
     * @param resourceQuery:
     * @return com.utils.Json
     * @author 孙凯伦
     * @since 2021-10-21 01:59:45
     */
    @Methods(methodsName = "资源表集合分页查询", methods = "select")
    @ApiOperation(value = "资源表集合分页查询", httpMethod = "GET", notes = "资源表集合查询", response = Json.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "current", value = "当前页", dataType = "int", required = true),
            @ApiImplicitParam(name = "size", value = "分页显示数量", dataType = "int", required = true),
    })
    @GetMapping("/select")
    public Json<Page<ResourceVO>> select(@Validated(value = ResourceQuery.Select.class) ResourceQuery resourceQuery, Integer current, Integer size) {
        //返回内容
        return new Json(ReturnCode.成功, resourceService.pageEnhance(new Page(current, size), resourceQuery));
    }


    /**
     * 资源表集合查询
     *
     * @param resourceQuery:
     * @return com.utils.Json
     * @author 孙凯伦
     * @since 2021-10-21 01:59:45
     */
    @Methods(methodsName = "资源表集合查询", methods = "selectList")
    @ApiOperation(value = "资源表集合查询", httpMethod = "GET", notes = "资源表集合查询", response = Json.class)
    @GetMapping("/selectList")
    public Json<List<ResourceVO>> selectList(@Validated(value = ResourceQuery.SelectList.class) ResourceQuery resourceQuery) {
        //返回内容
        return new Json(ReturnCode.成功, resourceService.listEnhance(resourceQuery));
    }


    /**
     * 资源表单条查询
     *
     * @param resourceQuery:
     * @return com.utils.Json
     * @author 孙凯伦
     * @since 2021-10-21 01:59:45
     */
    @Methods(methodsName = "资源表单条查询", methods = "selectOne")
    @ApiOperation(value = "资源表单条查询", httpMethod = "GET", notes = "资源表单条查询", response = Json.class)
    @GetMapping("/selectOne")
    public Json<ResourceVO> selectOne(@Validated(value = ResourceQuery.SelectOne.class) ResourceQuery resourceQuery) {
        //返回内容
        return new Json(ReturnCode.成功, resourceService.getOneEnhance(resourceQuery));
    }


    /**
     * 资源表总数查询
     *
     * @param resourceQuery:
     * @return com.utils.Json
     * @author 孙凯伦
     * @since 2021-10-21 01:59:45
     */
    @Methods(methodsName = "资源表总数查询", methods = "count")
    @ApiOperation(value = "资源表总数查询", httpMethod = "GET", notes = "资源表总数查询", response = Json.class)
    @GetMapping(value = "/count")
    public Json<Integer> count(@Validated(value = ResourceQuery.Count.class) ResourceQuery resourceQuery) {
        //返回内容
        return new Json(ReturnCode.成功, resourceService.countEnhance(resourceQuery));
    }


    /**
     * 资源表新增
     *
     * @param resourceBO:
     * @return com.utils.Json
     * @author 孙凯伦
     * @since 2021-10-21 01:59:45
     */
    @PreventRepeat
    @Methods(methodsName = "资源表新增", methods = "save")
    @ApiOperation(value = "资源表新增", httpMethod = "POST", notes = "资源表新增", response = Json.class)
    @ApiOperationSupport(ignoreParameters = {"id", "createDateTime", "createName", "modifyDateTime", "modifyName", "isDelete", "version"})
    @PostMapping("/save")
    public Json<String> save(@Validated(value = ResourceBO.Save.class) ResourceBO resourceBO, HttpServletRequest httpServletRequest) {
        //缓存取出用户
        Map<String, Object> u = RedisUtils.getToken(stringRedisTemplate, httpServletRequest);
        if (u != null) {
            resourceBO.setCreateName(u.get("name") + "-" + u.get("id"));
        }
        //返回内容
        return new Json(ReturnCode.成功, resourceService.saveEnhance(resourceBO));
    }


    /**
     * 资源表新增
     *
     * @param resourceBoList:
     * @return com.utils.Json
     * @author 孙凯伦
     * @since 2021-10-21 01:59:45
     */
    @PreventRepeat
    @Methods(methodsName = "资源表新增集合", methods = "saveList")
    @ApiOperation(value = "资源表新增集合", httpMethod = "POST", notes = "资源表新增集合", response = Json.class)
    @ApiOperationSupport(ignoreParameters = {"id", "createDateTime", "createName", "modifyDateTime", "modifyName", "isDelete", "version"})
    @PostMapping("/saveList")
    public Json<String> saveJson(@Validated(value = ResourceBO.Save.class) @RequestBody List<ResourceBO> resourceBoList, HttpServletRequest httpServletRequest) {
        //缓存取出用户
        Map<String, Object> u = RedisUtils.getToken(stringRedisTemplate, httpServletRequest);
        resourceBoList.forEach(resourceBO -> {
            if (u != null) {
                resourceBO.setCreateName(u.get("name") + "-" + u.get("id"));
            }
            resourceService.saveEnhance(resourceBO);
        });
        //返回内容
        return new Json(ReturnCode.成功, "");
    }


    /**
     * 资源表修改
     *
     * @param resourceBO:
     * @return com.utils.Json
     * @author 孙凯伦
     * @since 2021-10-21 01:59:45
     */
    @PreventRepeat
    @Methods(methodsName = "资源表修改", methods = "update")
    @ApiOperation(value = "资源表修改", httpMethod = "PUT", notes = "资源表修改", response = Json.class)
    @ApiOperationSupport(ignoreParameters = {"createDateTime", "createName", "modifyDateTime", "modifyName", "isDelete", "version"})
    @PutMapping("/update")
    public Json<Boolean> update(@Validated(value = ResourceBO.Update.class) ResourceBO resourceBO, HttpServletRequest httpServletRequest) {
        //缓存取出用户
        Map<String, Object> u = RedisUtils.getToken(stringRedisTemplate, httpServletRequest);
        if (u != null) {
            resourceBO.setModifyName(u.get("name") + "-" + u.get("id"));
        }
        //返回内容
        return new Json(ReturnCode.成功, resourceService.updateEnhance(resourceBO));
    }


    /**
     * 资源表修改集合
     *
     * @param resourceBOList:
     * @return com.utils.Json
     * @author 孙凯伦
     * @since 2021-10-21 01:59:45
     */
    @PreventRepeat
    @Methods(methodsName = "资源表修改集合", methods = "updateList")
    @ApiOperation(value = "资源表修改集合", httpMethod = "POST", notes = "资源表修改", response = Json.class)
    @ApiOperationSupport(ignoreParameters = {"createDateTime", "createName", "modifyDateTime", "modifyName", "isDelete", "version"})
    @PostMapping("/updateList")
    public Json<Boolean> updateList(@Validated(value = ResourceBO.Update.class)@RequestBody List<ResourceBO> resourceBOList, HttpServletRequest httpServletRequest) {
        //缓存取出用户
        Map<String, Object> u = RedisUtils.getToken(stringRedisTemplate, httpServletRequest);
        resourceBOList.forEach(resourceBO -> {
            if (u != null) {
                resourceBO.setModifyName(u.get("name") + "-" + u.get("id"));
            }
            resourceService.updateEnhance(resourceBO);
        });
        //返回内容
        return new Json(ReturnCode.成功, "");
    }


    /**
     * 资源表删除
     *
     * @param resourceBO:
     * @return com.utils.Json
     * @author 孙凯伦
     * @since 2021-10-21 01:59:45
     */
    @Methods(methodsName = "资源表删除", methods = "remove")
    @ApiOperation(value = "资源表删除", httpMethod = "DELETE", notes = "资源表删除", response = Json.class)
    @ApiOperationSupport(includeParameters = {"id"})
    @DeleteMapping("/remove")
    public Json<Boolean> remove(@Validated(value = ResourceBO.Remove.class) ResourceBO resourceBO) {
        return new Json(ReturnCode.成功, resourceService.removeEnhance(resourceBO));
    }


}