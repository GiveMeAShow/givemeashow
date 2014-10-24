package giveme.controllers;

import static giveme.controllers.bindings.SharedBindings.positionChooser;
import giveme.common.beans.ISOLang;
import giveme.common.beans.Season;
import giveme.common.beans.Show;
import giveme.common.beans.Video;
import giveme.common.dao.ISOLangDao;
import giveme.common.dao.SeasonDao;
import giveme.common.dao.ShowDao;
import giveme.common.dao.VideoDao;
import giveme.controllers.bindings.SelectedVideoFromFile;
import giveme.services.AutomaticInserter;
import giveme.services.FileExplorer;
import giveme.services.VideoServices;
import giveme.services.models.VideoFile;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class VideoController
{
	public static final Logger	LOGGER	= Logger.getLogger(VideoController.class.getName());

	@Autowired
	FileExplorer				fileExplorer;

	@Autowired
	VideoServices				videoServices;

	@Autowired
	ShowDao						showDao;

	@Autowired
	SeasonDao					seasonDao;

	@Autowired
	ISOLangDao					langDao;

	@Autowired
	VideoDao					videoDao;

	@Autowired
	AutomaticInserter			autoInsert;

	public VideoController()
	{
		LOGGER.info("lol");
		// TODO Auto-generated constructor stub
	}

	@RequestMapping(value = "/admin/video/new", method = RequestMethod.GET)
	public ModelAndView newForm()
	{
		final ModelAndView mdv = new ModelAndView("/admin/video/choose");
		mdv.addObject("selectedVideo", new SelectedVideoFromFile());
		return mdv;
	}

	@RequestMapping(value = "/admin/video/auto", method = RequestMethod.GET)
	public ModelAndView autoInsert()
	{
		final ModelAndView mdv = new ModelAndView();
		autoInsert.runAndFillDatabase();
		return mdv;
	}

	@RequestMapping(value = "/admin/video/pending", method = RequestMethod.GET)
	public ModelAndView showPending()
	{
		final ModelAndView mdv = new ModelAndView("/admin/video/list");
		mdv.addObject("videoList", videoDao.listPEnding());
		return mdv;
	}

	@RequestMapping(value = "/admin/video/add", method = RequestMethod.POST)
	public String add(@ModelAttribute("video") final Video video)
	{
		LOGGER.info("Saving video " + video.getTitle());
		final ISOLang lang = langDao.findByISO(video.getLanguage());
		video.setLanguageIso(lang);
		Video existingVideo = videoDao.findByShowAndSeasonIdsAndTitle(video.getShowId(), video.getSeasonId(),
				video.getTitle());
		if (existingVideo != null)
		{
			video.setId(existingVideo.getId());
			videoDao.update(video);
		}
		else
		{
			videoDao.save(video);
		}
		final ModelAndView mdv = new ModelAndView("/admin/video/validInsertion");
		return "redirect: /admin/video/new";
	}

	@RequestMapping(value = "/admin/video/new/{showId}/{seasonId}", method = RequestMethod.GET)
	public ModelAndView addAVideoWithShowAndSeason(@ModelAttribute("showId") final int showId,
			@ModelAttribute("seasonId") final int seasonId)
	{
		final ModelAndView mdv = new ModelAndView("/admin/video/choose");
		mdv.addObject("selectedVideo", new SelectedVideoFromFile());
		final SelectedVideoFromFile selectVideoModel = new SelectedVideoFromFile();
		selectVideoModel.setShowId(showId);
		selectVideoModel.setSeasonId(seasonId);
		return mdv;
	}

	@RequestMapping(value = "/admin/webservices/video/listVideos/{directoryId}", method = RequestMethod.GET)
	@ResponseBody
	public List<VideoFile> listVideos(@ModelAttribute("directoryId") final int directoryId)
	{
		LOGGER.info("received directory " + directoryId);
		return fileExplorer.listVideos(directoryId);
	}

	@RequestMapping(value = "/admin/video/edit/{videoId}", method = RequestMethod.GET)
	public ModelAndView buildVideo(@ModelAttribute("videoId") final int videoId, final HttpServletRequest context)
	{
		final ModelAndView mdv = new ModelAndView("/admin/video/build-details");
		LOGGER.info("Editing video");
		Video video = videoDao.findById(videoId);
		video.setValidated(true);
		mdv.addObject("video", video);

		mdv.addObject("seasons", Arrays.asList(new Season[]
		{ seasonDao.findById(video.getSeasonId()) }));
		mdv.addObject("shows", Arrays.asList(new Show[]
		{ showDao.findById(video.getShowId()) }));
		mdv.addObject("langList", Arrays.asList(new ISOLang[]
		{ langDao.findByISO(video.getLanguageIso().getIso()) }));
		mdv.addObject("positionList", new Integer[]
		{ video.getPosition() });

		return mdv;
	}

	@RequestMapping(value = "/webservices/video/shuffled", method = RequestMethod.GET)
	@ResponseBody
	public List<Video> getShuffledList()
	{
		List<Video> videoList = videoDao.list();
		Collections.shuffle(videoList);
		return videoList;
	}

	@RequestMapping(value = "/admin/video/select", method = RequestMethod.POST)
	public ModelAndView buildVideo(@ModelAttribute("selectedVideo") final SelectedVideoFromFile selectedVideo,
			final HttpServletRequest context)
	{
		final ModelAndView mdv = new ModelAndView("/admin/video/build-details");
		final Video videoModel = new Video();

		videoModel.setUrl(videoServices.buildUrl(context, selectedVideo.getPath()));
		videoModel.setRelativePath(selectedVideo.getPath());
		videoModel.setTitle(selectedVideo.getTitle());

		mdv.addObject("video", videoModel);
		mdv.addObject("shows", showDao.list());
		mdv.addObject("seasons", seasonDao.list());
		mdv.addObject("langList", langDao.list());
		mdv.addObject("positionList", positionChooser);

		return mdv;
	}
}
