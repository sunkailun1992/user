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
class PostgresqlDdlTest {

    @Test
    void personalBootstrapNeverRecreatesDemoAccountsPasswordsOrTheirDepartmentRelations() throws Exception {
        var ddl = new PostgresqlDdl(mock(DataSource.class));
        for (String resource : ddl.getSqlFiles()) {
            String sql = new ClassPathResource(resource).getContentAsString(java.nio.charset.StandardCharsets.UTF_8);
            assertThat(sql).as(resource)
                    .doesNotContainPattern("(?im)\\bINSERT\\s+(?:IGNORE\\s+)?INTO\\s+auth_user\\b")
                    .doesNotContain("$2a$", "$2b$", "$2y$");
        }
        String schema = new ClassPathResource("db/auth-schema.sql")
                .getContentAsString(java.nio.charset.StandardCharsets.UTF_8);
        assertThat(schema).contains("CREATE TABLE IF NOT EXISTS auth_user", "'r_admin_100'")
                .doesNotContain("'200'", "r_scope_", "u_scope_", "u_admin_100", "123456")
                .doesNotContainPattern("(?im)^INSERT IGNORE INTO auth_(dept|user_tenant|user_role|role_data_scope)\\b");
    }

    @Test
    void personalDatabaseInitializationMustNotRegisterExternalOAuthClientsOrFixedCredentials() throws Exception {
        String sql = new ClassPathResource("db/auth-oauth-client-schema.sql")
                .getContentAsString(java.nio.charset.StandardCharsets.UTF_8);

        assertThat(sql).contains("CREATE TABLE IF NOT EXISTS auth_oauth_client", "client_secret varchar(255)")
                .doesNotContainPattern("(?im)^\\s*(INSERT|REPLACE|UPDATE|DELETE)\\s")
                .doesNotContainPattern("https?://")
                .doesNotContain("$2a$", "$2b$", "$2y$");
    }

    @Test
    void shouldRegisterOnlyPostgresqlStructureThenConflictSafeBootstrap() {
        PostgresqlDdl postgresqlDdl = new PostgresqlDdl(mock(DataSource.class));

        List<String> sqlFiles = postgresqlDdl.getSqlFiles();

        assertThat(sqlFiles).containsExactly(
                "db/postgresql/common-infra-schema.sql",
                "db/postgresql/user-schema.sql",
                "db/postgresql/user-bootstrap.sql"
        );
        assertThat(sqlFiles).doesNotHaveDuplicates();
        assertThat(sqlFiles).allMatch(path -> path.startsWith("db/postgresql/"));
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
