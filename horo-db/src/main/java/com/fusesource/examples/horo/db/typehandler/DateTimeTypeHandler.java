package com.fusesource.examples.horo.db.typehandler;

import org.apache.commons.lang.Validate;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedTypes;
import org.joda.time.DateTime;

import java.sql.*;

@MappedTypes(DateTime.class)
public class DateTimeTypeHandler extends BaseTypeHandler<DateTime> {

    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int i, DateTime dateTime, JdbcType jdbcType) throws SQLException {
        Validate.notNull(dateTime, "dateTime is null");
        if ((jdbcType == null) || (jdbcType.equals(JdbcType.DATE))) {
            preparedStatement.setDate(i, new java.sql.Date(dateTime.getMillis()));
        } else {
            throw new UnsupportedOperationException("Unable to convert DateTime to " + jdbcType.toString());
        }
    }

    @Override
    public DateTime getNullableResult(ResultSet rs, String columnName) throws SQLException {
        Date date = rs.getDate(columnName);
        return (date == null) ? null : new DateTime(date.getTime());
    }

    @Override
    public DateTime getNullableResult(ResultSet rs, int i) throws SQLException {
        Date date = rs.getDate(i);
        return (date == null) ? null : new DateTime(date.getTime());
    }

    @Override
    public DateTime getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
        Date date = callableStatement.getDate(i);
        return (date == null) ? null : new DateTime(date.getTime());
    }

}