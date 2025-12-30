package com.gb.user.service.results;

import com.gb.user.entity.UserNotSpu;
import com.gb.user.entity.vo.UserNotSpuVO;
import com.gb.user.entity.bo.UserNotSpuBO;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import com.gb.utils.GeneralConvertor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * TODO 企业渠道用户排除产品,Service返回实现
 * 代码生成器
 *
 * @author 孙凯伦
 * @className UserNotSpuServiceResults
 * @time 2023-07-07 04:36:59
 */
@Slf4j
@Service
@Setter(onMethod_ = {@Autowired})
public class UserNotSpuServiceResults {


    /**
     * TODO 单条，增强返回参数追加
     *
     * @param userNotSpuVO 企业渠道用户排除产品
     * @return UserNotSpuVO
     * @author 孙凯伦
     * @methodName assignment
     * @time 2023-07-07 04:36:59
     */
    public UserNotSpuVO assignment(UserNotSpuVO userNotSpuVO) {
        if (userNotSpuVO != null) {
            return userNotSpuVO;
        } else {
            return userNotSpuVO;
        }
    }


    /**
     * TODO 分页，增强返回参数追加
     *
     * @param userNotSpuVOList 企业渠道用户排除产品
     * @return Page<UserNotSpuVO>
     * @author 孙凯伦
     * @methodName assignment
     * @time 2023-07-07 04:36:59
     */
    public Page<UserNotSpuVO> assignment(Page<UserNotSpuVO> userNotSpuVOList) {
        userNotSpuVOList.getRecords().forEach(userNotSpuVO -> {
        });
        return userNotSpuVOList;
    }


    /**
     * TODO 集合，增强返回参数追加
     *
     * @param userNotSpuVOList 企业渠道用户排除产品
     * @return List<UserNotSpuVO>
     * @author 孙凯伦
     * @methodName assignment
     * @time 2023-07-07 04:36:59
     */
    public List<UserNotSpuVO> assignment(List<UserNotSpuVO> userNotSpuVOList) {
        userNotSpuVOList.forEach(userNotSpuVO -> {
        });
        return userNotSpuVOList;
    }


    /**
     * TODO DO转化VO
     *
     * @param pageDO 企业渠道用户排除产品
     * @return Page<UserNotSpuVO>
     * @author 孙凯伦
     * @methodName toPageVO
     * @time 2023-07-07 04:36:59
     */
    public Page<UserNotSpuVO> toPageVO(Page<UserNotSpu> pageDO) {
        Page<UserNotSpuVO> pageVO = new Page<UserNotSpuVO>();
        pageVO.setRecords(GeneralConvertor.convertor(pageDO.getRecords(), UserNotSpuVO.class));
        pageVO.setCurrent(pageDO.getCurrent());
        pageVO.setPages(pageDO.getPages());
        pageVO.setSize(pageDO.getSize());
        pageVO.setTotal(pageDO.getTotal());
        return pageVO;
    }
}