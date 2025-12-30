package com.gb.user.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gb.user.entity.bo.UserIdentityBO;
import com.gb.user.entity.model.request.UserVerifyIdentityRequest;
import com.gb.user.entity.query.UserInfoQuery;
import com.gb.user.entity.vo.UserLogVO;
import com.gb.user.service.GbtTransferProcessService;
import com.gb.user.service.UserQueryService;
import com.gb.utils.Json;
import com.gb.utils.RedisUtils;
import com.gb.utils.annotations.Methods;
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
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

import static com.gb.user.enums.ForwardProcesEnum.验证身份;

/**
 * <p>
 * 用户信息表 前端控制器
 * </p>
 *
 * @author 孙凯伦
 * @since 2020-12-29
 */
@Slf4j
@RequestRequired
@RestController
@Setter(onMethod_ = {@Autowired})
@RequestMapping("/userQuery")
@Api(tags = "用户查询控制器")
public class UserQueryController {

    private UserQueryService userQueryService;

    private GbtTransferProcessService gbtTransferProcessService;
    /**
     * redis操作
     */
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 根据标签参数查询标签内的用户基本信息集合
     *
     * @param userInfoQuery:
     * @return com.utils.Json
     * @author sunx
     * @since 2021-10-21 01:49:05
     */
    @Methods(methodsName = "根据标签参数查询标签内的用户基本信息集合", methods = "queryLabelInUserByTypeParams")
    @ApiOperation(value = "根据标签参数查询标签内的用户基本信息集合", httpMethod = "GET", notes = "根据标签参数查询标签内的用户基本信息集合", response = Json.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "queryType", value = "查询类型（0-代表只查询标签内用户基本信息，1-代表查询标签内已经认证的用户基本信息）", dataType = "int", required = true),
    })
    @GetMapping("/queryLabelInUserByTypeParams")
    public Json<Map<String, Object>> queryLabelInUserByTypeParams(@Validated(value = UserInfoQuery.QueryLabelInUserByTypeParams.class) UserInfoQuery userInfoQuery) throws Exception {
        return new Json(ReturnCode.成功, userQueryService.queryLabelInUserByTypeParams(userInfoQuery));
    }

    /**
     * 分页查询用户日志集合
     *
     * @param userId: 用户序列
     * @param pageNumber: 当前页
     * @param pageSize: 分页显示数量
     * @return com.utils.Json
     * @author sunx
     * @since 2021-10-21 01:49:05
     */
    @Methods(methodsName = "分页查询用户日志集合", methods = "queryUserLogList")
    @ApiOperation(value = "分页查询用户日志集合", httpMethod = "GET", notes = "分页查询用户日志集合", response = Json.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNumber", value = "当前页", dataType = "int", required = true),
            @ApiImplicitParam(name = "pageSize", value = "分页显示数量", dataType = "int", required = true),
    })

    @GetMapping({"/queryUserLogList"})
    public Json<Page<UserLogVO>> queryUserLogList(String userId, Integer pageNumber, Integer pageSize) {
        return new Json(ReturnCode.成功, this.userQueryService.pageEnhance(userId, pageNumber, pageSize));
    }

    /**
     * 校验身份
     *
     * @param httpServletRequest: http请求
     * @param bo: 请求参数
     * @return com.utils.Json
     * @author sunx
     * @since 2021-10-21 01:49:05
     */
    @Methods(methodsName = "校验身份", methods = "verifyIdentity")
    @ApiOperation(value = "校验身份", httpMethod = "GET", notes = "verifyIdentity", response = Json.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "mobile", value = "手机号", dataType = "int", required = true),
            @ApiImplicitParam(name = "smsCode", value = "短信验证码", dataType = "int", required = true),
            @ApiImplicitParam(name = "verifyType", value = "验证类型", dataType = "int", required = true),
    })

    @PostMapping({"/verifyIdentity"})
    public Json<Boolean> verifyIdentity(HttpServletRequest httpServletRequest, UserIdentityBO bo) {
        //缓存取出用户
        Map<String, Object> u = RedisUtils.getToken(stringRedisTemplate, httpServletRequest);
        if(MapUtils.isNotEmpty(u)){
            userQueryService.verifyIdentity(bo.getMobile(), String.valueOf(u.get(UniversalConstant.ID)));
            UserVerifyIdentityRequest userVerifyIdentityRequest = new UserVerifyIdentityRequest();
            userVerifyIdentityRequest.setPhone(bo.getMobile()).setVerifyCode(bo.getSmsCode()).setVerifyType(bo.getVerifyType());
            gbtTransferProcessService.dealWith(验证身份, httpServletRequest, userVerifyIdentityRequest);
        } else {
            throw new ParameterNullException("token无效，请重新登录！");
        }

        return new Json(ReturnCode.成功, true);
    }

}
