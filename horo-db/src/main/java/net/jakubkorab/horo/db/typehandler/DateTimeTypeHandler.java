package net.jakubkorab.horo.db.typehandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedTypes;
import org.apache.ibatis.type.TypeHandler;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@MappedTypes(DateTime.class)
public class DateTimeTypeHandler implements TypeHandler {
	private Logger log = LoggerFactory.getLogger(this.getClass());

	@Override
	public Object getResult(ResultSet rs, String columnName) throws SQLException {
		String unparsedDate = rs.getString(columnName);
		log.info("parsing date {}", unparsedDate);
		return new DateTime();
	}

	@Override
	public Object getResult(CallableStatement cs, int columnIndex) throws SQLException {
		throw new UnsupportedOperationException("getResult(CallableStatement cs, int columnIndex)");
	}

	@Override
	public void setParameter(PreparedStatement ps, int i, Object parameter, JdbcType jdbcType) throws SQLException {
		throw new UnsupportedOperationException("setParameter(PreparedStatement ps, int i, Object parameter, JdbcType jdbcType)");
	}

}
