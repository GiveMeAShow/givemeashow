package giveme.test.controllers;

import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.Mockito.mock;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;
import giveme.common.beans.ISOLang;
import giveme.common.dao.ISOLangDao;
import giveme.controllers.LangController;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:gmas-db-cfg-test.xml",
		"file:src/main/webapp/WEB-INF/mvc-dispatcher-servlet.xml" })
@WebAppConfiguration
public class LangControllerTest {

	private static final Logger	LOGGER	= Logger.getLogger(LangControllerTest.class
												.getName());

	//	public MockMvc	mockMvc;
	@InjectMocks
	LangController				langController;

	@Autowired
	ISOLangDao					isoLangDaoMock;

	private MockMvc				mockMvc;

	@Before
	public void init() {
		isoLangDaoMock = mock(ISOLangDao.class);
		initMocks(this);

		mockMvc = standaloneSetup(langController).build();
	}

	@Test
	public void showNewPageTest() throws Exception {
		mockMvc.perform(get("/admin/lang/new"))
				.andExpect(view().name("/admin/lang/createNew.jsp"))
				.andExpect(model().attribute("lang", notNullValue()));
	}

	@Test
	public void insertTest() throws Exception {
		ISOLang lang = new ISOLang();
		lang.setFlagUrl("url/icon.png");
		lang.setIso("fr");
		lang.setLanguage("French");

		mockMvc.perform(
				post("/admin/lang/insert")
						.param("language", "French").param("iso", "fr")
						.param("flagUrl", "urlicon.png"))
				.andExpect(view().name("/admin/lang/validInsertion.jsp"))
				.andExpect(model().attribute("lang", notNullValue()))
				.andExpect(
						model().attribute("lang",
								hasProperty("language", is("French"))))
				.andExpect(
						model().attribute("lang", hasProperty("iso", is("FR"))))
				.andExpect(
						model().attribute("lang",
								hasProperty("flagUrl", is("urlicon.png"))));
	}
}
