package com.kellen.database;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;
import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.spring.MybatisSqlSessionFactoryBean;
import com.kellen.auth.entity.AuthExternalIdentity;
import com.kellen.auth.mapper.AuthExternalIdentityMapper;
import com.kellen.auth.typehandler.PostgresqlJsonStringTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;

import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/** PostgreSQL 实际执行、重复建表及布尔/时间/字符比较回归，不连接真实业务库。 */
@EnabledIfEnvironmentVariable(named = "FLEET_PG_TEST_URL", matches = ".+")
class PostgresqlSchemaLiveTest extends PostgresqlSchemaFixture {

    @Test
    void usernameAndResourceKeysRetainCaseAndAccentInsensitiveUniqueness() throws Exception {
        script("db/postgresql/user-schema.sql");
        execute("INSERT INTO auth_user(id, username, password, tenant_id) VALUES ('fixture-user', 'Café', 'synthetic-only', 'fixture-tenant')");
        assertThat(number("SELECT count(*) FROM auth_user WHERE username = 'CAFE' AND is_delete = false")).isEqualTo(1);
        assertThat(number("SELECT count(*) FROM auth_user WHERE username LIKE 'caf%'")).isEqualTo(1);
        assertThatThrownBy(() -> execute("INSERT INTO auth_user(id, username, password, tenant_id) VALUES ('fixture-duplicate', 'CAFE', 'synthetic-only', 'fixture-tenant')"))
                .isInstanceOf(SQLException.class).extracting("SQLState").isEqualTo("23505");
        execute("INSERT INTO auth_resource(id, name, resource_category, code, tenant_id) VALUES ('fixture-resource', 'fixture', 'BACKEND', 'API:CAFÉ', 'fixture-tenant')");
        assertThatThrownBy(() -> execute("INSERT INTO auth_resource(id, name, resource_category, code, tenant_id) VALUES ('fixture-resource-duplicate', 'fixture', 'BACKEND', 'api:cafe', 'fixture-tenant')"))
                .isInstanceOf(SQLException.class).extracting("SQLState").isEqualTo("23505");
    }

    @Test
    void bootstrapKeepsExistingTenantRoleAndPermissionsAndPreservesSha256Ids() throws Exception {
        script("db/postgresql/user-schema.sql");
        script("db/postgresql/user-bootstrap.sql");
        long resources = number("SELECT count(*) FROM auth_resource");
        execute("UPDATE auth_tenant SET name = 'synthetic-custom-tenant' WHERE id = '100'");
        execute("UPDATE auth_role SET name = 'synthetic-custom-role', data_scope = 'SELF' WHERE id = 'r_admin_100'");
        execute("UPDATE auth_resource SET name = 'synthetic-custom-resource' WHERE code = 'menu:rag:graph'");
        script("db/postgresql/user-bootstrap.sql");
        assertThat(number("SELECT count(*) FROM auth_resource")).isEqualTo(resources);
        assertThat(number("SELECT count(*) FROM auth_user")).isZero();
        assertThat(number("SELECT count(*) FROM auth_tenant WHERE id = '100' AND name = 'synthetic-custom-tenant'")).isEqualTo(1);
        assertThat(number("SELECT count(*) FROM auth_role WHERE id = 'r_admin_100' AND name = 'synthetic-custom-role' AND data_scope = 'SELF'")).isEqualTo(1);
        assertThat(number("SELECT count(*) FROM auth_resource WHERE code = 'menu:rag:graph' AND name = 'synthetic-custom-resource' AND id = 'res_500ab6edc80891bf27e75bcecd06d98c'"))
                .isEqualTo(1);
    }

    @Test
    void explicitJsonHandlerSurvivesARealMapperRoundTripAndDatabaseValidation() throws Exception {
        script("db/postgresql/user-schema.sql");
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
            identity.setIsDelete(false);
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
    void createsTheCurrentBusinessSchemaTwiceWithoutMysqlSyntax() throws Exception {
        script("db/postgresql/user-schema.sql");
        script("db/postgresql/user-schema.sql");
        assertThat(number("SELECT count(*) FROM information_schema.tables WHERE table_schema = '" + schema + "' AND table_type = 'BASE TABLE'"))
                .isEqualTo(13);
        assertThat(number("SELECT count(*) FROM information_schema.columns WHERE table_schema = '" + schema + "' AND column_name = 'is_delete' AND data_type = 'boolean'"))
                .isEqualTo(13);
        assertThat(number("SELECT count(*) FROM information_schema.columns WHERE table_schema = '" + schema + "' AND column_name = 'modify_date_time' AND data_type = 'timestamp without time zone'"))
                .isEqualTo(13);
        assertThat(number("SELECT CASE WHEN 'Café' COLLATE public.fleet_general_ci = 'CAFE' THEN 1 ELSE 0 END")).isEqualTo(1);
        assertThat(number("SELECT CASE WHEN 'Café' COLLATE public.fleet_general_ci LIKE 'caf%' THEN 1 ELSE 0 END")).isEqualTo(1);
    }
}
