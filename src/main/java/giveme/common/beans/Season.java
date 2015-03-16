package giveme.common.beans;

public class Season implements Comparable<Season>
{
	private int		id;
	private String	name;
	private int		position;
	private String	iconUrl;
	private int		showId;

	public Season()
	{
		// TODO Auto-generated constructor stub
	}

	public Season(int id, String name, int position, String iconUrl, int showId)
	{
		super();
		this.id = id;
		this.name = name;
		this.position = position;
		this.iconUrl = iconUrl;
		this.showId = showId;
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

	public int getPosition()
	{
		return position;
	}

	public void setPosition(int position)
	{
		this.position = position;
	}

	public String getIconUrl()
	{
		return iconUrl;
	}

	public void setIconUrl(String iconUrl)
	{
		this.iconUrl = iconUrl;
	}

	public int getShowId()
	{
		return showId;
	}

	public void setShowId(int showId)
	{
		this.showId = showId;
	}

	@Override
	public int compareTo(Season o)
	{
		return position - o.position;
	}
}
