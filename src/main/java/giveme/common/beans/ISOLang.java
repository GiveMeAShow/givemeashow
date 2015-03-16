package giveme.common.beans;

public class ISOLang
{
	private String	language;
	private String	iso;
	private String	flagUrl;

	public ISOLang()
	{
		// TODO Auto-generated constructor stub
	}

	public ISOLang(String language, String iso, String flagUrl)
	{
		super();
		this.language = language;
		this.iso = iso;
		this.flagUrl = flagUrl;
	}

	public String getLanguage()
	{
		return language;
	}

	public void setLanguage(String language)
	{
		this.language = language;
	}

	public String getIso()
	{
		return iso;
	}

	public void setIso(String iso)
	{
		this.iso = iso.toUpperCase();
	}

	public String getFlagUrl()
	{
		return flagUrl;
	}

	public void setFlagUrl(String flagUrl)
	{
		this.flagUrl = flagUrl;
	}
}
