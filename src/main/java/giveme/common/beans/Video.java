package giveme.common.beans;

import org.apache.log4j.Logger;

public class Video
{
	public static Logger	LOGGER;
	private int				id;
	private String			title;
	private int				seasonId;
	private int				showId;
	private ISOLang			languageIso;
	private int				position;
	public boolean			isTransition;
	private String			relativePath;
	private long			viewed;
	private String			url;
	private Double			endIntroTime;
	private Double			startOutroTime;
	private String			language;

	public Video()
	{
		LOGGER = Logger.getLogger(Video.class.getName());
		endIntroTime = 0.0;
		startOutroTime = 0.0;
	}

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

	public ISOLang getLanguageIso()
	{
		return languageIso;
	}

	public void setLanguageIso(ISOLang language)
	{
		languageIso = language;
		this.language = language.getLanguage();
	}

	public String getLanguage()
	{
		return language;
	}

	public void setLanguage(String language)
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

	public boolean getIsTransition()
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

	public String languageHasString()
	{
		return languageIso.getLanguage();
	}
}
