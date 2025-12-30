package com.gb.account.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gb.account.entity.UserRole;
import com.gb.account.entity.bo.UserRoleBO;
import com.gb.account.entity.query.UserRoleQuery;
import com.gb.account.entity.vo.UserRoleVO;
import com.gb.account.mapper.UserRoleMapper;
import com.gb.account.service.UserRoleService;
import com.gb.account.service.query.UserRoleServiceQuery;
import com.gb.account.service.results.UserRoleServiceResults;
import com.gb.permissions.entity.query.RoleQuery;
import com.gb.permissions.entity.vo.RoleVO;
import com.gb.permissions.service.RoleService;
import com.gb.utils.GeneralConvertor;
import com.gb.utils.JsonUtil;
import com.gb.utils.exception.ParameterNullException;
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
import java.util.Objects;

/**
 * Created with IntelliJ IDEA.
 * @author:     	孙凯伦
 * @since:   	    2021-10-21 01:50:37
 * @description:	TODO  用户角色表，Service服务实现层
 * @source:  	    代码生成器
 */
@Slf4j
@Service
@Setter(onMethod_ = {@Autowired})
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements UserRoleService {


    /**
     * 用户角色表
     */
    private UserRoleMapper userRoleMapper;


    /**
     * 用户角色表
     */
    private UserRoleServiceResults userRoleServiceResults;


    /**
     * 角色表
     */
    private RoleService roleService;


    /**
     * 集合条件查询
     * @author  孙凯伦
     * @since   2021-10-21 01:50:37
     * @param   userRoleQuery:
     * @return  java.util.List<com.entity.UserRoleVO>
     */
    @Override
    public List<UserRoleVO> listEnhance(UserRoleQuery userRoleQuery) {
        UserRole userRole = GeneralConvertor.convertor(userRoleQuery, UserRole.class);
        QueryWrapper<UserRole> queryWrapper = new QueryWrapper<>(userRole);
        // TODO 自动生成查询，禁止手动写语句
        UserRoleServiceQuery.query(userRoleQuery, queryWrapper);
        // TODO 人工查询条件
        queryArtificial(userRoleQuery, queryWrapper);
        //DO数据
        List<UserRole> userRoleDO = userRoleMapper.selectList(queryWrapper);
        //VO数据
        List<UserRoleVO> userRoleVO = GeneralConvertor.convertor(userRoleDO, UserRoleVO.class);
        return userRoleServiceResults.assignment(userRoleVO);
    }


    /**
     * 分页条件查询
     * @author  孙凯伦
     * @since   2021-10-21 01:50:37
     * @param   page:
     * @param   userRoleQuery:
     * @return  com.baomidou.mybatisplus.core.metadata.IPage
     */
    @Override
    public Page<UserRoleVO> pageEnhance(Page page, UserRoleQuery userRoleQuery) {
        UserRole userRole = GeneralConvertor.convertor(userRoleQuery, UserRole.class);
        QueryWrapper<UserRole> queryWrapper = new QueryWrapper<>(userRole);
        //TODO 自动生成查询，禁止手动写语句
        UserRoleServiceQuery.query(userRoleQuery, queryWrapper);
        // TODO 人工查询条件
        queryArtificial(userRoleQuery, queryWrapper);
        //DO数据
        Page<UserRole> pageDO = userRoleMapper.selectPage(page, queryWrapper);
        //VO数据
        Page<UserRoleVO> pageVO = userRoleServiceResults.toPageVO(pageDO);
        return userRoleServiceResults.assignment(pageVO);
    }


    /**
     * 单条条件查询
     * @author  孙凯伦
     * @since   2021-10-21 01:50:37
     * @param   userRoleQuery:
     * @return  java.util.List<com.entity.UserRoleVO>
     */
    @Override
    public UserRoleVO getOneEnhance(UserRoleQuery userRoleQuery) {
        UserRole userRole = GeneralConvertor.convertor(userRoleQuery, UserRole.class);
        QueryWrapper<UserRole> queryWrapper = new QueryWrapper<>(userRole);
        //TODO 自动生成查询，禁止手动写语句
        UserRoleServiceQuery.query(userRoleQuery, queryWrapper);
        // TODO 人工查询条件
        queryArtificial(userRoleQuery, queryWrapper);
        //DO数据
        UserRole userRoleDO = userRoleMapper.selectOne(queryWrapper);
        //VO数据
        UserRoleVO userRoleVO = GeneralConvertor.convertor(userRoleDO, UserRoleVO.class);
        return userRoleServiceResults.assignment(userRoleVO);
    }


    /**
     * 总数
     * @author  孙凯伦
     * @since   2021-10-21 01:50:37
     * @param   userRoleQuery:
     * @return  java.lang.Integer
     */
    @Override
    public Long countEnhance(UserRoleQuery userRoleQuery) {
        UserRole userRole = GeneralConvertor.convertor(userRoleQuery, UserRole.class);
        QueryWrapper<UserRole> queryWrapper = new QueryWrapper<>(userRole);
        //TODO 自动生成查询，禁止手动写语句
        UserRoleServiceQuery.query(userRoleQuery, queryWrapper);
        // TODO 人工查询条件
        queryArtificial(userRoleQuery, queryWrapper);
        return userRoleMapper.selectCount(queryWrapper);
    }


    /**
     * 新增
     * @author  孙凯伦
     * @since   2021-10-21 01:50:37
     * @param   userRoleBO:
     * @return  java.lang.String
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, timeout = 36000, rollbackFor = Exception.class)
    public String saveEnhance(UserRoleBO userRoleBO) {
        UserRoleQuery userRoleQuery = BeanUtil.copyProperties(userRoleBO, UserRoleQuery.class);
        UserRoleVO userRoleVO = getOneEnhance(userRoleQuery);
        if(Objects.nonNull(userRoleVO)) {
            log.error("用户ID：{}，角色ID：{}，用户角色关联表已经配置！", userRoleBO.getUserId(), userRoleBO.getRoleId());
            return userRoleVO.getId();
        }
        if(StringUtils.isNotBlank(userRoleBO.getRoleCode())) {
            RoleVO roleVO = roleService.getOneEnhance(new RoleQuery(){{setValue(userRoleBO.getRoleCode());}});
            if(Objects.isNull(roleVO)) {
                throw new ParameterNullException("未找到该角色编码！");
            }
            userRoleBO.setRoleId(roleVO.getId());
        }
        UserRole userRole = GeneralConvertor.convertor(userRoleBO, UserRole.class);
        userRoleMapper.insert(userRole);
        return userRole.getId();
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, timeout = 36000, rollbackFor = Exception.class)
    public Integer saveBatchEnhance(List<UserRoleBO> userRoleBoList) {
        try{
            List<UserRole> userRoleList = GeneralConvertor.convertor(userRoleBoList, UserRole.class);
            return userRoleMapper.insertBatch(userRoleList);
        }catch (Exception e){
            log.error("用户角色关联表【userRoleBOList：{}】批量新增异常：", JsonUtil.json(userRoleBoList), e);
            throw e;
        }
    }

    /**
     * 修改
     * @author  孙凯伦
     * @since   2021-10-21 01:50:37
     * @param   userRoleBO:
     * @return  java.lang.Boolean
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, timeout = 36000, rollbackFor = Exception.class)
    public Boolean updateEnhance(UserRoleBO userRoleBO) {
        UserRole userRole = GeneralConvertor.convertor(userRoleBO, UserRole.class);
        UpdateWrapper<UserRole > updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id", userRoleBO.getId());
        Integer i = userRoleMapper.update(userRole, updateWrapper);
        return i > 0 ? true : false;
    }


    /**
     * 删除
     * @author  孙凯伦
     * @since   2021-10-21 01:50:37
     * @param   userRoleBO:
     * @return  java.lang.Boolean
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, timeout = 36000, rollbackFor = Exception.class)
    public Boolean removeEnhance(UserRoleBO userRoleBO) {
        UserRole userRole = GeneralConvertor.convertor(userRoleBO, UserRole.class);
        QueryWrapper<UserRole> queryWrapper = new QueryWrapper<>(userRole);
        if(StringUtils.isBlank(userRoleBO.getId())) {
            buildRoleCodeSql(userRoleBO.getRoleCode(), queryWrapper);
        }
        Integer i = userRoleMapper.delete(queryWrapper);
        return i > 0 ? true : false;
    }


    /**
     * 查询人工查询条件
     *
     * @author     	孙凯伦
     * @since   	2021-10-21 01:50:37
     * @param       userRoleQuery 用户角色表
     * @return      QueryWrapper
     */
    private QueryWrapper queryArtificial(UserRoleQuery userRoleQuery, QueryWrapper<UserRole> queryWrapper) {
        if(CollectionUtils.isNotEmpty(userRoleQuery.getRoleIdList())){
            queryWrapper.in("role_id", userRoleQuery.getRoleIdList());
        }
        //模糊查询
        if (StringUtils.isNotBlank(userRoleQuery.getQuery())) {
            queryWrapper
                    .inSql("`user_id`", "select `id` from `user` where `user_name` like \"" + userRoleQuery.getQuery() + "%\"")
                    .or()
                    .inSql("`user_id`", "select `user_id` from `user_extends` where `name` like \"" + userRoleQuery.getQuery() + "%\"");
        }
        buildRoleCodeSql(userRoleQuery.getRoleCode(), queryWrapper);
        return queryWrapper;
    }


    /**
     * 组织角色的对应查询SQL
     * @param roleCode：角色码值
     * @param queryWrapper：查询包装类
     */
    private void buildRoleCodeSql(String roleCode, QueryWrapper<UserRole> queryWrapper) {
        if(StringUtils.isBlank(roleCode)) {
            return;
        }
        String[] roleCodeArray = roleCode.split(",");
        StringBuilder sbf = new StringBuilder();
        for (String r : roleCodeArray) {
            sbf.append("'" + r + "',");
        }
        String code = String.valueOf(sbf);
        code  = code.substring(0, code.length()-1);
        queryWrapper.inSql("role_id", "select `id` from `role` where `role`.`value` in (" + code + ")");
    }
}