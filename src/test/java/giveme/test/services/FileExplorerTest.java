package giveme.test.services;

import giveme.services.FileExplorer;

import org.apache.log4j.Logger;

public class FileExplorerTest
{
	public static Logger	LOGGER	= Logger.getLogger(FileExplorerTest.class.getName());

	public void listTest()
	{
		LOGGER.info("TESTING FOLDER LIST");
		FileExplorer fe = new FileExplorer();
	}
}
