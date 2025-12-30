package com.gb.account.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.PhoneUtil;
import cn.hutool.crypto.SecureUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gb.account.entity.User;
import com.gb.account.entity.UserTypeValueRelationship;
import com.gb.account.entity.bo.*;
import com.gb.account.entity.enums.UserStateEnum;
import com.gb.account.entity.query.*;
import com.gb.account.entity.vo.UserExtendsVO;
import com.gb.account.entity.vo.UserTypeValueVO;
import com.gb.account.entity.vo.UserVO;
import com.gb.account.mapper.UserMapper;
import com.gb.account.service.*;
import com.gb.account.service.query.UserServiceQuery;
import com.gb.account.service.results.UserServiceResults;
import com.gb.permissions.entity.query.RoleQuery;
import com.gb.permissions.entity.query.SystemQuery;
import com.gb.permissions.entity.vo.RoleVO;
import com.gb.permissions.entity.vo.SystemVO;
import com.gb.permissions.service.RoleService;
import com.gb.permissions.service.SystemService;
import com.gb.user.entity.bo.UserBasicInfoBO;
import com.gb.user.entity.query.TeamUserQuery;
import com.gb.user.entity.vo.TeamUserVO;
import com.gb.user.enums.RoleUserTypeRelatedEnum;
import com.gb.user.service.TeamUserService;
import com.gb.user.service.UserQueryService;
import com.gb.utils.AddressUtils;
import com.gb.utils.GeneralConvertor;
import com.gb.utils.enumeration.ReturnCode;
import com.gb.utils.exception.BusinessException;
import com.gb.utils.exception.ParameterNullException;
import com.gb.utils.exception.PreventRepeatException;
import com.gb.utils.exception.UserException;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.gb.account.entity.enums.UserFormalStateEnum.*;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: 孙凯伦
 * @since: 2021-10-21 11:06:07
 * @description: TODO 用户表，Service服务实现层
 * @source: 代码生成器
 */
@Slf4j
@Service
@Setter(onMethod_ = {@Autowired})
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {


    /**
     * 用户表
     */
    private UserMapper userMapper;


    /**
     * 用户表
     */
    private UserServiceResults userServiceResults;

    /**
     * 用户查询服务
     */
    private UserQueryService userQueryService;


    /**
     * 用户组
     */
    private UserGroupService userGroupService;


    /**
     * 用户角色表
     */
    private UserRoleService userRoleService;


    /**
     * 用户扩展表
     */
    private UserExtendsService userExtendsService;


    /**
     * 用户类型值关联
     */
    private UserTypeValueRelationshipService userTypeValueRelationshipService;


    /**
     * 用户类型值表
     */
    private UserTypeValueService userTypeValueService;


    /**
     * 角色表
     */
    private RoleService roleService;


    /**
     * 团队人员服务表
     */
    private TeamUserService teamUserService;


    /**
     * 系统表
     */
    private SystemService systemService;


    /**
     * 集合条件查询
     *
     * @param userQuery:
     * @return java.util.List<com.entity.UserVO>
     * @author 孙凯伦
     * @since 2021-10-21 11:06:07
     */
    @Override
    public List<UserVO> listEnhance(UserQuery userQuery) {
        //设置userQuery查询用户标签值的ID
        if(setUserTypeValueId(userQuery).equals(0)) {
            return Lists.newArrayList();
        }
        User user = GeneralConvertor.convertor(userQuery, User.class);
        QueryWrapper<User> queryWrapper = new QueryWrapper<>(user);
        // TODO 自动生成查询，禁止手动写语句
        UserServiceQuery.query(userQuery, queryWrapper);
        // TODO 人工查询条件
        queryArtificial(userQuery, queryWrapper);
        //DO数据
        List<User> userDO = userMapper.selectList(queryWrapper);
        //VO数据
        List<UserVO> userVO = GeneralConvertor.convertor(userDO, UserVO.class);
        return userServiceResults.assignment(userQuery, userVO);
    }


    /**
     * 分页条件查询
     *
     * @param page:
     * @param userQuery:
     * @return com.baomidou.mybatisplus.core.metadata.IPage
     * @author 孙凯伦
     * @since 2021-10-21 11:06:07
     */
    @Override
    public Page<UserVO> pageEnhance(Page page, UserQuery userQuery) throws Exception {
        Map<String, Object> userEnterpriseMap = Maps.newHashMap();
        //查询标签
        if(setUserTypeValueId(userQuery).equals(0)) {
            return new Page<>();
        }
        //查询企业信息
        if (userQuery.isQueryEnterpriseInfo() && StringUtils.isNotBlank(userQuery.getUserTypeCode())) {
            if (StringUtils.isBlank(userQuery.getUserName()) && StringUtils.isNotBlank(userQuery.getEnterpriseId())) {
                userEnterpriseMap = userQueryService.queryUserEnterpriseInfoList(null, userQuery);
                if (MapUtil.isEmpty(userEnterpriseMap)) {
                    return new Page<>();
                }
                userQuery.setUserIdList(Lists.newArrayList(userEnterpriseMap.keySet()));
            }
        }
        //查询用户信息
        User user = GeneralConvertor.convertor(userQuery, User.class);
        QueryWrapper<User> queryWrapper = new QueryWrapper<>(user);
        //TODO 自动生成查询，禁止手动写语句
        UserServiceQuery.query(userQuery, queryWrapper);
        // TODO 人工查询条件
        queryArtificial(userQuery, queryWrapper);
        //DO数据
        Page<User> pageDO = userMapper.selectPage(page, queryWrapper);
        //VO数据
        Page<UserVO> pageVO = userServiceResults.toPageVO(pageDO);
        return userServiceResults.assignment(userEnterpriseMap, userQuery, pageVO);
    }


    /**
     * 单条条件查询
     *
     * @param userQuery:
     * @return java.util.List<com.entity.UserVO>
     * @author 孙凯伦
     * @since 2021-10-21 11:06:07
     */
    @Override
    public UserVO getOneEnhance(UserQuery userQuery) {
        User user = GeneralConvertor.convertor(userQuery, User.class);
        QueryWrapper<User> queryWrapper = new QueryWrapper<>(user);
        //TODO 自动生成查询，禁止手动写语句
        UserServiceQuery.query(userQuery, queryWrapper);
        // TODO 人工查询条件
        queryArtificial(userQuery, queryWrapper);
        //DO数据
        User userDO = userMapper.selectOne(queryWrapper);
        //VO数据
        UserVO userVO = GeneralConvertor.convertor(userDO, UserVO.class);
        return userServiceResults.assignment(userQuery, userVO);
    }


    /**
     * 总数
     *
     * @param userQuery:
     * @return java.lang.Integer
     * @author 孙凯伦
     * @since 2021-10-21 11:06:07
     */
    @Override
    public Long countEnhance(UserQuery userQuery) {
        User user = GeneralConvertor.convertor(userQuery, User.class);
        QueryWrapper<User> queryWrapper = new QueryWrapper<>(user);
        //TODO 自动生成查询，禁止手动写语句
        UserServiceQuery.query(userQuery, queryWrapper);
        // TODO 人工查询条件
        queryArtificial(userQuery, queryWrapper);
        return userMapper.selectCount(queryWrapper);
    }


    /**
     * 新增
     *
     * @param userBO:
     * @return java.lang.String
     * @author 孙凯伦
     * @since 2021-10-21 11:06:07
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, timeout = 36000, rollbackFor = Exception.class)
    public String saveEnhance(UserBO userBO) {
        User user = GeneralConvertor.convertor(userBO, User.class);
        if (StringUtils.isNotBlank(user.getPassword())) {
            user.setPassword(SecureUtil.md5(user.getPassword()));
        }
        //新增
        if(countEnhance(new UserQuery(){{
            setId(user.getId());
        }})<1) {
            userMapper.insert(user);
        }
        //1、新增用户角色组关联
        userRole(userBO, user);
        //2、新增用户组关联
        userGroup(userBO, user);
        //3、新增扩展表
        if (Objects.nonNull(userBO.getUserExtendsBO()) && StringUtils.isNotBlank(user.getId())) {
            //新增用户扩展信息
            UserExtendsBO userExtendsBO = userBO.getUserExtendsBO();
            userExtendsBO.setUserId(user.getId());
            userExtendsBO.setCreateName(userBO.getCreateName());
            userExtendsService.saveEnhance(userExtendsBO);
        }
        //4、标签新增修改
        typeValueCode(userBO, user);
        return user.getId();
    }


    /**
     * 修改
     *
     * @param userBO:
     * @return java.lang.Boolean
     * @author 孙凯伦
     * @since 2021-10-21 11:06:07
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, timeout = 36000, rollbackFor = Exception.class)
    public Boolean updateEnhance(UserBO userBO) {
        log.debug("工保网-本地修改用户信息-请求参数：{}", JSON.toJSONString(userBO));
        String userName = userBO.getUserName();
        setUserInfo(userBO);
        User user = GeneralConvertor.convertor(userBO, User.class);
        UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id", userBO.getId());
        Integer i = userMapper.update(user, updateWrapper);
        if(i < 1 ) {
            return false;
        }
        if(!userBO.getUserFormalStateEnum().equals(注销) && !userBO.getUserFormalStateEnum().equals(离职)) {
            //新增角色关联
            userRole(userBO, user);
            //新增用户组关联
            userGroup(userBO, user);
            //标签新增修改
            typeValueCode(userBO, user);
        }
        //修改扩展表
        if(Objects.nonNull(userBO.getUserExtendsBO())) {
            userExtendsService.updateEnhance(userBO.getUserExtendsBO(), userName, userBO.getUserName());
        }
        return true;
    }

    /**
     * 设置用户信息
     * @param userBO 用户信息BO
     */
    private void setUserInfo(UserBO userBO) {
        if(StringUtils.isNotBlank(userBO.getPassword())) {
            userBO.setPassword(SecureUtil.md5(userBO.getPassword()));
        }
        if(Objects.isNull(userBO.getUserFormalStateEnum())){
            userBO.setUserFormalStateEnum(修改新增);
        }
        UserExtendsBO userExtendsBO = userBO.getUserExtendsBO();
        if(userBO.getUserFormalStateEnum().equals(注销) || userBO.getUserFormalStateEnum().equals(离职)) {
            String date = DateUtil.now();
            if(Objects.isNull(userExtendsBO)) {
                userExtendsBO = new UserExtendsBO();
            }
            if(userBO.getUserFormalStateEnum().equals(注销)) {
                String newMobile = date + "-" + userExtendsBO.getMobile();
                String newUserName = date + "-" + userBO.getUserName();
                if(StringUtils.isBlank(userBO.getUserName())) {
                    newUserName = newMobile ;
                }
                userBO.setUserName(newUserName);
                userBO.setState(UserStateEnum.注销);
                userExtendsBO.setMobile(newMobile);
            }
            userExtendsBO.setModifyName(userBO.getModifyName());
            userExtendsBO.setLeaveDateTimeStr(date);
        }
        if(Objects.nonNull(userExtendsBO)) {
            userExtendsBO.setUserId(userBO.getId());
            userExtendsBO.setUserFormalStateEnum(userBO.getUserFormalStateEnum());
            userBO.setUserExtendsBO(userExtendsBO);
        }
    }

    /**
     * 修改密码
     *
     * @param bo: 请求参数
     * @return java.lang.Boolean
     * @author 孙凯伦
     * @since 2021-10-21 11:06:07
     */
    @Override
    public Boolean password(UserBO bo) {
        User user = new User();
        user.setId(bo.getId());
        user.setPassword(SecureUtil.md5(bo.getPassword()));
        user.setModifyName(bo.getModifyName());
        Integer i = userMapper.updateById(user);
       return i>0;
    }


    /**
     * 删除
     *
     * @param userBO:
     * @return java.lang.Boolean
     * @author 孙凯伦
     * @since 2021-10-21 11:06:07
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, timeout = 36000, rollbackFor = Exception.class)
    public Boolean removeEnhance(UserBO userBO) {
        User user = GeneralConvertor.convertor(userBO, User.class);
        QueryWrapper<User> queryWrapper = new QueryWrapper<>(user);
        Integer i = userMapper.delete(queryWrapper);
        return i > 0 ? true : false;
    }

    @Override
    public void checkUserRepeat(String userId, String userName,  UserExtendsBO userExtendsBO) {
        //1、用户信息存在校验
        if(StringUtils.isNotBlank(userId)) {
            UserVO userVO = getOneEnhance(new UserQuery() {{
                setId(userId);
            }});
            if(Objects.isNull(userVO)) {
                throw new ParameterNullException("账户不存在！");
            }
        }
        //2、账户名存在校验
        if(StringUtils.isNotBlank(userName)) {
            List<UserVO> userVOList = listEnhance(new UserQuery() {{
                setUserName(userName);
            }});
            if (StringUtils.isBlank(userId)) {
                if(userVOList.size() > 0 ) {
                    throw new PreventRepeatException("登录账户名系统已存在！");
                }
            } else {
                boolean existFlag = userVOList.size() > 1 ? true : (CollectionUtils.isNotEmpty(userVOList) && !StringUtils.equals(userVOList.get(0).getId(), userId));
                if(existFlag) {
                    throw new PreventRepeatException("登录账户名系统已存在！");
                }
            }
        }
        //3、用户扩展信息存在校验
        if(Objects.nonNull(userExtendsBO)) {
            //3.1、手机号存在校验
            String mobile = userExtendsBO.getMobile();
            if(StringUtils.isNotBlank(mobile)) {
                if(!PhoneUtil.isMobile(mobile)) {
                    throw new BusinessException("手机号校验失败！");
                }
                List<UserExtendsVO> userExtendsVOList = userExtendsService.listEnhance(new UserExtendsQuery(){{
                    setMobile(mobile);
                }});
                if(StringUtils.isBlank(userId)) {
                    if(userExtendsVOList.size() > 0 ) {
                        throw new PreventRepeatException("手机号系统已存在！");
                    }
                } else {
                    boolean existFlag = userExtendsVOList.size() > 1 ? true : (CollectionUtils.isNotEmpty(userExtendsVOList) && !StringUtils.equals(userExtendsVOList.get(0).getUserId(), userId));
                    if(existFlag) {
                        throw new PreventRepeatException("手机号系统已存在！");
                    }
                }
            }
            //3.2、证件号存在校验
            String idCard = userExtendsBO.getIdCard();
            if(StringUtils.isNotBlank(idCard)) {
                List<UserExtendsVO> userExtendsVOList = userExtendsService.listEnhance(new UserExtendsQuery(){{
                    setIdCard(idCard);
                }});
                if(StringUtils.isBlank(userId)) {
                    if(userExtendsVOList.size() > 0 ) {
                        throw new PreventRepeatException("证件号码系统已存在！");
                    }
                } else {
                    boolean existFlag = userExtendsVOList.size() > 1 ? true : (CollectionUtils.isNotEmpty(userExtendsVOList) && !StringUtils.equals(userExtendsVOList.get(0).getUserId(), userId));
                    if(existFlag) {
                        throw new PreventRepeatException("证件号码系统已存在！");
                    }
                }
            }
            //校验离职状态
            if(Objects.nonNull(userExtendsBO.getUserFormalStateEnum()) && userExtendsBO.getUserFormalStateEnum().equals(离职)) {
                TeamUserVO teamUserVO = teamUserService.getOneEnhance(new TeamUserQuery(){{setUserId(userId);}});
                if(Objects.nonNull(teamUserVO) && teamUserVO.getChannel()) {
                    throw new BusinessException("您的账号渠道合作中，请拨打400-800-5100联系客服人员为您离职！");
                }
            }
        }
    }

    @Override
    public void initUserInfo(UserBasicInfoBO bo) {
        UserBO userBO = buildUserBO(bo);
        UserVO userVO = getOneEnhance(new UserQuery(){{
            setId(String.valueOf(bo.getId()));
        }});
        if(Objects.isNull(userVO)) {
            //设置标签、角色
            String roleCode = getDefaultRoleByAppCode(bo.getAppCode());
            RoleUserTypeRelatedEnum userTypeRelatedEnum = RoleUserTypeRelatedEnum.getByRoleCode(roleCode);
            if(Objects.nonNull(userTypeRelatedEnum)) {
                userBO.setRoleCode(new String[]{roleCode});
                if (StringUtils.isNotBlank(userTypeRelatedEnum.getUserTypeValueCode())) {
                    userBO.setTypeValueCode(new String[]{userTypeRelatedEnum.getUserTypeValueCode()});
                }
            } else {
                log.error("appCode：{}，标签、角色初始化分配异常！", bo.getAppCode());
                throw new UserException(ReturnCode.请求参数值超出允许的范围, "标签、角色初始化分配异常！");
            }
            userBO.setSourceId(bo.getSourceId());
            userBO.setSourceValueId(bo.getSourceValueId());
            //设置今天登录次数、月登录次数、年登录次数、总登录次数
            userBO.setTodayLoginCount(1);
            userBO.setMonthlyLoginCount(1);
            userBO.setYearsLoginCount(1);
            userBO.setLoginCount(1);
            //设置业务明细
            userBO.setBusinessDetails(bo.getBusinessDetails());
            userBO.setCreateName(userBO.getId() + "-" + bo.getName());
            userBO.getUserExtendsBO().setCreateName(userBO.getId() + "-" + bo.getName());
            saveEnhance(userBO);
        } else {
            userBO.setBeforeIp(userVO.getIp());
            userBO.setBeforeIpAddress(userVO.getIpAddress());
            userBO.setBeforeLoginDateTime(userVO.getLoginDateTime());
            userBO.setModifyName(userBO.getId() + "-" + bo.getName());
            userBO.getUserExtendsBO().setModifyName(userBO.getId() + "-" + bo.getName());
            //并判断登录时间今天是否存在登录，存在不做登录次数累加。
            if(userVO.getTodayLoginCount() == 0) {
                userBO.setTodayLoginCount(1);
                userBO.setMonthlyLoginCount(userVO.getMonthlyLoginCount() + 1);
                userBO.setYearsLoginCount(userVO.getYearsLoginCount() + 1);
                userBO.setLoginCount(userVO.getLoginCount() + 1);
            }
            updateEnhance(userBO);
        }
    }


    /**
     * 查询人工查询条件
     *
     * @param userQuery 用户表
     * @return QueryWrapper
     * @author 孙凯伦
     * @since 2021-10-21 11:06:07
     */
    private QueryWrapper queryArtificial(UserQuery userQuery, QueryWrapper<User> queryWrapper) {
        if (StringUtils.isNotBlank(userQuery.getCreateDateTimeStart())) {
            queryWrapper.ge("`create_date_time`", userQuery.getCreateDateTimeStart());
        }
        if (StringUtils.isNotBlank(userQuery.getCreateDateTimeEnd())) {
            queryWrapper.le("`create_date_time`", userQuery.getCreateDateTimeEnd());
        }
        if (CollectionUtil.isNotEmpty(userQuery.getUserIdList())) {
            queryWrapper.in("`id`", userQuery.getUserIdList());
        }
        if(StringUtils.isNotBlank(userQuery.getSourceCode())) {
            queryWrapper.inSql("`source_id`", "select `id` from `source` where `code` = '" + userQuery.getSourceCode()+"'");
        }
        //排除标签值的用户信息查询
        if(StringUtils.isNotBlank(userQuery.getNeTypeValueCode())) {
            queryWrapper.inSql("`id`", "select `user_id` from `user_type_value_relationship` where `is_delete` = 0 and `user_type_value_id` in (select `id` from `user_type_value` where `code` not in (" + com.gb.utils.StringUtils.in(userQuery.getNeTypeValueCode()) + "))");
        }
        if(Objects.nonNull(userQuery.getUserFormalStateEnum())) {
            if(userQuery.getUserFormalStateEnum().equals(离职)) {
                queryWrapper.inSql("`id`", "select `user_id` from `user_extends` where  `is_delete` = 0 and `leave_date_time` is not null").ne("state", 1);
            }else {
                queryWrapper.eq("state", userQuery.getUserFormalStateEnum().getValue());
            }
        }
        return queryWrapper;
    }


    /**
     * @param userBO
     * @param user
     * @auther: 孙凯伦
     * @mobile: 13777579028
     * @email: 376253703@qq.com
     * @name: typeValueCode
     * @description: TODO  标签新增修改
     * @return: void
     * @date: 2021/10/28 11:44 上午
     */
    private void typeValueCode(UserBO userBO, User user) {
        if(StringUtils.isBlank(user.getId())) {
            return;
        }
        if(Objects.isNull(userBO.getTypeValueId()) && Objects.isNull(userBO.getTypeValueCode())) {
            return;
        }
        //用户标签值列表
        if (Objects.nonNull(userBO.getTypeValueId())) {
            List<UserTypeValueRelationshipBO> list = Lists.newArrayList();
            //删除历史标签
            userTypeValueRelationshipService.removeEnhance(new UserTypeValueRelationshipBO(){{
                setUserId(user.getId());
            }});
            //循环
            for (String typeValueId : userBO.getTypeValueId()) {
                //判断是否重复
                Long i = userTypeValueRelationshipService.countEnhance(new UserTypeValueRelationshipQuery() {{
                    setUserId(user.getId());
                    setUserTypeValueId(typeValueId);
                }});
                //判断没有重复
                if (i <= 0) {
                    list.add(new UserTypeValueRelationshipBO() {{
                        setUserId(user.getId());
                        setUserTypeValueId(typeValueId);
                        setCreateName(userBO.getCreateName());
                    }});
                }
            }
            //批量新增
            if (list.size() > 0) {
                userTypeValueRelationshipService.saveBatch(GeneralConvertor.convertor(list, UserTypeValueRelationship.class));
            }
        }
        //用户标签值CODE列表
        if (Objects.nonNull(userBO.getTypeValueCode())) {
            List<UserTypeValueRelationshipBO> code = Lists.newArrayList();
            //删除历史
            userTypeValueRelationshipService.removeEnhance(new UserTypeValueRelationshipBO(){{
                setUserId(user.getId());
            }});
            //循环
            for (String typeValueCode : userBO.getTypeValueCode()) {
                //角色集合
                UserTypeValueVO userTypeValueVO = userTypeValueService.getOneEnhance(new UserTypeValueQuery() {{
                    setCode(typeValueCode);
                }});
                //判断是否重复
                Long i = userTypeValueRelationshipService.countEnhance(new UserTypeValueRelationshipQuery() {{
                    setUserId(user.getId());
                    setUserTypeValueId(userTypeValueVO.getId());
                }});
                //判断没有重复
                if (i <= 0) {
                    code.add(new UserTypeValueRelationshipBO() {{
                        setUserId(user.getId());
                        setUserTypeValueId(userTypeValueVO.getId());
                        setCreateName(userBO.getCreateName());
                    }});
                }
            }
            //批量新增
            if (code.size() > 0) {
                userTypeValueRelationshipService.saveBatch(GeneralConvertor.convertor(code, UserTypeValueRelationship.class));
            }
        }

    }


    /**
     * 批量新增用户id对应的角色关联信息
     *
     * @param userBO 用户BO
     * @return Integer
     * @author 孙凯伦
     * @since 2021-10-21 11:06:07
     */
    private void userRole(UserBO userBO, User user) {
        if(StringUtils.isBlank(user.getId())) {
            return;
        }
        if(Objects.nonNull(userBO.getRoleIdList()) && Objects.nonNull(userBO.getRoleCode())) {
            return;
        }
        //用户角色Id列表
        if (Objects.nonNull(userBO.getRoleIdList())) {
            List<UserRoleBO> list = Lists.newArrayList();
            //删除历史
            userRoleService.removeEnhance(new UserRoleBO(){{
                setUserId(user.getId());
            }});
            //循环
            for (String roleId : userBO.getRoleIdList()) {
                //判断是否重复
                Long i = userRoleService.countEnhance(new UserRoleQuery() {{
                    setUserId(user.getId());
                    setRoleId(roleId);
                }});
                //判断没有重复
                if (i <= 0) {
                    list.add(new UserRoleBO() {{
                        setUserId(user.getId());
                        setRoleId(roleId);
                        setCreateName(userBO.getCreateName());
                    }});
                }
            }
            //批量新增
            if (list.size() > 0) {
                userRoleService.saveBatchEnhance(list);
            }
        }
        //用户角色Code列表
        if (Objects.nonNull(userBO.getRoleCode())) {
            List<UserRoleBO> code = Lists.newArrayList();
            //删除历史
            userRoleService.removeEnhance(new UserRoleBO(){{
                setUserId(user.getId());
            }});
            //循环
            for (String roleCode : userBO.getRoleCode()) {
                //角色集合
                RoleVO roleVO = roleService.getOneEnhance(new RoleQuery() {{
                    setValue(roleCode);
                }});
                if (Objects.nonNull(roleVO)) {
                    //判断是否重复
                    Long i = userRoleService.countEnhance(new UserRoleQuery() {{
                        setUserId(user.getId());
                        setRoleId(roleVO.getId());
                    }});
                    //判断没有重复
                    if (i <= 0) {
                        code.add(new UserRoleBO() {{
                            setUserId(user.getId());
                            setRoleId(roleVO.getId());
                            setCreateName(userBO.getCreateName());
                        }});
                    }
                }
            }
            //批量新增
            if (code.size() > 0) {
                userRoleService.saveBatchEnhance(code);
            }
        }
    }


    /**
     * @param userBO
     * @param user
     * @auther: 孙凯伦
     * @mobile: 13777579028
     * @email: 376253703@qq.com
     * @name: userGroup
     * @description: TODO  用户组维护
     * @return: void
     * @date: 2021/11/9 9:21 AM
     */
    private void userGroup(UserBO userBO, User user) {
        List<UserGroupBO> list = Lists.newArrayList();
        if (Objects.nonNull(userBO.getGroupList()) && StringUtils.isNotBlank(user.getId())) {
            //删除组关联
            userGroupService.removeEnhance(new UserGroupBO() {{
                setUserId(user.getId());
            }});
            //赋值内容
            for (String groupId : userBO.getGroupList()) {
                list.add(new UserGroupBO() {{
                    setGroupId(groupId);
                    setUserId(user.getId());
                    setCreateName(userBO.getCreateName());
                }});
            }
            if (list.size() > 0) {
                userGroupService.saveBatchEnhance(list);
            }
        }
    }


    /**
     * @param userQuery
     * @auther: 孙凯伦
     * @mobile: 13777579028
     * @email: 376253703@qq.com
     * @name: userGroup
     * @description: TODO  后台财务模块-查询条件用到
     * @return: void
     * @date: 2021/11/9 9:21 AM
     */
    private Integer setUserTypeValueId(UserQuery userQuery) {
      if(Objects.isNull(userQuery) || StringUtils.isBlank(userQuery.getUserTypeCode())){
          return 1;
      }
      List<UserTypeValueVO> userTypeValueVOList = userTypeValueService.listEnhance(new UserTypeValueQuery(){{
          setUserTypeCode(userQuery.getUserTypeCode());
      }});
      if(CollectionUtil.isEmpty(userTypeValueVOList)){
        log.debug("标签CODE：{}，未找到该标签下标签值配置信息！", userQuery.getUserTypeCode());
        return 0;
      }
      String userTypeValueId = userTypeValueVOList.stream().map(s->s.getId()).collect(Collectors.joining(","));
      userQuery.setTypeValueId(userTypeValueId);
      return 1;
    }


    /**
     * 组织用户BO
     * @param bo 用户基本信息BO
     * @return UserBO
     */
    private UserBO buildUserBO(UserBasicInfoBO bo) {
        UserBO userBO = new UserBO();
        userBO.setId(String.valueOf(bo.getId()));
        userBO.setUserName(bo.getUserName());
        userBO.setPassword(bo.getPassword());
        userBO.setAvatarUrl(bo.getAvatar());
        userBO.setIp(bo.getIp());
        userBO.setIpAddress(AddressUtils.getAddresses(bo.getIp()));
        userBO.setLoginDateTime(LocalDateTime.now());

        userBO.setUserExtendsBO(new UserExtendsBO() {{
            setUserId(userBO.getId());
            setName(bo.getName());
            setMobile(bo.getMobile());
            setEmail(bo.getEmail());
        }});
        if (StringUtils.isNotBlank(bo.getSex())) {
            userBO.getUserExtendsBO().setSex(Integer.parseInt(bo.getSex()));
        }
        return userBO;
    }

    /**
     * 根据系统编码，获取对应默认角色
     * @param systemCode 系统编码
     * @return String
     */
    private String getDefaultRoleByAppCode(String systemCode) {
        SystemVO systemVO = systemService.getOneEnhance(new SystemQuery(){{
            setCode(systemCode);
        }});
        if(Objects.isNull(systemVO)) {
            log.error("getDefaultRoleByAppCode--systemCode:{},system表中不存在！", systemCode);
            throw new ParameterNullException("平台编码不存在!");
        }
        //获取系统对应的默认角色，label为1的时候，表示系统默认角色，其他不是
        RoleVO roleVO = roleService.getOneEnhance(new RoleQuery(){{
            setSystemId(systemVO.getId());
            setLabel("1");
        }});
        if(Objects.isNull(roleVO)) {
            log.error("getDefaultRoleByAppCode--role表，systemId:{},默认角色不存在！", systemVO.getId());
            throw new ParameterNullException("默认角色不存在!");
        }
        return roleVO.getValue();
    }
}