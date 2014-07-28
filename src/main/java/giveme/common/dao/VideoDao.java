package giveme.common.dao;

import static giveme.common.dao.SharedConstants.DB_NAME;
import giveme.common.beans.Video;
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
public class VideoDao extends IDao<Video>
{
	public VideoDao()
	{
		TABLE_NAME = DB_NAME + ".video";
		LOGGER = Logger.getLogger(VideoDao.class.getName());
	}

	@Override
	public Video createObjectFromResultSet(ResultSet rs) throws SQLException
	{
		Video video = new Video();
		video.setId(rs.getInt("id"));
		video.setTitle(rs.getString("title"));
		// season.setLanguage(rs.getString("name"));
		video.setShowId(rs.getInt("show_id"));
		video.setPosition(rs.getInt("position"));
		video.setTransition(rs.getBoolean("is_transition"));
		video.setRelativePath(rs.getString("relative_path"));
		video.setViewed(rs.getLong("viewed"));
		video.setUrl(rs.getString(rs.getString("url")));
		video.setStartOutroTime(rs.getDouble("start_intro_time"));
		video.setEndIntroTime(rs.getDouble("start_outro_time"));
		return video;
	}

	/**
	 * Save a new season.
	 */
	@Override
	public void save(Video toSave)
	{
		LOGGER.info("Saving a new Video");
		connection = JdbcConnector.getConnection();
		try
		{
			final String query = "insert into " + TABLE_NAME
					+ " (title, season_id, lang_iso, position, is_transition, relative_path,"
					+ " viewed, url, show_id, end_intro_time, start_outro_time) "
					+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
			final PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			statement.setString(1, toSave.getTitle());
			statement.setInt(2, toSave.getSeasonId());
			statement.setString(3, toSave.getLanguageIso().getIso());
			statement.setInt(4, toSave.getPosition());
			statement.setBoolean(5, toSave.isTransition());
			statement.setString(6, toSave.getRelativePath());
			statement.setLong(7, toSave.getViewed());
			statement.setString(8, toSave.getUrl());
			statement.setLong(9, toSave.getShowId());
			statement.setDouble(10, toSave.getStartOutroTime());
			statement.setDouble(11, toSave.getEndIntroTime());
			statement.executeUpdate();
			ResultSet idresult = statement.getGeneratedKeys();
			if (idresult.next() && idresult != null)
			{
				toSave.setId(idresult.getInt(1));
			}
			connection.close();
		} catch (Exception e)
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
	public List<Video> listByShowId(final int showId)
	{
		connection = JdbcConnector.getConnection();
		List<Video> videoList = new ArrayList<Video>();
		try
		{
			final String query = "select * from " + TABLE_NAME + " WHERE show_id = ?";
			final PreparedStatement statement = connection.prepareStatement(query);
			statement.setInt(1, showId);
			ResultSet rs = statement.executeQuery();
			while (rs.next())
			{
				videoList.add(createObjectFromResultSet(rs));
			}
			connection.close();
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return videoList;
	}

	public Video findById(Integer videoId)
	{
		connection = JdbcConnector.getConnection();
		Video video = new Video();
		try
		{
			final String query = "select * from " + TABLE_NAME + " WHERE id = ?";
			final PreparedStatement statement = connection.prepareStatement(query);
			statement.setInt(1, videoId);
			ResultSet rs = statement.executeQuery();
			while (rs.next())
			{
				video = createObjectFromResultSet(rs);
			}
			connection.close();
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return video;
	}

	public List<Video> findByShowAndSeasonId(final int showId, final int seasonId)
	{
		connection = JdbcConnector.getConnection();
		List<Video> video = new ArrayList<Video>();
		try
		{
			final String query = "select * from " + TABLE_NAME + " WHERE id = ?";
			final PreparedStatement statement = connection.prepareStatement(query);
			statement.setInt(1, showId);
			statement.setInt(2, seasonId);
			ResultSet rs = statement.executeQuery();
			while (rs.next())
			{
				video.add(createObjectFromResultSet(rs));
			}
			connection.close();
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return video;
	}
}
