package giveme.common.beans;

public class Show
{
	private int		id;
	private String	name;
	private String	iconUrl;

	public Show()
	{
		// TODO Auto-generated constructor stub
	}

	public Show(int id, String name, String iconUrl)
	{
		super();
		this.id = id;
		this.name = name;
		this.iconUrl = iconUrl;
	}

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getIconUrl()
	{
		return iconUrl;
	}

	public void setIconUrl(String iconUrl)
	{
		this.iconUrl = iconUrl;
	}
}
