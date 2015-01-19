package giveme.inserters;

import giveme.common.beans.ISOLang;
import giveme.common.beans.Season;
import giveme.common.beans.Show;
import giveme.shared.GiveMeProperties;

import java.io.File;

import javax.swing.filechooser.FileNameExtensionFilter;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

/**
 * This class is used to automatically update/insert shows, seasons and videos
 * in DB from a LOCAL DIRECTORY.
 * @author Axel
 *
 */
@Component
@Repository
public class LocalFilesAutoInserter implements IAutomaticInserter
{
	private static final Logger				LOGGER	= Logger.getLogger(LocalFilesAutoInserter.class.getName());
	private final FileNameExtensionFilter	videoFormatFilter;

	@Autowired
	public Inserter							inserter;

	/**
	 *
	 */
	public LocalFilesAutoInserter()
	{
		videoFormatFilter = new FileNameExtensionFilter("video extension filter", GiveMeProperties.VIDEO_EXT);
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
		final File baseFolder = new File(GiveMeProperties.BASE_FOLDER);
		visitShowFolder(baseFolder);
	}

	/**
	 * 
	 * @param baseFolder
	 */
	private void visitShowFolder(final File baseFolder)
	{
		if (baseFolder != null && baseFolder.listFiles() != null && baseFolder.listFiles().length != 0)
		{
			for (final File showFolder : baseFolder.listFiles())
			{
				final String showFolderName = showFolder.getName();
				final Show show = inserter.insertShow(showFolderName);

				// now add the seasons if they don't exist
				visitSeasonFolder(showFolder, show);
			}
		}
	}

	/**
	 * 
	 * @param showFolder
	 * @param show
	 */
	private void visitSeasonFolder(final File showFolder, final Show show)
	{
		if (showFolder.listFiles() != null && showFolder.listFiles().length != 0)
		{
			for (final File seasonFolder : showFolder.listFiles())
			{
				if (seasonFolder.listFiles() != null && seasonFolder.listFiles().length != 0)
				{
					String seasonFolderName = seasonFolder.getName();

					final Season season = inserter.insertSeason(show,
							seasonFolderName);

					// Add all the files in a season folder. It can be a video,
					// a subtitle or a poster
					visitVideoFolder(show, seasonFolder, season);
				}
			}
		}
	}

	/**
	 * 
	 * @param show
	 * @param seasonFolder
	 * @param season
	 */
	private void visitVideoFolder(final Show show, final File seasonFolder, final Season season)
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

						inserter.insertVideo(show, season, videoFileName, lang,
								relativePath);
					}
				}
			}
		}
	}

}
