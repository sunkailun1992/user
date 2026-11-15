package com.kellen.database;

import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.spring.MybatisSqlSessionFactoryBean;
import com.kellen.auth.entity.AuthExternalIdentity;
import com.kellen.auth.mapper.AuthExternalIdentityMapper;
import com.kellen.auth.typehandler.PostgresqlJsonStringTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.flywaydb.core.api.FlywayException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;

import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/** 服务自己的 Flyway 迁移在独占 schema 上实际执行，并回归布尔、时间与大小写不敏感比较；不连接真实业务库。 */
@EnabledIfEnvironmentVariable(named = "FLEET_PG_TEST_URL", matches = ".+")
class PostgresqlSchemaLiveTest extends PostgresqlSchemaFixture {

    private static final List<String> TABLES = List.of("auth_dept", "auth_external_client", "auth_external_identity",
            "auth_external_signature_nonce", "auth_oauth_client", "auth_resource", "auth_role", "auth_role_data_scope",
            "auth_role_resource", "auth_tenant", "auth_user", "auth_user_role", "auth_user_tenant");

    /** V1 里种子段的开头标记；种子用 ON CONFLICT DO NOTHING，只补缺失的行。 */
    private static final String SEED_MARKER = "-- 种子数据";

    @Test
    void migratesEmptySchemaToLatestVersion() throws Exception {
        assertThat(migrate(null).success).isTrue();
        assertThat(appliedVersions()).containsExactly("1", "2", "3", "4");
        assertThat(strings("SELECT table_name FROM information_schema.tables WHERE table_schema = '" + schema
                + "' AND table_type = 'BASE TABLE' AND table_name NOT IN ('undo_log', 'flyway_schema_history') ORDER BY 1"))
                .containsExactlyElementsOf(TABLES);
        for (String table : TABLES) {
            assertThat(columns(table)).as(table)
                    .contains("id", "create_time", "update_time", "creator", "updater", "is_deleted", "tenant_id", "version")
                    .doesNotContain("create_date_time", "modify_date_time", "create_name", "modify_name", "is_delete", "label");
        }
        for (String table : List.of("auth_tenant", "auth_role", "auth_dept", "auth_user", "auth_resource")) {
            assertThat(columns(table)).as(table).contains("code", "description", "sorting");
        }
        for (String table : List.of("auth_role_data_scope", "auth_role_resource", "auth_user_role", "auth_user_tenant")) {
            assertThat(columns(table)).as(table).contains("code", "description").doesNotContain("sorting");
        }
        for (String table : List.of("auth_external_client", "auth_external_identity", "auth_external_signature_nonce",
                "auth_oauth_client")) {
            assertThat(columns(table)).as(table).doesNotContain("code", "description", "sorting");
        }
        assertThat(number("SELECT count(*) FROM information_schema.tables WHERE table_schema = '" + schema
                + "' AND table_name = 'ddl_history'")).isZero();
        assertThat(number("SELECT count(*) FROM information_schema.tables WHERE table_schema = '" + schema
                + "' AND table_name = 'undo_log'")).isEqualTo(1);
    }

    @Test
    void dropUnusedColumnsRefusesWhenAColumnStillHasData() throws Exception {
        migrate("3");
        execute("INSERT INTO auth_user_role(id, user_id, role_id, tenant_id, sorting) VALUES ('fixture-sorted', 'u', 'r', 't', 7)");
        assertThatThrownBy(() -> migrate(null)).isInstanceOf(FlywayException.class);
        assertThat(appliedVersions()).containsExactly("1", "2", "3");
        assertThat(columns("auth_user_role")).contains("sorting", "label");
        assertThat(columns("auth_external_client")).contains("code");
    }

    @Test
    void usernameAndResourceKeysRetainCaseAndAccentInsensitiveUniqueness() throws Exception {
        migrate(null);
        execute("INSERT INTO auth_user(id, username, password, tenant_id) VALUES ('fixture-user', 'Café', 'synthetic-only', 'fixture-tenant')");
        assertThat(number("SELECT count(*) FROM auth_user WHERE username = 'CAFE' AND is_deleted = false")).isEqualTo(1);
        assertThat(number("SELECT count(*) FROM auth_user WHERE username LIKE 'caf%'")).isEqualTo(1);
        assertThatThrownBy(() -> execute("INSERT INTO auth_user(id, username, password, tenant_id) VALUES ('fixture-duplicate', 'CAFE', 'synthetic-only', 'fixture-tenant')"))
                .isInstanceOf(SQLException.class).extracting("SQLState").isEqualTo("23505");
        execute("INSERT INTO auth_resource(id, name, resource_category, code, tenant_id) VALUES ('fixture-resource', 'fixture', 'BACKEND', 'API:CAFÉ', 'fixture-tenant')");
        assertThatThrownBy(() -> execute("INSERT INTO auth_resource(id, name, resource_category, code, tenant_id) VALUES ('fixture-resource-duplicate', 'fixture', 'BACKEND', 'api:cafe', 'fixture-tenant')"))
                .isInstanceOf(SQLException.class).extracting("SQLState").isEqualTo("23505");
    }

    @Test
    void seedKeepsExistingTenantRoleAndPermissionsAndPreservesSha256Ids() throws Exception {
        migrate("1");
        long resources = number("SELECT count(*) FROM auth_resource");
        execute("UPDATE auth_tenant SET name = 'synthetic-custom-tenant' WHERE id = '100'");
        execute("UPDATE auth_role SET name = 'synthetic-custom-role', data_scope = 'SELF' WHERE id = 'r_admin_100'");
        execute("UPDATE auth_resource SET name = 'synthetic-custom-resource' WHERE code = 'menu:rag:graph'");
        script(seedSection());
        migrate(null);
        assertThat(number("SELECT count(*) FROM auth_resource")).isEqualTo(resources);
        assertThat(number("SELECT count(*) FROM auth_user")).isZero();
        assertThat(number("SELECT count(*) FROM auth_tenant WHERE id = '100' AND name = 'synthetic-custom-tenant'")).isEqualTo(1);
        assertThat(number("SELECT count(*) FROM auth_role WHERE id = 'r_admin_100' AND name = 'synthetic-custom-role' AND data_scope = 'SELF'")).isEqualTo(1);
        assertThat(number("SELECT count(*) FROM auth_resource WHERE code = 'menu:rag:graph' AND name = 'synthetic-custom-resource' AND id = 'res_500ab6edc80891bf27e75bcecd06d98c'"))
                .isEqualTo(1);
    }

    @Test
    void explicitJsonHandlerSurvivesARealMapperRoundTripAndDatabaseValidation() throws Exception {
        migrate(null);
        var configuration = new MybatisConfiguration();
        configuration.setMapUnderscoreToCamelCase(true);
        configuration.addMapper(AuthExternalIdentityMapper.class);
        var factory = new MybatisSqlSessionFactoryBean();
        factory.setDataSource(new SingleConnectionDataSource(connection, true));
        factory.setConfiguration(configuration);
        try (var session = factory.getObject().openSession(true)) {
            var identity = new AuthExternalIdentity();
            identity.setId("fixture-json");
            identity.setProviderCode("synthetic");
            identity.setExternalTenantId("");
            identity.setExternalUserId("");
            identity.setExternalPatientId("");
            identity.setLocalTenantId("fixture-tenant");
            identity.setLocalUserId("fixture-user");
            identity.setTenantId("fixture-tenant");
            identity.setDeleted(false);
            identity.setMetadataJson("{\"synthetic\":true}");
            var mapper = session.getMapper(AuthExternalIdentityMapper.class);
            assertThat(mapper.insert(identity)).isEqualTo(1);
            session.clearCache();
            assertThat(mapper.selectById(identity.getId()).getMetadataJson()).isEqualTo("{\"synthetic\": true}");
        }
        try (var statement = connection.prepareStatement("UPDATE auth_external_identity SET metadata_json = ? WHERE id = 'fixture-json'")) {
            new PostgresqlJsonStringTypeHandler().setNonNullParameter(statement, 1, "not-json", JdbcType.OTHER);
            assertThatThrownBy(statement::executeUpdate).isInstanceOf(SQLException.class)
                    .extracting("SQLState").isEqualTo("22P02");
        }
    }

    @Test
    void secondMigrationRunIsANoOpAndKeepsPostgresqlTypes() throws Exception {
        migrate(null);
        assertThat(migrate(null).migrationsExecuted).isZero();
        assertThat(number("SELECT count(*) FROM information_schema.columns WHERE table_schema = '" + schema + "' AND column_name = 'is_deleted' AND data_type = 'boolean'"))
                .isEqualTo(13);
        assertThat(number("SELECT count(*) FROM information_schema.columns WHERE table_schema = '" + schema + "' AND column_name = 'update_time' AND data_type = 'timestamp without time zone'"))
                .isEqualTo(13);
        assertThat(number("SELECT CASE WHEN 'Café' COLLATE fleet_general_ci = 'CAFE' THEN 1 ELSE 0 END")).isEqualTo(1);
        assertThat(number("SELECT CASE WHEN 'Café' COLLATE fleet_general_ci LIKE 'caf%' THEN 1 ELSE 0 END")).isEqualTo(1);
    }

    /** 从 V1 截出种子段：只补缺失的默认租户、角色与权限，重复执行不改变已有行。 */
    private ByteArrayResource seedSection() throws Exception {
        String baseline = new ClassPathResource("db/migration/V1__baseline.sql").getContentAsString(StandardCharsets.UTF_8);
        int start = baseline.indexOf(SEED_MARKER);
        assertThat(start).as("V1 种子段标记").isPositive();
        return new ByteArrayResource(baseline.substring(start).getBytes(StandardCharsets.UTF_8));
    }
}
