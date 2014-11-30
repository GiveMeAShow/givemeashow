package giveme.controllers;

import giveme.common.beans.User;
import giveme.common.dao.UserDao;
import giveme.services.EncryptionServices;
import giveme.services.MailServices;

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
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
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

	private final EmailValidator	emailValidator;

	public UserController()
	{
		emailValidator = EmailValidator.getInstance();
	}

	@RequestMapping(value = "/admin**", method = RequestMethod.GET)
	public ModelAndView adminPage()
	{

		ModelAndView model = new ModelAndView();
		model.addObject("title", "Spring Security Custom Login Form");
		model.addObject("message", "This is protected page!");
		model.setViewName("admin");

		return model;
	}

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

	@RequestMapping(value = "/webservices/user/invite", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	@ResponseBody
	public String invite(@RequestBody String emails) throws JSONException
	{
		LOGGER.info(emails);
		JSONArray jsonEmails = new JSONArray(emails);
		for (int i = 0; i < jsonEmails.length(); i++)
		{
			String email = jsonEmails.getString(i);
			if (emailValidator.isValid(email))
			{
				LOGGER.info("Sending email :D");
				mailServices.sendInvite(email);
				// if not admin, decrease number of available invites
			}
			else
			{
				LOGGER.info("email " + email + " is invalid");
			}
		}
		return "";
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ModelAndView login()
	{
		ModelAndView model = new ModelAndView("/login.html");
		User user = new User();
		model.addObject("user", user);
		return model;
	}

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
				UserDetails userDetails = userDao.loadUserByUsername(user.getLogin());
				Authentication auth = new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(),
						userDetails.getAuthorities());
				SecurityContextHolder.getContext().setAuthentication(auth);
				LOGGER.info("user " + user.getLogin() + " logged in.");
				return new ModelAndView("redirect:/");
			}
		}
		return model;
	}
}
