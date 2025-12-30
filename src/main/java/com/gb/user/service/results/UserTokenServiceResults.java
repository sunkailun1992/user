package com.gb.user.service.results;

import com.gb.user.entity.UserToken;
import com.gb.user.entity.vo.UserTokenVO;
import com.gb.user.entity.bo.UserTokenBO;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import com.gb.utils.GeneralConvertor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * TODO 用户设备信息表,Service返回实现
 * 代码生成器
 *
 * @author wgs
 * @className UserTokenServiceResults
 * @time 2022-01-20 03:40:09
 */
@Slf4j
@Service
@Setter(onMethod_ = {@Autowired})
public class UserTokenServiceResults {


    /**
     * TODO 单条，增强返回参数追加
     *
     * @param userTokenVO 用户设备信息表
     * @return UserTokenVO
     * @author wgs
     * @methodName assignment
     * @time 2022-01-20 03:40:09
     */
    public UserTokenVO assignment(UserTokenVO userTokenVO) {
        if (userTokenVO != null) {
            return userTokenVO;
        } else {
            return userTokenVO;
        }
    }


    /**
     * TODO 分页，增强返回参数追加
     *
     * @param userTokenVOList 用户设备信息表
     * @return Page<UserTokenVO>
     * @author wgs
     * @methodName assignment
     * @time 2022-01-20 03:40:09
     */
    public Page<UserTokenVO> assignment(Page<UserTokenVO> userTokenVOList) {
        userTokenVOList.getRecords().forEach(userTokenVO -> {
        });
        return userTokenVOList;
    }


    /**
     * TODO 集合，增强返回参数追加
     *
     * @param userTokenVOList 用户设备信息表
     * @return List<UserTokenVO>
     * @author wgs
     * @methodName assignment
     * @time 2022-01-20 03:40:09
     */
    public List<UserTokenVO> assignment(List<UserTokenVO> userTokenVOList) {
        userTokenVOList.forEach(userTokenVO -> {
        });
        return userTokenVOList;
    }


    /**
     * TODO DO转化VO
     *
     * @param pageDO 用户设备信息表
     * @return Page<UserTokenVO>
     * @author wgs
     * @methodName toPageVO
     * @time 2022-01-20 03:40:09
     */
    public Page<UserTokenVO> toPageVO(Page<UserToken> pageDO) {
        Page<UserTokenVO> pageVO = new Page<UserTokenVO>();
        pageVO.setRecords(GeneralConvertor.convertor(pageDO.getRecords(), UserTokenVO.class));
        pageVO.setCurrent(pageDO.getCurrent());
        pageVO.setPages(pageDO.getPages());
        pageVO.setSize(pageDO.getSize());
        pageVO.setTotal(pageDO.getTotal());
        return pageVO;
    }
}