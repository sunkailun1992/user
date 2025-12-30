package com.gb.user.service.results;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gb.account.entity.enums.UserFormalStateEnum;
import com.gb.account.entity.enums.UserStateEnum;
import com.gb.account.entity.query.UserQuery;
import com.gb.account.entity.vo.UserExtendsVO;
import com.gb.account.entity.vo.UserVO;
import com.gb.account.service.UserService;
import com.gb.platform.entity.query.TransformationExternalPlatformSystemUserQuery;
import com.gb.platform.entity.vo.TransformationExternalPlatformSystemUserVO;
import com.gb.platform.service.TransformationExternalPlatformSystemUserService;
import com.gb.user.entity.TeamUser;
import com.gb.user.entity.vo.TeamUserVO;
import com.gb.utils.GeneralConvertor;
import com.google.common.collect.Sets;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import static com.gb.account.entity.enums.UserFormalStateEnum.*;


/**
 * TODO 团队人员,Service返回实现
 * 代码生成器
 *
 * @author sunx
 * @className TeamUserServiceResults
 * @time 2022-08-31 11:01:59
 */
@Slf4j
@Service
@Setter(onMethod_ = {@Autowired})
public class TeamUserServiceResults {

    private UserService userService;

    private TransformationExternalPlatformSystemUserService transformationExternalPlatformSystemUserService;

    /**
     * TODO 单条，增强返回参数追加
     *
     * @param teamUserVO 团队人员
     * @return TeamUserVO
     * @author sunx
     * @methodName assignment
     * @time 2022-08-31 11:01:59
     */
    public TeamUserVO assignment(TeamUserVO teamUserVO) {
       if(Objects.isNull(teamUserVO)) {
           return teamUserVO;
       }
       //查询团队人员姓名、业务助理姓名、业务助理手机号、业务助理名称
        Set<String> userIdSet = Sets.newTreeSet();
        if(StringUtils.isNotBlank(teamUserVO.getUserId())) {
            userIdSet.add(teamUserVO.getUserId());
        }
        if(StringUtils.isNotBlank(teamUserVO.getAssistantUserId())) {
            userIdSet.add(teamUserVO.getAssistantUserId());
        }
       if(CollectionUtils.isEmpty(userIdSet)) {
           return teamUserVO;
       }
       List<UserVO> userList = userService.listEnhance(new UserQuery() {{
           setUserIdList(new ArrayList<String>(userIdSet));
           setQueryUserExtendsInfo(true);
       }});
       if(CollectionUtils.isNotEmpty(userList)) {
            Map<String, UserVO> userMap = userList.stream().collect(Collectors.toMap(UserVO::getId, data -> data));
            if(StringUtils.isNotBlank(teamUserVO.getUserId()) && Objects.nonNull(userMap.get(teamUserVO.getUserId()))) {
                teamUserVO.setUserName(userMap.get(teamUserVO.getUserId()).getName());
                teamUserVO.setUserMobile(userMap.get(teamUserVO.getUserId()).getMobile());
                UserFormalStateEnum userFormalStateEnum = userMap.get(teamUserVO.getUserId()).getState().equals(UserStateEnum.注销) ? 注销 : 在职;
                UserExtendsVO userExtendsVO = userMap.get(teamUserVO.getUserId()).getUserExtends();
                if(!userFormalStateEnum.equals(注销) && Objects.nonNull(userExtendsVO) && Objects.nonNull(userExtendsVO.getLeaveDateTime())) {
                    userFormalStateEnum = 离职;
                }
                teamUserVO.setUserFormalStateEnum(userFormalStateEnum);
            }
            if(StringUtils.isNotBlank(teamUserVO.getAssistantUserId()) && Objects.nonNull(userMap.get(teamUserVO.getAssistantUserId()))) {
                teamUserVO.setAssistantUserName(userMap.get(teamUserVO.getAssistantUserId()).getName());
                teamUserVO.setAssistantUserMobile(userMap.get(teamUserVO.getAssistantUserId()).getMobile());
                int assistantUserFormalStateValue = userMap.get(teamUserVO.getAssistantUserId()).getState().equals(UserStateEnum.注销) ? 注销.getValue() : 在职.getValue();
                UserExtendsVO userExtendsVO = userMap.get(teamUserVO.getAssistantUserId()).getUserExtends();
                if(assistantUserFormalStateValue != 1 && Objects.nonNull(userExtendsVO) && Objects.nonNull(userExtendsVO.getLeaveDateTime())) {
                    assistantUserFormalStateValue = 离职.getValue();
                }
                teamUserVO.setAssistantUserFormalStateValue(assistantUserFormalStateValue);
            }
       }
       List<TransformationExternalPlatformSystemUserVO> transformationExternalPlatformSystemUserVOList = transformationExternalPlatformSystemUserService.listEnhance(new TransformationExternalPlatformSystemUserQuery(){{
           setUserId(teamUserVO.getUserId());
           setAssignment(true);
       }});
       teamUserVO.setExternalPlatformSystemList(transformationExternalPlatformSystemUserVOList);
       return teamUserVO;
    }


    /**
     * TODO 分页，增强返回参数追加
     *
     * @param teamUserVOList 团队人员
     * @return Page<TeamUserVO>
     * @author sunx
     * @methodName assignment
     * @time 2022-08-31 11:01:59
     */
    public Page<TeamUserVO> assignment(Page<TeamUserVO> teamUserVOList) {
        if(Objects.isNull(teamUserVOList)) {
            return teamUserVOList;
        }
        List<TeamUserVO> teamUserList = assignment(teamUserVOList.getRecords());
        teamUserVOList.setRecords(teamUserList);
        return teamUserVOList;
    }


    /**
     * TODO 集合，增强返回参数追加
     *
     * @param teamUserVOList 团队人员
     * @return List<TeamUserVO>
     * @author sunx
     * @methodName assignment
     * @time 2022-08-31 11:01:59
     */
    public List<TeamUserVO> assignment(List<TeamUserVO> teamUserVOList) {
        if(CollectionUtils.isEmpty(teamUserVOList)) {
            return teamUserVOList;
        }
        teamUserVOList.forEach(teamUserVO -> {
            assignment(teamUserVO);
        });
        return teamUserVOList;
    }


    /**
     * TODO DO转化VO
     *
     * @param pageDO 团队人员
     * @return Page<TeamUserVO>
     * @author sunx
     * @methodName toPageVO
     * @time 2022-08-31 11:01:59
     */
    public Page<TeamUserVO> toPageVO(Page<TeamUser> pageDO) {
        Page<TeamUserVO> pageVO = new Page<TeamUserVO>();
        pageVO.setRecords(GeneralConvertor.convertor(pageDO.getRecords(), TeamUserVO.class));
        pageVO.setCurrent(pageDO.getCurrent());
        pageVO.setPages(pageDO.getPages());
        pageVO.setSize(pageDO.getSize());
        pageVO.setTotal(pageDO.getTotal());
        return pageVO;
    }
}