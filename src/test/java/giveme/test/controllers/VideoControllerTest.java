package giveme.test.controllers;

import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;

import giveme.common.beans.ISOLang;
import giveme.common.beans.Season;
import giveme.common.beans.Show;
import giveme.common.beans.Video;
import giveme.common.dao.ISOLangDao;
import giveme.common.dao.InviteCodeDao;
import giveme.common.dao.SeasonDao;
import giveme.common.dao.ShowDao;
import giveme.common.dao.UserDao;
import giveme.common.dao.VideoDao;
import giveme.controllers.VideoController;
import giveme.inserters.AwsFilesAutoInserter;
import giveme.inserters.LocalFilesAutoInserter;
import giveme.services.EncryptionServices;
import giveme.services.FileExplorer;
import giveme.services.MailServices;
import giveme.services.VideoServices;
import giveme.shared.GiveMeProperties;

import org.assertj.core.api.Assertions;
import org.hamcrest.Matchers;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.ModelResultMatchers;
import org.springframework.web.context.WebApplicationContext;

public class VideoControllerTest
{
	@InjectMocks
	VideoController					videoController;

	@Autowired
	private WebApplicationContext	context;

	@Autowired
	private GiveMeProperties		giveMeAShowProperties;

	@Autowired
	private AwsFilesAutoInserter	awsAuto;

	@Autowired
	private VideoDao				videoDao;

	@Autowired
	private ISOLangDao				isoLangDao;

	@Autowired
	private SeasonDao				seasonDao;

	@Autowired
	private ShowDao					showDao;

	@Autowired
	private LocalFilesAutoInserter	localAuto;

	@Autowired
	FileExplorer					fileExplorer;

	@Autowired
	VideoServices					videoService;

	private MockMvc					mockMvc;

	@Before
	public void init()
	{
		// replace inject by mock
		giveMeAShowProperties = Mockito.mock(GiveMeProperties.class);
		awsAuto = Mockito.mock(AwsFilesAutoInserter.class);
		localAuto = Mockito.mock(LocalFilesAutoInserter.class);
		videoDao = Mockito.mock(VideoDao.class);
		isoLangDao = Mockito.mock(ISOLangDao.class);
		fileExplorer = Mockito.mock(FileExplorer.class);
		seasonDao = Mockito.mock(SeasonDao.class);
		showDao = Mockito.mock(ShowDao.class);
		videoService = Mockito.mock(VideoServices.class);

		// init
		initMocks(this);

		// setup
		mockMvc = standaloneSetup(videoController).build();
		// mockMvc =
		// MockMvcBuilders.webAppContextSetup(context).addFilters(springSecurityFilterChain).build();
	}

	@Test
	public void newFormTest() throws Exception
	{
		mockMvc.perform(get("/admin/video/new")).andExpect(view().name("/admin/video/choose.jsp"))
				.andExpect(model().attribute("selectedVideo", notNullValue()));
	}

	@Test
	public void autoInsertAwsTest() throws Exception
	{
		Mockito.doReturn(true).when(giveMeAShowProperties).isAWS();
		mockMvc.perform(get("/admin/video/auto")).andExpect(view().name("redirect:/admin/video/pending"));
	}

	@Test
	public void autoInsertLocalTest() throws Exception
	{
		Mockito.doReturn(false).when(giveMeAShowProperties).isAWS();
		mockMvc.perform(get("/admin/video/auto")).andExpect(view().name("redirect:/admin/video/pending"));
	}

	@Test
	public void pendingTest() throws Exception
	{
		Mockito.doReturn(Arrays.asList(new Video[]
		{ new Video() })).when(videoDao).listPEnding();
		Mockito.doReturn(false).when(giveMeAShowProperties).isAWS();
		mockMvc.perform(get("/admin/video/pending")).andExpect(view().name("/admin/video/list.jsp"))
				.andExpect(model().attribute("videoList", notNullValue()));
	}

	@Test
	public void addVideoPOSTTest() throws Exception
	{
		ISOLang isoLang = new ISOLang("French", "fr", "flag.png");
		Mockito.doReturn(isoLang).when(isoLangDao).findByISO(Mockito.anyString());

		Video video1 = new Video(1, "Video 1", 1, 1, isoLang, 1, false, "r/path", 0L, "url", 15.5, 146.2, "French",
				true, "postrUrl");

		Mockito.doReturn(false).when(giveMeAShowProperties).isAWS();
		mockMvc.perform(post("/admin/video/add").param("id", "1").param("langguage", "fr")).andExpect(
				view().name("redirect: /admin/video/new"));
	}

	@Test
	public void addVideoUpdatePOSTTest() throws Exception
	{
		ISOLang isoLang = new ISOLang("French", "fr", "flag.png");
		Mockito.doReturn(isoLang).when(isoLangDao).findByISO(Mockito.anyString());

		Video video1 = new Video(1, "Video 1", 1, 1, isoLang, 1, false, "r/path", 0L, "url", 15.5, 146.2, "French",
				true, "postrUrl");
		Mockito.doReturn(video1).when(videoDao)
				.findByShowAndSeasonIdsAndTitle(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyString());
		Mockito.doReturn(false).when(giveMeAShowProperties).isAWS();
		mockMvc.perform(post("/admin/video/add").param("id", "1").param("langguage", "fr")).andExpect(
				view().name("redirect: /admin/video/new"));
	}

	@Test
	public void addAVideoWithShowAndSeasonTest() throws Exception
	{
		mockMvc.perform(get("/admin/video/new/{showId}/{seasonId}", 1, 1))
				.andExpect(view().name("/admin/video/choose.jsp"))
				.andExpect(model().attribute("selectedVideo", notNullValue()));
	}

	@Test
	public void listVideosTest() throws Exception
	{
		ISOLang isoLang = new ISOLang("French", "fr", "flag.png");
		Video video1 = new Video(1, "Video 1", 1, 1, isoLang, 1, false, "r/path", 0L, "url", 15.5, 146.2, "French",
				true, "postrUrl");
		Video video2 = new Video(1, "Video 2", 1, 2, isoLang, 1, false, "r/path/2", 12L, "url/2", 17.5, 165.2,
				"French", true, "postrUrl");
		Mockito.doReturn(Arrays.asList(new Video[]
		{ video1, video2 })).when(fileExplorer).listVideos(Mockito.anyInt());

		MvcResult result = mockMvc.perform(get("/admin/webservices/video/listVideos/{directoryId}", 1)).andReturn();

		JSONArray videoList = new JSONArray(result.getResponse().getContentAsString());

		JSONObject videoJson = videoList.getJSONObject(0);
		Assertions.assertThat(videoJson.getString("title")).isEqualTo(video1.getTitle());
	}

	@Test
	public void buildVideoTest() throws Exception
	{
		ISOLang isoLang = new ISOLang("French", "fr", "flag.png");
		Video video1 = new Video(1, "Video 1", 1, 1, isoLang, 1, false, "r/path", 0L, "url", 15.5, 146.2, "French",
				true, "postrUrl");
		Season season1 = new Season(1, "Season 1", 1, "icon.png", 1);
		Show show = new Show(1, "Show 1", "url");
		ISOLang lang = new ISOLang("French", "ffr", "url/lang");
		Mockito.doReturn(lang).when(isoLangDao).findByISO(Mockito.anyString());
		Mockito.doReturn(show).when(showDao).findById(Mockito.anyInt());
		Mockito.doReturn(season1).when(seasonDao).findById(Mockito.anyInt());
		Mockito.doReturn(video1).when(videoDao).findById(Mockito.anyInt());

		mockMvc.perform(get("/admin/video/edit/{videoId}", 1)).andExpect(view().name("/admin/video/build-details.jsp"))
				.andExpect(model().attribute("seasons", notNullValue()))
				.andExpect(model().attribute("shows", notNullValue()))
				.andExpect(model().attribute("positionList", notNullValue()))
				.andExpect(model().attribute("langList", notNullValue()));
	}

	@Test
	public void getShuffledListTest() throws Exception

	{
		ISOLang isoLang = new ISOLang("French", "fr", "flag.png");
		Video video1 = new Video(1, "Video 1", 1, 1, isoLang, 1, false, "r/path", 0L, "url", 15.5, 146.2, "French",
				true, "postrUrl");
		Mockito.doReturn(Arrays.asList(new Video[]
		{ video1 })).when(videoDao).list();

		MvcResult result = mockMvc.perform(get("/webservices/video/shuffled")).andReturn();

		JSONArray videoList = new JSONArray(result.getResponse().getContentAsString());

		JSONObject videoJson = videoList.getJSONObject(0);
		Assertions.assertThat(videoJson.getString("title")).isEqualTo(video1.getTitle());
	}

	@Test
	public void buildVideoPOSTTest() throws Exception
	{
		Mockito.doReturn("urlVideo").when(videoService)
				.buildUrl(Mockito.any(HttpServletRequest.class), Mockito.anyString());

		mockMvc.perform(
				post("/admin/video/select").param("showId", "1").param("seasonId", "1").param("title", "Show 1")
						.param("path", "path/to/video")).andExpect(view().name("/admin/video/build-details.jsp"))
				.andExpect(model().attribute("video", notNullValue()));
	}
}
