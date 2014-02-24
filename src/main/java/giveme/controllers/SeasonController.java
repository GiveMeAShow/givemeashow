package giveme.controllers;

import giveme.common.beans.Season;
import giveme.common.dao.SeasonDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class SeasonController {
	@Autowired
	SeasonDao seasonDao;
	
	/**
	 * Show the entire showList for an admin.
	 * @return
	 */
	@RequestMapping(value="/admin/season/list", method = RequestMethod.GET)
	public ModelAndView list()
	{
		ModelAndView view = new ModelAndView("/admin/season/seasonList");
		view.addObject("seasonList", seasonDao.list());
		return view;
	}
	
	/**
	 * Add a new season to a show
	 * @return
	 */
	@RequestMapping(value="/admin/season/addNew", method = RequestMethod.POST)
	public ModelAndView addSeasonToShow(@ModelAttribute("command") final Season season)
	{
		ModelAndView view = new ModelAndView("/admin/season/validInsertion");
		view.addObject("season", season);
		return view;
	}

	/**
	 * Show the entire showList for an admin.
	 * @return
	 */
	@RequestMapping(value="/admin/season/list/{showId}", method = RequestMethod.GET)
	public ModelAndView listByShowId(@PathVariable(value = "showId") int showId)
	{
		ModelAndView view = new ModelAndView("/admin/season/seasonList");
		view.addObject("seasonList", seasonDao.listByShowId(showId));
		return view;
	}
}
