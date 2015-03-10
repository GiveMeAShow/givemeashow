package giveme.test.inserters;

import static org.assertj.core.api.Assertions.assertThat;
import giveme.common.beans.Season;
import giveme.common.beans.Show;
import giveme.common.beans.Video;
import giveme.inserters.Inserter;
import giveme.inserters.LocalFilesAutoInserter;
import giveme.shared.GiveMeProperties;

import java.io.File;
import java.util.List;

import javax.swing.filechooser.FileNameExtensionFilter;

import org.apache.log4j.Logger;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

/**
 * The purpose of the {@link LocalFilesAutoInserter} is only to send the correct datas to the inserter
 * @author Axel
 *
 */
public class LocalFilesAutoInserterTest {
	private static final Logger	LOGGER	= Logger.getLogger(LocalFilesAutoInserterTest.class
												.getName());

	@Before
	public void init() {

	}

	@Test
	public void initWithoutPropertiesTest() {
		LocalFilesAutoInserter lfai = new LocalFilesAutoInserter();
		assertThat(lfai.getVideoFormatFilter()).isNotNull();
		assertThat(lfai.getVideoFormatFilter().getExtensions()[0]).isEqualTo(
				"mp4");
	}

	@Test
	public void initWithPropertiesTest() {
		LocalFilesAutoInserter lfai = new LocalFilesAutoInserter();
		GiveMeProperties givemeashowproperties = new GiveMeProperties();
		givemeashowproperties.setVIDEO_EXT("mp4");
		lfai.setGiveMeAShowProperties(givemeashowproperties);
		lfai.init();
		Assertions.assertThat(lfai.getVideoFormatFilter()).isNotNull();
		assertThat(lfai.getVideoFormatFilter().getExtensions()[0]).isEqualTo(
				"mp4");
	}

	@Test
	public void creatUrlAndRelativePathTest() {
		LocalFilesAutoInserter mockAuto = Mockito
				.mock(LocalFilesAutoInserter.class);
		Mockito.doCallRealMethod()
				.when(mockAuto)
				.createUrlAndRelativePath(Mockito.anyString(),
						Mockito.any(Video.class));
		Mockito.doCallRealMethod().when(mockAuto)
				.setGiveMeAShowProperties(Mockito.any(GiveMeProperties.class));

		GiveMeProperties props = new GiveMeProperties();
		props.setBASE_FOLDER("src/test/resources/localTest/");
		mockAuto.setGiveMeAShowProperties(props);
		Video v = new Video();
		mockAuto.createUrlAndRelativePath(props.getBASE_FOLDER()
				+
				"American_Dad/Season_1/fr/bonjour.mp4", v);
		assertThat(v.getUrl())
				.as("Video url")
				.isEqualTo(
						"/showsDB/src/test/resources/localTest/American_Dad/Season_1/fr/bonjour.mp4");
		assertThat(v.getRelativePath()).isEqualTo(
				"American_Dad/Season_1/fr/bonjour.mp4");
	}

	@Test
	public void visitShowFolderTest() {
		LocalFilesAutoInserter mockAuto = Mockito
				.mock(LocalFilesAutoInserter.class);
		Mockito.doCallRealMethod().when(mockAuto)
				.visitShowFolder(Mockito.any(File.class));
		Mockito.doCallRealMethod().when(mockAuto)
				.setInserter(Mockito.any(Inserter.class));
		Inserter inserter = Mockito.mock(Inserter.class);
		
		Show show = new Show();
		show.setIconUrl("iconurl.png");
		show.setName("Show 1");
		show.setId(1);
		
		Mockito.doReturn(show).when(inserter).insertShow(Mockito.anyString());
		mockAuto.setInserter(inserter);

		GiveMeProperties props = new GiveMeProperties();
		props.setBASE_FOLDER("src/test/resources/localTest/");
		mockAuto.setGiveMeAShowProperties(props);
		File testFolder = new File("src/test/resources/localTest/");

		List<Show> showList = mockAuto.visitShowFolder(testFolder);

		LOGGER.info("Found " + showList.size() + " shows");
		assertThat(showList.size()).as("Number of shows").isEqualTo(2);
		assertThat(showList.get(0).getIconUrl()).as("First show icon url")
				.isEqualTo("iconurl.png");
	}

	@Test
	public void visitSeasonFolder() {
		LocalFilesAutoInserter mockAuto = Mockito
				.mock(LocalFilesAutoInserter.class);
		Mockito.doCallRealMethod().when(mockAuto)
				.visitSeasonFolder(Mockito.any(File.class),
						Mockito.any(Show.class));
		Mockito.doCallRealMethod().when(mockAuto)
				.setInserter(Mockito.any(Inserter.class));
		Inserter inserter = Mockito.mock(Inserter.class);


		Show show = new Show();
		show.setIconUrl("url/icon.png");
		show.setId(1);
		show.setName("Show 1");

		Season season = new Season();
		season.setIconUrl("iconUrl.png");
		season.setId(2);
		season.setName("Season 1");
		season.setPosition(1);
		season.setShowId(show.getId());

		Mockito.doReturn(season).when(inserter)
				.insertSeason(Mockito.any(Show.class), Mockito.anyString());
		mockAuto.setInserter(inserter);

		File showFolder = new File("src/test/resources/localTest/");

		List<Season> seasonList = mockAuto.visitSeasonFolder(showFolder, show);
		LOGGER.info("Found " + seasonList.size() + " seasons");
		assertThat(seasonList.size()).isEqualTo(2);
		assertThat(seasonList.get(0).getName()).isEqualTo("Season 1");
	}

	@Test
	public void visitVideoFolder() {
		LocalFilesAutoInserter mockAuto = Mockito
				.mock(LocalFilesAutoInserter.class);
		Mockito.doCallRealMethod()
				.when(mockAuto)
				.visitVideoFolder(Mockito.any(Show.class),
						Mockito.any(File.class), Mockito.any(Season.class));
		Mockito.doCallRealMethod().when(mockAuto)
				.setInserter(Mockito.any(Inserter.class));
		Mockito.doCallRealMethod()
				.when(mockAuto)
				.setVideoFormatFilter(
						Mockito.any(FileNameExtensionFilter.class));
		Inserter inserter = Mockito.mock(Inserter.class);
		FileNameExtensionFilter formatFilter = new FileNameExtensionFilter(
				"mp4", "mp4");
		mockAuto.setVideoFormatFilter(formatFilter);
		mockAuto.setInserter(inserter);

		Show show = new Show();
		show.setIconUrl("url/icon.png");
		show.setId(1);
		show.setName("Show 1");

		Season season = new Season();
		season.setName("Season 1");
		season.setIconUrl("Show_1/Season_1/icon.png");
		season.setPosition(1);
		season.setShowId(1);
		File seasonFolder = new File(
				"src/test/resources/localTest/Show_1/Season_1");
		mockAuto.visitVideoFolder(show, seasonFolder, season);
	}

	@Test
	public void visitAll() {
		LocalFilesAutoInserter mockAuto = Mockito
				.mock(LocalFilesAutoInserter.class);
		Mockito.doCallRealMethod()
				.when(mockAuto)
				.visitAll();
		Mockito.doCallRealMethod().when(mockAuto)
				.setGiveMeAShowProperties(Mockito.any(GiveMeProperties.class));

		GiveMeProperties props = new GiveMeProperties();
		props.setBASE_FOLDER("src/test/resources/localTest/");
		mockAuto.setGiveMeAShowProperties(props);

		mockAuto.visitAll();
	}
}
