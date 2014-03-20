package giveme.common.dao;

import static giveme.common.dao.SharedConstants.DB_NAME;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.mysql.jdbc.Statement;

import giveme.common.beans.ISOLang;
import giveme.common.beans.Season;
import giveme.services.JdbcConnector;

@Component
@Repository
public class ISOLangDao extends IDao<ISOLang>{
	public ISOLangDao() {
		TABLE_NAME = DB_NAME + ".lang_iso";
		LOGGER = Logger.getLogger(ISOLangDao.class.getName());
	}

	/**
	 * Insert a new langage into the DB.
	 * Language should be iso compliant (en, fr ...)
	 */
	@Override
	public void save(ISOLang toSave) {
		LOGGER.info("Saving a new Show");
		connection = JdbcConnector.getConnection();
		try
		{
			final String query = "insert into " + TABLE_NAME + " (lang_iso) "
					+ "VALUES (?)";
			final PreparedStatement statement = connection.prepareStatement(query);
			statement.setString(1, toSave.getLanguage());
			statement.executeUpdate();
			connection.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	

	@Override
	public ISOLang createObjectFromResultSet(ResultSet rs) throws SQLException {
		ISOLang lang = new ISOLang();
		lang.setLanguage(rs.getString("lang_iso"));
		return lang;
	}
}