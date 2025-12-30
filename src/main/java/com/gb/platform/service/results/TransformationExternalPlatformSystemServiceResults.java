package com.gb.platform.service.results;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gb.platform.entity.TransformationExternalPlatformSystem;
import com.gb.platform.entity.query.TransformationExternalPlatformSystemUserQuery;
import com.gb.platform.entity.vo.TransformationExternalPlatformSystemUserVO;
import com.gb.platform.entity.vo.TransformationExternalPlatformSystemVO;
import com.gb.platform.service.TransformationExternalPlatformSystemUserService;
import com.gb.user.entity.query.TeamUserQuery;
import com.gb.user.entity.vo.TeamUserVO;
import com.gb.user.service.TeamUserService;
import com.gb.utils.GeneralConvertor;
import com.google.common.collect.Maps;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.compress.utils.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;


/**
 * TODO 转化外部系统平台,Service返回实现
 * 代码生成器
 *
 * @author sunx
 * @className TransformationExternalPlatformSystemServiceResults
 * @time 2022-12-16 03:10:08
 */
@Slf4j
@Service
@Setter(onMethod_ = {@Autowired})
public class TransformationExternalPlatformSystemServiceResults {

    private TransformationExternalPlatformSystemUserService transformationExternalPlatformSystemUserService;

    private TeamUserService teamUserService;


    /**
     * TODO 单条，增强返回参数追加
     *
     * @param transformationExternalPlatformSystemVO 转化外部系统平台
     * @return TransformationExternalPlatformSystemVO
     * @author sunx
     * @methodName assignment
     * @time 2022-12-16 03:10:08
     */
    public TransformationExternalPlatformSystemVO assignment(TransformationExternalPlatformSystemVO transformationExternalPlatformSystemVO) {
        if (transformationExternalPlatformSystemVO != null) {
            return transformationExternalPlatformSystemVO;
        } else {
            return transformationExternalPlatformSystemVO;
        }
    }


    /**
     * TODO 分页，增强返回参数追加
     *
     * @param transformationExternalPlatformSystemVOList 转化外部系统平台
     * @return Page<TransformationExternalPlatformSystemVO>
     * @author sunx
     * @methodName assignment
     * @time 2022-12-16 03:10:08
     */
    public Page<TransformationExternalPlatformSystemVO> assignment(Page<TransformationExternalPlatformSystemVO> transformationExternalPlatformSystemVOList) {
        transformationExternalPlatformSystemVOList.getRecords().forEach(transformationExternalPlatformSystemVO -> {
        });
        return transformationExternalPlatformSystemVOList;
    }


    /**
     * TODO 集合，增强返回参数追加
     *
     * @param transformationExternalPlatformSystemVOList 转化外部系统平台
     * @return List<TransformationExternalPlatformSystemVO>
     * @author sunx
     * @methodName assignment
     * @time 2022-12-16 03:10:08
     */
    public List<TransformationExternalPlatformSystemVO> assignment(List<TransformationExternalPlatformSystemVO> transformationExternalPlatformSystemVOList) {
        transformationExternalPlatformSystemVOList.forEach(transformationExternalPlatformSystemVO -> {
            if(Objects.nonNull(transformationExternalPlatformSystemVO)) {
                List<TransformationExternalPlatformSystemUserVO> list = transformationExternalPlatformSystemUserService.listEnhance(new TransformationExternalPlatformSystemUserQuery(){{
                    setTransformationExternalPlatformSystemId(transformationExternalPlatformSystemVO.getId());
                    setAssignment(false);
                }});
                if(CollectionUtils.isNotEmpty(list)) {
                    List<Map<String, Object>> teamUserVOList = Lists.newArrayList();
                    for(TransformationExternalPlatformSystemUserVO vo : list) {
                        Map<String, Object> teamUserMap = Maps.newHashMap();
                        TeamUserVO teamUserVO = teamUserService.getOneEnhance(new TeamUserQuery(){{
                            setUserId(vo.getUserId());
                            setAssignment(false);
                        }});
                        if(Objects.nonNull(teamUserVO)) {
                            teamUserMap.put("clue", vo.getClue());
                            teamUserMap.put("userId", teamUserVO.getUserId());
                            teamUserMap.put("userName", teamUserVO.getUserName());
                            teamUserMap.put("assistantUserId", teamUserVO.getAssistantUserId());
                            teamUserMap.put("type", teamUserVO.getType());
                            teamUserMap.put("id", teamUserVO.getId());
                            teamUserMap.put("teamId", teamUserVO.getTeamId());
                            teamUserMap.put("teamName", teamUserVO.getTeamName());
                            teamUserMap.put("personal", teamUserVO.getPersonal());
                            teamUserMap.put("channel", teamUserVO.getChannel());
                            teamUserVOList.add(teamUserMap);
                        }
                    }
                    transformationExternalPlatformSystemVO.setTeamUserVOList(teamUserVOList);
                }
            }
        });
        return transformationExternalPlatformSystemVOList;
    }


    /**
     * TODO DO转化VO
     *
     * @param pageDO 转化外部系统平台
     * @return Page<TransformationExternalPlatformSystemVO>
     * @author sunx
     * @methodName toPageVO
     * @time 2022-12-16 03:10:08
     */
    public Page<TransformationExternalPlatformSystemVO> toPageVO(Page<TransformationExternalPlatformSystem> pageDO) {
        Page<TransformationExternalPlatformSystemVO> pageVO = new Page<TransformationExternalPlatformSystemVO>();
        pageVO.setRecords(GeneralConvertor.convertor(pageDO.getRecords(), TransformationExternalPlatformSystemVO.class));
        pageVO.setCurrent(pageDO.getCurrent());
        pageVO.setPages(pageDO.getPages());
        pageVO.setSize(pageDO.getSize());
        pageVO.setTotal(pageDO.getTotal());
        return pageVO;
    }
}