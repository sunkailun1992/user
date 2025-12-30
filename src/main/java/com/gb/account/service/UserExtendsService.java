package com.gb.account.service;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gb.account.entity.UserExtends;
import com.gb.account.entity.bo.UserExtendsBO;
import com.gb.account.entity.query.UserExtendsQuery;
import com.gb.account.entity.vo.UserExtendsVO;

import java.util.List;


/**
 * Created with IntelliJ IDEA.
 * @author:     	孙凯伦
 * @since:   	    2021-10-21 01:50:40
 * @description:	TODO  用户扩展表，Service服务接口层
 * @source:  	    代码生成器
 */
public interface UserExtendsService extends IService<UserExtends> {


    /**
     * 集合条件查询
     * @author  孙凯伦
     * @since   2021-10-21 01:50:40
     * @param   userExtendsQuery:
     * @return  java.util.List<com.entity.UserExtendsVO>
     */
    List<UserExtendsVO> listEnhance(UserExtendsQuery userExtendsQuery);


    /**
     * 分页条件查询
     * @author  孙凯伦
     * @since   2021-10-21 01:50:40
     * @param   page:
     * @param   userExtendsQuery:
     * @return  com.baomidou.mybatisplus.extension.plugins.pagination.Page
     */
    Page<UserExtendsVO> pageEnhance(Page page, UserExtendsQuery userExtendsQuery);


    /**
     * 单条条件查询
     * @author  孙凯伦
     * @since   2021-10-21 01:50:40
     * @param   userExtendsQuery:
     * @return  java.util.List<com.entity.UserExtendsVO>
     */
    UserExtendsVO getOneEnhance(UserExtendsQuery userExtendsQuery);


    /**
     * 总数
     * @author  孙凯伦
     * @since   2021-10-21 01:50:40
     * @param   userExtendsQuery:
     * @return  java.lang.Integer
     */
    Long countEnhance(UserExtendsQuery userExtendsQuery);


    /**
     * 新增
     * @author  孙凯伦
     * @since   2021-10-21 01:50:40
     * @param   userExtendsBO:
     * @return  java.lang.String
     */
     String saveEnhance(UserExtendsBO userExtendsBO);


    /**
     * 修改
     * @author  孙凯伦
     * @since   2021-10-21 01:50:40
     * @param   userExtendsBO: 用户扩展信息
     * @param   userName: 登录账户名
     * @param   newUserName: 新的登录账户名
     * @param   saveUserExtends: 是否添加用户扩展信息表
     */
    void updateEnhance(UserExtendsBO userExtendsBO, String userName, String newUserName);


    /**
     * 删除
     * @author  孙凯伦
     * @since   2021-10-21 01:50:40
     * @param   userExtendsBO:
     * @return  java.lang.Boolean
     */
    Boolean removeEnhance(UserExtendsBO userExtendsBO);


    /**
     * 组织用户扩展数据
     *
     * @author     	孙凯伦
     * @since   	2021-10-21 01:50:40
     * @param       userExtendsBO 用户扩展请求BO
     * @param       updateWrapper 更新sql条件
     * @return      UserExtends
     */
    UserExtends buildUserExtends(UserExtendsBO userExtendsBO, UpdateWrapper<UserExtends> updateWrapper);
}
