package com.gb.permissions.service.results;

import com.gb.permissions.entity.Group;
import com.gb.permissions.entity.query.RoleQuery;
import com.gb.permissions.entity.vo.GroupVO;
import com.gb.permissions.entity.bo.GroupBO;
import com.gb.permissions.service.RoleService;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import com.gb.utils.GeneralConvertor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * Created with IntelliJ IDEA.
 *
 * @author: 孙凯伦
 * @since: 2021-10-21 01:59:44
 * @description: TODO 组,Service返回实现
 * @source: 代码生成器
 */
@Slf4j
@Service
@Setter(onMethod_ = {@Autowired})
public class GroupServiceResults {

    /**
     * 角色表
     */
    private RoleService roleService;


    /**
     * 单条，增强返回参数追加
     *
     * @param groupVO 组
     * @return GroupVO
     * @author 孙凯伦
     * @since 2021-10-21 01:59:44
     */
    public GroupVO assignment(GroupVO groupVO) {
        if (groupVO != null) {
            groupVO.setRoleList(roleService.listEnhance(new RoleQuery() {{
                setGroupId(groupVO.getId());
            }}));
            return groupVO;
        } else {
            return groupVO;
        }
    }


    /**
     * 分页，增强返回参数追加
     *
     * @param groupVOList 组
     * @return Page<GroupVO>
     * @author 孙凯伦
     * @since 2021-10-21 01:59:44
     */
    public Page<GroupVO> assignment(Page<GroupVO> groupVOList) {
        groupVOList.getRecords().forEach(groupVO -> {
            groupVO.setRoleList(roleService.listEnhance(new RoleQuery() {{
                setGroupId(groupVO.getId());
            }}));
        });
        return groupVOList;
    }


    /**
     * 集合，增强返回参数追加
     *
     * @param groupVOList 组
     * @return List<GroupVO>
     * @author 孙凯伦
     * @since 2021-10-21 01:59:44
     */
    public List<GroupVO> assignment(List<GroupVO> groupVOList) {
        groupVOList.forEach(groupVO -> {
            groupVO.setRoleList(roleService.listEnhance(new RoleQuery() {{
                setGroupId(groupVO.getId());
            }}));
        });
        return groupVOList;
    }


    /**
     * DO转化VO
     *
     * @param pageDO 组
     * @return com.baomidou.mybatisplus.extension.plugins.pagination.Page
     * @author 孙凯伦
     * @since 2021-10-21 01:59:44
     */
    public Page<GroupVO> toPageVO(Page<Group> pageDO) {
        Page<GroupVO> pageVO = new Page<GroupVO>();
        pageVO.setRecords(GeneralConvertor.convertor(pageDO.getRecords(), GroupVO.class));
        pageVO.setCurrent(pageDO.getCurrent());
        pageVO.setPages(pageDO.getPages());
        pageVO.setSize(pageDO.getSize());
        pageVO.setTotal(pageDO.getTotal());
        return pageVO;
    }


}