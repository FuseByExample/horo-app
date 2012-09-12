/*
 * Copyright 2012 FuseSource
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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