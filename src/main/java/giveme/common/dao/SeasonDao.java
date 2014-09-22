package giveme.common.dao;

import giveme.common.beans.Season;
import giveme.services.JdbcConnector;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import static giveme.common.dao.SharedConstants.DB_NAME;

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
		connection = JdbcConnector.getConnection();
		try
		{
			final String query = "insert into " + TABLE_NAME + " (name, position, icon_url, show_id) "
					+ "VALUES (?, ?, ?, ?)";
			final PreparedStatement statement = connection.prepareStatement(
					query, Statement.RETURN_GENERATED_KEYS);
			statement.setString(1, toSave.getName());
			statement.setInt(2, toSave.getPosition());
			statement.setString(3, toSave.getIconUrl());
			statement.setInt(4, toSave.getShowId());
			statement.executeUpdate();
			final ResultSet idresult = statement.getGeneratedKeys();
			if (idresult.next() && idresult != null)
			{
				toSave.setId(idresult.getInt(1));
			}
			connection.close();
		}
		catch (final Exception e)
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * List the seasons associated to a show
	 * @param showId
	 * @return
	 */
	public List<Season> listByShowId(final int showId)
	{
		connection = JdbcConnector.getConnection();
		final List<Season> seasonList = new ArrayList<Season>();
		try
		{
			final String query = "select * from " + TABLE_NAME + " WHERE show_id = ?";
			final PreparedStatement statement = connection.prepareStatement(query);
			statement.setInt(1, showId);
			final ResultSet rs = statement.executeQuery();
			while (rs.next())
			{
				seasonList.add(createObjectFromResultSet(rs));
			}
			connection.close();
		}
		catch (final Exception e)
		{
			e.printStackTrace();
		}
		return seasonList;
	}
	
	public Season findById(final Integer seasonId)
	{
		connection = JdbcConnector.getConnection();
		Season season = new Season();
		try
		{
			final String query = "select * from " + TABLE_NAME + " WHERE id = ?";
			final PreparedStatement statement = connection.prepareStatement(query);
			statement.setInt(1, seasonId);
			final ResultSet rs = statement.executeQuery();
			while (rs.next())
			{
				season = createObjectFromResultSet(rs);
			}
			connection.close();
		}
		catch (final Exception e)
		{
			e.printStackTrace();
		}
		return season;
	}
	
	public Season findByName(final String name)
	{
		connection = JdbcConnector.getConnection();
		Season season = null;
		try
		{
			final String query = "select * from " + TABLE_NAME + " WHERE name = ?";
			final PreparedStatement statement = connection.prepareStatement(query);
			statement.setString(1, name);
			final ResultSet rs = statement.executeQuery();
			while (rs.next())
			{
				season = createObjectFromResultSet(rs);
			}
		}
		catch (final Exception e)
		{
			e.printStackTrace();
		}
		return season;
	}
	
	//TODO change ici le nom de colonnes
	public void update(final Season season)
	{
		connection = JdbcConnector.getConnection();
		try
		{
			final String query = "update " + TABLE_NAME
					+ " set name = ?, icon_url = ?, position = ?, showId = ? where id = ?";
			final PreparedStatement statement = connection.prepareStatement(query);
			statement.setString(1, season.getName());
			statement.setString(2, season.getIconUrl());
			statement.setInt(3, season.getPosition());
			statement.setInt(4, season.getShowId());
			statement.setInt(5, season.getId());
			statement.executeUpdate();
			connection.close();
		}
		catch (final Exception e)
		{
			e.printStackTrace();
		}
		
	}
}
