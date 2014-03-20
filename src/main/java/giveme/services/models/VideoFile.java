package giveme.services.models;

import java.io.File;

public class VideoFile {
	private boolean isAVideo;
	private String name;
	private String path;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public boolean isAVideo() {
		return isAVideo;
	}
	public void setAVideo(boolean isAVideo) {
		this.isAVideo = isAVideo;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
}
