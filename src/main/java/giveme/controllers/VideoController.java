package giveme.controllers;

import java.util.List;

import giveme.common.beans.Video;
import giveme.controllers.bindings.SelectedVideoFromFile;
import giveme.services.FileExplorer;
import giveme.services.models.VideoFile;

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
	
	@RequestMapping(value = "/admin/video/new", method = RequestMethod.GET)
	public ModelAndView addAVideo()
	{
		ModelAndView mdv = new ModelAndView("/admin/video/choose");
		mdv.addObject("command", new SelectedVideoFromFile());
		return mdv;
	}
	
	@RequestMapping(value = "/admin/webservices/video/listVideos/{directoryName}", method = RequestMethod.GET)
	@ResponseBody
	public List<VideoFile> listVideos(@ModelAttribute("directoryName") String directoryName)
	{
		System.out.println("received directory " + directoryName);
		return fileExplorer.listVideos(directoryName);
	}
	
	@RequestMapping(value = "/admin/video/select", method = RequestMethod.POST)
	public ModelAndView buildVideo(@ModelAttribute("command") SelectedVideoFromFile selectedVideo)
	{
		ModelAndView mdv = new ModelAndView("/admin/video/build-details");
		selectedVideo.setPath(selectedVideo.getPath().replaceAll("-", "/"));
		Video formModel = new Video();
		formModel.setRelativePath(selectedVideo.getPath());
		formModel.setTitle(selectedVideo.getTitle());
		mdv.addObject("video", new Video());
		return mdv;
	}
}
