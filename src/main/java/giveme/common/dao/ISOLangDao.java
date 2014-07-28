package giveme.common.dao;

import static giveme.common.dao.SharedConstants.DB_NAME;
import giveme.common.beans.ISOLang;
import giveme.services.JdbcConnector;

import java.sql.PreparedStatement;
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
	public void save(ISOLang toSave)
	{
		LOGGER.info("Saving a new Show");
		connection = JdbcConnector.getConnection();
		try
		{
			final String query = "insert into " + TABLE_NAME + " (lang_iso, lang_name, lang_flag_url) "
					+ "VALUES (?, ?, ?)";
			final PreparedStatement statement = connection.prepareStatement(query);
			statement.setString(1, toSave.getIso());
			statement.setString(2, toSave.getLanguage());
			statement.setString(3, toSave.getFlagUrl());
			statement.executeUpdate();
			connection.close();
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public ISOLang createObjectFromResultSet(ResultSet rs) throws SQLException
	{
		ISOLang lang = new ISOLang();
		lang.setLanguage(rs.getString("lang_name"));
		lang.setFlagUrl(rs.getString("lang_flag_url"));
		lang.setIso(rs.getString("lang_iso"));
		return lang;
	}

	public ISOLang findByISO(String iso)
	{
		connection = JdbcConnector.getConnection();
		ISOLang language = new ISOLang();
		try
		{
			final String query = "select * from " + TABLE_NAME + " where lang_iso.lang_iso = ?";
			final PreparedStatement statement = connection.prepareStatement(query);
			statement.setString(1, iso);
			ResultSet rs = statement.executeQuery();
			while (rs.next())
			{
				language.setIso(rs.getString("lang_iso"));
				language.setLanguage(rs.getString("lang_name"));
				language.setFlagUrl(rs.getString("lang_flag_url"));
			}
			connection.close();

		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return language;
	}
}