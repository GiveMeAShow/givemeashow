package giveme.services.models;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class VideoFile {
	private boolean isAVideo;
	private String name;
	private String path;
    private boolean isRoot;
    private Integer folderId;
    private Integer parentfolderId;
    private String htmlPath;

    public Integer getFolderId() {
        return folderId;
    }

    public void setFolderId(Integer folderId) {
        this.folderId = folderId;
    }

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
		htmlPath = path.replace("\\", "\\\\");
	}

    public boolean isRoot() {
        return isRoot;
    }

    public void setRoot(boolean isRoot) {
        this.isRoot = isRoot;
    }

	public Integer getParentfolderId() {
		return parentfolderId;
	}

	public void setParentfolderId(Integer parentfolderId) {
		this.parentfolderId = parentfolderId;
	}

	public String getHtmlPath() {
		return htmlPath;
	}

	public void setHtmlPath(String htmlPath) {
		this.htmlPath = htmlPath;
	}
}
