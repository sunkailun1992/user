package com.kellen.database;

import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 从服务自己的迁移文件取 H2（PostgreSQL 模式）能执行的建表语句：V1 的建表，加上 V3 的审计列改名与 V4 新增的创建部门列。
 *
 * <p>只供不连 PostgreSQL 的 HTTP 集成测试用；真实结构由 {@code PostgresqlSchemaLiveTest} 在 PostgreSQL 上迁移核对。</p>
 */
public final class MigrationTables {

    private static final Pattern RENAME = Pattern.compile("ALTER TABLE (\\w+) RENAME COLUMN (\\w+) TO (\\w+);");
    private static final Pattern ADD = Pattern.compile("ALTER TABLE (\\w+) ADD COLUMN ([^;]+);");

    private MigrationTables() {
    }

    /**
     * 取一张表在 H2 上可执行的建表语句。
     *
     * @param table 表名
     * @return 去掉 ICU 排序规则、审计列已改名、已补新增列的建表语句
     */
    public static String createTable(String table) {
        String baseline = read("db/migration/V1__baseline.sql");
        int start = baseline.indexOf("CREATE TABLE IF NOT EXISTS " + table + " (");
        if (start < 0) {
            throw new IllegalArgumentException("V1 里没有这张表：" + table);
        }
        String ddl = baseline.substring(start, baseline.indexOf(";", start) + 1).replace(" COLLATE fleet_general_ci", "");
        Matcher rename = RENAME.matcher(read("db/migration/V3__rename_audit_columns.sql"));
        while (rename.find()) {
            if (rename.group(1).equals(table)) {
                ddl = ddl.replaceAll("\\b" + rename.group(2) + "\\b", rename.group(3));
            }
        }
        Matcher add = ADD.matcher(read("db/migration/V4__add_create_dept.sql"));
        while (add.find()) {
            if (add.group(1).equals(table)) {
                int end = ddl.lastIndexOf(");");
                ddl = ddl.substring(0, end).stripTrailing() + ",\n    " + add.group(2).replace(" COLLATE fleet_general_ci", "")
                        + "\n" + ddl.substring(end);
            }
        }
        return ddl.replace(" COLLATE fleet_general_ci", "");
    }

    private static String read(String path) {
        try {
            return new ClassPathResource(path).getContentAsString(StandardCharsets.UTF_8);
        } catch (IOException failure) {
            throw new UncheckedIOException(failure);
        }
    }
}
