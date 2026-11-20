package com.kellen.auth.support;

import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.core.config.GlobalConfig;
import com.baomidou.mybatisplus.core.toolkit.GlobalConfigUtils;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.spring.MybatisSqlSessionFactoryBean;
import com.kellen.auth.mapper.AuthDeptMapper;
import com.kellen.auth.mapper.AuthResourceMapper;
import com.kellen.auth.mapper.AuthRoleDataScopeMapper;
import com.kellen.auth.mapper.AuthRoleMapper;
import com.kellen.auth.mapper.AuthRoleResourceMapper;
import com.kellen.auth.mapper.AuthTenantMapper;
import com.kellen.auth.mapper.AuthUserMapper;
import com.kellen.auth.mapper.AuthUserRoleMapper;
import com.kellen.auth.mapper.AuthUserTenantMapper;
import com.kellen.config.mybatis.MyBatisPlusConfig;
import com.kellen.config.mybatis.MyMetaObjectHandler;
import com.kellen.database.MigrationTables;
import com.kellen.security.config.TenantProperties;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.context.ConfigurationPropertiesAutoConfiguration;
import org.springframework.boot.autoconfigure.context.PropertyPlaceholderAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.List;
import java.util.UUID;

/**
 * 认证表的 H2（PostgreSQL 模式）测试上下文：真实的 MyBatis-Plus 配置（租户插件、数据权限、自动填充）、Spring 事务与全部认证 Mapper。
 * 事务代理用类代理，与 Spring Boot 默认（{@code spring.aop.proxy-target-class=true}）一致。
 * 表结构由 {@link MigrationTables} 按 Flyway 迁移重放，不连接个人业务库。
 */
@Configuration(proxyBeanMethods = false)
@EnableTransactionManagement(proxyTargetClass = true)
@Import(MyBatisPlusConfig.class)
@ImportAutoConfiguration({PropertyPlaceholderAutoConfiguration.class, ConfigurationPropertiesAutoConfiguration.class})
public class AuthH2TestConfiguration {

    /**
     * 测试用到的认证表。
     */
    public static final List<String> TABLES = List.of("auth_tenant", "auth_user", "auth_user_tenant", "auth_user_role", "auth_role",
            "auth_role_resource", "auth_resource", "auth_dept", "auth_role_data_scope");

    /**
     * 按迁移重放建表（已存在则先删）。
     *
     * @param dataSource 数据源
     */
    public static void recreateTables(DataSource dataSource) {
        JdbcTemplate jdbc = new JdbcTemplate(dataSource);
        for (String table : TABLES) {
            jdbc.execute("DROP TABLE IF EXISTS " + table);
            jdbc.execute(MigrationTables.createTable(table));
        }
    }

    @Bean
    DataSource dataSource() {
        return new DriverManagerDataSource("jdbc:h2:mem:auth_" + UUID.randomUUID() + ";MODE=PostgreSQL;DB_CLOSE_DELAY=-1", "sa", "");
    }

    @Bean
    PlatformTransactionManager transactionManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean
    SqlSessionFactory sqlSessionFactory(DataSource dataSource, MybatisPlusInterceptor interceptor, TenantProperties tenant) throws Exception {
        MybatisConfiguration configuration = new MybatisConfiguration();
        configuration.setMapUnderscoreToCamelCase(true);
        GlobalConfig global = new GlobalConfig().setDbConfig(new GlobalConfig.DbConfig()).setMetaObjectHandler(new MyMetaObjectHandler(tenant));
        GlobalConfigUtils.setGlobalConfig(configuration, global);
        for (Class<?> mapper : List.of(AuthTenantMapper.class, AuthUserMapper.class, AuthUserTenantMapper.class, AuthUserRoleMapper.class,
                AuthRoleMapper.class, AuthRoleResourceMapper.class, AuthResourceMapper.class, AuthDeptMapper.class, AuthRoleDataScopeMapper.class)) {
            configuration.addMapper(mapper);
        }
        MybatisSqlSessionFactoryBean factory = new MybatisSqlSessionFactoryBean();
        factory.setDataSource(dataSource);
        factory.setConfiguration(configuration);
        factory.setGlobalConfig(global);
        factory.setPlugins(interceptor);
        return factory.getObject();
    }

    @Bean
    SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory factory) {
        return new SqlSessionTemplate(factory);
    }

    @Bean
    AuthTenantMapper authTenantMapper(SqlSessionTemplate template) {
        return template.getMapper(AuthTenantMapper.class);
    }

    @Bean
    AuthUserMapper authUserMapper(SqlSessionTemplate template) {
        return template.getMapper(AuthUserMapper.class);
    }

    @Bean
    AuthUserTenantMapper authUserTenantMapper(SqlSessionTemplate template) {
        return template.getMapper(AuthUserTenantMapper.class);
    }

    @Bean
    AuthUserRoleMapper authUserRoleMapper(SqlSessionTemplate template) {
        return template.getMapper(AuthUserRoleMapper.class);
    }

    @Bean
    AuthRoleMapper authRoleMapper(SqlSessionTemplate template) {
        return template.getMapper(AuthRoleMapper.class);
    }

    @Bean
    AuthRoleResourceMapper authRoleResourceMapper(SqlSessionTemplate template) {
        return template.getMapper(AuthRoleResourceMapper.class);
    }

    @Bean
    AuthResourceMapper authResourceMapper(SqlSessionTemplate template) {
        return template.getMapper(AuthResourceMapper.class);
    }

    @Bean
    AuthDeptMapper authDeptMapper(SqlSessionTemplate template) {
        return template.getMapper(AuthDeptMapper.class);
    }

    @Bean
    AuthRoleDataScopeMapper authRoleDataScopeMapper(SqlSessionTemplate template) {
        return template.getMapper(AuthRoleDataScopeMapper.class);
    }
}
