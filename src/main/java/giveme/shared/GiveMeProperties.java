package giveme.shared;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

/**
 * Created by couty on 21/03/14.
 */
@Component
@Repository
public class GiveMeProperties
{
	@Value("${baseFolder")
	private String	BASE_FOLDER;

	@Value("${max_invite}")
	private int		MAX_INVITE;

	@Value("${extensions}")
	private String	VIDEO_EXT;

	@Value("${bannerSuffixe}")
	private String	BANNER_SUFFIX;

	@Value("${accessKey}")
	private String	AWKEY;

	@Value("${accessSecret}")
	private String	AWSECRET;

	@Value("${aws}")
	private boolean	AWS;

	public GiveMeProperties()
	{

	}

	public String getBASE_FOLDER()
	{
		return BASE_FOLDER;
	}

	public void setBASE_FOLDER(String bASE_FOLDER)
	{
		BASE_FOLDER = bASE_FOLDER;
	}

	public int getMAX_INVITE()
	{
		return MAX_INVITE;
	}

	public void setMAX_INVITE(int mAX_INVITE)
	{
		MAX_INVITE = mAX_INVITE;
	}

	public String getVIDEO_EXT()
	{
		return VIDEO_EXT;
	}

	public void setVIDEO_EXT(String vIDEO_EXT)
	{
		VIDEO_EXT = vIDEO_EXT;
	}

	public String getBANNER_SUFFIX()
	{
		return BANNER_SUFFIX;
	}

	public void setBANNER_SUFFIX(String bANNER_SUFFIX)
	{
		BANNER_SUFFIX = bANNER_SUFFIX;
	}

	public String getAWKEY()
	{
		return AWKEY;
	}

	public void setAWKEY(String aWKEY)
	{
		AWKEY = aWKEY;
	}

	public String getAWSECRET()
	{
		return AWSECRET;
	}

	public void setAWSECRET(String aWSECRET)
	{
		AWSECRET = aWSECRET;
	}

	public boolean isAWS()
	{
		return AWS;
	}

	public void setAWS(boolean aWS)
	{
		AWS = aWS;
	}

}
