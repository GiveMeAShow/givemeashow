package giveme.test.inserters;

import static org.assertj.core.api.Assertions.assertThat;
import giveme.common.beans.ISOLang;
import giveme.common.beans.Season;
import giveme.common.beans.Show;
import giveme.common.beans.Video;
import giveme.common.dao.ISOLangDao;
import giveme.common.dao.SeasonDao;
import giveme.common.dao.ShowDao;
import giveme.common.dao.VideoDao;
import giveme.inserters.Inserter;
import giveme.shared.GiveMeProperties;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class InserterTest
{
	Inserter	inserter;

	public InserterTest()
	{
		// TODO Auto-generated constructor stub
	}

	private static final Logger	LOGGER	= Logger.getLogger(InserterTest.class.getName());

	@Before
	public void init()
	{
		inserter = new Inserter();
		GiveMeProperties givemeAShowProperties = new GiveMeProperties();
		givemeAShowProperties.setBANNER_SUFFIX("_banner.png");
		givemeAShowProperties.setVIDEO_EXT("mp4");
		inserter.setGiveMeAShowProperties(givemeAShowProperties);
	}

	@Test
	public void insertNewShowTest()
	{
		String showName = "Show_1";
		ShowDao showDao = Mockito.mock(ShowDao.class);
		Mockito.doReturn(null).when(showDao).findByName(showName);
		inserter.setShowDao(showDao);

		Show show = new Show();
		show = inserter.insertShow(showName);

		assertThat(show.getName()).as("show name").isEqualTo("Show 1");
	}

	@Test
	public void updateShowTest()
	{
		String showName = "Show_1";
		ShowDao showDao = Mockito.mock(ShowDao.class);

		Show show = createShow(showName);
		show.setId(4);
		show.setName(showName);
		show.setIconUrl("icon.png");

		Mockito.doReturn(show).when(showDao).findByName(Mockito.anyString());
		inserter.setShowDao(showDao);

		show = inserter.insertShow(showName);

		assertThat(show.getId()).as("show id").isEqualTo(4);
		assertThat(show.getIconUrl()).as("show iconurl").isEqualTo("Show_1\\show_1_banner.png");
		assertThat(show.getName()).as("show name").isEqualTo("Show 1");
	}

	@Test
	public void insertNewSeasonTest()
	{
		String seasonFolderName = "Season_1";
		Show show = createShow("Show 1");
		SeasonDao seasonDao = Mockito.mock(SeasonDao.class);
		Mockito.doReturn(null).when(seasonDao).findBy(Mockito.anyString(), Mockito.anyInt());
		inserter.setSeasonDao(seasonDao);

		Season season = inserter.insertSeason(show, seasonFolderName);

		assertThat(season.getName()).as("season name").isEqualTo("Season 1");
	}

	@Test
	public void insertLangTest()
	{
		String langIsoString = "fr";
		ISOLangDao isoLangDao = Mockito.mock(ISOLangDao.class);
		inserter.setLanguageDao(isoLangDao);
		ISOLang lang = inserter.insertLang(langIsoString);
		assertThat(lang.getLanguage()).isEqualTo(langIsoString);
	}

	@Test
	public void updateSeasonTest()
	{
		String seasonFolderName = "Season_1";
		Show show = createShow("Show 1");
		SeasonDao seasonDao = Mockito.mock(SeasonDao.class);
		Season season = createSeason(seasonFolderName);
		Mockito.doReturn(season).when(seasonDao).findBy(Mockito.anyString(), Mockito.anyInt());
		inserter.setSeasonDao(seasonDao);

		season = inserter.insertSeason(show, seasonFolderName);

		assertThat(season.getName()).as("season name").isEqualTo("Season 1");
		assertThat(season.getId()).as("season id").isEqualTo(3);
		assertThat(season.getPosition()).as("season position").isEqualTo(1);
		assertThat(season.getShowId()).as("show is").isEqualTo(show.getId());
		assertThat(season.getIconUrl()).as("season iconurl").isEqualTo("Season_1\\season_1_banner.png");
	}

	@Test
	public void insertVideoTest()
	{
		Show show = createShow("Show 1");
		Season season = createSeason("Season 1");
		String relativePath = "path/to/video.mp4";
		String videoFileName = "07-Les_etoiles_dans_le_ciel.mp4";
		String videoUrl = "url.mp4";
		ISOLang lang = createIsoLang();
		Video video = new Video();
		video.setShowId(show.getId());
		video.setSeasonId(season.getId());
		video.setRelativePath(relativePath);
		video.setUrl(videoUrl);
		VideoDao videoDao = Mockito.mock(VideoDao.class);
		inserter.setVideoDao(videoDao);
		inserter.insertVideo(show, season, videoFileName, lang, relativePath, video);

		assertThat(video.getTitle()).isEqualTo("Les etoiles dans le ciel");
		assertThat(video.getPosition()).isEqualTo(7);
		assertThat(video.getIsTransition()).isEqualTo(false);
		assertThat(video.getEndIntroTime()).isEqualTo(0.0);
		assertThat(video.getId()).isEqualTo(0);
		assertThat(video.getLanguage()).isEqualTo("French");
		assertThat(video.getPosterUrl()).isEqualTo(null);
		assertThat(video.getRelativePath()).isEqualTo(relativePath);
		assertThat(video.getSeasonId()).isEqualTo(season.getId());
		assertThat(video.getShowId()).isEqualTo(show.getId());
		assertThat(video.getStartOutroTime()).isEqualTo(0.0);
		assertThat(video.getUrl()).isEqualTo(videoUrl);
		assertThat(video.getViewed()).isEqualTo(0);
	}

	private ISOLang createIsoLang()
	{
		ISOLang lang = new ISOLang();
		lang.setFlagUrl("flag.png");
		lang.setIso("fr");
		lang.setLanguage("French");

		return lang;
	}

	private Season createSeason(String name)
	{
		Season season = new Season();
		season.setIconUrl("season.png");
		season.setId(3);
		season.setName(name);
		season.setPosition(3);
		season.setShowId(4);
		return season;
	}

	private Show createShow(String name)
	{
		Show show = new Show();
		show.setId(4);
		show.setName(name);
		show.setIconUrl("icon.png");
		return show;
	}
}
