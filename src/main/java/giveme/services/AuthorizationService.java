package giveme.services;

import giveme.common.beans.User;
import giveme.common.dao.UserDao;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Service;

@Service
public class AuthorizationService implements UserDetailsService, AuthenticationSuccessHandler
{

	@Autowired
	UserDao	userDao;

	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
	{
		User myUser = userDao.findByLogin(username);
		UserDetails user = null;
		Collection<SimpleGrantedAuthority> grantedAuths = new ArrayList<SimpleGrantedAuthority>();
		SimpleGrantedAuthority grantedAuth = new SimpleGrantedAuthority(myUser.getUserRole());
		if (myUser != null)
		{
			grantedAuths.add(grantedAuth);
			if (myUser.getIsAdmin())
			{
				SimpleGrantedAuthority adminAuth = new SimpleGrantedAuthority("ROLE_ADMIN");
				grantedAuths.add(adminAuth);
			}
			user = new org.springframework.security.core.userdetails.User(myUser.getLogin(), myUser.getPassword(),
					grantedAuths);
		}
		return user;
	}

	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authenficate) throws IOException, ServletException
	{
		HttpSession session = request.getSession();
		if (session.getAttribute("projection") != null)
		{
			response.sendRedirect("/");
			return;
		}
		response.sendRedirect("/");
	}
}
