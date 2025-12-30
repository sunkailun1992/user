package com.gb.platform.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gb.platform.entity.TransformationExternalPlatformSystemUser;
import com.gb.platform.entity.bo.BatchPlatformSystemUserBO;
import com.gb.platform.entity.bo.TransformationExternalPlatformSystemUserBO;
import com.gb.platform.entity.query.TransformationExternalPlatformSystemUserQuery;
import com.gb.platform.entity.vo.TransformationExternalPlatformSystemUserVO;

import java.util.List;


/**
 * TODO 转化外部系统平台用户关联，Service服务接口层
 * 代码生成器
 *
 * @author sunx
 * @className TransformationExternalPlatformSystemUserService
 * @time 2022-12-16 03:10:09
 */
public interface TransformationExternalPlatformSystemUserService extends IService<TransformationExternalPlatformSystemUser> {


    /**
     * TODO 分页
     *
     * @param page
     * @param transformationExternalPlatformSystemUserQuery 转化外部系统平台用户关联
     * @return Page<TransformationExternalPlatformSystemUserVO>
     * @author sunx
     * @methodName pageEnhance
     * @time 2022-12-16 03:10:09
     */
    Page<TransformationExternalPlatformSystemUserVO> pageEnhance(Page page, TransformationExternalPlatformSystemUserQuery transformationExternalPlatformSystemUserQuery);


    /**
     * TODO 集合
     *
     * @param transformationExternalPlatformSystemUserQuery 转化外部系统平台用户关联
     * @return List<TransformationExternalPlatformSystemUserVO>
     * @author sunx
     * @methodName listEnhance
     * @time 2022-12-16 03:10:09
     */
    List<TransformationExternalPlatformSystemUserVO> listEnhance(TransformationExternalPlatformSystemUserQuery transformationExternalPlatformSystemUserQuery);


    /**
     * TODO 单条
     *
     * @param transformationExternalPlatformSystemUserQuery 转化外部系统平台用户关联
     * @return TransformationExternalPlatformSystemUserVO
     * @author sunx
     * @methodName getOneEnhance
     * @time 2022-12-16 03:10:09
     */
    TransformationExternalPlatformSystemUserVO getOneEnhance(TransformationExternalPlatformSystemUserQuery transformationExternalPlatformSystemUserQuery);


    /**
     * TODO 总数
     *
     * @param transformationExternalPlatformSystemUserQuery 转化外部系统平台用户关联
     * @return Long
     * @author sunx
     * @methodName countEnhance
     * @time 2022-12-16 03:10:09
     */
    Long countEnhance(TransformationExternalPlatformSystemUserQuery transformationExternalPlatformSystemUserQuery);


    /**
     * TODO 新增
     *
     * @param transformationExternalPlatformSystemUserBO 转化外部系统平台用户关联
     * @return String
     * @author sunx
     * @methodName saveEnhance
     * @time 2022-12-16 03:10:09
     */
    String saveEnhance(TransformationExternalPlatformSystemUserBO transformationExternalPlatformSystemUserBO);


    /**
     * TODO 批量外部系统用户新增
     *
     * @param bo
     * @return void
     * @author 孙凯伦
     * @methodName saveBatchPlateform
     * @time 2023/9/14 11:36
     */
    void saveBatchPlateform(BatchPlatformSystemUserBO bo);


    /**
     * TODO 修改
     *
     * @param transformationExternalPlatformSystemUserBO 转化外部系统平台用户关联
     * @return Boolean
     * @author sunx
     * @methodName updateEnhance
     * @time 2022-12-16 03:10:09
     */
    Boolean updateEnhance(TransformationExternalPlatformSystemUserBO transformationExternalPlatformSystemUserBO);


    /**
     * TODO 修改
     *
     * @param transformationExternalPlatformSystemUserBO 转化外部系统平台用户关联
     * @return Boolean
     * @author sunx
     * @methodName updateEnhance
     * @time 2022-12-16 03:10:09
     */
    Boolean updateUserIdEnhance(TransformationExternalPlatformSystemUserBO transformationExternalPlatformSystemUserBO);


    /**
     * 批量外部系统用户更新
     * @param bo
     * @author sunx
     * @methodName updateBatchPlateform
     * @time 2022-12-16 03:10:09
     */
    void updateBatchPlateform(BatchPlatformSystemUserBO bo);


    /**
     * TODO 删除
     *
     * @param transformationExternalPlatformSystemUserBO 转化外部系统平台用户关联
     * @return Boolean
     * @author sunx
     * @methodName removeEnhance
     * @time 2022-12-16 03:10:09
     */
    Boolean removeEnhance(TransformationExternalPlatformSystemUserBO transformationExternalPlatformSystemUserBO);
}
