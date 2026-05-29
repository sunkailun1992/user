package com.kellen.bean;

import com.baomidou.mybatisplus.extension.ddl.IDdl;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.List;
import java.util.function.Consumer;

/**
 * MyBatis-Plus自动维护DDL。
 * <p>
 * 项目数据库结构由SQL脚本统一维护，不在Controller或Service中拼接CREATE TABLE。
 *
 * @author sunkailun
 * @className MysqlDdl
 * @DateTime 2026/05/26
 * @email 376253703@qq.com
 */
@Component
public class MysqlDdl implements IDdl {

    /**
     * 当前项目数据源。
     */
    private final DataSource dataSource;

    /**
     * 构造MyBatis-Plus自动维护DDL组件。
     *
     * @param dataSource 当前项目数据源
     * @return void
     * @author sunkailun
     * @DateTime 2026/05/26
     * @email 376253703@qq.com
     */
    public MysqlDdl(DataSource dataSource) {
        // 保存当前项目数据源，交给MyBatis-Plus DDL运行器执行脚本。
        this.dataSource = dataSource;
    }

    /**
     * 指定执行脚本的数据源。
     *
     * @param consumer MyBatis-Plus DDL脚本执行器
     * @return void
     * @author sunkailun
     * @DateTime 2026/05/26
     * @email 376253703@qq.com
     */
    @Override
    public void runScript(Consumer<DataSource> consumer) {
        // 使用当前应用数据源执行DDL脚本，避免业务代码手写建表SQL。
        consumer.accept(dataSource);
    }

    /**
     * 获取自动维护DDL脚本列表。
     *
     * @return java.util.List<java.lang.String>
     * @author sunkailun
     * @DateTime 2026/05/26
     * @email 376253703@qq.com
     */
    @Override
    public List<String> getSqlFiles() {
        // 原始DDL脚本执行后会记录到ddl_history，后续变更必须追加新脚本并保持顺序。
        return List.of(
                "db/auth-schema.sql",
                "db/20260527-auth-resource-tree-data.sql",
                "db/20260527-auth-data-permission.sql",
                "db/20260529-auth-permission-test-data.sql"
        );
    }
}
