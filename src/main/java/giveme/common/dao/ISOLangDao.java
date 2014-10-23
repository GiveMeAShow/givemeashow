package giveme.common.dao;

import static giveme.common.dao.SharedConstants.DB_NAME;
import giveme.common.beans.ISOLang;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Component
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
		LOGGER.info("Saving a new Show");
		try
		{
			final String query = "insert into " + TABLE_NAME + " (lang_iso, lang_name, lang_flag_url) "
					+ "VALUES (?, ?, ?)";
			jdbcTemplate.update(query, toSave.getIso(), toSave.getLanguage(), toSave.getFlagUrl());
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
}