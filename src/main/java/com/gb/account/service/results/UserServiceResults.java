package com.gb.account.service.results;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.map.MapUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gb.account.entity.User;
import com.gb.account.entity.query.UserExtendsQuery;
import com.gb.account.entity.query.UserQuery;
import com.gb.account.entity.query.UserTypeValueQuery;
import com.gb.account.entity.vo.UserExtendsVO;
import com.gb.account.entity.vo.UserVO;
import com.gb.account.service.UserExtendsService;
import com.gb.account.service.UserTypeValueService;
import com.gb.permissions.entity.query.GroupQuery;
import com.gb.permissions.entity.query.ResourceQuery;
import com.gb.permissions.entity.query.RoleQuery;
import com.gb.permissions.entity.vo.ResourceVO;
import com.gb.permissions.service.GroupService;
import com.gb.permissions.service.ResourceService;
import com.gb.permissions.service.RoleService;
import com.gb.rpc.component.RpcComponent;
import com.gb.rpc.enums.RpcTypeEnum;
import com.gb.user.service.UserQueryService;
import com.gb.utils.GeneralConvertor;
import com.gb.utils.constants.UniversalConstant;
import com.gb.utils.enumeration.NumericEnum;
import com.google.common.collect.Maps;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;


/**
 * Created with IntelliJ IDEA.
 *
 * @author: 孙凯伦
 * @since: 2021-10-21 11:06:07
 * @description: TODO 用户表,Service返回实现
 * @source: 代码生成器
 */
@Slf4j
@Service
@Setter(onMethod_ = {@Autowired})
public class UserServiceResults {


    /**
     * 用户类型值表
     */
    private UserTypeValueService userTypeValueService;


    /**
     * 角色表
     */
    private RoleService roleService;


    /**
     * 组
     */
    private GroupService groupService;


    /**
     * 用户扩展表
     */
    private UserExtendsService userExtendsService;

    private UserQueryService userQueryService;

    private RpcComponent rpcComponent;

    private ResourceService resourceService;


    /**
     * 单条，增强返回参数追加
     *
     * @param userVO 用户表
     * @return UserVO
     * @author 孙凯伦
     * @since 2021-10-21 11:06:07
     */
    public UserVO assignment(UserQuery userQuery, UserVO userVO) {
        if(Objects.isNull(userVO)){
            return userVO;
        }
        //查询企业信息
        if(userQuery.isQueryEnterpriseInfo() && StringUtils.isNotBlank(userQuery.getUserTypeCode())) {
            Map<String, Object> objMap = userQueryService.queryUserEnterpriseInfoList(new ArrayList<UserVO>(){{add(userVO);}}, userQuery);
            if(MapUtil.isNotEmpty(objMap) && objMap.containsKey(userVO.getId())){
                List<Object> enterpriseList = (List)objMap.get(userVO.getId());
                if (!StringUtils.equals(userQuery.getUserTypeCode(), String.valueOf(NumericEnum.THREE.getValue())) && CollectionUtils.isNotEmpty(enterpriseList)) {
                    enterpriseList = enterpriseList.stream().filter(item -> Objects.nonNull(item) && StringUtils.isNotBlank(String.valueOf(item))).distinct().collect(Collectors.toList());
                }
                userVO.setLinkEnterpriseNameList(enterpriseList);
            }
        }
        //标签
        userVO.setValueList(userTypeValueService.listEnhance(new UserTypeValueQuery() {{
            setUserId(userVO.getId());
        }}));
        //用户角色
        userVO.setRoleList(roleService.listEnhance(new RoleQuery(){{
            setUserId(userVO.getId());
        }}));
        //用户组
        userVO.setGroupList(groupService.listEnhance(new GroupQuery(){{
            setUserId(userVO.getId());
        }}));
        //用户扩展表
        userVO.setUserExtends(userExtendsService.getOneEnhance(new UserExtendsQuery(){{
            setUserId(userVO.getId());
        }}));
        //业务明细
        String userSource = userVO.getSourceName() + "-" + userVO.getSourceValueName();
        if(StringUtils.isNotBlank(userVO.getBusinessDetails())) {
            userSource = userSource.concat("-");
            String businessDetails = userVO.getBusinessDetails();
            if(businessDetails.contains(UniversalConstant.H_SEPARATOR)) {
                String[] businessDetailsArray = businessDetails.split(UniversalConstant.H_SEPARATOR);
                for(int i = 0; i< businessDetailsArray.length; i++) {
                    String value = businessDetailsArray[i];
                    if(i != 0) {
                        ResourceQuery resourceQuery = new ResourceQuery();
                        resourceQuery.setId(value);
                        ResourceVO resourceVO = resourceService.getOneEnhance(resourceQuery);
                        if(Objects.nonNull(resourceVO)) {
                            value = "-".concat(StringUtils.defaultString(resourceVO.getName()));
                        }else{
                            value = "-".concat(value);
                        }
                    }
                    userSource = userSource.concat(value);
                }
            } else {
                // 代码优化：原始版本B-3.2.4 统计用户来源 businessDetails字段存的是【表单id、险种id、险种id】可查看相关蓝湖，
                // 由于后期版本 刷数据【业务明细刷为"G端保单"】数据量较多，导致该rpc每次查询都是空，所以增加此判断，减少空访问customer服务量
                if (businessDetails.matches("[0-9]*")) {
                    HashMap<String, String> params = Maps.newHashMap();
                    params.put(UniversalConstant.ID, businessDetails);
                    Map<String, Object> userSourceMap = rpcComponent.rpcQuery(params, RpcTypeEnum.PROMOTE_FORM_GET, Map.class);
                    if(MapUtils.isEmpty(userSourceMap)) {
                        userSourceMap = rpcComponent.rpcQuery(params, RpcTypeEnum.DANGER_PLANTED_ONE, Map.class);
                    }
                    if(MapUtils.isNotEmpty(userSourceMap)) {
                        userSource = userSource.concat(Convert.toStr(userSourceMap.get(UniversalConstant.NAME), StringUtils.EMPTY));
                    } else {
                        userSource = userSource.concat(businessDetails);
                    }

                } else {
                    userSource = userSource.concat(businessDetails);
                }
            }
        }
        userVO.setUserSource(userSource);
        return userVO;
    }


    /**
     * 分页，增强返回参数追加
     *
     * @param userVOList 用户表
     * @return Page<UserVO>
     * @author 孙凯伦
     * @since 2021-10-21 11:06:07
     */
    public Page<UserVO> assignment(Map<String, Object> objMap, UserQuery userQuery, Page<UserVO> userVOList) throws Exception {
        if(CollectionUtils.isEmpty(userVOList.getRecords())){
            return userVOList;
        }
        if(userQuery.isQueryEnterpriseInfo() && StringUtils.isNotBlank(userQuery.getUserTypeCode())){
            if(MapUtil.isEmpty(objMap)){
                objMap = userQueryService.queryUserEnterpriseInfoList(userVOList.getRecords(), userQuery);
            }
            Map<String, Object> userEnterpriseInfoMap = objMap;
            userVOList.getRecords().forEach(s -> {
                if(MapUtils.isNotEmpty(userEnterpriseInfoMap) && userEnterpriseInfoMap.containsKey(s.getId())){
                    List<Object> enterpriseList = (List)userEnterpriseInfoMap.get(s.getId());
                    if (!StringUtils.equals(userQuery.getUserTypeCode(), String.valueOf(NumericEnum.THREE.getValue())) && CollectionUtils.isNotEmpty(enterpriseList)) {
                        enterpriseList = enterpriseList.stream().filter(item -> Objects.nonNull(item) && StringUtils.isNotBlank(String.valueOf(item))).distinct().collect(Collectors.toList());
                    }
                    s.setLinkEnterpriseNameList(enterpriseList);
                }
            });
        } else {
            userVOList.getRecords().forEach(userVO -> {
                userVO.setValueList(userTypeValueService.listEnhance(new UserTypeValueQuery() {{
                    setUserId(userVO.getId());
                }}));
            });
        }
        return userVOList;
    }


    /**
     * 集合，增强返回参数追加
     *
     * @param userVOList 用户表
     * @return List<UserVO>
     * @author 孙凯伦
     * @since 2021-10-21 11:06:07
     */
    public List<UserVO> assignment(UserQuery userQuery, List<UserVO> userVOList) {
        if(CollectionUtils.isEmpty(userVOList)){
            return userVOList;
        }
        //1、查询用户扩展表信息
        Map<String, List<UserExtendsVO>> objGroup = Maps.newHashMap();
        if(userQuery.isQueryUserExtendsInfo()){
            List<String> userIdList = userVOList.stream().map(s->s.getId()).collect(Collectors.toList());
            List<UserExtendsVO> userExtendsVOList = userExtendsService.listEnhance(new UserExtendsQuery(){{
                setUserIdList(userIdList);
            }});
            objGroup = userExtendsVOList.stream().collect(Collectors.groupingBy(s->s.getUserId()));
        }
        //1、查询用户企业/机构信息
        Map<String, Object> objMap = Maps.newHashMap();
        if(userQuery.isQueryEnterpriseInfo() && StringUtils.isNotBlank(userQuery.getUserTypeCode())){
            objMap = userQueryService.queryUserEnterpriseInfoList(userVOList, userQuery);
        }
        //3、组装最终的用户信息结果
        Map<String, Object> userEnterpriseMap = objMap;
        Map<String, List<UserExtendsVO>> userInfoGroup = objGroup;
        userVOList.forEach(userVO -> {
            if(MapUtils.isNotEmpty(userInfoGroup) && userInfoGroup.containsKey(userVO.getId())){
                userVO.setUserExtends(userInfoGroup.get(userVO.getId()).get(0));
            }
            if(MapUtils.isNotEmpty(userEnterpriseMap) && userEnterpriseMap.containsKey(userVO.getId())){
                List<Object> enterpriseList = (List)userEnterpriseMap.get(userVO.getId());
                if (!StringUtils.equals(userQuery.getUserTypeCode(), String.valueOf(NumericEnum.THREE.getValue())) && CollectionUtils.isNotEmpty(enterpriseList)) {
                    enterpriseList = enterpriseList.stream().filter(item -> Objects.nonNull(item) && StringUtils.isNotBlank(String.valueOf(item))).distinct().collect(Collectors.toList());
                }
                userVO.setLinkEnterpriseNameList(enterpriseList);
            }
        });
        return userVOList;
    }


    /**
     * DO转化VO
     *
     * @param pageDO 用户表
     * @return com.baomidou.mybatisplus.extension.plugins.pagination.Page
     * @author 孙凯伦
     * @since 2021-10-21 11:06:07
     */
    public Page<UserVO> toPageVO(Page<User> pageDO) {
        Page<UserVO> pageVO = new Page<UserVO>();
        pageVO.setRecords(GeneralConvertor.convertor(pageDO.getRecords(), UserVO.class));
        pageVO.setCurrent(pageDO.getCurrent());
        pageVO.setPages(pageDO.getPages());
        pageVO.setSize(pageDO.getSize());
        pageVO.setTotal(pageDO.getTotal());
        return pageVO;
    }
}