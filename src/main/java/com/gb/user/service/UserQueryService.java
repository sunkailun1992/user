package com.gb.user.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gb.account.entity.query.UserQuery;
import com.gb.account.entity.vo.UserVO;
import com.gb.user.entity.bo.UserBatchQueryBO;
import com.gb.user.entity.query.UserInfoQuery;
import com.gb.user.entity.vo.UserBasicInfoVO;
import com.gb.user.entity.vo.UserLabelInfoVO;
import com.gb.user.entity.vo.UserLogVO;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 用户中心查询接口控制器接口
 * </p>
 *
 * @author sunx
 * @since 2021-03-15
 */
public interface UserQueryService {

    /**
     * 获取用户关联的企业信息
     *
     * @param current: 当前页码
     * @param size: 总页码
     * @param userTypeCode: 用户标签码值
     * @param userNameList: 用户列表
     * @param enterpriseId;企业ID TODO：经纪人关联企业ID，目前经纪人原型还不确定，后期会改
     * @return com.utils.Json
     * @author sunx
     * @since 2021-03-04
     */
    Map<String, Object> queryEnterpriseInfoByUser(Integer current, Integer size, String userTypeCode, String userNameList, String enterpriseId);

    /**
     * 根据用户ID列表查询用户基本信息列表
     *
     * @author sunx
     * @since 2021-05-25
     * @param bo: 请求参数
     * @return  Map<String, List<UserBasicInfoVo>>
     */
    Map<String, List<UserBasicInfoVO>> groupQueryUserInfoByUserIdList(UserBatchQueryBO bo);

    /**
     * 保险用户信息分页查询【企业信息】
     *
     * @author sunx
     * @since 2021-11-04
     * @param userVOList; 用户信息列表
     * @param userQuery; 请求参数
     * @return Page<UserVO>
     */
    Map<String, Object> queryUserEnterpriseInfoList(List<UserVO> userVOList, UserQuery userQuery);

    /**
     *  根据标签查询参数查询标签内的用户信息
     *
     * @author sunx
     * @since 2021-11-04
     * @param userInfoQuery; 请求参数
     * @throws Exception
     * @return  List<UserLabelInfoVO>
     */
    List<UserLabelInfoVO> queryLabelInUserByTypeParams(UserInfoQuery userInfoQuery) throws Exception;

    /**
     * 分页查询用户日志集合
     * @param userId; 用户序列
     * @param pageNumber;
     * @param pageSize
     * @return
     */
    Page<UserLogVO> pageEnhance(String userId, Integer pageNumber, Integer pageSize);

    /**
     * 校验身份
     * @param mobile; 用户手机号
     * @param userId; 用户序列
     * @return void
     */
    void verifyIdentity(String mobile, String userId);
}
