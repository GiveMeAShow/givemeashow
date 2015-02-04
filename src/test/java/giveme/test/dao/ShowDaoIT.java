package giveme.test.dao;

import giveme.common.dao.ShowDao;

import javax.inject.Inject;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations =
{ "classpath:gmas-db-cfg-test.xml" })
public class ShowDaoIT
{
	public static Logger	LOGGER	= Logger.getLogger(ShowDaoIT.class.getName());

	@Inject
	ShowDao					showDao;

	@Test
	public void saveIT()
	{
		// Show show = new Show();
		// show.setIconUrl("show1_banner.png");
		// show.setName("show1");
		// showDao.save(show);
		// showDao.list();
	}
}
