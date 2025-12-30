package com.gb.user.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gb.user.entity.Institutions;
import com.gb.user.entity.bo.InstitutionsBO;
import com.gb.user.entity.query.InstitutionsQuery;
import com.gb.user.entity.vo.InstitutionsVO;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;


/**
 * TODO 机构，Service服务接口层
 * 代码生成器
 *
 * @author sunxin
 * @className InstitutionsService
 * @time 2022-07-04 10:48:36
 */
public interface InstitutionsService extends IService<Institutions> {


    /**
     * TODO 分页
     *
     * @param page
     * @param institutionsQuery 机构
     * @return Page<InstitutionsVO>
     * @author {author}
     * @methodName pageEnhance
     * @time 2022-07-04 10:48:36
     */
    Page<InstitutionsVO> pageEnhance(Page page, InstitutionsQuery institutionsQuery);


    /**
     * TODO 集合
     *
     * @param institutionsQuery 机构
     * @return List<InstitutionsVO>
     * @author sunxin
     * @methodName listEnhance
     * @time 2022-07-04 10:48:36
     */
    List<InstitutionsVO> listEnhance(InstitutionsQuery institutionsQuery);


    /**
     * TODO 单条
     *
     * @param institutionsQuery 机构
     * @return InstitutionsVO
     * @author sunxin
     * @methodName getOneEnhance
     * @time 2022-07-04 10:48:36
     */
    InstitutionsVO getOneEnhance(InstitutionsQuery institutionsQuery);


    /**
     * TODO 总数
     *
     * @param institutionsQuery 机构
     * @return Long
     * @author sunxin
     * @methodName countEnhance
     * @time 2022-07-04 10:48:36
     */
    Long countEnhance(InstitutionsQuery institutionsQuery);


    /**
     * TODO 新增
     *
     * @param institutionsBO 机构
     * @return String
     * @author sunxin
     * @methodName saveEnhance
     * @time 2022-07-04 10:48:36
     */
    String saveEnhance(InstitutionsBO institutionsBO);


    /**
     * TODO 修改
     *
     * @param institutionsBO 机构
     * @return Boolean
     * @author sunxin
     * @methodName updateEnhance
     * @time 2022-07-04 10:48:36
     */
    Boolean updateEnhance(InstitutionsBO institutionsBO);


    /**
     * TODO 删除
     *
     * @param institutionsBO 机构
     * @return Boolean
     * @author sunxin
     * @methodName removeEnhance
     * @time 2022-07-04 10:48:36
     */
    Boolean removeEnhance(InstitutionsBO institutionsBO);


    /**
     * TODO 导出Excel:xls格式
     *
     * @param response http输出
     * @param institutionsQuery 机构
     * @author sunxin
     * @methodName removeEnhance
     * @time 2022-07-04 10:48:36
     */
    void exportExcel(HttpServletResponse response, InstitutionsQuery institutionsQuery);

    /**
     * TODO 生成Excel:xls格式
     *
     * @param response http输出
     * @param mapList 待导出数据列表
     * @param fileName 生成文件名称
     * @param sheetName 单元名称
     * @return void
     * @author sunxin
     * @methodName removeEnhance
     * @time 2022-07-04 10:48:36
     */
    void generateExcel(HttpServletResponse response, List<Map<String, String>> mapList, String fileName, String sheetName);
}
