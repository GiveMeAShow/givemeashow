package giveme.controllers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import giveme.common.beans.Season;
import giveme.common.beans.Show;
import giveme.common.dao.SeasonDao;
import giveme.common.dao.ShowDao;
import giveme.controllers.bindings.SeasonAndShowName;

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
	
	@Autowired
	ShowDao showDao;

    List<Integer> positionChooser;

    public SeasonController(){
        positionChooser = new ArrayList<Integer>();
        for (int i = 0; i < 50; i++)
        {
            positionChooser.add(i);
        }
    }

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
	 * The admin page to create a new season
	 * @return
	 */
	@RequestMapping(value="/admin/season/new", method = RequestMethod.GET)
	public ModelAndView adminNewSeason()
	{
		ModelAndView mdv = new ModelAndView("/admin/season/createNew");
		mdv.addObject("seasonAndShowName", new SeasonAndShowName());
		mdv.addObject("nameList", showDao.listNames());
        mdv.addObject("positionList", positionChooser);
		return mdv;
	}
	
	/**
	 * The admin page to create a new season
	 * @return
	 */
	@RequestMapping(value="/admin/season/new/{showName}", method = RequestMethod.GET)
	public ModelAndView adminNewSesonByShowName(@ModelAttribute("showName") String showName)
	{
		ModelAndView mdv = new ModelAndView("/admin/season/createNew");
		mdv.addObject("seasonAndShowName", new SeasonAndShowName());
		List<String> nameList = new ArrayList<String>();
		nameList.add(showName);
		mdv.addObject("nameList", nameList);
		mdv.addObject("positionList", positionChooser);
		return mdv;
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
	
	/**
	 * Valid a show and insert it !
	 * @param show
	 * @return to a new page
	 */
	@RequestMapping(value="/admin/season/addSeason", method = RequestMethod.POST)
	public ModelAndView adminInsertNewShow(@ModelAttribute("seasonAndShowName") final SeasonAndShowName seasonAndShowName)
	{
		ModelAndView mdv = new ModelAndView("/admin/season/validInsertion");
		Show show = showDao.findByName(seasonAndShowName.getShowName());
		seasonAndShowName.getSeason().setShowId(show.getId());
		seasonDao.save(seasonAndShowName.getSeason());
		return mdv;
	}

    @RequestMapping(value = "/admin/season/{id}", method = RequestMethod.GET)
    public ModelAndView showSeasonDetails(@ModelAttribute("id") final Integer seasonId)
    {
        ModelAndView mdv = new ModelAndView("/admin/season/showSeason");
        mdv.addObject("season", seasonDao.findById(seasonId));

        return mdv;
    }
}
