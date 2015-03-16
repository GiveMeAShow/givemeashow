package giveme.test.controllers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;
import giveme.common.beans.Season;
import giveme.common.beans.Show;
import giveme.common.dao.SeasonDao;
import giveme.common.dao.ShowDao;
import giveme.controllers.ShowController;

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
@ContextConfiguration(locations = { "classpath:gmas-db-cfg-test.xml",
		"file:src/main/webapp/WEB-INF/mvc-dispatcher-servlet.xml" })
@WebAppConfiguration
public class ShowControllerTest {
	private static final Logger	LOGGER	= Logger.getLogger(ShowControllerTest.class
												.getName());

	@InjectMocks
	ShowController				showController;

	@Autowired
	ShowDao						showDao;

	@Autowired
	SeasonDao					seasonDao;

	private MockMvc				mockMvc;

	@Before
	public void init() {
		// replace inject by mock
		seasonDao = Mockito.mock(SeasonDao.class);
		showDao = Mockito.mock(ShowDao.class);

		// init
		initMocks(this);

		// setup
		mockMvc = standaloneSetup(showController).build();
	}

	@Test
	public void listTest() throws Exception {
		Show show1 = new Show(1, "Show 1", "icon/1");
		Show show2 = new Show(2, "Show 2", "icon/2");

		Mockito.doReturn(Arrays.asList(new Show[] { show1, show2 }))
				.when(showDao).list();

		MvcResult result = mockMvc.perform(get("/rest/shows/list")).andReturn();
		String jsonResult = result.getResponse().getContentAsString();
		ObjectMapper mapper = new ObjectMapper();

		List<Show> showList = mapper.readValue(
				jsonResult,
				mapper.getTypeFactory().constructCollectionType(List.class,
						Show.class));
		assertThat(showList.size()).isEqualTo(2);
		assertThat(showList.get(0).getId()).isEqualTo(show1.getId());
		assertThat(showList.get(0).getName()).isEqualTo(show1.getName());
		assertThat(showList.get(0).getIconUrl()).isEqualTo(show1.getIconUrl());
		assertThat(showList.get(1).getId()).isEqualTo(show2.getId());
		assertThat(showList.get(1).getName()).isEqualTo(show2.getName());
		assertThat(showList.get(1).getIconUrl()).isEqualTo(show2.getIconUrl());
		LOGGER.info("returned json : " + jsonResult);
	}

	@Test
	public void listWSTest() throws Exception {
		Show show1 = new Show(1, "Show 1", "icon/1");
		Show show2 = new Show(2, "Show 2", "icon/2");

		Mockito.doReturn(Arrays.asList(new Show[] { show1, show2 }))
				.when(showDao).list();

		MvcResult result = mockMvc.perform(get("/webservices/show"))
				.andReturn();
		String jsonResult = result.getResponse().getContentAsString();
		ObjectMapper mapper = new ObjectMapper();

		List<Show> showList = mapper.readValue(
				jsonResult,
				mapper.getTypeFactory().constructCollectionType(List.class,
						Show.class));
		assertThat(showList.size()).isEqualTo(2);
		assertThat(showList.get(0).getId()).isEqualTo(show1.getId());
		assertThat(showList.get(0).getName()).isEqualTo(show1.getName());
		assertThat(showList.get(0).getIconUrl()).isEqualTo(show1.getIconUrl());
		assertThat(showList.get(1).getId()).isEqualTo(show2.getId());
		assertThat(showList.get(1).getName()).isEqualTo(show2.getName());
		assertThat(showList.get(1).getIconUrl()).isEqualTo(show2.getIconUrl());
		LOGGER.info("returned json : " + jsonResult);
	}

	@Test
	public void listAdminPageTest() throws Exception {
		Show show1 = new Show(1, "Show 1", "icon/1");
		Show show2 = new Show(2, "Show 2", "icon/2");

		Mockito.doReturn(Arrays.asList(new Show[] { show1, show2 }))
				.when(showDao).list();

		mockMvc.perform(get("/admin/show/list"))
				.andExpect(view().name("/admin/show/showList.jsp"))
				.andExpect(model().attribute("showList", notNullValue()))
				.andExpect(
						model().attribute(
								"showList",
								allOf(hasItem(allOf(
										hasProperty("id", is(show1.getId())),
										hasProperty("name", is(show1.getName())),
										hasProperty("iconUrl",
												is(show1.getIconUrl())))),
										hasItem(allOf(
												hasProperty("id",
														is(show2.getId())),
												hasProperty("name",
														is(show2.getName())),
												hasProperty("iconUrl",
														is(show2.getIconUrl())))))));

	}

	@Test
	public void showShowAdminPageTest() throws Exception {
		Show show1 = new Show(1, "Show 1", "icon/1");
		Mockito.doReturn(show1).when(showDao).findById(Mockito.anyInt());
		
		Season season1 = new Season(1, "Season 1", 1, "icon.png", 1);
		Season season2 = new Season(2, "Season 1", 2, "icon.png", 1);

		Mockito.doReturn(Arrays.asList(new Season[] { season1, season2 }))
				.when(seasonDao).listByShowId(Mockito.anyInt());
		
		mockMvc.perform(get("/admin/show/{showId}", show1.getId()))
				.andExpect(view().name("/admin/show/showShow.jsp"))
				.andExpect(model().attribute("seasonList", notNullValue()))
				.andExpect(model().attribute("show", notNullValue()));
	}

	@Test
	public void newShowAdminPageTest() throws Exception {
		mockMvc.perform(get("/admin/show/new"))
				.andExpect(view().name("/admin/show/createNew.jsp"))
				.andExpect(model().attribute("command", notNullValue()));
	}

	@Test
	public void insertNewShowAdminTest() throws Exception {
		mockMvc.perform(
				post("/admin/show/addShow").param("id", "1")
						.param("name", "Show 1").param("iconUrl", "url"))
				.andExpect(view().name("/admin/show/validInsertion.jsp"))
				.andExpect(model().attribute("show", notNullValue()));
	}
}