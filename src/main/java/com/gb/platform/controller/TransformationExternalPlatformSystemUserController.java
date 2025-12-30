package com.gb.platform.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gb.platform.entity.bo.BatchPlatformSystemUserBO;
import com.gb.platform.entity.bo.ExternalPlatformSystemBO;
import com.gb.platform.entity.bo.TransformationExternalPlatformSystemUserBO;
import com.gb.platform.entity.enums.TransformationExternalPlatformSystemStateEnum;
import com.gb.platform.entity.query.TransformationExternalPlatformSystemQuery;
import com.gb.platform.entity.query.TransformationExternalPlatformSystemUserQuery;
import com.gb.platform.entity.vo.TransformationExternalPlatformSystemUserVO;
import com.gb.platform.entity.vo.TransformationExternalPlatformSystemVO;
import com.gb.platform.service.TransformationExternalPlatformSystemService;
import com.gb.platform.service.TransformationExternalPlatformSystemUserService;
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
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.Objects;


/**
 * TODO 转化外部系统平台用户关联，Comment请求层
 * 代码生成器
 *
 * @author sunx
 * @className TransformationExternalPlatformSystemUserController
 * @time 2022-12-16 03:10:09
 */
@Slf4j
@RequestRequired
@ApiSupport(author = "sunx")
@Setter(onMethod_ = {@Autowired})
@RestController
@RequestMapping("/transformation-external-platform-system-user")
@Api(tags = "转化外部系统平台用户关联")
public class TransformationExternalPlatformSystemUserController {


    /**
     * 转化外部系统平台用户关联
     */
    private TransformationExternalPlatformSystemUserService transformationExternalPlatformSystemUserService;
    /**
     * 转化外部系统平台
     */
    private TransformationExternalPlatformSystemService transformationExternalPlatformSystemService;
    /**
     * redis操作
     */
    private StringRedisTemplate stringRedisTemplate;


    /**
     * TODO 分页
     *
     * @param transformationExternalPlatformSystemUserQuery 转化外部系统平台用户关联
     * @param current
     * @param size
     * @return Json<Page < TransformationExternalPlatformSystemUserVO>>
     * @author sunx
     * @methodName select
     * @time 2022-12-16 03:10:09
     */
    @Methods(methodsName = "转化外部系统平台用户关联集合分页查询", methods = "select")
    @ApiOperation(value = "转化外部系统平台用户关联集合分页查询", httpMethod = "GET", notes = "转化外部系统平台用户关联集合查询", response = Json.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "current", value = "当前页", dataType = "int", required = true),
            @ApiImplicitParam(name = "size", value = "分页显示数量", dataType = "int", required = true),
    })
    @GetMapping("/select")
    public Json<Page<TransformationExternalPlatformSystemUserVO>> select(@Validated(value = TransformationExternalPlatformSystemUserQuery.Select.class) TransformationExternalPlatformSystemUserQuery transformationExternalPlatformSystemUserQuery, Integer current, Integer size) {
        //返回内容
        return new Json(ReturnCode.成功, transformationExternalPlatformSystemUserService.pageEnhance(new Page(current, size), transformationExternalPlatformSystemUserQuery));
    }


    /**
     * TODO 集合
     *
     * @param transformationExternalPlatformSystemUserQuery 转化外部系统平台用户关联
     * @return Json<List < TransformationExternalPlatformSystemUserVO>>
     * @author sunx
     * @methodName selectList
     * @time 2022-12-16 03:10:09
     */
    @Methods(methodsName = "转化外部系统平台用户关联集合查询", methods = "selectList")
    @ApiOperation(value = "转化外部系统平台用户关联集合查询", httpMethod = "GET", notes = "转化外部系统平台用户关联集合查询", response = Json.class)
    @GetMapping("/selectList")
    public Json<List<TransformationExternalPlatformSystemUserVO>> selectList(@Validated(value = TransformationExternalPlatformSystemUserQuery.SelectList.class) TransformationExternalPlatformSystemUserQuery transformationExternalPlatformSystemUserQuery) {
        //返回内容
        return new Json(ReturnCode.成功, transformationExternalPlatformSystemUserService.listEnhance(transformationExternalPlatformSystemUserQuery));
    }


    /**
     * TODO 单条
     *
     * @param transformationExternalPlatformSystemUserQuery 转化外部系统平台用户关联
     * @return Json<TransformationExternalPlatformSystemUserVO>
     * @author sunx
     * @methodName selectOne
     * @time 2022-12-16 03:10:09
     */
    @Methods(methodsName = "转化外部系统平台用户关联单条查询", methods = "selectOne")
    @ApiOperation(value = "转化外部系统平台用户关联单条查询", httpMethod = "GET", notes = "转化外部系统平台用户关联单条查询", response = Json.class)
    @GetMapping("/selectOne")
    public Json<TransformationExternalPlatformSystemUserVO> selectOne(@Validated(value = TransformationExternalPlatformSystemUserQuery.SelectOne.class) TransformationExternalPlatformSystemUserQuery transformationExternalPlatformSystemUserQuery) {
        //返回内容
        return new Json(ReturnCode.成功, transformationExternalPlatformSystemUserService.getOneEnhance(transformationExternalPlatformSystemUserQuery));
    }


    /**
     * TODO 总数
     *
     * @param transformationExternalPlatformSystemUserQuery 转化外部系统平台用户关联
     * @return Json<Integer>
     * @author sunx
     * @methodName count
     * @time 2022-12-16 03:10:09
     */
    @Methods(methodsName = "转化外部系统平台用户关联总数查询", methods = "count")
    @ApiOperation(value = "转化外部系统平台用户关联总数查询", httpMethod = "GET", notes = "转化外部系统平台用户关联总数查询", response = Json.class)
    @GetMapping(value = "/count")
    public Json<Integer> count(@Validated(value = TransformationExternalPlatformSystemUserQuery.Count.class) TransformationExternalPlatformSystemUserQuery transformationExternalPlatformSystemUserQuery) {
        //返回内容
        return new Json(ReturnCode.成功, transformationExternalPlatformSystemUserService.countEnhance(transformationExternalPlatformSystemUserQuery));
    }


    /**
     * TODO 新增
     *
     * @param transformationExternalPlatformSystemUserBO 转化外部系统平台用户关联
     * @return Json<String>
     * @author sunx
     * @methodName save
     * @time 2022-12-16 03:10:09
     */
    @PreventRepeat
    @Methods(methodsName = "转化外部系统平台用户关联新增", methods = "save")
    @ApiOperation(value = "转化外部系统平台用户关联新增", httpMethod = "POST", notes = "转化外部系统平台用户关联新增", response = Json.class)
    @ApiOperationSupport(ignoreParameters = {"id", "createDateTime", "createName", "modifyDateTime", "modifyName", "isDelete", "version"})
    @PostMapping("/save")
    public Json<String> save(@Validated(value = TransformationExternalPlatformSystemUserBO.Save.class) TransformationExternalPlatformSystemUserBO transformationExternalPlatformSystemUserBO, HttpServletRequest httpServletRequest) {
        //缓存取出用户
        Map<String, Object> u = RedisUtils.getToken(stringRedisTemplate, httpServletRequest);
        if (u != null) {
            transformationExternalPlatformSystemUserBO.setCreateName(u.get("name") + "-" + u.get("id"));
        }
        //返回内容
        return new Json(ReturnCode.成功, transformationExternalPlatformSystemUserService.saveEnhance(transformationExternalPlatformSystemUserBO));
    }


    /**
     * TODO 批量外部系统用户新增
     *
     * @param bo
     * @param httpServletRequest
     * @return com.gb.utils.Json<java.lang.String>
     * @author 孙凯伦
     * @methodName saveBatchPlateform
     * @time 2023/9/14 11:45
     */
    @PreventRepeat
    @Methods(methodsName = "批量外部系统用户新增", methods = "save")
    @ApiOperation(value = "批量外部系统用户新增", httpMethod = "POST", notes = "批量外部系统用户新增", response = Json.class)
    @ApiOperationSupport(ignoreParameters = {"id", "createDateTime", "createName", "modifyDateTime", "modifyName", "isDelete", "version"})
    @PostMapping("/saveBatchPlateform")
    public Json<String> saveBatchPlateform(@RequestBody BatchPlatformSystemUserBO bo, HttpServletRequest httpServletRequest) {
        if (StringUtils.isBlank(bo.getUserId())) {
            return new Json(ReturnCode.请求必填参数为空, "缺少必传参数！");
        }
        //缓存取出用户
        Map<String, Object> u = RedisUtils.getToken(stringRedisTemplate, httpServletRequest);
        if (u != null) {
            bo.setModifyName(u.get("name") + "-" + u.get("id"));
        }
        transformationExternalPlatformSystemUserService.saveBatchPlateform(bo);
        //返回内容
        return new Json(ReturnCode.成功, true);
    }


    /**
     * TODO 修改
     *
     * @param transformationExternalPlatformSystemUserBO 转化外部系统平台用户关联
     * @return Json<Boolean>
     * @author sunx
     * @methodName update
     * @time 2022-12-16 03:10:09
     */
    @PreventRepeat
    @Methods(methodsName = "转化外部系统平台用户关联修改", methods = "update")
    @ApiOperation(value = "转化外部系统平台用户关联修改", httpMethod = "PUT", notes = "转化外部系统平台用户关联修改", response = Json.class)
    @ApiOperationSupport(ignoreParameters = {"createDateTime", "createName", "modifyDateTime", "modifyName", "isDelete", "version"})
    @PutMapping("/update")
    public Json<Boolean> update(@Validated(value = TransformationExternalPlatformSystemUserBO.Update.class) TransformationExternalPlatformSystemUserBO transformationExternalPlatformSystemUserBO, HttpServletRequest httpServletRequest) {
        if (StringUtils.isBlank(transformationExternalPlatformSystemUserBO.getId())) {
            return new Json(ReturnCode.请求必填参数为空, "缺少必传参数！");
        }
        //缓存取出用户
        Map<String, Object> u = RedisUtils.getToken(stringRedisTemplate, httpServletRequest);
        if (u != null) {
            transformationExternalPlatformSystemUserBO.setModifyName(u.get("name") + "-" + u.get("id"));
        }
        //返回内容
        return new Json(ReturnCode.成功, transformationExternalPlatformSystemUserService.updateEnhance(transformationExternalPlatformSystemUserBO));
    }


    /**
     * TODO 修改
     *
     * @param transformationExternalPlatformSystemUserBO 转化外部系统平台用户关联
     * @return Json<Boolean>
     * @author sunx
     * @methodName update
     * @time 2022-12-16 03:10:09
     */
    @PreventRepeat
    @Methods(methodsName = "转化外部系统平台用户关联修改", methods = "updateUserId")
    @ApiOperation(value = "转化外部系统平台用户关联修改", httpMethod = "PUT", notes = "转化外部系统平台用户关联修改", response = Json.class)
    @ApiOperationSupport(ignoreParameters = {"createDateTime", "createName", "modifyDateTime", "modifyName", "isDelete", "version"})
    @PutMapping("/updateUserId")
    public Json<Boolean> updateUserId(@Validated(value = TransformationExternalPlatformSystemUserBO.Update.class) TransformationExternalPlatformSystemUserBO transformationExternalPlatformSystemUserBO, HttpServletRequest httpServletRequest) {
        if (StringUtils.isBlank(transformationExternalPlatformSystemUserBO.getUserId())) {
            return new Json(ReturnCode.请求必填参数为空, "缺少必传参数！");
        }
        //缓存取出用户
        Map<String, Object> u = RedisUtils.getToken(stringRedisTemplate, httpServletRequest);
        if (u != null) {
            transformationExternalPlatformSystemUserBO.setModifyName(u.get("name") + "-" + u.get("id"));
        }
        //返回内容
        return new Json(ReturnCode.成功, transformationExternalPlatformSystemUserService.updateUserIdEnhance(transformationExternalPlatformSystemUserBO));
    }


    /**
     * TODO 批量外部系统用户更新
     *
     * @param bo 转化外部系统平台用户关联
     * @return Json<Boolean>
     * @author sunx
     * @methodName update
     * @time 2022-12-16 03:10:09
     */
    @PreventRepeat
    @Methods(methodsName = "批量外部系统用户更新", methods = "updateBatchPlateform")
    @ApiOperation(value = "批量外部系统用户更新", httpMethod = "PUT", notes = "批量外部系统用户更新", response = Json.class)
    @ApiOperationSupport(ignoreParameters = {"createDateTime", "createName", "modifyDateTime", "modifyName", "isDelete", "version"})
    @PutMapping("/updateBatchPlateform")
    public Json<Boolean> updateBatchPlateform(@RequestBody BatchPlatformSystemUserBO bo, HttpServletRequest httpServletRequest) {
        if (StringUtils.isBlank(bo.getUserId())) {
            return new Json(ReturnCode.请求必填参数为空, "缺少必传参数！");
        }
        //缓存取出用户
        Map<String, Object> u = RedisUtils.getToken(stringRedisTemplate, httpServletRequest);
        if (u != null) {
            bo.setModifyName(u.get("name") + "-" + u.get("id"));
        }
        //是否全部
        if (Objects.nonNull(bo.getAll()) && bo.getAll()) {
            List<TransformationExternalPlatformSystemVO> list = transformationExternalPlatformSystemService.listEnhance(new TransformationExternalPlatformSystemQuery());
            List<ExternalPlatformSystemBO> l = Lists.newArrayList();
            for (TransformationExternalPlatformSystemVO transformationExternalPlatformSystemVO : list) {
                if (transformationExternalPlatformSystemVO.getState() == TransformationExternalPlatformSystemStateEnum.启用) {
                    l.add(new ExternalPlatformSystemBO() {{
                        setExternalSystemCode(transformationExternalPlatformSystemVO.getExternalSystemCode());
                        setExternalPlatformCode(transformationExternalPlatformSystemVO.getExternalPlatformCode());
                        setClue(true);
                    }});
                }
            }
            bo.setExternalPlatformSystemList(l);
        }
        //返回内容
        transformationExternalPlatformSystemUserService.updateBatchPlateform(bo);
        return new Json(ReturnCode.成功, true);
    }


    /**
     * TODO 删除
     *
     * @param transformationExternalPlatformSystemUserBO 转化外部系统平台用户关联
     * @return Json<Boolean>
     * @author sunx
     * @methodName remove
     * @time 2022-12-16 03:10:09
     */
    @Methods(methodsName = "转化外部系统平台用户关联删除", methods = "remove")
    @ApiOperation(value = "转化外部系统平台用户关联删除", httpMethod = "DELETE", notes = "转化外部系统平台用户关联删除", response = Json.class)
    @ApiOperationSupport(includeParameters = {"id"})
    @DeleteMapping("/remove")
    public Json<Boolean> remove(@Validated(value = TransformationExternalPlatformSystemUserBO.Remove.class) TransformationExternalPlatformSystemUserBO transformationExternalPlatformSystemUserBO) {
        return new Json(ReturnCode.成功, transformationExternalPlatformSystemUserService.removeEnhance(transformationExternalPlatformSystemUserBO));
    }
}