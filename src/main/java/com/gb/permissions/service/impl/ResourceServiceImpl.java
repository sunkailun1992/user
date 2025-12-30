package com.gb.permissions.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gb.permissions.entity.Resource;
import com.gb.permissions.entity.TreeNode;
import com.gb.permissions.entity.bo.ResourceBO;
import com.gb.permissions.entity.query.ResourceQuery;
import com.gb.permissions.entity.vo.ResourceVO;
import com.gb.permissions.mapper.ResourceMapper;
import com.gb.permissions.service.ResourceService;
import com.gb.permissions.service.query.ResourceServiceQuery;
import com.gb.permissions.service.results.ResourceServiceResults;
import com.gb.user.enums.RoleUserTypeRelatedEnum;
import com.gb.utils.GeneralConvertor;
import com.google.common.collect.Lists;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
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
 * @since: 2021-10-21 01:59:45
 * @description: TODO 资源表，Service服务实现层
 * @source: 代码生成器
 */
@Slf4j
@Service
@Setter(onMethod_ = {@Autowired})
public class ResourceServiceImpl extends ServiceImpl<ResourceMapper, Resource> implements ResourceService {


    /**
     * 资源表
     */
    private ResourceMapper resourceMapper;


    /**
     * 资源表
     */
    private ResourceServiceResults resourceServiceResults;

    @Override
    public List<TreeNode> roleResource(String userId, Boolean advanceSelected, String roleId, String systemId, Boolean api) {
        //获得的第一级菜单
        List<ResourceVO> menu = listEnhance(new ResourceQuery() {{
            setSystemId(systemId);
            setSuperiorsId("0");
            setApi(api);
        }});
        //菜单
        List<TreeNode> list = Lists.newArrayList();
        //递归
        recursive(userId, roleId, advanceSelected, menu, list, systemId, api);
        return list;
    }

    /**
     * 获得用户资源
     *
     * @param userId:
     * @return java.util.List<com.entity.Resource>
     * @author sunkailun
     * @DateTime 2020/1/2  3:05 下午
     * @email 376253703@qq.com
     * @phone 13777579028
     */
    @Override
    public List<ResourceVO> userResource(String userId, Boolean api, String appCode, String superiorsId) {
        List<Resource> resource = resourceMapper.userResource(userId, api, appCode, superiorsId);
        //VO数据
        List<ResourceVO> resourceVO = GeneralConvertor.convertor(resource, ResourceVO.class);
        return resourceVO;
    }

    @Override
    public List<ResourceVO> bigPermissionResource(Boolean isBacAccount, Boolean api) {
        List<Resource> resource = Lists.newArrayList();
        if(isBacAccount) {
            resource = resourceMapper.getRoleResource(RoleUserTypeRelatedEnum.工保网管理端角色.getRoleCode(), api);
        }else {
            resource = resourceMapper.getRoleResource(RoleUserTypeRelatedEnum.非正式经纪人.getRoleCode(), api);
        }
        List<ResourceVO> resourceVO = GeneralConvertor.convertor(resource, ResourceVO.class);
        return resourceVO;
    }

    /**
     * 集合条件查询
     *
     * @param resourceQuery:
     * @return java.util.List<com.entity.ResourceVO>
     * @author 孙凯伦
     * @since 2021-10-21 01:59:45
     */
    @Override
    public List<ResourceVO> listEnhance(ResourceQuery resourceQuery) {
        Resource resource = GeneralConvertor.convertor(resourceQuery, Resource.class);
        QueryWrapper<Resource> queryWrapper = new QueryWrapper<>(resource);
        // TODO 自动生成查询，禁止手动写语句
        ResourceServiceQuery.query(resourceQuery, queryWrapper);
        // TODO 人工查询条件
        queryArtificial(resourceQuery, queryWrapper);
        //DO数据
        List<Resource> resourceDO = resourceMapper.selectList(queryWrapper);
        //VO数据
        List<ResourceVO> resourceVO = GeneralConvertor.convertor(resourceDO, ResourceVO.class);
        return resourceServiceResults.assignment(resourceVO);
    }


    /**
     * 分页条件查询
     *
     * @param page:
     * @param resourceQuery:
     * @return com.baomidou.mybatisplus.core.metadata.IPage
     * @author 孙凯伦
     * @since 2021-10-21 01:59:45
     */
    @Override
    public Page<ResourceVO> pageEnhance(Page page, ResourceQuery resourceQuery) {
        Resource resource = GeneralConvertor.convertor(resourceQuery, Resource.class);
        QueryWrapper<Resource> queryWrapper = new QueryWrapper<>(resource);
        //TODO 自动生成查询，禁止手动写语句
        ResourceServiceQuery.query(resourceQuery, queryWrapper);
        // TODO 人工查询条件
        queryArtificial(resourceQuery, queryWrapper);
        //DO数据
        Page<Resource> pageDO = resourceMapper.selectPage(page, queryWrapper);
        //VO数据
        Page<ResourceVO> pageVO = resourceServiceResults.toPageVO(pageDO);
        return resourceServiceResults.assignment(pageVO);
    }


    /**
     * 单条条件查询
     *
     * @param resourceQuery:
     * @return java.util.List<com.entity.ResourceVO>
     * @author 孙凯伦
     * @since 2021-10-21 01:59:45
     */
    @Override
    public ResourceVO getOneEnhance(ResourceQuery resourceQuery) {
        Resource resource = GeneralConvertor.convertor(resourceQuery, Resource.class);
        QueryWrapper<Resource> queryWrapper = new QueryWrapper<>(resource);
        //TODO 自动生成查询，禁止手动写语句
        ResourceServiceQuery.query(resourceQuery, queryWrapper);
        // TODO 人工查询条件
        queryArtificial(resourceQuery, queryWrapper);
        //DO数据
        Resource resourceDO = resourceMapper.selectOne(queryWrapper);
        //VO数据
        ResourceVO resourceVO = GeneralConvertor.convertor(resourceDO, ResourceVO.class);
        return resourceServiceResults.assignment(resourceVO);
    }


    /**
     * 总数
     *
     * @param resourceQuery:
     * @return java.lang.Integer
     * @author 孙凯伦
     * @since 2021-10-21 01:59:45
     */
    @Override
    public Long countEnhance(ResourceQuery resourceQuery) {
        Resource resource = GeneralConvertor.convertor(resourceQuery, Resource.class);
        QueryWrapper<Resource> queryWrapper = new QueryWrapper<>(resource);
        //TODO 自动生成查询，禁止手动写语句
        ResourceServiceQuery.query(resourceQuery, queryWrapper);
        // TODO 人工查询条件
        queryArtificial(resourceQuery, queryWrapper);
        return resourceMapper.selectCount(queryWrapper);
    }


    /**
     * 新增
     *
     * @param resourceBO:
     * @return java.lang.String
     * @author 孙凯伦
     * @since 2021-10-21 01:59:45
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, timeout = 36000, rollbackFor = Exception.class)
    public String saveEnhance(ResourceBO resourceBO) {
        Resource resource = GeneralConvertor.convertor(resourceBO, Resource.class);
        resourceMapper.insert(resource);
        return resource.getId();
    }


    /**
     * 修改
     *
     * @param resourceBO:
     * @return java.lang.Boolean
     * @author 孙凯伦
     * @since 2021-10-21 01:59:45
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, timeout = 36000, rollbackFor = Exception.class)
    public Boolean updateEnhance(ResourceBO resourceBO) {
        Resource resource = GeneralConvertor.convertor(resourceBO, Resource.class);
        UpdateWrapper<Resource> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id", resourceBO.getId());
        Integer i = resourceMapper.update(resource, updateWrapper);
        return i > 0 ? true : false;
    }


    /**
     * 删除
     *
     * @param resourceBO:
     * @return java.lang.Boolean
     * @author 孙凯伦
     * @since 2021-10-21 01:59:45
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, timeout = 36000, rollbackFor = Exception.class)
    public Boolean removeEnhance(ResourceBO resourceBO) {
        Resource resource = GeneralConvertor.convertor(resourceBO, Resource.class);
        QueryWrapper<Resource> queryWrapper = new QueryWrapper<>(resource);
        Integer i = resourceMapper.delete(queryWrapper);
        return i > 0 ? true : false;
    }


    /**
     * 查询人工查询条件
     *
     * @param resourceQuery 资源表
     * @return QueryWrapper
     * @author 孙凯伦
     * @since 2021-10-21 01:59:45
     */
    private QueryWrapper queryArtificial(ResourceQuery resourceQuery, QueryWrapper<Resource> queryWrapper) {
        /**
         * 用户查询
         */
        if (resourceQuery.getUserId() != null) {
            queryWrapper
                    .inSql("id", "select `resource_id` from `role_resource` where `role_id` in (select `id` from `role` where `id` in ( select `role_id` from `user_role` where `user_id` = " + resourceQuery.getUserId() + " and `is_delete` = false) and `is_delete` = false) and `is_delete` = false")
                    .or()
                    .inSql("id", "select `resource_id` from `role_resource` where `role_id` in (select `role_id` from `group_role` where `group_id` in ( select `group_id` from `user_group` where `user_id` = " + resourceQuery.getUserId() + " and `is_delete` = false) and `is_delete` = false) and `is_delete` = false");
        }
        return queryWrapper;
    }


    /**
     * @param userId
     * @param roleId
     * @param advanceSelected
     * @param menu
     * @param list
     * @param systemId
     * @auther: 孙凯伦
     * @mobile: 13777579028
     * @email: 376253703@qq.com
     * @name: recursive
     * @description: TODO  递归
     * @return: void
     * @date: 2021/10/26 4:48 下午
     */
    private void recursive(String userId, String roleId, Boolean advanceSelected, List<ResourceVO> menu, List<TreeNode> list, String systemId, Boolean api) {
        for (ResourceVO resourceVO : menu) {
            //菜单对象
            TreeNode treeNode = new TreeNode();
            //放入树内容
            tree(advanceSelected, roleId, resourceVO, treeNode);
            //递归判断，是否有下级
            Long superiors = countEnhance(new ResourceQuery() {{
                setSystemId(systemId);
                setSuperiorsId(resourceVO.getId());
                setApi(api);
            }});
            //开始递归
            if (superiors > 0) {
                //递归集合
                List<TreeNode> recursive = Lists.newArrayList();
                //递归菜单
                List<ResourceVO> menuRecursive = listEnhance(new ResourceQuery() {{
                    setSystemId(systemId);
                    setSuperiorsId(resourceVO.getId());
                    setApi(api);
                }});
                //递归结果
                recursive(userId, roleId, advanceSelected, menuRecursive, recursive, systemId, api);
                //递归下级
                treeNode.setChildren(recursive);
            }
            //放入第一节点结合
            list.add(treeNode);
        }
    }


    /**
     * @param advanceSelected
     * @param roleId
     * @param resourceVO
     * @param treeNode
     * @auther: 孙凯伦
     * @mobile: 13777579028
     * @email: 376253703@qq.com
     * @name: tree
     * @description: TODO  树结构内容
     * @return: void
     * @date: 2021/10/26 4:50 下午
     */
    private void tree(Boolean advanceSelected, String roleId, ResourceVO resourceVO, TreeNode treeNode) {
        treeNode.setId(resourceVO.getId());
        treeNode.setName(resourceVO.getName());
        treeNode.setApi(resourceVO.getApi());
        treeNode.setButton(resourceVO.getButton());
        treeNode.setParentId(resourceVO.getSuperiorsId());
        if (advanceSelected) {
            //判断用户权限
            Long integer = countEnhance(new ResourceQuery() {{
                setId(resourceVO.getId());
                setRoleId(roleId);
            }});
            if (integer > 0) {
                treeNode.setState(new TreeNode.State() {{
                    setChecked(true);
                }});
            } else {
                treeNode.setState(new TreeNode.State() {{
                    setChecked(false);
                }});
            }
        }
    }


}