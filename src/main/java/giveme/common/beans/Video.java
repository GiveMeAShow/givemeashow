package giveme.common.beans;

public class Video {
	private int id;
	private String title;
	private int seasonId;
	private ISOLang language;
	private int position;
	private boolean isTransition;
	private String relativePath;
	private long viewed;
	
	public long getViewed() {
		return viewed;
	}
	public void setViewed(long viewed) {
		this.viewed = viewed;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public int getSeasonId() {
		return seasonId;
	}
	public void setSeasonId(int seasonId) {
		this.seasonId = seasonId;
	}
	public ISOLang getLanguage() {
		return language;
	}
	public void setLanguage(ISOLang language) {
		this.language = language;
	}
	public int getPosition() {
		return position;
	}
	public void setPosition(int position) {
		this.position = position;
	}
	public boolean isTransition() {
		return isTransition;
	}
	public void setTransition(boolean isTransition) {
		this.isTransition = isTransition;
	}
	public String getRelativePath() {
		return relativePath;
	}
	public void setRelativePath(String relativePath) {
		this.relativePath = relativePath;
	}
}
