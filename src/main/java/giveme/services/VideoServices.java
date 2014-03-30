package giveme.services;

import giveme.shared.GiveMeProperties;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import javax.servlet.http.HttpServletRequest;

import static giveme.shared.GiveMeProperties.BASE_FOLDER;

/**
 * Created by couty on 21/03/14.
 */
@Component
@Repository
public class VideoServices {

    public static Logger LOGGER;

    public VideoServices()
    {
        LOGGER = Logger.getLogger(VideoServices.class.getName());
    }

    public String buildUrl(HttpServletRequest context, String path)
    {
        StringBuilder urlBuilder = new StringBuilder();
        urlBuilder.append("http://");
        urlBuilder.append(context.getRemoteAddr());
        urlBuilder.append(context.getContextPath());
        //urlBuilder.append(BASE_FOLDER);
        urlBuilder.append("/");
        urlBuilder.append(path.replaceAll("-", "/"));
        LOGGER.info("computed url : " + urlBuilder.toString());
        return urlBuilder.toString();
    }
}
