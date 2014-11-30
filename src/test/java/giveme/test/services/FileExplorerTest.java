package giveme.test.services;

import giveme.services.FileExplorer;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
public class FileExplorerTest
{
	public static Logger	LOGGER	= Logger.getLogger(FileExplorerTest.class.getName());

	@Test
	public void listTest()
	{
		LOGGER.info("TESTING FOLDER LIST");
		FileExplorer fe = new FileExplorer();
	}
}
