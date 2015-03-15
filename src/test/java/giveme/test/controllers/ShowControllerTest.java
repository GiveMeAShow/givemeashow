package giveme.test.controllers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import java.util.Arrays;
import java.util.List;

import giveme.common.beans.Season;
import giveme.common.beans.Show;
import giveme.common.dao.SeasonDao;
import giveme.common.dao.ShowDao;
import giveme.controllers.ShowController;

import org.apache.log4j.Logger;
import org.assertj.core.api.Assertions;
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
public class ShowControllerTest
{
	private static final Logger	LOGGER	= Logger.getLogger(ShowControllerTest.class.getName());

	@InjectMocks
	ShowController				showController;

	@Autowired
	ShowDao						showDao;

	@Autowired
	SeasonDao					seasonDao;

	private MockMvc				mockMvc;

	@Before
	public void init()
	{
		// replace inject by mock
		seasonDao = Mockito.mock(SeasonDao.class);
		showDao = Mockito.mock(ShowDao.class);

		// init
		initMocks(this);

		// setup
		mockMvc = standaloneSetup(showController).build();
	}

	@Test
	public void listTest() throws Exception
	{
		Show show1 = new Show(1, "Show 1", "icon/1");
		Show show2 = new Show(2, "Show 2", "icon/2");

		Mockito.doReturn(Arrays.asList(new Show[]
		{ show1, show2 })).when(showDao).list();

		MvcResult result = mockMvc.perform(get("/rest/shows/list")).andReturn();
		String jsonResult = result.getResponse().getContentAsString();
		ObjectMapper mapper = new ObjectMapper();

		List<Show> showList = mapper.readValue(jsonResult,
				mapper.getTypeFactory().constructCollectionType(List.class, Show.class));
		assertThat(showList.size()).isEqualTo(2);
		assertThat(showList.get(0).getId()).isEqualTo(show1.getId());
		assertThat(showList.get(0).getName()).isEqualTo(show1.getName());
		assertThat(showList.get(0).getIconUrl()).isEqualTo(show1.getIconUrl());
		assertThat(showList.get(1).getId()).isEqualTo(show2.getId());
		assertThat(showList.get(1).getName()).isEqualTo(show2.getName());
		assertThat(showList.get(1).getIconUrl()).isEqualTo(show2.getIconUrl());
		LOGGER.info("returned json : " + jsonResult);
	}
}