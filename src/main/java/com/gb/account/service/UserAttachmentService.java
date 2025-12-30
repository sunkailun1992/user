package com.gb.account.service;

import com.gb.account.entity.query.UserAttachmentQuery;
import com.gb.account.entity.vo.UserAttachmentVO;
import com.gb.account.entity.bo.UserAttachmentBO;
import com.gb.account.entity.UserAttachment;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import java.util.List;


/**
 * TODO 用户附件，Service服务接口层
 * 代码生成器
 *
 * @author lijh
 * @className UserAttachmentService
 * @time 2022-04-14 10:04:04
 */
public interface UserAttachmentService extends IService<UserAttachment> {


    /**
     * TODO 分页
     *
     * @param page
     * @param userAttachmentQuery 用户附件
     * @return Page<UserAttachmentVO>
     * @author {author}
     * @methodName pageEnhance
     * @time 2022-04-14 10:04:04
     */
    Page<UserAttachmentVO> pageEnhance(Page page, UserAttachmentQuery userAttachmentQuery);


    /**
     * TODO 集合
     *
     * @param userAttachmentQuery 用户附件
     * @return List<UserAttachmentVO>
     * @author lijh
     * @methodName listEnhance
     * @time 2022-04-14 10:04:04
     */
    List<UserAttachmentVO> listEnhance(UserAttachmentQuery userAttachmentQuery);


    /**
     * TODO 单条
     *
     * @param userAttachmentQuery 用户附件
     * @return UserAttachmentVO
     * @author lijh
     * @methodName getOneEnhance
     * @time 2022-04-14 10:04:04
     */
    UserAttachmentVO getOneEnhance(UserAttachmentQuery userAttachmentQuery);


    /**
     * TODO 总数
     *
     * @param userAttachmentQuery 用户附件
     * @return Long
     * @author lijh
     * @methodName countEnhance
     * @time 2022-04-14 10:04:04
     */
    Long countEnhance(UserAttachmentQuery userAttachmentQuery);


    /**
     * TODO 新增
     *
     * @param userAttachmentBO 用户附件
     * @return String
     * @author lijh
     * @methodName saveEnhance
     * @time 2022-04-14 10:04:04
     */
    String saveEnhance(UserAttachmentBO userAttachmentBO);


    /**
     * TODO 修改
     *
     * @param userAttachmentBO 用户附件
     * @return Boolean
     * @author lijh
     * @methodName updateEnhance
     * @time 2022-04-14 10:04:04
     */
    Boolean updateEnhance(UserAttachmentBO userAttachmentBO);


    /**
     * TODO 删除
     *
     * @param userAttachmentBO 用户附件
     * @return Boolean
     * @author lijh
     * @methodName removeEnhance
     * @time 2022-04-14 10:04:04
     */
    Boolean removeEnhance(UserAttachmentBO userAttachmentBO);
}
