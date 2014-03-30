package giveme.shared;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.Properties;

/**
 * Created by couty on 21/03/14.
 */
@Component
@Repository
public class GiveMeProperties {
    public static String BASE_FOLDER;
    private Properties props;


    public GiveMeProperties()
    {
        props = new Properties();
        try {
            loadBaseFolder();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void loadBaseFolder() throws IOException {
        props.load(GiveMeProperties.class.getClassLoader()
                .getResourceAsStream("givemeashow.properties"));
        BASE_FOLDER = props.getProperty("baseFolder");
    }
}
