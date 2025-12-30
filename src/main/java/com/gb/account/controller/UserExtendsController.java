package com.gb.account.controller;

import cn.hutool.core.convert.Convert;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gb.account.entity.bo.UserExtendsBO;
import com.gb.account.entity.query.UserExtendsQuery;
import com.gb.account.entity.vo.UserExtendsVO;
import com.gb.account.service.UserExtendsService;
import com.gb.user.service.GbtTransferProcessService;
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
import org.apache.commons.lang3.StringUtils;
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
 * @since: 2021-10-21 01:50:40
 * @description: TODO 用户扩展表，Comment请求层
 * @source: 代码生成器
 */
@Slf4j
@RequestRequired
@ApiSupport(author = "孙凯伦")
@Setter(onMethod_ = {@Autowired})
@RestController
@RequestMapping("/userExtends")
@Api(tags = "用户扩展表")
public class UserExtendsController {


    /**
     * 用户扩展表
     */
    private UserExtendsService userExtendsService;
    /**
     * redis操作
     */
    private StringRedisTemplate stringRedisTemplate;
    /**
     * 请求工保通服务中转处理服务
     */
    private GbtTransferProcessService gbtTransferProcessService;


    /**
     * 用户扩展表集合分页查询
     *
     * @param current:
     * @param size:
     * @param userExtendsQuery:
     * @return com.utils.Json
     * @author 孙凯伦
     * @since 2021-10-21 01:50:40
     */
    @Methods(methodsName = "用户扩展表集合分页查询", methods = "select")
    @ApiOperation(value = "用户扩展表集合分页查询", httpMethod = "GET", notes = "用户扩展表集合查询", response = Json.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "current", value = "当前页", dataType = "int", required = true),
            @ApiImplicitParam(name = "size", value = "分页显示数量", dataType = "int", required = true),
    })
    @GetMapping("/select")
    public Json<Page<UserExtendsVO>> select(@Validated(value = UserExtendsQuery.Select.class) UserExtendsQuery userExtendsQuery, Integer current, Integer size) {
        //返回内容
        return new Json(ReturnCode.成功, userExtendsService.pageEnhance(new Page(current, size), userExtendsQuery));
    }


    /**
     * 用户扩展表集合查询
     *
     * @param userExtendsQuery:
     * @return com.utils.Json
     * @author 孙凯伦
     * @since 2021-10-21 01:50:40
     */
    @Methods(methodsName = "用户扩展表集合查询", methods = "selectList")
    @ApiOperation(value = "用户扩展表集合查询", httpMethod = "GET", notes = "用户扩展表集合查询", response = Json.class)
    @GetMapping("/selectList")
    public Json<List<UserExtendsVO>> selectList(@Validated(value = UserExtendsQuery.SelectList.class) UserExtendsQuery userExtendsQuery) {
        //返回内容
        return new Json(ReturnCode.成功, userExtendsService.listEnhance(userExtendsQuery));
    }


    /**
     * 用户扩展表单条查询
     *
     * @param userExtendsQuery:
     * @return com.utils.Json
     * @author 孙凯伦
     * @since 2021-10-21 01:50:40
     */
    @Methods(methodsName = "用户扩展表单条查询", methods = "selectOne")
    @ApiOperation(value = "用户扩展表单条查询", httpMethod = "GET", notes = "用户扩展表单条查询", response = Json.class)
    @GetMapping("/selectOne")
    public Json<UserExtendsVO> selectOne(HttpServletRequest httpServletRequest, @Validated(value = UserExtendsQuery.SelectOne.class) UserExtendsQuery userExtendsQuery) {
        //返回内容
        return new Json(ReturnCode.成功, userExtendsService.getOneEnhance(userExtendsQuery));
    }


    /**
     * 修改手机号
     *
     * @param newMobile: 新手机号
     * @param smsCode:   短信验证码
     * @param id:        用户扩展表序列
     * @return com.utils.Json
     * @author sunx
     * @since 2021-10-21 11:06:07
     */
    @PreventRepeat
    @Methods(methodsName = "修改手机号", methods = "updateMobile")
    @ApiOperation(value = "修改手机号", httpMethod = "PUT", notes = "修改手机号", response = Json.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "newMobile", value = "新手机号", dataType = "String", required = true),
            @ApiImplicitParam(name = "smsCode", value = "验证码", dataType = "String", required = true),
            @ApiImplicitParam(name = "id", value = "用户扩展表序列", dataType = "String", required = true),
    })
    @PutMapping("/updateMobile")
    public Json<Boolean> updateMobile(String id, String newMobile, String smsCode, HttpServletRequest httpServletRequest) {
        gbtTransferProcessService.updateMobile(httpServletRequest, id, newMobile, smsCode);
        return new Json(ReturnCode.成功, true);
    }


    /**
     * 用户扩展表总数查询
     *
     * @param userExtendsQuery:
     * @return com.utils.Json
     * @author 孙凯伦
     * @since 2021-10-21 01:50:40
     */
    @Methods(methodsName = "用户扩展表总数查询", methods = "count")
    @ApiOperation(value = "用户扩展表总数查询", httpMethod = "GET", notes = "用户扩展表总数查询", response = Json.class)
    @GetMapping(value = "/count")
    public Json<Integer> count(@Validated(value = UserExtendsQuery.Count.class) UserExtendsQuery userExtendsQuery) {
        //返回内容
        return new Json(ReturnCode.成功, userExtendsService.countEnhance(userExtendsQuery));
    }


    /**
     * 用户扩展表新增
     *
     * @param userExtendsBO:
     * @return com.utils.Json
     * @author 孙凯伦
     * @since 2021-10-21 01:50:40
     */
    @PreventRepeat
    @Methods(methodsName = "用户扩展表新增", methods = "save")
    @ApiOperation(value = "用户扩展表新增", httpMethod = "POST", notes = "用户扩展表新增", response = Json.class)
    @ApiOperationSupport(ignoreParameters = {"id", "createDateTime", "createName", "modifyDateTime", "modifyName", "isDelete", "version"})
    @PostMapping("/save")
    public Json<String> save(@Validated(value = UserExtendsBO.Save.class) UserExtendsBO userExtendsBO, HttpServletRequest httpServletRequest) {
        //缓存取出用户
        Map<String, Object> u = RedisUtils.getToken(stringRedisTemplate, httpServletRequest);
        if (u != null) {
            userExtendsBO.setCreateName(u.get(UniversalConstant.NAME) + "-" + u.get(UniversalConstant.ID));
        }
        //返回内容
        return new Json(ReturnCode.成功, userExtendsService.saveEnhance(userExtendsBO));
    }


    /**
     * 用户扩展表修改
     *
     * @param userExtendsBO:
     * @return com.utils.Json
     * @author 孙凯伦
     * @since 2021-10-21 01:50:40
     */
    @PreventRepeat
    @Methods(methodsName = "用户扩展表修改", methods = "update")
    @ApiOperation(value = "用户扩展表修改", httpMethod = "PUT", notes = "用户扩展表修改", response = Json.class)
    @ApiOperationSupport(ignoreParameters = {"createDateTime", "createName", "modifyDateTime", "modifyName", "isDelete", "version"})
    @PutMapping("/update")
    public Json<Boolean> update(@Validated(value = UserExtendsBO.Update.class) UserExtendsBO userExtendsBO, HttpServletRequest httpServletRequest) {
        if (StringUtils.isBlank(userExtendsBO.getId()) && StringUtils.isBlank(userExtendsBO.getUserId())) {
            return new Json(ReturnCode.请求必填参数为空, "缺少修改必要参数！");
        }
        //缓存取出用户
        Map<String, Object> u = RedisUtils.getToken(stringRedisTemplate, httpServletRequest);
        if (u != null) {
            userExtendsBO.setModifyName(u.get(UniversalConstant.NAME) + "-" + u.get(UniversalConstant.ID));
        }
        userExtendsService.updateEnhance(userExtendsBO, null, null);
        return new Json(ReturnCode.成功, true);
    }


    /**
     * 用户扩展表删除
     *
     * @param userExtendsBO:
     * @return com.utils.Json
     * @author 孙凯伦
     * @since 2021-10-21 01:50:40
     */
    @Methods(methodsName = "用户扩展表删除", methods = "remove")
    @ApiOperation(value = "用户扩展表删除", httpMethod = "DELETE", notes = "用户扩展表删除", response = Json.class)
    @ApiOperationSupport(includeParameters = {"id"})
    @DeleteMapping("/remove")
    public Json<Boolean> remove(@Validated(value = UserExtendsBO.Remove.class) UserExtendsBO userExtendsBO) {
        return new Json(ReturnCode.成功, userExtendsService.removeEnhance(userExtendsBO));
    }


    /**
     * TODO 用户信息查询
     *
     * @param httpServletRequest
     * @return com.gb.utils.Json<com.gb.account.entity.vo.UserExtendsVO>
     * @author 孙凯伦
     * @methodName token
     * @time 2023/10/25 09:44
     */
    @Methods(methodsName = "用户信息查询", methods = "token")
    @ApiOperation(value = "用户信息查询", httpMethod = "GET", notes = "用户信息查询", response = Json.class)
    @GetMapping("/token")
    public Json<UserExtendsVO> token(HttpServletRequest httpServletRequest) {
        //缓存取出用户
        Map<String, Object> u = RedisUtils.getToken(stringRedisTemplate, httpServletRequest);
        if (u.get("id") == null) {
            return new Json(ReturnCode.用户账户不存在, "token未找到用户信息！");
        }
        //返回内容
        return new Json(ReturnCode.成功, userExtendsService.getOneEnhance(new UserExtendsQuery() {{
            setUserId(Convert.toStr(u.get("id")));
        }}));
    }
}