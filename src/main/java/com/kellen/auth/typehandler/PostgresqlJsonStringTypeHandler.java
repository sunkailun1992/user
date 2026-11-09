package com.kellen.auth.typehandler;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

/**
 * 外部身份元数据 JSONB 的显式字段处理器。
 *
 * <p>保留应用的 JSON 字符串接口，由 PostgreSQL 校验 JSON；不全局接管 String，
 * 避免普通 varchar 或 text 字段被改为 JSON 绑定。</p>
 */
public class PostgresqlJsonStringTypeHandler extends BaseTypeHandler<String> {

    /** 使用 JDBC OTHER 让 PostgreSQL 按目标 JSONB 列处理参数。 */
    @Override
    public void setNonNullParameter(PreparedStatement statement, int index, String value, JdbcType jdbcType)
            throws SQLException {
        statement.setObject(index, value, Types.OTHER);
    }

    /** 按列名读取 JSON 文本，SQL NULL 保持 null。 */
    @Override
    public String getNullableResult(ResultSet resultSet, String columnName) throws SQLException {
        return resultSet.getString(columnName);
    }

    /** 按列序号读取 JSON 文本。 */
    @Override
    public String getNullableResult(ResultSet resultSet, int columnIndex) throws SQLException {
        return resultSet.getString(columnIndex);
    }

    /** 读取可调用语句返回的 JSON 文本。 */
    @Override
    public String getNullableResult(CallableStatement statement, int columnIndex) throws SQLException {
        return statement.getString(columnIndex);
    }
}
