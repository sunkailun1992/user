package com.kellen.database;

import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.spring.MybatisSqlSessionFactoryBean;
import com.kellen.auth.entity.AuthExternalIdentity;
import com.kellen.auth.mapper.AuthExternalIdentityMapper;
import com.kellen.auth.typehandler.PostgresqlJsonStringTypeHandler;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.baomidou.mybatisplus.core.metadata.TableInfoHelper;
import org.apache.ibatis.builder.MapperBuilderAssistant;
import org.apache.ibatis.type.JdbcType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;

import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.ArrayList;
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

    /** 每张表都必须有的全部基础字段；undo_log 与 Flyway 记录表是框架表，除外。 */
    private static final List<String> BASE_COLUMNS = List.of("id", "code", "description", "create_time", "create_by",
            "update_time", "update_by", "is_deleted", "label", "sorting", "version", "tenant_id", "create_dept");

    @Test
    void everyTableHasAllBaseColumns() throws Exception {
        migrate(null);
        List<String> tables = strings("SELECT table_name FROM information_schema.tables WHERE table_schema = '" + schema
                + "' AND table_type = 'BASE TABLE' AND table_name NOT IN ('undo_log', 'flyway_schema_history') ORDER BY 1");
        assertThat(tables).isNotEmpty();
        for (String table : tables) {
            assertThat(columns(table)).as(table).containsAll(BASE_COLUMNS)
                    .doesNotContain("creator", "updater", "create_name", "modify_name", "create_date_time", "modify_date_time");
        }
    }

    @Test
    void migratesEmptySchemaToLatestVersion() throws Exception {
        assertThat(migrate(null).success).isTrue();
        assertThat(appliedVersions()).containsExactly("1", "2", "3", "4", "5");
        assertThat(strings("SELECT table_name FROM information_schema.tables WHERE table_schema = '" + schema
                + "' AND table_type = 'BASE TABLE' AND table_name NOT IN ('undo_log', 'flyway_schema_history') ORDER BY 1"))
                .containsExactlyElementsOf(TABLES);
        for (String table : TABLES) {
            assertThat(columns(table)).as(table)
                    .contains("id", "code", "description", "create_time", "create_by", "update_time", "update_by", "is_deleted",
                            "label", "sorting", "version", "tenant_id", "create_dept")
                    .doesNotContain("create_date_time", "modify_date_time", "create_name", "modify_name", "is_delete", "creator", "updater");
        }
        assertThat(number("SELECT count(*) FROM information_schema.columns WHERE table_schema = '" + schema
                + "' AND column_name = 'create_dept' AND data_type = 'character varying' AND character_maximum_length = 64"
                + " AND is_nullable = 'YES'")).isEqualTo(TABLES.size());
        assertThat(number("SELECT count(*) FROM information_schema.tables WHERE table_schema = '" + schema
                + "' AND table_name = 'ddl_history'")).isZero();
        assertThat(number("SELECT count(*) FROM information_schema.tables WHERE table_schema = '" + schema
                + "' AND table_name = 'undo_log'")).isEqualTo(1);
    }

    @Test
    void createDeptIsAddedWithoutDroppingAnyColumnOrRow() throws Exception {
        migrate("3");
        execute("INSERT INTO auth_user_role(id, user_id, role_id, tenant_id, sorting, label) VALUES ('fixture-kept', 'u', 'r', 't', 7, 'synthetic')");
        migrate(null);
        assertThat(appliedVersions()).containsExactly("1", "2", "3", "4", "5");
        assertThat(number("SELECT count(*) FROM auth_user_role WHERE id = 'fixture-kept' AND sorting = 7 AND label = 'synthetic'"
                + " AND create_dept IS NULL")).isEqualTo(1);
        assertThat(strings("SELECT col_description(('" + schema + ".auth_user_role')::regclass, ordinal_position::int)"
                + " FROM information_schema.columns WHERE table_schema = '" + schema
                + "' AND table_name = 'auth_user_role' AND column_name = 'create_dept'")).containsExactly("创建部门ID");
    }

    @Test
    void everyEntityFieldMapsToAColumnOfItsTable() throws Exception {
        migrate(null);
        var assistant = new MapperBuilderAssistant(new MybatisConfiguration(), "entity-columns");
        for (Class<?> entity : EntityBaseFieldsTest.ENTITIES) {
            TableInfo info = TableInfoHelper.initTableInfo(assistant, entity);
            List<String> mapped = new ArrayList<>(List.of(info.getKeyColumn()));
            info.getFieldList().forEach(field -> mapped.add(field.getColumn()));
            assertThat(columns(info.getTableName())).as(entity.getSimpleName()).containsAll(mapped);
        }
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
