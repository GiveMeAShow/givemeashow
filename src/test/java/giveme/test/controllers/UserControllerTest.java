package giveme.test.controllers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;
import giveme.common.beans.ISOLang;
import giveme.common.beans.InviteCode;
import giveme.common.dao.ISOLangDao;
import giveme.common.dao.InviteCodeDao;
import giveme.common.dao.UserDao;
import giveme.controllers.UserController;
import giveme.services.EncryptionServices;
import giveme.services.MailServices;
import giveme.shared.GiveMeProperties;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.results.ResultMatchers;
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
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.ui.Model;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations =
{ "file:src/main/webapp/WEB-INF/gmas-database-configuration-spring.xml",
		"file:src/main/webapp/WEB-INF/mvc-dispatcher-servlet.xml", "file:src/main/webapp/WEB-INF/spring-security.xml" })
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
	InviteCodeDao					inviteCodeDao;

	@Autowired
	MailServices					mailService;

	@Autowired
	ISOLangDao						isoLangDao;

	@Autowired
	private WebApplicationContext	context;

	private MockMvc					mockMvc;

	@Before
	public void init()
	{
		// replace inject by mock
		userDao = Mockito.mock(UserDao.class);
		encryptionServices = Mockito.mock(EncryptionServices.class);
		givemeAShowProperties = Mockito.mock(GiveMeProperties.class);
		mailService = Mockito.mock(MailServices.class);
		inviteCodeDao = Mockito.mock(InviteCodeDao.class);
		isoLangDao = Mockito.mock(ISOLangDao.class);
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

	/**
	 * Test failure when max invite is reached
	 * 
	 * @throws Exception
	 */
	@Test
	public void inviteMaxTest() throws Exception
	{
		givemeAShowProperties.setMAX_INVITE(0);

		injectMockPrincipal();

		JSONArray emailsJson = new JSONArray();
		emailsJson.put("coucou@coucou.com");
		emailsJson.put("testmail@mailTest.com");
		giveme.common.beans.User user = new giveme.common.beans.User(1, "zda", false, "xaxaxaxa", "isx27", 0, true,
				true, "zdwa@sdzw.com");

		MvcResult result = mockMvc.perform(
				post("/webservices/user/invite").accept(MediaType.APPLICATION_JSON)
						.contentType(MediaType.APPLICATION_JSON).content(emailsJson.toString())).andReturn();
	}

	/**
	 * Test failure when max invite is reached
	 * 
	 * @throws Exception
	 */
	@Test
	public void inviteTest() throws Exception
	{
		Mockito.doReturn(50).when(givemeAShowProperties).getMAX_INVITE();
		injectMockPrincipal();

		JSONArray emailsJson = new JSONArray();
		emailsJson.put("coucou@coucou.com");
		emailsJson.put("testmail@mailTest.com");
		giveme.common.beans.User user = new giveme.common.beans.User(1, "zda", false, "xaxaxaxa", "isx27", 0, true,
				true, "zdwa@sdzw.com");

		MvcResult result = mockMvc.perform(
				post("/webservices/user/invite").accept(MediaType.APPLICATION_JSON)
						.contentType(MediaType.APPLICATION_JSON).content(emailsJson.toString())).andReturn();
	}

	/**
	 * Test failure when max invite is reached
	 * 
	 * @throws Exception
	 */
	@Test
	public void inviteMaxSecondReachedTest() throws Exception
	{
		Mockito.doReturn(1).when(givemeAShowProperties).getMAX_INVITE();
		Authentication auth = injectMockPrincipal();

		JSONArray emailsJson = new JSONArray();
		emailsJson.put("coucou@coucou.com");
		emailsJson.put("testmail@mailTest.com");
		emailsJson.put("ç");
		giveme.common.beans.User user = new giveme.common.beans.User(1, "zda", false, "xaxaxaxa", "isx27", 0, true,
				true, "zdwa@sdzw.com");

		MvcResult result = mockMvc.perform(
				post("/webservices/user/invite").accept(MediaType.APPLICATION_JSON)
						.contentType(MediaType.APPLICATION_JSON).content(emailsJson.toString())).andReturn();
	}

	/**
	 * Inject a principal into the spring security context
	 * 
	 * @return
	 */
	private Authentication injectMockPrincipal()
	{
		Collection<SimpleGrantedAuthority> grantedAuths = new ArrayList<SimpleGrantedAuthority>();
		SimpleGrantedAuthority grantedAuth = new SimpleGrantedAuthority("ROLE_USER");
		grantedAuths.add(grantedAuth);
		giveme.common.beans.User userBean = new giveme.common.beans.User();
		userBean.setId(1);
		userBean.setLogin("zda");
		userBean.setInvited(0);
		userBean.setAdmin(false);
		userBean.setEmail("user@user.com");
		Mockito.doReturn(userBean).when(userDao).findByLogin(Mockito.anyString());
		User springUser = new User("zda", "isx27", grantedAuths);
		Authentication auth = new UsernamePasswordAuthenticationToken(springUser, null);
		SecurityContextHolder.getContext().setAuthentication(auth);
		return auth;
	}

	/**
	 * Test failure when max invite is reached
	 * 
	 * @throws Exception
	 */
	@Test
	public void inviteInvalidMailTest() throws Exception
	{
		Mockito.doReturn(4).when(givemeAShowProperties).getMAX_INVITE();
		Authentication auth = injectMockPrincipal();

		SecurityContextHolder.getContext().setAuthentication(auth);
		JSONArray emailsJson = new JSONArray();
		emailsJson.put("coucou@coucou.com");
		emailsJson.put("testmail@mailTest.com");
		emailsJson.put("ç");
		giveme.common.beans.User user = new giveme.common.beans.User(1, "zda", false, "xaxaxaxa", "isx27", 0, true,
				true, "zdwa@sdzw.com");

		MvcResult result = mockMvc.perform(
				post("/webservices/user/invite").accept(MediaType.APPLICATION_JSON)
						.contentType(MediaType.APPLICATION_JSON).content(emailsJson.toString())).andReturn();
	}

	/**
	 * test login page
	 * 
	 * @throws Exception
	 */
	@Test
	public void loginTest() throws Exception
	{
		giveme.common.beans.User user = new giveme.common.beans.User();
		user.setLogin("user");
		user.setId(4);
		mockMvc.perform(get("/login")).andExpect(view().name("/login.html"))
				.andExpect(model().attribute("user", notNullValue()));
	}

	@Test
	public void registerPageWithInviteCodeTest() throws Exception
	{
		InviteCode inviteCode = new InviteCode();
		inviteCode.setInviteCode("code");
		inviteCode.setUserId(1);
		Mockito.doReturn(inviteCode).when(inviteCodeDao).find(Mockito.anyString());

		giveme.common.beans.User user = new giveme.common.beans.User();
		user.setId(1);
		Mockito.doReturn(user).when(userDao).findById(Mockito.anyInt());

		mockMvc.perform(get("/register/{inviteCode}", "code")).andExpect(view().name("/register.jsp"))
				.andExpect(model().attribute("user", notNullValue()))
				.andExpect(model().attribute("host", hasProperty("id", is(1))));
	}

	@Test
	public void registerPageWithWrongInviteCodeTest() throws Exception
	{
		InviteCode inviteCode = new InviteCode();
		inviteCode.setInviteCode("code");
		inviteCode.setUserId(1);
		Mockito.doReturn(null).when(inviteCodeDao).find(Mockito.anyString());

		mockMvc.perform(get("/register/{inviteCode}", "code")).andExpect(view().name("redirect:/login"));
	}

	@Test
	public void registerPOSTWithInviteCodeAndNullUserTest() throws Exception
	{
		InviteCode inviteCode = new InviteCode();
		inviteCode.setInviteCode("code");
		inviteCode.setUserId(1);
		Mockito.doReturn(inviteCode).when(inviteCodeDao).find(Mockito.anyString());

		giveme.common.beans.User user = new giveme.common.beans.User();
		user.setId(1);
		Mockito.doReturn(user).when(userDao).findById(Mockito.anyInt());

		mockMvc.perform(post("/register/{inviteCode}", "code")).andExpect(view().name("redirect:/"))
				.andExpect(model().attribute("error", is("Please fill all the fields")));
	}

	@Test
	public void registerPOSTWithInviteCodeTest() throws Exception
	{
		InviteCode inviteCode = new InviteCode();
		inviteCode.setInviteCode("code");
		inviteCode.setUserId(1);
		Mockito.doReturn(inviteCode).when(inviteCodeDao).find(Mockito.anyString());
		ISOLang french = new ISOLang();
		french.setIso("fr");
		Mockito.doReturn(french).when(isoLangDao).findByISO(Mockito.anyString());
		giveme.common.beans.User user = new giveme.common.beans.User();
		user.setId(1);
		user.setLogin("username");
		user.setEmail("user@user.com");
		user.setPassword("password");
		Mockito.doReturn(null).when(userDao).findByLogin(Mockito.anyString());
		Mockito.doReturn(null).when(userDao).findByEmail(Mockito.anyString());
		MockUserLogin(user);

		mockMvc.perform(
				post("/register/{inviteCode}", "code").param("id", "1").param("login", user.getLogin())
						.param("email", user.getEmail()).param("password", user.getPassword())).andExpect(
				view().name("redirect:/"));
	}

	/**
	 * Mail already in use
	 * 
	 * @throws Exception
	 */
	@Test
	public void registerPOSTWithInviteCodeAndEmailAlreadyUsedTest() throws Exception
	{
		InviteCode inviteCode = new InviteCode();
		inviteCode.setInviteCode("code");
		inviteCode.setUserId(1);
		Mockito.doReturn(inviteCode).when(inviteCodeDao).find(Mockito.anyString());
		ISOLang french = new ISOLang();
		french.setIso("fr");
		Mockito.doReturn(french).when(isoLangDao).findByISO(Mockito.anyString());
		giveme.common.beans.User user = new giveme.common.beans.User();
		user.setId(1);
		user.setLogin("username");
		user.setEmail("user@user.com");
		user.setPassword("password");
		Mockito.doReturn(null).when(userDao).findByLogin(Mockito.anyString());
		Mockito.doReturn("hey !").when(userDao).findByEmail(Mockito.anyString());
		MockUserLogin(user);

		mockMvc.perform(
				post("/register/{inviteCode}", "code").param("id", "1").param("login", user.getLogin())
						.param("email", user.getEmail()).param("password", user.getPassword()))
				.andExpect(view().name("redirect:/"))
				.andExpect(model().attribute("error", is("This email is already in use")));
	}

	/**
	 * Mail already in use
	 * 
	 * @throws Exception
	 */
	@Test
	public void registerPOSTWithInviteCodeAndLoginAlreadyUsedTest() throws Exception
	{
		InviteCode inviteCode = new InviteCode();
		inviteCode.setInviteCode("code");
		inviteCode.setUserId(1);
		Mockito.doReturn(inviteCode).when(inviteCodeDao).find(Mockito.anyString());
		ISOLang french = new ISOLang();
		french.setIso("fr");
		Mockito.doReturn(french).when(isoLangDao).findByISO(Mockito.anyString());
		giveme.common.beans.User user = new giveme.common.beans.User();
		user.setId(1);
		user.setLogin("username");
		user.setEmail("user@user.com");
		user.setPassword("password");
		Mockito.doReturn(new giveme.common.beans.User()).when(userDao).findByLogin(Mockito.anyString());
		Mockito.doReturn("hey !").when(userDao).findByEmail(Mockito.anyString());
		MockUserLogin(user);

		mockMvc.perform(
				post("/register/{inviteCode}", "code").param("id", "1").param("login", user.getLogin())
						.param("email", user.getEmail()).param("password", user.getPassword()))
				.andExpect(view().name("redirect:/"))
				.andExpect(model().attribute("error", is("This login is already in use")));
	}

	/**
	 * Test login action : User name not found
	 * 
	 * @throws Exception
	 */
	@Test
	public void loginNotFoundPOSTTest() throws Exception
	{
		giveme.common.beans.User user = new giveme.common.beans.User();
		user.setLogin("username");
		user.setPassword("password");

		mockMvc.perform(
				post("/login").param("id", "1").param("login", user.getLogin()).param("email", user.getEmail())
						.param("password", user.getPassword())).andExpect(view().name("/login.html"))
				.andExpect(model().attribute("error", is("No username " + user.getLogin() + " found.")));
	}

	/**
	 * Test login action : Wrong password
	 * 
	 * @throws Exception
	 */
	@Test
	public void loginWrongPasswordPOSTTest() throws Exception
	{
		giveme.common.beans.User user = new giveme.common.beans.User();
		user.setLogin("username");
		user.setPassword("password");
		Mockito.doReturn(user).when(userDao).findByLogin(Mockito.anyString());
		mockMvc.perform(
				post("/login").param("id", "1").param("login", user.getLogin()).param("email", user.getEmail())
						.param("password", user.getPassword())).andExpect(view().name("/login.html"));
	}

	/**
	 * Test login action : login success
	 * 
	 * @throws Exception
	 */
	@Test
	public void loginPOSTTest() throws Exception
	{
		giveme.common.beans.User user = new giveme.common.beans.User();
		user.setLogin("username");
		user.setPassword("password");
		Mockito.doReturn(user).when(userDao).findByLogin(Mockito.anyString());
		Mockito.doReturn(true).when(encryptionServices).match(Mockito.anyString(), Mockito.anyString());

		MockUserLogin(user);

		mockMvc.perform(
				post("/login").param("id", "1").param("login", user.getLogin()).param("email", user.getEmail())
						.param("password", user.getPassword())).andExpect(view().name("redirect:/"));
	}

	private void MockUserLogin(giveme.common.beans.User user)
	{
		// login the registered useer
		UserDetails userDetails = null;
		Collection<SimpleGrantedAuthority> grantedAuths = new ArrayList<SimpleGrantedAuthority>();
		SimpleGrantedAuthority grantedAuth = new SimpleGrantedAuthority("ROLE_USER");
		grantedAuths.add(grantedAuth);
		userDetails = new org.springframework.security.core.userdetails.User(user.getLogin(), user.getPassword(),
				grantedAuths);
		Mockito.doReturn(userDetails).when(userDao).loadUserByUsername(Mockito.anyString());
	}

}
