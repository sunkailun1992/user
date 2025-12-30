package com.gb.platform.service;

import com.gb.platform.entity.query.TransformationExternalPlatformSystemQuery;
import com.gb.platform.entity.vo.TransformationExternalPlatformSystemVO;
import com.gb.platform.entity.bo.TransformationExternalPlatformSystemBO;
import com.gb.platform.entity.TransformationExternalPlatformSystem;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import java.util.List;


/**
 * TODO 转化外部系统平台，Service服务接口层
 * 代码生成器
 *
 * @author sunx
 * @className TransformationExternalPlatformSystemService
 * @time 2022-12-16 03:10:08
 */
public interface TransformationExternalPlatformSystemService extends IService<TransformationExternalPlatformSystem> {


    /**
     * TODO 分页
     *
     * @param page
     * @param transformationExternalPlatformSystemQuery 转化外部系统平台
     * @return Page<TransformationExternalPlatformSystemVO>
     * @author sunx
     * @methodName pageEnhance
     * @time 2022-12-16 03:10:08
     */
    Page<TransformationExternalPlatformSystemVO> pageEnhance(Page page, TransformationExternalPlatformSystemQuery transformationExternalPlatformSystemQuery);


    /**
     * TODO 集合
     *
     * @param transformationExternalPlatformSystemQuery 转化外部系统平台
     * @return List<TransformationExternalPlatformSystemVO>
     * @author sunx
     * @methodName listEnhance
     * @time 2022-12-16 03:10:08
     */
    List<TransformationExternalPlatformSystemVO> listEnhance(TransformationExternalPlatformSystemQuery transformationExternalPlatformSystemQuery);


    /**
     * TODO 单条
     *
     * @param transformationExternalPlatformSystemQuery 转化外部系统平台
     * @return TransformationExternalPlatformSystemVO
     * @author sunx
     * @methodName getOneEnhance
     * @time 2022-12-16 03:10:08
     */
    TransformationExternalPlatformSystemVO getOneEnhance(TransformationExternalPlatformSystemQuery transformationExternalPlatformSystemQuery);


    /**
     * TODO 总数
     *
     * @param transformationExternalPlatformSystemQuery 转化外部系统平台
     * @return Long
     * @author sunx
     * @methodName countEnhance
     * @time 2022-12-16 03:10:08
     */
    Long countEnhance(TransformationExternalPlatformSystemQuery transformationExternalPlatformSystemQuery);


    /**
     * TODO 新增
     *
     * @param transformationExternalPlatformSystemBO 转化外部系统平台
     * @return String
     * @author sunx
     * @methodName saveEnhance
     * @time 2022-12-16 03:10:08
     */
    String saveEnhance(TransformationExternalPlatformSystemBO transformationExternalPlatformSystemBO);


    /**
     * TODO 修改
     *
     * @param transformationExternalPlatformSystemBO 转化外部系统平台
     * @return Boolean
     * @author sunx
     * @methodName updateEnhance
     * @time 2022-12-16 03:10:08
     */
    Boolean updateEnhance(TransformationExternalPlatformSystemBO transformationExternalPlatformSystemBO);


    /**
     * TODO 删除
     *
     * @param transformationExternalPlatformSystemBO 转化外部系统平台
     * @return Boolean
     * @author sunx
     * @methodName removeEnhance
     * @time 2022-12-16 03:10:08
     */
    Boolean removeEnhance(TransformationExternalPlatformSystemBO transformationExternalPlatformSystemBO);
}
