package giveme.controllers;

import giveme.common.beans.User;
import giveme.common.dao.UserDao;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UserController
{
	public static final Logger	LOGGER	= Logger.getLogger(UserController.class.getName());

	@Autowired
	UserDao						userDao;

	public UserController()
	{
		// TODO Auto-generated constructor stub
	}

	@RequestMapping(value = "/admin**", method = RequestMethod.GET)
	public ModelAndView adminPage()
	{

		ModelAndView model = new ModelAndView();
		model.addObject("title", "Spring Security Custom Login Form");
		model.addObject("message", "This is protected page!");
		model.setViewName("admin");

		return model;

	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ModelAndView login()
	{
		ModelAndView model = new ModelAndView("/login");
		User user = new User();
		user.setLogin("dqsd");
		user.setPassword("qdsd");
		model.addObject("user", user);

		return model;
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ModelAndView validLogin(@ModelAttribute("user") User user, HttpServletRequest request,
			HttpServletResponse response) throws IOException
	{
		ModelAndView model = new ModelAndView("/login");
		if (user != null)
		{
			String login = user.getLogin();
			user = userDao.findByLoginAndPassword(user.getLogin(), user.getPassword());
			if (user == null)
			{
				model.addObject("error", "No username " + login + " found.");
				return model;
			}
			else
			{
				UserDetails userDetails = userDao.loadUserByUsername(user.getLogin());
				Authentication auth = new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(),
						userDetails.getAuthorities());
				SecurityContextHolder.getContext().setAuthentication(auth);
				LOGGER.info("user " + user.getLogin() + " logged in.");
				return new ModelAndView("redirect:/");
			}
		}
		return model;
	}

	// @RequestMapping(value = "/admin/video/new", method = RequestMethod.GET)
	// public ModelAndView newForm()
	// {
	// final ModelAndView mdv = new ModelAndView("/admin/video/choose");
	// mdv.addObject("selectedVideo", new SelectedVideoFromFile());
	// return mdv;
	// }
	//
	// @RequestMapping(value = "/admin/video/auto", method = RequestMethod.GET)
	// public ModelAndView autoInsert()
	// {
	// final ModelAndView mdv = new ModelAndView();
	// autoInsert.runAndFillDatabase();
	// return mdv;
	// }
	//
	// @RequestMapping(value = "/admin/video/pending", method =
	// RequestMethod.GET)
	// public ModelAndView showPending()
	// {
	// final ModelAndView mdv = new ModelAndView("/admin/video/list");
	// mdv.addObject("videoList", videoDao.listPEnding());
	// return mdv;
	// }
	//
	// @RequestMapping(value = "/admin/video/add", method = RequestMethod.POST)
	// public String add(@ModelAttribute("video") final Video video)
	// {
	// LOGGER.info("Saving video " + video.getTitle());
	// final ISOLang lang = langDao.findByISO(video.getLanguage());
	// video.setLanguageIso(lang);
	// Video existingVideo =
	// videoDao.findByShowAndSeasonIdsAndTitle(video.getShowId(),
	// video.getSeasonId(),
	// video.getTitle());
	// if (existingVideo != null)
	// {
	// video.setId(existingVideo.getId());
	// videoDao.update(video);
	// }
	// else
	// {
	// videoDao.save(video);
	// }
	// final ModelAndView mdv = new ModelAndView("/admin/video/validInsertion");
	// return "redirect: /admin/video/new";
	// }
	//
	// @RequestMapping(value = "/admin/video/new/{showId}/{seasonId}", method =
	// RequestMethod.GET)
	// public ModelAndView addAVideoWithShowAndSeason(@ModelAttribute("showId")
	// final int showId,
	// @ModelAttribute("seasonId") final int seasonId)
	// {
	// final ModelAndView mdv = new ModelAndView("/admin/video/choose");
	// mdv.addObject("selectedVideo", new SelectedVideoFromFile());
	// final SelectedVideoFromFile selectVideoModel = new
	// SelectedVideoFromFile();
	// selectVideoModel.setShowId(showId);
	// selectVideoModel.setSeasonId(seasonId);
	// return mdv;
	// }
	//
	// @RequestMapping(value =
	// "/admin/webservices/video/listVideos/{directoryId}", method =
	// RequestMethod.GET)
	// @ResponseBody
	// public List<VideoFile> listVideos(@ModelAttribute("directoryId") final
	// int directoryId)
	// {
	// LOGGER.info("received directory " + directoryId);
	// return fileExplorer.listVideos(directoryId);
	// }
	//
	// @RequestMapping(value = "/admin/video/edit/{videoId}", method =
	// RequestMethod.GET)
	// public ModelAndView buildVideo(@ModelAttribute("videoId") final int
	// videoId, final HttpServletRequest context)
	// {
	// final ModelAndView mdv = new ModelAndView("/admin/video/build-details");
	// LOGGER.info("Editing video");
	// Video video = videoDao.findById(videoId);
	// video.setValidated(true);
	// mdv.addObject("video", video);
	//
	// mdv.addObject("seasons", Arrays.asList(new Season[]
	// { seasonDao.findById(video.getSeasonId()) }));
	// mdv.addObject("shows", Arrays.asList(new Show[]
	// { showDao.findById(video.getShowId()) }));
	// mdv.addObject("langList", Arrays.asList(new ISOLang[]
	// { langDao.findByISO(video.getLanguageIso().getIso()) }));
	// mdv.addObject("positionList", new Integer[]
	// { video.getPosition() });
	//
	// return mdv;
	// }
	//
	// @RequestMapping(value = "/webservices/video/shuffled", method =
	// RequestMethod.GET)
	// @ResponseBody
	// public List<Video> getShuffledList()
	// {
	// List<Video> videoList = videoDao.list();
	// Collections.shuffle(videoList);
	// return videoList;
	// }
	//
	// @RequestMapping(value = "/admin/video/select", method =
	// RequestMethod.POST)
	// public ModelAndView buildVideo(@ModelAttribute("selectedVideo") final
	// SelectedVideoFromFile selectedVideo,
	// final HttpServletRequest context)
	// {
	// final ModelAndView mdv = new ModelAndView("/admin/video/build-details");
	// final Video videoModel = new Video();
	//
	// videoModel.setUrl(videoServices.buildUrl(context,
	// selectedVideo.getPath()));
	// videoModel.setRelativePath(selectedVideo.getPath());
	// videoModel.setTitle(selectedVideo.getTitle());
	//
	// mdv.addObject("video", videoModel);
	// mdv.addObject("shows", showDao.list());
	// mdv.addObject("seasons", seasonDao.list());
	// mdv.addObject("langList", langDao.list());
	// mdv.addObject("positionList", positionChooser);
	//
	// return mdv;
	// }
}
