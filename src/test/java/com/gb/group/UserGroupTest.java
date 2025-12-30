package com.gb.group;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gb.SpringTest;
import com.gb.account.entity.query.UserGroupQuery;
import com.gb.account.entity.vo.UserGroupVO;
import com.gb.account.service.UserGroupService;
import com.gb.permissions.entity.bo.GroupBO;
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
public class UserGroupTest extends SpringTest {


    /**
     * 用户组
     */
    @Autowired
    private UserGroupService userGroupService;


    @Test
    public void page() {
        Page<UserGroupVO> page = userGroupService.pageEnhance(new Page(1, 10), new UserGroupQuery(){{
            setGroupId("1");
        }});
        System.out.printf("");
    }

}
