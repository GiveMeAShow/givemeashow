package giveme.shared;

import java.io.IOException;
import java.util.Properties;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

/**
 * Created by couty on 21/03/14.
 */
@Component
@Repository
public class GiveMeProperties
{
	public static String		BASE_FOLDER;
	private final Properties	props;
	public static int			MAX_INVITE;

	public GiveMeProperties()
	{
		props = new Properties();
		try
		{
			loadBaseFolder();
		} catch (IOException e)
		{
			e.printStackTrace();
		}

	}

	private void loadBaseFolder() throws IOException
	{
		props.load(GiveMeProperties.class.getClassLoader().getResourceAsStream("givemeashow.properties"));
		BASE_FOLDER = props.getProperty("baseFolder");
		MAX_INVITE = Integer.parseInt(props.getProperty("max_invite"));
	}
}
