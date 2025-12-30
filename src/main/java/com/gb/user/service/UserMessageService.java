package com.gb.user.service;

import com.gb.user.entity.UserMessage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import java.util.List;


/**
 * <p>
 * 用户消息表 服务类
 * </p>
 *
 * @author ranyang
 * @since 2021-06-01
 */
public interface UserMessageService extends IService<UserMessage> {


    /**
     * 集合条件查询
     * @author ranyang
     * @since 2021-06-01
     * @param userMessage:
     * @return      java.util.List<com.entity.UserMessage>
     */
    List<UserMessage> listEnhance(UserMessage userMessage);


    /**
     * 分页条件查询
     * @author ranyang
     * @since 2021-06-01
     * @param page:
     * @param userMessage:
     * @return      java.util.List<com.entity.UserMessage>
     */
    IPage pageEnhance(Page page, UserMessage userMessage);


    /**
     * 单条条件查询
     * @author ranyang
     * @since 2021-06-01
     * @param userMessage:
     * @return      java.util.List<com.entity.UserMessage>
     */
    UserMessage getOneEnhance(UserMessage userMessage);


    /**
     * 总数
     * @author ranyang
     * @since 2021-06-01
     * @param userMessage:
     * @return      java.util.List<com.entity.UserMessage>
     */
    Long countEnhance(UserMessage userMessage);


    /**
     * 新增
     * @author ranyang
     * @since 2021-06-01
     * @param userMessage:
     * @return      java.util.List<com.entity.UserMessage>
     */
    String saveEnhance(UserMessage userMessage);


    /**
     * 修改
     * @author ranyang
     * @since 2021-06-01
     * @param userMessage:
     * @return      java.util.List<com.entity.UserMessage>
     */
    Boolean updateEnhance(UserMessage userMessage);


    /**
     * 删除
     * @author ranyang
     * @since 2021-06-01
     * @param userMessage:
     * @return      java.util.List<com.entity.UserMessage>
     */
    Boolean removeEnhance(UserMessage userMessage);

    /**
     * 消息已读
     * @param userMessage
     * @return
     */
    Boolean msgRead(UserMessage userMessage);

    /**
     * 未读消息数量
     * @param userMessage
     * @return
     */
    Long unreadMsgNum(UserMessage userMessage);
}
