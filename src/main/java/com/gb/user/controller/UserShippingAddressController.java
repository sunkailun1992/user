package com.gb.user.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gb.user.entity.UserShippingAddress;
import com.gb.user.service.UserShippingAddressService;
import com.gb.utils.Json;
import com.gb.utils.RedisUtils;
import com.gb.utils.annotations.Methods;
import com.gb.utils.annotations.PreventRepeat;
import com.gb.utils.annotations.RequestRequired;
import java.util.Optional;
import com.gb.utils.enumeration.ReturnCode;
import com.gb.utils.exception.ParameterNullException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
/**
 * <p>
 * 用户收货地址 前端控制器
 * </p>
 *
 * @author sunx
 * @since 2021-05-25
 */
@Slf4j
@RequestRequired
@RestController
@Setter(onMethod_ = {@Autowired})
@RequestMapping("/user-shipping-address")
@Api(tags = "用户收货地址")
public class UserShippingAddressController {

    /**
     * 用户收货地址
     */
    private UserShippingAddressService userShippingAddressService;
    /**
     * redis操作
     */
    private StringRedisTemplate stringRedisTemplate;


    /**
     * 用户收货地址集合查询
     *
     * @param current:
     * @param size:
     * @param userShippingAddress:
     * @return com.utils.Json
     * @author sunx
     * @since 2021-05-25
     */
    @Methods(methodsName = "用户收货地址集合查询", methods = "select")
    @ApiOperation(value = "用户收货地址集合查询", httpMethod = "GET", notes = "用户收货地址集合查询", response = Json.class)
    @ApiImplicitParams({
        @ApiImplicitParam(name = "current", value = "当前页", dataType = "int"),
        @ApiImplicitParam(name = "size", value = "分页显示数量", dataType = "int"),
    })
    @GetMapping("/select")
    public Json<IPage<UserShippingAddress>> select(Integer current, Integer size, UserShippingAddress userShippingAddress) {
        if (current != null && size != null) {
            Page page = new Page(current, size);
            //返回内容
            return new Json(ReturnCode.成功, userShippingAddressService.pageEnhance(page, userShippingAddress));
        } else {
            //返回内容
            return new Json(ReturnCode.成功, userShippingAddressService.listEnhance(userShippingAddress));
        }
    }


    /**
     * 用户收货地址单条查询
     *
     * @param userShippingAddress:
     * @return com.utils.Json
     * @author sunx
     * @since 2021-05-25
     */
    @Methods(methodsName = "用户收货地址单条查询", methods = "selectOne")
    @ApiOperation(value = "用户收货地址单条查询", httpMethod = "GET", notes = "用户收货地址单条查询", response = Json.class)
    @GetMapping("/selectOne")
    public Json<UserShippingAddress> selectOne(UserShippingAddress userShippingAddress) {
        //返回内容
        return new Json(ReturnCode.成功, userShippingAddressService.getOneEnhance(userShippingAddress));
    }


    /**
     * 用户收货地址总数查询
     *
     * @param userShippingAddress:
     * @return com.utils.Json
     * @author sunx
     * @since 2021-05-25
     */
    @Methods(methodsName = "用户收货地址总数查询", methods = "count")
    @ApiOperation(value = "用户收货地址总数查询", httpMethod = "GET", notes = "用户收货地址总数查询", response = Json.class)
    @GetMapping(value = "/count")
    public Json<Integer> count(UserShippingAddress userShippingAddress) {
        //返回内容
        return new Json(ReturnCode.成功, userShippingAddressService.countEnhance(userShippingAddress));
    }


   /**
    * 用户收货地址新增
    *
    * @param userShippingAddress:
    * @return com.utils.Json
    * @author sunx
    * @since 2021-05-25
    */
    @PreventRepeat
    @Methods(methodsName = "用户收货地址新增", methods = "save")
    @ApiOperation(value = "用户收货地址新增", httpMethod = "POST", notes = "用户收货地址新增", response = Json.class)
    @PostMapping("/save")
    public Json<String> save(UserShippingAddress userShippingAddress, HttpServletRequest httpServletRequest) throws Exception {
        //缓存取出用户
        Map<String, Object> u = RedisUtils.getToken(stringRedisTemplate, httpServletRequest);
        if(u != null){
            userShippingAddress.setCreateName(u.get("name") + "-" + u.get("id"));
        }
        userShippingAddressService.saveEnhance(userShippingAddress);
        //返回内容
        return new Json(ReturnCode.成功, userShippingAddress.getId());


    }


    /**
     * 用户收货地址修改
     *
     * @param userShippingAddress:
     * @return com.utils.Json
     * @author sunx
     * @since 2021-05-25
     */
    @PreventRepeat
    @Methods(methodsName = "用户收货地址修改", methods = "update")
    @ApiOperation(value = "用户收货地址修改", httpMethod = "PUT", notes = "用户收货地址修改", response = Json.class)
    @PutMapping("/update")
    public Json<Boolean> update(UserShippingAddress userShippingAddress, HttpServletRequest httpServletRequest) throws Exception {
        //缓存取出用户
        Map<String, Object> u = RedisUtils.getToken(stringRedisTemplate, httpServletRequest);
        if(u != null){
            userShippingAddress.setModifyName(u.get("name") + "-" + u.get("id"));
        }
        //返回内容
        return new Json(ReturnCode.成功, userShippingAddressService.updateEnhance(false, userShippingAddress));
    }


    /**
     * 设置默认状态
     *
     * @param state:
     * @param id:
     * @return com.utils.Json
     * @author sunx
     * @since 2021-05-25
     */
    @PreventRepeat
    @Methods(methodsName = "设置默认状态", methods = "setState")
    @ApiOperation(value = "设置默认状态", httpMethod = "PUT", notes = "设置默认状态", response = Json.class)
    @PutMapping("/setState")
    public Json setState(Integer state, String id, HttpServletRequest httpServletRequest) throws Exception {
        if(null == state || StringUtils.isBlank(id)){
            throw new ParameterNullException("缺少设置默认状态必要参数！");
        }
        String modifyName = StringUtils.EMPTY;
        //缓存取出用户
        Map<String, Object> u = RedisUtils.getToken(stringRedisTemplate, httpServletRequest);
        if(u != null){
            modifyName = (u.get("name") + "-" + u.get("id"));
        }
        UserShippingAddress address = new UserShippingAddress();
        address.setModifyName(modifyName);
        address.setState(state);
        address.setId(id);
        //返回内容
        return new Json(ReturnCode.成功, userShippingAddressService.updateEnhance(true, address));
    }


    /**
     * 用户收货地址删除
     *
     * @param userShippingAddress:
     * @return com.utils.Json
     * @author sunx
     * @since 2021-05-25
     */
    @Methods(methodsName = "用户收货地址删除", methods = "remove")
    @ApiOperation(value = "用户收货地址删除", httpMethod = "DELETE", notes = "用户收货地址删除", response = Json.class)
    @DeleteMapping("/remove")
    public Json<Boolean> remove(UserShippingAddress userShippingAddress) {
        return new Json(ReturnCode.成功, userShippingAddressService.removeEnhance(userShippingAddress));
    }


}
