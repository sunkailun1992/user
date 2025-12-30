package com.gb.quotation.service.impl;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.map.MapUtil;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.CannedAccessControlList;
import com.aliyun.oss.model.OSSObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gb.aliyun.AliyunKey;
import com.gb.aliyun.Oss;
import com.gb.quotation.entity.QuotationScheme;
import com.gb.quotation.entity.QuotationSchemeOther;
import com.gb.quotation.entity.QuotationSchemeSpu;
import com.gb.quotation.mapper.QuotationSchemeMapper;
import com.gb.quotation.service.QuotationSchemeOtherService;
import com.gb.quotation.service.QuotationSchemeService;
import com.gb.quotation.service.QuotationSchemeSpuService;
import com.gb.rpc.ProductRpc;
import com.gb.user.entity.UserAgentCertification;
import com.gb.user.service.UserAgentCertificationService;
import com.gb.utils.PdfUtils;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileInputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.*;

/**
 * <p>
 * 报价方案 服务实现类
 * </p>
 *
 * @author 尹涛涛
 * @since 2021-05-22
 */
@Slf4j
@Service
@Setter(onMethod_ = {@Autowired})
public class QuotationSchemeServiceImpl extends ServiceImpl<QuotationSchemeMapper, QuotationScheme> implements QuotationSchemeService {
    /**
     * 报价方案dao
     */
    private QuotationSchemeMapper quotationSchemeMapper;

    /**
     * 报价方案产品
     */
    private QuotationSchemeSpuService quotationSchemeSpuService;

    /**
     * 其他方案推荐
     */
    private QuotationSchemeOtherService quotationSchemeOtherService;

    /**
     * 用户经纪人
     */
    private UserAgentCertificationService userAgentCertificationService;

    /**
     * 产品rpc
     */
    private ProductRpc productRpc;


    /**
     * 集合条件查询
     *
     * @param quotationScheme:
     * @return java.util.List<com.entity.QuotationScheme>
     * @author 尹涛涛
     * @since 2021-05-22
     */
    @Override
    public List<QuotationScheme> listEnhance(QuotationScheme quotationScheme) {
        QueryWrapper<QuotationScheme> queryWrapper = new QueryWrapper<>(quotationScheme);
        query(quotationScheme, queryWrapper);
        return assignment(quotationSchemeMapper.selectList(queryWrapper));
    }


    /**
     * 分页条件查询
     *
     * @param quotationScheme:
     * @param page:
     * @return java.util.List<com.entity.QuotationScheme>
     * @author 尹涛涛
     * @since 2021-05-22
     */
    @Override
    public IPage pageEnhance(Page page, QuotationScheme quotationScheme) {
        QueryWrapper<QuotationScheme> queryWrapper = new QueryWrapper<>(quotationScheme);
        query(quotationScheme, queryWrapper);
        return assignment(quotationSchemeMapper.selectPage(page, queryWrapper));
    }


    /**
     * 单条条件查询
     *
     * @param quotationScheme:
     * @return java.util.List<com.entity.QuotationScheme>
     * @author 尹涛涛
     * @since 2021-05-22
     */
    @Override
    public QuotationScheme getOneEnhance(QuotationScheme quotationScheme) {
        QueryWrapper<QuotationScheme> queryWrapper = new QueryWrapper<>(quotationScheme);
        query(quotationScheme, queryWrapper);
        return assignment(quotationSchemeMapper.selectOne(queryWrapper));
    }


    /**
     * 总数
     *
     * @param quotationScheme:
     * @return java.util.List<com.entity.QuotationScheme>
     * @author 尹涛涛
     * @since 2021-05-22
     */
    @Override
    public Long countEnhance(QuotationScheme quotationScheme) {
        QueryWrapper<QuotationScheme> queryWrapper = new QueryWrapper<>(quotationScheme);
        query(quotationScheme, queryWrapper);
        return quotationSchemeMapper.selectCount(queryWrapper);
    }


    /**
     * 新增一个报价单
     *
     * @param quotationScheme:
     * @return java.util.List<com.entity.QuotationScheme>
     * @author 尹涛涛
     * @since 2021-05-22
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, timeout = 36000, rollbackFor = Exception.class)
    public Boolean saveEnhance(QuotationScheme quotationScheme) throws Exception {
        //打印日志
        log.debug("进入了保存报价单的service方法");
        //定义pdf模板的填充数据map
        Map<String, String> pdfDataMap = new HashMap<>(10);
        //咨询人名称
        pdfDataMap.put("name", quotationScheme.getName());
        //投保险种
        pdfDataMap.put("dangerPlantedName", quotationScheme.getDangerPlantedName());
        //保证金
        pdfDataMap.put("schemaMargin", Convert.toStr(quotationScheme.getProjectMargin()));
        //企业资质
        pdfDataMap.put("enterpriseQualification", Convert.toStr(quotationScheme.getEnterpriseQualification()));
        //项目工期
        pdfDataMap.put("projectTimeLimit", Convert.toStr(quotationScheme.getProjectTimeLimit()));
        //项目总投资
        pdfDataMap.put("projectInvestment", Convert.toStr(quotationScheme.getProjectInvestment()));
        //首先插入报价单获取主键id
        Integer flag = quotationSchemeMapper.insert(quotationScheme);
        //获取主键id
        String quotationSchemeId = quotationScheme.getId();
        //获取产品报价集合
        List<QuotationSchemeSpu> quotationSchemeSpuList = quotationScheme.getQuotationSchemeSpuList();
        //定义批量spu集合
        List<QuotationSchemeSpu> batchSchemeSpuList = new ArrayList<>();
        //遍历产品报价
        if (quotationSchemeSpuList != null && quotationSchemeSpuList.size() > 0) {
            //开始遍历
            for (int i = 0; i < quotationSchemeSpuList.size(); i++) {
                //拿到一个元素
                QuotationSchemeSpu quotationSchemeSpu = quotationSchemeSpuList.get(i);
                //设置关联的报价方案id
                quotationSchemeSpu.setQuotationSchemeId(quotationSchemeId);
                //添加到集合
                batchSchemeSpuList.add(quotationSchemeSpu);
                //保险企业名称
                pdfDataMap.put("company_" + i, quotationSchemeSpu.getInsuranceEnterpriseName());
                //获取保险费率
                BigDecimal fixedRate = quotationSchemeSpu.getFixedRate();
                //保险费率
                pdfDataMap.put("rate_" + i, fixedRate == null ? "" : fixedRate.multiply(new BigDecimal(100)) + "%");
                //保障期限
                String insuranceTimeLimit = Convert.toStr(quotationSchemeSpu.getInsuranceTimeLimit(), "");
                //保障期限
                pdfDataMap.put("spuLimit_" + i, "".equals(insuranceTimeLimit) ? "" : insuranceTimeLimit);
                //保费
                String payMoney = Convert.toStr(quotationSchemeSpu.getPayMoney(), "");
                //保费
                pdfDataMap.put("spuPremium_" + i, "".equals(payMoney) ? "" : payMoney);
            }
        }
        //获取其他推荐集合
        List<QuotationSchemeOther> quotationSchemeOtherList = quotationScheme.getQuotationSchemeOtherList();
        //定义批量集合
        List<QuotationSchemeOther> batchSchemeOtherList = new ArrayList<>();
        //非空判断
        if (quotationSchemeOtherList != null && quotationSchemeOtherList.size() > 0) {
            //遍历
            for (int i = 0; i < quotationSchemeOtherList.size(); i++) {
                //获取第一个
                QuotationSchemeOther quotationSchemeOther = quotationSchemeOtherList.get(i);
                //设置关联的报价方案id
                quotationSchemeOther.setQuotationSchemeId(quotationSchemeId);
                //插入数据库
                batchSchemeOtherList.add(quotationSchemeOther);
                //保险类型
                pdfDataMap.put("dangerPlantedName_" + i, quotationSchemeOther.getDangerPlantedName());
                //保证金金额
                String marginAmount = Convert.toStr(quotationSchemeOther.getMarginAmount(), "");
                //保证金金额
                pdfDataMap.put("otherMargin_" + i, "".equals(marginAmount) ? "" : marginAmount);
                //保障期限
                String insuranceTimeLimit = Convert.toStr(quotationSchemeOther.getInsuranceTimeLimit(), "");
                //保证期限
                pdfDataMap.put("otherLimit_" + i, "".equals(insuranceTimeLimit) ? "" : insuranceTimeLimit);
                //保费
                String payMoney = Convert.toStr(quotationSchemeOther.getPayMoney(), "");
                //保费
                pdfDataMap.put("otherPremium_" + i, "".equals(payMoney) ? "" : payMoney);
            }
        }
        //设置userId
        UserAgentCertification userAgentCertification = new UserAgentCertification();
        //设置userId
        userAgentCertification.setUserId(quotationScheme.getUserId());
        //查询
        try {
            userAgentCertification = userAgentCertificationService.getOneEnhance(userAgentCertification);
        } catch (Exception e) {
            //打印日志
            log.error("获取经纪人信息异常:", e);
            //抛出异常
            throw new RuntimeException("获取经纪人信息异常");
        }
        //非空判断
        if (userAgentCertification != null) {
            //经纪人姓名
            pdfDataMap.put("agent", userAgentCertification.getName());
            //工号
            pdfDataMap.put("workNo", userAgentCertification.getCertificateCode());
            //联系电话
            pdfDataMap.put("mobile", userAgentCertification.getAlias());
        } else {
            //打印错误信息
            log.error("获取经纪人信息失败,userId为" + quotationScheme.getUserId());
        }
        //提交任务
        uploadPdf(pdfDataMap, quotationScheme, quotationSchemeMapper);
        //批量插入spu
        quotationSchemeSpuService.saveBatch(batchSchemeSpuList);
        //批量插入其他产品
        quotationSchemeOtherService.saveBatch(batchSchemeOtherList);
        //返回
        return flag > 0 ? true : false;
    }


    /**
     * 上传文件的方法
     *
     * @param pdfDataMap
     * @param quotationScheme
     * @param quotationSchemeMapper
     */
    @Async
    public void uploadPdf(Map<String, String> pdfDataMap, QuotationScheme quotationScheme, QuotationSchemeMapper quotationSchemeMapper) {
        //开启线程来处理上传
        log.debug("进入上传pdf的方法");
        //调用工具类
        try {
            //生成PDF的测试path
            String pdfLocalPath = "./" + UUID.randomUUID() + "quotation.pdf";
            //String pdfLocalPath ="C:\\Users\\Administrator\\Desktop\\quotation.pdf";
            log.debug("开始执行PdfUtils");
            //生成本地文件
            PdfUtils.generate(downloadPdfTemplate(), pdfLocalPath, pdfDataMap, null);
            //打印执行完毕
            log.debug("PdfUtils执行完毕");
            //调用OSS工具类上传OSS服务器并返回下载地址
            String downloadUrl = uploadFile(pdfLocalPath, "quotation/" + UUID.randomUUID() + "quotation.pdf");
            //截取这个地址?前面的字符串
            quotationScheme.setDownloadUrl(downloadUrl.substring(0, downloadUrl.indexOf("?")));
            //更新downloadUrl
            quotationSchemeMapper.updateById(quotationScheme);
            //上传完成之后,再把本地的文件删除了
            FileUtil.del(pdfLocalPath);
        } catch (Exception e) {
            //服务器输出
            log.error("生成报价单pdf异常", e);
            //打印具体异常
            e.printStackTrace();
            //抛出异常
            throw new RuntimeException("生成报价单pdf文件失败");
        }
    }


    /**
     * 修改
     *
     * @param quotationScheme:
     * @return java.util.List<com.entity.QuotationScheme>
     * @author 尹涛涛
     * @since 2021-05-22
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, timeout = 36000, rollbackFor = Exception.class)
    public Boolean updateEnhance(QuotationScheme quotationScheme) {
        UpdateWrapper<QuotationScheme> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id", quotationScheme.getId());
        Integer i = quotationSchemeMapper.update(quotationScheme, updateWrapper);
        return i > 0 ? true : false;
    }


    /**
     * 删除
     *
     * @param quotationScheme:
     * @return java.util.List<com.entity.QuotationScheme>
     * @author 尹涛涛
     * @since 2021-05-22
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, timeout = 36000, rollbackFor = Exception.class)
    public Boolean removeEnhance(QuotationScheme quotationScheme) {
        QueryWrapper<QuotationScheme> queryWrapper = new QueryWrapper<>(quotationScheme);
        Integer i = quotationSchemeMapper.delete(queryWrapper);
        return i > 0 ? true : false;
    }


    /**
     * 增强查询条件
     *
     * @param quotationScheme:
     * @param queryWrapper:
     * @return void
     * @author 尹涛涛
     * @since 2021-05-22
     */
    private void query(QuotationScheme quotationScheme, QueryWrapper<QuotationScheme> queryWrapper) {
        /**
         * 排序,默认按照修改时间倒序排序
         */
        if (quotationScheme.getCollation() != null && StringUtils.isNotBlank(quotationScheme.getCollationFields())) {
            if (quotationScheme.getCollation()) {
                queryWrapper.orderByAsc(quotationScheme.getCollationFields());
            } else {
                queryWrapper.orderByDesc(quotationScheme.getCollationFields());
            }
        } else {
            queryWrapper.orderByDesc(quotationScheme.getCollationFields());
        }

        /**
         * 显示字段
         */
        if (StringUtils.isNotBlank(quotationScheme.getFields())) {
            queryWrapper.select(quotationScheme.getFields());
        }
        //根据咨询人姓名进行模糊查询
        if (StringUtils.isNotBlank(quotationScheme.getQuery())) {
            queryWrapper.like("name", quotationScheme.getQuery());
        }
    }


    /**
     * 单条，增强返回参数追加
     *
     * @param quotationScheme:
     * @return QuotationScheme
     * @author 尹涛涛
     * @since 2021-05-22
     */
    private QuotationScheme assignment(QuotationScheme quotationScheme) {
        return quotationScheme;
    }

    /**
     * 分页,增强返回参数追加
     *
     * @param quotationSchemeList:
     * @return QuotationScheme
     * @author 尹涛涛
     * @since 2021-05-22
     */
    private IPage assignment(IPage<QuotationScheme> quotationSchemeList) {
        //判断集合是否为空
        if (CollectionUtils.isEmpty(quotationSchemeList.getRecords())) {
            //直接返回
            return quotationSchemeList;
        }
        //遍历
        for (QuotationScheme quotationScheme : quotationSchemeList.getRecords()) {
            //险种id为空
            if (StringUtils.isBlank(quotationScheme.getDangerPlantedId())) {
                //继续下一次循环
                continue;
            }
            //创建rpcMap
            Map<String, String> rpcMap = new HashMap<>(1);
            //放入主键id
            rpcMap.put("id", quotationScheme.getDangerPlantedId());
            //打印
            log.debug("调用productRpc接口查询险种信息的入参:{}", rpcMap);
            //调用rpc
            Map<String, Object> resMap = productRpc.findProductDangerPlantedById(rpcMap).getObj();
            //打印
            log.debug("返回的险种信息为:{}", resMap);
            //shezhi
            if (MapUtil.isNotEmpty(resMap)) {
                //设置险种logo
                quotationScheme.setDangerPlantedLogo(Convert.toStr(resMap.get("picture")));
            }
        }
        //返回
        return quotationSchemeList;
    }


    /**
     * 集合,增强返回参数追加
     *
     * @param quotationSchemeList:
     * @return QuotationScheme
     * @author 尹涛涛
     * @since 2021-05-22
     */
    private List<QuotationScheme> assignment(List<QuotationScheme> quotationSchemeList) {
        quotationSchemeList.forEach(quotationScheme -> {
        });
        return quotationSchemeList;
    }

    /**
     * 根据id查询报价单详情
     *
     * @param quotationScheme:
     * @return
     */
    @Override
    public QuotationScheme findQuotationSchemeInfoById(QuotationScheme quotationScheme) {
        //打印
        log.debug("进入查询报价单详情的service方法");
        //先根据id查询报价单
        quotationScheme = getOneEnhance(quotationScheme);
        //判断是否为空
        if (quotationScheme == null) {
            //抛出异常
            throw new RuntimeException("报价单不存在");
        }
        //获取主键id
        String quotationSchemeId = quotationScheme.getId();
        //获取spuId
        QuotationSchemeSpu quotationSchemeSpu = new QuotationSchemeSpu();
        //设置spuId
        quotationSchemeSpu.setQuotationSchemeId(quotationSchemeId);
        //根据quotationSchemeId查询spu集合
        List<QuotationSchemeSpu> quotationSchemeSpuList = quotationSchemeSpuService.listEnhance(quotationSchemeSpu);
        //设置spu集合
        quotationScheme.setQuotationSchemeSpuList(quotationSchemeSpuList);
        //根据quotationSchemeId查询other集合
        QuotationSchemeOther quotationSchemeOther = new QuotationSchemeOther();
        //设置报价单id
        quotationSchemeOther.setQuotationSchemeId(quotationSchemeId);
        //查询
        List<QuotationSchemeOther> quotationSchemeOtherList = quotationSchemeOtherService.listEnhance(quotationSchemeOther);
        //设置other集合
        quotationScheme.setQuotationSchemeOtherList(quotationSchemeOtherList);
        //根据userId查询经纪人信息
        UserAgentCertification userAgentCertification = new UserAgentCertification();
        //设置userId
        userAgentCertification.setUserId(quotationScheme.getUserId());
        //打印
        log.debug("开始获取经纪人信息");
        //查询
        try {
            userAgentCertification = userAgentCertificationService.getOneEnhance(userAgentCertification);
        } catch (Exception e) {
            //打印日志
            log.error("获取经纪人信息异常:", e);
        }
        //非空判断
        if (userAgentCertification != null) {
            //设置经纪人
            quotationScheme.setAgent(userAgentCertification.getName());
            //设置工号
            quotationScheme.setWorkNo(userAgentCertification.getCertificateCode());
            //设置电话
            quotationScheme.setAgentMobile(userAgentCertification.getAlias());
        }
        //返回
        return quotationScheme;
    }

    /**
     * 上传
     *
     * @param file 文件地址
     * @param name 上传地址
     * @throws Exception
     */
    public String uploadFile(String file, String name) throws Exception {
        //创建客户端
        OSS client = new OSSClientBuilder().build(Oss.endpoint, AliyunKey.accessKeyId, AliyunKey.accessKeySecret);
        //获取上传文件流。
        InputStream inputStream = new FileInputStream(file);
        //上传
        client.putObject(Oss.bucket, name, inputStream);
        //设置URL过期时间为24小时。
        Date expiration = new Date(System.currentTimeMillis() + 24 * 3600 * 1000);
        //设置
        client.setBucketAcl(Oss.bucket, CannedAccessControlList.PublicRead);
        // 生成以GET方法访问的签名URL，访客可以直接通过浏览器访问相关内容。
        String url = client.generatePresignedUrl(Oss.bucket, name, expiration).toString();
        //关闭ossClient
        client.shutdown();
        //返回
        return url;
    }

    /**
     * 读取模板的字节数组
     *
     * @return
     */
    public byte[] downloadPdfTemplate() {
        //创建客户端
        OSS client = new OSSClientBuilder().build(Oss.endpoint, AliyunKey.accessKeyId, AliyunKey.accessKeySecret);
        //创建oss对象
        OSSObject ossObject = client.getObject(Oss.bucket, QuotationSchemeService.PDF_TEMPLATE_PATH);
        //转化为字节数组
        byte[] bytes = IoUtil.readBytes(ossObject.getObjectContent());
        //返回
        return bytes;
    }
}
