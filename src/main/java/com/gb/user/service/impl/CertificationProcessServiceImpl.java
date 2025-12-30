package com.gb.user.service.impl;

import com.alibaba.fastjson.JSON;
import com.gb.account.entity.bo.UserBO;
import com.gb.account.entity.bo.UserRoleBO;
import com.gb.account.entity.query.UserExtendsQuery;
import com.gb.account.entity.vo.UserExtendsVO;
import com.gb.account.service.UserExtendsService;
import com.gb.account.service.UserRoleService;
import com.gb.rpc.component.RpcComponent;
import com.gb.user.entity.enums.UserAgentCertificationStateEnum;
import com.gb.user.enums.RoleUserTypeRelatedEnum;
import com.gb.user.service.CertificationProcessService;
import com.gb.user.service.GbtTransferProcessService;
import com.gb.utils.RedisUtils;
import com.gb.utils.constants.UniversalConstant;
import com.gb.utils.exception.ParameterNullException;
import com.google.common.collect.Maps;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.Objects;

import static com.gb.rpc.enums.RpcTypeEnum.CERT_BROKER_CRM;

/**
 * <p>
 * 认证流程 服务类接口实现类
 * </p>
 *
 * @author sunx
 * @since 2021-05-25
 */
@Slf4j
@Service
@Setter(onMethod_ = {@Autowired})
public class CertificationProcessServiceImpl implements CertificationProcessService {

    private GbtTransferProcessService gbtTransferProcessService;

    private UserExtendsService userExtendsService;

    private RpcComponent rpcComponent;

    private UserRoleService userRoleService;

    private StringRedisTemplate stringRedisTemplate;

    @Override
    public void certificationSuccessProcess(HttpServletRequest httpServletRequest, Boolean isUpdateUserInfo, UserBO userBO, Integer oldState, Integer newState) {
        if(Objects.isNull(userBO)) {
            return;
        }
        log.debug("经纪人认证成功的处理流程---请求参数【isUpdateUserInfo：{}, userBO：{}】", isUpdateUserInfo, JSON.toJSONString(userBO));
        if(Objects.isNull(isUpdateUserInfo)) {
            isUpdateUserInfo = false;
            if(!UserAgentCertificationStateEnum.认证成功.getValue().equals(oldState) && UserAgentCertificationStateEnum.认证成功.getValue().equals(newState)){
                isUpdateUserInfo = true;
            }
        }
        //1、更新用户信息
        if(isUpdateUserInfo) {
            //1.2、用户信息更新同步到工保通
            gbtTransferProcessService.updateUserInfo(httpServletRequest, userBO);
            //1.1、经纪人信息，需要更新到库【2个页面涉及：经纪人认证，经纪人管理】
            userExtendsService.updateEnhance(userBO.getUserExtendsBO(), null, null);

        }
        //2、老的经纪人状态已经是认证成功的，无须通知---通知CRM系统及修改角色为非正式经纪人了【需求：已经认证过得经纪人，不能变】
        if(UserAgentCertificationStateEnum.认证成功.getValue().equals(oldState) || !UserAgentCertificationStateEnum.认证成功.getValue().equals(newState)) {
            return;
        }
        //3、更新经纪人角色为正式经纪人
        updateBrokerRole(userBO.getId(), userBO.getUserExtendsBO().getModifyName());
        //4、获取操作的用户姓名、用户序列--通知CRM系统
        String opName = UniversalConstant.SYS_OP_NAME;
        String opUserId = UniversalConstant.SYS_OP_ID;
        Map<String, Object> u = RedisUtils.getToken(stringRedisTemplate, httpServletRequest);
        if(MapUtils.isNotEmpty(u)) {
            if (Objects.nonNull(u.get(UniversalConstant.ID)) && StringUtils.isNotBlank(String.valueOf(u.get(UniversalConstant.ID)))) {
                opUserId = String.valueOf(u.get(UniversalConstant.ID));
            }
            if (Objects.nonNull(u.get(UniversalConstant.NAME)) && StringUtils.isNotBlank(String.valueOf(u.get(UniversalConstant.NAME)))) {
                opName = String.valueOf(u.get(UniversalConstant.NAME));
            }
        }
        noticeCrmByCertUserInfo(userBO.getId(), opUserId, opName);
        log.debug("经纪人认证成功的处理流程结束.");
    }

    /**
     * 更新非正式经纪人、工保网经纪人角色为正式经纪人角色
     *
     * @param userId: 用户ID
     * @return void
     * @author sunx
     * @since 2021-11-19
     */
    private void updateBrokerRole(String userId, String opName) {
        //1、移除用户的其他角色配置
        userRoleService.removeEnhance(new UserRoleBO(){{
            setUserId(userId);
            setRoleCode(RoleUserTypeRelatedEnum.非正式经纪人.getRoleCode());
        }});
        //2、关联用户与正式经纪人的角色配置
        userRoleService.saveEnhance(new UserRoleBO(){{
            setRoleCode(RoleUserTypeRelatedEnum.正式经纪人.getRoleCode());
            setUserId(userId);
            setCreateName(opName);
        }});
    }

    /**
     * 已经认证的用户信息通知CRM
     *
     * @param userId:                 经纪人ID
     * @param opUserId: 操作员用户标识
     * @param opName: 操作员用户名称
     * @return void
     * @author sunx
     * @since 2021-05-25
     */
    private void noticeCrmByCertUserInfo(String userId, String opUserId, String opName) {
        UserExtendsVO userExtendsVO = userExtendsService.getOneEnhance(new UserExtendsQuery(){{
            setUserId(userId);
        }});
        if(Objects.isNull(userExtendsVO)) {
            log.error("userId：{}，对应用户扩展信息表记录不存在！", userId);
            throw new ParameterNullException("用户不存在！");
        }
        //4、通知CRM系统
        try {
            Map<String, String> requestMap = Maps.newHashMap();
            requestMap.put(UniversalConstant.MOBILE, userExtendsVO.getMobile());
            requestMap.put("userId", userId);
            if (StringUtils.isNotBlank(userExtendsVO.getName())) {
                requestMap.put(UniversalConstant.NAME, userExtendsVO.getName());
            }
            requestMap.put("agentUserId", opUserId);
            requestMap.put("agentUserName", opName);
            log.debug("【经纪人认证结束】开始RPC调用【CRM-认证经纪人】【请求参数：{}】：", JSON.toJSONString(requestMap));
            rpcComponent.rpcQuery(requestMap, CERT_BROKER_CRM, Boolean.class);
            log.debug("【经纪人认证结束】RPC调用【CRM-认证经纪人】结束。");
        } catch (Exception e) {
            log.error("RPC调用【CRM-认证经纪人】异常：", e);
        }
    }
}
