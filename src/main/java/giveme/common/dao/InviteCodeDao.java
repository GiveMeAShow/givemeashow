package giveme.common.dao;

import static giveme.common.dao.SharedConstants.DB_NAME;
import giveme.common.beans.InviteCode;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.stereotype.Repository;

@Repository
public class InviteCodeDao extends IDao<InviteCode>
{
	public InviteCodeDao()
	{
		TABLE_NAME = DB_NAME + ".invite_code";
		LOGGER = Logger.getLogger(InviteCodeDao.class.getName());
	}

	@Override
	public InviteCode createObjectFromRows(Map<String, Object> row)
	{
		InviteCode inviteCode = new InviteCode();
		inviteCode.setInviteCode((String) row.get("INVITE_CODE"));
		inviteCode.setUserId(Integer.parseInt((String) row.get("INVITE_CODE")));
		return inviteCode;
	}

	@Override
	public void save(final InviteCode toSave)
	{
		LOGGER.info("Saving a new Video");
		try
		{
			final String query = "insert into " + TABLE_NAME + " (invite_code, user_id) " + "VALUES (?, ?)";

			jdbcTemplate.update(new PreparedStatementCreator()
			{

				public PreparedStatement createPreparedStatement(Connection con) throws SQLException
				{
					PreparedStatement ps = con.prepareStatement(query, new String[]
					{ "invite_code", "user_id" });
					ps.setString(1, toSave.getInviteCode());
					ps.setInt(2, toSave.getUserId());
					return ps;
				}
			});

		} catch (final Exception e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public InviteCode createObjectFromResultSet(ResultSet rs) throws SQLException
	{
		InviteCode inviteCode = new InviteCode();
		inviteCode.setInviteCode(rs.getString("invite_code"));
		inviteCode.setUserId(rs.getInt("user_id"));
		return inviteCode;
	}

	public InviteCode find(String inviteCode)
	{
		InviteCode code = null;
		try
		{
			final String query = "select * from " + TABLE_NAME + " WHERE invite_code = ?";
			code = jdbcTemplate.queryForObject(query, new Object[]
			{ inviteCode }, new MyObjectMapper());
		} catch (final Exception e)
		{
			return null;
		}
		return code;
	}

	public void delete(InviteCode inviteCode)
	{
		String query = "delete from " + TABLE_NAME + " WHERE invite_code = ?";
		int rows = jdbcTemplate.update(query, new Object[]
		{ inviteCode.getInviteCode() });

		LOGGER.info(rows + " rows deleted");

	}
}
