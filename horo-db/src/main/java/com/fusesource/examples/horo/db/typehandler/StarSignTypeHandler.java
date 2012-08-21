package com.fusesource.examples.horo.db.typehandler;

import com.fusesource.examples.horo.model.StarSign;
import org.apache.commons.lang.Validate;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedTypes;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@MappedTypes(StarSign.class)
public class StarSignTypeHandler extends BaseTypeHandler<StarSign> {

    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int i, StarSign starSign, JdbcType jdbcType) throws SQLException {
        Validate.notNull(starSign, "starSign is null");
        if ((jdbcType == null) || (jdbcType.equals(JdbcType.VARCHAR))) {
            preparedStatement.setString(i, starSign.getName());
        } else {
            throw new UnsupportedOperationException("Unable to convert StarSign to " + jdbcType.toString());
        }
    }

    @Override
    public StarSign getNullableResult(ResultSet rs, String columnName) throws SQLException {
        String name = rs.getString(columnName);
        return (name == null) ? null : StarSign.getInstance(name);
    }

    @Override
    public StarSign getNullableResult(ResultSet rs, int column) throws SQLException {
        String name = rs.getString(column);
        return (name == null) ? null : StarSign.getInstance(name);
    }

    @Override
    public StarSign getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
        String name = callableStatement.getString(i);
        return (name == null) ? null : StarSign.getInstance(name);
    }

}