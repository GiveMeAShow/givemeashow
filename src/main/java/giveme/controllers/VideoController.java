package giveme.controllers;

import java.util.List;

import giveme.common.beans.Video;
import giveme.controllers.bindings.SelectedVideoFromFile;
import giveme.controllers.bindings.SharedBindings;
import giveme.services.FileExplorer;
import giveme.services.VideoServices;
import giveme.services.models.VideoFile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

import static giveme.controllers.bindings.SharedBindings.*;

@Controller
public class VideoController {

    @Autowired
	FileExplorer fileExplorer;

    @Autowired
    VideoServices videoServices;

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

   /* @RequestMapping(value = "/admin/video/new", method = RequestMethod.GET)
    public ModelAndView addAVideoFromShow()
    {
        ModelAndView mdv = new ModelAndView("/admin/video/choose");
        mdv.addObject("selectedVideo", new SelectedVideoFromFile());
        return mdv;
    }*/
	
	@RequestMapping(value = "/admin/webservices/video/listVideos/{directoryName}", method = RequestMethod.GET)
	@ResponseBody
	public List<VideoFile> listVideos(@ModelAttribute("directoryName") String directoryName)
	{
		System.out.println("received directory " + directoryName);
		return fileExplorer.listVideos(directoryName);
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
