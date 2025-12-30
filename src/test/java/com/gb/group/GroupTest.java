package com.gb.group;

import com.gb.SpringTest;
import com.gb.permissions.entity.Group;
import com.gb.permissions.entity.bo.GroupBO;
import com.gb.permissions.service.GroupService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @ClassName GroupRoleTest
 * @Description 组
 * @Author 孙凯伦
 * @Mobile 13777579028
 * @Email 376253703@qq.com
 * @Time 2021/11/2 3:58 PM
 */
public class GroupTest extends SpringTest {


    /**
     * 组
     */
    @Autowired
    private GroupService groupService;


    @Test
    public void save(){
        groupService.saveEnhance(new GroupBO(){{
            setSystemId("2");
            setName("test");
            setRoleList(new String[]{"2"});
            setUserList(new String[]{"922335205"});
        }});
    }

    @Test
    public void update(){
        groupService.updateEnhance(new GroupBO(){{
            setId("1455446004481404929");
            setName("111");
            setRoleList(new String[]{"2"});
            setUserList(new String[]{"922335205"});
        }});
    }
}
