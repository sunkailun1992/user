package com.gb.role;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gb.SpringTest;
import com.gb.account.entity.query.UserGroupQuery;
import com.gb.account.entity.query.UserRoleQuery;
import com.gb.account.entity.vo.UserGroupVO;
import com.gb.account.entity.vo.UserRoleVO;
import com.gb.account.service.UserRoleService;
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
public class UserRoleTest extends SpringTest {

    /**
     * 角色表
     */
    @Autowired
    private UserRoleService userRoleService;


    @Test
    public void page() {
        Page<UserRoleVO> page = userRoleService.pageEnhance(new Page(1, 10), new UserRoleQuery() {{
            setRoleId("1");
        }});
        System.out.printf("");
    }

}
