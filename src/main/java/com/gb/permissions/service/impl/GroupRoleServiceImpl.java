package com.gb.permissions.service.impl;

import com.gb.permissions.entity.query.GroupRoleQuery;
import com.gb.permissions.entity.vo.GroupRoleVO;
import com.gb.permissions.entity.bo.GroupRoleBO;
import com.gb.permissions.entity.GroupRole;
import com.gb.permissions.mapper.GroupRoleMapper;
import com.gb.permissions.service.GroupRoleService;
import com.gb.permissions.service.query.GroupRoleServiceQuery;
import com.gb.permissions.service.results.GroupRoleServiceResults;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Setter;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import com.gb.utils.GeneralConvertor;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * @author:     	孙凯伦
 * @since:   	    2021-10-21 01:59:44
 * @description:	TODO  角色用户组，Service服务实现层
 * @source:  	    代码生成器
 */
@Slf4j
@Service
@Setter(onMethod_ = {@Autowired})
public class GroupRoleServiceImpl extends ServiceImpl<GroupRoleMapper, GroupRole> implements GroupRoleService {


    /**
     * 角色用户组
     */
    private GroupRoleMapper groupRoleMapper;


    /**
     * 角色用户组
     */
    private GroupRoleServiceResults groupRoleServiceResults;


    /**
     * 集合条件查询
     * @author  孙凯伦
     * @since   2021-10-21 01:59:44
     * @param   groupRoleQuery:
     * @return  java.util.List<com.entity.GroupRoleVO>
     */
    @Override
    public List<GroupRoleVO> listEnhance(GroupRoleQuery groupRoleQuery) {
        GroupRole groupRole = GeneralConvertor.convertor(groupRoleQuery, GroupRole.class);
        QueryWrapper<GroupRole> queryWrapper = new QueryWrapper<>(groupRole);
        // TODO 自动生成查询，禁止手动写语句
        GroupRoleServiceQuery.query(groupRoleQuery, queryWrapper);
        // TODO 人工查询条件
        queryArtificial(groupRoleQuery, queryWrapper);
        //DO数据
        List<GroupRole> groupRoleDO = groupRoleMapper.selectList(queryWrapper);
        //VO数据
        List<GroupRoleVO> groupRoleVO = GeneralConvertor.convertor(groupRoleDO, GroupRoleVO.class);
        return groupRoleServiceResults.assignment(groupRoleVO);
    }


    /**
     * 分页条件查询
     * @author  孙凯伦
     * @since   2021-10-21 01:59:44
     * @param   page:
     * @param   groupRoleQuery:
     * @return  com.baomidou.mybatisplus.core.metadata.IPage
     */
    @Override
    public Page<GroupRoleVO> pageEnhance(Page page, GroupRoleQuery groupRoleQuery) {
        GroupRole groupRole = GeneralConvertor.convertor(groupRoleQuery, GroupRole.class);
        QueryWrapper<GroupRole> queryWrapper = new QueryWrapper<>(groupRole);
        //TODO 自动生成查询，禁止手动写语句
        GroupRoleServiceQuery.query(groupRoleQuery, queryWrapper);
        // TODO 人工查询条件
        queryArtificial(groupRoleQuery, queryWrapper);
        //DO数据
        Page<GroupRole> pageDO = groupRoleMapper.selectPage(page, queryWrapper);
        //VO数据
        Page<GroupRoleVO> pageVO = groupRoleServiceResults.toPageVO(pageDO);
        return groupRoleServiceResults.assignment(pageVO);
    }


    /**
     * 单条条件查询
     * @author  孙凯伦
     * @since   2021-10-21 01:59:44
     * @param   groupRoleQuery:
     * @return  java.util.List<com.entity.GroupRoleVO>
     */
    @Override
    public GroupRoleVO getOneEnhance(GroupRoleQuery groupRoleQuery) {
        GroupRole groupRole = GeneralConvertor.convertor(groupRoleQuery, GroupRole.class);
        QueryWrapper<GroupRole> queryWrapper = new QueryWrapper<>(groupRole);
        //TODO 自动生成查询，禁止手动写语句
        GroupRoleServiceQuery.query(groupRoleQuery, queryWrapper);
        // TODO 人工查询条件
        queryArtificial(groupRoleQuery, queryWrapper);
        //DO数据
        GroupRole groupRoleDO = groupRoleMapper.selectOne(queryWrapper);
        //VO数据
        GroupRoleVO groupRoleVO = GeneralConvertor.convertor(groupRoleDO, GroupRoleVO.class);
        return groupRoleServiceResults.assignment(groupRoleVO);
    }


    /**
     * 总数
     * @author  孙凯伦
     * @since   2021-10-21 01:59:44
     * @param   groupRoleQuery:
     * @return  java.lang.Integer
     */
    @Override
    public Long countEnhance(GroupRoleQuery groupRoleQuery) {
        GroupRole groupRole = GeneralConvertor.convertor(groupRoleQuery, GroupRole.class);
        QueryWrapper<GroupRole> queryWrapper = new QueryWrapper<>(groupRole);
        //TODO 自动生成查询，禁止手动写语句
        GroupRoleServiceQuery.query(groupRoleQuery, queryWrapper);
        // TODO 人工查询条件
        queryArtificial(groupRoleQuery, queryWrapper);
        return groupRoleMapper.selectCount(queryWrapper);
    }


    /**
     * 新增
     * @author  孙凯伦
     * @since   2021-10-21 01:59:44
     * @param   groupRoleBO:
     * @return  java.lang.String
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, timeout = 36000, rollbackFor = Exception.class)
    public String saveEnhance(GroupRoleBO groupRoleBO) {
        GroupRole groupRole = GeneralConvertor.convertor(groupRoleBO, GroupRole.class);
        groupRoleMapper.insert(groupRole);
        return groupRole.getId();
    }


    /**
     * 修改
     * @author  孙凯伦
     * @since   2021-10-21 01:59:44
     * @param   groupRoleBO:
     * @return  java.lang.Boolean
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, timeout = 36000, rollbackFor = Exception.class)
    public Boolean updateEnhance(GroupRoleBO groupRoleBO) {
        GroupRole groupRole = GeneralConvertor.convertor(groupRoleBO, GroupRole.class);
        UpdateWrapper<GroupRole > updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id", groupRoleBO.getId());
        Integer i = groupRoleMapper.update(groupRole, updateWrapper);
        return i > 0 ? true : false;
    }


    /**
     * 删除
     * @author  孙凯伦
     * @since   2021-10-21 01:59:44
     * @param   groupRoleBO:
     * @return  java.lang.Boolean
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, timeout = 36000, rollbackFor = Exception.class)
    public Boolean removeEnhance(GroupRoleBO groupRoleBO) {
        GroupRole groupRole = GeneralConvertor.convertor(groupRoleBO, GroupRole.class);
        QueryWrapper<GroupRole> queryWrapper = new QueryWrapper<>(groupRole);
        Integer i = groupRoleMapper.delete(queryWrapper);
        return i > 0 ? true : false;
    }


    /**
     * 查询人工查询条件
     *
     * @author     	孙凯伦
     * @since   	2021-10-21 01:59:44
     * @param       groupRoleQuery 角色用户组
     * @return      QueryWrapper
     */
     private QueryWrapper queryArtificial(GroupRoleQuery groupRoleQuery, QueryWrapper<GroupRole> queryWrapper) {
        return queryWrapper;
    }
}