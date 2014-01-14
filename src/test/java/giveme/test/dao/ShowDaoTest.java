package giveme.test.dao;

import giveme.common.beans.Show;
import giveme.common.dao.ShowDao;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Test;

public class ShowDaoTest {
	public static Logger LOGGER = Logger.getLogger(ShowDaoTest.class
	        .getName());
	
	private List<Show> insertList = new ArrayList<Show>();
	
	@Test
	public void listTest()
	{
		LOGGER.info("Testing show list");
		ShowDao showDao = new ShowDao();
		List<Show> showList = showDao.list();
		for(Show show : showList)
		{
			LOGGER.info("found " + show.getName());
		}
	}
	
	@Test
	public void saveTest()
	{
		LOGGER.info("Testing to save a show");
		Show newShow = new Show();
		newShow.setName("American Dad");
		newShow.setIconUrl("http://test.com/img");
		ShowDao showDao = new ShowDao();
		showDao.save(newShow);
		insertList.add(newShow);
		LOGGER.info("Show inserted, id is : " + newShow.getId());
	}
	
	@Test
	public void updateTest()
	{
		LOGGER.info("Testing to update a show");
		Show s = new Show();
		s.setName("Babar");
		s.setIconUrl("urrrrl");
		ShowDao showDao = new ShowDao();
		showDao.save(s);
		insertList.add(s);
		s.setName("Lol");
		showDao.update(s);
		List<Show> showList = showDao.list();
		for (Show sh : showList)
		{
			LOGGER.info("Found : " + sh.getName());
		}
	}
	
	/**
	 * ONLY TO EMPTY THE SHOW TABLE
	 */
	//@Test
	public void cleanDatabase()
	{
		ShowDao showDao = new ShowDao();
		List<Show> showList = showDao.list();
		for (Show s : showList)
		{
			showDao.delete(s);
		}
	}
	
	@After
	public void cleanInserts()
	{
		ShowDao showDao = new ShowDao();
		for (Show s : insertList)
		{
			showDao.delete(s);
		}
	}
}
