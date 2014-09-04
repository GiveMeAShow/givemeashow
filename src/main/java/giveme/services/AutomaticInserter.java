package giveme.services;

import giveme.common.beans.Season;
import giveme.common.beans.Show;
import giveme.common.beans.Video;
import giveme.common.dao.ISOLangDao;
import giveme.common.dao.SeasonDao;
import giveme.common.dao.ShowDao;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

import javax.swing.filechooser.FileNameExtensionFilter;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

class AutomaticInserter
{
	private static final Logger LOGGER = Logger.getLogger(AutomaticInserter.class
			.getName());
	public static String BASE_FOLDER_PATH;
	private final FileNameExtensionFilter videoFormatFilter;
	private final String bannerSuffix;
	
	@Autowired
	ShowDao showDao;
	
	@Autowired
	SeasonDao seasonDao;
	
	@Autowired
	ISOLangDao languageDao;
	
	public AutomaticInserter()
	{
		final Properties props = new Properties();
		try
		{
			props.load(JdbcConnector.class.getClassLoader()
					.getResourceAsStream("givemeashow.properties"));
		}
		catch (final IOException e)
		{
			e.printStackTrace();
		}
		
		BASE_FOLDER_PATH = System.getProperty("catalina.base") + props.getProperty("baseFolder");
		LOGGER.info("Base folder " + BASE_FOLDER_PATH);
		videoFormatFilter = new FileNameExtensionFilter(
				"video extension filter", props.getProperty("extensions"));
		bannerSuffix = props.getProperty("bannerSuffixe");
		
		runAndBuildDatabase();
	}
	
	/**
	 * Folder structure :
	 * {BASE_FOLDER_PATH}
	 * |
	 * |-Show_Name_1
	 * | |-show_name_1_banner.png
	 * | |-Season_1
	 * | | |-season_1_banner.png
	 * | | |-video-1_en.webm
	 * | | |-video-1_fr.webm
	 * | | |-video-1_poster.png
	 * | | |-video-2_fr.webm
	 * | | |-video-2_poster.png
	 * | |-season_2
	 * |-Show_Name_2
	 * ...
	 */
	private void runAndBuildDatabase()
	{
		// Get shows names from the first folder level
		final File baseFolder = new File(BASE_FOLDER_PATH);
		if (baseFolder != null && baseFolder.listFiles() != null && baseFolder.listFiles().length != 0)
		{
			for (final File showFolder : baseFolder.listFiles())
			{
				final String showFolderName = showFolder.getName();
				final Show show = new Show();
				show.setName(showFolderName.replaceAll("_", ""));
				show.setIconUrl(showFolderName + File.separator + showFolderName.toLowerCase() + bannerSuffix);
				
				if (showDao.findByName(show.getName()) == null)
				{
					LOGGER.info("Show " + show.getName() + " with icon " + show.getIconUrl() + " and id "
							+ show.getId() + " has been saved");
					showDao.save(show);
				}
				else
				{
					LOGGER.info("Show " + show.getName() + " with icon " + show.getIconUrl() + " and id "
							+ show.getId() + " has been updated");
					showDao.update(show);
				}
				
				// now add the seasons if they don't exist
				if (showFolder.listFiles() != null && showFolder.listFiles().length != 0)
				{
					for (final File seasonFolder : showFolder.listFiles())
					{
						final String seasonFolderName = seasonFolder.getName();
						final Season season = new Season();
						season.setName(seasonFolderName.replaceAll("_", ""));
						season.setIconUrl(seasonFolderName + File.separator + seasonFolderName.toLowerCase()
								+ bannerSuffix);
						
						if (seasonDao.findByName(season.getName()) == null)
						{
							season.setShowId(show.getId());
							season.setPosition(extractPositionFromSeasonFolderName(seasonFolderName));
							seasonDao.save(season);
						}
						else
						{
							seasonDao.update(season);
						}
						
						// Add all the files in a season folder. It can be a video, a subtitle or a poster
						if (seasonFolder.listFiles() != null && seasonFolder.listFiles().length != 0)
						{
							for (final File videoFile : seasonFolder.listFiles())
							{
								final String videoFileName = videoFile.getName();
								
								// if it is a video
								if (videoFormatFilter.accept(videoFile))
								{
									final Video video = new Video();
									video.setSeasonId(season.getId());
									video.setShowId(show.getId());
									
									final String tempName = videoFileName.substring(0,
											videoFileName.lastIndexOf("-") - 1);
									video.setTitle(tempName.replaceAll("_", ""));
									video.setPosition(extractPositionFromVideoFileName(videoFileName));
									final String languageISO = videoFileName.substring(
											videoFileName.lastIndexOf("_") + 1,
											videoFileName.lastIndexOf("."));
									if (languageDao.findByISO(languageISO) != null)
									{
										video.setLanguageIso(languageDao.findByISO(languageISO));
									}
									// TODO Compute relative path
									//video.setRelativePath(relativePath);
									// TODO Compute URL ?
									// TODO Thumbnails
									video.setValidated(false);
									video.setViewed(0);
								}
							}
						}
						
					}
				}
			}
		}
	}
	
	private int extractPositionFromVideoFileName(final String videoFileName)
	{
		final int index = videoFileName.lastIndexOf("-");
		final int position = Integer.parseInt("" + videoFileName.charAt(index + 1));
		return position;
	}
	
	private int extractPositionFromSeasonFolderName(final String seasonFolderName)
	{
		final int position = Integer.parseInt(seasonFolderName.substring(seasonFolderName.lastIndexOf("_") + 1));
		LOGGER.debug("Position is " + position);
		return position;
	}
}
