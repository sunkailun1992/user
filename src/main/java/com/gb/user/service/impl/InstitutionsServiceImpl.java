package com.gb.user.service.impl;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.map.MapUtil;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gb.rpc.component.RpcComponent;
import com.gb.user.entity.Institutions;
import com.gb.user.entity.bo.InstitutionsBO;
import com.gb.user.entity.bo.InstitutionsExecutivesBO;
import com.gb.user.entity.query.InstitutionsQuery;
import com.gb.user.entity.query.InstitutionsUserQuery;
import com.gb.user.entity.vo.InstitutionsVO;
import com.gb.user.mapper.InstitutionsMapper;
import com.gb.user.service.InstitutionsExecutivesService;
import com.gb.user.service.InstitutionsService;
import com.gb.user.service.InstitutionsUserService;
import com.gb.user.service.query.InstitutionsServiceQuery;
import com.gb.user.service.results.InstitutionsServiceResults;
import com.gb.utils.GeneralConvertor;
import com.gb.utils.constants.UniversalConstant;
import com.gb.utils.exception.BusinessException;
import com.gb.utils.exception.ParameterNullException;
import com.gb.utils.exception.PreventRepeatException;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.compress.utils.Lists;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.util.SheetUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.gb.rpc.enums.RpcTypeEnum.*;


/**
 * TODO 机构，Service服务实现层
 * 代码生成器
 *
 * @author sunxin
 * @className InstitutionsServiceImpl
 * @time 2022-07-04 10:48:36
 */
@Slf4j
@Service
@Setter(onMethod_ = {@Autowired})
public class InstitutionsServiceImpl extends ServiceImpl<InstitutionsMapper, Institutions> implements InstitutionsService {


    /**
     * 机构
     */
    private InstitutionsMapper institutionsMapper;


    /**
     * 机构
     */
    private InstitutionsServiceResults institutionsServiceResults;


    /**
     * 机构增强条件
     */
    private InstitutionsServiceQuery institutionsServiceQuery;

    private InstitutionsUserService institutionsUserService;

    private InstitutionsExecutivesService institutionsExecutivesService;

    private RpcComponent rpcComponent;


    /**
     * TODO 集合
     *
     * @param institutionsQuery 机构
     * @return List<InstitutionsVO>
     * @author sunxin
     * @methodName listEnhance
     * @time 2022-07-04 10:48:36
     */
    @Override
    public List<InstitutionsVO> listEnhance(InstitutionsQuery institutionsQuery) {
        Institutions institutions = GeneralConvertor.convertor(institutionsQuery, Institutions.class);
        QueryWrapper<Institutions> queryWrapper = new QueryWrapper<>(institutions);
        // TODO 自动生成查询，禁止手动写语句
        institutionsServiceQuery.query(institutionsQuery, queryWrapper);
        // TODO 人工查询条件
        queryArtificial(institutionsQuery, queryWrapper);
        // DO数据
        List<Institutions> institutionsDO = institutionsMapper.selectList(queryWrapper);
        // VO数据
        List<InstitutionsVO> institutionsVO = GeneralConvertor.convertor(institutionsDO, InstitutionsVO.class);
        return institutionsServiceResults.assignment(institutionsVO);
    }


    /**
     * TODO 分页
     *
     * @param page
     * @param institutionsQuery 机构
     * @return Page<InstitutionsVO>
     * @author sunxin
     * @methodName pageEnhance
     * @time 2022-07-04 10:48:36
     */
    @Override
    public Page<InstitutionsVO> pageEnhance(Page page, InstitutionsQuery institutionsQuery) {
        Institutions institutions = GeneralConvertor.convertor(institutionsQuery, Institutions.class);
        QueryWrapper<Institutions> queryWrapper = new QueryWrapper<>(institutions);
        // TODO 自动生成查询，禁止手动写语句
        institutionsServiceQuery.query(institutionsQuery, queryWrapper);
        // TODO 人工查询条件
        queryArtificial(institutionsQuery, queryWrapper);
        // DO数据
        Page<Institutions> pageDO = institutionsMapper.selectPage(page, queryWrapper);
        // VO数据
        Page<InstitutionsVO> pageVO = institutionsServiceResults.toPageVO(pageDO);
        return institutionsServiceResults.assignment(pageVO);
    }


    /**
     * TODO 单条
     *
     * @param institutionsQuery 机构
     * @return InstitutionsVO
     * @author sunxin
     * @methodName getOneEnhance
     * @time 2022-07-04 10:48:36
     */
    @Override
    public InstitutionsVO getOneEnhance(InstitutionsQuery institutionsQuery) {
        Institutions institutions = GeneralConvertor.convertor(institutionsQuery, Institutions.class);
        QueryWrapper<Institutions> queryWrapper = new QueryWrapper<>(institutions);
        // TODO 自动生成查询，禁止手动写语句
        institutionsServiceQuery.query(institutionsQuery, queryWrapper);
        // TODO 人工查询条件
        queryArtificial(institutionsQuery, queryWrapper);
        // DO数据
        Institutions institutionsDO = institutionsMapper.selectOne(queryWrapper);
        // VO数据
        InstitutionsVO institutionsVO = GeneralConvertor.convertor(institutionsDO, InstitutionsVO.class);
        return institutionsServiceResults.assignment(institutionsVO);
    }


    /**
     * TODO 总数
     *
     * @param institutionsQuery 机构
     * @return Integer
     * @author sunxin
     * @methodName countEnhance
     * @time 2022-07-04 10:48:36
     */
    @Override
    public Long countEnhance(InstitutionsQuery institutionsQuery) {
        Institutions institutions = GeneralConvertor.convertor(institutionsQuery, Institutions.class);
        QueryWrapper<Institutions> queryWrapper = new QueryWrapper<>(institutions);
        // TODO 自动生成查询，禁止手动写语句
        institutionsServiceQuery.query(institutionsQuery, queryWrapper);
        // TODO 人工查询条件
        queryArtificial(institutionsQuery, queryWrapper);
        return institutionsMapper.selectCount(queryWrapper);
    }


    /**
     * TODO 新增
     *
     * @param institutionsBO 机构
     * @return String
     * @author sunxin
     * @methodName saveEnhance
     * @time 2022-07-04 10:48:36
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, timeout = 36000, rollbackFor = Exception.class)
    public String saveEnhance(InstitutionsBO institutionsBO) {
        //1、机构地区不能完全为空
        if(StringUtils.isBlank(institutionsBO.getProvinceCode()) && StringUtils.isBlank(institutionsBO.getCityCode()) && StringUtils.isBlank(institutionsBO.getAreaCode())) {
            throw new ParameterNullException("机构地区不能为空！");
        }
        //2、校验机构名称是否已经存在
        if(countEnhance(new InstitutionsQuery() {{
            setName(institutionsBO.getName());
        }}) > 0) {
            throw new PreventRepeatException("机构名称已存在！");
        }
        //3、生成机构编码、校验
        String code = generateCode(institutionsBO);
        if(countEnhance(new InstitutionsQuery(){{
            setCode(code);
        }}) > 0) {
            throw new PreventRepeatException("您选择地区已存在机构，请确认后再新增！");
        }
        institutionsBO.setCode(code);
        //4、插入机构数据到数据库
        Institutions institutions = GeneralConvertor.convertor(institutionsBO, Institutions.class);
        institutionsMapper.insert(institutions);
        return institutions.getId();
    }


    /**
     * TODO 修改
     *
     * @param institutionsBO 机构
     * @return Boolean
     * @author sunxin
     * @methodName updateEnhance
     * @time 2022-07-04 10:48:36
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, timeout = 36000, rollbackFor = Exception.class)
    public Boolean updateEnhance(InstitutionsBO institutionsBO) {
        //1、机构名称、上级机构校验
        if(StringUtils.isNotBlank(institutionsBO.getName())) {
            List<InstitutionsVO> institutionsVOList = listEnhance(new InstitutionsQuery() {{
                setName(institutionsBO.getName());
            }});
            boolean validateFlag = institutionsVOList.size() > 1 || (CollectionUtils.isNotEmpty(institutionsVOList) && !StringUtils.equals(institutionsVOList.get(0).getId(), institutionsBO.getId()));
            if(validateFlag) {
                throw new ParameterNullException("机构名称已存在！");
            }
        }
        //2、生成机构编码、校验
        String code = generateCode(institutionsBO);
        if(!StringUtils.equals(code, institutionsBO.getCode())) {
            List<InstitutionsVO> institutionsVOList = listEnhance(new InstitutionsQuery() {{
                setCode(code);
            }});
            boolean validateFlag = institutionsVOList.size() > 1 || (CollectionUtils.isNotEmpty(institutionsVOList) && !StringUtils.equals(institutionsVOList.get(0).getId(), institutionsBO.getId()));
            if(validateFlag) {
                throw new PreventRepeatException("您选择地区已存在机构，请确认后再进行修改！");
            }
            institutionsBO.setCode(code);
        }
        //3、更新机构数据到数据库
        Institutions institutions = GeneralConvertor.convertor(institutionsBO, Institutions.class);
        UpdateWrapper<Institutions> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id", institutionsBO.getId());
        if(institutionsMapper.update(institutions, updateWrapper) < 0 ) {
            throw new BusinessException("机构详情编辑失败！");
        }
        if(CollectionUtils.isEmpty(institutionsBO.getInstitutionsExecutivesBOList())) {
            return true;
        }
        //4、校验请求的高管信息是否存在重复的数据【高管姓名+岗位】
        Map<String, Long>  repeatMap = institutionsBO.getInstitutionsExecutivesBOList().stream().collect(Collectors.groupingBy(u->u.getName()+"-"+u.getJobs(), Collectors.counting()));
        boolean isRepeatFlag = repeatMap.keySet().stream().anyMatch(key -> repeatMap.get(key) > 1 || StringUtils.endsWith(key, "-") || StringUtils.startsWith(key, "-"));
        if(isRepeatFlag) {
            throw new PreventRepeatException("高管信息存在重复或高管姓名、岗位为空数据！");
        }
        //5、删除该机构下所有的高管，然后讲请求的高管信息全部插入到数据库中
        institutionsExecutivesService.removeEnhance(new InstitutionsExecutivesBO(){{setInstitutionsId(institutionsBO.getId());}});
        for(InstitutionsExecutivesBO bo : institutionsBO.getInstitutionsExecutivesBOList()) {
            institutionsExecutivesService.saveEnhance(new InstitutionsExecutivesBO() {{
                setInstitutionsId(institutionsBO.getId());
                setCreateName(institutionsBO.getCreateName());
                setName(bo.getName());
                setJobs(bo.getJobs());
            }});
        }
        return true;
    }


    /**
     * TODO 删除
     *
     * @param institutionsBO 机构
     * @return Boolean
     * @author sunxin
     * @methodName removeEnhance
     * @time 2022-07-04 10:48:36
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, timeout = 36000, rollbackFor = Exception.class)
    public Boolean removeEnhance(InstitutionsBO institutionsBO) {
        if(countEnhance(new InstitutionsQuery(){{
            setSuperiorId(institutionsBO.getId());
        }}) > 0) {
            throw new BusinessException("存在分支机构！");
        }
        if(institutionsUserService.countEnhance(new InstitutionsUserQuery() {{
            setInstitutionsId(institutionsBO.getId());
        }}) > 0) {
            throw new BusinessException("存在经纪人！");
        }
        Institutions institutions = GeneralConvertor.convertor(institutionsBO, Institutions.class);
        QueryWrapper<Institutions> queryWrapper = new QueryWrapper<>(institutions);
        Integer i = institutionsMapper.delete(queryWrapper);
        return i > 0 ? true : false;
    }

    @Override
    public void exportExcel(HttpServletResponse response, InstitutionsQuery institutionsQuery) {
       List<InstitutionsVO> institutionsVOList = listEnhance(institutionsQuery);
       if(CollectionUtils.isEmpty(institutionsVOList)) {
           throw new BusinessException("无可供导出的数据！");
       }
       List<Map<String, String>> mapList = Lists.newArrayList();
       for (InstitutionsVO vo: institutionsVOList) {
           Map<String, String> map = MapUtil.newHashMap(true);
           map.put("机构名称", StringUtils.defaultString(vo.getName()));
           String type = Objects.isNull(vo.getType()) ? StringUtils.EMPTY : StringUtils.defaultString(vo.getType().getDesc());
           map.put("机构类别", type);
           String state = Objects.isNull(vo.getState()) ? StringUtils.EMPTY : StringUtils.defaultString(vo.getState().getDesc());
           map.put("机构状态", state);
           map.put("机构编码", StringUtils.defaultString(vo.getCode()));
           String area = (StringUtils.defaultString(vo.getProvinceName()) + StringUtils.defaultString(vo.getCityName()) + StringUtils.defaultString(vo.getAreaName()));
           map.put("所在地区", area);
           String startDate = Objects.isNull(vo.getStartDate()) ? StringUtils.EMPTY : vo.getStartDate().format(DateTimeFormatter.ofPattern(DatePattern.NORM_DATE_PATTERN));
           map.put("成立日期", startDate);
           map.put("办公地址", StringUtils.defaultString(vo.getAddress()));
           mapList.add(map);
       }
       String tempFileName = "工保机构明细.xls";
       generateExcel(response, mapList, tempFileName, tempFileName);
    }

    @Override
    public void generateExcel(HttpServletResponse response, List<Map<String, String>> mapList, String fileName, String sheetName) {
        try{
            response.reset();
            response.setCharacterEncoding("UTF-8");
            response.setHeader("Content-Disposition", "attachment;fileName=" + fileName);
            response.setContentType("application/octet-stream;charset=utf-8");
            OutputStream os = response.getOutputStream();
            ExcelWriter writer =  ExcelUtil.getBigWriter();
            writer.renameSheet(sheetName);
            writer.write(mapList);
            for (int i = 0; i < writer.getColumnCount(); ++i) {
                double width = SheetUtil.getColumnWidth(writer.getSheet(), i, false);
                if (Double.compare(width, -1.0D) != 0 ) {
                    width *= 600.0D;
                    writer.setColumnWidth(i, Math.toIntExact(Math.round(width / 256D)));
                }
            }
            writer.flush(os, true);
            writer.close();
            os.flush();
            os.close();
        }catch (IOException e) {
            throw new BusinessException("导出异常！");
        }
    }

    /**
     * TODO 人工查询条件
     *
     * @param institutionsQuery 机构
     * @return QueryWrapper
     * @author sunxin
     * @methodName queryArtificial
     * @time 2022-07-04 10:48:36
     */
    private QueryWrapper queryArtificial(InstitutionsQuery institutionsQuery, QueryWrapper<Institutions> queryWrapper) {
        //1、机构名称全模糊查询
        if(StringUtils.isNotBlank(institutionsQuery.getNameQuery())) {
            queryWrapper.like(UniversalConstant.NAME, institutionsQuery.getNameQuery());
        }
        //2、机构编码全模糊查询
        if(StringUtils.isNotBlank(institutionsQuery.getCodeQuery())) {
            queryWrapper.like("code", institutionsQuery.getCodeQuery());
        }
        return queryWrapper;
    }

    /**
     * 产生机构编码
     *
     * @param institutionsBO 机构请求参数信息
     * @return void
     * @author sunxin
     * @methodName generateCode
     * @time 2022-07-04 10:48:36
     */
    private String generateCode(InstitutionsBO institutionsBO) {
        //1、校验省、市、区域码值
        String code = institutionsBO.getCode();
        if(StringUtils.isNotBlank(institutionsBO.getAreaCode())) {
            List<Map<String, Object>> objList  = rpcComponent.rpcQuery(institutionsBO.getAreaCode(), AREA_GET, List.class);
            if(CollectionUtils.isEmpty(objList)) {
                throw new BusinessException("地区未找到！");
            }
            Map<String, Object> objMap = objList.get(0);
            String provinceCode = Convert.toStr(objMap.get("provinceCode"));
            String cityCode = Convert.toStr(objMap.get("cityCode"));
            if(StringUtils.isBlank(cityCode) || StringUtils.isBlank(provinceCode)) {
                throw new BusinessException("区域数据未找到！");
            }
            if(StringUtils.isNotBlank(institutionsBO.getProvinceCode()) && !StringUtils.equals(institutionsBO.getProvinceCode(), provinceCode)) {
                throw new BusinessException("地区传参与配置不一致！");
            }
            if(StringUtils.isNotBlank(institutionsBO.getCityCode()) && !StringUtils.equals(institutionsBO.getCityCode(), cityCode)) {
                throw new BusinessException("地区传参与配置不一致！");
            }
            institutionsBO.setProvinceCode(provinceCode);
            institutionsBO.setProvinceName(Convert.toStr(objMap.get("provinceName")));
            institutionsBO.setCityCode(cityCode);
            institutionsBO.setCityName(Convert.toStr(objMap.get("cityName")));
            institutionsBO.setAreaName(Convert.toStr(objMap.get("areaName")));
            code = "GBW" + institutionsBO.getAreaCode().substring(0, 6);
        } else if (StringUtils.isNotBlank(institutionsBO.getCityCode())) {
            Map<String, Object> objMap = rpcComponent.rpcQuery(new HashMap<String, String>(1){{put("cityCode", institutionsBO.getCityCode());}}, CITY_GET, Map.class);
            if(MapUtils.isEmpty(objMap)) {
                throw new BusinessException("区域数据未找到！");
            }
            String provinceCode = Convert.toStr(objMap.get("provinceCode"));
            if(StringUtils.isBlank(provinceCode)) {
                throw new BusinessException("缺少对应省配置！");
            } else if(!StringUtils.equals(institutionsBO.getProvinceCode(), provinceCode)) {
                throw new BusinessException("地区传参与配置不一致！");
            }
            institutionsBO.setProvinceCode(provinceCode);
            institutionsBO.setProvinceName(Convert.toStr(objMap.get("provinceName")));
            institutionsBO.setCityName(Convert.toStr(objMap.get("cityName")));
            code = "GBW" + institutionsBO.getCityCode().substring(0, 6);
        } else if(StringUtils.isNotBlank(institutionsBO.getProvinceCode())) {
            Map<String, Object> objMap =  rpcComponent.rpcQuery(new HashMap<String, String>(1){{put("provinceCode", institutionsBO.getProvinceCode());}}, PROVINCE_GET, Map.class);
            if(MapUtils.isEmpty(objMap)) {
                throw new BusinessException("缺少对应省配置！");
            }
            institutionsBO.setProvinceName(Convert.toStr(objMap.get(UniversalConstant.NAME)));
            code = "GBW" + institutionsBO.getProvinceCode().substring(0, 6);
        }
        return code;
    }
}