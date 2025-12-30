package com.gb.user;

import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.http.HttpRequest;
import com.alibaba.fastjson.JSON;
import com.gb.SpringTest;
import com.gb.account.entity.bo.UserBO;
import com.gb.account.entity.bo.UserExtendsBO;
import com.gb.account.entity.enums.UserFormalStateEnum;
import com.gb.account.entity.query.UserQuery;
import com.gb.account.entity.vo.UserVO;
import com.gb.account.service.UserService;
import com.gb.permissions.entity.bo.RoleBO;
import com.gb.user.entity.bo.UserBasicInfoBO;
import io.seata.common.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.utils.Lists;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @ClassName UserTest
 * @Description 用户测试
 * @Author 孙凯伦
 * @Mobile 13777579028
 * @Email 376253703@qq.com
 * @Time 2021/11/2 3:17 PM
 */
@Slf4j
public class UserTest extends SpringTest {


    /**
     * 用户表
     */
    @Autowired
    private UserService userService;


    @Test
    public void updateEnhance(){
        userService.updateEnhance(new UserBO(){{
            setId("922335205");
            setUserExtendsBO(new UserExtendsBO(){{
                setName("测试修改");
            }});
            setRoleIdList(new String[]{"1"});
            setTypeValueId(new String[]{"1"});
            setUserFormalStateEnum(UserFormalStateEnum.修改);
        }});
    }


    @Test
    public void selectOne() throws Exception {
        UserVO user = userService.getOneEnhance(new UserQuery(){{
            setId("922335205");
        }});
        System.out.printf("11");
    }

    @Test
    public void ss(){
        userService.initUserInfo(new UserBasicInfoBO(){{
            setAppCode("NET-INSURANCE");
            setId(Long.valueOf("4545454"));
        }});
    }

    @Test
    public void updateRole(){
        String token = "44390b34777e4452ba8835532ca0fd9f";
        String runtimeName = "test";
        String url = getUrl(runtimeName);
        List<String> jsonParams = Lists.newArrayList();
        jsonParams.add("{\"id\":\"1750357569559863298\",\"label\":\"1\",\"systemId\":\"7\"}");
        jsonParams.add("{\"id\":\"32\",\"label\":\"1\",\"systemId\":\"4\"}");
        jsonParams.add("{\"id\":\"34\",\"label\":\"1\",\"systemId\":\"3\"}");
        jsonParams.add("{\"id\":\"35\",\"label\":\"1\",\"systemId\":\"1\"}");
        jsonParams.add("{\"id\":\"55\",\"label\":\"1\",\"systemId\":\"2\"}");
        jsonParams.add("{\"id\":\"56\",\"label\":\"1\",\"systemId\":\"5\"}");
        try {
            for(String json : jsonParams) {
                RoleBO bo = JSON.parseObject(json, RoleBO.class);
                String reqJson = JSON.toJSONString(bo);
                reqJson = reqJson.replaceAll("\\{\"","").replaceAll("\",\"","&").replaceAll("\":\"","=").replaceAll("\"}","");
                String putUrl = url + reqJson;
                HttpRequest httpRequest = HttpRequest.put(putUrl);
                if(StringUtils.equals(runtimeName,"gray")) {
                    httpRequest.header("dataSource", runtimeName);
                }
                if(StringUtils.isNotBlank(token)) {
                    httpRequest.header("token", token);
                }
                final String resultJson =  httpRequest.execute().body();
                ThreadUtil.sleep(1000);
                log.debug("put请求完毕--请求后台url：{}，\n 请求结果：{}\n", putUrl, resultJson);
                log.debug("***********************更新角色配置信息完毕***********************");
            }
        } catch (Exception e) {
            log.info("错误信息：", e);
        }
    }

    /**
     * 根据当前环境获取请求路径
     * @param runtimeName 当前运行时环境
     * @return String
     */
    private String getUrl(String runtimeName) {
        String url = StringUtils.EMPTY;
        switch (runtimeName) {
            case "local":
                url = "http://127.0.0.1:7500/role/update?";
                break;
            case "test":
                url = "http://t.cngongbao.com:11000/test/gbw/user/role/update?";
                break;
            case "release":
                url = "http://release-gateway.gongbao.cn/user/role/update?";
                break;
            case "gray":
            case "produce":
                url = "http://gateway.gongbao.cn/user/role/update?";
                break;
            default:break;
        }
        if(StringUtils.isNotBlank(url) && StringUtils.equals(runtimeName,"gray")) {
            url = url + "dataSource=gray&";
        }
        return url;
    }
}
