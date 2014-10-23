package giveme.common.dao.mapper;

import giveme.common.beans.Show;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class ShowMapper implements RowMapper<Show>
{
	public Show mapRow(ResultSet rs, int rowNum) throws SQLException
	{
		final Show show = new Show();
		show.setId(rs.getInt("id"));
		show.setIconUrl(rs.getString("icon_url"));
		show.setName(rs.getString("name"));
		return show;
	}
}
