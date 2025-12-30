package com.gb.permissions.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gb.permissions.entity.Resource;
import com.gb.permissions.entity.TreeNode;
import com.gb.permissions.entity.bo.ResourceBO;
import com.gb.permissions.entity.query.ResourceQuery;
import com.gb.permissions.entity.vo.ResourceVO;

import java.util.List;


/**
 * Created with IntelliJ IDEA.
 *
 * @author: 孙凯伦
 * @since: 2021-10-21 01:59:45
 * @description: TODO 资源表，Service服务接口层
 * @source: 代码生成器
 */
public interface ResourceService extends IService<Resource> {


    /**
     * 获取角色对应的菜单列表
     * @param userId
     * @param advanceSelected
     * @param roleId
     * @param appCode
     * @param api
     * @auther: 孙凯伦
     * @mobile: 13777579028
     * @email: 376253703@qq.com
     * @name: roleResource
     * @description: TODO  角色资源--获取角色对应的菜单列表
     * @return: java.util.List<com.gb.permissions.entity.TreeNode>
     * @date: 2021/10/26 4:46 下午
     */
    List<TreeNode> roleResource(String userId, Boolean advanceSelected, String roleId, String appCode, Boolean api);


    /**
     * 获得用户资源
     *
     * @param userId:
     * @param api:
     * @param appCode:
     * @param superiorsId:
     * @return java.util.List<com.entity.Resource>
     * @author sunkailun
     * @DateTime 2020/1/2  3:05 下午
     * @email 376253703@qq.com
     * @phone 13777579028
     */
    List<ResourceVO> userResource(String userId, Boolean api, String appCode, String superiorsId);


    /**
     * 获取最大账户的权限
     *
     * @param isBacAccount: 是否是后台账号
     * @param api: 是否是API
     * @return java.util.List<com.entity.Resource>
     * @author sunkailun
     * @DateTime 2020/1/2  3:05 下午
     * @email 376253703@qq.com
     * @phone 13777579028
     */
    List<ResourceVO> bigPermissionResource(Boolean isBacAccount, Boolean api);

    /**
     * 集合条件查询
     *
     * @param resourceQuery:
     * @return java.util.List<com.entity.ResourceVO>
     * @author 孙凯伦
     * @since 2021-10-21 01:59:45
     */
    List<ResourceVO> listEnhance(ResourceQuery resourceQuery);


    /**
     * 分页条件查询
     *
     * @param page:
     * @param resourceQuery:
     * @return com.baomidou.mybatisplus.extension.plugins.pagination.Page
     * @author 孙凯伦
     * @since 2021-10-21 01:59:45
     */
    Page<ResourceVO> pageEnhance(Page page, ResourceQuery resourceQuery);


    /**
     * 单条条件查询
     *
     * @param resourceQuery:
     * @return java.util.List<com.entity.ResourceVO>
     * @author 孙凯伦
     * @since 2021-10-21 01:59:45
     */
    ResourceVO getOneEnhance(ResourceQuery resourceQuery);


    /**
     * 总数
     *
     * @param resourceQuery:
     * @return java.lang.Integer
     * @author 孙凯伦
     * @since 2021-10-21 01:59:45
     */
    Long countEnhance(ResourceQuery resourceQuery);


    /**
     * 新增
     *
     * @param resourceBO:
     * @return java.lang.String
     * @author 孙凯伦
     * @since 2021-10-21 01:59:45
     */
    String saveEnhance(ResourceBO resourceBO);


    /**
     * 修改
     *
     * @param resourceBO:
     * @return java.lang.Boolean
     * @author 孙凯伦
     * @since 2021-10-21 01:59:45
     */
    Boolean updateEnhance(ResourceBO resourceBO);


    /**
     * 删除
     *
     * @param resourceBO:
     * @return java.lang.Boolean
     * @author 孙凯伦
     * @since 2021-10-21 01:59:45
     */
    Boolean removeEnhance(ResourceBO resourceBO);
}
