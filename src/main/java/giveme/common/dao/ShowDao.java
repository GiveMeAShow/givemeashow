package giveme.common.dao;

import static giveme.common.dao.SharedConstants.DB_NAME;
import giveme.common.beans.Show;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Component
@Repository
public class ShowDao extends IDao<Show>
{
	public ShowDao()
	{
		LOGGER = Logger.getLogger(ShowDao.class.getName());
		TABLE_NAME = DB_NAME + ".show";
	}

	/**
	 * Get all the show present in the DB.
	 * 
	 * @return a List<Show>
	 */
	public List<String> listNames()
	{
		LOGGER.info("Listing shows.");

		final String query = "select name from " + TABLE_NAME;
		return jdbcTemplate.queryForList(query, String.class);
	}

	/**
	 * Save a new show.
	 * 
	 * @param show
	 */
	@Override
	public void save(final Show show)
	{
		LOGGER.info("Saving a new Show");
		try
		{
			final String query = "insert into " + TABLE_NAME + " (name, icon_url) " + "VALUES (?, ?)";

			KeyHolder keyHolder = new GeneratedKeyHolder();

			jdbcTemplate.update(new PreparedStatementCreator()
			{

				public PreparedStatement createPreparedStatement(Connection con) throws SQLException
				{
					PreparedStatement ps = con.prepareStatement(query, new String[]
					{ "name", "icon_url" });
					ps.setString(1, show.getName());
					ps.setString(2, show.getIconUrl());
					return ps;
				}
			}, keyHolder);

		} catch (final Exception e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @param rs
	 * @return a new Show
	 * @throws SQLException
	 */
	public Show createShowFromResultSet(final ResultSet rs) throws SQLException
	{
		final Show show = new Show();
		show.setId(rs.getInt("id"));
		show.setIconUrl(rs.getString("icon_url"));
		show.setName(rs.getString("name"));
		return show;
	}

	/**
	 * Update a Show
	 * 
	 * @param s
	 */
	public void update(final Show s)
	{
		try
		{
			final String query = "update " + TABLE_NAME + " set name = ?, icon_url = ? where id = ?";

			jdbcTemplate.update(query, new Object[]
			{ s.getName(), s.getIconUrl(), s.getId() });
		} catch (final Exception e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * Delete a Show
	 * 
	 * @param s
	 */
	public void delete(final Show s)
	{
		LOGGER.info("Removing show " + s.getName());
		try
		{
			final String query = "delete from " + TABLE_NAME + " where id = ?";
			jdbcTemplate.update(query);
		} catch (final Exception e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * Find a show by it's id
	 * 
	 * @param showId
	 * @return
	 */
	public Show findById(final int showId)
	{
		Show show = null;
		try
		{
			final String query = "select * from " + TABLE_NAME + " WHERE id = ?";
			show = jdbcTemplate.queryForObject(query, new Object[]
			{ showId }, new MyObjectMapper());
		} catch (final Exception e)
		{
			e.printStackTrace();
		}
		return show;
	}

	public Show findByName(final String showName)
	{
		Show show = null;
		try
		{
			final String query = "select * from " + TABLE_NAME + " WHERE name = ?";
			show = jdbcTemplate.queryForObject(query, new Object[]
			{ showName }, new MyObjectMapper());
		} catch (final EmptyResultDataAccessException e)
		{
			return null;
		}
		return show;
	}

	public JdbcTemplate getJdbcTemplate()
	{
		return jdbcTemplate;
	}

	public void setJdbcTemplate(final JdbcTemplate jdbcTemplate)
	{
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public Show createObjectFromResultSet(final ResultSet rs) throws SQLException
	{
		final Show show = new Show();
		show.setId(rs.getInt("id"));
		show.setIconUrl(rs.getString("icon_url"));
		show.setName(rs.getString("name"));
		return show;
	}

	@Override
	public Show createObjectFromRows(Map<String, Object> row)
	{
		// TODO Auto-generated method stub
		return null;
	}
}
