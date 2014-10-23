package giveme.common.dao.mapper;

import giveme.common.beans.Video;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class VideoMapper implements RowMapper<Video>
{

	public Video mapRow(ResultSet rs, int rowNum) throws SQLException
	{
		final Video video = new Video();
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
		video.setValidated(rs.getBoolean("validated"));
		return video;
	}

}
