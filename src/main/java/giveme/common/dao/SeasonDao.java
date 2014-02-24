package giveme.common.dao;

import static giveme.common.dao.SharedConstants.DB_NAME;
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

@Component
@Repository
public class SeasonDao extends IDao<Season>{
	public SeasonDao() {
		TABLE_NAME = DB_NAME + ".season";
		LOGGER = Logger.getLogger(SeasonDao.class.getName());
	}

	@Override
	public Season createObjectFromResultSet(ResultSet rs) throws SQLException{
		Season season = new Season();
		season.setId(rs.getInt("id"));
		season.setIconUrl(rs.getString("icon_url"));
		season.setName(rs.getString("name"));
		season.setShowId(rs.getInt("show_id"));
		season.setPosition(rs.getInt("position"));
		return null;
	}

	/**
	 * Save a new season.
	 */
	@Override
	public void save(Season toSave) {
		LOGGER.info("Saving a new Show");
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
			ResultSet idresult = statement.getGeneratedKeys();
			if (idresult.next() && idresult != null)
			{
				toSave.setId(idresult.getInt(1));
			}
			connection.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * List the seasons associated to a show
	 * @param showId
	 * @return
	 */
	public List<Season> listByShowId(final int showId) {
		connection = JdbcConnector.getConnection();
		List<Season> seasonList = new ArrayList<Season>();
		try
		{
			final String query = "select * from " + TABLE_NAME + " WHERE show_id = ?";
			final PreparedStatement statement = connection.prepareStatement(query);
			statement.setInt(1, showId);
			ResultSet rs = statement.executeQuery();
			while (rs.next())
			{
				seasonList.add(createObjectFromResultSet(rs));
			}
			connection.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return seasonList;
	}
}
