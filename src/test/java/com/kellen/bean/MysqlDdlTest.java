package com.kellen.bean;

import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;

import javax.sql.DataSource;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

/**
 * 用户中心 DDL 脚本注册顺序测试。
 */
class MysqlDdlTest {

    @Test
    void shouldRegisterRagPermissionMigrationAfterExistingAuthScripts() {
        MysqlDdl mysqlDdl = new MysqlDdl(mock(DataSource.class));

        List<String> sqlFiles = mysqlDdl.getSqlFiles();

        assertThat(sqlFiles).containsSubsequence(
                "db/auth-schema.sql",
                "db/auth-external-identity-schema.sql",
                "db/auth-oauth-client-schema.sql",
                "db/auth-rag-resource-schema.sql",
                "db/auth-rag-graph-resource-schema.sql"
        );
        assertThat(sqlFiles).doesNotHaveDuplicates();
    }

    @Test
    void shouldDeclareGraphMenuViewManageAndAdministratorInheritance() throws Exception {
        String sql = new ClassPathResource("db/auth-rag-graph-resource-schema.sql")
                .getContentAsString(java.nio.charset.StandardCharsets.UTF_8);

        assertThat(sql)
                .contains("'menu:rag:graph'")
                .contains("'rag:graph:view'")
                .contains("'rag:graph:manage'")
                .contains("manage_resource.code = 'user:auth:manage'");
    }
}
