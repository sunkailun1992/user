package com.kellen.auth.typehandler;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.kellen.auth.entity.AuthExternalIdentity;
import org.apache.ibatis.type.JdbcType;
import org.junit.jupiter.api.Test;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Types;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/** PostgreSQL JSONB 绑定只作用于外部身份元数据，不改变普通字符串列。 */
class PostgresqlJsonStringTypeHandlerTest {

    @Test
    void writesJsonUsingOtherRatherThanVarchar() throws Exception {
        var statement = mock(PreparedStatement.class);
        new PostgresqlJsonStringTypeHandler().setNonNullParameter(statement, 2, "{\"synthetic\":true}", JdbcType.OTHER);
        verify(statement).setObject(2, "{\"synthetic\":true}", Types.OTHER);
    }

    @Test
    void preservesJsonStringAndSqlNullOnEveryReadPath() throws Exception {
        var handler = new PostgresqlJsonStringTypeHandler();
        var resultSet = mock(ResultSet.class);
        var callable = mock(CallableStatement.class);
        when(resultSet.getString("metadata_json")).thenReturn("{\"synthetic\":true}");
        when(resultSet.getString(2)).thenReturn(null);
        when(callable.getString(3)).thenReturn("{}");
        assertThat(handler.getNullableResult(resultSet, "metadata_json")).isEqualTo("{\"synthetic\":true}");
        assertThat(handler.getNullableResult(resultSet, 2)).isNull();
        assertThat(handler.getNullableResult(callable, 3)).isEqualTo("{}");
    }

    @Test
    void bindsOnlyTheExplicitJsonPropertyAndEnablesResultMapping() throws Exception {
        assertThat(AuthExternalIdentity.class.getAnnotation(TableName.class).autoResultMap()).isTrue();
        TableField field = AuthExternalIdentity.class.getDeclaredField("metadataJson").getAnnotation(TableField.class);
        assertThat(field.typeHandler()).isEqualTo(PostgresqlJsonStringTypeHandler.class);
    }
}
