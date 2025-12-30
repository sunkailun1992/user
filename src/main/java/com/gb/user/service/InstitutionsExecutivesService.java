package com.gb.user.service;

import com.gb.user.entity.query.InstitutionsExecutivesQuery;
import com.gb.user.entity.vo.InstitutionsExecutivesVO;
import com.gb.user.entity.bo.InstitutionsExecutivesBO;
import com.gb.user.entity.InstitutionsExecutives;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import java.util.List;


/**
 * TODO 机构高管，Service服务接口层
 * 代码生成器
 *
 * @author sunxin
 * @className InstitutionsExecutivesService
 * @time 2022-07-04 10:48:36
 */
public interface InstitutionsExecutivesService extends IService<InstitutionsExecutives> {


    /**
     * TODO 分页
     *
     * @param page
     * @param institutionsExecutivesQuery 机构高管
     * @return Page<InstitutionsExecutivesVO>
     * @author {author}
     * @methodName pageEnhance
     * @time 2022-07-04 10:48:36
     */
    Page<InstitutionsExecutivesVO> pageEnhance(Page page, InstitutionsExecutivesQuery institutionsExecutivesQuery);


    /**
     * TODO 集合
     *
     * @param institutionsExecutivesQuery 机构高管
     * @return List<InstitutionsExecutivesVO>
     * @author sunxin
     * @methodName listEnhance
     * @time 2022-07-04 10:48:36
     */
    List<InstitutionsExecutivesVO> listEnhance(InstitutionsExecutivesQuery institutionsExecutivesQuery);


    /**
     * TODO 单条
     *
     * @param institutionsExecutivesQuery 机构高管
     * @return InstitutionsExecutivesVO
     * @author sunxin
     * @methodName getOneEnhance
     * @time 2022-07-04 10:48:36
     */
    InstitutionsExecutivesVO getOneEnhance(InstitutionsExecutivesQuery institutionsExecutivesQuery);


    /**
     * TODO 总数
     *
     * @param institutionsExecutivesQuery 机构高管
     * @return Long
     * @author sunxin
     * @methodName countEnhance
     * @time 2022-07-04 10:48:36
     */
    Long countEnhance(InstitutionsExecutivesQuery institutionsExecutivesQuery);


    /**
     * TODO 新增
     *
     * @param institutionsExecutivesBO 机构高管
     * @return String
     * @author sunxin
     * @methodName saveEnhance
     * @time 2022-07-04 10:48:36
     */
    String saveEnhance(InstitutionsExecutivesBO institutionsExecutivesBO);


    /**
     * TODO 修改
     *
     * @param institutionsExecutivesBO 机构高管
     * @return Boolean
     * @author sunxin
     * @methodName updateEnhance
     * @time 2022-07-04 10:48:36
     */
    Boolean updateEnhance(InstitutionsExecutivesBO institutionsExecutivesBO);


    /**
     * TODO 删除
     *
     * @param institutionsExecutivesBO 机构高管
     * @return Boolean
     * @author sunxin
     * @methodName removeEnhance
     * @time 2022-07-04 10:48:36
     */
    Boolean removeEnhance(InstitutionsExecutivesBO institutionsExecutivesBO);
}
