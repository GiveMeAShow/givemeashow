package giveme.common.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

public abstract class IDao<T>
{
	protected String		TABLE_NAME;
	public static Logger	LOGGER;

	@Inject
	protected JdbcTemplate	jdbcTemplate;

	public List<T> list()
	{
		LOGGER.info("Listing shows.");

		final List<T> resultList = new ArrayList<T>();
		final String query = "select * from " + TABLE_NAME;

		List<Map<String, Object>> rows = jdbcTemplate.queryForList(query);
		for (Map<String, Object> row : rows)
		{
			resultList.add(createObjectFromRows(row));
		}

		LOGGER.info(resultList.size() + " " + TABLE_NAME + " found.");

		return resultList;
	}

	public abstract T createObjectFromRows(Map<String, Object> row);

	public abstract void save(final T toSave);

	public abstract T createObjectFromResultSet(ResultSet rs) throws SQLException;

	public class MyObjectMapper implements RowMapper<T>
	{
		public T mapRow(final ResultSet rs, final int rowNum) throws SQLException
		{
			return createObjectFromResultSet(rs);
		}
	}
}
