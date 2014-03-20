package giveme.common.dao;

import static giveme.common.dao.SharedConstants.DB_NAME;
import giveme.common.beans.Show;
import giveme.services.JdbcConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Component
@Repository
public class ShowDao{
	private final String TABLE_NAME = DB_NAME + ".show";
	private Connection connection;
	public static Logger LOGGER = Logger.getLogger(ShowDao.class
	        .getName());
	
	/**
	 * Get all the show present in the DB.
	 * @return a List<Show>
	 */
	public List<Show> list()
	{
		LOGGER.info("Listing shows.");
		connection = JdbcConnector.getConnection();
		
		final List<Show> showList = new ArrayList<Show>();
		try
		{
			final String query = "select * from " + TABLE_NAME;
			final ResultSet rs = connection.createStatement().executeQuery(
			        query);
			while (rs.next())
			{
				final Show movie = createShowFromResultSet(rs);
				showList.add(movie);
			}
			connection.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		LOGGER.info(showList.size() + " shows found.");
		return showList;
	}
	
	/**
	 * Get all the show present in the DB.
	 * @return a List<Show>
	 */
	public List<String> listNames()
	{
		LOGGER.info("Listing shows.");
		connection = JdbcConnector.getConnection();
		
		final List<String> showList = new ArrayList<String>();
		try
		{
			final String query = "select name from " + TABLE_NAME;
			final ResultSet rs = connection.createStatement().executeQuery(
			        query);
			while (rs.next())
			{
				final String movieName = rs.getString("name");
				showList.add(movieName);
			}
			connection.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		LOGGER.info(showList.size() + " shows found.");
		return showList;
	}
	
	/**
	 * Save a new show.
	 * @param show
	 */
	public void save(Show show)
	{
		LOGGER.info("Saving a new Show");
		connection = JdbcConnector.getConnection();
		try
		{
			final String query = "insert into " + TABLE_NAME + " (name, icon_url) "
					+ "VALUES (?, ?)";
			final PreparedStatement statement = connection.prepareStatement(
			        query, Statement.RETURN_GENERATED_KEYS);
			statement.setString(1, show.getName());
			statement.setString(2, show.getIconUrl());
			statement.executeUpdate();
			ResultSet idresult = statement.getGeneratedKeys();
			if (idresult.next() && idresult != null)
			{
				show.setId(idresult.getInt(1));
			}
			connection.close();
		}
		catch (Exception e)
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
	private Show createShowFromResultSet(ResultSet rs) throws SQLException {
		Show show = new Show();
		show.setId(rs.getInt("id"));
		show.setIconUrl(rs.getString("icon_url"));
		show.setName(rs.getString("name"));
		return show;
	}
	
	/**
	 * Update a Show
	 * @param s
	 */
	public void update(Show s)
	{
		connection = JdbcConnector.getConnection();
		try
		{
			final String query = "update " + TABLE_NAME + " set name = ?, icon_url = ? where id = ?";
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setString(1, s.getName());
			statement.setString(2, s.getIconUrl());
			statement.setInt(3, s.getId());
			statement.executeUpdate();
			connection.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * Delete a Show
	 * @param s
	 */
	public void delete(Show s) {
		connection =JdbcConnector.getConnection();
		LOGGER.info("Removing show " + s.getName());
		try
		{
			final String query = "delete from " + TABLE_NAME + " where id = ?";
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setInt(1, s.getId());
			statement.executeUpdate();
			connection.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * Find a show by it's id
	 * @param showId
	 * @return
	 */
	public Show findById(int showId) {
		connection = JdbcConnector.getConnection();
		Show show = new Show();
		try
		{
			final String query = "select * from " + TABLE_NAME + " WHERE id = ?";
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setInt(1, showId);
			ResultSet rs = statement.executeQuery();
			while (rs.next())
			{
				show = createShowFromResultSet(rs);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return show;
	}

	public Show findByName(String showName) {
		connection = JdbcConnector.getConnection();
		Show show = new Show();
		try
		{
			final String query = "select * from " + TABLE_NAME + " WHERE name = ?";
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setString(1, showName);
			ResultSet rs = statement.executeQuery();
			while (rs.next())
			{
				show = createShowFromResultSet(rs);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return show;
	}
}
