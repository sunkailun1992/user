package com.gb.permissions.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gb.account.entity.bo.UserRoleBO;
import com.gb.account.entity.query.UserRoleQuery;
import com.gb.account.service.UserRoleService;
import com.gb.permissions.entity.Role;
import com.gb.permissions.entity.bo.RoleBO;
import com.gb.permissions.entity.bo.RoleResourceBO;
import com.gb.permissions.entity.query.RoleQuery;
import com.gb.permissions.entity.vo.RoleVO;
import com.gb.permissions.mapper.RoleMapper;
import com.gb.permissions.service.RoleResourceService;
import com.gb.permissions.service.RoleService;
import com.gb.permissions.service.query.RoleServiceQuery;
import com.gb.permissions.service.results.RoleServiceResults;
import com.gb.utils.GeneralConvertor;
import com.gb.utils.exception.BusinessException;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: 孙凯伦
 * @since: 2021-10-21 01:59:43
 * @description: TODO 角色表，Service服务实现层
 * @source: 代码生成器
 */
@Slf4j
@Service
@Setter(onMethod_ = {@Autowired})
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {


    /**
     * 角色表
     */
    private RoleMapper roleMapper;


    /**
     * 角色表
     */
    private RoleServiceResults roleServiceResults;


    /**
     * 角色资源表
     */
    private RoleResourceService roleResourceService;

    /**
     * 用户角色表
     */
    private UserRoleService userRoleService;


    /**
     * 集合条件查询
     *
     * @param roleQuery:
     * @return java.util.List<com.entity.RoleVO>
     * @author 孙凯伦
     * @since 2021-10-21 01:59:43
     */
    @Override
    public List<RoleVO> listEnhance(RoleQuery roleQuery) {
        Role role = GeneralConvertor.convertor(roleQuery, Role.class);
        QueryWrapper<Role> queryWrapper = new QueryWrapper<>(role);
        // TODO 自动生成查询，禁止手动写语句
        RoleServiceQuery.query(roleQuery, queryWrapper);
        // TODO 人工查询条件
        queryArtificial(roleQuery, queryWrapper);
        //DO数据
        List<Role> roleDO = roleMapper.selectList(queryWrapper);
        //VO数据
        List<RoleVO> roleVOList = GeneralConvertor.convertor(roleDO, RoleVO.class);
        return roleServiceResults.assignment(roleVOList);
    }


    /**
     * 分页条件查询
     *
     * @param page:
     * @param roleQuery:
     * @return com.baomidou.mybatisplus.core.metadata.IPage
     * @author 孙凯伦
     * @since 2021-10-21 01:59:43
     */
    @Override
    public Page<RoleVO> pageEnhance(Page page, RoleQuery roleQuery) {
        Role role = GeneralConvertor.convertor(roleQuery, Role.class);
        QueryWrapper<Role> queryWrapper = new QueryWrapper<>(role);
        //TODO 自动生成查询，禁止手动写语句
        RoleServiceQuery.query(roleQuery, queryWrapper);
        // TODO 人工查询条件
        queryArtificial(roleQuery, queryWrapper);
        //DO数据
        Page<Role> pageDO = roleMapper.selectPage(page, queryWrapper);
        //VO数据
        Page<RoleVO> pageVO = roleServiceResults.toPageVO(pageDO);
        return roleServiceResults.assignment(pageVO);
    }


    /**
     * 单条条件查询
     *
     * @param roleQuery:
     * @return java.util.List<com.entity.RoleVO>
     * @author 孙凯伦
     * @since 2021-10-21 01:59:43
     */
    @Override
    public RoleVO getOneEnhance(RoleQuery roleQuery) {
        Role role = GeneralConvertor.convertor(roleQuery, Role.class);
        QueryWrapper<Role> queryWrapper = new QueryWrapper<>(role);
        //TODO 自动生成查询，禁止手动写语句
        RoleServiceQuery.query(roleQuery, queryWrapper);
        // TODO 人工查询条件
        queryArtificial(roleQuery, queryWrapper);
        //DO数据
        Role roleDO = roleMapper.selectOne(queryWrapper);
        //VO数据
        RoleVO roleVO = GeneralConvertor.convertor(roleDO, RoleVO.class);
        return roleServiceResults.assignment(roleVO);
    }


    /**
     * 总数
     *
     * @param roleQuery:
     * @return java.lang.Integer
     * @author 孙凯伦
     * @since 2021-10-21 01:59:43
     */
    @Override
    public Long countEnhance(RoleQuery roleQuery) {
        Role role = GeneralConvertor.convertor(roleQuery, Role.class);
        QueryWrapper<Role> queryWrapper = new QueryWrapper<>(role);
        //TODO 自动生成查询，禁止手动写语句
        RoleServiceQuery.query(roleQuery, queryWrapper);
        // TODO 人工查询条件
        queryArtificial(roleQuery, queryWrapper);
        return roleMapper.selectCount(queryWrapper);
    }


    /**
     * 新增
     *
     * @param roleBO:
     * @return java.lang.String
     * @author 孙凯伦
     * @since 2021-10-21 01:59:43
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, timeout = 36000, rollbackFor = Exception.class)
    public String saveEnhance(RoleBO roleBO) {
        //判断是否为空
        if (StringUtils.isNotBlank(roleBO.getValue())) {
            //限制
            Long x = countEnhance(new RoleQuery() {{
                setValue(roleBO.getValue());
            }});
            if (x > 0) {
                throw new BusinessException("角色编码重复");
            }
        }
        Role role = GeneralConvertor.convertor(roleBO, Role.class);
        roleMapper.insert(role);
        //角色资源
        roleResource(roleBO, role);
        //用户角色
        roleUser(roleBO, role);
        return role.getId();
    }


    /**
     * 修改
     *
     * @param roleBO:
     * @return java.lang.Boolean
     * @author 孙凯伦
     * @since 2021-10-21 01:59:43
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, timeout = 36000, rollbackFor = Exception.class)
    public Boolean updateEnhance(RoleBO roleBO) {
        //默认不修改code
        roleBO.setValue(null);
        //校验
        if (StringUtils.isNotBlank(roleBO.getId())) {
            Long i = userRoleService.countEnhance(new UserRoleQuery() {{
                setRoleId(roleBO.getId());
            }});
            if (i > 0) {
                RoleVO roleVO = getOneEnhance(new RoleQuery() {{
                    setId(roleBO.getId());
                }});
                if (!StringUtils.equals(roleBO.getSystemId(), roleVO.getSystemId())) {
                    throw new BusinessException("该角色已关联用户，无法修改系统！");
                }
            }
        }
        Role role = GeneralConvertor.convertor(roleBO, Role.class);
        UpdateWrapper<Role> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id", roleBO.getId());
        Integer i = roleMapper.update(role, updateWrapper);
        //角色资源
        roleResource(roleBO, role);
        //用户角色
        roleUser(roleBO, role);
        return i > 0 ? true : false;
    }


    /**
     * 删除
     *
     * @param roleBO:
     * @return java.lang.Boolean
     * @author 孙凯伦
     * @since 2021-10-21 01:59:43
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, timeout = 36000, rollbackFor = Exception.class)
    public Boolean removeEnhance(RoleBO roleBO) {
        //校验
        if (StringUtils.isNotBlank(roleBO.getId())) {
            Long i = userRoleService.countEnhance(new UserRoleQuery() {{
                setRoleId(roleBO.getId());
            }});
            if (i > 0) {
                throw new BusinessException("删除角色已关联用户无法删除");
            }
        }
        Role role = GeneralConvertor.convertor(roleBO, Role.class);
        QueryWrapper<Role> queryWrapper = new QueryWrapper<>(role);
        Integer i = roleMapper.selectCount(queryWrapper).intValue();
        if(i == 0) {
            log.debug("待删除的角色未找到roleBO：{}，无须删除！", JSON.toJSONString(roleBO));
            return true;
        } else {
            i = roleMapper.delete(queryWrapper);
            if(i > 0){
                roleResourceService.removeEnhance(new RoleResourceBO(){{
                    setRoleId(roleBO.getId());
                }});
            }
        }
        return i > 0 ? true : false;
    }


    /**
     * 查询人工查询条件
     *
     * @param roleQuery 角色表
     * @return QueryWrapper
     * @author 孙凯伦
     * @since 2021-10-21 01:59:43
     */
    private QueryWrapper queryArtificial(RoleQuery roleQuery, QueryWrapper<Role> queryWrapper) {
        if (StringUtils.isNotBlank(roleQuery.getGroupId())) {
            queryWrapper.inSql("`id`", "select `role_id` from group_role where `is_delete` = 0 and`group_id` in(" + roleQuery.getGroupId() + ")");
        }
        if (CollectionUtils.isNotEmpty(roleQuery.getValueList())) {
            queryWrapper.in("`value`", roleQuery.getValueList());
        }
        return queryWrapper;
    }


    /**
     * @param roleBO
     * @param role
     * @auther: 孙凯伦
     * @mobile: 13777579028
     * @email: 376253703@qq.com
     * @name: roleResource
     * @description: TODO  新增修改角色资源
     * @return: void
     * @date: 2021/11/2 3:51 PM
     */
    private void roleResource(RoleBO roleBO, Role role) {
        if (ObjectUtil.isNotNull(roleBO.getResourceList())) {
            //删除角色资源
            roleResourceService.removeEnhance(new RoleResourceBO() {{
                setRoleId(role.getId());
            }});
            for (String resourceId : roleBO.getResourceList()) {
                //新增角色资源
                roleResourceService.saveEnhance(new RoleResourceBO() {{
                    setResourceId(resourceId);
                    setRoleId(role.getId());
                    setCreateName(roleBO.getCreateName());
                }});
            }
        }
    }


    /**
     * @param roleBO
     * @param role
     * @auther: 孙凯伦
     * @mobile: 13777579028
     * @email: 376253703@qq.com
     * @name: roleResource
     * @description: TODO  用户角色
     * @return: void
     * @date: 2021/11/2 3:51 PM
     */
    private void roleUser(RoleBO roleBO, Role role) {
        if (ObjectUtil.isNotNull(roleBO.getUserList())) {
            for (String userId : roleBO.getUserList()) {
                //新增角色资源
                userRoleService.saveEnhance(new UserRoleBO() {{
                    setUserId(userId);
                    setRoleId(role.getId());
                    setCreateName(roleBO.getCreateName());
                }});
            }
        }
    }
}