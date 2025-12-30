package com.gb.account.service.results;

import com.gb.account.entity.UserTypeValue;
import com.gb.account.entity.UserTypeValueRegion;
import com.gb.account.entity.vo.UserTypeValueRegionVO;
import com.gb.account.entity.bo.UserTypeValueRegionBO;
import com.gb.account.service.UserTypeValueService;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import com.gb.utils.GeneralConvertor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;


/**
 * TODO 用户类型值地区,Service返回实现
 * 代码生成器
 *
 * @author lijh
 * @className UserTypeValueRegionServiceResults
 * @time 2022-07-12 11:45:19
 */
@Slf4j
@Service
@Setter(onMethod_ = {@Autowired})
public class UserTypeValueRegionServiceResults {

    private UserTypeValueService userTypeValueService;

    /**
     * TODO 单条，增强返回参数追加
     *
     * @param userTypeValueRegionVO 用户类型值地区
     * @return UserTypeValueRegionVO
     * @author lijh
     * @methodName assignment
     * @time 2022-07-12 11:45:19
     */
    public UserTypeValueRegionVO assignment(UserTypeValueRegionVO userTypeValueRegionVO) {
        if (userTypeValueRegionVO != null) {
            UserTypeValue userTypeValue = userTypeValueService.getById(userTypeValueRegionVO.getUserTypeValueId());
            if (Objects.nonNull(userTypeValue)) {
                userTypeValueRegionVO.setUserTypeValueCode(userTypeValue.getCode());
            }
            return userTypeValueRegionVO;
        } else {
            return null;
        }
    }


    /**
     * TODO 分页，增强返回参数追加
     *
     * @param userTypeValueRegionVOList 用户类型值地区
     * @return Page<UserTypeValueRegionVO>
     * @author lijh
     * @methodName assignment
     * @time 2022-07-12 11:45:19
     */
    public Page<UserTypeValueRegionVO> assignment(Page<UserTypeValueRegionVO> userTypeValueRegionVOList) {
        userTypeValueRegionVOList.getRecords().forEach(userTypeValueRegionVO -> {
        });
        return userTypeValueRegionVOList;
    }


    /**
     * TODO 集合，增强返回参数追加
     *
     * @param userTypeValueRegionVOList 用户类型值地区
     * @return List<UserTypeValueRegionVO>
     * @author lijh
     * @methodName assignment
     * @time 2022-07-12 11:45:19
     */
    public List<UserTypeValueRegionVO> assignment(List<UserTypeValueRegionVO> userTypeValueRegionVOList) {
        userTypeValueRegionVOList.forEach(userTypeValueRegionVO -> {
        });
        return userTypeValueRegionVOList;
    }


    /**
     * TODO DO转化VO
     *
     * @param pageDO 用户类型值地区
     * @return Page<UserTypeValueRegionVO>
     * @author lijh
     * @methodName toPageVO
     * @time 2022-07-12 11:45:19
     */
    public Page<UserTypeValueRegionVO> toPageVO(Page<UserTypeValueRegion> pageDO) {
        Page<UserTypeValueRegionVO> pageVO = new Page<UserTypeValueRegionVO>();
        pageVO.setRecords(GeneralConvertor.convertor(pageDO.getRecords(), UserTypeValueRegionVO.class));
        pageVO.setCurrent(pageDO.getCurrent());
        pageVO.setPages(pageDO.getPages());
        pageVO.setSize(pageDO.getSize());
        pageVO.setTotal(pageDO.getTotal());
        return pageVO;
    }
}