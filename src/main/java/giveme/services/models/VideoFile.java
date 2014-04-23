package giveme.services.models;

import java.io.File;

public class VideoFile {
	private boolean isAVideo;
	private String name;
	private String path;
    private boolean isRoot;
    private Integer folderId;

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
	}

    public boolean isRoot() {
        return isRoot;
    }

    public void setRoot(boolean isRoot) {
        this.isRoot = isRoot;
    }
}
