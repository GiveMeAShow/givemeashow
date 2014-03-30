package giveme.controllers.bindings;

public class SelectedVideoFromFile {
	private String title;
    private String path;
    private int showId;
    private int seasonId;

    public SelectedVideoFromFile()
    {
        seasonId = -1;
        showId = -1;
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String name) {
		this.title = name;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}

    public int getShowId() {
        return showId;
    }

    public void setShowId(int showId) {
        this.showId = showId;
    }

    public int getSeasonId() {
        return seasonId;
    }

    public void setSeasonId(int seasonId) {
        this.seasonId = seasonId;
    }
}
