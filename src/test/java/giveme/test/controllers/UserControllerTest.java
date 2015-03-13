package giveme.test.controllers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;
import giveme.common.dao.UserDao;
import giveme.controllers.UserController;
import giveme.services.EncryptionServices;
import giveme.shared.GiveMeProperties;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations =
{ "classpath:gmas-db-cfg-test.xml", "file:src/main/webapp/WEB-INF/mvc-dispatcher-servlet.xml",
		"file:src/main/webapp/WEB-INF/spring-security.xml" })
@WebAppConfiguration
public class UserControllerTest
{
	private static final Logger		LOGGER	= Logger.getLogger(UserControllerTest.class.getName());

	@InjectMocks
	UserController					userController;

	@Autowired
	UserDao							userDao;

	@Autowired
	EncryptionServices				encryptionServices;

	@Autowired
	GiveMeProperties				givemeAShowProperties;

	@Autowired
	private WebApplicationContext	context;

	private MockMvc					mockMvc;

	@Before
	public void init()
	{
		// replace inject by mock
		userDao = Mockito.mock(UserDao.class);
		encryptionServices = Mockito.mock(EncryptionServices.class);

		// init
		initMocks(this);

		// setup
		mockMvc = standaloneSetup(userController).build();
		// mockMvc =
		// MockMvcBuilders.webAppContextSetup(context).addFilters(springSecurityFilterChain).build();
	}

	/**
	 * Test the getUser webservices.
	 * 
	 * @throws Exception
	 */
	@Test
	public void getUserSuccessTest() throws Exception
	{
		Collection<SimpleGrantedAuthority> grantedAuths = new ArrayList<SimpleGrantedAuthority>();
		SimpleGrantedAuthority grantedAuth = new SimpleGrantedAuthority("ROLE_USER");
		grantedAuths.add(grantedAuth);

		User springUser = new User("zda", "isx27", grantedAuths);
		Authentication auth = new UsernamePasswordAuthenticationToken(springUser, null);

		SecurityContextHolder.getContext().setAuthentication(auth);

		giveme.common.beans.User user = new giveme.common.beans.User(1, "zda", true, "xaxaxaxa", "isx27", 0, true,
				true, "zdwa@sdzw.com");
		Mockito.doReturn(user).when(userDao).findByLogin(Mockito.anyString());

		MvcResult result = mockMvc.perform(get("/webservices/user")).andReturn();

		String response = result.getResponse().getContentAsString();
		ObjectMapper mapper = new ObjectMapper();
		giveme.common.beans.User returnedUser = mapper.readValue(response, giveme.common.beans.User.class);

		assertThat(returnedUser.getLogin()).isNotNull().isNotEmpty().isEqualTo(user.getLogin());
		assertThat(returnedUser.getIsAdmin()).isNotNull().isEqualTo(user.getIsAdmin());
		assertThat(returnedUser.getConfirmed()).isFalse();
		assertThat(returnedUser.getDefaultLang()).isNull();
		assertThat(returnedUser.getEmail()).isNull();
		assertThat(returnedUser.getInviteCode()).isNull();
		assertThat(returnedUser.getInvited()).isNull();
		assertThat(returnedUser.getPassword()).isNull();
		LOGGER.info(returnedUser.getLogin());
		// mockMvc.perform(post("/admin/video/list").with(csrf().asHeader()));
	}

	/**
	 * No user registred in security context test. TODO : Use real
	 * authentication
	 * 
	 * @throws Exception
	 */
	@Test
	public void getUserFailureTest() throws Exception
	{
		Authentication auth = Mockito.mock(UsernamePasswordAuthenticationToken.class);
		Mockito.doReturn(null).when(auth).getPrincipal();
		SecurityContextHolder.getContext().setAuthentication(auth);

		MvcResult result = mockMvc.perform(get("/webservices/user")).andReturn();
		assertThat(result.getResponse().getContentAsString()).isEmpty();
	}

	/**
	 * Test the getUser webservices.
	 * 
	 * @throws Exception
	 */
	@Test
	public void getMeSuccessTest() throws Exception
	{
		Collection<SimpleGrantedAuthority> grantedAuths = new ArrayList<SimpleGrantedAuthority>();
		SimpleGrantedAuthority grantedAuth = new SimpleGrantedAuthority("ROLE_USER");
		grantedAuths.add(grantedAuth);

		User springUser = new User("zda", "isx27", grantedAuths);
		Authentication auth = new UsernamePasswordAuthenticationToken(springUser, null);

		SecurityContextHolder.getContext().setAuthentication(auth);

		giveme.common.beans.User user = new giveme.common.beans.User(1, "zda", true, "xaxaxaxa", "isx27", 0, true,
				true, "zdwa@sdzw.com");
		Mockito.doReturn(user).when(userDao).findByLogin(Mockito.anyString());

		MvcResult result = mockMvc.perform(get("/webservices/user/me")).andReturn();

		String response = result.getResponse().getContentAsString();
		ObjectMapper mapper = new ObjectMapper();
		giveme.common.beans.User returnedUser = mapper.readValue(response, giveme.common.beans.User.class);

		assertThat(returnedUser.getLogin()).isNotNull().isNotEmpty().isEqualTo(user.getLogin());
		assertThat(returnedUser.getIsAdmin()).isNotNull().isEqualTo(user.getIsAdmin());

		assertThat(returnedUser.getPassword()).isEmpty();
		assertThat(returnedUser.getUserRole()).isEmpty();
		LOGGER.info(returnedUser.getLogin());
		// mockMvc.perform(post("/admin/video/list").with(csrf().asHeader()));
	}

	/**
	 * No user registred in security context test. TODO : Use real
	 * authentication
	 * 
	 * @throws Exception
	 */
	@Test
	public void getMeFailureTest() throws Exception
	{
		Authentication auth = Mockito.mock(UsernamePasswordAuthenticationToken.class);
		Mockito.doReturn(null).when(auth).getPrincipal();
		SecurityContextHolder.getContext().setAuthentication(auth);

		MvcResult result = mockMvc.perform(get("/webservices/user/me")).andReturn();
		assertThat(result.getResponse().getContentAsString()).isEmpty();
	}

	/**
	 * 
	 * Test password change
	 * 
	 * @throws Exception
	 */
	@Test
	public void changePwTest() throws Exception
	{
		giveme.common.beans.User user = new giveme.common.beans.User();
		user.setLogin("zdaa");
		user.setPassword("super_password_105");
		EncryptionServices encc = new EncryptionServices();

		user.setPassword(encc.encryptPassword("super_password_105"));
		Mockito.doReturn(user).when(userDao).findByLogin(Mockito.anyString());
		Mockito.doReturn(true).when(encryptionServices).match(Mockito.anyString(), Mockito.anyString());

		JSONObject postPassword = new JSONObject();
		postPassword.put("login", "zdaa");
		postPassword.put("old", "super_password_105");
		postPassword.put("new", "super_password_106");
		LOGGER.info(postPassword.toString());
		MvcResult result = mockMvc.perform(
				post("/webservices/user/changePassword").accept(MediaType.APPLICATION_JSON)
						.contentType(MediaType.APPLICATION_JSON).content(postPassword.toString())).andReturn();
		String resultAsString = result.getResponse().getContentAsString();

		ObjectMapper mapper = new ObjectMapper();
		JSONObject resultAsJson = new JSONObject(resultAsString);
		assertThat(resultAsJson.get("login")).isEqualTo(postPassword.get("login"));
	}

	/**
	 * No user registred in security context test. TODO : Use real
	 * authentication
	 * 
	 * @throws Exception
	 */
	@Test
	public void changePwFailureTest() throws Exception
	{
		giveme.common.beans.User user = new giveme.common.beans.User();
		user.setLogin("zdaa");
		user.setPassword("super_password_105");
		EncryptionServices encc = new EncryptionServices();

		user.setPassword(encc.encryptPassword("super_password_105"));
		Mockito.doReturn(user).when(userDao).findByLogin(Mockito.anyString());

		JSONObject postPassword = new JSONObject();
		postPassword.put("login", "zdaa");
		postPassword.put("old", "super_password_105");
		postPassword.put("new", "super_password_106");
		LOGGER.info(postPassword.toString());
		MvcResult result = mockMvc.perform(
				post("/webservices/user/changePassword").accept(MediaType.APPLICATION_JSON)
						.contentType(MediaType.APPLICATION_JSON).content(postPassword.toString())).andReturn();

		assertThat(result.getResponse().getContentAsString()).isEmpty();
	}

	@Test
	public void inviteTest() throws Exception
	{
		givemeAShowProperties.setMAX_INVITE(0);
		Collection<SimpleGrantedAuthority> grantedAuths = new ArrayList<SimpleGrantedAuthority>();
		SimpleGrantedAuthority grantedAuth = new SimpleGrantedAuthority("ROLE_USER");
		grantedAuths.add(grantedAuth);

		User springUser = new User("zda", "isx27", grantedAuths);
		Authentication auth = new UsernamePasswordAuthenticationToken(springUser, null);

		SecurityContextHolder.getContext().setAuthentication(auth);
		JSONArray emailsJson = new JSONArray();
		emailsJson.put("coucou@coucou.com");
		emailsJson.put("testmail@mailTest.com");
		giveme.common.beans.User user = new giveme.common.beans.User(1, "zda", true, "xaxaxaxa", "isx27", 0, true,
				true, "zdwa@sdzw.com");
		Mockito.doReturn(user).when(userDao).findByLogin(Mockito.anyString());

		MvcResult result = mockMvc.perform(
				post("/webservices/user/invite").accept(MediaType.APPLICATION_JSON)
						.contentType(MediaType.APPLICATION_JSON).content(emailsJson.toString())).andReturn();

	}

}
