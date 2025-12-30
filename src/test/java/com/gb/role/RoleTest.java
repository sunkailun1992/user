package com.gb.role;

import com.gb.SpringTest;
import com.gb.permissions.entity.bo.GroupBO;
import com.gb.permissions.entity.bo.RoleBO;
import com.gb.permissions.service.RoleService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @ClassName RoleTest
 * @Description 角色
 * @Author 孙凯伦
 * @Mobile 13777579028
 * @Email 376253703@qq.com
 * @Time 2021/11/2 4:08 PM
 */
public class RoleTest extends SpringTest {

    /**
     * 角色表
     */
    @Autowired
    private RoleService roleService;


    @Test
    public void save(){
        roleService.saveEnhance(new RoleBO(){{
            setSystemId("2");
            setName("test");
            setResourceList(new String[]{"2"});
            setUserList(new String[]{"922335205"});
        }});
    }

    @Test
    public void update(){
        roleService.updateEnhance(new RoleBO(){{
            setId("1455447147567685634");
            setName("111");
            setResourceList(new String[]{"2"});
            setUserList(new String[]{"922335205"});
        }});
    }
}
