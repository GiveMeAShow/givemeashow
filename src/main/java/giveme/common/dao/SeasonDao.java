package giveme.common.dao;

import static giveme.common.dao.SharedConstants.DB_NAME;
import giveme.common.beans.Season;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Component
@Repository
public class SeasonDao extends IDao<Season>
{
	public SeasonDao()
	{
		TABLE_NAME = DB_NAME + ".season";
		LOGGER = Logger.getLogger(SeasonDao.class.getName());
	}

	@Override
	public Season createObjectFromResultSet(final ResultSet rs) throws SQLException
	{
		final Season season = new Season();
		season.setId(rs.getInt("id"));
		season.setIconUrl(rs.getString("icon_url"));
		season.setName(rs.getString("name"));
		season.setShowId(rs.getInt("show_id"));
		season.setPosition(rs.getInt("position"));
		return season;
	}

	/**
	 * Save a new season.
	 */
	@Override
	public void save(final Season toSave)
	{
		try
		{
			final String query = "insert into " + TABLE_NAME + " (name, position, icon_url, show_id) "
					+ "VALUES (?, ?, ?, ?)";
			LOGGER.info("query : " + "insert into " + TABLE_NAME + " (name, position, icon_url, show_id) " + "VALUES ("
					+ toSave.getName() + ", " + toSave.getPosition() + ", " + toSave.getIconUrl() + ", "
					+ toSave.getShowId() + ")");
			KeyHolder keyHolder = new GeneratedKeyHolder();
			jdbcTemplate.update(new PreparedStatementCreator()
			{

				public PreparedStatement createPreparedStatement(Connection con) throws SQLException
				{
					PreparedStatement ps = con.prepareStatement(query, new String[]
					{ "name", "position", "icon_url", "show_id" });
					ps.setString(1, toSave.getName());
					ps.setInt(2, toSave.getPosition());
					ps.setString(3, toSave.getIconUrl());
					ps.setInt(4, toSave.getShowId());
					return ps;
				}
			}, keyHolder);
			toSave.setId(keyHolder.getKey().intValue());
		} catch (final Exception e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * List the seasons associated to a show
	 * 
	 * @param showId
	 * @return
	 */
	public List<Season> listByShowId(final int showId)
	{
		List<Season> seasonList = new ArrayList<Season>();
		try
		{
			final String query = "select * from " + TABLE_NAME + " WHERE show_id = ?";
			seasonList = jdbcTemplate.query(query, new Object[]
			{ showId }, new MyObjectMapper());
		} catch (final Exception e)
		{
			e.printStackTrace();
		}
		return seasonList;
	}

	public Season findById(final Integer seasonId)
	{
		Season season = new Season();
		try
		{
			final String query = "select * from " + TABLE_NAME + " WHERE id = ?";
			jdbcTemplate.queryForObject(query, new MyObjectMapper(), seasonId);
		} catch (final Exception e)
		{
			e.printStackTrace();
		}
		return season;
	}

	public Season findByName(final String name)
	{
		Season season = null;
		try
		{
			final String query = "select * from " + TABLE_NAME + " WHERE name = ?";
			season = jdbcTemplate.queryForObject(query, new MyObjectMapper(), name);
		} catch (final EmptyResultDataAccessException e)
		{
			return null;
		}
		return season;
	}

	// TODO change ici le nom de colonnes
	public void update(final Season season)
	{
		try
		{
			final String query = "update " + TABLE_NAME
					+ " set name = ?, icon_url = ?, position = ?, show_Id = ? where id = ?";
			jdbcTemplate.update(query, new Object[]
			{ season.getName(), season.getPosition(), season.getIconUrl(), season.getShowId(), season.getId() });
		} catch (final Exception e)
		{
			e.printStackTrace();
		}

	}

	public Season findBy(String name, int showId)
	{
		Season season = null;
		LOGGER.info("finding season " + " name " + name + " title " + showId);
		try
		{
			final String query = "select * from " + TABLE_NAME + " WHERE name = ? AND show_id = ?";
			season = jdbcTemplate.queryForObject(query, new Object[]
			{ name, showId }, new MyObjectMapper());
		} catch (final EmptyResultDataAccessException e)
		{
			return null;
		}
		return season;
	}

	@Override
	public Season createObjectFromRows(Map<String, Object> row)
	{
		// TODO Auto-generated method stub
		return null;
	}
}
