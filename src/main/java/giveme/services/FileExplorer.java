package giveme.services;

import giveme.services.models.VideoFile;

import java.io.File;
import java.io.IOException;
import java.util.*;

import javax.swing.filechooser.FileNameExtensionFilter;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Component
@Repository
public class FileExplorer {
	private static final String SEPARATOR = "-";
	private final String BASE_FOLDER;
	private final FileNameExtensionFilter videoFormatFilter;
	private static final Logger LOGGER = Logger.getLogger(FileExplorer.class
			.getName());
    private Map<Integer, String> folderIdToPath = new HashMap<Integer, String>();
    private Integer folderCounter = 0;

	public FileExplorer() {
        Properties props = new Properties();
		try {
			props.load(JdbcConnector.class.getClassLoader()
					.getResourceAsStream("givemeashow.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		BASE_FOLDER = props.getProperty("baseFolder");
		videoFormatFilter = new FileNameExtensionFilter(
				"video extension filter", props.getProperty("extensions"));
        listFolders();
	}

    private void listFolders() {
        LOGGER.info("Initilazing folder list");
        File baseFolder = new File(BASE_FOLDER);
        if (baseFolder != null && baseFolder.listFiles() != null && baseFolder.listFiles().length != 0)
        {
            fillFolderList(baseFolder);
        }
        else
        {
            LOGGER.error("Base folder is empty");
        }
    }

    private void fillFolderList(File folder) {
        folderCounter ++;
        LOGGER.info("Adding directory id "  + folderCounter + " for path " + folder.getAbsolutePath());
        folderIdToPath.put(folderCounter, folder.getAbsolutePath());
        for (File f : folder.listFiles())
        {
            if (f != null && f.isDirectory())
            {
                fillFolderList(f);
            }
            else
            {
                LOGGER.debug(f.getName() + " is not a directory");
            }
        }
    }

    public List<VideoFile> listVideos(int directoryId) {
        List<VideoFile> fileList = new ArrayList<VideoFile>();
        String path = folderIdToPath.get(directoryId);
        VideoFile vf = new VideoFile();
        vf.setName("...");
        vf.setRoot(true);
        vf.setAVideo(false);
        fileList.add(vf);

        if ( path!= null)
        {
            File directory = new File(folderIdToPath.get(directoryId));
            if (directory.listFiles() != null)
            {
                for (File f : directory.listFiles())
                {
                    vf = new VideoFile();
                    if (f.isDirectory())
                    {
                        vf.setAVideo(false);
                    }
                    else
                    {
                        vf.setAVideo(true);
                    }
                    vf.setName(f.getName());
                    fileList.add(vf);
                }
            }
        }
        return fileList;
    }

//    /**
//     * Need to be rerfactorized
//     * Used to list video in a path, it needs to convert url compliant path and handle root path
//     * @param path
//     * @return
//     */
//	public List<VideoFile> listVideos(String path) {
//		path = path.replaceAll(SEPARATOR, "/");
//		List<VideoFile> videos = new ArrayList<VideoFile>();
//		File folder = null;
//		if (!"base".equals(path) && !"<- back".equals(path) && !BASE_FOLDER.contains(path)) {
//			path = BASE_FOLDER + "/" + path;
//			folder = new File(path);
//			VideoFile back = new VideoFile();
//			back.setAVideo(false);
//			String backPath = folder.getParentFile().getAbsolutePath();
//			System.out.println(backPath + " VS " + BASE_FOLDER);
//			if (BASE_FOLDER.equals(backPath))
//			{
//				back.setPath(folder.getParentFile().getName());
//			}
//			else
//			{
//				System.out.println("back path " + backPath);
//				backPath = backPath.replace(BASE_FOLDER, "");
//				System.out.println("cleaned: " + backPath);
//				back.setPath(backPath);
//			}
//			videos.add(back);
//		} else{
//			folder = new File(BASE_FOLDER);
//		}
//		System.out.println(	"exploring " + path);
//		if (folder != null) {
//			for (File file : folder.listFiles()) {
//				VideoFile vf = new VideoFile();
//				String filePath = file.getAbsolutePath();
//				filePath = filePath.replace(BASE_FOLDER + "/", "");
//				filePath = filePath.replace("/", SEPARATOR);
//				vf.setPath(filePath);
//				if (videoFormatFilter.accept(file) && !file.isDirectory()) {
//					vf.setName(file.getName());
//					vf.setAVideo(true);
//					LOGGER.debug("Video : " + file.getPath() + " added.");
//					videos.add(vf);
//				} else if (file.isDirectory()) {
//					vf.setName(file.getName());
//					videos.add(vf);
//					LOGGER.debug("Directory : " + file.getPath() + " added.");
//				}
//			}
//		}
//		return videos;
//	}
}
