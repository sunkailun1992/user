package com.gb.user.service.impl;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.io.FileUtil;
import cn.hutool.extra.qrcode.QrCodeUtil;
import com.alibaba.fastjson.JSON;
import com.gb.account.entity.query.UserTypeValueQuery;
import com.gb.account.service.UserTypeValueService;
import com.gb.aliyun.oss.OssUtils;
import com.gb.bean.GongBaoConfig;
import com.gb.user.constant.RedisConstant;
import com.gb.user.entity.UserAgentCertification;
import com.gb.user.entity.bo.InviteLinkBO;
import com.gb.user.entity.vo.InviteLinkVO;
import com.gb.user.enums.UserTypeInfoEnum;
import com.gb.user.service.BrokerInvitationService;
import com.gb.user.handle.OssHandle;
import com.gb.user.service.UserAgentCertificationService;
import com.gb.utils.JsonUtil;
import com.gb.utils.PdfUtils;
import com.gb.utils.RedisUtils;
import com.gb.utils.constants.UniversalConstant;
import com.gb.utils.exception.BusinessException;
import com.gb.utils.exception.ParameterNullException;
import com.google.common.collect.Maps;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


/**
 * <p>
 * 用户中心查询接口控制器接口实现类
 * </p>
 *
 * @author sunx
 * @since 2021-03-17
 */
@Slf4j
@Service
@Setter(onMethod_ = {@Autowired})
public class BrokerInvitationServiceImpl implements BrokerInvitationService {

    private UserTypeValueService userTypeValueService;

    private UserAgentCertificationService userAgentCertificationService;

    private StringRedisTemplate stringRedisTemplate;

    @Override
    public InviteLinkVO queryInviteLinkByUserId(InviteLinkBO bo) {
        //1、判断用户ID是否存在在组中，是否与传输的参数一致
        Long count = userTypeValueService.countEnhance(new UserTypeValueQuery() {{
            setUserId(bo.getUserId());
            setUserTypeCode(UserTypeInfoEnum.管家.getCode());
        }});
        if (count < 1 && (bo.getType() == 1)) {
            log.error("查询用户邀请链接，用户标签信息为空！请求参数：{}", JsonUtil.json(bo));
            throw new BusinessException("参数不匹配！");
        }
        String appointLink = ((bo.getType() == 0) ? GongBaoConfig.outBrokerAppointLink : GongBaoConfig.brokerAppointLink);
        //2、根据用户ID查询认证信息
        InviteLinkVO vo = new InviteLinkVO();
        BeanUtils.copyProperties(bo, vo);
        vo.setUserAppointLink(appointLink);
        vo.setBusinessBookedLink(GongBaoConfig.businessBookedLink);
        UserAgentCertification userAgentCertification = userAgentCertificationService.getOneEnhance(new UserAgentCertification().setUserId(bo.getUserId()));
        if (Objects.isNull(userAgentCertification)) {
            log.debug("查询用户邀请链接，用户认证信息不存在！请求参数：{}", JsonUtil.json(bo));
            return vo;
        }
        vo.setMobile(userAgentCertification.getMobile());
        vo.setName(userAgentCertification.getName());
        vo.setCertificateCode(userAgentCertification.getCertificateCode());
        return vo;
    }

    @Override
    public String downQrCode(InviteLinkBO bo) {
        //0、校验用户是否是已经认证的用户信息
        UserAgentCertification userAgentCertification = userAgentCertificationService.getOneEnhance(new UserAgentCertification().setUserId(bo.getUserId()).setCertificateCode(bo.getCertificateCode()));
        if (Objects.isNull(userAgentCertification) || userAgentCertification.getState() != 1 || !StringUtils.equals(bo.getName(), userAgentCertification.getName())) {
            log.error("用户ID：{}，对应的用户认证信息为：{}", bo.getUserId(), JSON.toJSONString(userAgentCertification));
            throw new ParameterNullException("未找到该用户的认证信息！");
        }
        //1、从缓存中获取图片地址
        String key = RedisConstant.BROKER_QR + bo.getUserId();
        Map<Integer, String> redisQrCodeMap = JsonUtil.bean(RedisUtils.get(stringRedisTemplate, key), Map.class);
        Map<Integer, String> qrCodeMap = MapUtils.isEmpty(redisQrCodeMap) ? Maps.newHashMap() : redisQrCodeMap;
        if(qrCodeMap.containsKey(bo.getType()) && StringUtils.isNotBlank(redisQrCodeMap.get(bo.getType()))){
            return redisQrCodeMap.get(bo.getType());
        }
        //2、缓存中不存在地址的话，就生成一个PDF地址，并添加到缓存中，返回给前端
        String url = generateQrUrl(bo);
        if(StringUtils.isBlank(url)){
            log.error("二维码PDF文件生成为空，请求参数信息：【{}】", JSON.toJSONString(bo));
            throw new BusinessException("二维码PDF文件生成失败！");
        }
        qrCodeMap.put(bo.getType(), url);
        RedisUtils.add(stringRedisTemplate, key, qrCodeMap);
        return url;
    }

    /**
     * 生成二维码URL
     *
     * @param bo: 请求参数
     * @return String
     * @author sunx
     * @since 2021-05-25
     */
    private String generateQrUrl(InviteLinkBO bo) {
        String pdfFileName = "brokerQR_" + bo.getUserId() + "_" + bo.getType() + ".pdf";
        String localPdfUrl = OssHandle.getLocalFilePath(pdfFileName);
        String ossPdfPath = OssHandle.getOssBrokerFilePath(pdfFileName);
        try {
            //1、判断阿里云上面是否存在二维码PDF文件，如果存在，直接返回阿里云PDF文件地址
            if (OssHandle.existFile(ossPdfPath)) {
                return OssHandle.getOssFileFullPath(ossPdfPath);
            }
            //2、判断本地是否存在二维码pdf文件地址，如果存在，上传到阿里云，并返回阿里云二维码pdf文件地址
            if (FileUtil.exist(localPdfUrl)) {
                OssUtils.upload(localPdfUrl, ossPdfPath);
            } else {
                //2.1、判断阿里云否存在pdf模板文件地址，如果存在，则把pdf模板以字节格式下载并保存到本地，然后上传到阿里云
                if(OssHandle.existFile(GongBaoConfig.brokerQRTempUrl)){
                    byte[] pdfTemplate = OssHandle.downloadFile(GongBaoConfig.brokerQRTempUrl);
                    PdfUtils.generate(pdfTemplate, localPdfUrl, new HashMap<String, String>(2){{
                        put("name", bo.getName());
                        put("certificateCode", bo.getCertificateCode());
                    }}, getQrCode(bo.getType(), bo.getUserId(), bo.getName()));
                    log.debug("用户二维码pdf文件生成成功！【userId：{}，name：{}，type：{}】", bo.getUserId(), bo.getName(), bo.getType());
                    OssUtils.upload(localPdfUrl, ossPdfPath);
                } else {
                    throw new ParameterNullException("未找到生成二维码的模板！");
                }
            }
            return OssHandle.getOssFileFullPath(ossPdfPath);
        }catch (Exception e) {
            log.error("用户id：{}，生成二维码pdf文件异常！错误信息：", bo.getUserId(), e);
            throw new BusinessException("生成二维码pdf文件异常！");
        } finally {
            try{
                if(FileUtil.exist(localPdfUrl)){
                    FileUtil.del(localPdfUrl);
                }
            }catch (Exception e){
                log.error("生成的本地二维码pdf文件删除异常：", e);
            }
        }
    }

    /**
     * 生成二维码
     *
     * @author sunx
     * @since 2021-05-25
     * @param type: 请求参数类型
     * @param userId: 用户唯一标志
     * @param name: 姓名
     * @return  Map<String, String>
     */
    private Map<String, String> getQrCode(int type, String userId, String name){
        String generateUrl = (type == 1 ? GongBaoConfig.brokerAppointLink : GongBaoConfig.outBrokerAppointLink) + "?agentUserName=" + name + "&agentUserId=" + userId;
        //生成二维码
        BufferedImage bufferedImage = QrCodeUtil.generate(generateUrl, UniversalConstant.TWO_HUNDRED, UniversalConstant.TWO_HUNDRED);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        try {
            ImageIO.write(bufferedImage, "png", stream);
        } catch (IOException e) {
            log.error("图片IO绘画二维码异常：", e);
        }
        return new HashMap<String, String>(1){{
            put("qrCode", Base64.encode(stream.toByteArray()));}};
    }
}