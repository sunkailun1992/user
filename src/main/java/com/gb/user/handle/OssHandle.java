package com.gb.user.handle;

import cn.hutool.core.io.IoUtil;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.OSSObject;
import com.gb.aliyun.AliyunKey;
import com.gb.aliyun.Oss;
import lombok.extern.slf4j.Slf4j;

import java.io.File;

/**
 * 常规处理类
 * @author yyl
 */
@Slf4j
public class OssHandle{

    public static final String BROKER_DIR = "broker/";

    public static final String LOCAL_ROOTDIR = System.getProperty("java.io.tmpdir") + File.separator;

    private OssHandle(){};

    private static OSS getOss(){
        return new OSSClientBuilder().build(Oss.endpoint, AliyunKey.accessKeyId, AliyunKey.accessKeySecret);
    }

    /**
     * 判断文件是否存在
     *
     * @param fileName: 文件名，包含bucket下面的目录
     * @return boolean
     * @author sunx
     * @since 2021/3/19  4:35 下午
     */
    public static boolean existFile(String fileName){
        OSS oss = getOss();
        boolean flag = oss.doesObjectExist(Oss.bucket, fileName);
        oss.shutdown();
        return flag;
    }

    /**
     * 读取文件字节数组
     *
     * @param fileName: 文件名，包含bucket下面的目录
     * @return byte[]
     * @author sunx
     * @since 2021/3/19  4:35 下午
     */
    public static byte[] downloadFile(String fileName){
        byte[] resultByta = new byte[100];
        OSS oss = getOss();
        if(!oss.doesObjectExist(Oss.bucket, fileName)){
            return resultByta;
        }
        OSSObject ossObject = oss.getObject(Oss.bucket, fileName);
        resultByta = IoUtil.readBytes(ossObject.getObjectContent());
        oss.shutdown();
        return resultByta;
    }

    /**
     * 获取经纪人本地文件临时路径
     *
     * @param fileName: 文件名，包含bucket下面的目录
     * @return String
     * @author sunx
     * @since 2021/3/19  4:35 下午
     */
    public static String getLocalFilePath(String fileName){
        return LOCAL_ROOTDIR  + fileName;
    }

    /**
     * 获取OSS文件全路径
     *
     * @param fileName: 文件名，包含bucket下面的目录
     * @return String
     * @author sunx
     * @since 2021/3/19  4:35 下午
     */
    public static String getOssFileFullPath(String fileName){
        return Oss.domain + "/" + fileName;
    }

    /**
     * 获取经纪人OSS文件路径
     *
     * @param fileName: 文件名，包含bucket下面的目录
     * @return String
     * @author sunx
     * @since 2021/3/19  4:35 下午
     */
    public static String getOssBrokerFilePath(String fileName){
        return BROKER_DIR  + fileName;
    }
}
