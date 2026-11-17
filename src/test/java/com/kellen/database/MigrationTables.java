package com.kellen.database;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 从服务自己的迁移文件取 H2（PostgreSQL 模式）能执行的建表语句：V1 的建表，再按版本顺序套用之后各版的列改名与新增列。
 *
 * <p>只供不连 PostgreSQL 的 HTTP 集成测试用；真实结构由 {@code PostgresqlSchemaLiveTest} 在 PostgreSQL 上迁移核对。</p>
 */
public final class MigrationTables {

    private static final Pattern VERSION = Pattern.compile("V(\\d+)__.+\\.sql");
    private static final Pattern RENAME = Pattern.compile("ALTER TABLE (\\w+) RENAME COLUMN (\\w+) TO (\\w+);");
    private static final Pattern ADD = Pattern.compile("ALTER TABLE (\\w+) ADD COLUMN ([^;]+);");

    private MigrationTables() {
    }

    /**
     * 取一张表在 H2 上可执行的建表语句。
     *
     * @param table 表名
     * @return 去掉 ICU 排序规则、已套用之后各版改名与新增列的建表语句
     */
    public static String createTable(String table) {
        String baseline = read("db/migration/V1__baseline.sql");
        int start = baseline.indexOf("CREATE TABLE IF NOT EXISTS " + table + " (");
        if (start < 0) {
            throw new IllegalArgumentException("V1 里没有这张表：" + table);
        }
        String ddl = baseline.substring(start, baseline.indexOf(";", start) + 1).replace(" COLLATE fleet_general_ci", "");
        for (String migration : laterMigrations()) {
            String sql = read("db/migration/" + migration);
            Matcher rename = RENAME.matcher(sql);
            while (rename.find()) {
                if (rename.group(1).equals(table)) {
                    ddl = ddl.replaceAll("\\b" + rename.group(2) + "\\b", rename.group(3));
                }
            }
            Matcher add = ADD.matcher(sql);
            while (add.find()) {
                if (add.group(1).equals(table)) {
                    int end = ddl.lastIndexOf(");");
                    ddl = ddl.substring(0, end).stripTrailing() + ",\n    "
                            + add.group(2).replace(" COLLATE fleet_general_ci", "") + "\n" + ddl.substring(end);
                }
            }
        }
        return ddl;
    }

    /** V1 之后的迁移文件名，按版本号从小到大。 */
    private static List<String> laterMigrations() {
        try {
            Resource[] resources = new PathMatchingResourcePatternResolver().getResources("classpath:db/migration/V*__*.sql");
            return Arrays.stream(resources)
                    .map(Resource::getFilename)
                    .filter(name -> name != null && VERSION.matcher(name).matches())
                    .sorted(Comparator.comparingInt(MigrationTables::version))
                    .filter(name -> version(name) > 1)
                    .toList();
        } catch (IOException failure) {
            throw new UncheckedIOException(failure);
        }
    }

    private static int version(String name) {
        Matcher matcher = VERSION.matcher(name);
        if (!matcher.matches()) {
            throw new IllegalArgumentException("迁移文件名不合规：" + name);
        }
        return Integer.parseInt(matcher.group(1));
    }

    private static String read(String path) {
        try {
            return new ClassPathResource(path).getContentAsString(StandardCharsets.UTF_8);
        } catch (IOException failure) {
            throw new UncheckedIOException(failure);
        }
    }
}
