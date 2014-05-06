package giveme.controllers;

import static giveme.controllers.bindings.SharedBindings.positionChooser;
import giveme.common.beans.Video;
import giveme.controllers.bindings.SelectedVideoFromFile;
import giveme.services.FileExplorer;
import giveme.services.VideoServices;
import giveme.services.models.VideoFile;

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
public class VideoController {

    @Autowired
	FileExplorer fileExplorer;

    @Autowired
    VideoServices videoServices;
    
    private static final Logger LOGGER = Logger.getLogger(VideoController.class
			.getName());

	@RequestMapping(value = "/admin/video/new", method = RequestMethod.GET)
	public ModelAndView addAVideo()
	{
		ModelAndView mdv = new ModelAndView("/admin/video/choose");
		mdv.addObject("selectedVideo", new SelectedVideoFromFile());
		return mdv;
	}

    @RequestMapping(value = "/admin/video/new/{showId}/{seasonId}", method = RequestMethod.GET)
    public ModelAndView addAVideoWithShowAndSeason(@ModelAttribute("showId") int showId, @ModelAttribute("seasonId") int seasonId)
    {
        ModelAndView mdv = new ModelAndView("/admin/video/choose");
        mdv.addObject("selectedVideo", new SelectedVideoFromFile());
        SelectedVideoFromFile selectVideoModel = new SelectedVideoFromFile();
        selectVideoModel.setShowId(showId);
        selectVideoModel.setSeasonId(seasonId);
        return mdv;
    }
	
	@RequestMapping(value = "/admin/webservices/video/listVideos/{directoryId}", method = RequestMethod.GET)
	@ResponseBody
	public List<VideoFile> listVideos(@ModelAttribute("directoryId") int directoryId)
	{
		LOGGER.info("received directory " + directoryId);
		return fileExplorer.listVideos(directoryId);
	}
	
	@RequestMapping(value = "/admin/video/select", method = RequestMethod.POST)
	public ModelAndView buildVideo(@ModelAttribute("selectedVideo") SelectedVideoFromFile selectedVideo, HttpServletRequest context)
	{
		ModelAndView mdv = new ModelAndView("/admin/video/build-details");
		selectedVideo.setPath(selectedVideo.getPath().replaceAll("-", "/"));
		Video videoModel = new Video();
		videoModel.setRelativePath(selectedVideo.getPath());
		videoModel.setTitle(selectedVideo.getTitle());
        videoModel.setUrl(videoServices.buildUrl(context, videoModel.getRelativePath()));
		mdv.addObject("video", videoModel);
        mdv.addObject("positionList", positionChooser);

		return mdv;
	}
}
