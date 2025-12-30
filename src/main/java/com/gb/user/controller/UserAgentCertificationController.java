package com.gb.user.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gb.user.entity.UserAgentCertification;
import com.gb.user.service.UserAgentCertificationService;
import com.gb.utils.Json;
import com.gb.utils.RedisUtils;
import com.gb.utils.annotations.Methods;
import com.gb.utils.annotations.PreventRepeat;
import com.gb.utils.annotations.RequestRequired;
import com.gb.utils.constants.UniversalConstant;
import com.gb.utils.enumeration.ReturnCode;
import com.gb.utils.exception.ParameterNullException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.Objects;

/**
 * <p>
 * 用户经纪人认证 前端控制器
 * </p>
 *
 * @author sunx
 * @since 2021-05-25
 */
@Slf4j
@RequestRequired
@RestController
@Setter(onMethod_ = {@Autowired})
@RequestMapping("/user-agent-certification")
@Api(tags = "用户经纪人认证")
public class UserAgentCertificationController {

    /**
     * 用户经纪人认证
     */
    private UserAgentCertificationService userAgentCertificationService;
    /**
     * redis操作
     */
    private StringRedisTemplate stringRedisTemplate;


    /**
     * 用户经纪人认证集合查询
     *
     * @param current:
     * @param size:
     * @param userAgentCertification:
     * @return com.utils.Json
     * @author sunx
     * @since 2021-05-25
     */
    @Methods(methodsName = "用户经纪人认证集合查询", methods = "select")
    @ApiOperation(value = "用户经纪人认证集合查询", httpMethod = "GET", notes = "用户经纪人认证集合查询", response = Json.class)
    @ApiImplicitParams({
        @ApiImplicitParam(name = "current", value = "当前页", dataType = "int"),
        @ApiImplicitParam(name = "size", value = "分页显示数量", dataType = "int"),
    })
    @GetMapping("/select")
    public Json<IPage<UserAgentCertification>> select(Integer current, Integer size, UserAgentCertification userAgentCertification) {
        if (current != null && size != null) {
            Page page = new Page(current, size);
            //返回内容
            return new Json(ReturnCode.成功, userAgentCertificationService.pageEnhance(page, userAgentCertification));
        } else {
            //返回内容
            return new Json(ReturnCode.成功, userAgentCertificationService.listEnhance(userAgentCertification));
        }
    }


    /**
     * 用户经纪人认证单条查询
     *
     * @param userAgentCertification:
     * @return com.utils.Json
     * @author sunx
     * @since 2021-05-25
     */
    @Methods(methodsName = "用户经纪人认证单条查询", methods = "selectOne")
    @ApiOperation(value = "用户经纪人认证单条查询", httpMethod = "GET", notes = "用户经纪人认证单条查询", response = Json.class)
    @GetMapping("/selectOne")
    public Json<UserAgentCertification> selectOne(UserAgentCertification userAgentCertification) {
        //返回内容
        return new Json(ReturnCode.成功, userAgentCertificationService.getOneEnhance(userAgentCertification));
    }


    /**
     * 用户经纪人认证总数查询
     *
     * @param userAgentCertification:
     * @return com.utils.Json
     * @author sunx
     * @since 2021-05-25
     */
    @Methods(methodsName = "用户经纪人认证总数查询", methods = "count")
    @ApiOperation(value = "用户经纪人认证总数查询", httpMethod = "GET", notes = "用户经纪人认证总数查询", response = Json.class)
    @GetMapping(value = "/count")
    public Json<Integer> count(UserAgentCertification userAgentCertification) {
        //返回内容
        return new Json(ReturnCode.成功, userAgentCertificationService.countEnhance(userAgentCertification));
    }


   /**
    * 用户经纪人认证新增
    *
    * @param userAgentCertification:
    * @return com.utils.Json
    * @author sunx
    * @since 2021-05-25
    */
    @PreventRepeat
    @Methods(methodsName = "用户经纪人认证新增", methods = "save")
    @ApiOperation(value = "用户经纪人认证新增", httpMethod = "POST", notes = "用户经纪人认证新增", response = Json.class)
    @PostMapping("/save")
    public Json<String> save(UserAgentCertification userAgentCertification, HttpServletRequest httpServletRequest) throws Exception {
        //缓存取出用户
        Map<String, Object> u = RedisUtils.getToken(stringRedisTemplate, httpServletRequest);
        if(u != null){
            userAgentCertification.setCreateName(u.get(UniversalConstant.NAME) + "-" + u.get(UniversalConstant.ID));
        }
        userAgentCertificationService.saveEnhance(userAgentCertification);
        return new Json(ReturnCode.成功, userAgentCertification.getId());

    }


    /**
     * 用户经纪人认证修改
     *
     * @param userAgentCertification:
     * @return com.utils.Json
     * @author sunx
     * @since 2021-05-25
     */
    @PreventRepeat
    @Methods(methodsName = "用户经纪人认证修改", methods = "update")
    @ApiOperation(value = "用户经纪人认证修改", httpMethod = "PUT", notes = "用户经纪人认证修改", response = Json.class)
    @PutMapping("/update")
    public Json<Boolean> update(UserAgentCertification userAgentCertification, HttpServletRequest httpServletRequest) {
        try{
            if(Objects.isNull(userAgentCertification.getState())) {
                throw new ParameterNullException("state不能为空！");
            }
            Map<String, Object> u = RedisUtils.getToken(stringRedisTemplate, httpServletRequest);
            if(MapUtils.isNotEmpty(u)) {
                userAgentCertification.setModifyName(u.get(UniversalConstant.NAME) + "-" + u.get(UniversalConstant.ID));
            }
            userAgentCertificationService.updateAgentCertEnhance(httpServletRequest, userAgentCertification, null);
            return new Json(ReturnCode.成功, true);
        }catch(Exception e){
            return new Json(ReturnCode.系统执行出错, e.getMessage());
        }
    }


    /**
     * 用户经纪人认证删除
     *
     * @param userAgentCertification:
     * @return com.utils.Json
     * @author sunx
     * @since 2021-05-25
     */
    @Methods(methodsName = "用户经纪人认证删除", methods = "remove")
    @ApiOperation(value = "用户经纪人认证删除", httpMethod = "DELETE", notes = "用户经纪人认证删除", response = Json.class)
    @DeleteMapping("/remove")
    public Json<Boolean> remove(UserAgentCertification userAgentCertification) {
        return new Json(ReturnCode.成功, userAgentCertificationService.removeEnhance(userAgentCertification));
    }


}
