package giveme.common.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

public abstract class IDao<T>
{
	protected String TABLE_NAME;
	public static Logger LOGGER;
	
	@Inject
	protected JdbcTemplate jdbcTemplate;
	
	public List<T> list()
	{
		LOGGER.info("Listing shows.");
		
		final List<T> resultList = new ArrayList<T>();
		final String query = "select * from " + TABLE_NAME;
		
		LOGGER.info(resultList.size() + " " + TABLE_NAME + " found.");
		return jdbcTemplate.query(query, new MyObjectMapper());
	}
	
	public abstract void save(T toSave);
	
	public abstract T createObjectFromResultSet(ResultSet rs) throws SQLException;
	
	protected class MyObjectMapper implements ParameterizedRowMapper<T>
	{
		public T mapRow(final ResultSet rs, final int rowNum) throws SQLException
		{
			return createObjectFromResultSet(rs);
		}
	}
	
}
