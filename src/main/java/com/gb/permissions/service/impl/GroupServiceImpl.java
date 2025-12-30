package com.gb.permissions.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gb.account.entity.bo.UserGroupBO;
import com.gb.account.entity.query.UserGroupQuery;
import com.gb.account.service.UserGroupService;
import com.gb.permissions.entity.Group;
import com.gb.permissions.entity.bo.GroupBO;
import com.gb.permissions.entity.bo.GroupRoleBO;
import com.gb.permissions.entity.query.GroupQuery;
import com.gb.permissions.entity.vo.GroupVO;
import com.gb.permissions.mapper.GroupMapper;
import com.gb.permissions.service.GroupRoleService;
import com.gb.permissions.service.GroupService;
import com.gb.permissions.service.query.GroupServiceQuery;
import com.gb.permissions.service.results.GroupServiceResults;
import com.gb.utils.GeneralConvertor;
import com.gb.utils.exception.BusinessException;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
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
 * @since: 2021-10-21 01:59:44
 * @description: TODO 组，Service服务实现层
 * @source: 代码生成器
 */
@Slf4j
@Service
@Setter(onMethod_ = {@Autowired})
public class GroupServiceImpl extends ServiceImpl<GroupMapper, Group> implements GroupService {


    /**
     * 组
     */
    private GroupMapper groupMapper;

    /**
     * 组
     */
    private GroupServiceResults groupServiceResults;

    /**
     * 角色用户组
     */
    private GroupRoleService groupRoleService;

    /**
     * 用户组
     */
    private UserGroupService userGroupService;


    /**
     * 集合条件查询
     *
     * @param groupQuery:
     * @return java.util.List<com.entity.GroupVO>
     * @author 孙凯伦
     * @since 2021-10-21 01:59:44
     */
    @Override
    public List<GroupVO> listEnhance(GroupQuery groupQuery) {
        Group group = GeneralConvertor.convertor(groupQuery, Group.class);
        QueryWrapper<Group> queryWrapper = new QueryWrapper<>(group);
        // TODO 自动生成查询，禁止手动写语句
        GroupServiceQuery.query(groupQuery, queryWrapper);
        // TODO 人工查询条件
        queryArtificial(groupQuery, queryWrapper);
        //DO数据
        List<Group> groupDO = groupMapper.selectList(queryWrapper);
        //VO数据
        List<GroupVO> groupVO = GeneralConvertor.convertor(groupDO, GroupVO.class);
        return groupServiceResults.assignment(groupVO);
    }


    /**
     * 分页条件查询
     *
     * @param page:
     * @param groupQuery:
     * @return com.baomidou.mybatisplus.core.metadata.IPage
     * @author 孙凯伦
     * @since 2021-10-21 01:59:44
     */
    @Override
    public Page<GroupVO> pageEnhance(Page page, GroupQuery groupQuery) {
        Group group = GeneralConvertor.convertor(groupQuery, Group.class);
        QueryWrapper<Group> queryWrapper = new QueryWrapper<>(group);
        //TODO 自动生成查询，禁止手动写语句
        GroupServiceQuery.query(groupQuery, queryWrapper);
        // TODO 人工查询条件
        queryArtificial(groupQuery, queryWrapper);
        //DO数据
        Page<Group> pageDO = groupMapper.selectPage(page, queryWrapper);
        //VO数据
        Page<GroupVO> pageVO = groupServiceResults.toPageVO(pageDO);
        return groupServiceResults.assignment(pageVO);
    }


    /**
     * 单条条件查询
     *
     * @param groupQuery:
     * @return java.util.List<com.entity.GroupVO>
     * @author 孙凯伦
     * @since 2021-10-21 01:59:44
     */
    @Override
    public GroupVO getOneEnhance(GroupQuery groupQuery) {
        Group group = GeneralConvertor.convertor(groupQuery, Group.class);
        QueryWrapper<Group> queryWrapper = new QueryWrapper<>(group);
        //TODO 自动生成查询，禁止手动写语句
        GroupServiceQuery.query(groupQuery, queryWrapper);
        // TODO 人工查询条件
        queryArtificial(groupQuery, queryWrapper);
        //DO数据
        Group groupDO = groupMapper.selectOne(queryWrapper);
        //VO数据
        GroupVO groupVO = GeneralConvertor.convertor(groupDO, GroupVO.class);
        return groupServiceResults.assignment(groupVO);
    }


    /**
     * 总数
     *
     * @param groupQuery:
     * @return java.lang.Integer
     * @author 孙凯伦
     * @since 2021-10-21 01:59:44
     */
    @Override
    public Long countEnhance(GroupQuery groupQuery) {
        Group group = GeneralConvertor.convertor(groupQuery, Group.class);
        QueryWrapper<Group> queryWrapper = new QueryWrapper<>(group);
        //TODO 自动生成查询，禁止手动写语句
        GroupServiceQuery.query(groupQuery, queryWrapper);
        // TODO 人工查询条件
        queryArtificial(groupQuery, queryWrapper);
        return groupMapper.selectCount(queryWrapper);
    }


    /**
     * 新增
     *
     * @param groupBO:
     * @return java.lang.String
     * @author 孙凯伦
     * @since 2021-10-21 01:59:44
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, timeout = 36000, rollbackFor = Exception.class)
    public String saveEnhance(GroupBO groupBO) {
        Group group = GeneralConvertor.convertor(groupBO, Group.class);
        groupMapper.insert(group);
        //角色组
        groupRole(groupBO, group);
        //用户组
        groupUser(groupBO, group);
        return group.getId();
    }


    /**
     * 修改
     *
     * @param groupBO:
     * @return java.lang.Boolean
     * @author 孙凯伦
     * @since 2021-10-21 01:59:44
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, timeout = 36000, rollbackFor = Exception.class)
    public Boolean updateEnhance(GroupBO groupBO) {
        //校验
        if (StringUtils.isNotBlank(groupBO.getId())) {
            Long i = userGroupService.countEnhance(new UserGroupQuery() {{
                setGroupId(groupBO.getId());
            }});
            if (i > 0) {
                GroupVO groupVO = getOneEnhance(new GroupQuery() {{
                    setId(groupBO.getId());
                }});
                if (!StringUtils.equals(groupBO.getSystemId(), groupVO.getSystemId())) {
                    throw new BusinessException("该用户组已关联用户，无法修改系统！");
                }
            }
        }
        Group group = GeneralConvertor.convertor(groupBO, Group.class);
        UpdateWrapper<Group> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id", groupBO.getId());
        Integer i = groupMapper.update(group, updateWrapper);
        //角色组
        groupRole(groupBO, group);
        //用户组
        groupUser(groupBO, group);
        return i > 0 ? true : false;
    }


    /**
     * 删除
     *
     * @param groupBO:
     * @return java.lang.Boolean
     * @author 孙凯伦
     * @since 2021-10-21 01:59:44
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, timeout = 36000, rollbackFor = Exception.class)
    public Boolean removeEnhance(GroupBO groupBO) {
        //校验
        if (StringUtils.isNotBlank(groupBO.getId())) {
            Long i = userGroupService.countEnhance(new UserGroupQuery() {{
                setGroupId(groupBO.getId());
            }});
            if (i > 0) {
                throw new BusinessException("删除组已关联用户无法删除");
            }
        }
        Group group = GeneralConvertor.convertor(groupBO, Group.class);
        QueryWrapper<Group> queryWrapper = new QueryWrapper<>(group);
        Integer i = groupMapper.delete(queryWrapper);
        return i > 0 ? true : false;
    }


    /**
     * 查询人工查询条件
     *
     * @param groupQuery 组
     * @return QueryWrapper
     * @author 孙凯伦
     * @since 2021-10-21 01:59:44
     */
    private QueryWrapper queryArtificial(GroupQuery groupQuery, QueryWrapper<Group> queryWrapper) {
        return queryWrapper;
    }


    /**
     * @param groupBO
     * @param group
     * @auther: 孙凯伦
     * @mobile: 13777579028
     * @email: 376253703@qq.com
     * @name: groupRole
     * @description: TODO  角色组
     * @return: void
     * @date: 2021/11/2 3:43 PM
     */
    private void groupRole(GroupBO groupBO, Group group) {
        if (ObjectUtil.isNotNull(groupBO.getRoleList())) {
            //删除组角色
            groupRoleService.removeEnhance(new GroupRoleBO() {{
                setGroupId(group.getId());
            }});
            for (String role : groupBO.getRoleList()) {
                //新增组角色
                groupRoleService.saveEnhance(new GroupRoleBO() {{
                    setRoleId(role);
                    setGroupId(group.getId());
                    setCreateName(groupBO.getCreateName());
                }});
            }
        }
    }


    /**
     * @param groupBO
     * @param group
     * @auther: 孙凯伦
     * @mobile: 13777579028
     * @email: 376253703@qq.com
     * @name: groupRole
     * @description: TODO  用户组
     * @return: void
     * @date: 2021/11/2 3:43 PM
     */
    private void groupUser(GroupBO groupBO, Group group) {
        if (ObjectUtil.isNotNull(groupBO.getUserList())) {
            for (String userId : groupBO.getUserList()) {
                //新增组角色
                userGroupService.saveEnhance(new UserGroupBO() {{
                    setUserId(userId);
                    setGroupId(group.getId());
                    setCreateName(groupBO.getCreateName());
                }});
            }
        }
    }
}