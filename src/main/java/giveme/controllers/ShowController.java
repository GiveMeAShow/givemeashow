package giveme.controllers;

import giveme.common.beans.Season;
import giveme.common.beans.Show;
import giveme.common.dao.SeasonDao;
import giveme.common.dao.ShowDao;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ShowController
{
	@Autowired
	ShowDao		showDao;

	@Autowired
	SeasonDao	seasonDao;

	@RequestMapping(value = "/rest/shows/list", method = RequestMethod.GET)
	@ResponseBody
	public List<Show> list()
	{
		return showDao.list();
	}

	@RequestMapping(value = "/webservices/show", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public List<Show> showListWebservice()
	{
		return showDao.list();
	}

	/**
	 * Show the entire showList for an admin.
	 * 
	 * @return
	 */
	@RequestMapping(value = "/admin/show/list", method = RequestMethod.GET)
	public ModelAndView adminListShows()
	{
		ModelAndView mdv = new ModelAndView("/admin/show/showList.jsp");
		List<Show> showList = showDao.list();
		mdv.addObject("showList", showList);
		return mdv;
	}

	/**
	 * Returns the page associated to a show.
	 * 
	 * @param showId
	 * @return
	 */
	@RequestMapping(value = "/admin/show/{showId}", method = RequestMethod.GET)
	public ModelAndView showShow(@ModelAttribute("showId") int showId)
	{
		ModelAndView mdv = new ModelAndView("admin/show/showShow");
		mdv.addObject("show", showDao.findById(showId));

		List<Season> seasonList = seasonDao.listByShowId(showId);
		Collections.sort(seasonList);
		mdv.addObject("seasonList", seasonList);

		return mdv;
	}

	/**
	 * The admin page to create a new show
	 * 
	 * @return
	 */
	@RequestMapping(value = "/admin/show/new", method = RequestMethod.GET)
	public ModelAndView adminNewShowPage()
	{
		ModelAndView mdv = new ModelAndView("/admin/show/createNew.jsp");
		mdv.addObject("command", new Show());
		return mdv;
	}

	/**
	 * Valid a show and insert it !
	 * 
	 * @param show
	 * @return to a new page
	 */
	@RequestMapping(value = "/admin/show/addShow", method = RequestMethod.POST)
	public ModelAndView adminInsertNewShow(@ModelAttribute("command") final Show show)
	{
		ModelAndView mdv = new ModelAndView("/admin/show/validInsertion");
		showDao.save(show);
		mdv.addObject("show", show);
		return mdv;
	}
}
