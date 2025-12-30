package com.gb.account.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gb.account.entity.User;
import com.gb.account.entity.bo.UserBO;
import com.gb.account.entity.bo.UserExtendsBO;
import com.gb.account.entity.query.UserQuery;
import com.gb.account.entity.vo.UserVO;
import com.gb.user.entity.bo.UserBasicInfoBO;

import java.util.List;


/**
 * Created with IntelliJ IDEA.
 * @author:     	孙凯伦
 * @since:   	    2021-10-21 11:06:07
 * @description:	TODO  用户表，Service服务接口层
 * @source:  	    代码生成器
 */
public interface UserService extends IService<User> {


    /**
     * 集合条件查询
     * @author  孙凯伦
     * @since   2021-10-21 11:06:07
     * @param   userQuery:
     * @return  java.util.List<com.entity.UserVO>
     */
    List<UserVO> listEnhance(UserQuery userQuery);


    /**
     * 分页条件查询
     * @author  孙凯伦
     * @since   2021-10-21 11:06:07
     * @param   page:
     * @param   userQuery:
     * @throws Exception
     * @return  com.baomidou.mybatisplus.extension.plugins.pagination.Page
     */
    Page<UserVO> pageEnhance(Page page, UserQuery userQuery) throws Exception;


    /**
     * 单条条件查询
     * @author  孙凯伦
     * @since   2021-10-21 11:06:07
     * @param   userQuery:
     * @return  java.util.List<com.entity.UserVO>
     */
    UserVO getOneEnhance(UserQuery userQuery);


    /**
     * 总数
     * @author  孙凯伦
     * @since   2021-10-21 11:06:07
     * @param   userQuery:
     * @return  java.lang.Integer
     */
    Long countEnhance(UserQuery userQuery);


    /**
     * 新增
     * @author  孙凯伦
     * @since   2021-10-21 11:06:07
     * @param   userBO:
     * @return  java.lang.String
     */
     String saveEnhance(UserBO userBO);


    /**
     * 修改
     * @author  孙凯伦
     * @since   2021-10-21 11:06:07
     * @param   userBO:
     * @return  java.lang.Boolean
     */
    Boolean updateEnhance(UserBO userBO);


    /**
     * 修改密码
     * @author  孙凯伦
     * @since   2021-10-21 11:06:07
     * @param   bo:
     * @return  java.lang.Boolean
     */
    Boolean password(UserBO bo);

    /**
     * 删除
     * @author  孙凯伦
     * @since   2021-10-21 11:06:07
     * @param   userBO:
     * @return  java.lang.Boolean
     */
    Boolean removeEnhance(UserBO userBO);

    /**
     * 校验账户重复
     * @author  sunx
     * @since   2021-10-21 11:06:07
     * @param   userId: 用户序列
     * @param   userName: 账户名称
     * @param   userExtendsBO:
     */
    void checkUserRepeat(String userId, String userName, UserExtendsBO userExtendsBO);


    /**
     * 初始化用户信息【暂时--登录、快速登录、免密登录、授权用户服务--用到】
     * @author  sunx
     * @since   2021-10-21 11:06:07
     * @param   bo: 用户基本信息
     */
    void initUserInfo(UserBasicInfoBO bo);
}
