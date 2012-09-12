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