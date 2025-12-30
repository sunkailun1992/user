package com.gb.permissions.service.impl;

import cn.hutool.core.convert.Convert;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gb.permissions.entity.RoleResource;
import com.gb.permissions.entity.bo.RoleResourceBO;
import com.gb.permissions.entity.query.ResourceQuery;
import com.gb.permissions.entity.query.RoleResourceQuery;
import com.gb.permissions.entity.vo.ResourceVO;
import com.gb.permissions.entity.vo.RoleResourceVO;
import com.gb.permissions.mapper.RoleResourceMapper;
import com.gb.permissions.service.ResourceService;
import com.gb.permissions.service.RoleResourceService;
import com.gb.permissions.service.query.RoleResourceServiceQuery;
import com.gb.permissions.service.results.RoleResourceServiceResults;
import com.gb.utils.GeneralConvertor;
import com.gb.utils.RedisUtils;
import com.gb.utils.enumeration.NumericEnum;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: 孙凯伦
 * @since: 2021-10-21 01:59:42
 * @description: TODO 角色资源表，Service服务实现层
 * @source: 代码生成器
 */
@Slf4j
@Service
@Setter(onMethod_ = {@Autowired})
public class RoleResourceServiceImpl extends ServiceImpl<RoleResourceMapper, RoleResource> implements RoleResourceService {


    /**
     * 角色资源表
     */
    private RoleResourceMapper roleResourceMapper;


    /**
     * 角色资源表
     */
    private RoleResourceServiceResults roleResourceServiceResults;


    /**
     * redis操作
     */
    private StringRedisTemplate stringRedisTemplate;


    /**
     * 资源表
     */
    private ResourceService resourceService;


    /**
     * 集合条件查询
     *
     * @param roleResourceQuery:
     * @return java.util.List<com.entity.RoleResourceVO>
     * @author 孙凯伦
     * @since 2021-10-21 01:59:42
     */
    @Override
    public List<RoleResourceVO> listEnhance(RoleResourceQuery roleResourceQuery) {
        RoleResource roleResource = GeneralConvertor.convertor(roleResourceQuery, RoleResource.class);
        QueryWrapper<RoleResource> queryWrapper = new QueryWrapper<>(roleResource);
        // TODO 自动生成查询，禁止手动写语句
        RoleResourceServiceQuery.query(roleResourceQuery, queryWrapper);
        // TODO 人工查询条件
        queryArtificial(roleResourceQuery, queryWrapper);
        //DO数据
        List<RoleResource> roleResourceDO = roleResourceMapper.selectList(queryWrapper);
        //VO数据
        List<RoleResourceVO> roleResourceVO = GeneralConvertor.convertor(roleResourceDO, RoleResourceVO.class);
        return roleResourceServiceResults.assignment(roleResourceVO);
    }


    /**
     * 分页条件查询
     *
     * @param page:
     * @param roleResourceQuery:
     * @return com.baomidou.mybatisplus.core.metadata.IPage
     * @author 孙凯伦
     * @since 2021-10-21 01:59:42
     */
    @Override
    public Page<RoleResourceVO> pageEnhance(Page page, RoleResourceQuery roleResourceQuery) {
        RoleResource roleResource = GeneralConvertor.convertor(roleResourceQuery, RoleResource.class);
        QueryWrapper<RoleResource> queryWrapper = new QueryWrapper<>(roleResource);
        //TODO 自动生成查询，禁止手动写语句
        RoleResourceServiceQuery.query(roleResourceQuery, queryWrapper);
        // TODO 人工查询条件
        queryArtificial(roleResourceQuery, queryWrapper);
        //DO数据
        Page<RoleResource> pageDO = roleResourceMapper.selectPage(page, queryWrapper);
        //VO数据
        Page<RoleResourceVO> pageVO = roleResourceServiceResults.toPageVO(pageDO);
        return roleResourceServiceResults.assignment(pageVO);
    }


    /**
     * 单条条件查询
     *
     * @param roleResourceQuery:
     * @return java.util.List<com.entity.RoleResourceVO>
     * @author 孙凯伦
     * @since 2021-10-21 01:59:42
     */
    @Override
    public RoleResourceVO getOneEnhance(RoleResourceQuery roleResourceQuery) {
        RoleResource roleResource = GeneralConvertor.convertor(roleResourceQuery, RoleResource.class);
        QueryWrapper<RoleResource> queryWrapper = new QueryWrapper<>(roleResource);
        //TODO 自动生成查询，禁止手动写语句
        RoleResourceServiceQuery.query(roleResourceQuery, queryWrapper);
        // TODO 人工查询条件
        queryArtificial(roleResourceQuery, queryWrapper);
        //DO数据
        RoleResource roleResourceDO = roleResourceMapper.selectOne(queryWrapper);
        //VO数据
        RoleResourceVO roleResourceVO = GeneralConvertor.convertor(roleResourceDO, RoleResourceVO.class);
        return roleResourceServiceResults.assignment(roleResourceVO);
    }


    /**
     * 总数
     *
     * @param roleResourceQuery:
     * @return java.lang.Integer
     * @author 孙凯伦
     * @since 2021-10-21 01:59:42
     */
    @Override
    public Long countEnhance(RoleResourceQuery roleResourceQuery) {
        RoleResource roleResource = GeneralConvertor.convertor(roleResourceQuery, RoleResource.class);
        QueryWrapper<RoleResource> queryWrapper = new QueryWrapper<>(roleResource);
        //TODO 自动生成查询，禁止手动写语句
        RoleResourceServiceQuery.query(roleResourceQuery, queryWrapper);
        // TODO 人工查询条件
        queryArtificial(roleResourceQuery, queryWrapper);
        return roleResourceMapper.selectCount(queryWrapper);
    }


    /**
     * 批量新增
     *
     * @param roleId
     * @param resourceList
     * @param httpServletRequest
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, timeout = 36000, rollbackFor = Exception.class)
    public String saveBatchEnhance(String roleId, String[] resourceList, Boolean api, HttpServletRequest httpServletRequest) {
        //删除所有
        removeEnhance(new RoleResourceBO() {{
            setRoleId(roleId);
            setApi(api);
        }});
        //缓存取出用户
        Map<String, Object> u = RedisUtils.getToken(stringRedisTemplate, httpServletRequest);
        for (String resourceId : resourceList) {
            recursive(roleId, resourceId, Objects.nonNull(u) ? u.get("name") + "-" + u.get("id") : "");
        }
        return roleId;
    }


    /**
     * 新增
     *
     * @param roleResourceBO:
     * @return java.lang.String
     * @author 孙凯伦
     * @since 2021-10-21 01:59:42
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, timeout = 36000, rollbackFor = Exception.class)
    public String saveEnhance(RoleResourceBO roleResourceBO) {
        RoleResource roleResource = GeneralConvertor.convertor(roleResourceBO, RoleResource.class);
        roleResourceMapper.insert(roleResource);
        return roleResource.getId();
    }


    /**
     * 修改
     *
     * @param roleResourceBO:
     * @return java.lang.Boolean
     * @author 孙凯伦
     * @since 2021-10-21 01:59:42
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, timeout = 36000, rollbackFor = Exception.class)
    public Boolean updateEnhance(RoleResourceBO roleResourceBO) {
        RoleResource roleResource = GeneralConvertor.convertor(roleResourceBO, RoleResource.class);
        UpdateWrapper<RoleResource> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id", roleResourceBO.getId());
        Integer i = roleResourceMapper.update(roleResource, updateWrapper);
        return i > 0 ? true : false;
    }


    /**
     * 删除
     *
     * @param roleResourceBO:
     * @return java.lang.Boolean
     * @author 孙凯伦
     * @since 2021-10-21 01:59:42
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, timeout = 36000, rollbackFor = Exception.class)
    public Boolean removeEnhance(RoleResourceBO roleResourceBO) {
        RoleResource roleResource = GeneralConvertor.convertor(roleResourceBO, RoleResource.class);
        QueryWrapper<RoleResource> queryWrapper = new QueryWrapper<>(roleResource);
        if (roleResourceBO.getApi() != null) {
            queryWrapper.inSql("`resource_id`", "select `id` from `resource` where `api` =" + Convert.toStr(roleResourceBO.getApi()));
        }
        Integer i = roleResourceMapper.delete(queryWrapper);
        return i > 0 ? true : false;
    }


    /**
     * 查询人工查询条件
     *
     * @param roleResourceQuery 角色资源表
     * @return QueryWrapper
     * @author 孙凯伦
     * @since 2021-10-21 01:59:42
     */
    private QueryWrapper queryArtificial(RoleResourceQuery roleResourceQuery, QueryWrapper<RoleResource> queryWrapper) {
        return queryWrapper;
    }


    /**
     * 递归新增
     *
     * @param roleId
     * @param resourceId
     */
    public void recursive(String roleId, String resourceId, String createName) {
        ResourceVO resourceVO = resourceService.getOneEnhance(new ResourceQuery() {{
            setId(resourceId);
        }});
        Long i = countEnhance(new RoleResourceQuery() {{
            setRoleId(roleId);
            setResourceId(resourceVO.getId());
        }});
        if (resourceVO.getSuperiorsId().equals(String.valueOf(NumericEnum.ZERO.getValue()))) {
            if (i <= 0) {
                //新增父级数据
                save(new RoleResource() {{
                    setRoleId(roleId);
                    setResourceId(resourceVO.getId());
                    setCreateName(createName);
                }});
            }
        } else {
            if (i <= 0) {
                //新增父级数据
                save(new RoleResource() {{
                    setRoleId(roleId);
                    setResourceId(resourceVO.getId());
                    setCreateName(createName);
                }});
            }
            if (!resourceVO.getSuperiorsId().equals(String.valueOf(NumericEnum.ZERO.getValue()))) {
                //递归到上级
                recursive(roleId, resourceVO.getSuperiorsId(), createName);
            }
        }
    }
}