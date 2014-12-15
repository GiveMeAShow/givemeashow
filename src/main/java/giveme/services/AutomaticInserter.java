package giveme.services;

import giveme.common.beans.ISOLang;
import giveme.common.beans.Season;
import giveme.common.beans.Show;
import giveme.common.beans.Video;
import giveme.common.dao.ISOLangDao;
import giveme.common.dao.SeasonDao;
import giveme.common.dao.ShowDao;
import giveme.common.dao.VideoDao;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

import javax.swing.filechooser.FileNameExtensionFilter;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Component
@Repository
public class AutomaticInserter
{
	private static final Logger				LOGGER	= Logger.getLogger(AutomaticInserter.class.getName());
	public static String					BASE_FOLDER_PATH;
	private final FileNameExtensionFilter	videoFormatFilter;
	private final String					bannerSuffix;

	@Autowired
	public ShowDao							showDao;

	@Autowired
	public SeasonDao						seasonDao;

	@Autowired
	public ISOLangDao						languageDao;

	@Autowired
	public VideoDao							videoDao;

	/**
	 *
	 */
	public AutomaticInserter()
	{
		final Properties props = new Properties();
		try
		{
			props.load(AutomaticInserter.class.getClassLoader().getResourceAsStream("givemeashow.properties"));
		} catch (final IOException e)
		{
			e.printStackTrace();
		}

		BASE_FOLDER_PATH = props.getProperty("baseFolder");
		LOGGER.info("Base folder " + BASE_FOLDER_PATH);
		videoFormatFilter = new FileNameExtensionFilter("video extension filter", props.getProperty("extensions"));
		bannerSuffix = props.getProperty("bannerSuffixe");
	}

	/**
	 * Folder structure : {BASE_FOLDER_PATH} | |-Show_Name_1 |
	 * |-show_name_1_banner.png | |-Season_1 | | |-season_1_banner.png | |
	 * |-video-1_en.webm | | |-video-1_fr.webm | | |-video-1_poster.png | |
	 * |-video-2_fr.webm | | |-video-2_poster.png | |-season_2 |-Show_Name_2 ...
	 */
	public void runAndFillDatabase()
	{
		// Get shows names from the first folder level
		final File baseFolder = new File(BASE_FOLDER_PATH);
		buildShows(baseFolder);
	}

	/**
	 * 
	 * @param baseFolder
	 */
	private void buildShows(final File baseFolder)
	{
		if (baseFolder != null && baseFolder.listFiles() != null && baseFolder.listFiles().length != 0)
		{
			for (final File showFolder : baseFolder.listFiles())
			{
				final String showFolderName = showFolder.getName();
				final Show show = new Show();
				show.setName(showFolderName.replaceAll("_", " "));
				final String showPath = showFolderName + File.separator + showFolderName.toLowerCase() + bannerSuffix;
				show.setIconUrl(showPath.replace(File.separatorChar, '\\'));
				final Show showInDb = showDao.findByName(show.getName());
				if (showInDb == null)
				{
					showDao.save(show);
					LOGGER.info("Show " + show.getName() + " with icon " + show.getIconUrl() + " and id "
							+ show.getId() + " has been saved");
				}
				else
				{
					show.setId(showInDb.getId());
					showDao.update(show);
					LOGGER.info("Show " + show.getName() + " with icon " + show.getIconUrl() + " and id "
							+ show.getId() + " has been updated");
				}

				// now add the seasons if they don't exist
				buildSeasonsFromShowFolder(showFolder, show);
			}
		}
	}

	/**
	 * 
	 * @param showFolder
	 * @param show
	 */
	private void buildSeasonsFromShowFolder(final File showFolder, final Show show)
	{
		if (showFolder.listFiles() != null && showFolder.listFiles().length != 0)
		{
			for (final File seasonFolder : showFolder.listFiles())
			{
				if (seasonFolder.listFiles() != null && seasonFolder.listFiles().length != 0)
				{
					final Season season = createSeason(show, seasonFolder);
					season.setShowId(show.getId());
					final Season seasonInDb = seasonDao.findByNameAndShowID(season.getName(), season.getShowId());
					if (seasonInDb == null)
					{
						seasonDao.save(season);
						LOGGER.info("Saved season " + season.getName() + " and id " + season.getId());
					}
					else
					{
						season.setId(seasonInDb.getId());
						LOGGER.info("Updated season " + season.getName() + " and id " + season.getId());
					}

					// Add all the files in a season folder. It can be a video,
					// a subtitle or a poster
					buildVideosFromSeasonFolder(show, seasonFolder, season);
				}
			}
		}
	}

	private String extractSeasonNameFromFile(File seasonFolder)
	{
		return seasonFolder.getName().replaceAll("_", " ");
	}

	/**
	 * 
	 * @param show
	 * @param seasonFolder
	 * @param season
	 */
	private void buildVideosFromSeasonFolder(final Show show, final File seasonFolder, final Season season)
	{
		if (seasonFolder.listFiles() != null && seasonFolder.listFiles().length != 0)
		{
			for (final File videoFolersByLang : seasonFolder.listFiles())
			{
				final String langIso = videoFolersByLang.getName();
				ISOLang lang = languageDao.findByISO(langIso);
				if (lang == null)
				{
					LOGGER.error("LANG NOT FOUND");
					return;
				}

				for (File videoFile : videoFolersByLang.listFiles())
				{
					if (videoFormatFilter.accept(videoFile))
					{
						final String videoFileName = videoFile.getName();
						Video video = new Video();
						video.setLanguageIso(lang);
						createUrlAndRelativePath(videoFile.getAbsolutePath(), video);
						createVideo(show, season, videoFileName, video);
						if (videoDao.findByShowAndSeasonIdsAndTitle(video.getShowId(), video.getSeasonId(),
								video.getTitle()) == null)
						{
							videoDao.save(video);
						}
					}
				}

			}
		}
	}

	private void createUrlAndRelativePath(String absolutePath, Video video)
	{
		String relativePath = absolutePath.replace(BASE_FOLDER_PATH, "");
		String url = "/showsDB" + relativePath.replace(File.separatorChar, '/');
		video.setUrl(url);
		video.setRelativePath(absolutePath);
		LOGGER.info("video relative path : " + relativePath);
	}

	/**
	 * 
	 * @param show
	 * @param season
	 * @param videoFileName
	 */
	private Video createVideo(final Show show, final Season season, final String videoFileName, Video video)
	{
		LOGGER.info("Creating video");
		video.setPosition(extractPositionFromVideoFolder(videoFileName));
		video.setSeasonId(season.getId());
		video.setShowId(show.getId());
		String tempName = "" + videoFileName.substring(videoFileName.lastIndexOf('-'));
		tempName = "" + tempName.subSequence(0, (videoFileName.lastIndexOf('.') - 1));
		video.setTitle(tempName.replaceAll("_", " "));

		// TODO Compute relative path
		// video.setRelativePath(relativePath);
		// TODO Compute URL ?
		// TODO Thumbnails
		video.setValidated(false);
		video.setViewed(0);
		return video;
	}

	/**
	 * 
	 * @param show
	 * @param seasonFolder
	 * @return
	 */
	private Season createSeason(final Show show, final File seasonFolder)
	{
		final String seasonFolderName = seasonFolder.getName();
		final Season season = new Season();
		season.setName(seasonFolderName.replaceAll("_", " "));
		season.setIconUrl(seasonFolderName + File.separator + seasonFolderName.toLowerCase() + bannerSuffix);
		season.setShowId(show.getId());
		season.setPosition(extractPositionFromSeasonFolderName(seasonFolderName));
		if (seasonDao.findByNameAndShowID(season.getName(), season.getShowId()) != null)
		{
			seasonDao.update(season);
		}
		LOGGER.info("Season \"" + season.getName() + "\" from show " + season.getShowId() + " at pos "
				+ season.getPosition() + " with icon " + season.getIconUrl() + " has been updated");

		return season;
	}

	/**
	 * 
	 * @param videoFileName
	 * @return
	 */
	private int extractPositionFromVideoFolder(final String videoFileName)
	{
		final int position = Integer.parseInt("" + videoFileName.subSequence(0, videoFileName.lastIndexOf('-')));
		return position;
	}

	/**
	 * 
	 * @param seasonFolderName
	 * @return
	 */
	private int extractPositionFromSeasonFolderName(final String seasonFolderName)
	{
		final int position = Integer.parseInt(seasonFolderName.substring(seasonFolderName.lastIndexOf("_") + 1));
		LOGGER.debug("Position is " + position);
		return position;
	}
}
