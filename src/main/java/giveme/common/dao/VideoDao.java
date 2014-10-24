package giveme.common.dao;

import static giveme.common.dao.SharedConstants.DB_NAME;
import giveme.common.beans.Video;
import giveme.common.dao.mapper.VideoMapper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Component
@Repository
public class VideoDao extends IDao<Video>
{
	@Autowired
	public ISOLangDao	isolangDao;

	public VideoDao()
	{
		TABLE_NAME = DB_NAME + ".video";
		LOGGER = Logger.getLogger(VideoDao.class.getName());
	}

	@Override
	public Video createObjectFromResultSet(final ResultSet rs) throws SQLException
	{
		final Video video = new Video();
		video.setId(rs.getInt("id"));
		video.setTitle(rs.getString("title"));
		video.setLanguageIso(isolangDao.findByISO(rs.getString("lang_iso")));
		;
		// season.setLanguage(rs.getString("name"));
		video.setShowId(rs.getInt("show_id"));
		video.setSeasonId(rs.getInt("season_id"));
		video.setPosition(rs.getInt("position"));
		video.setTransition(rs.getBoolean("is_transition"));
		video.setRelativePath(rs.getString("relative_path"));
		video.setViewed(rs.getLong("viewed"));
		video.setUrl(rs.getString("url"));
		video.setStartOutroTime(rs.getDouble("start_outro_time"));
		video.setEndIntroTime(rs.getDouble("end_intro_time"));
		video.setValidated(rs.getBoolean("validated"));
		return video;
	}

	/**
	 * Save a new season.
	 */
	@Override
	public void save(final Video toSave)
	{
		LOGGER.info("Saving a new Video");
		try
		{
			final String query = "insert into " + TABLE_NAME
					+ " (title, season_id, lang_iso, position, is_transition, relative_path,"
					+ " viewed, url, show_id, end_intro_time, start_outro_time, validated) "
					+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
			KeyHolder keyHolder = new GeneratedKeyHolder();

			jdbcTemplate.update(new PreparedStatementCreator()
			{

				public PreparedStatement createPreparedStatement(Connection con) throws SQLException
				{
					PreparedStatement ps = con.prepareStatement(query, new String[]
					{ "title", "season_id", "lang_iso", "position", "is_transition", "relative_path", "viewed", "url",
							"show_id", "end_intro_time", "start_outro_time", "validated" });
					ps.setString(1, toSave.getTitle());
					ps.setInt(2, toSave.getSeasonId());
					ps.setString(3, "fr");
					ps.setInt(4, toSave.getPosition());
					ps.setBoolean(5, toSave.getIsTransition());
					ps.setString(6, toSave.getRelativePath());
					ps.setLong(7, toSave.getViewed());
					ps.setString(8, toSave.getUrl());
					ps.setInt(9, toSave.getShowId());
					ps.setDouble(10, toSave.getEndIntroTime());
					ps.setDouble(11, toSave.getStartOutroTime());
					ps.setBoolean(12, toSave.isValidated());
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
	public List<Video> listByShowId(final int showId)
	{
		List<Video> videoList = new ArrayList<Video>();
		try
		{
			final String query = "select * from " + TABLE_NAME + " WHERE show_id = ?";
			videoList = jdbcTemplate.query(query, new Object[]
			{ showId }, new VideoMapper());
		} catch (final Exception e)
		{

		}
		return videoList;
	}

	public Video findById(final Integer videoId)
	{
		Video video = null;
		try
		{
			final String query = "select * from " + TABLE_NAME + " WHERE id = ?";
			video = jdbcTemplate.queryForObject(query, new Object[]
			{ videoId }, new MyObjectMapper());
		} catch (final Exception e)
		{
			return null;
		}
		return video;
	}

	public List<Video> findByShowAndSeasonId(final int showId, final int seasonId)
	{
		final List<Video> video = new ArrayList<Video>();
		try
		{
			final String query = "select * from " + TABLE_NAME + " WHERE show_id = ? AND season_id = ?";
			jdbcTemplate.query(query, new Object[]
			{ showId, seasonId }, new MyObjectMapper());
		} catch (final EmptyResultDataAccessException e)
		{

		}
		return video;
	}

	public Video findByRelativePath(String relativePath)
	{
		Video video = null;
		try
		{
			final String query = "select * from " + TABLE_NAME + " WHERE relative_path = ?";
			video = jdbcTemplate.queryForObject(query, new Object[]
			{ relativePath }, new MyObjectMapper());
		} catch (final EmptyResultDataAccessException e)
		{
			return null;
		}
		return video;
	}

	public Video findByShowAndSeasonIdsAndTitle(int showId, int seasonId, String title)
	{
		Video video = null;
		LOGGER.info("finding show " + showId + " seasonID " + seasonId + " title " + title);
		try
		{
			final String query = "select * from " + TABLE_NAME + " WHERE show_id = ? AND season_id = ? AND title = ?";
			video = jdbcTemplate.queryForObject(query, new Object[]
			{ showId, seasonId, title }, new MyObjectMapper());
		} catch (final EmptyResultDataAccessException e)
		{
			return null;
		}
		return video;
	}

	public List<Video> listPEnding()
	{
		List<Video> videoList = new ArrayList<Video>();
		try
		{

			final String query = "select * from " + TABLE_NAME + " WHERE validated = ?";
			List<Map<String, Object>> rows = jdbcTemplate.queryForList(query, new Object[]
			{ 0 });
			for (Map<String, Object> row : rows)
			{
				videoList.add(createObjectFromRows(row));
			}
		} catch (final Exception e)
		{

		}
		return videoList;
	}

	@Override
	public Video createObjectFromRows(Map<String, Object> row)
	{

		Video video = new Video();
		video.setTitle((String) row.get("TITLE"));
		video.setId(Integer.parseInt(String.valueOf(row.get("ID"))));
		video.setShowId(Integer.parseInt(String.valueOf(row.get("SHOW_ID"))));
		video.setSeasonId(Integer.parseInt(String.valueOf(row.get("SEASON_ID"))));
		video.setEndIntroTime(Double.parseDouble(String.valueOf(row.get("END_INTRO_TIME"))));
		video.setStartOutroTime(Double.parseDouble(String.valueOf(row.get("START_OUTRO_TIME"))));
		video.setLanguageIso(isolangDao.findByISO((String) row.get("LANG_ISO")));
		video.setPosition(Integer.parseInt(String.valueOf(row.get("POSITION"))));
		video.setValidated(Boolean.parseBoolean(String.valueOf(row.get("VALIDATED"))));
		video.setTransition(Boolean.parseBoolean(String.valueOf(row.get("IS_TRANSITION"))));
		video.setRelativePath((String) row.get("RELATIVE_PATH"));
		video.setUrl((String) row.get("URL"));
		video.setViewed(Long.parseLong((String) row.get("VIEWED")));
		return video;
	}

	public void update(Video video)
	{
		LOGGER.info("Updating video " + video.getTitle());
		jdbcTemplate
				.update("update "
						+ TABLE_NAME
						+ " set title = ?, season_id = ?, lang_iso = ?, position = ?, is_transition = ?, relative_path = ?, viewed = ?, url = ?"
						+ ", show_id = ?, end_intro_time = ?, start_outro_time = ?, validated = ? WHERE id = ?",
						new Object[]
						{ video.getTitle(), video.getSeasonId(), video.getLanguageIso().getIso(), video.getPosition(),
								video.getIsTransition(), video.getRelativePath(), video.getViewed(), video.getUrl(),
								video.getShowId(), video.getEndIntroTime(), video.getStartOutroTime(),
								video.isValidated(), video.getId() });
	}

	public List<Video> findBySeasonId(Integer seasonId)
	{
		LOGGER.info("finding video seasonID " + seasonId);
		List<Video> videoList = new ArrayList<Video>();
		try
		{

			final String query = "select * from " + TABLE_NAME + " WHERE season_id = ?";
			List<Map<String, Object>> rows = jdbcTemplate.queryForList(query, new Object[]
			{ seasonId });
			for (Map<String, Object> row : rows)
			{
				videoList.add(createObjectFromRows(row));
			}
		} catch (final Exception e)
		{

		}
		return videoList;
	}
}
