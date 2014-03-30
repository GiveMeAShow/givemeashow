package giveme.controllers.bindings;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by couty on 21/03/14.
 *
 */
@Component
@Repository
public class SharedBindings {
    public static List<Integer> positionChooser;

    public SharedBindings()
    {
        positionChooser = new ArrayList<Integer>();
        for (int i = 0; i < 50; i++)
        {
            positionChooser.add(i);
        }
    }
}
