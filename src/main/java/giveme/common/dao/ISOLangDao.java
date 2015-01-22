package giveme.common.dao;

import static giveme.common.dao.SharedConstants.DB_NAME;
import giveme.common.beans.ISOLang;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.stereotype.Repository;

@Repository
public class ISOLangDao extends IDao<ISOLang>
{
	public ISOLangDao()
	{
		TABLE_NAME = DB_NAME + ".lang_iso";
		LOGGER = Logger.getLogger(ISOLangDao.class.getName());
	}

	/**
	 * Insert a new langage into the DB. Language should be iso compliant (en,
	 * fr ...)
	 */
	@Override
	public void save(final ISOLang toSave)
	{
		LOGGER.info("Saving a new lang");
		try
		{
			final String query = "insert into " + TABLE_NAME + " (lang_iso, lang_name, lang_flag_url) "
					+ "VALUES (?, ?, ?)";

			jdbcTemplate.update(new PreparedStatementCreator()
			{

				public PreparedStatement createPreparedStatement(Connection con) throws SQLException
				{
					PreparedStatement ps = con.prepareStatement(query, new String[]
					{ "lang_iso", "lang_name", "lang_flag_url" });
					ps.setString(1, toSave.getIso());
					ps.setString(2, toSave.getLanguage());
					ps.setString(3, toSave.getFlagUrl());
					return ps;
				}
			});

		} catch (final Exception e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public ISOLang createObjectFromResultSet(final ResultSet rs) throws SQLException
	{
		final ISOLang lang = new ISOLang();
		lang.setLanguage(rs.getString("lang_name"));
		lang.setFlagUrl(rs.getString("lang_flag_url"));
		lang.setIso(rs.getString("lang_iso"));
		return lang;
	}

	public ISOLang findByISO(final String iso)
	{
		ISOLang language = null;
		try
		{
			final String query = "select * from " + TABLE_NAME + " where lang_iso.lang_iso = ?";
			language = jdbcTemplate.queryForObject(query, new Object[]
			{ iso }, new MyObjectMapper());
		} catch (final Exception e)
		{
			e.printStackTrace();
		}
		return language;
	}

	@Override
	public ISOLang createObjectFromRows(Map<String, Object> row)
	{
		// TODO Auto-generated method stub
		return null;
	}
}