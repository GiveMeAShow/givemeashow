package giveme.controllers;

import giveme.common.beans.Show;
import giveme.common.dao.ShowDao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ShowController {
	@Autowired
	ShowDao showDao;
	
	/**
	 * Show the entire showList for an admin.
	 * @return
	 */
	@RequestMapping(value="/admin/show/list", method = RequestMethod.GET)
	public ModelAndView adminListShows()
	{
		ModelAndView mdv = new ModelAndView("/admin/show/showList");
		List<Show> showList = showDao.list();
		mdv.addObject("showList", showList);
		return mdv;
	}
	
	/**
	 * The admin page to create a new show
	 * @return
	 */
	@RequestMapping(value="/admin/show/new", method = RequestMethod.GET)
	public ModelAndView adminNewShowPage()
	{
		ModelAndView mdv = new ModelAndView("/admin/show/createNew");
		mdv.addObject("command", new Show());
		return mdv;
	}
	
	/**
	 * Valid a show and insert it !
	 * @param show
	 * @return to a new page
	 */
	@RequestMapping(value="/admin/show/addShow", method = RequestMethod.POST)
	public ModelAndView adminInsertNewShow(@ModelAttribute("command") final Show show)
	{
		ModelAndView mdv = new ModelAndView("/admin/show/validInsertion");
		showDao.save(show);
		return mdv;
	}
}
