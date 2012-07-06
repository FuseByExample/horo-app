package net.jakubkorab.horo.db.typehandler;

import org.apache.commons.lang.Validate;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedTypes;
import org.joda.time.DateTime;

import java.sql.*;

@MappedTypes(DateTime.class)
public class DateTimeTypeHandler extends BaseTypeHandler {

    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int i, Object dateTimeObject, JdbcType jdbcType) throws SQLException {
        Validate.notNull(dateTimeObject, "dateTimeObject is null");
        DateTime dateTime = (DateTime) dateTimeObject;
        if (jdbcType.equals(JdbcType.DATE)) {
            preparedStatement.setDate(i, new java.sql.Date(dateTime.getMillis()));
        }   else {
            throw new UnsupportedOperationException("Unable to convert DateTime to " + jdbcType.toString());
        }
    }

    @Override
    public DateTime getNullableResult(ResultSet rs, String columnName) throws SQLException {
        Date date = rs.getDate(columnName);
        return (date == null) ? null : new DateTime(date.getTime());
    }

    @Override
    public DateTime getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
        Date date = callableStatement.getDate(i);
        return (date == null) ? null : new DateTime(date.getTime());
    }

}