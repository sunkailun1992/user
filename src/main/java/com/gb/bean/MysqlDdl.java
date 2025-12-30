package com.gb.bean;

import mybatis.mate.ddl.SimpleDdl;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

/**
 * @ClassName MysqlDdl
 * @Description 版本sql同步
 * @Author 孙凯伦
 * @Mobile 13777579028
 * @Email 376253703@qq.com
 * @Time 2021/12/1 3:33 PM
 */

@Component
public class MysqlDdl extends SimpleDdl {

    /**
     * 执行 SQL 脚本方式
     */
    @Override
    public List<String> getSqlFiles() {
        return Arrays.asList(
                "db/g/3.2.6/用户标签地区.sql",
                "db/b/3.2.2/新增表.sql",
                "db/b/3.2.2/结构调整.sql",
                "db/crm/2.0.0/团队.sql"
        );
    }
}

