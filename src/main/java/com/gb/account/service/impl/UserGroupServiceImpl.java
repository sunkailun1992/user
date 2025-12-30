package com.gb.account.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gb.account.entity.UserGroup;
import com.gb.account.entity.bo.UserGroupBO;
import com.gb.account.entity.query.UserGroupQuery;
import com.gb.account.entity.vo.UserGroupVO;
import com.gb.account.mapper.UserGroupMapper;
import com.gb.account.service.UserGroupService;
import com.gb.account.service.query.UserGroupServiceQuery;
import com.gb.account.service.results.UserGroupServiceResults;
import com.gb.utils.GeneralConvertor;
import com.gb.utils.JsonUtil;
import com.gb.utils.RedisUtils;
import com.google.common.collect.Lists;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: 孙凯伦
 * @since: 2021-10-21 01:50:39
 * @description: TODO 用户组，Service服务实现层
 * @source: 代码生成器
 */
@Slf4j
@Service
@Setter(onMethod_ = {@Autowired})
public class UserGroupServiceImpl extends ServiceImpl<UserGroupMapper, UserGroup> implements UserGroupService {


    /**
     * 用户组
     */
    private UserGroupMapper userGroupMapper;


    /**
     * 用户组
     */
    private UserGroupServiceResults userGroupServiceResults;


    /**
     * redis操作
     */
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 集合条件查询
     *
     * @param userGroupQuery:
     * @return java.util.List<com.entity.UserGroupVO>
     * @author 孙凯伦
     * @since 2021-10-21 01:50:39
     */
    @Override
    public List<UserGroupVO> listEnhance(UserGroupQuery userGroupQuery) {
        UserGroup userGroup = GeneralConvertor.convertor(userGroupQuery, UserGroup.class);
        QueryWrapper<UserGroup> queryWrapper = new QueryWrapper<>(userGroup);
        // TODO 自动生成查询，禁止手动写语句
        UserGroupServiceQuery.query(userGroupQuery, queryWrapper);
        // TODO 人工查询条件
        queryArtificial(userGroupQuery, queryWrapper);
        //DO数据
        List<UserGroup> userGroupDO = userGroupMapper.selectList(queryWrapper);
        //VO数据
        List<UserGroupVO> userGroupVO = GeneralConvertor.convertor(userGroupDO, UserGroupVO.class);
        return userGroupServiceResults.assignment(userGroupVO);
    }


    /**
     * 分页条件查询
     *
     * @param page:
     * @param userGroupQuery:
     * @return com.baomidou.mybatisplus.core.metadata.IPage
     * @author 孙凯伦
     * @since 2021-10-21 01:50:39
     */
    @Override
    public Page<UserGroupVO> pageEnhance(Page page, UserGroupQuery userGroupQuery) {
        UserGroup userGroup = GeneralConvertor.convertor(userGroupQuery, UserGroup.class);
        QueryWrapper<UserGroup> queryWrapper = new QueryWrapper<>(userGroup);
        //TODO 自动生成查询，禁止手动写语句
        UserGroupServiceQuery.query(userGroupQuery, queryWrapper);
        // TODO 人工查询条件
        queryArtificial(userGroupQuery, queryWrapper);
        //DO数据
        Page<UserGroup> pageDO = userGroupMapper.selectPage(page, queryWrapper);
        //VO数据
        Page<UserGroupVO> pageVO = userGroupServiceResults.toPageVO(pageDO);
        return userGroupServiceResults.assignment(pageVO);
    }


    /**
     * 单条条件查询
     *
     * @param userGroupQuery:
     * @return java.util.List<com.entity.UserGroupVO>
     * @author 孙凯伦
     * @since 2021-10-21 01:50:39
     */
    @Override
    public UserGroupVO getOneEnhance(UserGroupQuery userGroupQuery) {
        UserGroup userGroup = GeneralConvertor.convertor(userGroupQuery, UserGroup.class);
        QueryWrapper<UserGroup> queryWrapper = new QueryWrapper<>(userGroup);
        //TODO 自动生成查询，禁止手动写语句
        UserGroupServiceQuery.query(userGroupQuery, queryWrapper);
        // TODO 人工查询条件
        queryArtificial(userGroupQuery, queryWrapper);
        //DO数据
        UserGroup userGroupDO = userGroupMapper.selectOne(queryWrapper);
        //VO数据
        UserGroupVO userGroupVO = GeneralConvertor.convertor(userGroupDO, UserGroupVO.class);
        return userGroupServiceResults.assignment(userGroupVO);
    }


    /**
     * 总数
     *
     * @param userGroupQuery:
     * @return java.lang.Integer
     * @author 孙凯伦
     * @since 2021-10-21 01:50:39
     */
    @Override
    public Long countEnhance(UserGroupQuery userGroupQuery) {
        UserGroup userGroup = GeneralConvertor.convertor(userGroupQuery, UserGroup.class);
        QueryWrapper<UserGroup> queryWrapper = new QueryWrapper<>(userGroup);
        //TODO 自动生成查询，禁止手动写语句
        UserGroupServiceQuery.query(userGroupQuery, queryWrapper);
        // TODO 人工查询条件
        queryArtificial(userGroupQuery, queryWrapper);
        return userGroupMapper.selectCount(queryWrapper);
    }


    /**
     * 新增
     *
     * @param userGroupBO:
     * @return java.lang.String
     * @author 孙凯伦
     * @since 2021-10-21 01:50:39
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, timeout = 36000, rollbackFor = Exception.class)
    public String saveEnhance(UserGroupBO userGroupBO) {
        UserGroup userGroup = GeneralConvertor.convertor(userGroupBO, UserGroup.class);
        userGroupMapper.insert(userGroup);
        return userGroup.getId();
    }


    /**
     * 新增
     *
     * @param userId:
     * @param groupId:
     * @return java.lang.String
     * @author 孙凯伦
     * @since 2021-10-21 01:50:39
     */
    @Override
    public void saveEnhanceBatch(String[] userId, String groupId, HttpServletRequest httpServletRequest) {
        //缓存取出用户
        Map<String, Object> u = RedisUtils.getToken(stringRedisTemplate, httpServletRequest);
        //批量插入集合
        List<UserGroup> list = Lists.newArrayList();
        for (String id : userId) {
            //判断是否存在
            Long i = countEnhance(new UserGroupQuery() {{
                setUserId(id);
                setGroupId(groupId);
            }});
            //不存在放入批量插入
            if (i <= 0) {
                list.add(new UserGroup() {{
                    setUserId(id);
                    setGroupId(groupId);
                    if (u != null) {
                        setCreateName(u.get("name") + "-" + u.get("id"));
                    }
                }});
            }
        }
        //批量插入
        if (list.size() > 0) {
            saveBatch(list);
        }
    }


    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, timeout = 36000, rollbackFor = Exception.class)
    public Integer saveBatchEnhance(List<UserGroupBO> userGroupBOList) {
        try {
            List<UserGroup> userGroupList = GeneralConvertor.convertor(userGroupBOList, UserGroup.class);
            return userGroupMapper.insertBatch(userGroupList);
        } catch (Exception e) {
            log.error("用户组关联表【userGroupBOList：{}】批量新增异常：", JsonUtil.json(userGroupBOList), e);
            throw e;
        }
    }

    /**
     * 修改
     *
     * @param userGroupBO:
     * @return java.lang.Boolean
     * @author 孙凯伦
     * @since 2021-10-21 01:50:39
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, timeout = 36000, rollbackFor = Exception.class)
    public Boolean updateEnhance(UserGroupBO userGroupBO) {
        UserGroup userGroup = GeneralConvertor.convertor(userGroupBO, UserGroup.class);
        UpdateWrapper<UserGroup> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id", userGroupBO.getId());
        Integer i = userGroupMapper.update(userGroup, updateWrapper);
        return i > 0 ? true : false;
    }


    /**
     * 删除
     *
     * @param userGroupBO:
     * @return java.lang.Boolean
     * @author 孙凯伦
     * @since 2021-10-21 01:50:39
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, timeout = 36000, rollbackFor = Exception.class)
    public Boolean removeEnhance(UserGroupBO userGroupBO) {
        UserGroup userGroup = GeneralConvertor.convertor(userGroupBO, UserGroup.class);
        QueryWrapper<UserGroup> queryWrapper = new QueryWrapper<>(userGroup);
        Integer i = userGroupMapper.delete(queryWrapper);
        return i > 0 ? true : false;
    }


    /**
     * 查询人工查询条件
     *
     * @param userGroupQuery 用户组
     * @return QueryWrapper
     * @author 孙凯伦
     * @since 2021-10-21 01:50:39
     */
    private QueryWrapper queryArtificial(UserGroupQuery userGroupQuery, QueryWrapper<UserGroup> queryWrapper) {
        //模糊查询
        if (StringUtils.isNotBlank(userGroupQuery.getQuery())) {
            queryWrapper
                    .inSql("`user_id`", "select `id` from `user` where `user_name` like \"" + userGroupQuery.getQuery() + "%\"")
                    .or()
                    .inSql("`user_id`", "select `user_id` from `user_extends` where `name` like \"" + userGroupQuery.getQuery() + "%\"");
        }
        return queryWrapper;
    }
}