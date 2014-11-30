/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package giveme.common.beans;

import java.io.Serializable;

public class User implements Serializable
{
	private static final long	serialVersionUID	= 1L;
	private Integer				id;
	private String				login;
	private boolean				isAdmin;
	private String				inviteCode;
	private String				password;
	private long				timeSpent;
	private boolean				useSubtitles;
	private boolean				confirmed;
	private String				email;
	private ISOLang				subDefaultLang;
	private ISOLang				defaultLang;
	private String				userRole;
	private Integer				invited;

	public Integer getInvited()
	{
		return invited;
	}

	public void setInvited(Integer invited)
	{
		this.invited = invited;
	}

	public String getUserRole()
	{
		return userRole;
	}

	public void setUserRole(String userRole)
	{
		this.userRole = userRole;
	}

	public User()
	{
	}

	public User(Integer id)
	{
		this.id = id;
	}

	public User(Integer id, String login, boolean isAdmin, String inviteCode, String password, long timeSpent,
			boolean useSubtitles, boolean confirmed, String email)
	{
		this.id = id;
		this.login = login;
		this.isAdmin = isAdmin;
		this.inviteCode = inviteCode;
		this.password = password;
		this.timeSpent = timeSpent;
		this.useSubtitles = useSubtitles;
		this.confirmed = confirmed;
		this.email = email;
	}

	public Integer getId()
	{
		return id;
	}

	public void setId(Integer id)
	{
		this.id = id;
	}

	public String getLogin()
	{
		return login;
	}

	public void setLogin(String login)
	{
		this.login = login;
	}

	public boolean getIsAdmin()
	{
		return isAdmin;
	}

	public void setIsAdmin(boolean isAdmin)
	{
		this.isAdmin = isAdmin;
	}

	public String getInviteCode()
	{
		return inviteCode;
	}

	public void setInviteCode(String inviteCode)
	{
		this.inviteCode = inviteCode;
	}

	public String getPassword()
	{
		return password;
	}

	public void setPassword(String password)
	{
		this.password = password;
	}

	public long getTimeSpent()
	{
		return timeSpent;
	}

	public void setTimeSpent(long timeSpent)
	{
		this.timeSpent = timeSpent;
	}

	public boolean getUseSubtitles()
	{
		return useSubtitles;
	}

	public void setUseSubtitles(boolean useSubtitles)
	{
		this.useSubtitles = useSubtitles;
	}

	public boolean getConfirmed()
	{
		return confirmed;
	}

	public void setConfirmed(boolean confirmed)
	{
		this.confirmed = confirmed;
	}

	public String getEmail()
	{
		return email;
	}

	public void setEmail(String email)
	{
		this.email = email;
	}

	public ISOLang getSubDefaultLang()
	{
		return subDefaultLang;
	}

	public void setSubDefaultLang(ISOLang subDefaultLang)
	{
		this.subDefaultLang = subDefaultLang;
	}

	public ISOLang getDefaultLang()
	{
		return defaultLang;
	}

	public void setDefaultLang(ISOLang defaultLang)
	{
		this.defaultLang = defaultLang;
	}

	public void setAdmin(boolean isAdmin)
	{
		this.isAdmin = isAdmin;
	}

	@Override
	public int hashCode()
	{
		int hash = 0;
		hash += (id != null ? id.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object)
	{
		// TODO: Warning - this method won't work in the case the id fields are
		// not set
		if (!(object instanceof User))
		{
			return false;
		}
		User other = (User) object;
		if ((id == null && other.id != null) || (id != null && !id.equals(other.id)))
		{
			return false;
		}
		return true;
	}

	@Override
	public String toString()
	{
		return "giveme.common.beans.User[ id=" + id + " ]";
	}

}
