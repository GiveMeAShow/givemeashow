package giveme.controllers.bindings;

import giveme.common.dao.VideoDao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

/**
 * Created by couty on 21/03/14.
 *
 */
@Component
@Repository
public class SharedBindings
{
	public static List<Integer>	positionChooser;

	@Autowired
	public VideoDao				videoDAO;

	public SharedBindings()
	{
		positionChooser = new ArrayList<Integer>();
		for (int i = 0; i < 50; i++)
		{
			positionChooser.add(i);
		}
	}
}
