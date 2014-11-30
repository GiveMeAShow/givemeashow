package giveme.common.dao;

import static giveme.common.dao.SharedConstants.DB_NAME;
import giveme.common.beans.User;
import giveme.services.EncryptionServices;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Component
@Repository
public class UserDao extends IDao<User> implements UserDetailsService
{
	@Autowired
	public ISOLangDao			isolangDao;

	@Autowired
	public EncryptionServices	encryptionServices;

	public UserDao()
	{
		TABLE_NAME = DB_NAME + ".user";
		LOGGER = Logger.getLogger(UserDao.class.getName());
	}

	@Override
	public User createObjectFromResultSet(ResultSet rs) throws SQLException
	{
		User user = new User();
		user.setId(rs.getInt("id"));
		user.setLogin(rs.getString("login"));
		user.setIsAdmin(rs.getBoolean("is_admin"));
		user.setInviteCode(rs.getString("invite_code"));
		user.setPassword(rs.getString("password"));
		user.setTimeSpent(rs.getLong("time_spent"));
		user.setDefaultLang(isolangDao.findByISO(rs.getString("default_lang")));
		user.setUseSubtitles(rs.getBoolean("use_subtitles"));
		user.setSubDefaultLang(isolangDao.findByISO(rs.getString("sub_default_lang")));
		user.setConfirmed(rs.getBoolean("confirmed"));
		user.setEmail(rs.getString("email"));
		user.setUserRole(rs.getString("user_role"));
		return user;
	}

	/**
	 * Save a new season.
	 */
	@Override
	public void save(final User toSave)
	{
		LOGGER.info("Saving a new Video");
		try
		{
			final String query = "insert into "
					+ TABLE_NAME
					+ " (login, isAdmin, invite_code, password, time_spent, default_lang, use_subtitles, sub_default_lang, confirmed, email) "
					+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
			KeyHolder keyHolder = new GeneratedKeyHolder();

			jdbcTemplate.update(new PreparedStatementCreator()
			{

				public PreparedStatement createPreparedStatement(Connection con) throws SQLException
				{
					PreparedStatement ps = con.prepareStatement(query, new String[]
					{ "login", "isAdmin", "invite_code", "password", "time_spent", "default_lang", "use_subtitles",
							"sub_default_lang", "confirmed", "email ", "user_role" });
					ps.setString(1, toSave.getLogin());
					ps.setBoolean(2, toSave.getIsAdmin());
					ps.setString(3, toSave.getInviteCode());
					ps.setString(4, toSave.getPassword());
					ps.setLong(5, toSave.getTimeSpent());
					ps.setString(6, toSave.getDefaultLang().getIso());
					ps.setBoolean(7, toSave.getUseSubtitles());
					ps.setString(8, toSave.getSubDefaultLang().getIso());
					ps.setBoolean(9, toSave.getConfirmed());
					ps.setString(10, toSave.getEmail());
					ps.setString(11, toSave.getUserRole());
					return ps;
				}
			}, keyHolder);

			toSave.setId(keyHolder.getKey().intValue());
		} catch (final Exception e)
		{
			e.printStackTrace();
		}
	}

	public User findById(final Integer userId)
	{
		User user = null;
		try
		{
			final String query = "select * from " + TABLE_NAME + " WHERE id = ?";
			user = jdbcTemplate.queryForObject(query, new Object[]
			{ userId }, new MyObjectMapper());
		} catch (final Exception e)
		{
			return null;
		}
		return user;
	}

	@Override
	public User createObjectFromRows(Map<String, Object> row)
	{

		User user = new User();
		user.setLogin((String) row.get("LOGIN"));
		user.setId(Integer.parseInt(String.valueOf(row.get("ID"))));
		user.setAdmin(Boolean.parseBoolean(String.valueOf(row.get("IS_ADMIN"))));
		user.setConfirmed(Boolean.parseBoolean(String.valueOf(row.get("CONFIRMED"))));
		user.setDefaultLang(isolangDao.findByISO((String) row.get("DEFAULT_LANG")));
		user.setEmail((String) row.get("EMAIL"));
		user.setInviteCode((String) row.get("INVITE_CODE"));
		user.setPassword((String) row.get("PASSWORD"));
		user.setSubDefaultLang(isolangDao.findByISO((String) row.get("SUB_DEFAULT_LANG")));
		user.setTimeSpent(Long.parseLong((String) row.get("TIME_SPENT")));
		user.setUseSubtitles(Boolean.parseBoolean(String.valueOf(row.get("USE_SUBTITLES"))));
		user.setUserRole((String) row.get("USER_ROLE"));
		return user;
	}

	public void update(User user)
	{
		LOGGER.info("Updating user " + user.getLogin());
		jdbcTemplate
				.update("update "
						+ TABLE_NAME
						+ " set login = ?, is_admin = ?, confirmed = ?, default_lang = ?, email = ?, invite_code = ?, password = ?, sub_default_lang = ?"
						+ ", time_spent = ?, use_subtitles = ?, user_roles_id where id = ?",
						new Object[]
						{ user.getLogin(), user.getIsAdmin(), user.getConfirmed(), user.getDefaultLang().getIso(),
								user.getEmail(), user.getInviteCode(), user.getPassword(),
								user.getSubDefaultLang().getIso(), user.getTimeSpent(), user.getUseSubtitles(),
								user.getUserRole(), user.getId() });
	}

	public User findByLogin(String userName)
	{
		User user = null;
		try
		{
			final String query = "select * from " + TABLE_NAME + " WHERE login = ?";
			user = jdbcTemplate.queryForObject(query, new Object[]
			{ userName }, new MyObjectMapper());
		} catch (final Exception e)
		{
			return null;
		}
		return user;
	}

	public User findByLoginAndPassword(String userName, String password)
	{
		User user = null;
		LOGGER.info("encrypted = " + password);
		try
		{
			final String query = "select * from " + TABLE_NAME + " WHERE login = ? AND password = ?";
			user = jdbcTemplate.queryForObject(query, new Object[]
			{ userName, password }, new MyObjectMapper());
		} catch (final Exception e)
		{
			return null;
		}
		return user;
	}

	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
	{
		User myUser = findByLogin(username);
		UserDetails user = null;
		Collection<SimpleGrantedAuthority> grantedAuths = new ArrayList<SimpleGrantedAuthority>();
		SimpleGrantedAuthority grantedAuth = new SimpleGrantedAuthority("ROLE_USER");
		grantedAuths.add(grantedAuth);
		if (myUser.getIsAdmin())
		{
			SimpleGrantedAuthority adminAuth = new SimpleGrantedAuthority("ROLE_ADMIN");
			grantedAuths.add(adminAuth);
		}
		user = new org.springframework.security.core.userdetails.User(myUser.getLogin(), myUser.getPassword(),
				grantedAuths);
		return user;
	}

	public void updatePassword(Integer id, String encryptPassword)
	{
		LOGGER.info("Updating user " + id);
		jdbcTemplate.update("update " + TABLE_NAME + " set password = ? where id = ?", new Object[]
		{ encryptPassword, id });

	}
}
