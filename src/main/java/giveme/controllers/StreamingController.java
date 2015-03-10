package giveme.controllers;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class StreamingController
{
	private static final Logger	LOGGER	= Logger.getLogger(StreamingController.class.getName());

	/**
	 * Show the entire showList for an admin.
	 * 
	 * @return
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView welcome()
	{
		ModelAndView mdv = new ModelAndView("/index.html");
		return mdv;
	}
}
