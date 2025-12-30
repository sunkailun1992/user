package com.gb.platform.service;

import com.gb.platform.entity.query.ExternalSystemQuery;
import com.gb.platform.entity.vo.ExternalSystemVO;
import com.gb.platform.entity.bo.ExternalSystemBO;
import com.gb.platform.entity.ExternalSystem;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import java.util.List;


/**
 * TODO 外部系统，Service服务接口层
 * 代码生成器
 *
 * @author sunx
 * @className ExternalSystemService
 * @time 2022-12-16 03:10:08
 */
public interface ExternalSystemService extends IService<ExternalSystem> {


    /**
     * TODO 分页
     *
     * @param page
     * @param externalSystemQuery 外部系统
     * @return Page<ExternalSystemVO>
     * @author sunx
     * @methodName pageEnhance
     * @time 2022-12-16 03:10:08
     */
    Page<ExternalSystemVO> pageEnhance(Page page, ExternalSystemQuery externalSystemQuery);


    /**
     * TODO 集合
     *
     * @param externalSystemQuery 外部系统
     * @return List<ExternalSystemVO>
     * @author sunx
     * @methodName listEnhance
     * @time 2022-12-16 03:10:08
     */
    List<ExternalSystemVO> listEnhance(ExternalSystemQuery externalSystemQuery);


    /**
     * TODO 单条
     *
     * @param externalSystemQuery 外部系统
     * @return ExternalSystemVO
     * @author sunx
     * @methodName getOneEnhance
     * @time 2022-12-16 03:10:08
     */
    ExternalSystemVO getOneEnhance(ExternalSystemQuery externalSystemQuery);


    /**
     * TODO 总数
     *
     * @param externalSystemQuery 外部系统
     * @return Long
     * @author sunx
     * @methodName countEnhance
     * @time 2022-12-16 03:10:08
     */
    Long countEnhance(ExternalSystemQuery externalSystemQuery);


    /**
     * TODO 新增
     *
     * @param externalSystemBO 外部系统
     * @return String
     * @author sunx
     * @methodName saveEnhance
     * @time 2022-12-16 03:10:08
     */
    String saveEnhance(ExternalSystemBO externalSystemBO);


    /**
     * TODO 修改
     *
     * @param externalSystemBO 外部系统
     * @return Boolean
     * @author sunx
     * @methodName updateEnhance
     * @time 2022-12-16 03:10:08
     */
    Boolean updateEnhance(ExternalSystemBO externalSystemBO);


    /**
     * TODO 删除
     *
     * @param externalSystemBO 外部系统
     * @return Boolean
     * @author sunx
     * @methodName removeEnhance
     * @time 2022-12-16 03:10:08
     */
    Boolean removeEnhance(ExternalSystemBO externalSystemBO);
}
