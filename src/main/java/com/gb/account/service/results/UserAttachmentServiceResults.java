package com.gb.account.service.results;

import com.gb.account.entity.UserAttachment;
import com.gb.account.entity.vo.UserAttachmentVO;
import com.gb.account.entity.bo.UserAttachmentBO;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import com.gb.utils.GeneralConvertor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * TODO 用户附件,Service返回实现
 * 代码生成器
 *
 * @author lijh
 * @className UserAttachmentServiceResults
 * @time 2022-04-14 10:04:04
 */
@Slf4j
@Service
@Setter(onMethod_ = {@Autowired})
public class UserAttachmentServiceResults {


    /**
     * TODO 单条，增强返回参数追加
     *
     * @param userAttachmentVO 用户附件
     * @return UserAttachmentVO
     * @author lijh
     * @methodName assignment
     * @time 2022-04-14 10:04:04
     */
    public UserAttachmentVO assignment(UserAttachmentVO userAttachmentVO) {
        if (userAttachmentVO != null) {
            return userAttachmentVO;
        } else {
            return userAttachmentVO;
        }
    }


    /**
     * TODO 分页，增强返回参数追加
     *
     * @param userAttachmentVOList 用户附件
     * @return Page<UserAttachmentVO>
     * @author lijh
     * @methodName assignment
     * @time 2022-04-14 10:04:04
     */
    public Page<UserAttachmentVO> assignment(Page<UserAttachmentVO> userAttachmentVOList) {
        userAttachmentVOList.getRecords().forEach(userAttachmentVO -> {
        });
        return userAttachmentVOList;
    }


    /**
     * TODO 集合，增强返回参数追加
     *
     * @param userAttachmentVOList 用户附件
     * @return List<UserAttachmentVO>
     * @author lijh
     * @methodName assignment
     * @time 2022-04-14 10:04:04
     */
    public List<UserAttachmentVO> assignment(List<UserAttachmentVO> userAttachmentVOList) {
        userAttachmentVOList.forEach(userAttachmentVO -> {
        });
        return userAttachmentVOList;
    }


    /**
     * TODO DO转化VO
     *
     * @param pageDO 用户附件
     * @return Page<UserAttachmentVO>
     * @author lijh
     * @methodName toPageVO
     * @time 2022-04-14 10:04:04
     */
    public Page<UserAttachmentVO> toPageVO(Page<UserAttachment> pageDO) {
        Page<UserAttachmentVO> pageVO = new Page<UserAttachmentVO>();
        pageVO.setRecords(GeneralConvertor.convertor(pageDO.getRecords(), UserAttachmentVO.class));
        pageVO.setCurrent(pageDO.getCurrent());
        pageVO.setPages(pageDO.getPages());
        pageVO.setSize(pageDO.getSize());
        pageVO.setTotal(pageDO.getTotal());
        return pageVO;
    }
}