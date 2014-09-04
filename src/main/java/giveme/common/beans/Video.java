package giveme.common.beans;

import org.apache.log4j.Logger;

public class Video
{
	public static Logger LOGGER;
	private int id;
	private String title;
	private int seasonId;
	private int showId;
	private ISOLang languageIso;
	private int position;
	public boolean isTransition;
	private String relativePath;
	private long viewed;
	private String url;
	private Double endIntroTime;
	private Double startOutroTime;
	private String language;
	private boolean validated;
	private String posterUrl;
	
	public String getPosterUrl()
	{
		return posterUrl;
	}
	
	public void setPosterUrl(final String posterUrl)
	{
		this.posterUrl = posterUrl;
	}
	
	public boolean isValidated()
	{
		return validated;
	}
	
	public void setValidated(final boolean validated)
	{
		this.validated = validated;
	}
	
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
	
	public void setViewed(final long viewed)
	{
		this.viewed = viewed;
	}
	
	public int getId()
	{
		return id;
	}
	
	public void setId(final int id)
	{
		this.id = id;
	}
	
	public String getTitle()
	{
		return title;
	}
	
	public void setTitle(final String title)
	{
		this.title = title;
	}
	
	public int getSeasonId()
	{
		return seasonId;
	}
	
	public void setSeasonId(final int seasonId)
	{
		this.seasonId = seasonId;
	}
	
	public ISOLang getLanguageIso()
	{
		return languageIso;
	}
	
	public void setLanguageIso(final ISOLang language)
	{
		languageIso = language;
		this.language = language.getLanguage();
	}
	
	public String getLanguage()
	{
		return language;
	}
	
	public void setLanguage(final String language)
	{
		this.language = language;
	}
	
	public int getPosition()
	{
		return position;
	}
	
	public void setPosition(final int position)
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
	
	public void setTransition(final boolean isTransition)
	{
		this.isTransition = isTransition;
	}
	
	public String getRelativePath()
	{
		return relativePath;
	}
	
	public void setRelativePath(final String relativePath)
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
	
	public void setShowId(final int showId)
	{
		this.showId = showId;
	}
	
	public Double getEndIntroTime()
	{
		return endIntroTime;
	}
	
	public void setEndIntroTime(final Double endIntroTime)
	{
		this.endIntroTime = endIntroTime;
	}
	
	public Double getStartOutroTime()
	{
		return startOutroTime;
	}
	
	public void setStartOutroTime(final Double startOutroTime)
	{
		this.startOutroTime = startOutroTime;
	}
	
	public void setUrl(final String url)
	{
		this.url = url;
	}
	
	public String languageHasString()
	{
		return languageIso.getLanguage();
	}
}
