package giveme.test.dao;

import giveme.common.beans.Show;
import giveme.common.dao.ShowDao;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.apache.log4j.Logger;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations =
{ "classpath:gmas-db-cfg-test.xml" })
public class ShowDaoTest
{
	public static Logger		LOGGER		= Logger.getLogger(ShowDaoTest.class.getName());

	private final List<Show>	insertList	= new ArrayList<Show>();

	@Inject
	ShowDao						showDao;

	@Test
	@Ignore
	public void listTest()
	{
		try
		{

			final List<Show> showList = showDao.list();
			LOGGER.info(showList.size() + " shows found ");
			for (final Show show : showList)
			{
				LOGGER.info("found " + show.getName());
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	// @Test
	// public void saveTest()
	// {
	// LOGGER.info("Testing to save a show");
	// final Show newShow = new Show();
	// newShow.setName("American Dad");
	// newShow.setIconUrl("http://test.com/img");
	// showDao.save(newShow);
	// insertList.add(newShow);
	// LOGGER.info("Show inserted, id is : " + newShow.getId());
	// }
	//
	// @Test
	// public void updateTest()
	// {
	// LOGGER.info("Testing to update a show");
	// final Show s = new Show();
	// s.setName("Babar");
	// s.setIconUrl("urrrrl");
	// final ShowDao showDao = new ShowDao();
	// showDao.save(s);
	// insertList.add(s);
	// s.setName("Lol");
	// showDao.update(s);
	// final List<Show> showList = showDao.list();
	// for (final Show sh : showList)
	// {
	// LOGGER.info("Found : " + sh.getName());
	// }
	// }
	//
	// /**
	// * ONLY TO EMPTY THE SHOW TABLE
	// */
	// //@Test
	// public void cleanDatabase()
	// {
	// final ShowDao showDao = new ShowDao();
	// final List<Show> showList = showDao.list();
	// for (final Show s : showList)
	// {
	// showDao.delete(s);
	// }
	// }
	//
	// @After
	// public void cleanInserts()
	// {
	// final ShowDao showDao = new ShowDao();
	// for (final Show s : insertList)
	// {
	// showDao.delete(s);
	// }
	// }
}
