package com.gb.user.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gb.user.entity.InstitutionsUser;
import com.gb.user.entity.bo.InstitutionsUserBO;
import com.gb.user.entity.query.InstitutionsUserQuery;
import com.gb.user.entity.vo.InstitutionsUserVO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;


/**
 * TODO 机构用户关联，Service服务接口层
 * 代码生成器
 *
 * @author sunxin
 * @className InstitutionsUserService
 * @time 2022-07-04 10:48:37
 */
public interface InstitutionsUserService extends IService<InstitutionsUser> {


    /**
     * TODO 分页
     *
     * @param page
     * @param institutionsUserQuery 机构用户关联
     * @return Page<InstitutionsUserVO>
     * @author {author}
     * @methodName pageEnhance
     * @time 2022-07-04 10:48:37
     */
    Page<InstitutionsUserVO> pageEnhance(Page page, InstitutionsUserQuery institutionsUserQuery);


    /**
     * TODO 集合
     *
     * @param institutionsUserQuery 机构用户关联
     * @return List<InstitutionsUserVO>
     * @author sunxin
     * @methodName listEnhance
     * @time 2022-07-04 10:48:37
     */
    List<InstitutionsUserVO> listEnhance(InstitutionsUserQuery institutionsUserQuery);


    /**
     * TODO 单条
     *
     * @param institutionsUserQuery 机构用户关联
     * @return InstitutionsUserVO
     * @author sunxin
     * @methodName getOneEnhance
     * @time 2022-07-04 10:48:37
     */
    InstitutionsUserVO getOneEnhance(InstitutionsUserQuery institutionsUserQuery);


    /**
     * TODO 总数
     *
     * @param institutionsUserQuery 机构用户关联
     * @return Long
     * @author sunxin
     * @methodName countEnhance
     * @time 2022-07-04 10:48:37
     */
    Long countEnhance(InstitutionsUserQuery institutionsUserQuery);


    /**
     * TODO 新增
     *
     * @param httpServletRequest http请求
     * @param institutionsUserBO 机构用户关联
     * @return String
     * @author sunxin
     * @methodName saveEnhance
     * @time 2022-07-04 10:48:37
     */
    String saveEnhance(HttpServletRequest httpServletRequest, InstitutionsUserBO institutionsUserBO);


    /**
     * TODO 修改
     *
     * @param httpServletRequest http请求
     * @param institutionsUserBO 机构用户关联
     * @return Boolean
     * @author sunxin
     * @methodName updateEnhance
     * @time 2022-07-04 10:48:37
     */
    Boolean updateEnhance(HttpServletRequest httpServletRequest, InstitutionsUserBO institutionsUserBO);


    /**
     * TODO 删除
     *
     * @param institutionsUserBO 机构用户关联
     * @return Boolean
     * @author sunxin
     * @methodName removeEnhance
     * @time 2022-07-04 10:48:37
     */
    Boolean removeEnhance(InstitutionsUserBO institutionsUserBO);


    /**
     * TODO 导出Excel:xls格式
     *
     * @param response http输出
     * @param institutionsUserQuery 机构用户关联
     * @author sunxin
     * @methodName removeEnhance
     * @time 2022-07-04 10:48:36
     */
    void exportExcel(HttpServletResponse response, InstitutionsUserQuery institutionsUserQuery);
}
