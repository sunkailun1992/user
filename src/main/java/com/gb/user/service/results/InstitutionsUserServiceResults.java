package com.gb.user.service.results;

import cn.hutool.core.map.MapUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gb.account.entity.query.UserExtendsQuery;
import com.gb.account.entity.vo.UserExtendsVO;
import com.gb.account.service.UserExtendsService;
import com.gb.user.entity.InstitutionsUser;
import com.gb.user.entity.UserAgentCertification;
import com.gb.user.entity.vo.InstitutionsUserVO;
import com.gb.user.service.UserAgentCertificationService;
import com.gb.utils.GeneralConvertor;
import com.google.common.collect.Maps;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.compress.utils.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;


/**
 * TODO 机构用户关联,Service返回实现
 * 代码生成器
 *
 * @author sunxin
 * @className InstitutionsUserServiceResults
 * @time 2022-07-04 10:48:37
 */
@Slf4j
@Service
@Setter(onMethod_ = {@Autowired})
public class InstitutionsUserServiceResults {

    private UserExtendsService userExtendsService;

    private UserAgentCertificationService userAgentCertificationService;

    /**
     * TODO 单条，增强返回参数追加
     *
     * @param institutionsUserVO 机构用户关联
     * @return InstitutionsUserVO
     * @author sunxin
     * @methodName assignment
     * @time 2022-07-04 10:48:37
     */
    public InstitutionsUserVO assignment(InstitutionsUserVO institutionsUserVO) {
        if (Objects.isNull(institutionsUserVO)) {
            return institutionsUserVO;
        }
        List<InstitutionsUserVO> institutionsUserVOList = Lists.newArrayList();
        institutionsUserVOList.add(institutionsUserVO);
        return assignment(institutionsUserVOList).get(0);
    }


    /**
     * TODO 分页，增强返回参数追加
     *
     * @param institutionsUserVOList 机构用户关联
     * @return Page<InstitutionsUserVO>
     * @author sunxin
     * @methodName assignment
     * @time 2022-07-04 10:48:37
     */
    public Page<InstitutionsUserVO> assignment(Page<InstitutionsUserVO> institutionsUserVOList) {
        if(Objects.isNull(institutionsUserVOList) || CollectionUtils.isEmpty(institutionsUserVOList.getRecords())) {
            return institutionsUserVOList;
        }
        List<InstitutionsUserVO> resultList = assignment(institutionsUserVOList.getRecords());
        institutionsUserVOList.setRecords(resultList);
        return institutionsUserVOList;
    }


    /**
     * TODO 集合，增强返回参数追加
     *
     * @param institutionsUserVOList 机构用户关联
     * @return List<InstitutionsUserVO>
     * @author sunxin
     * @methodName assignment
     * @time 2022-07-04 10:48:37
     */
    public List<InstitutionsUserVO> assignment(List<InstitutionsUserVO> institutionsUserVOList) {
        if(CollectionUtils.isEmpty(institutionsUserVOList)) {
            return institutionsUserVOList;
        }
        Map<String, List<InstitutionsUserVO>> userIdGroupMap = institutionsUserVOList.stream().collect(Collectors.groupingBy(InstitutionsUserVO::getUserId));
        List<String> userIdList = Arrays.asList(userIdGroupMap.keySet().toArray(new String[0]));
        //1、用户扩展信息查询
        List<UserExtendsVO> userExtendsVOList = userExtendsService.listEnhance(new UserExtendsQuery(){{
            setUserIdList(userIdList);
        }});
        Map<String, List<UserExtendsVO>> userExtendsVoGroupMap = Maps.newHashMap();
        if(CollectionUtils.isNotEmpty(userExtendsVOList)) {
            userExtendsVoGroupMap = userExtendsVOList.stream().collect(Collectors.groupingBy(UserExtendsVO::getUserId));
        }
        //2、经纪人查询
        List<UserAgentCertification> userAgentCertificationList = userAgentCertificationService.listEnhance(new UserAgentCertification(){{setUserIdList(userIdList);}});
        Map<String, List<UserAgentCertification>> agentCertGroupMap = Maps.newHashMap();
        if(CollectionUtils.isNotEmpty(userAgentCertificationList)) {
            agentCertGroupMap = userAgentCertificationList.stream().collect(Collectors.groupingBy(UserAgentCertification::getUserId));
        }
        Map<String, List<UserAgentCertification>> certGroupMap = agentCertGroupMap;
        Map<String, List<UserExtendsVO>> extendsVoGroupMap = userExtendsVoGroupMap;
        institutionsUserVOList.forEach(s -> {
            if(MapUtil.isNotEmpty(certGroupMap) && CollectionUtils.isNotEmpty(certGroupMap.get(s.getUserId()))) {
                s.setUserAgentCertification(certGroupMap.get(s.getUserId()).get(0));
            }
            if(MapUtil.isNotEmpty(extendsVoGroupMap) && CollectionUtils.isNotEmpty(extendsVoGroupMap.get(s.getUserId()))) {
                s.setUserExtendsVO(extendsVoGroupMap.get(s.getUserId()).get(0));
            }
        });

        return institutionsUserVOList;
    }


    /**
     * TODO DO转化VO
     *
     * @param pageDO 机构用户关联
     * @return Page<InstitutionsUserVO>
     * @author sunxin
     * @methodName toPageVO
     * @time 2022-07-04 10:48:37
     */
    public Page<InstitutionsUserVO> toPageVO(Page<InstitutionsUser> pageDO) {
        Page<InstitutionsUserVO> pageVO = new Page<InstitutionsUserVO>();
        pageVO.setRecords(GeneralConvertor.convertor(pageDO.getRecords(), InstitutionsUserVO.class));
        pageVO.setCurrent(pageDO.getCurrent());
        pageVO.setPages(pageDO.getPages());
        pageVO.setSize(pageDO.getSize());
        pageVO.setTotal(pageDO.getTotal());
        return pageVO;
    }
}