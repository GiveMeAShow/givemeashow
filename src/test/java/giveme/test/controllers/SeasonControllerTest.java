package giveme.test.controllers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.iterableWithSize;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;
import giveme.common.beans.ISOLang;
import giveme.common.beans.Season;
import giveme.common.beans.Show;
import giveme.common.beans.Video;
import giveme.common.dao.SeasonDao;
import giveme.common.dao.ShowDao;
import giveme.common.dao.VideoDao;
import giveme.controllers.SeasonController;
import giveme.controllers.bindings.SeasonAndShowName;

import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations =
{ "classpath:gmas-db-cfg-test.xml", "file:src/main/webapp/WEB-INF/mvc-dispatcher-servlet.xml" })
@WebAppConfiguration
public class SeasonControllerTest
{
	private static final Logger	LOGGER	= Logger.getLogger(SeasonControllerTest.class.getName());

	@InjectMocks
	SeasonController			seasonController;

	@Autowired
	SeasonDao					seasonDao;

	@Autowired
	ShowDao						showDao;

	@Autowired
	VideoDao					videoDao;

	private MockMvc				mockMvc;

	@Before
	public void init()
	{
		// replace inject by mock
		seasonDao = Mockito.mock(SeasonDao.class);
		showDao = Mockito.mock(ShowDao.class);
		videoDao = Mockito.mock(VideoDao.class);

		// init
		initMocks(this);

		// setup
		mockMvc = standaloneSetup(seasonController).build();
	}

	@Test
	public void constructorTest()
	{
		assertThat(seasonController).isNotNull();
		assertThat(seasonController.getPositionChooser().size()).isEqualTo(50);
	}

	/**
	 * Test list controller (jsp)
	 * 
	 * @throws Exception
	 */
	@Test
	public void listTest() throws Exception
	{

		mockSeasonList();

		mockMvc.perform(get("/admin/season/list")).andExpect(view().name("/admin/season/seasonList.jsp"))
				.andExpect(model().attribute("seasonList", notNullValue()))
				.andExpect(model().attribute("seasonList", iterableWithSize(3)))
				.andExpect(model().attribute("seasonList", hasItem(allOf(hasProperty("id", is(1))))))
				.andExpect(model().attribute("seasonList", hasItem(allOf(hasProperty("name", is("Season 2"))))))
				.andExpect(model().attribute("seasonList", hasItem(allOf(hasProperty("name", is("Season 1"))))))
				.andExpect(model().attribute("seasonList", hasItem(allOf(hasProperty("iconUrl", is("icon.png"))))))
				.andExpect(model().attribute("seasonList", hasItem(allOf(hasProperty("showId", is(2))))));
	}

	/**
	 * Test new season page (jsp)
	 * 
	 * @throws Exception
	 */
	@Test
	public void newSeasonTest() throws Exception
	{
		Show show1 = new Show(1, "Show 1", "icon.png");

		Mockito.doReturn(Arrays.asList(new Show[]
		{ show1 })).when(showDao).listNames();

		mockMvc.perform(get("/admin/season/new"))
				.andExpect(view().name("/admin/season/createNew.jsp"))
				.andExpect(model().attribute("seasonAndShowName", notNullValue()))
				.andExpect(
						model().attribute(
								"nameList",
								hasItem(allOf(hasProperty("id", is(1)), hasProperty("name", is("Show 1")),
										hasProperty("iconUrl", is("icon.png"))))))
				.andExpect(model().attribute("positionList", notNullValue()));
	}

	/**
	 * Test new season by show name page (jsp)
	 * 
	 * @throws Exception
	 */
	@Test
	public void adminNewSesonByShowNameTest() throws Exception
	{
		Show show1 = new Show(1, "Show 1", "icon.png");

		mockMvc.perform(get("/admin/season/new/{showName}", show1.getName()))
				.andExpect(view().name("/admin/season/createNew.jsp"))
				.andExpect(model().attribute("seasonAndShowName", notNullValue()))
				.andExpect(model().attribute("nameList", hasItem(show1.getName())))
				.andExpect(model().attribute("positionList", notNullValue()));
	}

	/**
	 * Show the entire seasonList for a show in an admin page (jsp)
	 * 
	 * @throws Exception
	 */
	@Test
	public void listByShowIdTest() throws Exception
	{
		Show show1 = new Show(1, "Show 1", "icon.png");

		mockSeasonList();

		MvcResult result = mockMvc.perform(get("/webservices/season/getByShowId/{showId}", show1.getId())).andReturn();
		String jsonResult = result.getResponse().getContentAsString();
		ObjectMapper mapper = new ObjectMapper();

		List<Season> seasonList = mapper.readValue(jsonResult,
				mapper.getTypeFactory().constructCollectionType(List.class, Season.class));
		LOGGER.info("returned json : " + jsonResult);
	}

	/**
	 * Show the entire seasonList for a show in an admin page (json)
	 * 
	 * @throws Exception
	 */
	@Test
	public void getSeasonByShowIdTest() throws Exception
	{
		Show show1 = new Show(1, "Show 1", "icon.png");

		mockSeasonList();

		mockMvc.perform(get("/admin/season/list/{showId}", show1.getId()))
				.andExpect(view().name("/admin/season/seasonList.jsp"))
				.andExpect(
						model().attribute(
								"seasonList",
								hasItem(allOf(hasProperty("name", is("Season 2")), hasProperty("id", is(2)),
										hasProperty("iconUrl", is("icon.png")),
										hasProperty("showId", is(show1.getId()))))))

		;
	}

	/**
	 * Valid a show and insert it ! (jsp)
	 * 
	 * @throws Exception
	 */
	@Test
	public void adminInsertNewSeasonTest() throws Exception
	{
		Show show1 = new Show(1, "Show 1", "icon.png");
		Mockito.doReturn(show1).when(showDao).findByName(Mockito.anyString());

		Season season1 = new Season(1, "Season 1", 1, "icon.png", 1);

		SeasonAndShowName seasonAndShowName = new SeasonAndShowName();
		seasonAndShowName.setSeason(season1);
		seasonAndShowName.setShowName(show1.getName());

		mockMvc.perform(
				post("/admin/season/addSeason").param("Season.id", Integer.toString(season1.getId()))
						.param("Season.name", season1.getName()).param("Season.iconUrl", season1.getIconUrl())
						.param("Season.position", Integer.toString(season1.getPosition()))
						.param("Season.showId", Integer.toString(season1.getShowId()))
						.param("showName", show1.getName()))
				.andExpect(view().name("/admin/season/validInsertion.jsp"))
				.andExpect(model().attribute("season", notNullValue()))
				.andExpect(
						model().attribute(
								"season",
								allOf(hasProperty("id", is(season1.getId())),
										hasProperty("name", is(season1.getName())),
										hasProperty("showId", is(season1.getShowId())),
										hasProperty("position", is(season1.getPosition())),
										hasProperty("iconUrl", is(season1.getIconUrl())))))

		;
	}

	@Test
	public void showSeasonDetailsTest() throws Exception
	{
		Season season1 = new Season(1, "Season 1", 1, "icon.png", 1);
		ISOLang lang = new ISOLang();
		lang.setIso("fr");
		lang.setFlagUrl("fflagurl");
		lang.setLanguage("French");

		Video video1 = new Video(1, "Video 1", season1.getId(), season1.getShowId(), lang, 1, false, "r/path", 0L,
				"url", 15.5, 146.2, "French", true, "postrUrl");
		Video video2 = new Video(2, "Video 2", season1.getId(), season1.getShowId(), lang, 2, false, "r/path2", 12574L,
				"url", 13.1, 127.6, "French", true, "postrUrl2");
		Mockito.doReturn(Arrays.asList(new Video[]
		{ video1, video2 })).when(videoDao).findBySeasonId(Mockito.anyInt());

		mockMvc.perform(get("/admin/season/{id}", season1.getId()))
				.andExpect(view().name("/admin/season/showSeason.jsp"))
				.andExpect(model().attribute("videoList", notNullValue()))
				.andExpect(
						model().attribute(
								"videoList",
								allOf(hasItem(allOf(hasProperty("title", is(video1.getTitle())),
										hasProperty("id", is(video1.getId())),
										hasProperty("posterUrl", is(video1.getPosterUrl())),
										hasProperty("endIntroTime", is(video1.getEndIntroTime())),
										hasProperty("isTransition", is(video1.getIsTransition())),
										hasProperty("language", is(video1.getLanguage())),
										hasProperty("posterUrl", is(video1.getPosterUrl())),
										hasProperty("languageIso", is(video1.getLanguageIso())),
										hasProperty("relativePath", is(video1.getRelativePath())),
										hasProperty("seasonId", is(video1.getSeasonId())),
										hasProperty("startOutroTime", is(video1.getStartOutroTime())),
										hasProperty("url", is(video1.getUrl())),
										hasProperty("viewed", is(video1.getViewed())),
										hasProperty("showId", is(video1.getShowId())))),

										hasItem(allOf(hasProperty("title", is(video2.getTitle())),
												hasProperty("id", is(video2.getId())),
												hasProperty("posterUrl", is(video2.getPosterUrl())),
												hasProperty("endIntroTime", is(video2.getEndIntroTime())),
												hasProperty("isTransition", is(video2.getIsTransition())),
												hasProperty("language", is(video2.getLanguage())),
												hasProperty("posterUrl", is(video2.getPosterUrl())),
												hasProperty("languageIso", is(video2.getLanguageIso())),
												hasProperty("relativePath", is(video2.getRelativePath())),
												hasProperty("seasonId", is(video2.getSeasonId())),
												hasProperty("startOutroTime", is(video2.getStartOutroTime())),
												hasProperty("url", is(video2.getUrl())),
												hasProperty("viewed", is(video2.getViewed())),
												hasProperty("showId", is(video2.getShowId())))))))

		;
	}

	/**
	 * The seasonDao will now return a list when calling {@link SeasonDao} .list
	 * .listByShowId
	 */
	private void mockSeasonList()
	{
		Season season1 = new Season(1, "Season 1", 1, "icon.png", 1);
		Season season2 = new Season(2, "Season 2", 2, "icon.png", 1);
		Season season3 = new Season(1, "Season 1", 1, "icon.png", 2);

		Mockito.doReturn(Arrays.asList(new Season[]
		{ season1, season2, season3 })).when(seasonDao).list();

		Mockito.doReturn(Arrays.asList(new Season[]
		{ season1, season2 })).when(seasonDao).listByShowId(Mockito.anyInt());
	}
}
