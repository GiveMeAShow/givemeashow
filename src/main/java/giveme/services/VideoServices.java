package giveme.services;

import java.io.IOException;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

/**
 * Created by couty on 21/03/14.
 */
@Component
@Repository
public class VideoServices
{

	public static Logger	LOGGER;
	private String			baseFolderProperty;
	private String			baseVideoUrl;

	public VideoServices()
	{
		LOGGER = Logger.getLogger(VideoServices.class.getName());
		Properties props = new Properties();
		try
		{
			props.load(VideoServices.class.getClassLoader().getResourceAsStream("givemeashow.properties"));
			baseFolderProperty = System.getProperty("catalina.base") + props.getProperty("baseFolder");
			baseVideoUrl = props.getProperty("baseVideoUrl");
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	public String buildUrl(HttpServletRequest context, String path)
	{
		LOGGER.info("path1 " + path);
		LOGGER.info("basEfolder " + baseFolderProperty);
		LOGGER.info("removing " + baseFolderProperty + " : " + path);
		path = path.replace(baseFolderProperty, "");
		path = path.replaceFirst("\\\\", "");
		path = baseVideoUrl + path.replaceAll("\\\\", "/");

		StringBuilder urlBuilder = new StringBuilder();
		urlBuilder.append("http://");
		urlBuilder.append(context.getRemoteAddr());
		urlBuilder.append(":");
		urlBuilder.append(context.getLocalPort());
		urlBuilder.append(context.getContextPath());
		urlBuilder.append("/");

		urlBuilder.append(path.replaceAll("-", "/"));
		LOGGER.info("Compute url for path : " + path);
		LOGGER.info("computed url : " + urlBuilder.toString());
		return urlBuilder.toString();
	}
}
