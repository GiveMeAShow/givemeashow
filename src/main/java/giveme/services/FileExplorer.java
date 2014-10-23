package giveme.services;

import giveme.services.models.VideoFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.swing.filechooser.FileNameExtensionFilter;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Component
@Repository
public class FileExplorer
{
	private static final String				SEPARATOR		= "-";
	private final String					BASE_FOLDER;
	private final FileNameExtensionFilter	videoFormatFilter;
	private static final Logger				LOGGER			= Logger.getLogger(FileExplorer.class.getName());

	private final Map<Integer, String>		folderIdToPath	= new HashMap<Integer, String>();

	private final Map<Integer, VideoFile>	foldersMap		= new HashMap<Integer, VideoFile>();

	private Integer							folderCounter	= 0;

	public FileExplorer()
	{
		Properties props = new Properties();
		try
		{
			props.load(FileExplorer.class.getClassLoader().getResourceAsStream("givemeashow.properties"));
		} catch (IOException e)
		{
			e.printStackTrace();
		}

		BASE_FOLDER = System.getProperty("catalina.base") + props.getProperty("baseFolder");
		LOGGER.info("Base folder " + BASE_FOLDER);
		videoFormatFilter = new FileNameExtensionFilter("video extension filter", props.getProperty("extensions"));
		listFolders();
	}

	/**
	 * Used at INIT to list all folders
	 */
	private void listFolders()
	{
		LOGGER.info("Initilazing folder list");
		File baseFolder = new File(BASE_FOLDER);

		if (baseFolder != null && baseFolder.listFiles() != null && baseFolder.listFiles().length != 0)
		{
			fillFolderList(baseFolder, folderCounter);
		}
		else
		{
			LOGGER.error("Base folder is empty");
		}
	}

	/**
	 * Used at INIT to list all folders
	 * 
	 * @param folder
	 * @param parentId
	 */
	private void fillFolderList(File folder, Integer parentId)
	{

		folderCounter++;
		LOGGER.info("Adding directory id " + folderCounter + " for path " + folder.getAbsolutePath()
				+ " and parent is " + parentId);

		folderIdToPath.put(folderCounter, folder.getAbsolutePath());

		VideoFile bf = new VideoFile();
		bf.setPath(folder.getAbsolutePath());
		bf.setName(folder.getName());
		if (foldersMap.isEmpty())
		{
			bf.setRoot(true);
		}
		else
		{
			bf.setRoot(false);
		}
		bf.setFolderId(folderCounter);
		bf.setAVideo(false);
		bf.setParentfolderId(parentId);
		foldersMap.put(folderCounter, bf);

		for (File f : folder.listFiles())
		{
			if (f != null && f.isDirectory())
			{
				fillFolderList(f, bf.getFolderId());
			}
			else
			{
				LOGGER.debug(f.getName() + " is not a directory");
			}
		}
	}

	/**
	 * If ParentId == -1, then it is the root folder.
	 * 
	 * @param directoryId
	 * @param parentId
	 * @return
	 */
	public List<VideoFile> listVideos(int directoryId)
	{
		List<VideoFile> fileList = new ArrayList<VideoFile>();

		VideoFile vf = foldersMap.get(directoryId);

		if (vf != null)
		{
			// insert parent
			if (!vf.isRoot())
			{
				VideoFile rootFile = new VideoFile();
				rootFile.setName("...");
				rootFile.setFolderId(vf.getParentfolderId());
				rootFile.setParentfolderId(vf.getParentfolderId());
				rootFile.setPath(vf.getPath());
				rootFile.setAVideo(vf.isAVideo());
				rootFile.setRoot(vf.isRoot());
				fileList.add(rootFile);
			}

			// Get all directories there
			for (Integer directoryIds : foldersMap.keySet())
			{
				VideoFile d = foldersMap.get(directoryIds);
				if (!d.isRoot() && d.getParentfolderId() == directoryId)
				{
					fileList.add(d);
				}
			}

			// finish with files
			File directoryAsFile = new File(vf.getPath());
			if (directoryAsFile.listFiles() != null)
			{
				for (File f : directoryAsFile.listFiles())
				{
					if (!f.isDirectory())
					{
						VideoFile file = new VideoFile();
						file.setAVideo(true);
						file.setName(f.getName());
						file.setPath(f.getAbsolutePath());
						fileList.add(file);
					}
				}
			}
			File dir = new File(vf.getPath());
		}
		return fileList;
	}
}