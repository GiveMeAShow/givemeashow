package giveme.common.beans;

import org.apache.log4j.Logger;

public class Video
{
	public static Logger	LOGGER;
	private int				id;
	private String			title;
	private int				seasonId;
	private int				showId;
	private ISOLang			language;
	private int				position;
	private boolean			isTransition;
	private String			relativePath;
	private long			viewed;
	private String			url;
	private Double			endIntroTime;
	private Double			startOutroTime;

	public long getViewed()
	{
		return viewed;
	}

	public void setViewed(long viewed)
	{
		this.viewed = viewed;
	}

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public String getTitle()
	{
		return title;
	}

	public void setTitle(String title)
	{
		this.title = title;
	}

	public int getSeasonId()
	{
		return seasonId;
	}

	public void setSeasonId(int seasonId)
	{
		this.seasonId = seasonId;
	}

	public ISOLang getLanguage()
	{
		return language;
	}

	public void setLanguage(ISOLang language)
	{
		this.language = language;
	}

	public int getPosition()
	{
		return position;
	}

	public void setPosition(int position)
	{
		this.position = position;
	}

	public boolean isTransition()
	{
		return isTransition;
	}

	public void setTransition(boolean isTransition)
	{
		this.isTransition = isTransition;
	}

	public String getRelativePath()
	{
		return relativePath;
	}

	public void setRelativePath(String relativePath)
	{
		this.relativePath = relativePath;
	}

	public String getUrl()
	{
		return url;
	}

	public int getShowId()
	{
		return showId;
	}

	public void setShowId(int showId)
	{
		this.showId = showId;
	}

	public Video()
	{
		LOGGER = Logger.getLogger(Video.class.getName());
	}

	public Double getEndIntroTime()
	{
		return endIntroTime;
	}

	public void setEndIntroTime(Double endIntroTime)
	{
		this.endIntroTime = endIntroTime;
	}

	public Double getStartOutroTime()
	{
		return startOutroTime;
	}

	public void setStartOutroTime(Double startOutroTime)
	{
		this.startOutroTime = startOutroTime;
	}

	public void setUrl(String url)
	{
		this.url = url;
	}

	public String asString()
	{
		return language.getLanguage();
	}
}
