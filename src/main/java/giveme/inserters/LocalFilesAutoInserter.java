package giveme.inserters;

import giveme.common.beans.ISOLang;
import giveme.common.beans.Season;
import giveme.common.beans.Show;
import giveme.common.beans.Video;
import giveme.shared.GiveMeProperties;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.filechooser.FileNameExtensionFilter;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * This class is used to automatically update/insert shows, seasons and videos
 * in DB from a LOCAL DIRECTORY.
 * 
 * @author Axel
 * 
 */
@Component
public class LocalFilesAutoInserter
{
	private static final Logger		LOGGER	= Logger.getLogger(LocalFilesAutoInserter.class.getName());
	private FileNameExtensionFilter	videoFormatFilter;

	@Autowired
	public Inserter					inserter;

	@Autowired
	public GiveMeProperties			giveMeAShowProperties;

	/**
	 *
	 */
	public LocalFilesAutoInserter()
	{
		init();
	}

	public FileNameExtensionFilter getVideoFormatFilter() {
		return videoFormatFilter;
	}

	public void setVideoFormatFilter(FileNameExtensionFilter videoFormatFilter) {
		this.videoFormatFilter = videoFormatFilter;
	}


	public void setInserter(Inserter inserter) {
		this.inserter = inserter;
	}

	public void setGiveMeAShowProperties(GiveMeProperties giveMeAShowProperties) {
		this.giveMeAShowProperties = giveMeAShowProperties;
	}

	public void init() {
		try
		{
			videoFormatFilter = new FileNameExtensionFilter("video extension filter",
					giveMeAShowProperties.getVIDEO_EXT());
		} catch (Exception e)
		{
			LOGGER.warn("GiveMeASho properties not available !");
			videoFormatFilter = new FileNameExtensionFilter("video extension filter", "mp4");
		}
	}

	/**
	 * Folder structure : {BASE_FOLDER_PATH} | |-Show_Name_1 |
	 * |-show_name_1_banner.png | |-Season_1 | | |-season_1_banner.png | |
	 * |-video-1_en.webm | | |-video-1_fr.webm | | |-video-1_poster.png | |
	 * |-video-2_fr.webm | | |-video-2_poster.png | |-season_2 |-Show_Name_2 ...
	 */
	public void visitAll()
	{
		// Get shows names from the first folder level
		final File baseFolder = new File(giveMeAShowProperties.getBASE_FOLDER());
		visitShowFolder(baseFolder);
	}

	/**
	 * 
	 * @param baseFolder
	 * @return 
	 */
	public ArrayList<Show> visitShowFolder(final File baseFolder)
	{
		ArrayList<Show> showList = new ArrayList<Show>();
		if (baseFolder != null && baseFolder.listFiles() != null && baseFolder.listFiles().length != 0)
		{
			for (final File showFolder : baseFolder.listFiles())
			{
				final String showFolderName = showFolder.getName();
				final Show show = inserter.insertShow(showFolderName);
				// now add the seasons if they don't exist
				visitSeasonFolder(showFolder, show);
				showList.add(show);
			}
		}
		return showList;
	}

	/**
	 * 
	 * @param showFolder
	 * @param show
	 * @return 
	 */
	public List<Season> visitSeasonFolder(final File showFolder, final Show show)
	{
		List<Season> seasonList = new ArrayList<Season>();
		if (showFolder.listFiles() != null && showFolder.listFiles().length != 0)
		{
			for (final File seasonFolder : showFolder.listFiles())
			{
				if (seasonFolder.listFiles() != null && seasonFolder.listFiles().length != 0)
				{
					String seasonFolderName = seasonFolder.getName();

					final Season season = inserter.insertSeason(show, seasonFolderName);

					// Add all the files in a season folder. It can be a video,
					// a subtitle or a poster
					visitVideoFolder(show, seasonFolder, season);
					seasonList.add(season);
				}
			}
		}
		return seasonList;
	}

	/**
	 * 
	 * @param show
	 * @param seasonFolder
	 * @param season
	 */
	public void visitVideoFolder(final Show show, final File seasonFolder,
			final Season season)
	{
		if (seasonFolder.listFiles() != null && seasonFolder.listFiles().length != 0)
		{
			for (final File videoFolersByLang : seasonFolder.listFiles())
			{
				final String langIso = videoFolersByLang.getName();
				ISOLang lang = inserter.insertLang(langIso);

				for (File videoFile : videoFolersByLang.listFiles())
				{
					if (videoFormatFilter.accept(videoFile))
					{
						final String videoFileName = videoFile.getName();
						final String relativePath = videoFile.getAbsolutePath();
						Video video = new Video();
						createUrlAndRelativePath(relativePath, video);
						inserter.insertVideo(show, season, videoFileName, lang, relativePath, video);
					}
				}
			}
		}
	}

	public void createUrlAndRelativePath(String relativePath, Video video)
	{
		String path = relativePath.replace(giveMeAShowProperties.getBASE_FOLDER(), "");
		String url = "/showsDB/" + relativePath.replace(File.separatorChar, '/');
		video.setUrl(url);
		video.setRelativePath(path);
		LOGGER.info("video relative path : " + relativePath);
	}

}
