package giveme.controllers;

import giveme.common.dao.SeasonDao;
import giveme.common.dao.ShowDao;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class StreamingController
{
	private static final Logger	LOGGER	= Logger.getLogger(StreamingController.class.getName());

	@Autowired
	SeasonDao					seasonDao;

	@Autowired
	ShowDao						showDao;

	List<Integer>				positionChooser;

	/**
	 * Show the entire showList for an admin.
	 *
	 * @return
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView list()
	{
		ModelAndView mdv = new ModelAndView("/index");
		return mdv;
	}
}
