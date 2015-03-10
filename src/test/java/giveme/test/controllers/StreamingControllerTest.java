package giveme.test.controllers;

import static org.mockito.MockitoAnnotations.initMocks;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;
import giveme.controllers.StreamingController;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:gmas-db-cfg-test.xml",
		"file:src/main/webapp/WEB-INF/mvc-dispatcher-servlet.xml" })
@WebAppConfiguration
public class StreamingControllerTest {

	@InjectMocks
	StreamingController	streamingControllerMock;

	private MockMvc		mockMvc;

	@Before
	public void init() {
		initMocks(this);

		mockMvc = standaloneSetup(streamingControllerMock).build();
	}
	
	@Test
	public void welcomeTest() throws Exception {
		mockMvc.perform(get("/")).andExpect(view().name("/index.html"));
	}

}
