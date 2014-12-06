package giveme.common.beans;

public class InviteCode
{
	private String	inviteCode;
	private int		userId;

	public InviteCode(String inviteCodeStr, Integer userId)
	{
		inviteCode = inviteCodeStr;
		this.userId = userId;
	}

	public InviteCode()
	{

	}

	public InviteCode(String inviteCode2)
	{
		inviteCode = inviteCode2;
	}

	public String getInviteCode()
	{
		return inviteCode;
	}

	public void setInviteCode(String inviteCode)
	{
		this.inviteCode = inviteCode;
	}

	public int getUserId()
	{
		return userId;
	}

	public void setUserId(int userId)
	{
		this.userId = userId;
	}

}
