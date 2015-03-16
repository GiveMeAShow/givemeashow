package giveme.controllers;

import giveme.common.beans.ISOLang;
import giveme.common.beans.InviteCode;
import giveme.common.beans.User;
import giveme.common.dao.ISOLangDao;
import giveme.common.dao.InviteCodeDao;
import giveme.common.dao.UserDao;
import giveme.services.EncryptionServices;
import giveme.services.MailServices;
import giveme.shared.GiveMeProperties;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.validator.routines.EmailValidator;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UserController
{
	public static final Logger		LOGGER	= Logger.getLogger(UserController.class.getName());

	@Autowired
	UserDao							userDao;

	@Autowired
	EncryptionServices				encryptionServices;

	@Autowired
	MailServices					mailServices;

	@Autowired
	InviteCodeDao					inviteCodeDao;

	private final EmailValidator	emailValidator;

	@Autowired
	ISOLangDao						isoLangDao;

	@Autowired
	public GiveMeProperties			giveMeAShowProperties;

	public UserController()
	{
		emailValidator = EmailValidator.getInstance();
	}

	/**
	 * 
	 * @param resonse
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/webservices/user", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public User getUser(HttpServletResponse resonse, HttpServletRequest request)
	{
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		UserDetails userDetails = null;
		if (principal instanceof UserDetails)
		{
			userDetails = (UserDetails) principal;
			User tmpUser = userDao.findByLogin(userDetails.getUsername());
			User result = new User();
			result.setAdmin(tmpUser.getIsAdmin());
			result.setLogin(tmpUser.getLogin());
			return result;
		}
		return null;
	}

	/**
	 * 
	 * @param resonse
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/webservices/user/me", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public User getMe(HttpServletResponse resonse, HttpServletRequest request)
	{
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		UserDetails userDetails = null;
		if (principal instanceof UserDetails)
		{
			userDetails = (UserDetails) principal;
			User tmpUser = userDao.findByLogin(userDetails.getUsername());
			tmpUser.setPassword("");
			tmpUser.setUserRole("");
			return tmpUser;
		}
		return null;
	}

	/**
	 * 
	 * @param newPassword
	 * @return
	 * @throws JSONException
	 */
	@RequestMapping(value = "/webservices/user/changePassword", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	@ResponseBody
	public User changePw(@RequestBody String newPassword) throws JSONException
	{
		JSONObject newPasswordDatas = new JSONObject(newPassword);
		String login = newPasswordDatas.getString("login");
		String oldPassword = newPasswordDatas.getString("old");
		User user = userDao.findByLogin(login);
		if (encryptionServices.match(oldPassword, user.getPassword()))
		{
			String newPw = newPasswordDatas.getString("new");
			userDao.updatePassword(user.getId(), encryptionServices.encryptPassword(newPw));
			user.setPassword("");
			return user;
		}
		else
		{
			return null;
		}
	}

	/**
	 * 
	 * @param emails
	 * @return
	 * @throws JSONException
	 */
	@RequestMapping(value = "/webservices/user/invite", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	@ResponseBody
	public String invite(@RequestBody String emails) throws JSONException
	{
		org.springframework.security.core.userdetails.User springSecuritySessionUser = (org.springframework.security.core.userdetails.User) SecurityContextHolder
				.getContext().getAuthentication().getPrincipal();
		String userName = springSecuritySessionUser.getUsername();
		User user = userDao.findByLogin(userName);

		LOGGER.info(emails);
		JSONArray jsonEmails = new JSONArray(emails);

		int invited = user.getInvited();
		if (invited >= giveMeAShowProperties.getMAX_INVITE() && !user.getIsAdmin())
		{
			// TODO ERRH
			return "YOU CAN'T";
		}

		for (int i = 0; i < jsonEmails.length(); i++)
		{
			String email = jsonEmails.getString(i);
			if (emailValidator.isValid(email))
			{
				LOGGER.info("Sending email :D");
				try
				{
					if (user.getIsAdmin() || (!user.getIsAdmin() && invited < giveMeAShowProperties.getMAX_INVITE()))
					{
						String inviteCodeStr = mailServices.sendInvite(userName, email);
						invited += 1;
						InviteCode inviteCode = new InviteCode(inviteCodeStr, user.getId());
						inviteCodeDao.save(inviteCode);
						userDao.updateInvited(user.getId(), invited);
					}
					else
					{
						// TODO ERRH
						return "YOU CAN'T";
					}
				} catch (MailException e)
				{
					LOGGER.error("Couldnt send email for " + email);
				}
			}
			else
			{
				LOGGER.info("email " + email + " is invalid");
			}
		}
		return "";
	}

	/**
	 * 
	 * @return
	 */
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ModelAndView login()
	{
		ModelAndView model = new ModelAndView("/login.html");
		User user = new User();
		model.addObject("user", user);
		return model;
	}

	/**
	 * 
	 * @param inviteCode
	 * @return
	 */
	@RequestMapping(value = "/register/{inviteCode}", method = RequestMethod.GET)
	public ModelAndView registerWithInviteCode(@PathVariable("inviteCode") String inviteCode)
	{
		InviteCode inviteCodeDB = inviteCodeDao.find(inviteCode);
		if (inviteCodeDB != null)
		{
			User user = userDao.findById(inviteCodeDB.getUserId());
			user.setPassword("");
			ModelAndView mdv = new ModelAndView("/register.jsp");
			mdv.addObject("host", user);
			mdv.addObject("user", new User());
			mdv.addObject("inviteCode", inviteCodeDB);
			return mdv;
		}
		else
		{
			return new ModelAndView("redirect:/login");
		}
	}

	/**
	 * 
	 * @param inviteCode
	 * @return
	 */
	@RequestMapping(value = "/register/{inviteCode}", method = RequestMethod.POST)
	public ModelAndView register(@ModelAttribute("user") User user, @PathVariable("inviteCode") String inviteCode)
	{
		// TODO use confirmation mail ? don't think so.
		ModelAndView redirectView = new ModelAndView("redirect:/");
		InviteCode invite = new InviteCode(inviteCode);
		if (user.getLogin() == null || user.getEmail() == null || user.getPassword() == null)
		{
			redirectView.addObject("error", "Please fill all the fields");
			return redirectView;
		}
		if (userDao.findByLogin(user.getLogin()) == null)
		{
			if (userDao.findByEmail(user.getEmail()) == null)
			{
				ISOLang defaultLang = isoLangDao.findByISO("fr");
				user.setDefaultLang(defaultLang);
				user.setInvited(0);
				user.setSubDefaultLang(defaultLang);
				user.setTimeSpent(0);
				user.setUserRole("ROLE_USER");
				user.setPassword(encryptionServices.encryptPassword(user.getPassword()));
				userDao.save(user);
				try
				{
					mailServices.sendWelcome(user);
				} catch (MailException e)
				{
					userDao.delete(user);
					redirectView.addObject("error", "Error while trying to send confirmation mail.");
					return new ModelAndView("redirect:/");
				}
				inviteCodeDao.delete(invite);
				logUser(user);
				return redirectView;
			}
			else
			{
				redirectView.addObject("error", "This email is already in use");
				return redirectView;
			}
		}
		redirectView.addObject("error", "This login is already in use");
		return redirectView;
	}

	/**
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public ModelAndView logOut(HttpServletRequest request, HttpServletResponse response)
	{
		try
		{
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			if (auth != null)
			{
				new SecurityContextLogoutHandler().logout(request, response, auth);
			}
			HttpSession session = request.getSession();
			if (session != null)
			{
				session.invalidate();
			}
			request.logout();
		} catch (ServletException e)
		{
			e.printStackTrace();
		}
		return new ModelAndView("redirect:/login");
	}

	/**
	 * 
	 * @param user
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ModelAndView validLogin(@ModelAttribute("user") User user, HttpServletRequest request,
			HttpServletResponse response) throws IOException
	{
		ModelAndView model = new ModelAndView("/login.html");
		String password = user.getPassword();
		if (user != null)
		{
			String login = user.getLogin();
			user = userDao.findByLogin(user.getLogin());
			if (user == null)
			{
				model.addObject("error", "No username " + login + " found.");
				return model;
			}
			else if (user != null && encryptionServices.match(password, user.getPassword()))
			{
				logUser(user);
				return new ModelAndView("redirect:/");
			}
		}
		return model;
	}

	private void logUser(User user)
	{
		UserDetails userDetails = userDao.loadUserByUsername(user.getLogin());
		Authentication auth = new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(),
				userDetails.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(auth);
		LOGGER.info("user " + user.getLogin() + " logged in.");
	}

}
