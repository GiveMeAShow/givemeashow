package giveme.inserters;

import giveme.common.beans.ISOLang;
import giveme.common.beans.Season;
import giveme.common.beans.Show;
import giveme.common.beans.Video;
import giveme.common.dao.ISOLangDao;
import giveme.common.dao.SeasonDao;
import giveme.common.dao.ShowDao;
import giveme.common.dao.VideoDao;
import giveme.shared.GiveMeProperties;

import java.io.File;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Inserter
{
	public Inserter()
	{
		// TODO Auto-generated constructor stub
	}

	private static final Logger	LOGGER	= Logger.getLogger(Inserter.class.getName());

	@Autowired
	public ShowDao				showDao;

	@Autowired
	public SeasonDao			seasonDao;

	@Autowired
	public VideoDao				videoDao;

	@Autowired
	public ISOLangDao			languageDao;

	@Autowired
	public GiveMeProperties		giveMeAShowProperties;

	public Show insertShow(String rawShowName)
	{
		final Show show = new Show();

		show.setName(rawShowName.replaceAll("_", " "));
		final String showPath = rawShowName + File.separator + rawShowName.toLowerCase()
				+ giveMeAShowProperties.getBANNER_SUFFIX();
		// TODO maybe a mistake there, replacing with \\.
		show.setIconUrl(showPath.replace(File.separatorChar, '\\'));
		final Show showInDb = showDao.findByName(show.getName());
		if (showInDb == null)
		{
			showDao.save(show);
			LOGGER.info("Show " + show.getName() + " with icon " + show.getIconUrl() + " and id " + show.getId()
					+ " has been saved");
		}
		else
		{
			show.setId(showInDb.getId());
			showDao.update(show);
			LOGGER.info("Show " + show.getName() + " with icon " + show.getIconUrl() + " and id " + show.getId()
					+ " has been updated");
		}
		return show;
	}

	public Season insertSeason(final Show show, String seasonFolderName)
	{

		final Season season = createSeason(show, seasonFolderName);

		season.setShowId(show.getId());
		final Season seasonInDb = seasonDao.findBy(season.getName(), season.getShowId());
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
		return season;
	}

	/**
	 * 
	 * @param show
	 * @param season
	 * @param videoFileName
	 * @param lang
	 * @param relativePath
	 * @param video
	 */
	public Video insertVideo(final Show show, final Season season, final String videoFileName, ISOLang lang,
			String relativePath, Video video)
	{
		video = createVideo(show, season, videoFileName, lang, relativePath, video);
		if (videoDao.findByShowAndSeasonIdsAndTitle(video.getShowId(), video.getSeasonId(), video.getTitle()) == null)
		{
			videoDao.save(video);
		}
		return video;
	}

	/**
	 * 
	 * @param langIsoString
	 * @return
	 */
	public ISOLang insertLang(String langIsoString)
	{
		ISOLang isoLang = languageDao.findByISO(langIsoString);
		if (isoLang == null)
		{
			isoLang = new ISOLang();
			isoLang.setLanguage(langIsoString);
			languageDao.save(isoLang);
			LOGGER.error("LANG NOT FOUND");
		}
		return isoLang;
	}

	/**
	 * 
	 * @param show
	 * @param seasonFolderName
	 * @return
	 */
	private Season createSeason(final Show show, final String seasonFolderName)
	{
		final Season season = new Season();
		season.setName(seasonFolderName.replaceAll("_", " "));
		season.setIconUrl(seasonFolderName + File.separator + seasonFolderName.toLowerCase()
				+ giveMeAShowProperties.getBANNER_SUFFIX());
		season.setShowId(show.getId());
		extractPosition(seasonFolderName, season);
		if (seasonDao.findBy(season.getName(), season.getShowId()) != null)
		{
			seasonDao.update(season);
		}
		LOGGER.info("Season \"" + season.getName() + "\" from show " + season.getShowId() + " at pos "
				+ season.getPosition() + " with icon " + season.getIconUrl() + " has been updated");

		return season;
	}

	private Video createVideo(final Show show, final Season season, final String videoFileName, ISOLang lang,
			String relativePath, Video video)
	{
		LOGGER.info("Creating video");
		video.setLanguageIso(lang);
		extractPosition(videoFileName, video);
		video.setSeasonId(season.getId());
		video.setShowId(show.getId());
		String tempName = "" + videoFileName.substring(videoFileName.indexOf('-') + 1);
		tempName = "" + tempName.subSequence(0, (tempName.lastIndexOf('.')));
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
	 * @param season
	 * @param seasonFolderName
	 * @return
	 */
	private void extractPosition(final String seasonFolderName, Season season)
	{
		final int position = Integer.parseInt(seasonFolderName.substring(seasonFolderName.lastIndexOf("_") + 1));
		season.setPosition(position);
		LOGGER.debug("Position is " + position);
	}

	/**
	 * 
	 * @param videoFileName
	 * @return
	 */
	private void extractPosition(final String videoFileName, Video video)
	{
		final int position = Integer.parseInt("" + videoFileName.subSequence(0, videoFileName.indexOf('-')));
		video.setPosition(position);
	}

}
