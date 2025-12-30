package com.gb.platform.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gb.platform.entity.ExternalPlatform;
import com.gb.platform.entity.bo.ExternalPlatformBO;
import com.gb.platform.entity.query.ExternalPlatformQuery;
import com.gb.platform.entity.vo.ExternalPlatformVO;


/**
 * TODO 外部平台，Service服务接口层
 * 代码生成器
 *
 * @author sunx
 * @className ExternalPlatformService
 * @time 2022-12-16 03:10:07
 */
public interface ExternalPlatformService extends IService<ExternalPlatform> {


    /**
     * TODO 分页
     *
     * @param page
     * @param externalPlatformQuery 外部平台
     * @return Page<ExternalPlatformVO>
     * @author sunx
     * @methodName pageEnhance
     * @time 2022-12-16 03:10:07
     */
    Page<ExternalPlatformVO> pageEnhance(Page page, ExternalPlatformQuery externalPlatformQuery);


    /**
     * TODO 集合
     *
     * @param externalPlatformQuery 外部平台
     * @return Object
     * @author sunx
     * @methodName listEnhance
     * @time 2022-12-16 03:10:07
     */
    Object listEnhance(ExternalPlatformQuery externalPlatformQuery);


    /**
     * TODO 单条
     *
     * @param externalPlatformQuery 外部平台
     * @return ExternalPlatformVO
     * @author sunx
     * @methodName getOneEnhance
     * @time 2022-12-16 03:10:07
     */
    ExternalPlatformVO getOneEnhance(ExternalPlatformQuery externalPlatformQuery);


    /**
     * TODO 总数
     *
     * @param externalPlatformQuery 外部平台
     * @return Long
     * @author sunx
     * @methodName countEnhance
     * @time 2022-12-16 03:10:07
     */
    Long countEnhance(ExternalPlatformQuery externalPlatformQuery);


    /**
     * TODO 新增
     *
     * @param externalPlatformBO 外部平台
     * @return String
     * @author sunx
     * @methodName saveEnhance
     * @time 2022-12-16 03:10:07
     */
    String saveEnhance(ExternalPlatformBO externalPlatformBO);


    /**
     * TODO 修改
     *
     * @param externalPlatformBO 外部平台
     * @return Boolean
     * @author sunx
     * @methodName updateEnhance
     * @time 2022-12-16 03:10:07
     */
    Boolean updateEnhance(ExternalPlatformBO externalPlatformBO);


    /**
     * TODO 删除
     *
     * @param externalPlatformBO 外部平台
     * @return Boolean
     * @author sunx
     * @methodName removeEnhance
     * @time 2022-12-16 03:10:07
     */
    Boolean removeEnhance(ExternalPlatformBO externalPlatformBO);
}
